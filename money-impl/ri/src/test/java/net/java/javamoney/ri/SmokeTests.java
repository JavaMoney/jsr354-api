package net.java.javamoney.ri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.RoundingMode;
import java.util.Collection;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;
import javax.money.Money;
import javax.money.MoneyCurrency;
import javax.money.MoneyRounding;
import javax.money.convert.ConversionProvider;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateType;
import javax.money.convert.MonetaryConversions;
import javax.money.ext.MonetaryCurrencies;
import javax.money.format.ItemFormat;
import javax.money.format.ItemParseException;
import javax.money.format.LocalizationStyle;
import javax.money.format.MonetaryFormats;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmokeTests {
	private static final Logger logger = LoggerFactory
			.getLogger(SmokeTests.class);

	private static final ExchangeRateType EZB_RATE_TYPE = ExchangeRateType
			.of("EZB");
	private static final ExchangeRateType IMF_RATE_TYPE = ExchangeRateType
			.of("IMF");

	@Test
	public void testCreateAmounts() {
		// Creating one
		CurrencyUnit currency = MonetaryCurrencies.get(
				MoneyCurrency.ISO_NAMESPACE, "CHF");
		MonetaryAmount amount1 = Money.of(currency, 1.0d);
		MonetaryAmount amount2 = Money.of(currency, 1.0d);
		MonetaryAmount amount3 = amount1.add(amount2);
		logger.debug(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals("ISO-4217", currency.getNamespace());
		assertEquals(1.0d, amount1.doubleValue(), 0);
		assertEquals(1.0d, amount2.doubleValue(), 0);
		assertEquals(2.0d, amount3.doubleValue(), 0);
	}

	@Test
	public void testCurrencyAccess() {
		// Creating one
		CurrencyUnit currency = MonetaryCurrencies.get(
				MoneyCurrency.ISO_NAMESPACE, "INR");
		assertNotNull(currency);
		assertEquals("INR", currency.getCurrencyCode());
		assertEquals(MoneyCurrency.ISO_NAMESPACE, currency.getNamespace());
	}

	@Test
	public void testCreateMoney() {
		// Creating one
		Money amount1 = Money.of("CHF", 1.0d);
		Money amount2 = Money.of("CHF", 1.0d);
		MonetaryAmount amount3 = amount1.add(amount2);
		logger.debug(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals(1.0d, amount1.doubleValue(), 0);
		assertEquals(1.0d, amount2.doubleValue(), 0);
		assertEquals(2.0d, amount3.doubleValue(), 0);
	}


	@Test
	public void testExchangeEZB() {
		ConversionProvider prov = MonetaryConversions
				.getConversionProvider(EZB_RATE_TYPE);
		assertNotNull(prov);
		ExchangeRate rate1 = prov.getExchangeRate(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("EUR"));
		ExchangeRate rate2 = prov.getExchangeRate(MoneyCurrency.of("EUR"),
				MoneyCurrency.of("CHF"));
		ExchangeRate rate3 = prov.getExchangeRate(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("USD"));
		ExchangeRate rate4 = prov.getExchangeRate(MoneyCurrency.of("USD"),
				MoneyCurrency.of("CHF"));
		System.out.println(rate1);
		System.out.println(rate2);
		System.out.println(rate3);
		System.out.println(rate4);
	}
	
	@Test
	public void testExchangeIMF() {
		ConversionProvider prov = MonetaryConversions
				.getConversionProvider(IMF_RATE_TYPE);
		assertNotNull(prov);
		ExchangeRate rate1 = prov.getExchangeRate(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("EUR"));
		ExchangeRate rate2 = prov.getExchangeRate(MoneyCurrency.of("EUR"),
				MoneyCurrency.of("CHF"));
		ExchangeRate rate3 = prov.getExchangeRate(MoneyCurrency.of("CHF"),
				MoneyCurrency.of("GBP"));
		ExchangeRate rate4 = prov.getExchangeRate(MoneyCurrency.of("USD"),
				MoneyCurrency.of("AED"));
		System.out.println(rate1);
		System.out.println(rate2);
		System.out.println(rate3);
		System.out.println(rate4);
	}

	@Test
	public void testCurrencyConverter() {
		MonetaryOperator rounding = MoneyRounding.of(2, RoundingMode.HALF_UP);

		MonetaryAmount srcCHF = Money.of("CHF", 100.15);
		MonetaryAmount srcUSD = Money.of("USD", 100.15);
		MonetaryAmount srcEUR = Money.of("EUR", 100.15);

		MonetaryAmount tgt = MonetaryConversions
				.getConversionProvider(EZB_RATE_TYPE).getConverter()
				.convert(srcCHF, MoneyCurrency.of("EUR"));
		MonetaryAmount tgt2 = MonetaryConversions
				.getConversionProvider(EZB_RATE_TYPE).getConverter()
				.convert(tgt, MoneyCurrency.of("CHF"));
		assertEquals(srcCHF, tgt2);
		tgt = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter().convert(srcEUR, MoneyCurrency.of("CHF"));
		tgt2 = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter().convert(tgt, MoneyCurrency.of("EUR"));
		assertEquals(srcEUR, tgt2);
		tgt = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter().convert(srcCHF, MoneyCurrency.of("USD"));
		tgt2 = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter()
				.convert(Money.of("CHF", 100.15d), MoneyCurrency.of("USD"));
		assertEquals(tgt, tgt2);
		tgt = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter().convert(srcUSD, MoneyCurrency.of("CHF"));
		tgt2 = MonetaryConversions.getConversionProvider(EZB_RATE_TYPE)
				.getConverter()
				.convert(Money.of("USD", 100.15d), MoneyCurrency.of("CHF"));
		assertEquals(tgt, tgt2);
	}

	@Test
	public void testGettingFormatters() throws ItemParseException {
		// Using formatters
		CurrencyUnit currency = MonetaryCurrencies.get("ISO-4217", "CHF");
		MonetaryAmount amount = Money.of(currency, 1.0d);
		ItemFormat<MonetaryAmount> formatter = MonetaryFormats.getItemFormat(
				MonetaryAmount.class,
				LocalizationStyle.of(CurrencyUnit.class, "CODE"));
		System.out.println("Formatted amount: " + formatter.format(amount, Locale.GERMANY));
		assertEquals(1.0d, amount.doubleValue(), 0);

		LocalizationStyle.Builder b = new LocalizationStyle.Builder(CurrencyUnit.class, "CODE");
		ItemFormat<CurrencyUnit> formatter2 = MonetaryFormats.getItemFormat(
				CurrencyUnit.class, b.build());
		CurrencyUnit cur = formatter2.parse("CHF", Locale.GERMANY);
		assertNotNull(cur);
		assertEquals("CHF", cur.getCurrencyCode());
	}
}
