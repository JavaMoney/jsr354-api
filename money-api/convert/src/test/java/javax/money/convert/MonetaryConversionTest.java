package javax.money.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.money.convert.TestMonetaryConversionSpi.DummyConversionProvider;

import org.junit.Test;

public class MonetaryConversionTest {

	@Test
	public void testGetConversionProvider() {
		ConversionProvider prov = MonetaryConversions
				.getConversionProvider("test");
		assertTrue(prov != null);
		assertEquals(DummyConversionProvider.class, prov.getClass());
	}

	@Test
	public void testGetSupportedExchangeRateTypes() {
		Collection<String> types = MonetaryConversions
				.getSupportedExchangeRateTypes();
		assertNotNull(types);
		assertTrue(types.size() >= 1);
		assertTrue(types.contains("test"));
	}

	@Test
	public void testIsSupportedExchangeRateType() {
		assertTrue(MonetaryConversions
				.isSupportedExchangeRateType("test"));
		assertFalse(MonetaryConversions
				.isSupportedExchangeRateType("test2"));
	}

}
