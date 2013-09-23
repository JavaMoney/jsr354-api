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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link ResourceCache}, using the local file system.
 * 
 * @author Anatole Tresch
 */
public class DefaultResourceCache implements ResourceCache {

	private static final Logger LOG = LoggerFactory
			.getLogger(DefaultResourceCache.class);

	private static final String SUFFIX = ".dat";

	private File localDir = new File(
			System.getProperty("temp.dir", ".resourceCache"));

	private Map<String, File> cachedResources = new ConcurrentHashMap<>();

	public DefaultResourceCache() {
		if (!localDir.exists()) {
			if (!localDir.mkdirs()) {
				LOG.error("Error creating cache dir  " + localDir
						+ ", resource cache disabled!");
				localDir = null;
			}
			else {
				LOG.debug("Created cache dir  " + localDir);
			}
		}
		else if (!localDir.isDirectory()) {
			LOG.error("Error initializing cache dir  " + localDir
					+ ", not a directory, resource cache disabled!");
			localDir = null;
		}
		else if (!localDir.canWrite()) {
			LOG.error("Error initializing cache dir  " + localDir
					+ ", not writable, resource cache disabled!");
			localDir = null;
		}
		if (localDir != null) {
			File[] files = localDir.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					String resourceId = file.getName().substring(0,
							file.getName().length() - 4);
					cachedResources.put(resourceId, file);
				}
			}
		}
	}

	@Override
	public void write(String resourceId, byte[] data) throws IOException {
		File f = this.cachedResources.get(resourceId);
		if (f == null) {
			f = new File(localDir, resourceId + SUFFIX);
			writeFile(f, data);
			this.cachedResources.put(resourceId, f);
		}
		else {
			writeFile(f, data);
		}
	}

	private void writeFile(File f, byte[] data) throws IOException {
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(f));
			bos.write(data);
			bos.flush();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e2) {
				LOG.error("Error closing output stream for " + f, e2);
			}
		}

	}

	@Override
	public boolean isCached(String resourceId) {
		return this.cachedResources.containsKey(resourceId);
	}

	@Override
	public byte[] read(String resourceId) {
		File f = this.cachedResources.get(resourceId);
		if (f == null) {
			return null;
		}
		return readFile(f);
	}

	private byte[] readFile(File f) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BufferedInputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(f));
			byte[] input = new byte[1024];
			int read = 1;
			while (read > 0) {
				read = is.read(input);
				bos.write(input, 0, read);
			}
			return bos.toByteArray();
		} catch (Exception e) {
			LOG.error("Error reading cached resource from " + f, e);
			return null;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e2) {
				LOG.error("Error closing input stream from " + f, e2);
			}
		}

	}

}
