package javax.money.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;

import org.junit.Test;

public class ExchangeRateTest {

	@Test
	public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberString() {
		CurrencyUnit base = MoneyCurrency.of("CHF");
		CurrencyUnit term = MoneyCurrency.of("EUR");
		ExchangeRate rate = new ExchangeRate(ExchangeRateType.of("test"),base,term,1.5,"myProvider");
		assertEquals(base, rate.getBase());
		assertEquals(term, rate.getTerm());
		assertTrue(1.5d == rate.getFactor().doubleValue());
		assertEquals(ExchangeRateType.of("test"), rate.getExchangeRateType());
		assertEquals(Arrays.asList(new ExchangeRate[]{rate}), rate.getExchangeRateChain());
		assertEquals("myProvider", rate.getProvider());
	}

	@Test
	public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberStringLongLong() {
		CurrencyUnit base = MoneyCurrency.of("CHF");
		CurrencyUnit term = MoneyCurrency.of("EUR");
		ExchangeRate rate = new ExchangeRate(ExchangeRateType.of("test"),base,term,1.5,"myProvider", 10L, 100L);
		assertEquals(base, rate.getBase());
		assertEquals(term, rate.getTerm());
		assertTrue(1.5d == rate.getFactor().doubleValue());
		assertEquals(ExchangeRateType.of("test"), rate.getExchangeRateType());
		assertEquals(Arrays.asList(new ExchangeRate[]{rate}), rate.getExchangeRateChain());
		assertEquals("myProvider", rate.getProvider());
		assertEquals(10, rate.getValidToTimeInMillis().longValue());
		assertEquals(100, rate.getValidToTimeInMillis().longValue());
	}

	@Test
	public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberStringExchangeRateArray() {
		CurrencyUnit base = MoneyCurrency.of("CHF");
		CurrencyUnit baseTerm = MoneyCurrency.of("EUR");
		CurrencyUnit term = MoneyCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate(ExchangeRateType.of("test"),base,baseTerm,0.8,"myProvider");
		ExchangeRate rate2 = new ExchangeRate(ExchangeRateType.of("test"),baseTerm,term,1.4,"myProvider");
		// derived rate
		ExchangeRate rate = new ExchangeRate(ExchangeRateType.of("test"),base,term,0.8 * 1.4,"myProvider", rate1, rate2);
		
		assertEquals(base, rate.getBase());
		assertEquals(term, rate.getTerm());
		assertTrue(0.8d * 1.4d == rate.getFactor().doubleValue());
		assertEquals(ExchangeRateType.of("test"), rate.getExchangeRateType());
		assertEquals(Arrays.asList(new ExchangeRate[]{rate1, rate2}), rate.getExchangeRateChain());
		assertEquals("myProvider", rate.getProvider());
		assertNull(rate.getValidFrom());
		assertNull(rate.getValidToTimeInMillis());
	}

	@Test
	public void testExchangeRateExchangeRateTypeCurrencyUnitCurrencyUnitNumberStringLongLongExchangeRateArray() {
		CurrencyUnit base = MoneyCurrency.of("CHF");
		CurrencyUnit baseTerm = MoneyCurrency.of("EUR");
		CurrencyUnit term = MoneyCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate(ExchangeRateType.of("test"),base,baseTerm,0.8,"myProvider");
		ExchangeRate rate2 = new ExchangeRate(ExchangeRateType.of("test"),baseTerm,term,1.4,"myProvider");
		// derived rate
		ExchangeRate rate = new ExchangeRate(ExchangeRateType.of("test"),base,term,0.8 * 1.4,"myProvider", 10L, 100L, rate1, rate2);
		
		assertEquals(base, rate.getBase());
		assertEquals(term, rate.getTerm());
		assertTrue(0.8d * 1.4d == rate.getFactor().doubleValue());
		assertEquals(ExchangeRateType.of("test"), rate.getExchangeRateType());
		assertEquals(Arrays.asList(new ExchangeRate[]{rate1, rate2}), rate.getExchangeRateChain());
		assertEquals("myProvider", rate.getProvider());
		assertEquals(10, rate.getValidFromTimeInMillis().longValue());
		assertEquals(100, rate.getValidToTimeInMillis().longValue());
	}

	
	@Test
	public void testToString() {
		CurrencyUnit base = MoneyCurrency.of("CHF");
		CurrencyUnit baseTerm = MoneyCurrency.of("EUR");
		CurrencyUnit term = MoneyCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate(ExchangeRateType.of("test"),base,baseTerm,0.8,"myProvider");
		ExchangeRate rate2 = new ExchangeRate(ExchangeRateType.of("test"),baseTerm,term,1.4,"myProvider");
		// derived rate
		ExchangeRate rate = new ExchangeRate(ExchangeRateType.of("test"),base,term,0.8 * 1.4,"myProvider", 10L, 100L, rate1, rate2);
		assertEquals("ExchangeRate [type=test, base=ISO-4217:CHF, term=ISO-4217:USD, factor=1.1199999999999999, validFrom=10, validUntil=100, provider=myProvider]",rate.toString());
		rate = new ExchangeRate(ExchangeRateType.of("test"),base,term,1.5,"myProvider");
		assertEquals("ExchangeRate [type=test, base=ISO-4217:CHF, term=ISO-4217:USD, factor=1.5, validFrom=null, validUntil=null, provider=myProvider]",rate.toString());
	}

}
