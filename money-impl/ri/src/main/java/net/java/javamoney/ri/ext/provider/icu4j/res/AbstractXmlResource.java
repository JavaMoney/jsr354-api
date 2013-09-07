/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation. Werner Keil -
 * extension and adjustment.
 */
package net.java.javamoney.ri.ext.provider.icu4j.res;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.money.util.DataLoader.LoadListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.java.javamoney.ri.loader.ResourceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class AbstractXmlResource {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	// private LoadableResource resource; //
	// http://unicode.org/repos/cldr/trunk/common/supplemental/supplementalData.xml

	private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
			.newInstance();

	private Document document;

	private String resourceId;

	/**
	 * This flags allow to determine along with LoadableResource#getLastLoaded()
	 * if a newer version of the file was loaded.
	 */
	private long lastLoaded;

	public AbstractXmlResource(String resourceId, URL resource,
			String defaultItem) {
		ResourceLoader.getInstance().registerData(resourceId, resource,
				defaultItem);
		this.resourceId = resourceId;
		docBuilderFactory.setIgnoringComments(true);
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		docBuilderFactory.setValidating(false);
		ResourceLoader.getInstance().addLoadListener(resourceId,
				new LoadListener() {
					@Override
					public void dataLoaded(String dataId) {
						loadResource();
					}
				});
		ResourceLoader.getInstance().loadData(resourceId);
	}

	public void loadResource() {
		InputStream is = null;
		try {
			is = ResourceLoader.getInstance().getInputStream(resourceId);
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
		} catch (Exception e) {
			logger.error("Failed to parse resource " + this.resourceId, e);
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

	/**
	 * This method is called whenever the document was reloaded automatically.
	 */
	protected void documentReloaded() {
	}

	public Document getDocument() {
		if (document == null) {
			loadResource();
		}
		if (document == null) {
			throw new IllegalStateException("Resource could not be loaded: "
					+ resourceId);
		}
		return document;
	}
}
