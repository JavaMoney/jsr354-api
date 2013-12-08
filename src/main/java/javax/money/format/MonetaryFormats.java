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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.CurrencyUnit;
import javax.money.MonetaryContext;
import javax.money.spi.MonetaryAmountFormatProviderSpi;

/**
 * This class models the accessor for monetary formats, modeled by
 * {@link MonetaryAmountFormat}.
 * <p>
 * This class is thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryFormats {

	/** Currently loaded SPIs. */
	private static MonetaryAmountFormatProviderSpi providerSpi = loadSpi();

	/**
	 * Private singleton constructor.
	 */
	private MonetaryFormats() {
		// Singleton
	}

	private static MonetaryAmountFormatProviderSpi loadSpi() {
		try {
			List<MonetaryAmountFormatProviderSpi> providers = new ArrayList<>();
			for (MonetaryAmountFormatProviderSpi spi : ServiceLoader
					.load(MonetaryAmountFormatProviderSpi.class)) {
				providers.add(spi);
			}
			// Collections.sort(providers, new PriorityComparator());
			if (providers.isEmpty()) {
				return null;
			}
			return providers.get(0);
		} catch (Exception e) {
			Logger.getLogger(MonetaryFormats.class.getName()).log(
					Level.SEVERE,
					"Error loading MonetaryAmountFormatProviderSpi instances.",
					e);
			return null;
		}
	}

	public static MonetaryAmountFormat getAmountFormat(Locale locale) {
		Objects.requireNonNull(locale, "Locale required.");
		if (providerSpi == null) {
			throw new IllegalStateException(
					"No MonetaryAmountFormatProviderSpi registered.");
		}
		return providerSpi.getFormat(locale, null, null, null);
	}

	public static MonetaryAmountFormat getAmountFormat(Locale locale,
			CurrencyUnit defaultCurrency) {
		Objects.requireNonNull(locale, "Locale required.");
		if (providerSpi == null) {
			throw new IllegalStateException(
					"No MonetaryAmountFormatProviderSpi registered.");
		}
		return providerSpi.getFormat(locale, null, null, defaultCurrency);
	}

	public static MonetaryAmountFormat getAmountFormat(Locale locale,
			MonetaryContext monetaryContext) {
		Objects.requireNonNull(locale, "Locale required.");
		if (providerSpi == null) {
			throw new IllegalStateException(
					"No MonetaryAmountFormatProviderSpi registered.");
		}
		return providerSpi.getFormat(locale, null, monetaryContext, null);
	}

	public static MonetaryAmountFormat getAmountFormat(Locale locale,
			MonetaryContext monetaryContext,
			CurrencyUnit defaultCurrency) {
		Objects.requireNonNull(locale, "Locale required.");
		if (providerSpi == null) {
			throw new IllegalStateException(
					"No MonetaryAmountFormatProviderSpi registered.");
		}
		return providerSpi.getFormat(locale, null, monetaryContext,
				defaultCurrency);
	}

	public static MonetaryAmountFormat getAmountFormat(FormatStyle style) {
		Objects.requireNonNull(style, "FormatStyle required.");
		if (providerSpi == null) {
			throw new IllegalStateException(
					"No MonetaryAmountFormatProviderSpi registered.");
		}
		return providerSpi.getFormat(null, style, null, null);
	}

	public static MonetaryAmountFormat getAmountFormat(
			FormatStyle style,
			CurrencyUnit defaultCurrency) {
		Objects.requireNonNull(style, "FormatStyle required.");
		if (providerSpi == null) {
			throw new IllegalStateException(
					"No MonetaryAmountFormatProviderSpi registered.");
		}
		return providerSpi.getFormat(null, style, null, defaultCurrency);
	}

	public static MonetaryAmountFormat getAmountFormat(
			FormatStyle style,
			MonetaryContext monetaryContext) {
		Objects.requireNonNull(style, "FormatStyle required.");
		if (providerSpi == null) {
			throw new IllegalStateException(
					"No MonetaryAmountFormatProviderSpi registered.");
		}
		return providerSpi.getFormat(null, style, monetaryContext, null);
	}

	public static MonetaryAmountFormat getAmountFormat(
			FormatStyle style,
			MonetaryContext monetaryContext,
			CurrencyUnit defaultCurrency) {
		Objects.requireNonNull(style, "FormatStyle required.");
		if (providerSpi == null) {
			throw new IllegalStateException(
					"No MonetaryAmountFormatProviderSpi registered.");
		}
		return providerSpi.getFormat(null, style, monetaryContext,
				defaultCurrency);
	}

}
