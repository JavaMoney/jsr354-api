package net.java.javamoney.ri.convert;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.money.convert.ExchangeRate;

import org.junit.Test;

public class ExchangeRateImplTest extends ConvertTestBase {

	@Test
	public void testGetSourceCurrency() {
		ExchangeRate rate = new ExchangeRateImpl(EURO, DOLLAR, BigDecimal.valueOf(1.30693d), 
				System.currentTimeMillis(),	TEN_MINUTES_IN_MILLIS);
		assertNotNull(rate);
		assertEquals("EUR", rate.getSourceCurrency().getCurrencyCode());
	}

	@Test
	public void testGetTargetCurrency() {
		ExchangeRate rate = new ExchangeRateImpl(EURO, DOLLAR, BigDecimal.valueOf(1.30693d), 
				System.currentTimeMillis(),	TEN_MINUTES_IN_MILLIS);
		assertNotNull(rate);
		assertEquals("USD", rate.getTargetCurrency().getCurrencyCode());
	}

	@Test
	public void testGetTimestamp() {
		long timestamp = System.currentTimeMillis();
		ExchangeRate rate = new ExchangeRateImpl(EURO, DOLLAR, BigDecimal.valueOf(1.30693d), timestamp, 
				TEN_MINUTES_IN_MILLIS);
		assertNotNull(rate);
		assertEquals(timestamp, rate.getTimestamp());
	}

}
