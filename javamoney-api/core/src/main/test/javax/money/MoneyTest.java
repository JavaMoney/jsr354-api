package javax.money;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class MoneyTest extends RITestBase{

	private static final BigDecimal TEN = new BigDecimal(10.0d);
	
	@Test
	public void testGetInstanceCurrencyBigDecimal() {
		Money m = Money.valueOf(MoneyCurrency.getInstance("EUR"), TEN);
		assertEquals(TEN, m.asType(BigDecimal.class));
	}

	@Test
	public void testGetInstanceCurrencyDouble() {
		Money m = Money.valueOf(MoneyCurrency.getInstance("EUR"), 10.0d);
		assertEquals(TEN, m.asType(BigDecimal.class));
	}

	@Test
	public void testGetCurrency() {
		MonetaryAmount money = Money.valueOf(EURO, BigDecimal.TEN);
		assertNotNull(money.getCurrency());
		assertEquals("EUR", money.getCurrency().getCurrencyCode());
	}

	@Test
	public void testAddNumber() {
		MonetaryAmount money1 = Money.valueOf(EURO, BigDecimal.TEN);
		MonetaryAmount money2 = Money.valueOf(EURO, BigDecimal.ONE);
		MonetaryAmount moneyResult = money1.add(money2);
		assertNotNull(moneyResult);
		assertEquals(11d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testSubtractMonetaryAmount() {
		MonetaryAmount money1 = Money.valueOf(EURO, BigDecimal.TEN);
		MonetaryAmount money2 = Money.valueOf(EURO, BigDecimal.ONE);
		MonetaryAmount moneyResult = money1.subtract(money2);
		assertNotNull(moneyResult);
		assertEquals(9d, moneyResult.doubleValue(), 0d);
	}

	@Test
	public void testDivideAndRemainder_BigDecimal() {
		MonetaryAmount money1 = Money.valueOf(EURO, BigDecimal.ONE);
		MonetaryAmount money2 = Money.valueOf(EURO, new BigDecimal("0.50000000000000000001"));
		MonetaryAmount[] divideAndRemainder = money1.divideAndRemainder(money2);
		assertThat(divideAndRemainder[0].asType(BigDecimal.class), equalTo(BigDecimal.ONE));
		assertThat(divideAndRemainder[1].asType(BigDecimal.class), equalTo(new BigDecimal("0.49999999999999999999")));
	}

	@Test
	public void testDivideToIntegralValue_BigDecimal() {
		MonetaryAmount money1 = Money.valueOf(EURO, BigDecimal.ONE);
		MonetaryAmount money2 = Money.valueOf(EURO, new BigDecimal("0.50000000000000000001"));
		MonetaryAmount result = money1.divideToIntegralValue(money2);
		assertThat(result.asType(BigDecimal.class), equalTo(BigDecimal.ONE));
	}
}
