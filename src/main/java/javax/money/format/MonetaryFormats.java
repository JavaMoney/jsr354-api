/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryContext;
import javax.money.MonetaryException;
import javax.money.MonetaryOperator;
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
	public static MonetaryAmountFormat getAmountFormat(Locale locale) {
		Objects.requireNonNull(locale, "Locale required");
		for (MonetaryAmountFormatProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountFormatProviderSpi.class)) {
			MonetaryAmountFormat f = spi.getFormat(AmountStyle
					.of(locale));
			if (f != null) {
				return f;
			}
		}
		throw new MonetaryException("No MonetaryAmountFormat for locale "
				+ locale);
	}

	/**
	 * Access an {@link MonetaryAmountFormat} given a {@link AmountStyle}.
	 * 
	 * @param style
	 *            the target {@link AmountStyle}, not {@code null}.
	 * @return the corresponding {@link MonetaryAmountFormat}
	 * @throws MonetaryException
	 *             if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
	 *             corresponding {@link MonetaryAmountFormat} instance.
	 */
	public static MonetaryAmountFormat getAmountFormat(AmountStyle style) {
		Objects.requireNonNull(style, "AmountStyle required");
		for (MonetaryAmountFormatProviderSpi spi : Bootstrap
				.getServices(
				MonetaryAmountFormatProviderSpi.class)) {
			MonetaryAmountFormat f = spi.getFormat(style);
			if (f != null) {
				return f;
			}
		}
		throw new MonetaryException("No MonetaryAmountFormat for style "
				+ style);
	}

	/**
	 * Get all available locales. This equals to {@link AmountStyle#getAvailableLocales()}.
	 * 
	 * @return all available locales, never {@code null}.
	 */
	public static final Set<Locale> getAvailableLocales() {
		return AmountStyle.getAvailableLocales();
	}

