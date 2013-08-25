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
package net.java.javamoney.ri.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.java.javamoney.ri.loader.LoadableResource.Loader;

/**
 * Implementation class of {@link Loader} based on a {@link URL}.
 * 
 * @author Anatole Tresch
 */
public class URLLoader implements Loader {
	/** The URL, not {@code null}. */
	private URL url;

	/**
	 * Constructor.
	 * 
	 * @param url
	 *            the URL, not {@code null}
	 */
	public URLLoader(URL url) {
		if (url == null) {
			throw new IllegalArgumentException("url required");
		}
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.java.javamoney.ri.loader.LoadableResource.Loader#load(java.lang.String
	 * )
	 */
	@Override
	public InputStream load(String resourceId) throws IOException {
		return url.openStream();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "URLLoader [url=" + url + "]";
	}

}
