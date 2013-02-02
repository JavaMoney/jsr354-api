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
package net.java.javamoney.ri.convert.providers;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;
import javax.money.convert.spi.ExchangeRateProviderSPI;

public class IMFExchangeRateProvider implements ExchangeRateProviderSPI {

	 // TSV: http://www.imf.org/external/np/fin/data/rms_five.aspx?tsvflag=Y
	
	
	@Override
	public ExchangeRate getExchangeRate(CurrencyUnit source,
			CurrencyUnit target, ExchangeRateType type, boolean deferred) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExchangeRate getExchangeRate(CurrencyUnit source,
			CurrencyUnit target, ExchangeRateType type, long timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
