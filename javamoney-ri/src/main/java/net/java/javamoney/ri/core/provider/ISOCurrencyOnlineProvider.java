/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation.
 */
package net.java.javamoney.ri.core.provider;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.CurrencyUnit;
import javax.money.LocalizableCurrencyUnit;
import javax.money.spi.CurrencyUnitProviderSpi;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ISOCurrencyOnlineProvider implements CurrencyUnitProviderSpi {
	private static final Logger logger = LoggerFactory.getLogger(ISOCurrencyOnlineProvider.class);
	
	private SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

	private Map<String, String> countryCodeMap = new ConcurrentHashMap<String, String>();

	private Map<String, ISOCurrency> currencies = new ConcurrentHashMap<String, ISOCurrency>();

	public ISOCurrencyOnlineProvider() {
		saxParserFactory.setNamespaceAware(false);
		saxParserFactory.setValidating(false);
		loadCountries();
		loadCurrencies();
		logger.debug("Currencies loaded from ISO:" + this.currencies.values());
	}

	public void loadCurrencies() {
		try {
			URL url = new URL(
					"http://www.currency-iso.org/dam/isocy/downloads/dl_iso_table_a1.xml");
			SAXParser parser = saxParserFactory.newSAXParser();
			parser.parse(url.openStream(), new CurrencyHandler());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadCountries() {
		try {
			URL url = new URL(
					"http://www.iso.org/iso/home/standards/country_codes/country_names_and_code_elements_xml.htm");
			SAXParser parser = saxParserFactory.newSAXParser();
			parser.parse(url.openStream(), new CountryHandler());
		} catch (Exception e) {
			logger.error("Error", e);
		}
	}

	private final class ISOCurrency implements LocalizableCurrencyUnit {
		private Locale country;
		private String currencyName;
		private String currencyCode;
		private int numericCode;
		private int minorUnits;

		@Override
		public <T> T getAttribute(String key, Class<T> type) {
			return null;
		}

		@Override
		public Enumeration<String> getAttributeKeys() {
			return Collections.emptyEnumeration();
		}

		@Override
		public Class<?> getAttributeType(String key) {
			return null;
		}

		@Override
		public int compareTo(CurrencyUnit o) {
			// TODO implement correctly here
			return toString().compareTo(o.toString());
		}

		@Override
		public String getNamespace() {
			return CurrencyUnit.ISO_NAMESPACE;
		}

		@Override
		public String getCurrencyCode() {
			return currencyCode;
		}

		@Override
		public int getNumericCode() {
			return numericCode;
		}

		@Override
		public int getDefaultFractionDigits() {
			return minorUnits;
		}

		@Override
		public boolean isVirtual() {
			return false;
		}

		@Override
		public long getValidFrom() {
			return -1L;
		}

		@Override
		public long getValidUntil() {
			return -1L;
		}

		@Override
		public String toString() {
			return getNamespace() + ':' + this.currencyCode;
		}

		@Override
		public String getDisplayName(Locale locale) {
			// TODO use Locale and add getDisplayName(), too
			return currencyName;
		}

		@Override
		public String getSymbol(Locale locale) {
			return currencyCode;
		}
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
				countryCodeMap.put(name, alpha2Code);
			} else if ("ISO_3166-1_Country_name".equals(qName)) {
				name = text.toString();
			} else if ("ISO_3166-1_Alpha-2_Code_element".equals(qName)) {
				alpha2Code = text.toString();
			}
			super.endElement(uri, localName, qName);
		}
	}

	private class CurrencyHandler extends DefaultHandler {

		// <ISO_CCY_CODES>
		// <ISO_CURRENCY>
		// <ENTITY>AFGHANISTAN</ENTITY>
		// <CURRENCY>Afghani</CURRENCY>
		// <ALPHABETIC_CODE>AFN</ALPHABETIC_CODE>
		// <NUMERIC_CODE>971</NUMERIC_CODE>
		// <MINOR_UNIT>2</MINOR_UNIT>
		// </ISO_CURRENCY>
		// ...
		// </ISO_CCY_CODES>

		private ISOCurrency currency = null;
		private StringBuilder text = new StringBuilder();

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if ("ISO_CURRENCY".equals(qName)) {
				currency = new ISOCurrency();
			} else if ("ENTITY".equals(qName)) {
				text.setLength(0);
			} else if ("CURRENCY".equals(qName)) {
				text.setLength(0);
			} else if ("ALPHABETIC_CODE".equals(qName)) {
				text.setLength(0);
			} else if ("NUMERIC_CODE".equals(qName)) {
				text.setLength(0);
			} else if ("MINOR_UNIT".equals(qName)) {
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
			if ("ISO_CURRENCY".equals(qName)) {
				currencies.put(currency.currencyCode, currency);
			} else if ("ENTITY".equals(qName)) {
				String countryName = text.toString();
				String code = countryCodeMap.get(countryName);
				if (code != null) {
					currency.country = new Locale("", code);
				} else {
					currency.country = Locale.ROOT;
				}
			} else if ("CURRENCY".equals(qName)) {
				currency.currencyName = text.toString();
			} else if ("ALPHABETIC_CODE".equals(qName)) {
				currency.currencyCode = text.toString();
			} else if ("NUMERIC_CODE".equals(qName)) {
				String value = text.toString();
				if (!value.isEmpty()) {
					try {
						currency.numericCode = Integer.valueOf(value);
					} catch (NumberFormatException nfe) {
						currency.numericCode = -1;
					}
				} else {
					currency.numericCode = -1;
				}
			} else if ("MINOR_UNIT".equals(qName)) {
				String value = text.toString();
				if (!value.isEmpty()) {
					try {
						currency.minorUnits = Integer.valueOf(value);
					} catch (NumberFormatException nfe) {
						currency.minorUnits = -1;
					}
				} else {
					currency.minorUnits = -1;
				}
			}
			super.endElement(uri, localName, qName);
		}
	}

	@Override
	public String getNamespace() {
		return "ISO-4217";
	}

	@Override
	public CurrencyUnit getCurrency(String code, long timestamp) {
		if (timestamp < 0) {
			return this.currencies.get(code);
		}
		return null;
	}

	@Override
	public CurrencyUnit[] getCurrencies(Locale locale, long timestamp) {
		if (locale!=null && timestamp < 0) {
			List<CurrencyUnit> result = new ArrayList<CurrencyUnit>();
			for (ISOCurrency currency : currencies.values()) {
				if (locale.equals(currency.country)) {
					result.add(currency);
				}
			}
			return result.toArray(new CurrencyUnit[result.size()]);
		}
		return null;
	}

	@Override
	public CurrencyUnit[] getCurrencies(long timestamp) {
		if (timestamp < 0) {
			return currencies.values().toArray(
					new CurrencyUnit[currencies.size()]);
		}
		return null;
	}

	@Override
	public boolean isAvailable(String code, long start, long end) {
		if (start < 0 && end < 0) {
			return this.currencies.containsKey(code);
		}
		return false;
	}

	public static void main(String[] args) {
		new ISOCurrencyOnlineProvider();
	}
}
