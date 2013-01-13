package net.java.javamoney.ri;

import java.io.IOException;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatter;
import javax.money.format.AmountParser;
import javax.money.format.LocalizableAmountFormatter;
import javax.money.format.LocalizableAmountParser;
import javax.money.format.ParseException;

import org.junit.Ignore;
import org.junit.Test;

public class SmokeTests {

	@Test
	public void testCreateAmounts() {
		// Creating one
		CurrencyUnit currency = Monetary.getCurrencyUnitProvider().get(
				"ISO4217", "CHF");
		MonetaryAmount amount1 = Monetary.getMonetaryAmountFactory().get(
				currency, 1.0d);
		MonetaryAmount amount2 = Monetary.getMonetaryAmountFactory().get(
				currency, 1.0d);
		MonetaryAmount amount3 = amount1.add(amount2);
		System.out.print(amount1);
		System.out.print(" + ");
		System.out.print(amount2);
		System.out.print(" = ");
		System.out.println(amount3);
	}

	@Test
	@Ignore
	public void testGettingParsers() {
		// Using parsers
		try {
			AmountParser parser = Monetary.getAmountParserFactory()
					.getAmountParser(Locale.GERMANY);
			MonetaryAmount amount1 = parser.parse("CFH 123.45");

			LocalizableAmountParser locParser = Monetary
					.getAmountParserFactory().getLocalizableAmountParser();

			MonetaryAmount amount2 = locParser
					.parse("CFH 123.45", Locale.CHINA);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			String formatted = formatter.print(amount);
			LocalizableAmountFormatter locFormatter = Monetary
					.getAmountFormatterFactory()
					.getLocalizableAmountFormatter();

			String formatted2 = locFormatter.print(amount, Locale.CHINA);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
