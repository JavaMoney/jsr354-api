package net.java.javamoney.ri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Locale;

import javax.money.AmountAdjuster;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.format.AmountFormatter;
import javax.money.format.AmountParser;
import javax.money.format.StyleableAmountFormatter;
import javax.money.format.StyleableAmountParser;
import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.ParseException;
import javax.money.provider.Monetary;

import net.java.javamoney.ri.convert.SingletonExchangeRateType;
import net.java.javamoney.ri.core.Money;
import net.java.javamoney.ri.core.MoneyCurrency;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmokeTests {
	private static final Logger logger = LoggerFactory
			.getLogger(SmokeTests.class);

	@Test
	public void testCreateAmounts() {
		// Creating one
		CurrencyUnit currency = Monetary.getCurrencyUnitProvider().get(
				"ISO-4217", "CHF");
		MonetaryAmount amount1 = Monetary.getMonetaryAmountProvider().get(
				currency, 1.0d);
		MonetaryAmount amount2 = Monetary.getMonetaryAmountProvider().get(
				currency, 1.0d);
		MonetaryAmount amount3 = amount1.add(amount2);
		logger.debug(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals("ISO-4217", currency.getNamespace());
		assertEquals(1.0d, amount1.doubleValue(), 0);
		assertEquals(1.0d, amount2.doubleValue(), 0);
		assertEquals(2.0d, amount3.doubleValue(), 0);
	}

	@Test
	public void testCreateMoney() {
		// Creating one
		Money amount1 = Money.valueOf("CHF", 1.0d);
		Money amount2 = Money.valueOf("CHF", 1.0d);
		MonetaryAmount amount3 = amount1.add(amount2);
		logger.debug(amount1 + " + " + amount2 + " = " + amount3);
		assertEquals(1.0d, amount1.doubleValue(), 0);
		assertEquals(1.0d, amount2.doubleValue(), 0);
		assertEquals(2.0d, amount3.doubleValue(), 0);
	}

	@Test
	public void testGettingCurrenciesPerLocale() {
		CurrencyUnit[] currencies = Monetary.getCurrencyUnitProvider().getAll(
				Locale.US);
		logger.debug("Currencies for US: " + Arrays.toString(currencies));
		currencies = Monetary.getCurrencyUnitProvider().getAll(Locale.CHINA);
		logger.debug("Currencies for CHINA: " + Arrays.toString(currencies));
		currencies = Monetary.getCurrencyUnitProvider().getAll(Locale.ROOT);
		logger.debug("Currencies for ROOT (undefined): "
				+ Arrays.toString(currencies));
	}

	@Test
	public void testExchange() {
		ExchangeRateProvider prov = Monetary
				.getExchangeRateProvider(SingletonExchangeRateType.of("EZB"));
		assertNotNull(prov);
		ExchangeRate rate1 = prov.get(MoneyCurrency.getInstance("CHF"),MoneyCurrency.getInstance("EUR"));
		ExchangeRate rate2 = prov.get(MoneyCurrency.getInstance("EUR"),MoneyCurrency.getInstance("CHF"));
		ExchangeRate rate3 = prov.get(MoneyCurrency.getInstance("CHF"),MoneyCurrency.getInstance("USD"));
		ExchangeRate rate4 = prov.get(MoneyCurrency.getInstance("USD"),MoneyCurrency.getInstance("CHF"));
		System.out.println(rate1);
		System.out.println(rate2);
		System.out.println(rate3);
		System.out.println(rate4);
	}
	
	@Test
	public void testCurrencyConverter() {
		AmountAdjuster rounding = StandardRoundings.getRounding(5, RoundingMode.HALF_UP);
		
		CurrencyConverter conv = Monetary
				.getCurrencyConverter(SingletonExchangeRateType.of("EZB"));
		assertNotNull(conv);
		MonetaryAmount srcCHF = Money.valueOf(MoneyCurrency.getInstance("CHF"), 100.15);
		MonetaryAmount srcUSD = Money.valueOf(MoneyCurrency.getInstance("USD"), 100.15);
		MonetaryAmount srcEUR = Money.valueOf(MoneyCurrency.getInstance("EUR"), 100.15);
		
		MonetaryAmount tgt = conv.convert(srcCHF, MoneyCurrency.getInstance("EUR"));
		MonetaryAmount tgt2 = conv.convert(100.15d, MoneyCurrency.getInstance("CHF"), MoneyCurrency.getInstance("EUR"));
		MonetaryAmount tgt3 = conv.convert(tgt2, MoneyCurrency.getInstance("CHF"));
		assertEquals(tgt, tgt2);
		assertEquals(srcCHF, rounding.adjust(tgt3));
		tgt = conv.convert(srcEUR,MoneyCurrency.getInstance("CHF"));
		tgt2 = conv.convert(100.15d, MoneyCurrency.getInstance("EUR"), MoneyCurrency.getInstance("CHF"));
		tgt3 = conv.convert(tgt, MoneyCurrency.getInstance("EUR"));
		assertEquals(tgt, tgt2);
		assertEquals(srcEUR, rounding.adjust(tgt3));
		tgt = conv.convert(srcCHF,MoneyCurrency.getInstance("USD"));
		tgt2 = conv.convert(100.15d, MoneyCurrency.getInstance("CHF"), MoneyCurrency.getInstance("USD"));
		tgt3 = conv.convert(tgt2, MoneyCurrency.getInstance("CHF"));
		assertEquals(tgt, tgt2);
		assertEquals(srcCHF, rounding.adjust(tgt3));
		tgt = conv.convert(srcUSD,MoneyCurrency.getInstance("CHF"));
		tgt2 = conv.convert(100.15d, MoneyCurrency.getInstance("USD"), MoneyCurrency.getInstance("CHF"));
		tgt3 = conv.convert(tgt2, MoneyCurrency.getInstance("USD"));
		assertEquals(tgt, tgt2);
		assertEquals(srcUSD, rounding.adjust(tgt3));
	}

	@Test
	@Ignore
	public void testGettingParsers() {
		// Using parsers
		try {
			AmountParser parser = Monetary.getAmountParserFactory()
					.getAmountParser(LocalizationStyle.of(Locale.GERMANY));
			MonetaryAmount amount1 = parser.parse("CFH 123.45");

			StyleableAmountParser locParser = Monetary.getAmountParserFactory()
					.getLocalizableAmountParser();

			MonetaryAmount amount2 = locParser.parse("CFH 123.45",
					LocalizationStyle.of(Locale.CHINA));
		} catch (ParseException e) {
			logger.debug("Error", e);
		}
	}

	@Test
	@Ignore
	public void testGettingFormatters() {
		// Using formatters
		CurrencyUnit currency = Monetary.getCurrencyUnitProvider().get(
				"ISO4217", "CHF");
		MonetaryAmount amount = Monetary.getMonetaryAmountProvider().get(
				currency, 1.0d);
		try {
			AmountFormatter formatter = Monetary.getAmountFormatterFactory()
					.getAmountFormatter(LocalizationStyle.of(Locale.GERMANY));
			String formatted = formatter.format(amount);
			assertEquals(1.0d, amount.doubleValue(), 0);
			StyleableAmountFormatter locFormatter = Monetary
					.getAmountFormatterFactory()
					.getLocalizableAmountFormatter();

			String formatted2 = locFormatter.format(amount,
					LocalizationStyle.of(Locale.CHINA));
		} catch (Exception e) {
			logger.debug("Error", e);
		}
	}
}
