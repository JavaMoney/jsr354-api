package net.java.javamoney.ri.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.java.javamoney.ri.loader.LoadableResource.Loader;

public class URLLoader implements Loader {

	private URL url;

	public URLLoader(URL url) {
		if(url==null){
			throw new IllegalArgumentException("url required");
		}
		this.url = url;
	}

	@Override
	public InputStream load(String resourceId) throws IOException {
		return url.openStream();
	}

}
