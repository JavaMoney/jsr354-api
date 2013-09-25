package org.javamoney.convert;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;


import org.javamoney.convert.ConversionProvider;
import org.javamoney.convert.ExchangeRateType;
import org.javamoney.convert.MonetaryConversions;
import org.javamoney.convert.TestMonetaryConversionSpi.DummyConversionProvider;
import org.junit.Test;

public class MonetaryConversionTest {

	@Test
	public void testGetConversionProvider() {
		ConversionProvider prov = MonetaryConversions
				.getConversionProvider(ExchangeRateType.of("test"));
		assertTrue(prov != null);
		assertEquals(DummyConversionProvider.class, prov.getClass());
	}

	@Test
	public void testGetSupportedExchangeRateTypes() {
		ExchangeRateType.of("test");
		Collection<ExchangeRateType> types = MonetaryConversions
			.getSupportedExchangeRateTypes();
		assertNotNull(types);
		assertTrue(types.size() >= 1);
		assertTrue(types.contains(ExchangeRateType.of("test")));
	}

	@Test
	public void testIsSupportedExchangeRateType() {
		assertTrue(MonetaryConversions
				.isSupportedExchangeRateType(ExchangeRateType.of("test")));
		assertFalse(MonetaryConversions
				.isSupportedExchangeRateType(ExchangeRateType.of("test2")));
	}

}
