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
package net.java.javamoney.ri.ext.provider.iso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.Displayable;
import javax.money.MoneyCurrency;
import javax.money.ext.spi.CurrencyUnitProviderSpi;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Singleton
public class IsoCurrencyOnlineProvider implements CurrencyUnitProviderSpi {

    private static final Logger LOGGER = LoggerFactory.getLogger(IsoCurrencyOnlineProvider.class);

    private SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

    private Map<String, String> countryCodeMap = new ConcurrentHashMap<String, String>();

    private Map<String, CurrencyUnit> currencies = new ConcurrentHashMap<String, CurrencyUnit>();

    public IsoCurrencyOnlineProvider() {
	saxParserFactory.setNamespaceAware(false);
	saxParserFactory.setValidating(false);
	new CurrencyLoader().start();
    }

    public void loadCurrencies() {
	try {
	    URL url = new URL("http://www.currency-iso.org/dam/isocy/downloads/dl_iso_table_a1.xml");
	    SAXParser parser = saxParserFactory.newSAXParser();
	    parser.parse(url.openStream(), new CurrencyHandler());
	} catch (Exception e) {
	    LOGGER.debug("Error", e);
	}
    }

    public void loadCountries() {
	try {
	    URL url = new URL(
		    "http://www.iso.org/iso/home/standards/country_codes/country_names_and_code_elements_xml.htm");
	    SAXParser parser = saxParserFactory.newSAXParser();
	    parser.parse(url.openStream(), new CountryHandler());
	} catch (Exception e) {
	    LOGGER.error("Error", e);
	}
    }

    private final class ISOCurrency implements CurrencyUnit, Displayable {
	private Locale country;
	private String currencyName;
	private String currencyCode;
	private int numericCode;
	private int minorUnits;

	@Override
	public String getNamespace() {
	    return MoneyCurrency.ISO_NAMESPACE;
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
	public String toString() {
	    return getNamespace() + ':' + this.currencyCode;
	}

	public String getDisplayName(Locale locale) {
	    // TODO use Locale and add getDisplayName(), too
	    return currencyName;
	}

	public String getSymbol(Locale locale) {
	    return currencyCode;
	}

	public String getSymbol() {
	    return currencyCode;
	}

	public String getDisplayName() {
	    return currencyName;
	}

	@Override
	public boolean isLegalTender() {
	    if (getCurrencyCode().startsWith("X")) {
		return false;
	    }
	    return true;
	}

	@Override
	public int getCashRounding() {
	    return -1;
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
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
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
	public void characters(char[] ch, int start, int length) throws SAXException {
	    text.append(ch, start, length);
	    super.characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
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
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
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
	public void characters(char[] ch, int start, int length) throws SAXException {
	    text.append(ch, start, length);
	    super.characters(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
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
	return MoneyCurrency.ISO_NAMESPACE;
    }

    @Override
    public CurrencyUnit get(String code) {
	return this.currencies.get(code);
    }

//    @Override
//    public Collection<CurrencyUnit> getCurrencies(Locale locale) {
//	if (locale != null) {
//	    List<CurrencyUnit> result = new ArrayList<CurrencyUnit>();
//	    for (CurrencyUnit currency : currencies.values()) {
//		if (locale.equals(((ISOCurrency) currency).country)) {
//		    result.add(currency);
//		}
//	    }
//	    return result;
//	}
//	return null;
//    }

    @Override
    public Collection<CurrencyUnit> getAll() {
	return Collections.unmodifiableCollection(this.currencies.values());
    }

    @Override
    public boolean isAvailable(String code) {
	return this.currencies.containsKey(code);
    }

    private final class CurrencyLoader extends Thread {

	public CurrencyLoader() {
	    super("ISO Currency Online Loader");
	}

	public void run() {
	    loadCountries();
	    loadCurrencies();
	    LOGGER.debug("Currencies loaded from ISO:" + IsoCurrencyOnlineProvider.this.currencies.values());
	}

    }
}
