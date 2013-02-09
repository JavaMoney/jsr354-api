package javamoney.util;

import static java.math.BigDecimal.TEN;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;


public class MoneyTest {

	@Test
	public void testGetInstanceCurrencyBigDecimal() {
		Money m = Money.valueOf(Currency.getInstance("EUR"), TEN);
		assertEquals(TEN, m.getValue());
	}

	@Test
	public void testGetInstanceCurrencyDouble() {
		Money m = Money.valueOf(Currency.getInstance("EUR"), 10d);
		assertEquals(BigDecimal.valueOf(10d), m.getValue());
	}

}

