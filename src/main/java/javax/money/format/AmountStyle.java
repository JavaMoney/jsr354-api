/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.money.MonetaryException;
import javax.money.MonetaryOperator;
import javax.money.spi.AmountStyleProviderSpi;
import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryAmountFormatProviderSpi;

/**
 * The {@link AmountStyle} defines how a {@link javax.money.MonetaryAmount} should be formatted and
 * is used within a {@link MonetaryAmountFormat}.
 * 
 * @see MonetaryAmountFormat
 * @author Anatole Tresch
 */
public final class AmountStyle implements Serializable{
	/** serialVersionUID. */
	private static final long serialVersionUID = -7744853434156071725L;
	/** The format pattern used. */
	private String pattern;
	/** The conversion applied before formatting/displaying (optional). */
	private MonetaryOperator displayConversion;
	/** The conversion applied after parsing (optional). */
	private MonetaryOperator parseConversion;
	/** The customized group sizes. */
	private int[] groupingSizes;
	/** The {@link CurrencyStyle} to be used, not {@code null}. */
	private CurrencyStyle currencyStyle;
	/** The target {@link Locale} this style is representing. */
	private Locale locale;
	/** The {@link AmountFormatSymbols} used for this style. */
	private AmountFormatSymbols symbols;

	/**
	 * Constructor.
	 * 
	 * @param builder
	 *            the {@link Builder} providing the data required.
	 */
	private AmountStyle(Builder builder) {
		Objects.requireNonNull(builder.pattern,
				"Format pattern required.");
		Objects.requireNonNull(builder.symbols,
				"Symbols required.");
		Objects.requireNonNull(builder.currencyStyle,
				"currencyFormat required.");
		Objects.requireNonNull(builder.locale, "Locale required.");
		this.groupingSizes = builder.groupingSizes;
		this.displayConversion = builder.displayConversion;
		this.parseConversion = builder.parseConversion;
		this.pattern = builder.pattern;
		this.currencyStyle = builder.currencyStyle;
		this.locale = builder.locale;
		this.symbols = builder.symbols;
	}

	/**
	 * Get an {@link AmountStyle} given a {@link Locale}.
	 * 
	 * @param locale
	 *            the target {@link Locale}
	 * @return a corresponding {@link AmountStyle} instance, never {@code null}.
	 * @throws MonetaryException
	 *             if no registered {@link AmountStyleProviderSpi} can provide a matching instance.
	 */
	public static final AmountStyle of(Locale locale) {
		Objects.requireNonNull(locale, "Locale required.");
		for (AmountStyleProviderSpi spi : Bootstrap
				.getServices(
				AmountStyleProviderSpi.class)) {
			AmountStyle style = spi.getAmountStyle(locale);
			if (style != null) {
				return style;
			}
		}
		throw new MonetaryException(
				"No AmountStyle for locale "
						+ locale);
	}
	
	/**
	 * Get all available locales. For each {@link Locale} returned {@link #of(Locale)} will
	 * return an instance of {@link AmountStyle}.
	 * 
	 * @return all available locales, never {@code null}.
	 */
	public static final Set<Locale> getAvailableLocales() {
		Set<Locale> locales = new HashSet<>();
		for (AmountStyleProviderSpi spi : Bootstrap
				.getServices(
				AmountStyleProviderSpi.class)) {
			locales.addAll(spi.getSupportedLocales());
		}
		return locales;
	}
	
	/**
	 * Access the style's {@link Locale}.
	 * 
	 * @return the {@link Locale}, never {@code null}.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Access the style's pattern.
	 * 
	 * @return the style's pattern, never {@code null}.
	 */
	public String getPattern() {
		return this.pattern;
	}

	/**
	 * Access the style's pattern, localized with the values from {@link AmountFormatSymbols}.
	 * 
	 * @return the style's localized pattern, never {@code null}.
	 */
	public String getLocalizedPattern() {
		return this.pattern;
	}

	/**
	 * Access the {@link CurrencyStyle} to be used.
	 * 
	 * @return the format to be used.
	 */
	public CurrencyStyle getCurrencyStyle() {
		return this.currencyStyle;
	}

	/**
	 * Access the style's {@link AmountFormatSymbols}.
	 * 
	 * @return the style's {@link AmountFormatSymbols}, never {@code null}.
	 */
	public AmountFormatSymbols getSymbols() {
		return this.symbols;
	}

	/**
	 * Get the conversion applied before formatting.
	 * 
	 * @return the conversion used, or {@code null}.
	 */
	public MonetaryOperator getDisplayConversion() {
		return this.displayConversion;
	}

	/**
	 * Get the conversion applied after parsing.
	 * 
	 * @return the conversion used, or {@code null}.
	 */
	public MonetaryOperator getParseConversion() {
		return this.parseConversion;
	}

	/**
	 * Get the number groups sizes used, or an empty array if no custom sizes are configured.
	 * 
	 * @return the groupings sizes, never {@code null}.
	 */
	public int[] getGroupingSizes() {
		return this.groupingSizes.clone();
	}

