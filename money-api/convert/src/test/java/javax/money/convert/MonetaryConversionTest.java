package javax.money.convert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class MonetaryConversionTest {

	@Test
	public void testGetConversionProvider() {
		ConversionProvider prov = MonetaryConversions
				.getConversionProvider(ExchangeRateType.of("test"));
		assertTrue(prov != null);
		//assertEquals(DummyConversionProvider.class, prov.getClass());
	}

	@Test
	public void testGetSupportedExchangeRateTypes() {
		Collection<ExchangeRateType> types = MonetaryConversions
				.getSupportedExchangeRateTypes();
		assertNotNull(types);
		assertTrue(types.size() == 1);
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
