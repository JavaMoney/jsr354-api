/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package javax.money.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.convert.ExchangeRate;
import javax.money.ext.spi.RegionsSingletonSpi;

/**
 * Platform RI: this class provides an updatable/reloadable data cache for
 * providing data sources that are updatable by any remote {@link URL}s. Initial
 * version are loaded from the classpath. A {@link UpdatePolicy} can be set and
 * configured to enable automatic updating, e.g. of {@link ExchangeRate}s. By
 * default the {@link UpdatePolicy#NEVER}.
 * <p>
 * This class is used throughout the RI for managing/updating/reloading of data
 * sources, e.g. data streams for exchange rates, additional currency data,
 * historical currency data and so on.
 * <p>
 * Note: this class is implementation specific and not part of the official JSR's API.
 * 
 * @author Anatole Tresch
 */
public final class DataLoader {
	/** the spi loaded from the {@link ServiceLoader}. */
	private static DataLoaderSingletonSpi loaderSpi = loadSpi();

	/**
	 * Private singleton constructor.
	 */
	private DataLoader() {
		// singleton
	}

	/**
	 * Init method that tries to load an instance of
	 * {@link DataLoaderSingletonSpi} from the {@link ServiceLoader}:
	 * 
	 * @return the {@link DataLoaderSingletonSpi} instance to be used.
	 */
	private static DataLoaderSingletonSpi loadSpi() {
		try {
			// try loading directly from ServiceLoader
			Iterator<DataLoaderSingletonSpi> instances = ServiceLoader.load(
					DataLoaderSingletonSpi.class).iterator();
			DataLoaderSingletonSpi spiLoaded = null;
			if (instances.hasNext()) {
				spiLoaded = instances.next();
				if (instances.hasNext()) {
					throw new IllegalStateException(
							"Ambigous reference to spi (only "
									+ "one can be registered: "
									+ DataLoaderSingletonSpi.class.getName());
				}
				return spiLoaded;
			}
		} catch (Throwable e) {
			Logger.getLogger(RegionsSingletonSpi.class.getName()).log(
					Level.INFO,
					"No MonetaryRegionSpi registered, using  default.", e);
		}
		return null;
	}

	/**
	 * Platform RI: The update policy defines how and when the
	 * {@link DataLoader} tries to update the local cache with newest version of
	 * the registered data resources, accessing the configured remote
	 * {@link URL}s. By default no remote connections are done (
	 * {@link UpdatePolicy#NEVER} ).
	 * 
	 * @author Anatole Tresch
	 */
	public enum UpdatePolicy {
		NEVER,
		ONSTARTUP,
		SCHEDULED
	}

	/**
	 * Programmatically registers a remote resource {@code resourceLocation},
	 * backed up by a classpath resource {@code backupResource}, reachable as
	 * {@code dataId}.
	 * 
	 * @param dataId
	 *            The unique identifier of the resource that must also be used
	 *            for accessing the resource, not {@code null}.
	 * @param resourceLocation
	 *            The remote resource location, not {@code null}.
	 * @param backupResource
	 *            The backup resource location in the classpath, not
	 *            {@code null}.
	 */
	public void registerData(String dataId, URL resourceLocation,
			String backupResource) {
		loaderSpi.registerData(dataId, resourceLocation, backupResource);
	}

	/**
	 * Configures the {@link UpdatePolicy} for a registered resource.
	 * 
	 * @param dataId
	 *            The unique identifier of the resource, not {@code null}.
	 * @param policy
	 *            The new policy to be applied, not {@code null}.
	 * @param config
	 *            Optional configuration properties.
	 */
	public void configureUpdatePolicy(String dataId, UpdatePolicy policy,
			Properties config) {
		loaderSpi.configureUpdatePolicy(dataId, policy, config);
	}

	/**
	 * Get the current active {@link UpdatePolicy} for a resource.
	 * 
	 * @param dataId
	 *            The unique identifier of the resource, not {@code null}.
	 * @return the current active {@link UpdatePolicy}, not {@code null}.
	 */
	public UpdatePolicy getUpdatePolicy(String dataId) {
		return loaderSpi.getUpdatePolicy(dataId);
	}

	/**
	 * Get the current active {@link UpdatePolicy} configuration for a resource.
	 * 
	 * @param dataId
	 *            The unique identifier of the resource, not {@code null}.
	 * @return the current active {@link UpdatePolicy} configuration, may be
	 *         also {@code null}.
	 */
	public Properties getUpdatePolicyConfiguration(String dataId) {
		return loaderSpi.getUpdatePolicyConfiguration(dataId);
	}

