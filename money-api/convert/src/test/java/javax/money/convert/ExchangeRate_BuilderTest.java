package javax.money.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;
import javax.money.convert.ExchangeRate.Builder;

import org.junit.Test;

public class ExchangeRate_BuilderTest extends Builder {

	@Test
	public void testGetSetExchangeRateType() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		assertNull(b.getExchangeRateType());
		ExchangeRate.Builder b2 = b.setExchangeRateType(ExchangeRateType
				.of("test"));
		assertTrue(b == b2);
		assertEquals(ExchangeRateType.of("test"), b.getExchangeRateType());
		b2 = b.setExchangeRateType("test2");
		assertTrue(b == b2);
		assertEquals(ExchangeRateType.of("test2"), b.getExchangeRateType());
	}

	@Test
	public void testGetSetBase() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		assertNull(b.getBase());
		ExchangeRate.Builder b2 = b.setBase(MoneyCurrency.of("CHF"));
		assertTrue(b == b2);
		assertEquals(MoneyCurrency.of("CHF"), b.getBase());
	}

	@Test
	public void testGetSetTerm() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		assertNull(b.getBase());
		ExchangeRate.Builder b2 = b.setTerm(MoneyCurrency.of("CHF"));
		assertTrue(b == b2);
		assertEquals(MoneyCurrency.of("CHF"), b.getTerm());
	}

	@Test
	public void testGetSetValidFrom() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		assertNull(b.getBase());
		ExchangeRate.Builder b2 = b.setValidFrom(100L);
		assertTrue(b == b2);
		assertEquals(Long.valueOf(100), b.getValidFrom());
	}

	@Test
	public void testGetSetValidUntil() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		assertNull(b.getBase());
		ExchangeRate.Builder b2 = b.setValidUntil(100L);
		assertTrue(b == b2);
		assertEquals(Long.valueOf(100), b.getValidUntil());
	}

	@Test
	public void testGetSetExchangeRateChain() {
		CurrencyUnit base = MoneyCurrency.of("CHF");
		CurrencyUnit baseTerm = MoneyCurrency.of("EUR");
		CurrencyUnit term = MoneyCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate(ExchangeRateType.of("test"),base,baseTerm,0.8,"myProvider");
		ExchangeRate rate2 = new ExchangeRate(ExchangeRateType.of("test"),baseTerm,term,1.4,"myProvider");
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		assertNotNull(b.getExchangeRateChain());
		assertTrue(b.getExchangeRateChain().isEmpty());
		ExchangeRate.Builder b2 = b.setExchangeRateChain(rate1, rate2);
		assertTrue(b == b2);
		assertEquals(Arrays.asList(new ExchangeRate[]{rate1, rate2}),b.getExchangeRateChain());
	}

	@Test
	public void testGetSetBaseLeadingFactor() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		assertNull(b.getFactor());
		ExchangeRate.Builder b2 = b.setFactor(Long.MAX_VALUE);
		assertTrue(b == b2);
		assertEquals(BigDecimal.valueOf(Long.MAX_VALUE), b.getFactor());
		b.setFactor(100L);
		assertEquals(BigDecimal.valueOf(100L), b.getFactor());
	}

	@Test
	public void testGetSetTermLeadingFactorBigDecimal() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		assertNull(b.getFactor());
		ExchangeRate.Builder b2 = b.setFactor(1.2);
		assertTrue(b == b2);
		assertEquals(BigDecimal.ONE.divide(BigDecimal.valueOf(1.2),RoundingMode.HALF_EVEN), b.getFactor());
	}

	@Test
	public void testGetSetProvider() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		assertNull(b.getProvider());
		ExchangeRate.Builder b2 = b.setProvider("testProvider");
		assertTrue(b == b2);
		assertEquals("testProvider", b.getProvider());
	}

	@Test
	public void testIsBuildeable() {
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		assertFalse(b.isBuildeable());
		b.setExchangeRateType(ExchangeRateType.of("test"));
		assertFalse(b.isBuildeable());
		b.setBase(MoneyCurrency.of("CHF"));
		assertFalse(b.isBuildeable());
		b.setTerm(MoneyCurrency.of("CHF"));
		assertFalse(b.isBuildeable());
		b.setFactor(2.0);
		assertTrue(b.isBuildeable());
	}

	@Test
	public void testBuild() {
		CurrencyUnit base = MoneyCurrency.of("CHF");
		CurrencyUnit baseTerm = MoneyCurrency.of("EUR");
		CurrencyUnit term = MoneyCurrency.of("USD");
		ExchangeRate rate1 = new ExchangeRate(ExchangeRateType.of("test"),base,baseTerm,0.8,"myProvider");
		ExchangeRate rate2 = new ExchangeRate(ExchangeRateType.of("test"),baseTerm,term,1.4,"myProvider");
		
		ExchangeRate.Builder b = new ExchangeRate.Builder();
		b.setExchangeRateType(ExchangeRateType.of("bla"));
		b.setBase(base);
		b.setTerm(term);
		b.setFactor(2.2);
		b.setProvider("myProvider");
		ExchangeRate rate = b.build();
		assertEquals(new ExchangeRate(ExchangeRateType.of("bla"),base,term,2.2,"myProvider"), rate);
		
		b = new ExchangeRate.Builder();
		b.setExchangeRateType(ExchangeRateType.of("test"));
		b.setBase(MoneyCurrency.of("CHF"));
		b.setTerm(MoneyCurrency.of("USD"));
		b.setExchangeRateChain(rate1, rate2);
		b.setFactor(2.0);
		rate = b.build();
		assertEquals(new ExchangeRate(b.getExchangeRateType(), b.getBase(), b.getTerm(), b.getFactor(), null, rate1, rate2), rate);
	}

}
