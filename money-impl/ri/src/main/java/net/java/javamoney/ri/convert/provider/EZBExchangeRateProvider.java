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
package net.java.javamoney.ri.convert.provider;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;
import javax.money.convert.ConversionProvider;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.java.javamoney.ri.core.provider.IsoCurrencyOnlineProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class implements an {@link ExchangeRateProviderSpi} that loads data from
 * the European Central Bank datafeed (XML). It loads the current exchange
 * rates, as well as historic rates for the past 90 days. By calling
 * {@link #loadHistoric()} the provider loads all data up to 1999 into its
 * historic data cache.
 * 
 * @author Anatole Tresch
 */
@Singleton
public class EZBExchangeRateProvider implements ConversionProvider {
	/** URL for the last 90 days data feed. */
	private static final String DAILY90_RATES_URL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
	/** URL for the daily data feed. */
	private static final String DAILY_RATES_URL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
	/** URL for the historic data feed. */
	private static final String HISTORIC_RATES_URL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist.xml";
	/** Source currency of the loaded rates is always EUR. */
	private static final CurrencyUnit SOURCE_CURRENCY = MoneyCurrency.of("EUR");
	/** The logger used. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IsoCurrencyOnlineProvider.class);

	/** Historic exchange rates, rate timestamp as UTC long. */
	private Map<Long, Map<String, ExchangeRate>> historicRates = new ConcurrentHashMap<Long, Map<String, ExchangeRate>>();
	/** Current exchange rates. */
	private Map<String, ExchangeRate> currentRates = new ConcurrentHashMap<String, ExchangeRate>();
	/** Parser factory. */
	private SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	/** The {@link ExchangeRateType} of this provider. */
	private static final ExchangeRateType RATE_TYPE = ExchangeRateType
			.of("EZB");
	
	private CurrencyConverter currencyConverter = new DefaultCurrencyConverter(this);
 
	/**
	 * Constructor, also loads initial data.
	 */
	public EZBExchangeRateProvider() {
		saxParserFactory.setNamespaceAware(false);
		saxParserFactory.setValidating(false);
		loadCurrent();
		loadRates90();
	}

	/**
	 * (Re)load the current daily data feed.
	 */
	public void loadCurrent() {
		int oldSize = this.currentRates.size();
		try {
			URL url = new URL(DAILY_RATES_URL);
			SAXParser parser = saxParserFactory.newSAXParser();
			parser.parse(url.openStream(), new RateReadingHandler(true));
		} catch (Exception e) {
			LOGGER.debug("Error", e);
		}
		LOGGER.info("Loaded exchange rates for days:"
				+ (this.currentRates.size() - oldSize));
	}

	/**
	 * (Re)load the full historic daily data feed up to 1999.
	 */
	public void loadHistoric() {
		int oldSize = this.historicRates.size();
		try {
			URL url = new URL(HISTORIC_RATES_URL);
			SAXParser parser = saxParserFactory.newSAXParser();
			parser.parse(url.openStream(), new RateReadingHandler(false));
		} catch (Exception e) {
			LOGGER.debug("Error", e);
		}
		LOGGER.info("Loaded exchange rates for days:"
				+ (this.historicRates.size() - oldSize));
	}

	/**
	 * (Re)load the historic data feed for the last 90 days.
	 */
	public void loadRates90() {
		int oldSize = this.historicRates.size();
		try {
			URL url = new URL(DAILY90_RATES_URL);
			SAXParser parser = saxParserFactory.newSAXParser();
			parser.parse(url.openStream(), new RateReadingHandler(false));
		} catch (Exception e) {
			LOGGER.debug("Error", e);
		}
		LOGGER.info("Loaded exchange rates for days:"
				+ (this.historicRates.size() - oldSize));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.convert.spi.ExchangeRateProviderSpi#getExchangeRateType
	 * ()
	 */
	@Override
	public ExchangeRateType getExchangeRateType() {
		return RATE_TYPE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.money.convert.spi.ExchangeRateProviderSpi#getExchangeRate
	 * (javax .money.CurrencyUnit, javax.money.CurrencyUnit, java.lang.Long)
	 */
	@Override
	public ExchangeRate getExchangeRate(CurrencyUnit base,
			CurrencyUnit term, Long timestamp) {
		if (!MoneyCurrency.ISO_NAMESPACE.equals(base.getNamespace())
				|| !MoneyCurrency.ISO_NAMESPACE.equals(term.getNamespace())) {
			return null;
		}
		ExchangeRate.Builder builder = new ExchangeRate.Builder();
		builder.setProvider("European Central Bank");
		builder.setExchangeRateType(RATE_TYPE);
		builder.setBase(base);
		builder.setTerm(term);
		ExchangeRate sourceRate = null;
		ExchangeRate targetRate = null;
		if (timestamp == null) {
			if (currentRates.isEmpty()) {
				return null;
			}
			sourceRate = currentRates.get(base.getCurrencyCode());
			targetRate = currentRates.get(term.getCurrencyCode());
		} else {
			if (historicRates.isEmpty()) {
				return null;
			}
			GregorianCalendar cal = new GregorianCalendar(
					TimeZone.getTimeZone("UTC"));
			if (timestamp != null) {
				cal.setTimeInMillis(timestamp);
			}
			cal.set(GregorianCalendar.HOUR, 0);
			cal.set(GregorianCalendar.MINUTE, 0);
			cal.set(GregorianCalendar.SECOND, 0);
			cal.set(GregorianCalendar.MILLISECOND, 0);
			Long targetTS = Long.valueOf(cal.getTimeInMillis());
			builder.setValidFrom(targetTS);
			Map<String, ExchangeRate> targetRates = this.historicRates
					.get(targetTS);
			if (targetRates == null) {
				return null;
			}
			sourceRate = targetRates.get(base.getCurrencyCode());
			targetRate = targetRates.get(term.getCurrencyCode());
		}
		if ("EUR".equals(base.getCurrencyCode())
				&& "EUR".equals(term.getCurrencyCode())) {
			builder.setFactor(BigDecimal.ONE);
			return builder.build();
		} else if ("EUR".equals(term.getCurrencyCode())) {
			if (sourceRate == null) {
				return null;
			}
			return reverse(sourceRate);
		} else if ("EUR".equals(base.getCurrencyCode())) {
			return targetRate;
		} else {
			// Get Conversion base as derived rate: base -> EUR -> term
			ExchangeRate rate1 = getExchangeRate(base, MoneyCurrency.of("EUR"),
					timestamp);
			ExchangeRate rate2 = getExchangeRate(MoneyCurrency.of("EUR"), term,
					timestamp);
			if(rate1!=null || rate2!=null){
				builder.setFactor(rate1.getFactor().multiply(rate2.getFactor()));
				builder.setExchangeRateChain(rate1, rate2);
				return builder.build();
			}
			return null;
			// sourceRate = reverse(sourceRate);
			// builder.setExchangeRateChain(sourceRate, targetRate);
			// builder.setSourceLeadingFactor(sourceRate.getFactor().doubleValue()
			// * targetRate.getFactor().doubleValue());
			// return builder.build();
		}
	}

	private static ExchangeRate reverse(ExchangeRate rate) {
		if (rate == null) {
			throw new IllegalArgumentException("Rate null is not reversable.");
		}
		return new ExchangeRate(rate.getExchangeRateType(), rate.getTerm(),
				rate.getBase(), BigDecimal.ONE.divide(rate
						.getFactor(), MathContext.DECIMAL64), rate.getProvider(), rate.getValidFrom(),
				rate.getValidUntil());
	}

	/**
	 * SAX Event Handler that reads the quotes.
	 * <p>
	 * Format: <gesmes:Envelope
	 * xmlns:gesmes="http://www.gesmes.org/xml/2002-08-01"
	 * xmlns="http://www.ecb.int/vocabulary/2002-08-01/eurofxref">
	 * <gesmes:subject>Reference rates</gesmes:subject> <gesmes:Sender>
	 * <gesmes:name>European Central Bank</gesmes:name> </gesmes:Sender> <Cube>
	 * <Cube time="2013-02-21">...</Cube> <Cube time="2013-02-20">...</Cube>
	 * <Cube time="2013-02-19"> <Cube currency="USD" rate="1.3349"/> <Cube
	 * currency="JPY" rate="124.81"/> <Cube currency="BGN" rate="1.9558"/> <Cube
	 * currency="CZK" rate="25.434"/> <Cube currency="DKK" rate="7.4599"/> <Cube
	 * currency="GBP" rate="0.8631"/> <Cube currency="HUF" rate="290.79"/> <Cube
	 * currency="LTL" rate="3.4528"/> ...
	 * 
	 * @author Anatole Tresch
	 */
	private class RateReadingHandler extends DefaultHandler {

		/** Date parser. */
		private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		/** Current timestamp for the given section. */
		private Long timestamp;
		/** Flag, if current or historic data is loaded. */
		private boolean loadCurrent;

		/**
		 * Creates a new parser.
		 * 
		 * @param loadCurrent
		 *            Flag, if current or historic data is loaded.
		 */
		public RateReadingHandler(boolean loadCurrent) {
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			this.loadCurrent = loadCurrent;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
		 * java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			try {
				if ("Cube".equals(qName)) {
					if (attributes.getValue("time") != null) {
						Date date = dateFormat.parse(attributes
								.getValue("time"));
						timestamp = Long.valueOf(date.getTime());
					} else if (attributes.getValue("currency") != null) {
						// read data <Cube currency="USD" rate="1.3349"/>
						CurrencyUnit tgtCurrency = MoneyCurrency.of(attributes
								.getValue("currency"));
						addRate(tgtCurrency, timestamp,
								BigDecimal.valueOf(Double
										.parseDouble(attributes
												.getValue("rate"))),
								loadCurrent);
					}
				}
				super.startElement(uri, localName, qName, attributes);
			} catch (ParseException e) {
				throw new SAXException("Failed to read.", e);
			}
		}

	}

	/**
	 * Method to add a currency exchange rate.
	 * 
	 * @param tgtCurrency
	 *            the target currency, mapped from EUR.
	 * @param timestamp
	 *            The target day.
	 * @param rate
	 *            The rate.
	 * @param loadCurrent
	 *            Flag, if current or historic data is loaded.
	 */
	void addRate(CurrencyUnit tgtCurrency, Long timestamp, BigDecimal rate,
			boolean loadCurrent) {
		ExchangeRate.Builder builder = new ExchangeRate.Builder();
		builder.setBase(SOURCE_CURRENCY);
		builder.setTerm(tgtCurrency);
		builder.setValidFrom(timestamp);
		builder.setProvider("European Central Bank");
		builder.setFactor(rate);
		builder.setExchangeRateType(RATE_TYPE);
		ExchangeRate exchangeRate = builder.build();
		if (loadCurrent) {
			this.currentRates.put(tgtCurrency.getCurrencyCode(), exchangeRate);
		} else {
			Map<String, ExchangeRate> rateMap = this.historicRates
					.get(timestamp);
			if (rateMap == null) {
				synchronized (this.historicRates) {
					rateMap = this.historicRates.get(timestamp);
					if (rateMap == null) {
						rateMap = new ConcurrentHashMap<String, ExchangeRate>();
						this.historicRates.put(timestamp, rateMap);
					}
				}
			}
			rateMap.put(tgtCurrency.getCurrencyCode(), exchangeRate);
		}
	}

	@Override
	public boolean isAvailable(CurrencyUnit src, CurrencyUnit target) {
		return isAvailable(src, target, null);
	}

	@Override
	public boolean isAvailable(CurrencyUnit src, CurrencyUnit target,
			Long timestamp) {
		return getExchangeRate(src, target, timestamp) != null;
	}

	@Override
	public ExchangeRate getExchangeRate(CurrencyUnit source, CurrencyUnit target) {
		return getExchangeRate(source, target, null);
	}

	@Override
	public ExchangeRate getReversed(ExchangeRate rate) {
		return getExchangeRate(rate.getTerm(), rate.getBase(), rate.getValidFrom());
	}

	@Override
	public CurrencyConverter getConverter() {
		return currencyConverter;
	}

}