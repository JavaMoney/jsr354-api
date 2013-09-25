/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation. 
 * Werner Keil - extension and adjustment.
 */
package org.javamoney.loader;

import java.io.InputStream;
import java.net.URL;


import org.javamoney.util.DataLoader.LoadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstraction of a resource.
 * 
 * @version 0.9
 * @author Anatole Tresch
 * @author Werner Keil
 */
public abstract class AbstractResource {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final String resourceId;

	/**
	 * This flags allow to determine along with LoadableResource#getLastLoaded()
	 * if a newer version of the file was loaded.
	 */
	private long lastLoaded;

	public AbstractResource(String resourceId, URL resource,
			String defaultItem) {
		ResourceLoader.getInstance().registerData(resourceId, resource,
				defaultItem);
		this.resourceId = resourceId;
		ResourceLoader.getInstance().addLoadListener(resourceId,
				new LoadListener() {
					@Override
					public void dataLoaded(String dataId) {
						AbstractResource.this.dataLoaded();
					}
				});
	}

	/**
	 * Get the identifier of this resource.
	 * 
	 * @return The identifier.
	 */
	public String getId() {
		return resourceId;
	}

	/**
	 * The time stamp when this resource was last loaded
	 * @return the loading time stamp
	 */
	public long getLastLoaded() {
		return lastLoaded;
	}

	public void load() {
		ResourceLoader.getInstance().loadData(this.resourceId);
	}

	public void loadAsynch() {
		ResourceLoader.getInstance().loadDataAsynch(this.resourceId);
	}

	protected void dataLoaded() {
		InputStream is = null;
		try {
			is = ResourceLoader.getInstance().getInputStream(resourceId);
			loadData(is);
			lastLoaded = System.currentTimeMillis();
		} catch (Exception e) {
			logger.error("Failed to load resource " + this.resourceId, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					logger.error("Error closing input stream from "
							+ this.resourceId, e);
				}
			}
		}
	}

	protected abstract void loadData(InputStream is) throws Exception;

}
