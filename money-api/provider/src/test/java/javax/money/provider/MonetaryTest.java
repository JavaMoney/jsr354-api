/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
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
