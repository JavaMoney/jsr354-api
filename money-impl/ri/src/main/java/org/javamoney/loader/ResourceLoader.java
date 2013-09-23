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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import org.javamoney.util.DataLoader.DataLoaderSingletonSpi;
import org.javamoney.util.DataLoader.LoadListener;
import org.javamoney.util.DataLoader.UpdatePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides a mechanism to register resources, that may be updated
 * regularly. The implementation, based on the {@link UpdatePolicy}
 * loads/updates the resources from arbitrary locations and stores it to the
 * interal file cache.
 * 
 * @author Anatole Tresch
 */
public class ResourceLoader implements DataLoaderSingletonSpi {

	private static final Logger LOG = LoggerFactory
			.getLogger(ResourceLoader.class);

	private Map<String, LoadableResource> resources = new ConcurrentHashMap<>();

	private Map<String, List<LoadListener>> listenersMap = new ConcurrentHashMap<>();

	private ResourceCache resourceCache = loadResourceCache();

	private final static ResourceLoader INSTANCE = new ResourceLoader();

	private volatile Timer timer;

	private ExecutorService loaderService = Executors.newCachedThreadPool();

	private ResourceLoader() {
	}

	private Timer getTimer() {
		if (timer == null) {
			synchronized (resources) {
				if (timer == null) {
					timer = new Timer();
				}
			}
		}
		return timer;
	}

	private ResourceCache loadResourceCache() {
		try {
			for (ResourceCache cache : ServiceLoader.load(ResourceCache.class)) {
				if (cache == null) {
					this.resourceCache = cache;
				}
				else {
					LOG.warn("Only one ResourceCache supported, "
							+ cache.getClass() + " will be ignored.");
				}
			}
		} catch (Exception e) {
			LOG.error("Error loading ResourceCache instances.", e);
		}
		if (this.resourceCache == null) {
			this.resourceCache = new DefaultResourceCache();
		}
		return null;
	}

	public static ResourceLoader getInstance() {
		return INSTANCE;
	}

	public ResourceCache getResourceCache() {
		return this.resourceCache;
	}

	public void unload(String resourceId) {
		LoadableResource res = this.resources.get(resourceId);
		if (res != null) {
			res.unload();
		}
	}

	@Override
	public void registerData(String resourceId, URL resourceLocation,
			String backupResource) {
		if (resources.containsKey(resourceId)) {
			throw new IllegalArgumentException("Resource : " + resourceId
					+ " already registered.");
		}
		LoadableResource res = new LoadableResource(resourceId,
				resourceLocation, backupResource);
		this.resources.put(resourceId, res);
	}

	@Override
	public void configureUpdatePolicy(String resourceId,
			org.javamoney.util.DataLoader.UpdatePolicy policy, Properties config) {
		LoadableResource res = this.resources.get(resourceId);
		if (res != null) {
			res.setUpdatePolicy(policy, config);
		}
		throw new IllegalArgumentException("No such resource: " + resourceId);
	}

	@Override
	public org.javamoney.util.DataLoader.UpdatePolicy getUpdatePolicy(
			String resourceId) {
		LoadableResource res = this.resources.get(resourceId);
		if (res != null) {
			return res.getUpdatePolicy();
		}
		throw new IllegalArgumentException("No such resource: " + resourceId);
	}

	@Override
	public Properties getUpdatePolicyConfiguration(String resourceId) {
		LoadableResource res = this.resources.get(resourceId);
		if (res != null) {
			return res.getUpdatePolicyConfig();
		}
		return null;
	}

	@Override
	public boolean isRegistered(String dataId) {
		return this.resources.containsKey(dataId);
	}

	@Override
	public Set<String> getRegisteredDataIds() {
		return this.resources.keySet();
	}

	@Override
	public InputStream getInputStream(String resourceId) throws IOException {
		LoadableResource res = this.resources.get(resourceId);
		if (res != null) {
			return res.load();
		}
		throw new IllegalArgumentException("No such resource: " + resourceId);
	}

	@Override
	public void loadData(String resourceId) {
		loadDataSynch(resourceId);
	}

	@Override
	public Future<?> loadDataAsynch(final String resourceId) {
		return loaderService.submit(new Runnable() {
			@Override
			public void run() {
				loadDataSynch(resourceId);
			}
		});
	}

	private void loadDataSynch(String resourceId) {
		LoadableResource res = this.resources.get(resourceId);
		if (res != null) {
			try {
				res.updateRemote();
				triggerListeners(resourceId);
			} catch (Exception e) {
				LOG.error("Failed to load resource: " + resourceId, e);
			}
		}
		else {
			throw new IllegalArgumentException("No such resource: "
					+ resourceId);
		}
	}

