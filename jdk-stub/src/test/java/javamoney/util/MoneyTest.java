package javamoney.util;

import static org.junit.Assert.*;
import static java.math.BigDecimal.TEN;

import java.math.BigDecimal;

import javamoney.util.Currency;
import javamoney.util.Money;

import org.junit.Test;

public class MoneyTest {

	@Test
	public void testGetInstanceCurrencyBigDecimal() {
		Money m = Money.getInstance(Currency.getInstance("EUR"), TEN);
		assertEquals(TEN, m.getAmount());
	}

	@Test
	public void testGetInstanceCurrencyDouble() {
		Money m = Money.getInstance(Currency.getInstance("EUR"), 10d);
		assertEquals(BigDecimal.valueOf(10d), m.getAmount());
	}

}
