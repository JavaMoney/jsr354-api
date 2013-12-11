package javax.money.function;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Locale;

import javax.money.MonetaryContext;
import javax.money.MonetaryCurrencies;
import javax.money.MonetaryOperator;

import org.junit.Test;

public class MonetaryRoundingsTest {

	@Test
	public void testMonetaryRoundingsGetCashRoundingCurrencyUnit() {
		MonetaryOperator op = MonetaryRoundings
				.getCashRounding(MonetaryCurrencies.getCurrency(new Locale("", "TEST1L")));
		assertNotNull(op);
	}

	@Test
	public void testMonetaryRoundingsGetCashRoundingCurrencyUnitLong() {
		MonetaryOperator op = MonetaryRoundings.getCashRounding(
				MonetaryCurrencies.getCurrency(new Locale("", "TEST1L")), 200L);
		assertNotNull(op);
	}

	@Test
	public void testMonetaryRoundingsGetRoundingCurrencyUnit() {
		MonetaryOperator op = MonetaryRoundings.getRounding(MonetaryCurrencies
				.getCurrency("test1"));
		assertNotNull(op);
	}

	@Test
	public void testMonetaryRoundingsGetRoundingCurrencyUnitLong() {
		MonetaryOperator op = MonetaryRoundings.getRounding(
				MonetaryCurrencies.getCurrency("test1"), 200L);
		assertNotNull(op);
	}

	@Test
	public void testMonetaryRoundingsGetRounding() {
		MonetaryOperator op = MonetaryRoundings.getRounding();
		assertNotNull(op);
	}

	@Test
	public void testMonetaryRoundingsGetRoundingString() {
		MonetaryOperator op = MonetaryRoundings.getRounding("custom1");
		assertNotNull(op);
	}

	@Test
	public void testMonetaryRoundingsGetRoundingMonetaryContext() {
		MonetaryOperator op = MonetaryRoundings
				.getRounding(new MonetaryContext.Builder(BigDecimal.class).build());
		assertNotNull(op);
	}
}
