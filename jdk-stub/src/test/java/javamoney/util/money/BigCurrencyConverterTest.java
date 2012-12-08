package javamoney.util.money;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import javamoney.util.Currency;

import javamoney.util.Money;

import javax.money.convert.CurrencyConverter;

import org.junit.Ignore;
import org.junit.Test;

public class BigCurrencyConverterTest {

	@Test
	public void testGetSource() {
		CurrencyConverter<BigDecimal> converter = new BigCurrencyConverter(Money.getInstance(
				Currency.getInstance("EUR"), BigDecimal.TEN),
				Currency.getInstance("USD"), BigDecimal.valueOf(0.7));
		assertEquals(BigDecimal.valueOf(21d), converter.convert(BigDecimal.valueOf(30)));
	}

	@Test
	@Ignore
	public void testGetTarget() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testConvertDouble() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testConvertBigDecimalMathContext() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetExchangeRate() {
		fail("Not yet implemented");
	}

}