	@Override
	public void resetData(String dataId) throws IOException {
		LoadableResource res = this.resources.get(dataId);
		if (res != null) {
			res.reset();
			triggerListeners(dataId);
		}
		else {
			throw new IllegalArgumentException("No such resource: " + dataId);
		}
	}

	private void triggerListeners(String dataId) {
		List<LoadListener> listeners = getListeners("");
		synchronized (listeners) {
			for (LoadListener ll : listeners) {
				try {
					ll.dataLoaded(dataId);
				} catch (Exception e) {
					LOG.error("Error calling LoadListener: " + ll, e);
				}
			}
		}
		if (!(dataId == null || dataId.isEmpty())) {
			listeners = getListeners(dataId);
			synchronized (listeners) {
				for (LoadListener ll : listeners) {
					try {
						ll.dataLoaded(dataId);
					} catch (Exception e) {
						LOG.error("Error calling LoadListener: " + ll, e);
					}
				}
			}
		}
	}

	@Override
	public void addLoadListener(String dataId, LoadListener l) {
		List<LoadListener> listeners = getListeners(dataId);
		synchronized (listeners) {
			listeners.add(l);
		}
	}

	private List<LoadListener> getListeners(String dataId) {
		if (dataId == null) {
			dataId = "";
		}
		List<LoadListener> listeners = this.listenersMap.get(dataId);
		if (listeners == null) {
			synchronized (listenersMap) {
				listeners = this.listenersMap.get(dataId);
				if (listeners == null) {
					listeners = Collections
							.synchronizedList(new ArrayList<LoadListener>());
					this.listenersMap.put(dataId, listeners);
				}
			}
		}
		return listeners;
	}

	@Override
	public void removeLoadListener(String dataId, LoadListener l) {
		List<LoadListener> listeners = getListeners(dataId);
		synchronized (listeners) {
			listeners.remove(l);
		}
	}

	void removeScheduledLoad(LoadableResource loadableResource) {
		if (timer == null) {
			return;
		}
	}

	void addScheduledLoad(final LoadableResource loadableResource) {
		if (loadableResource.getUpdatePolicy() != UpdatePolicy.SCHEDULED) {
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					try {
						loadableResource.updateRemote();
					}
					catch (Exception e) {
						LOG.error("Failed to update remote resource: "
								+ loadableResource.getResourceId(), e);
					}
				}
			};
			Timer timer = getTimer();
			Properties props = loadableResource.getUpdatePolicyConfig();
			if (props != null) {
				String value = props.getProperty("period");
				long periodMS = periodMS = parseDuration(value);
				value = props.getProperty("delay");
				long delayMS = periodMS = parseDuration(value);
				if (periodMS > 0) {
					timer.scheduleAtFixedRate(task, delayMS, periodMS);
				}
				value = props.getProperty("at");
				if (value != null) {
					Date[] dates = parseDates(value);
					for (Date date : dates) {
						timer.schedule(task, date, 3600000 * 24 /* daily */);
					}
				}
			}
			return;
		}
	}

	private Date[] parseDates(String value) {
		String[] parts = value.split(",");
		List<Date> result = new ArrayList<Date>();
		for (String part : parts) {
			if (part.isEmpty()) {
				continue;
			}
			String[] subparts = part.split(":");
			GregorianCalendar cal = new GregorianCalendar();
			for (int i = 0; i < subparts.length; i++) {
				switch (i) {
				case 0:
					cal.set(GregorianCalendar.HOUR_OF_DAY,
							Integer.parseInt(subparts[i]));
					break;
				case 1:
					cal.set(GregorianCalendar.MINUTE,
							Integer.parseInt(subparts[i]));
					break;
				case 2:
					cal.set(GregorianCalendar.SECOND,
							Integer.parseInt(subparts[i]));
					break;
				case 3:
					cal.set(GregorianCalendar.MILLISECOND,
							Integer.parseInt(subparts[i]));
					break;
				}
			}
			result.add(cal.getTime());
		}
		return result.toArray(new Date[result.size()]);
	}

	protected long parseDuration(String value) {
		long periodMS = 0L;
		if (value != null) {
			String[] parts = value.split(":");
			for (int i = 0; i < parts.length; i++) {
				switch (i) {
				case 0: // hours
					periodMS += (Integer.parseInt(parts[i])) * 3600000L;
					break;
				case 1: // minutes
					periodMS += (Integer.parseInt(parts[i])) * 60000L;
					break;
				case 2: // seconds
					periodMS += (Integer.parseInt(parts[i])) * 1000L;
					break;
				case 3: // ms
					periodMS += (Integer.parseInt(parts[i]));
					break;
				default:
					break;
				}
			}
		}
		return periodMS;
	}
}
