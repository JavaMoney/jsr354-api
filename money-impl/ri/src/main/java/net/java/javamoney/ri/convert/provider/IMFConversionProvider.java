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
 * Contributors: Anatole Tresch - initial implementation.
 */
package net.java.javamoney.ri.convert.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;
import javax.money.convert.ConversionProvider;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements a {@link ConversionProvider} that loads the IMF conversion data.
 * In most cases this provider will provide chaind rates, since IMF always is
 * converting from/to the IMF <i>SDR</i> currency unit.
 * 
 * @author Anatole Tresch
 */
@Singleton
public class IMFConversionProvider implements ConversionProvider {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IMFConversionProvider.class);

	private static final ExchangeRateType RATE_TYPE = ExchangeRateType
			.of("IMF");

	private static final MoneyCurrency SDR = new MoneyCurrency.Builder(
			MoneyCurrency.ISO_NAMESPACE, "SDR").setLegalTender(false)
			.setVirtual(false).setDefaultFractionDigits(3).setNumericCode(-1)
			.build(true);

	private static final String PROVIDER_URL = "http://www.imf.org/external/np/fin/data/rms_five.aspx?tsvflag=Y";

	private Map<CurrencyUnit, List<ExchangeRate>> currencyToSdr = new HashMap<CurrencyUnit, List<ExchangeRate>>();

	private Map<CurrencyUnit, List<ExchangeRate>> sdrToCurrency = new HashMap<CurrencyUnit, List<ExchangeRate>>();

	private static Map<String, CurrencyUnit> currenciesByName = new HashMap<String, CurrencyUnit>();

	static {
		for (Currency currency : Currency.getAvailableCurrencies()) {
			currenciesByName.put(currency.getDisplayName(Locale.ENGLISH),
					MoneyCurrency.of(currency));
		}
		// Additional IMF differing codes:
		currenciesByName.put("U.K. Pound Sterling", MoneyCurrency.of("GBP"));
		currenciesByName.put("U.S. Dollar", MoneyCurrency.of("USD"));
		currenciesByName.put("Bahrain Dinar", MoneyCurrency.of("BHD"));
		currenciesByName.put("Botswana Pula", MoneyCurrency.of("BWP"));
		currenciesByName.put("Czech Koruna", MoneyCurrency.of("CZK"));
		currenciesByName.put("Icelandic Krona", MoneyCurrency.of("ISK"));
		currenciesByName.put("Korean Won", MoneyCurrency.of("KRW"));
		currenciesByName.put("Rial Omani", MoneyCurrency.of("OMR"));
		currenciesByName.put("Nuevo Sol", MoneyCurrency.of("PEN"));
		currenciesByName.put("Qatar Riyal", MoneyCurrency.of("QAR"));
		currenciesByName.put("Saudi Arabian Riyal", MoneyCurrency.of("SAR"));
		currenciesByName.put("Sri Lanka Rupee", MoneyCurrency.of("LKR"));
		currenciesByName.put("Trinidad And Tobago Dollar",
				MoneyCurrency.of("TTD"));
		currenciesByName.put("U.A.E. Dirham", MoneyCurrency.of("AED"));
		currenciesByName.put("Peso Uruguayo", MoneyCurrency.of("UYU"));
		currenciesByName.put("Bolivar Fuerte", MoneyCurrency.of("VEF"));
	}

	private CurrencyConverter currencyConverter = new DefaultCurrencyConverter(
			this);

	public IMFConversionProvider() {
		loadRates();
	}

	public void loadRates() {
		InputStream is = null;
		try {
			URL url = new URL(PROVIDER_URL);
			is = url.openStream();
			loadRatesTSV(is);
		} catch (Exception e) {
			LOGGER.error("Error", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LOGGER.warn("Error closing input stream.", e);
				}
			}
		}
	}

	private void loadRatesTSV(InputStream inputStream) throws IOException,
			ParseException {
		Map<CurrencyUnit, List<ExchangeRate>> newCurrencyToSdr = new HashMap<CurrencyUnit, List<ExchangeRate>>();
		Map<CurrencyUnit, List<ExchangeRate>> newSdrToCurrency = new HashMap<CurrencyUnit, List<ExchangeRate>>();
		NumberFormat f = new DecimalFormat("#0.0000000000");
		f.setGroupingUsed(false);
		BufferedReader pr = new BufferedReader(new InputStreamReader(
				inputStream));
		String line = pr.readLine();
		int lineType = 0;
		boolean currencyToSdr = true;
		// SDRs per Currency unit (2)
		//
		// Currency January 31, 2013 January 30, 2013 January 29, 2013
		// January 28, 2013 January 25, 2013
		// Euro 0.8791080000 0.8789170000 0.8742470000 0.8752180000
		// 0.8768020000

		// Currency units per SDR(3)
		//
		// Currency January 31, 2013 January 30, 2013 January 29, 2013
		// January 28, 2013 January 25, 2013
		// Euro 1.137520 1.137760 1.143840 1.142570 1.140510
		List<Long> timestamps = null;
		while (line != null) {
			if (line.trim().isEmpty()) {
				line = pr.readLine();
				continue;
			}
			if (line.startsWith("SDRs per Currency unit")) {
				currencyToSdr = false;
				line = pr.readLine();
				continue;
			} else if (line.startsWith("Currency units per SDR")) {
				currencyToSdr = true;
				line = pr.readLine();
				continue;
			} else if (line.startsWith("Currency")) {
				timestamps = readTimestamps(line);
				line = pr.readLine();
				continue;
			}
			String[] parts = line.split("\\t");
			CurrencyUnit currency = currenciesByName.get(parts[0]);
			if (currency == null) {
				LOGGER.warn("Unknown currency from, IMF data feed: " + parts[0]);
				line = pr.readLine();
				continue;
			}
			Double[] values = parseValues(f, parts);
			for (int i = 0; i < values.length; i++) {
				if (values[i] == null) {
					continue;
				}
				Long fromTS = timestamps.get(i);
				Long toTS = fromTS + 3600L * 1000L * 24L; // One day
				if (currencyToSdr) { // Currency -> SDR
					List<ExchangeRate> rates = this.currencyToSdr.get(currency);
					if (rates == null) {
						rates = new ArrayList<ExchangeRate>(5);
						newCurrencyToSdr.put(currency, rates);
					}
					ExchangeRate rate = new ExchangeRate(RATE_TYPE, currency,
							SDR, values[i], PROVIDER_URL, fromTS, toTS);
					rates.add(rate);
				} else { // SDR -> Currency
					List<ExchangeRate> rates = this.sdrToCurrency.get(currency);
					if (rates == null) {
						rates = new ArrayList<ExchangeRate>(5);
						newSdrToCurrency.put(currency, rates);
					}
					ExchangeRate rate = new ExchangeRate(RATE_TYPE, SDR,
							currency, values[i], PROVIDER_URL, fromTS, toTS);
					rates.add(rate);
				}
			}
			line = pr.readLine();
		}
		for (List<ExchangeRate> rateList : newSdrToCurrency.values()) {
			Collections.sort(rateList);
		}
		for (List<ExchangeRate> rateList : newCurrencyToSdr.values()) {
			Collections.sort(rateList);
		}
		this.sdrToCurrency = newSdrToCurrency;
		this.currencyToSdr = newCurrencyToSdr;
	}

	private Double[] parseValues(NumberFormat f, String[] parts)
			throws ParseException {
		Double[] result = new Double[parts.length - 1];
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].isEmpty()) {
				continue;
			}
			result[i - 1] = f.parse(parts[i]).doubleValue();
		}
		return result;
	}

	private List<Long> readTimestamps(String line) throws ParseException {
		// Currency May 01, 2013 April 30, 2013 April 29, 2013 April 26, 2013
		// April 25, 2013
		SimpleDateFormat sdf = new SimpleDateFormat("MMM DD, yyyy",
				Locale.ENGLISH);
		String[] parts = line.split("\\\t");
		List<Long> dates = new ArrayList<Long>(parts.length);
		for (int i = 1; i < parts.length; i++) {
			dates.add(sdf.parse(parts[i]).getTime());
		}
		return dates;
	}

	protected ExchangeRate getExchangeRateInternal(CurrencyUnit base,
			CurrencyUnit term,
			Long timestamp) {
		ExchangeRate rate1 = lookupRate(currencyToSdr.get(base), timestamp);
		ExchangeRate rate2 = lookupRate(sdrToCurrency.get(term), timestamp);
		if (base.equals(SDR)) {
			return rate2;
		} else if (term.equals(SDR)) {
			return rate1;
		}
		if (rate1 == null || rate2 == null) {
			return null;
		}
		ExchangeRate.Builder builder = new ExchangeRate.Builder();
		builder.setProvider(PROVIDER_URL);
		builder.setExchangeRateType(RATE_TYPE);
		builder.setBase(base);
		builder.setTerm(term);
		builder.setFactor(rate1.getFactor().multiply(rate2.getFactor()));
		builder.setExchangeRateChain(rate1, rate2);
		builder.setValidFrom(Math.max(rate1.getValidFromTimeInMillis(),
				rate2.getValidFromTimeInMillis()));
		builder.setValidTo(Math.min(rate1.getValidToTimeInMillis(),
				rate2.getValidToTimeInMillis()));
		return builder.build();
	}

	private ExchangeRate lookupRate(List<ExchangeRate> list, Long timestamp) {
		if (list == null) {
			return null;
		}
		ExchangeRate found = null;
		for (ExchangeRate rate : list) {
			if (rate.isValid(timestamp)) {
				return rate;
			}
			if (found == null) {
				found = rate;
			}
		}
		return found;
	}

	@Override
	public ExchangeRateType getExchangeRateType() {
		return ExchangeRateType.of("IMF");
	}

	@Override
	public boolean isAvailable(CurrencyUnit src, CurrencyUnit target) {
		return getExchangeRateInternal(src, target, null) != null;
	}

	@Override
	public boolean isAvailable(CurrencyUnit src, CurrencyUnit target,
			long timestamp) {
		return getExchangeRate(src, target, timestamp) != null;
	}

	@Override
	public ExchangeRate getExchangeRate(CurrencyUnit source, CurrencyUnit target) {
		return getExchangeRateInternal(source, target, null);
	}

	@Override
	public ExchangeRate getReversed(ExchangeRate rate) {
		return getExchangeRate(rate.getTerm(), rate.getBase(),
				rate.getValidFromTimeInMillis());
	}

	@Override
	public CurrencyConverter getConverter() {
		return currencyConverter;
	}

	@Override
	public ExchangeRate getExchangeRate(CurrencyUnit base,
			CurrencyUnit term, long timestamp) {
		return getExchangeRateInternal(base, term, timestamp);
	}

}