	/**
	 * Add a {@link LoadListener} callback that is informed when a data resource
	 * was update from remote, or reset. Passing an empty String or {@code null}
	 * sa {@code dataId} allows to register a listener for all data resources
	 * registered. {@link #loadData(String)} {@link #resetData(String)}
	 * 
	 * @see #removeLoadListener(String, LoadListener)
	 * @param dataId
	 *            The unique identifier of the resource, not {@code null}.
	 * @param l
	 *            The listener to be added
	 */
	public void addLoadListener(String dataId, LoadListener l) {
		loaderSpi.addLoadListener(dataId, l);
	}

	/**
	 * Remove a registered {@link LoadListener} callback.
	 * 
	 * @see #addLoadListener(String, LoadListener)
	 * @param dataId
	 *            The unique identifier of the resource, not {@code null}.
	 * @param l
	 *            The listener to be removed
	 */
	public void removeLoadListener(String dataId, LoadListener l) {
		loaderSpi.removeLoadListener(dataId, l);
	}

	/**
	 * Allows to check if a data resource with the given dataId is registered.
	 * 
	 * @param dataId
	 *            The unique identifier of the resource, not {@code null}.
	 * @return {@code true}, if such a data resource is registered.
	 */
	public boolean isRegistered(String dataId) {
		return loaderSpi.isRegistered(dataId);
	}

	/**
	 * Get a {@link Set} of all registered data resource identifiers.
	 * 
	 * @return a {@link Set} of all registered data resource identifiers, never
	 *         {@code null}.
	 */
	public Set<String> getRegisteredDataIds() {
		return loaderSpi.getRegisteredDataIds();
	}

	/**
	 * Access the input stream of the given data resource. This method is called
	 * by the modules that depend on the given data item. The method always
	 * returns the most current data, either from the classpath or the local
	 * cache, depending which flavors are available and recently updated.<br/>
	 * The method must be thread safe and can be accessed in parallel. Hereby it
	 * is possible that, when an intermediate update of the data by update
	 * occurs, that different input stream content is returned.
	 * 
	 * @see #configureUpdatePolicy(String, UpdatePolicy, Properties)
	 * @param dataId
	 *            The unique identifier of the resource, not {@code null}.
	 * @return The {@link InputStream} for reading the data.
	 * @throws IOException
	 *             if a problem occurred.
	 */
	public InputStream getInputStream(String dataId) throws IOException {
		return loaderSpi.getInputStream(dataId);
	}

	/**
	 * Explicitly triggers the remote loading of the registered data, regardless
	 * of its current {@link UpdatePolicy} configured.
	 * 
	 * @param dataId
	 *            The unique identifier of the resource, not {@code null}.
	 * @throws IOException
	 *             if a problem occurred.
	 */
	public void loadData(String dataId) throws IOException {
		loaderSpi.loadData(dataId);
	}

	/**
	 * Explicitly asynchronously triggers the remote loading of the registered
	 * data, regardless of its current {@link UpdatePolicy} configured.
	 * 
	 * @param dataId
	 *            The unique identifier of the resource, not {@code null}.
	 * @return the Future of the load task started.
	 */
	public Future<?> loadDataAsynch(String dataId) {
		return loaderSpi.loadDataAsynch(dataId);
	}

	/**
	 * Explicitly triggers the reset (loading of the registered data from the
	 * classpath backup resource).
	 * 
	 * @param dataId
	 *            The unique identifier of the resource, not {@code null}.
	 * @throws IOException
	 *             if a problem occurred.
	 */
	public void resetData(String dataId) throws IOException {
		loaderSpi.resetData(dataId);
	}

	/**
	 * Platform RI: Callback that can be registered to be informed, when a data
	 * item was loaded/updated or reset.
	 * 
	 * @see DataLoader#resetData(String)
	 * @see DataLoader#loadData(String)
	 * @author Anatole Tresch
	 */
	public static interface LoadListener {
		public void dataLoaded(String dataId);
	}

	/**
	 * Platform RI: this class provides the SPI for the {@link DataLoader}
	 * singleton to be implemented by the implementations.
	 * 
	 * @author Anatole Tresch
	 */
	public interface DataLoaderSingletonSpi {
		/**
		 * Programmatically registers a remote resource {@code resourceLocation}
		 * , backed up by a classpath resource {@code backupResource}, reachable
		 * as {@code dataId}.
		 * 
		 * @param dataId
		 *            The unique identifier of the resource that must also be
		 *            used for accessing the resource, not {@code null}.
		 * @param resourceLocation
		 *            The remote resource location, not {@code null}.
		 * @param backupResource
		 *            The backup resource location in the classpath, not
		 *            {@code null}.
		 */
		public void registerData(String dataId, URL resourceLocation,
				String backupResource);

