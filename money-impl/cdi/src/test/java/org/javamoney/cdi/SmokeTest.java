package org.javamoney.cdi;
/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors: Anatole Tresch - initial version.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.RoundingMode;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAdjuster;
import javax.money.MonetaryAmount;
import javax.money.Money;
import javax.money.MoneyCurrency;
import javax.money.function.MonetaryRoundings;

import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.ExchangeRate;
import org.javamoney.convert.ExchangeRateType;
import org.javamoney.convert.MonetaryConversions;
import org.javamoney.ext.MonetaryCurrencies;
import org.javamoney.format.ItemFormat;
import org.javamoney.format.ItemParseException;
import org.javamoney.format.LocalizationStyle;
import org.javamoney.format.MonetaryFormats;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmokeTest {
	private static final Logger logger = LoggerFactory
			.getLogger(SmokeTest.class);

	private static final ExchangeRateType RATE_TYPE = ExchangeRateType
			.of("EZB");

	@Test
	public void testCreateAmounts() {
		// Creating one
		CurrencyUnit currency = MonetaryCurrencies.get("CHF");
		Money amount1 = Money.of(currency, 1.0d);
		Money amount2 = Money.of(currency, 1.0d);
		Money amount3 = amount1.add(amount2);
		logger.debug(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals(1.0d, amount1.doubleValue(), 0);
		assertEquals(1.0d, amount2.doubleValue(), 0);
		assertEquals(2.0d, amount3.doubleValue(), 0);
	}

	@Test
	public void testCreateMoney() {
		// Creating one
		Money amount1 = Money.of("CHF", 1.0d);
		Money amount2 = Money.of("CHF", 1.0d);
		Money amount3 = amount1.add(amount2);
		logger.debug(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals(1.0d, amount1.doubleValue(), 0);
		assertEquals(1.0d, amount2.doubleValue(), 0);
		assertEquals(2.0d, amount3.doubleValue(), 0);
	}

	@Test
	public void testExchange() {
		ConversionProvider prov = MonetaryConversions
				.getConversionProvider(RATE_TYPE);
		assertNotNull(prov);
		ExchangeRate rate1 = prov.getExchangeRate(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("EUR"));
		ExchangeRate rate2 = prov.getExchangeRate(MoneyCurrency.of("EUR"),
				MoneyCurrency.of("CHF"));
		ExchangeRate rate3 = prov.getExchangeRate(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("USD"));
		ExchangeRate rate4 = prov.getExchangeRate(MoneyCurrency.of("USD"),
				MoneyCurrency.of("CHF"));
		System.out.println(rate1);
		System.out.println(rate2);
		System.out.println(rate3);
		System.out.println(rate4);
	}

	@Test
	public void testCurrencyConverter() {
		MonetaryAdjuster rounding = MonetaryRoundings.getRounding(2,
				RoundingMode.HALF_UP);

		MonetaryAmount srcCHF = Money.of(MoneyCurrency.of("CHF"), 100.15);
		MonetaryAmount srcUSD = Money.of(MoneyCurrency.of("USD"), 100.15);
		MonetaryAmount srcEUR = Money.of(MoneyCurrency.of("EUR"), 100.15);

		MonetaryAmount tgt = MonetaryConversions
				.getConversionProvider(RATE_TYPE).getConverter()
				.convert(srcCHF, MoneyCurrency.of("EUR"));
		MonetaryAmount tgt3 = MonetaryConversions
				.getConversionProvider(RATE_TYPE).getConverter()
				.convert(tgt, MoneyCurrency.of("CHF"));
		assertEquals(srcCHF.with(rounding), tgt3.with(rounding));
		tgt = MonetaryConversions.getConversionProvider(RATE_TYPE)
				.getConverter().convert(srcEUR, MoneyCurrency.of("CHF"));
		tgt3 = MonetaryConversions.getConversionProvider(RATE_TYPE)
				.getConverter().convert(tgt, MoneyCurrency.of("EUR"));
		assertEquals(srcEUR.with(rounding), rounding.adjustInto(tgt3));
		tgt = MonetaryConversions.getConversionProvider(RATE_TYPE)
				.getConverter().convert(srcCHF, MoneyCurrency.of("USD"));
		tgt3 = MonetaryConversions.getConversionProvider(RATE_TYPE)
				.getConverter().convert(tgt, MoneyCurrency.of("CHF"));
		assertEquals(srcCHF, tgt3);
		assertEquals(srcCHF.with(rounding), rounding.adjustInto(tgt3));
	}

	@Test
	public void testGettingParsers() {
		// Using parsers
		try {
			ItemFormat<CurrencyUnit> parser = MonetaryFormats.getItemFormat(
					CurrencyUnit.class,
					LocalizationStyle.of(CurrencyUnit.class, "ID"));
			CurrencyUnit cur = parser.parse("CHF", Locale.ENGLISH);
			assertNotNull(cur);
			assertEquals("CHF", cur.getCurrencyCode());
		} catch (ItemParseException e) {
			logger.debug("Error", e);
		}
	}

	@Test
	public void testGettingFormatters() {
		// Using formatters
		CurrencyUnit currency = MonetaryCurrencies.get("CHF");
		Money amount = Money.of(currency, 1.0d);
		ItemFormat<MonetaryAmount> formatter = MonetaryFormats.getItemFormat(
				MonetaryAmount.class,
				LocalizationStyle.of(MonetaryAmount.class, "CODE"));
		System.out.println("Formatted amount: "
				+ formatter.format(amount, Locale.GERMANY));
		assertEquals(1.0d, amount.doubleValue(), 0);
	}

	@Test
	public void testCurrencyAccess() {
		// Creating one
		CurrencyUnit currency = MonetaryCurrencies.get("INR");
		assertNotNull(currency);
	}
}
