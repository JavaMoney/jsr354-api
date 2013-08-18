package net.java.javamoney.ri.loader;

import java.io.IOException;
import java.net.URL;

public interface ResourceCache {

	public void write(String resourceId, byte[] data) throws IOException;

	public boolean isCached(String resourceId);
	
	public byte[] read(String resourceId);

}