		/**
		 * Configures the {@link UpdatePolicy} for a registered resource.
		 * 
		 * @param dataId
		 *            The unique identifier of the resource, not {@code null}.
		 * @param policy
		 *            The new policy to be applied, not {@code null}.
		 * @param config
		 *            Optional configuration properties.
		 */
		public void configureUpdatePolicy(String dataId, UpdatePolicy policy,
				Properties config);

		/**
		 * Get the current active {@link UpdatePolicy} configuration for a
		 * resource.
		 * 
		 * @param dataId
		 *            The unique identifier of the resource, not {@code null}.
		 * @return the current active {@link UpdatePolicy} configuration, may be
		 *         also {@code null}.
		 */
		public UpdatePolicy getUpdatePolicy(String dataId);

		/**
		 * Get the current active {@link UpdatePolicy} configuration for a
		 * resource.
		 * 
		 * @param dataId
		 *            The unique identifier of the resource, not {@code null}.
		 * @return the current active {@link UpdatePolicy} configuration, may be
		 *         also {@code null}.
		 */
		public Properties getUpdatePolicyConfiguration(String dataId);

		/**
		 * Add a {@link LoadListener} callback that is informed when a data
		 * resource was update from remote, or reset. Passing an empty String or
		 * {@code null} sa {@code dataId} allows to register a listener for all
		 * data resources registered. {@link #loadData(String)}
		 * {@link #resetData(String)}
		 * 
		 * @see #removeLoadListener(String, LoadListener)
		 * @param dataId
		 *            The unique identifier of the resource, not {@code null}.
		 * @param l
		 *            The listener to be added
		 */
		public void addLoadListener(String dataId, LoadListener l);

		/**
		 * Remove a registered {@link LoadListener} callback.
		 * 
		 * @see #addLoadListener(String, LoadListener)
		 * @param dataId
		 *            The unique identifier of the resource, not {@code null}.
		 * @param l
		 *            The listener to be removed
		 */
		public void removeLoadListener(String dataId, LoadListener l);

		/**
		 * Allows to check if a data resource with the given dataId is
		 * registered.
		 * 
		 * @param dataId
		 *            The unique identifier of the resource, not {@code null}.
		 * @return {@code true}, if such a data resource is registered.
		 */
		public boolean isRegistered(String dataId);

		/**
		 * Get a {@link Set} of all registered data resource identifiers.
		 * 
		 * @return a {@link Set} of all registered data resource identifiers,
		 *         never {@code null}.
		 */
		public Set<String> getRegisteredDataIds();

		/**
		 * Access the input stream of the given data resource. This method is
		 * called by the modules that depend on the given data item. The method
		 * always returns the most current data, either from the classpath or
		 * the local cache, depending which flavors are available and recently
		 * updated.<br/>
		 * The method must be thread safe and can be accessed in parallel.
		 * Hereby it is possible that, when an intermediate update of the data
		 * by update occurs, that different input stream content is returned.
		 * 
		 * @see #configureUpdatePolicy(String, UpdatePolicy, Properties)
		 * @param dataId
		 *            The unique identifier of the resource, not {@code null}.
		 * @return The {@link InputStream} for reading the data.
		 * @throws IOException
		 *             if a problem occurred.
		 */
		public InputStream getInputStream(String dataId) throws IOException;

		/**
		 * Explicitly triggers the remote loading of the registered data,
		 * regardless of its current {@link UpdatePolicy} configured.
		 * 
		 * @param dataId
		 *            The unique identifier of the resource, not {@code null}.
		 * @throws IOException
		 *             if a problem occurred.
		 */
		public void loadData(String resourceId) throws IOException;

		/**
		 * Explicitly asynchronously triggers the remote loading of the
		 * registered data, regardless of its current {@link UpdatePolicy}
		 * configured.
		 * 
		 * @param resourceId
		 * @return the Future of the load task started.
		 */
		public Future<?> loadDataAsynch(String resourceId);

		/**
		 * Explicitly triggers the reset (loading of the registered data from
		 * the classpath backup resource).
		 * 
		 * @param dataId
		 *            The unique identifier of the resource, not {@code null}.
		 * @throws IOException
		 *             if a problem occurred.
		 */
		public void resetData(String dataId) throws IOException;

	}

}
