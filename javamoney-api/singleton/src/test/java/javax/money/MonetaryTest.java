/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Enumeration;

import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.spi.MonetaryExtension;

import org.junit.Test;

/**
 * Test for the JSR 354 {@link Monetary} singleton. This test only checks if for
 * each exposed API component the correct instance is returned. The test bed
 * within the spec will register according empty implementation, which are not
 * provided as part of the spec itself (test scope only).
 * 
 * @author Anatole Tresch
 */
public class MonetaryTest {

	@Test
	public void testGetAmountFormatterFactory() {
		assertNotNull(Monetary.getAmountFormatterFactory());
		assertSame(Monetary.getAmountFormatterFactory().getClass(),
				TestAmountFormatterFactory.class);
	}

	@Test
	public void testGetAmountParserFactory() {
		assertNotNull(Monetary.getAmountParserFactory());
		assertSame(Monetary.getAmountParserFactory().getClass(),
				TestAmountParseFactory.class);
	}

	@Test
	public void testGetCurrencyConverter_ExchangeRateType() {
		Enumeration<ExchangeRateType> types = Monetary
				.getSupportedExchangeRateTypes();
		ExchangeRateType exchangeRateType = types.nextElement();
		CurrencyConverter prov = Monetary
				.getCurrencyConverter(exchangeRateType);
		assertTrue(prov == Monetary.getCurrencyConverter(exchangeRateType));
		assertSame(Monetary.getCurrencyConverter(exchangeRateType).getClass(),
				TestCurrencyConverter.class);
		assertTrue(prov.getExchangeRateType() == exchangeRateType);
	}

	@Test
	public void testGetCurrencyFormatterFactory() {
		assertNotNull(Monetary.getCurrencyFormatterFactory());
		assertSame(Monetary.getCurrencyFormatterFactory().getClass(),
				TestCurrencyFormatterFactory.class);
	}

	@Test
	public void testGetCurrencyUnitProvider() {
		assertNotNull(Monetary.getCurrencyUnitProvider());
		assertSame(Monetary.getCurrencyUnitProvider().getClass(),
				TestCurrencyUnitProvider.class);
	}

	@Test
	public void testGetCurrencyParserFactory() {
		assertNotNull(Monetary.getCurrencyParserFactory());
		assertSame(Monetary.getCurrencyParserFactory().getClass(),
				TestCurrencyParserFactory.class);
	}

	@Test
	public void testGetDefaultNumberClass() {
		assertNotNull(Monetary.getDefaultNumberClass());
		assertSame(Monetary.getDefaultNumberClass(), BigDecimal.class);
	}

	@Test
	public void testGetExchangeRateProvider_ExchangeRateType() {
		Enumeration<ExchangeRateType> types = Monetary
				.getSupportedExchangeRateTypes();
		ExchangeRateType exchangeRateType = types.nextElement();
		ExchangeRateProvider prov = Monetary
				.getExchangeRateProvider(exchangeRateType);
		assertTrue(prov == Monetary.getExchangeRateProvider(exchangeRateType));
		assertSame(Monetary.getExchangeRateProvider(exchangeRateType)
				.getClass(), TestExchangeRateProvider.class);
		assertTrue(Monetary.getExchangeRateProvider(exchangeRateType)
				.getExchangeRateType() == exchangeRateType);
	}

	@Test
	public void testGetMonetaryAmountFactory() {
		assertNotNull(Monetary.getMonetaryAmountFactory());
		assertSame(Monetary.getMonetaryAmountFactory().getClass(),
				TestMonetaryAmountFactory2.class);
	}

	@Test
	public void testGetMonetaryAmountFactory_Class() {
		assertNotNull(Monetary.getMonetaryAmountFactory(String.class));
		assertSame(Monetary.getMonetaryAmountFactory(String.class).getClass(),
				TestMonetaryAmountFactory.class);
	}

	@Test
	public void testGetRoundingProvider() {
		assertNotNull(Monetary.getRoundingProvider());
		assertSame(Monetary.getRoundingProvider().getClass(),
				TestRoundingProvider.class);
	}

	@Test
	public void testGetExtensions() {
		Enumeration<Class<?>> types = Monetary.getLoadedExtensions();
		assertNotNull(types);
		int count = 0;
		while (types.hasMoreElements()) {
			Class<?> clazz = types.nextElement();
			assertSame(clazz, TestExtension.class);
			count++;
		}
		assertTrue(count == 1);
	}

	@Test
	public void testIsExtensionAvailable() {
		assertTrue(Monetary.isExtensionAvailable(TestExtension.class));
		assertFalse(Monetary.isExtensionAvailable(String.class));
		assertFalse(Monetary.isExtensionAvailable(TestExtensionImpl.class));
	}

	@Test
	public void testGetAndUseExtension() {
		TestExtension ext = Monetary.getExtension(TestExtension.class);
		assertNotNull(ext);
		assertEquals("Hello!", ext.sayHello());
	}

	@Test
	public void testGetSupportedExchangeRateTypes() {
		Enumeration<ExchangeRateType> types = Monetary
				.getSupportedExchangeRateTypes();
		assertNotNull(types);
		int count = 0;
		while (types.hasMoreElements()) {
			ExchangeRateType exchangeRateType = (ExchangeRateType) types
					.nextElement();
			count++;
			assertEquals("TEST", exchangeRateType.getId());
		}
		assertTrue(count == 1);
	}
}
