package net.java.javamoney.ri.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import net.java.javamoney.ri.loader.LoadableResource.Loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ResourceLoader {

	private static final Logger LOG = LoggerFactory
			.getLogger(ResourceLoader.class);

	private Map<String, LoadableResource> resources = new ConcurrentHashMap<>();

	private ResourceCache resourceCache = loadResourceCache();

	private final static ResourceLoader INSTANCE = new ResourceLoader();

	private ResourceLoader() {
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
		if(this.resourceCache==null){
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

	public LoadableResource register(String resourceId, Loader loader,
			URL defaultItem,
			UpdatePolicy updatePolicy) {
		if (resources.containsKey(resourceId)) {
			throw new IllegalArgumentException("Resource : " + resourceId
					+ " already registered.");
		}
		LoadableResource res = new LoadableResource(resourceId, defaultItem,
				loader, updatePolicy);
		this.resources.put(resourceId, res);
		return res;
	}

	public void unload(String resourceId) {
		LoadableResource res = this.resources.get(resourceId);
		if (res != null) {
			res.unload();
		}
	}

	public InputStream getStream(String resourceId) throws IOException {
		LoadableResource res = this.resources.get(resourceId);
		if (res != null) {
			return res.load();
		}
		throw new IllegalArgumentException("No such respirce: " + resourceId);
	}

}
