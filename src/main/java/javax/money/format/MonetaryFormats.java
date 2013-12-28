/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import java.util.Locale;
import java.util.Objects;

import javax.money.CurrencyUnit;
import javax.money.MonetaryContext;
import javax.money.MonetaryException;
import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryAmountFormatProviderSpi;

/**
 * This class models the singleton accessor for {@link MonetaryAmountFormat} instances.
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

	/**
	 * Access the default {@link MonetaryAmountFormat} given a {@link Locale}.
	 * 
	 * @param locale
	 *            the target {@link Locale}, not {@code null}.
	 * @return the matching {@link MonetaryAmountFormat}
	 * @throws MonetaryException
	 *             if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
	 *             corresponding {@link MonetaryAmountFormat} instance.
	 */
	public static MonetaryAmountFormat getDefaultFormat(Locale locale) {
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

	/**
	 * Builder for creating new instances of {@link MonetaryAmountFormat}.
	 */
	public static final class Builder {
		/** The {@link Locale} to be used, may be null, when an {@link AmountStyle} is set. */
		private Locale locale;
		/** The default {@link CurrencyUnit}, may be null. */
		private CurrencyUnit defaultCurrency;
		/** The required {@link MonetaryContext}, may be null. */
		private MonetaryContext monetaryContext;
		/** The {@link AmountStyle} to be used, may be null, when an {@link Locale} is set. */
		private AmountStyle style;

		/**
		 * Creates a new {@link Builder}, hereby the {@link AmountStyle} is determined by the
		 * {@link Locale} given.
		 * 
		 * @param locale
		 *            the target {@link Locale}.
		 */
		public Builder(Locale locale) {
			Objects.requireNonNull(locale, "Locale required.");
			this.locale = locale;
		}

		/**
		 * Creates a new {@link Builder}.
		 * 
		 * @param style
		 *            the {@link AmountStyle} required.
		 */
		public Builder(AmountStyle style) {
			Objects.requireNonNull(style, "style required.");
			this.style = style;
		}

		/**
		 * Sets the default {@link CurrencyUnit} to be used, when parsing amounts where no currency
		 * is available on the input.
		 * 
		 * @param defaultCurrency
		 *            the default {@link CurrencyUnit}
		 * @return the {@link Builder}, for chaining.
		 */
		public Builder with(CurrencyUnit defaultCurrency) {
			this.defaultCurrency = defaultCurrency;
			return this;
		}

		/**
		 * Sets the {@link MonetaryContext} that determines the amount implementation class returned
		 * from parsing.
		 * 
		 * @param monetaryContext
		 *            the {@link MonetaryContext} to be used, or {@code null} for using the default
		 *            amount type.
		 * @return the {@link Builder}, for chaining.
		 * @see javax.money.MonetaryAmounts#queryAmountType(MonetaryContext)
		 * @see javax.money.MonetaryAmounts#getDefaultAmountType()
		 * @see javax.money.MonetaryAmounts#getDefaultAmountFactory()
		 */
		public Builder with(MonetaryContext monetaryContext) {
			this.monetaryContext = monetaryContext;
			return this;
		}

		/**
		 * Access a new {@link MonetaryAmountFormat}, matching the properties set.
		 * 
		 * @return a new {@link MonetaryAmountFormat} instance, never {@code null}.
		 * @throws MonetaryException
		 *             if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
		 *             corresponding {@link MonetaryAmountFormat} instance.
		 */
		public MonetaryAmountFormat create() {
			for (MonetaryAmountFormatProviderSpi spi : Bootstrap
					.getServices(
					MonetaryAmountFormatProviderSpi.class)) {
				MonetaryAmountFormat f = null;
				if (style == null) {
					f = spi.getFormat(locale, monetaryContext, defaultCurrency);
				}
				else {
					f = spi.getFormat(style, monetaryContext, defaultCurrency);
				}
				if (f != null) {
					return f;
				}
			}
			throw new MonetaryException(
					"No MonetaryAmountFormat found for locale="
							+ locale + ", defaultCurrency=" + defaultCurrency
							+ ", monetaryContext=" + monetaryContext
							+ ", amountStyle=" + style);
		}
	}

}
