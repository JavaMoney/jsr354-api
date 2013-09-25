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
package org.javamoney.loader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class AbstractXmlResource extends AbstractResource {

	private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
			.newInstance();

	private Document document;

	public AbstractXmlResource(String resourceId, URL resource,
			String defaultResource) {
		super(resourceId, resource, defaultResource);
		docBuilderFactory.setIgnoringComments(true);
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		docBuilderFactory.setValidating(false);
	}

	protected void loadData(InputStream is) {
		try {
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
			loadDocument(document);
		} catch (Exception e) {
			logger.error("Failed to parse resource " + getId(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					logger.error("Error closing input stream from "
							+ getId(), e);
				}
			}
		}
	}

	protected abstract void loadDocument(Document document);

	public Document getDocument() {
		if (document == null) {
			dataLoaded();
		}
		if (document == null) {
			throw new IllegalStateException("Resource could not be loaded: "
					+ getId());
		}
		return document;
	}
}
