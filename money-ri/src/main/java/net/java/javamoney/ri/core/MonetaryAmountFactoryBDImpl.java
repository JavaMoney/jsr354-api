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
package net.java.javamoney.ri.core;

import java.math.BigDecimal;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Money;
import javax.money.provider.MonetaryAmountFactory;

import net.java.javamoney.ri.qualifiers.Amount;

@Amount
public class MonetaryAmountFactoryBDImpl implements MonetaryAmountFactory {

	public MonetaryAmount get(CurrencyUnit currency, Number number) {
		return new Money(currency, number);
	}

	public MonetaryAmount get(CurrencyUnit currency, byte value) {
		return new Money(currency, new Byte(value));
	}

	public MonetaryAmount get(CurrencyUnit currency, short value) {
		return new Money(currency, new Short(value));
	}

	public MonetaryAmount get(CurrencyUnit currency, int value) {
		return new Money(currency, new Integer(value));
	}

	public MonetaryAmount get(CurrencyUnit currency, long value) {
		return new Money(currency, new Long(value));
	}

	public MonetaryAmount get(CurrencyUnit currency, long major, long minor) {
		if(minor < 0){
			throw new IllegalArgumentException("Minor value must not be < 0.");
		}
		return new Money(currency, new BigDecimal(
				String.valueOf(major) + '.' + minor));
	}

	public MonetaryAmount get(CurrencyUnit currency, float value) {
		return new Money(currency, new Float(value));
	}

	public MonetaryAmount get(CurrencyUnit currency, double value) {
		return new Money(currency, new Double(value));
	}

	public MonetaryAmount zero(CurrencyUnit currency) {
		return new Money(currency, new Double(0.0d));
	}

	public Class<?> getNumberClass() {
		return BigDecimal.class;
	}

}
