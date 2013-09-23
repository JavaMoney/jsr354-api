package org.javamoney.convert;

import static org.junit.Assert.*;
import javax.money.CurrencyUnit;

import org.javamoney.TestCurrency;
import org.javamoney.convert.CurrencyConversionException;
import org.junit.Test;

public class CurrencyConversionExceptionTest {

	@Test
	public void testCurrencyConversionExceptionCurrencyUnitCurrencyUnitLongString() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("EUR");
		CurrencyConversionException ex = new CurrencyConversionException(base, term, 100L, "test");
		assertEquals(null, ex.getCause());
		assertEquals(base, ex.getBase());
		assertEquals(term, ex.getTerm());
		assertEquals(Long.valueOf(100), ex.getTimestamp());
		assertEquals("Cannot convert CHF into EUR: test", ex.getMessage());
	}

	@Test
	public void testCurrencyConversionExceptionCurrencyUnitCurrencyUnitLong() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("EUR");
		CurrencyConversionException ex = new CurrencyConversionException(base, term, 100L);
		assertEquals(null, ex.getCause());
		assertEquals(base, ex.getBase());
		assertEquals(term, ex.getTerm());
		assertEquals(Long.valueOf(100), ex.getTimestamp());
		assertEquals("Cannot convert CHF into EUR", ex.getMessage());
	}
	

	@Test
	public void testCurrencyConversionExceptionCurrencyUnitCurrencyUnitLongStringThrowable() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("EUR");
		Exception cause = new Exception("cause");
		CurrencyConversionException ex = new CurrencyConversionException(base, term, 100L, "test", cause);
		assertEquals(cause, ex.getCause());
		assertEquals(base, ex.getBase());
		assertEquals(term, ex.getTerm());
		assertEquals(Long.valueOf(100), ex.getTimestamp());
		assertEquals("test", ex.getMessage());
	}


	@Test
	public void testToString() {
		CurrencyUnit base = TestCurrency.of("CHF");
		CurrencyUnit term = TestCurrency.of("EUR");
		Exception cause = new Exception("cause");
		CurrencyConversionException ex = new CurrencyConversionException(base, term, 100L, "test", cause);
		assertEquals("CurrencyConversionException [base=CHF, term=EUR, timestamp=100]: test", ex.toString());
	}

}