	public Builder toBuilder(){
		return new Builder(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currencyStyle == null) ? 0 : currencyStyle.hashCode());
		result = prime * result
				+ ((locale == null) ? 0 : locale.hashCode());
		result = prime * result
				+ ((pattern == null) ? 0 : pattern.hashCode());
		result = prime * result + Arrays.hashCode(groupingSizes);
		result = prime
				* result
				+ ((displayConversion == null) ? 0 : displayConversion
						.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmountStyle other = (AmountStyle) obj;
		if (currencyStyle != other.currencyStyle)
			return false;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (!Arrays.equals(groupingSizes, other.groupingSizes))
			return false;
		if (displayConversion == null) {
			if (other.displayConversion != null)
				return false;
		} else if (!displayConversion.equals(other.displayConversion))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AmountStyle [locale=" + locale + ", formatPattern="
				+ pattern
				+ ", currencyStyle="
				+ currencyStyle
				+ ", rounding="
				+ displayConversion + ", groupSizes="
				+ Arrays.toString(groupingSizes)
				+ "]";
	}

	/**
	 * Builder for creating a new {@link AmountStyle}.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder {
		/** Default array for uncostimized group sizes. */
		private static final int[] EMPTY_INT_ARRAY = new int[0];
		/** The target {@link Locale} to be used. */
		private Locale locale;
		/** The underlying format pattern. */
		private String pattern;
		/** The formatting conversion operator, if any. */
		private MonetaryOperator displayConversion;
		/** The parse conversion operator, if any. */
		private MonetaryOperator parseConversion;
		/** The customized goup sizes, if any. */
		private int[] groupingSizes = EMPTY_INT_ARRAY;
		/** The symbols to be used, or {@code null}, for the defaults based on the {@link #locale}. */
		private AmountFormatSymbols symbols;
		/** The {@link CurrencyStyle} to be used, not {@code null}. */
		private CurrencyStyle currencyStyle = CurrencyStyle.CODE;

		/**
		 * Creates a new {@link Builder}.
		 * 
		 * @param style
		 *            the base {@link AmountStyle}, not {@code null}.
		 */
		public Builder(AmountStyle style) {
			Objects.requireNonNull(style, "style required.");
			this.locale = style.locale;
			this.symbols = style.symbols;
			this.currencyStyle = style.currencyStyle;
			this.displayConversion = style.displayConversion;
			this.groupingSizes = style.groupingSizes;
			this.parseConversion = style.parseConversion;
			this.pattern = style.pattern;
		}
		
		/**
		 * Creates a new {@link Builder}.
		 * 
		 * @param locale
		 *            the target {@link Locale}, not {@code null}.
		 */
		public Builder(Locale locale) {
			Objects.requireNonNull(locale, "Locale required.");
			this.locale = locale;
			this.symbols = AmountFormatSymbols.of(locale);
		}

		/**
		 * Sets the conversion to be applied before formatting.
		 * 
		 * @param conversion
		 *            the conversion.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setDisplayConversion(MonetaryOperator conversion) {
			this.displayConversion = conversion;
			return this;
		}

		/**
		 * Sets the {@link AmountFormatSymbols} to be used.
		 * 
		 * @param symbols
		 *            the {@link AmountFormatSymbols}, not null.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setSymbols(AmountFormatSymbols symbols) {
			Objects.requireNonNull(symbols);
			this.symbols = symbols;
			return this;
		}

		/**
		 * Sets the conversion to be applied after parsing.
		 * 
		 * @param conversion
		 *            the conversion.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setParseConversion(MonetaryOperator conversion) {
			this.parseConversion = conversion;
			return this;
		}

		/**
		 * Sets the customized number group sizes to be used for formatting. Hereby each value in
		 * the array represents a group size, starting from the decimal point and going up the
		 * significant digits. The last entry in the array is used as a default group size for all
		 * subsequent groupings.
		 * 
		 * @param groupSizes
		 *            the group sizes, not null.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setGroupingSizes(int... groupSizes) {
			Objects.requireNonNull(groupSizes, "groupSizes required.");
			this.groupingSizes = groupSizes;
			return this;
		}

		/**
		 * Set the {@link java.text.DecimalFormat} as defined by
		 * {@link java.text.DecimalFormat#getInstance(Locale)} by the given {@link Locale} .
		 * 
		 * @see java.text.DecimalFormat#getInstance(Locale)
		 * @param pattern
		 *            The (non localized) pattern to be used, not null.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setPattern(String pattern) {
			Objects.requireNonNull(pattern, "pattern required.");
			this.pattern = pattern;
			return this;
		}

		/**
		 * Sets the {@link AmountFormatSymbols}.
		 * 
		 * @param symbols
		 *            The target {@link AmountFormatSymbols}, not null.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder withSymbols(AmountFormatSymbols symbols) {
			Objects.requireNonNull(symbols, "symbols required.");
			this.symbols = symbols;
			return this;
		}

		/**
		 * Set the {@link CurrencyStyle} to be used for rendering the
		 * {@link javax.money.CurrencyUnit}.
		 * 
		 * @param currencyStyle
		 *            The {@link CurrencyStyle} to be used, not {@code null}.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setCurrencyStyle(CurrencyStyle currencyStyle) {
			Objects.requireNonNull(currencyStyle,
					"CurrencyStyle null.");
			this.currencyStyle = currencyStyle;
			return this;
		}

		/**
		 * Creates a new {@link AmountStyle}.
		 * 
		 * @return a new {@link AmountStyle} instance, never {@code null}.
		 * @throws IllegalStateException
		 *             if no {@link AmountStyle} could be created.
		 */
		public AmountStyle build() {
			return new AmountStyle(this);
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "FormatStyle.Builder [locale=" + locale + ", formatPattern="
					+ pattern
					+ ", currencyFormat=" + currencyStyle
					+ ", rounding="
					+ displayConversion
					+ ", groupSizes=" + Arrays.toString(groupingSizes)
					+ ", symbols=" + symbols + "]";
		}

	}

}
