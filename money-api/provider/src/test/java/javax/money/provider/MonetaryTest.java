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
package javax.money.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Enumeration;

import javax.money.convert.ConversionProvider;
import javax.money.format.ItemFormatterFactory;
import javax.money.format.ItemParserFactory;
import javax.money.provider.Monetary.ComponentLoader;
import javax.money.provider.ext.TestExtension;
import javax.money.provider.impl.TestConversionProvider;
import javax.money.provider.impl.TestCurrencyUnitProvider;
import javax.money.provider.impl.TestExtensionImpl;
import javax.money.provider.impl.TestItemFormatterFactory;
import javax.money.provider.impl.TestItemParserFactory;
import javax.money.provider.impl.TestMonetaryAmountProvider;
import javax.money.provider.impl.TestRoundingProvider;

import org.junit.Test;

/**
 * Tests for the provider accessor {@link Monetary}.
 * 
 * @author Anatole Tresch
 */
public class MonetaryTest {

	/**
	 * Test method for
	 * {@link javax.money.provider.Monetary#getMonetaryAmountProvider(java.lang.Class)}
	 * .
	 */
	@Test
	public void testGetMonetaryAmountProviderClassOfQ() {
		ComponentLoader f = Monetary.getLoader();
		assertNotNull(f);
		assertEquals("javax.money.provider.Monetary$DefaultServiceLoader", f.getClass().getName());
	}

	/**
	 * Test method for
	 * {@link javax.money.provider.Monetary#getMonetaryAmountProvider()}.
	 */
	@Test
	public void testGetMonetaryAmountProvider() {
		MonetaryAmountProvider f = Monetary.getMonetaryAmountProvider();
		assertNotNull(f);
		assertEquals(TestMonetaryAmountProvider.class, f.getClass());
	}

	/**
	 * Test method for
	 * {@link javax.money.provider.Monetary#getCurrencyUnitProvider()}.
	 */
	@Test
	public void testGetCurrencyUnitProvider() {
		CurrencyUnitProvider prov = Monetary.getCurrencyUnitProvider();
		assertNotNull(prov);
		assertEquals(TestCurrencyUnitProvider.class, prov.getClass());
	}

	/**
	 * Test method for
	 * {@link javax.money.provider.Monetary#getExchangeRateProvider(javax.money.convert.ExchangeRateType)}
	 * .
	 */
	@Test
	public void testGetExchangeRateProvider() {
		ConversionProvider prov = Monetary.getConversionProvider();
		assertNotNull(prov);
		assertEquals(TestConversionProvider.class, prov.getClass());
	}

	/**
	 * Test method for
	 * {@link javax.money.provider.Monetary#getAmountFormatterFactory()}.
	 */
	@Test
	public void testGetItemFormatterFactory() {
		ItemFormatterFactory f = Monetary.getItemFormatterFactory();
		assertNotNull(f);
		assertEquals(TestItemFormatterFactory.class, f.getClass());
	}

	/**
	 * Test method for
	 * {@link javax.money.provider.Monetary#getAmountParserFactory()}.
	 */
	@Test
	public void testGetItemParserFactory() {
		ItemParserFactory f = Monetary.getItemParserFactory();
		assertNotNull(f);
		assertEquals(TestItemParserFactory.class, f.getClass());
	}

	/**
	 * Test method for
	 * {@link javax.money.provider.Monetary#getRoundingProvider()}.
	 */
	@Test
	public void testGetRoundingProvider() {
		RoundingProvider f = Monetary.getRoundingProvider();
		assertNotNull(f);
		assertEquals(TestRoundingProvider.class, f.getClass());
	}

	/**
	 * Test method for
	 * {@link javax.money.provider.Monetary#getExtension(java.lang.Class)}.
	 */
	@Test
	public void testGetExtension() {
		TestExtension ext = Monetary.getExtension(TestExtension.class);
		assertNotNull(ext);
		assertEquals(TestExtensionImpl.class, ext.getClass());
	}

	/**
	 * Test method for
	 * {@link javax.money.provider.Monetary#isExtensionAvailable(java.lang.Class)}
	 * .
	 */
	@Test
	public void testIsExtensionAvailable() {
		assertTrue(Monetary.isExtensionAvailable(TestExtension.class));
		assertFalse(Monetary.isExtensionAvailable(TestExtensionImpl.class));
		assertFalse(Monetary.isExtensionAvailable(String.class));
	}

	/**
	 * Test method for
	 * {@link javax.money.provider.Monetary#getLoadedExtensions()}.
	 */
	@Test
	public void testGetLoadedExtensions() {
		Enumeration<Class<?>> exts = Monetary.getLoadedExtensions();
		assertTrue(exts.hasMoreElements());
		while (exts.hasMoreElements()) {
			Class<?> type = (Class<?>) exts.nextElement();
			assertEquals(type, TestExtension.class);

		}
	}

}
