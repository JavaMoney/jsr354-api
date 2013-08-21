package net.java.javamoney.ri.ext.provider.icu4j.res;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.java.javamoney.ri.loader.LoadableResource;
import net.java.javamoney.ri.loader.ResourceLoader;
import net.java.javamoney.ri.loader.URLLoader;
import net.java.javamoney.ri.loader.UpdatePolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class AbstractXmlResource {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private LoadableResource resource; // http://unicode.org/repos/cldr/trunk/common/supplemental/supplementalData.xml

	private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
			.newInstance();

	private Document document;

	/**
	 * This flags allow to determine along with LoadableResource#getLastLoaded()
	 * if a newer version of the file was loaded.
	 */
	private long lastLoaded;

	public AbstractXmlResource(String resourceId, URL resource, URL defaultItem) {
		this(resourceId, resource, defaultItem, null);
	}

	public AbstractXmlResource(String resourceId, URL resource,
			URL defaultItem,
			UpdatePolicy updatePolicy) {
		if (resource == null) {
			throw new IllegalArgumentException("Resource URL required.");
		}
		this.resource = ResourceLoader.getInstance().register(resourceId,
				new URLLoader(resource), defaultItem, updatePolicy);
		docBuilderFactory.setIgnoringComments(true);
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		docBuilderFactory.setValidating(false);
		loadResource();
	}

	public boolean isReloadRequired() {
		return this.lastLoaded < this.resource.getLastLoaded();
	}

	public void loadResource() {
		loadResource(false);
	}

	public void loadResource(boolean forceReload) {
		InputStream is = null;
		try {
			if (forceReload || this.document == null) {
				is = this.resource.load();
				InputSource inputSource = new InputSource(is);
				DocumentBuilder builder = docBuilderFactory
						.newDocumentBuilder();
				builder.setEntityResolver(new EntityResolver() {
					public InputSource resolveEntity(java.lang.String publicId,
							java.lang.String systemId)
							throws SAXException, java.io.IOException
					{
						if (systemId.contains("ldmlSupplemental.dtd"))
							// this deactivates the open office DTD
							return new InputSource(new ByteArrayInputStream(
									"<?xml version='1.0' encoding='UTF-8'?>"
											.getBytes()));
						else
							return null;
					}
				});
				document = builder.
						parse(inputSource);
				document.normalize();
				documentReloaded();
			}
		} catch (Exception e) {
			logger.error("Failed to parse resource " + this.resource, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					logger.error("Error closing input stream from "
							+ this.resource, e);
				}
			}
		}
	}

	/**
	 * This method is called whenever the document was reloaded automatically.
	 */
	protected void documentReloaded() {
	}

	public Document getDocument() {
		if (document == null || isReloadRequired()) {
			loadResource(true);
		}
		if (document == null) {
			throw new IllegalStateException("Resource could not be loaded: "
					+ resource);
		}
		return document;
	}
}
