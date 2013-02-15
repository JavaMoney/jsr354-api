package net.java.javamoney.ri;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatter;
import javax.money.format.AmountParser;
import javax.money.format.StyleableAmountFormatter;
import javax.money.format.StyleableAmountParser;
import javax.money.format.common.ParseException;

import net.java.javamoney.ri.core.Money;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmokeTests {
	private static final Logger logger = LoggerFactory.getLogger(SmokeTests.class);

	@Test
	public void testCreateAmounts() {
		// Creating one
		CurrencyUnit currency = Monetary.getCurrencyUnitProvider().get(
				"ISO-4217", "CHF");
		MonetaryAmount amount1 = Monetary.getMonetaryAmountFactory().get(
				currency, 1.0d);
		MonetaryAmount amount2 = Monetary.getMonetaryAmountFactory().get(
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
		CurrencyUnit[] currencies = Monetary.getCurrencyUnitProvider().getAll(Locale.US);
		logger.debug("Currencies for US: " + Arrays.toString(currencies));
		currencies = Monetary.getCurrencyUnitProvider().getAll(Locale.CHINA);
		logger.debug("Currencies for CHINA: " + Arrays.toString(currencies));
		currencies = Monetary.getCurrencyUnitProvider().getAll(Locale.ROOT);
		logger.debug("Currencies for ROOT (undefined): " + Arrays.toString(currencies));
	}
	
	@Test
	public void testExchange(){
//		Monetary.getExchangeRateProvider(ConversionType.)
	}

	@Test
	@Ignore
	public void testGettingParsers() {
		// Using parsers
		try {
			AmountParser parser = Monetary.getAmountParserFactory()
					.getAmountParser(Locale.GERMANY);
			MonetaryAmount amount1 = parser.parse("CFH 123.45");

			StyleableAmountParser locParser = Monetary
					.getAmountParserFactory().getLocalizableAmountParser();

			MonetaryAmount amount2 = locParser
					.parse("CFH 123.45", Locale.CHINA);
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
		MonetaryAmount amount = Monetary.getMonetaryAmountFactory().get(
				currency, 1.0d);
		try {
			AmountFormatter formatter = Monetary.getAmountFormatterFactory()
					.getAmountFormatter(Locale.GERMANY);
			String formatted = formatter.format(amount);
			assertEquals(1.0d, amount.doubleValue(), 0);
			StyleableAmountFormatter locFormatter = Monetary
					.getAmountFormatterFactory()
					.getLocalizableAmountFormatter();

			String formatted2 = locFormatter.format(amount, Locale.CHINA);
		} catch (Exception e) {
			logger.debug("Error", e);
		}
	}

}
