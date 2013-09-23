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
 * 
 * Contributors: Anatole Tresch - initial implementation
 */
package org.javamoney.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;


import org.javamoney.util.DataLoader.UpdatePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represent a resource that automatically is reloaded, if needed.
 * 
 * @author Anatole Tresch
 */
public class LoadableResource {

	private static final Logger LOG = LoggerFactory
			.getLogger(LoadableResource.class);

	private String resourceId;
	private URL remoteResource;
	private String defaultResource;
	private URL cachedResource;
	private AtomicInteger loadCount = new AtomicInteger();
	private AtomicInteger accessCount = new AtomicInteger();
	private byte[] data;
	private long lastLoaded;
	private final Object LOCK = new Object();
	private UpdatePolicy updatePolicy = UpdatePolicy.NEVER;
	private Properties updateConfig;

	public LoadableResource(String resourceId, URL location,
			String classpathDefault) {
		if (resourceId == null) {
			throw new IllegalArgumentException("resourceId required");
		}
		if (classpathDefault == null) {
			throw new IllegalArgumentException("classpathDefault required");
		}
		if (getClass().getResource(classpathDefault) == null) {
			throw new IllegalArgumentException(classpathDefault
					+ " not found in classpath.");
		}
		this.resourceId = resourceId;
		this.remoteResource = location;
		this.defaultResource = classpathDefault;
	}

	public InputStream load() throws IOException {
		synchronized (LOCK) {
			accessCount.incrementAndGet();
			if (this.data == null) {
				loadData(null);
			}
		}
		return new WrappedInputStream(new ByteArrayInputStream(this.data));
	}

	/**
	 * @return the updatePolicy
	 */
	public final UpdatePolicy getUpdatePolicy() {
		return updatePolicy;
	}

	/**
	 * @param updatePolicy
	 *            the updatePolicy to set
	 */
	public final void setUpdatePolicy(UpdatePolicy updatePolicy,
			Properties properties) {
		if (updatePolicy == null) {
			throw new IllegalArgumentException("UpdatePolicy required");
		}
		this.updatePolicy = updatePolicy;
		this.updateConfig = properties;
		ResourceLoader.getInstance().removeScheduledLoad(this);
		switch (updatePolicy) {
		case SCHEDULED:
			ResourceLoader.getInstance().addScheduledLoad(this);
			break;
		default:
			break;
		}
	}

	/**
	 * @return the resourceId
	 */
	public final String getResourceId() {
		return resourceId;
	}

	/**
	 * @return the resourceLoader
	 */
	public final URL getRemoteResource() {
		return remoteResource;
	}

	/**
	 * @return the defaultResource
	 */
	public final String getDefaultResource() {
		return defaultResource;
	}

	/**
	 * @return the cachedResource
	 */
	public final URL getCachedResource() {
		return cachedResource;
	}

	/**
	 * @return the loadCount
	 */
	public final AtomicInteger getLoadCount() {
		return loadCount;
	}

	/**
	 * @return the accessCount
	 */
	public final AtomicInteger getAccessCount() {
		return accessCount;
	}

	/**
	 * @return the data
	 */
	public final byte[] getData() {
		return data.clone();
	}

	/**
	 * @return the lastLoaded
	 */
	public final long getLastLoaded() {
		return lastLoaded;
	}

	private void loadData(URL url) throws IOException {
		URL itemToLoad = url;
		if (itemToLoad == null) {
			itemToLoad = getLoadableURL();
		}
		if (itemToLoad != null) {
			InputStream is = null;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				URLConnection conn = itemToLoad.openConnection();
				byte[] data = new byte[4096];
				is = conn.getInputStream();
				int read = is.read(data);
				while(read>0){
					bos.write(data, 0, read);
					read = is.read(data);
				}
				this.data = bos.toByteArray();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
						LOG.error("Error closing resource input for "
								+ resourceId, e);
					}
				}
				if(bos!=null){
					bos.close();
				}
			}
		}
		else {
			InputStream is = null;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				is = this.remoteResource.openStream();
				byte[] data = new byte[4096]; // 4k
				int read = 0;

				while (read > 0) {
					read = is.read(data);
					bos.write(data, 0, read);
				}
				this.data = bos.toByteArray();
				updateCacheIfNeeded();
				lastLoaded = System.currentTimeMillis();
				loadCount.incrementAndGet();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
						LOG.error("Error closing resource input for "
								+ resourceId, e);
					}
				}
				if (bos != null) {
					bos.close();
				}
			}
		}
	}

	private void updateCacheIfNeeded() {
		// Evaluate cache location
		// write this.data to location
		// update cachedUrl
	}

	private URL getLoadableURL() {
		switch (updatePolicy) {
		case NEVER:
			if (cachedResource != null) {
				return cachedResource;
			}
			return getClassLoader().getResource(defaultResource);
		case ONSTARTUP:
			if (loadCount.get() == 0 && remoteResource != null) {
				return null; // trigger initial load
			}
			if (cachedResource != null) {
				return cachedResource;
			}
			return getClassLoader().getResource(defaultResource);
		case SCHEDULED:
		default:
			if (loadCount.get() == 0 && remoteResource != null) {
				return null; // trigger initial load
			}
			if (cachedResource != null) {
				return cachedResource;
			}
			return getClassLoader().getResource(defaultResource);
		}
	}

	private ClassLoader getClassLoader() {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null) {
			cl = getClass().getClassLoader();
		}
		return cl;
	}

	public void unload() {
		synchronized (LOCK) {
			int count = accessCount.decrementAndGet();
			if (count == 0) {
				this.data = null;
			}
		}
	}

	private final class WrappedInputStream extends InputStream {

		private InputStream wrapped;

		public WrappedInputStream(InputStream wrapped) {
			this.wrapped = wrapped;
		}

		@Override
		public int read() throws IOException {
			return wrapped.read();
		}

		@Override
		public void close() throws IOException {
			try {
				wrapped.close();
				super.close();
			} finally {
				unload();
			}
		}

	}

	public void reset() throws IOException {
		InputStream is = null;
		try {
			URLConnection conn = getClass().getResource(this.defaultResource)
					.openConnection();
			byte[] data = new byte[conn.getContentLength()];
			is = conn.getInputStream();
			is.read(data);
			this.data = data;
			loadCount.set(0);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					LOG.error("Error closing resource input for "
							+ resourceId, e);
				}
			}
		}
	}

	public Properties getUpdatePolicyConfig() {
		return this.updateConfig;
	}

	public void updateRemote() throws IOException {
		loadData(this.remoteResource);
	}

}
