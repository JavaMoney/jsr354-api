package net.java.javamoney.ri.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadableResource {

	private static final Logger LOG = LoggerFactory
			.getLogger(LoadableResource.class);
	
	private String resourceId;
	private Loader resourceLoader;
	private URL defaultResource;
	private URL cachedResource;
	private AtomicInteger loadCount = new AtomicInteger();
	private AtomicInteger accessCount = new AtomicInteger();
	private byte[] data;
	private long lastLoaded;
	private final Object LOCK = new Object();
	private UpdatePolicy updatePolicy = UpdatePolicy.NEVER;

	public LoadableResource(String resourceId, URL defaultItem, Loader loader) {
		this(resourceId, defaultItem, loader, null);
	}

	public LoadableResource(String resourceId, URL defaultItem, Loader loader,
			UpdatePolicy updatePolicy) {
		if (resourceId == null) {
			throw new IllegalArgumentException("resourceId required");
		}
		if (defaultItem == null) {
			throw new IllegalArgumentException("defaultItem required");
		}
		this.resourceId = resourceId;
		this.resourceLoader = loader;
		this.defaultResource = defaultItem;
		if (updatePolicy != null) {
			this.updatePolicy = updatePolicy;
		}
	}

	public static interface Loader {
		public InputStream load(String resourceId) throws IOException;
	}

	public InputStream load() throws IOException {
		synchronized (LOCK) {
			int count = accessCount.incrementAndGet();
			if (count == 1) {
				loadData();
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
	public final void setUpdatePolicy(UpdatePolicy updatePolicy) {
		this.updatePolicy = updatePolicy;
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
	public final Loader getResourceLoader() {
		return resourceLoader;
	}

	/**
	 * @return the defaultResource
	 */
	public final URL getDefaultResource() {
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
		return data;
	}

	/**
	 * @return the lastLoaded
	 */
	public final long getLastLoaded() {
		return lastLoaded;
	}

	private void loadData() throws IOException {
		URL itemToLoad = getLoadableURL();
		if (itemToLoad != null) {
			InputStream is = null;
			try {
				URLConnection conn = itemToLoad.openConnection();
				byte[] data = new byte[conn.getContentLength()];
				is = conn.getInputStream();
				is.read(data);
				this.data = data;
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
		else {
			InputStream is = null;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				is = this.resourceLoader.load(resourceId);
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
			return defaultResource;
		case ON_INITIAL_LOAD:
			if (loadCount.get() == 0 && resourceLoader != null) {
				return null; // trigger initial load
			}
			if (cachedResource != null) {
				return cachedResource;
			}
			return defaultResource;
		case ON_LOAD:
		case SCHEDULED:
		default:
			if (loadCount.get() == 0 && resourceLoader != null) {
				return null; // trigger initial load
			}
			if (cachedResource != null) {
				return cachedResource;
			}
			return defaultResource;
		}
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

}