//	/**
//	 * Builder for creating new instances of {@link MonetaryAmountFormat}.
//	 */
//	public static final class Builder {
//		/** The default {@link CurrencyUnit}, may be null. */
//		private CurrencyUnit defaultCurrency;
//		/** The required {@link MonetaryContext}, may be null. */
//		private MonetaryContext monetaryContext;
//		/** The {@link AmountStyle} to be used, may be null, when an {@link Locale} is set. */
//		private AmountStyle.Builder styleBuilder;
//		/** The format's name (optional). */
//		private String name;
//
//		/**
//		 * Creates a new {@link Builder}, hereby the {@link AmountStyle} is determined by the
//		 * {@link Locale} given.
//		 * 
//		 * @param locale
//		 *            the target {@link Locale}.
//		 */
//		public Builder(Locale locale) {
//			Objects.requireNonNull(locale, "Locale required.");
//			this.styleBuilder = new AmountStyle.Builder(locale);
//		}
//
//		/**
//		 * Sets the format's name.
//		 * 
//		 * @param name
//		 *            the name, not null.
//		 * @return the {@link Builder}, for chaining.
//		 */
//		public Builder setName(String name) {
//			this.name = name;
//			return this;
//		}
//
//		/**
//		 * Sets the default {@link CurrencyUnit} to be used, when parsing amounts where no currency
//		 * is available on the input.
//		 * 
//		 * @param defaultCurrency
//		 *            the default {@link CurrencyUnit}
//		 * @return the {@link Builder}, for chaining.
//		 */
//		public Builder setDefaultCurrency(CurrencyUnit defaultCurrency) {
//			this.defaultCurrency = defaultCurrency;
//			return this;
//		}
//
//		/**
//		 * Sets the {@link CurrencyStyle} to be used.
//		 * 
//		 * @param currencyFormat
//		 *            the {@link CurrencyStyle}, not null.
//		 * @return the {@link Builder}, for chaining.
//		 */
//		public Builder setCurrencyStyle(CurrencyStyle currencyStyle) {
//			this.styleBuilder.setCurrencyStyle(currencyStyle);
//			return this;
//		}
//
//		/**
//		 * Sets the {@link CurrencyStyle} to be used.
//		 * 
//		 * @param currencyFormat
//		 *            the {@link CurrencyStyle}, not null.
//		 * @return the {@link Builder}, for chaining.
//		 */
//		public Builder setAmountStyle(AmountStyle style) {
//			this.styleBuilder.setCurrencyStyle(style.getCurrencyStyle());
//			this.styleBuilder
//					.setDisplayConversion(style.getDisplayConversion());
//			this.styleBuilder.setGroupingSizes(style.getGroupingSizes());
//			this.styleBuilder.setParseConversion(style.getParseConversion());
//			this.styleBuilder.setPattern(style.getPattern());
//			this.styleBuilder.setSymbols(style.getSymbols());
//			return this;
//		}
//
//		/**
//		 * Sets the {@link MonetaryOperator} to be used as display conversion before formatting the
//		 * amount.
//		 * 
//		 * @param conversion
//		 *            the {@link MonetaryOperator}, or null.
//		 * @return the {@link Builder}, for chaining.
//		 */
//		public Builder setDisplayConversion(MonetaryOperator conversion) {
//			this.styleBuilder.setDisplayConversion(conversion);
//			return this;
//		}
//
//		/**
//		 * Sets the {@link MonetaryOperator} to be used as parse conversion after parsing the
//		 * amount.
//		 * 
//		 * @param conversion
//		 *            the {@link MonetaryOperator}, or null.
//		 * @return the {@link Builder}, for chaining.
//		 */
//		public Builder setParseConversion(MonetaryOperator conversion) {
//			this.styleBuilder.setParseConversion(conversion);
//			return this;
//		}
//
//		/**
//		 * Sets the customized number group sizes to be used for formatting. Hereby each value in
//		 * the array represents a group size, starting from the decimal point and going up the
//		 * significant digits. The last entry in the array is used as a default group size for all
//		 * subsequent groupings.
//		 * 
//		 * @param groupSizes
//		 *            the group sizes, not null.
//		 * @return the {@link Builder}, for chaining.
//		 */
//		public Builder setNumberGroupSizes(int... groupSizes) {
//			this.styleBuilder.setGroupingSizes(groupSizes);
//			return this;
//		}
//
//		/**
//		 * Sets the formats pattern, similar as in {@link java.text.DecimalFormat}.
//		 * 
//		 * @param pattern
//		 *            the pattern, not null.
//		 * @return the {@link Builder}, for chaining.
//		 */
//		public Builder setPattern(String pattern) {
//			this.styleBuilder.setPattern(pattern);
//			return this;
//		}
//
//		/**
//		 * Sets the formats {@link AmountFormatSymbols}, similar as in
//		 * {@link java.text.DecimalFormatSymbols}.
//		 * 
//		 * @param symbols
//		 *            the symbols, not null.
//		 * @return the {@link Builder}, for chaining.
//		 */
//		public Builder setSymbols(AmountFormatSymbols symbols) {
//			this.styleBuilder.setSymbols(symbols);
//			return this;
//		}
//
//		/**
//		 * Sets the {@link MonetaryContext} that determines the amount implementation class returned
//		 * from parsing.
//		 * 
//		 * @param monetaryContext
//		 *            the {@link MonetaryContext} to be used, or {@code null} for using the default
//		 *            amount type.
//		 * @return the {@link Builder}, for chaining.
//		 * @see javax.money.MonetaryAmounts#queryAmountType(MonetaryContext)
//		 * @see javax.money.MonetaryAmounts#getDefaultAmountType()
//		 * @see javax.money.MonetaryAmounts#getDefaultAmountFactory()
//		 */
//		public Builder setMonetaryContext(MonetaryContext monetaryContext) {
//			this.monetaryContext = monetaryContext;
//			return this;
//		}
//
//		/**
//		 * Access a new {@link MonetaryAmountFormat}, matching the properties set.
//		 * 
//		 * @return a new {@link MonetaryAmountFormat} instance, never {@code null}.
//		 * @throws MonetaryException
//		 *             if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
//		 *             corresponding {@link MonetaryAmountFormat} instance.
//		 */
//		public MonetaryAmountFormat create() {
//			AmountStyle style = styleBuilder.build();
//			for (MonetaryAmountFormatProviderSpi spi : Bootstrap
//					.getServices(
//					MonetaryAmountFormatProviderSpi.class)) {
//				MonetaryAmountFormat f = spi.getFormat(style);
//				if (f != null) {
//					f.setMonetaryContext(monetaryContext);
//					f.setDefaultCurrency(defaultCurrency);
//					return f;
//				}
//			}
//			throw new MonetaryException(
//					"No MonetaryAmountFormat found for amountStyle=" + style
//							+ ", defaultCurrency=" + defaultCurrency
//							+ ", monetaryContext=" + monetaryContext);
//		}
//	}

}
