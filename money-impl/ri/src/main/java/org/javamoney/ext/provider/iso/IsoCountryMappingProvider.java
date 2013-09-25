/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation.
 */
package org.javamoney.ext.provider.iso;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Mapping provider that is able to map ISO textual and ISO nuemeric codes and
 * vice versa.
 * 
 * @author Anatole Tresch
 */
public final class IsoCountryMappingProvider {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IsoCountryMappingProvider.class);

	private static final IsoCountryMappingProvider INSTANCE = new IsoCountryMappingProvider();

	private SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

	private Map<String, String> nameToCodeMap = new HashMap<String, String>();
	private Map<String, String> codeToNameMap = new HashMap<String, String>();

	private IsoCountryMappingProvider() {
		saxParserFactory.setNamespaceAware(false);
		saxParserFactory.setValidating(false);
		new Thread() {
			public void run() {
				loadCountries();
			};
		}.start();
	}

	public static IsoCountryMappingProvider getInstance() {
		return INSTANCE;
	}

	@PostConstruct
	public void loadCountries() {
		Map<String, String> newNameToCodeMap = new HashMap<String, String>();
		Map<String, String> newCodeToNameMap = new HashMap<String, String>();
		try {
			URL url = new URL(
					"http://www.iso.org/iso/home/standards/country_codes/country_names_and_code_elements_xml.htm");
			SAXParser parser = saxParserFactory.newSAXParser();
			parser.parse(url.openStream(), new CountryHandler(newNameToCodeMap,
					newCodeToNameMap));
			nameToCodeMap = newNameToCodeMap;
			codeToNameMap = newCodeToNameMap;
		} catch (Exception e) {
			LOGGER.error("Error", e);
		}
	}

	public Set<String> getIsoAlpha2Codes() {
		return this.codeToNameMap.keySet();
	}

	public Set<String> getIsoNames() {
		return this.nameToCodeMap.keySet();
	}

	public String getNameFromCode(String code) {
		return this.codeToNameMap.get(code);
	}

	public String getCodeFromName(String name) {
		return this.nameToCodeMap.get(name);
	}

	private class CountryHandler extends DefaultHandler {

		// <ISO_3166-1_List_en xml:lang="en">
		// <ISO_3166-1_Entry>
		// <ISO_3166-1_Country_name>AFGHANISTAN</ISO_3166-1_Country_name>
		// <ISO_3166-1_Alpha-2_Code_element>AF</ISO_3166-1_Alpha-2_Code_element>
		// </ISO_3166-1_Entry>
		// ...
		// </ISO_3166-1_List_en xml:lang="en">

		private String name;
		private String alpha2Code;
		private StringBuilder text = new StringBuilder();

		private Map<String, String> nameToCodeMap;
		private Map<String, String> codeToNameMap;

		public CountryHandler(Map<String, String> nameToCodeMap,
				Map<String, String> codeToNameMap) {
			this.nameToCodeMap = nameToCodeMap;
			this.codeToNameMap = codeToNameMap;
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if ("ISO_3166-1_Entry".equals(qName)) {
				name = null;
				alpha2Code = null;
			} else if ("ISO_3166-1_Country_name".equals(qName)) {
				text.setLength(0);
			} else if ("ISO_3166-1_Alpha-2_Code_element".equals(qName)) {
				text.setLength(0);
			}
			super.startElement(uri, localName, qName, attributes);
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			text.append(ch, start, length);
			super.characters(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if ("ISO_3166-1_Entry".equals(qName)) {
				nameToCodeMap.put(name, alpha2Code);
				codeToNameMap.put(alpha2Code, name);
			} else if ("ISO_3166-1_Country_name".equals(qName)) {
				name = text.toString();
			} else if ("ISO_3166-1_Alpha-2_Code_element".equals(qName)) {
				alpha2Code = text.toString();
			}
			super.endElement(uri, localName, qName);
		}

	}

}
