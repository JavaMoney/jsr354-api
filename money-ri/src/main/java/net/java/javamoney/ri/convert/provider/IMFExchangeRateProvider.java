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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;

import net.java.javamoney.ri.convert.CurrencyExchangeRateType;
import net.java.javamoney.ri.convert.spi.ExchangeRateProviderSpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IMFExchangeRateProvider implements ExchangeRateProviderSpi {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IMFExchangeRateProvider.class);

	private Map<CurrencyUnit, ExchangeRate> currencyToSDRMap = new HashMap<CurrencyUnit, ExchangeRate>();

	private Map<CurrencyUnit, ExchangeRate> sDRToCurrency = new HashMap<CurrencyUnit, ExchangeRate>();

	public void loadRates() {
		InputStream is = null;
		try {
			URL url = new URL(
					"http://www.imf.org/external/np/fin/data/rms_five.aspx?tsvflag=Y");
			is = url.openStream();
			loadRates(is);
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

	private void loadRates(InputStream inputStream) throws IOException {
		CurrencyUnit sdr = getCurrency("SDR");
		NumberFormat f = new DecimalFormat("#0.0000000000");
		f.setGroupingUsed(false);
		BufferedReader pr = new BufferedReader(new InputStreamReader(
				inputStream));
		String line = pr.readLine();
		int sdrToCurrency = 0;
		while (line != null) {
			Long[] timestamps = null;
			if (line.startsWith("SDRs per Currency unit")) {
				sdrToCurrency = 1;
			} else if (line.startsWith("Currency units per SDR")) {
				sdrToCurrency = 2;
			} else if (line.startsWith("Currency ")) {
				timestamps = readTimestamps(line);
			}
			String[] parts = line.split("\\t");
			CurrencyUnit currency = getCurrency(parts[0]);
			Double[] values = parseValues(f, parts, 1);
			// for(int i=0;i<values.length;i++){
			// if(values[i]!=null){
			// if(sdrToCurrency==1){ // Currency -> SDR
			// currencyToSDRMap.put(currency, new ExchangeRateImpl(currency,
			// sdr, values[i]));
			// }
			// else if(sdrToCurrency==2){ // SDR -> Currency
			// sDRToCurrency.put(currency, new ExchangeRateImpl(sdr, currency,
			// values[i]));
			// }
			// }
			// }
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

			line = pr.readLine();
		}

	}

	private Double[] parseValues(NumberFormat f, String[] parts, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	private Long[] readTimestamps(String line) {
		// TODO Auto-generated method stub
		return null;
	}

	private CurrencyUnit getCurrency(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExchangeRate getExchangeRate(CurrencyUnit source,
			CurrencyUnit target, Long timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExchangeRateType getExchangeRateType() {
		return CurrencyExchangeRateType.of("public");
	}

	public static void main(String[] args) {
		new IMFExchangeRateProvider().loadRates();
	}
}
