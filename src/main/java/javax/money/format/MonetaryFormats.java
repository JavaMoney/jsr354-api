/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money.format;

import java.util.Locale;
import java.util.Objects;

import javax.money.CurrencyUnit;
import javax.money.MonetaryContext;
import javax.money.MonetaryException;
import javax.money.bootstrap.Bootstrap;
import javax.money.spi.MonetaryAmountFormatProviderSpi;

/**
 * This class models the singleton accessor for {@link MonetaryAmountFormat}
 * instances.
 * <p>
 * This class is thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryFormats {

	/**
	 * Private singleton constructor.
	 */
	private MonetaryFormats() {
		// Singleton
	}

	public static MonetaryAmountFormat getAmountFormat(Locale locale) {
		Objects.requireNonNull(locale, "Locale required.");
		for (MonetaryAmountFormatProviderSpi spi : Bootstrap
				.getServices(
						MonetaryAmountFormatProviderSpi.class)) {
			MonetaryAmountFormat f = spi.getFormat(locale, null, null);
			if (f != null) {
				return f;
			}
		}
		throw new MonetaryException("No MonetaryAmountFormat found for locale "
				+ locale);
	}

	public static MonetaryAmountFormat getAmountFormat(Locale locale,
			CurrencyUnit defaultCurrency) {
		Objects.requireNonNull(locale, "Locale required.");
		for (MonetaryAmountFormatProviderSpi spi : Bootstrap
				.getServices(
						MonetaryAmountFormatProviderSpi.class)) {
			MonetaryAmountFormat f = spi.getFormat(locale, null, defaultCurrency);
			if (f != null) {
				return f;
			}
		}
		throw new MonetaryException("No MonetaryAmountFormat found for locale="
				+ locale +", defaultCurrency=" + defaultCurrency);
	}

	public static MonetaryAmountFormat getAmountFormat(Locale locale,
			MonetaryContext monetaryContext) {
		Objects.requireNonNull(locale, "Locale required.");
		for (MonetaryAmountFormatProviderSpi spi : Bootstrap
				.getServices(
						MonetaryAmountFormatProviderSpi.class)) {
			MonetaryAmountFormat f = spi.getFormat(locale, monetaryContext, null);
			if (f != null) {
				return f;
			}
		}
		throw new MonetaryException("No MonetaryAmountFormat found for locale="
				+ locale +", monetaryContext=" + monetaryContext);
	}

	public static MonetaryAmountFormat getAmountFormat(Locale locale,
			MonetaryContext monetaryContext,
			CurrencyUnit defaultCurrency) {
		Objects.requireNonNull(locale, "Locale required.");
		for (MonetaryAmountFormatProviderSpi spi : Bootstrap
				.getServices(
						MonetaryAmountFormatProviderSpi.class)) {
			MonetaryAmountFormat f = spi.getFormat(locale, monetaryContext, defaultCurrency);
			if (f != null) {
				return f;
			}
		}
		throw new MonetaryException("No MonetaryAmountFormat found for locale="
				+ locale +", monetaryContext=" + monetaryContext+", defaultCurrency=" + defaultCurrency);
	}

	public static MonetaryAmountFormat getAmountFormat(AmountStyle style) {
		Objects.requireNonNull(style, "FormatStyle required.");
		for (MonetaryAmountFormatProviderSpi spi : Bootstrap
				.getServices(
						MonetaryAmountFormatProviderSpi.class)) {
			MonetaryAmountFormat f = spi.getFormat(style, null, null);
			if (f != null) {
				return f;
			}
		}
		throw new MonetaryException("No MonetaryAmountFormat found for style="
				+ style);
	}

	public static MonetaryAmountFormat getAmountFormat(
			AmountStyle style,
			CurrencyUnit defaultCurrency) {
		Objects.requireNonNull(style, "FormatStyle required.");
		for (MonetaryAmountFormatProviderSpi spi : Bootstrap
				.getServices(
						MonetaryAmountFormatProviderSpi.class)) {
			MonetaryAmountFormat f = spi.getFormat(style, null, defaultCurrency);
			if (f != null) {
				return f;
			}
		}
		throw new MonetaryException("No MonetaryAmountFormat found for style="
				+ style +", defaultCurrency=" + defaultCurrency);
	}

	public static MonetaryAmountFormat getAmountFormat(
			AmountStyle style,
			MonetaryContext monetaryContext) {
		Objects.requireNonNull(style, "FormatStyle required.");
		for (MonetaryAmountFormatProviderSpi spi : Bootstrap
				.getServices(
						MonetaryAmountFormatProviderSpi.class)) {
			MonetaryAmountFormat f = spi.getFormat(style, monetaryContext, null);
			if (f != null) {
				return f;
			}
		}
		throw new MonetaryException("No MonetaryAmountFormat found for style="
				+ style +", monetaryContext=" + monetaryContext);
	}

	public static MonetaryAmountFormat getAmountFormat(
			AmountStyle style,
			MonetaryContext monetaryContext,
			CurrencyUnit defaultCurrency) {
		Objects.requireNonNull(style, "FormatStyle required.");
		for (MonetaryAmountFormatProviderSpi spi : Bootstrap
				.getServices(
						MonetaryAmountFormatProviderSpi.class)) {
			MonetaryAmountFormat f = spi.getFormat(style, monetaryContext, defaultCurrency);
			if (f != null) {
				return f;
			}
		}
		throw new MonetaryException("No MonetaryAmountFormat found for style="
				+ style +", monetaryContext=" + monetaryContext+", defaultCurrency=" + defaultCurrency);
	}

}
