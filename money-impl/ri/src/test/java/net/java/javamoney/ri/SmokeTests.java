package net.java.javamoney.ri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.RoundingMode;
import java.util.Collection;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryOperator;
import javax.money.MonetaryAmount;
import javax.money.Money;
import javax.money.MoneyCurrency;
import javax.money.MoneyRounding;
import javax.money.convert.ConversionProvider;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ExchangeRateType;
import javax.money.format.ItemFormatter;
import javax.money.format.ItemParseException;
import javax.money.format.ItemParser;
import javax.money.format.LocalizationStyle;
import javax.money.provider.Monetary;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmokeTests {
	private static final Logger logger = LoggerFactory
			.getLogger(SmokeTests.class);

	private static final ExchangeRateType RATE_TYPE = ExchangeRateType
			.of("EZB");

	@Test
	public void testCreateAmounts() {
		// Creating one
		CurrencyUnit currency = Monetary.getCurrencyUnitProvider().get(
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
		CurrencyUnit currency = Monetary.getCurrencyUnitProvider().get(
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
	public void testGettingCurrenciesPerLocale() {
		Collection<CurrencyUnit> currencies = Monetary
				.getCurrencyUnitProvider().getAll(Locale.US);
		logger.debug("Currencies for US: " + currencies);
		currencies = Monetary.getCurrencyUnitProvider().getAll(Locale.CHINA);
		logger.debug("Currencies for CHINA: " + currencies);
		currencies = Monetary.getCurrencyUnitProvider().getAll(Locale.ROOT);
		logger.debug("Currencies for ROOT (undefined): " + currencies);
	}

	@Test
	public void testExchange() {
		ExchangeRateProvider prov = Monetary.getConversionProvider()
				.getExchangeRateProvider(RATE_TYPE);
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
	public void testCurrencyConverter() {
		MonetaryOperator rounding = MoneyRounding.of(2, RoundingMode.HALF_UP);

		ConversionProvider conv = Monetary.getConversionProvider();
		assertNotNull(conv);
		MonetaryAmount srcCHF = Money.of("CHF", 100.15);
		MonetaryAmount srcUSD = Money.of("USD", 100.15);
		MonetaryAmount srcEUR = Money.of("EUR", 100.15);

		MonetaryAmount tgt = conv.getCurrencyConverter(RATE_TYPE).convert(
				srcCHF, MoneyCurrency.of("EUR"));
		MonetaryAmount tgt2 = conv.getCurrencyConverter(RATE_TYPE).convert(
				100.15d, MoneyCurrency.of("CHF"), MoneyCurrency.of("EUR"));
		MonetaryAmount tgt3 = conv.getCurrencyConverter(RATE_TYPE).convert(
				tgt2, MoneyCurrency.of("CHF"));
		assertEquals(tgt, tgt2);
		assertEquals(srcCHF.with(rounding), tgt3.with(rounding));
		tgt = conv.getCurrencyConverter(RATE_TYPE).convert(srcEUR,
				MoneyCurrency.of("CHF"));
		tgt2 = conv.getCurrencyConverter(RATE_TYPE).convert(100.15d,
				MoneyCurrency.of("EUR"), MoneyCurrency.of("CHF"));
		tgt3 = conv.getCurrencyConverter(RATE_TYPE).convert(tgt,
				MoneyCurrency.of("EUR"));
		assertEquals(tgt, tgt2);
		assertEquals(srcEUR.with(rounding), rounding.apply(tgt3));
		tgt = conv.getCurrencyConverter(RATE_TYPE).convert(srcCHF,
				MoneyCurrency.of("USD"));
		tgt2 = conv.getCurrencyConverter(RATE_TYPE).convert(100.15d,
				MoneyCurrency.of("CHF"), MoneyCurrency.of("USD"));
		tgt3 = conv.getCurrencyConverter(RATE_TYPE).convert(tgt2,
				MoneyCurrency.of("CHF"));
		assertEquals(tgt, tgt2);
		assertEquals(srcCHF.with(rounding), rounding.apply(tgt3));
		tgt = conv.getCurrencyConverter(RATE_TYPE).convert(srcUSD,
				MoneyCurrency.of("CHF"));
		tgt2 = conv.getCurrencyConverter(RATE_TYPE).convert(100.15d,
				MoneyCurrency.of("USD"), MoneyCurrency.of("CHF"));
		tgt3 = conv.getCurrencyConverter(RATE_TYPE).convert(tgt2,
				MoneyCurrency.of("USD"));
		assertEquals(tgt, tgt2);
		assertEquals(srcUSD.with(rounding), rounding.apply(tgt3));
	}

	@Test
	public void testGettingParsers() {
		// Using parsers
		try {
			ItemParser<CurrencyUnit> parser = Monetary.getItemParserFactory()
					.getItemParser(CurrencyUnit.class,
							LocalizationStyle.valueOf(Locale.ENGLISH));
			CurrencyUnit cur = parser.parse("CHF");
			assertNotNull(cur);
			assertEquals("CHF", cur.getCurrencyCode());
		} catch (ItemParseException e) {
			logger.debug("Error", e);
		}
	}

	@Test
	public void testGettingFormatters() {
		// Using formatters
		CurrencyUnit currency = Monetary.getCurrencyUnitProvider().get(
				"ISO-4217", "CHF");
		MonetaryAmount amount = Money.of(currency, 1.0d);
		ItemFormatter<MonetaryAmount> formatter = Monetary
				.getItemFormatterFactory().getItemFormatter(
						MonetaryAmount.class,
						LocalizationStyle.valueOf("CODE", Locale.GERMANY));
		System.out.println("Formatted amount: " + formatter.format(amount));
		assertEquals(1.0d, amount.doubleValue(), 0);
	}
}
