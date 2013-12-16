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

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import javax.money.MonetaryOperator;

/**
 * The {@link AmountStyle} defines how a {@link MonetaryAmount} should be
 * formatted.
 * 
 * @author Anatole Tresch
 */
public final class AmountStyle {
	/** Default array for uncostimized group characters. */
	private static final char[] EMPTY_CHAR_ARRAY = new char[0];
	/** Default array for uncostimized group sizes. */
	private static final int[] EMPTY_INT_ARRAY = new int[0];
	/** The {@link DecimalFormat} used. */
	private DecimalFormat format;
	/** The conversion applied before formatting (optional). */
	private MonetaryOperator formatConversion;
	/** The conversion applied after parsing (optional). */
	private MonetaryOperator parseConversion;
	/** The customized group sizes. */
	private int[] groupSizes;
	/** The customized group characters. */
	private char[] groupChars;
	/** The {@link CurrencyStyle} to be used, not {@code null}. */
	private CurrencyStyle currencyStyle;
	/** The {@link CurrencyPlacement} to be used, not {@code null}. */
	private CurrencyPlacement currencyPlacement;
	/** The {@link CurrencyPlacement} to be used, not {@code null}. */
	private String currencySeparator;
	/** The target {@link Locale} this style is representing. */
	private Locale locale;

	/**
	 * Constructor.
	 * 
	 * @param locale
	 *            The locale
	 * @param format
	 *            The {@link DecimalFormat} used.
	 * @param groupSizes
	 *            the customized group sizes.
	 * @param groupChars
	 *            the customized group characters.
	 * @param formatConversion
	 *            the conversion {@link MonetaryOperator} done before formatting
	 *            the amount.
	 * @param currencyStyle
	 *            the {@link CurrencyStyle} to be applied.
	 * @param parseConversion
	 *            the conversion {@link MonetaryOperator} done after parsing the
	 *            amount.
	 * @param currencyPlacement
	 *            THe {@link CurrencyPlacement}
	 * @param currencySeparator
	 *            the currency separating String.
	 */
	private AmountStyle(Locale locale, DecimalFormat format, int[] groupSizes,
			char[] groupChars, MonetaryOperator formatConversion,
			MonetaryOperator parseConversion,
			CurrencyStyle currencyStyle, CurrencyPlacement currencyPlacement,
			String currencySeparator) {
		this.groupSizes = groupSizes;
		this.groupChars = groupChars;
		this.formatConversion = formatConversion;
		this.parseConversion = parseConversion;
		this.format = format;
		this.currencyPlacement = currencyPlacement;
		this.currencyStyle = currencyStyle;
		this.currencySeparator = currencySeparator;
		this.locale = locale;
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
	 * Access the style's {@link DecimalFormat}.
	 * 
	 * @return the style's {@link DecimalFormat}, never {@code null}.
	 */
	public DecimalFormat getDecimalFormat() {
		return this.format;
	}

	/**
	 * Get the conversion applied before formatting.
	 * 
	 * @return the conversion used, or {@code null}.
	 */
	public MonetaryOperator getFormatConversion() {
		return this.formatConversion;
	}

	/**
	 * Get the conversion applied after parsing.
	 * 
	 * @return the conversion used, or {@code null}.
	 */
	public MonetaryOperator getParseConversion() {
		return this.formatConversion;
	}

	/**
	 * Get the number groups sizes used, or an empty array if no custom sizes
	 * are configured.
	 * 
	 * @return the groupings sizes, never {@code null}.
	 */
	public int[] getNumberGroupSizes() {
		if (this.groupSizes == null) {
			return EMPTY_INT_ARRAY;
		}
		return this.groupSizes.clone();
	}

	/**
	 * Get the number groups chars used, or an empty array if no custom chars
	 * are configured.
	 * 
	 * @return the groupings chars, never null.
	 */
	public char[] getNumberGroupChars() {
		if (this.groupChars == null) {
			return EMPTY_CHAR_ARRAY;
		}
		return this.groupChars.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((currencyPlacement == null) ? 0 : currencyPlacement
						.hashCode());
		result = prime
				* result
				+ ((currencySeparator == null) ? 0 : currencySeparator
						.hashCode());
		result = prime * result
				+ ((currencyStyle == null) ? 0 : currencyStyle.hashCode());
		result = prime * result
				+ ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + Arrays.hashCode(groupChars);
		result = prime * result + Arrays.hashCode(groupSizes);
		result = prime
				* result
				+ ((formatConversion == null) ? 0 : formatConversion.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (currencyPlacement != other.currencyPlacement)
			return false;
		if (currencySeparator == null) {
			if (other.currencySeparator != null)
				return false;
		} else if (!currencySeparator.equals(other.currencySeparator))
			return false;
		if (currencyStyle != other.currencyStyle)
			return false;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (!Arrays.equals(groupChars, other.groupChars))
			return false;
		if (!Arrays.equals(groupSizes, other.groupSizes))
			return false;
		if (formatConversion == null) {
			if (other.formatConversion != null)
				return false;
		} else if (!formatConversion.equals(other.formatConversion))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FormatStyle [locale=" + locale + ", format=" + format
				+ ", currencyStyle="
				+ currencyStyle + ", currencyPlacement=" + currencyPlacement
				+ ", currencySeparator=" + currencySeparator + ", rounding="
				+ formatConversion + ", groupSizes="
				+ Arrays.toString(groupSizes)
				+ ", groupChars=" + Arrays.toString(groupChars) + "]";
	}

	/**
	 * Builder for creating a new {@link AmountStyle}.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder {
		/** The target {@link Locale} to be used. */
		private Locale locale;
		/** The underlying {@link DecimalFormat}. */
		private DecimalFormat format;
		/** The formatting conversion operator, if any. */
		private MonetaryOperator formatConversion;
		/** The parse conversion operator, if any. */
		private MonetaryOperator parseConversion;
		/** The customized goup sizes, if any. */
		private int[] groupSizes;
		/** The customized group characters, if any. */
		private char[] groupChars;
		/** The {@link CurrencyStyle} to be used, not {@code null}. */
		private CurrencyStyle currencyStyle = CurrencyStyle.CODE;
		/** The {@link CurrencyPlacement} to be used, not {@code null}. */
		private CurrencyPlacement currencyPlacement = CurrencyPlacement.BEFORE;
		/** The {@link CurrencyPlacement} to be used, not {@code null}. */
		private String currencySeparator = " ";

		/**
		 * Creates a new {@link Builder}.
		 * 
		 * @param locale
		 *            the target {@link Locale}, not {@code null}.
		 */
		public Builder(Locale locale) {
			Objects.requireNonNull(locale, "Locale required.");
			this.format = (DecimalFormat) DecimalFormat.getInstance(locale);
			this.locale = locale;
		}

		/**
		 * Allows to adapt the {@link Locale}.
		 * 
		 * @param locale
		 *            the locale, not null.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setLocale(Locale locale) {
			Objects.requireNonNull(locale, "Locale required.");
			this.locale = locale;
			return this;
		}

		/**
		 * Sets the conversion to be applied before formatting.
		 * 
		 * @param conversion
		 *            the conversion.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setFormatConversion(MonetaryOperator conversion) {
			this.formatConversion = conversion;
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
		 * Sets the customized number group sizes to be used for formatting.
		 * Hereby each value in the array represents a group size, starting from
		 * the decimal point and going up the significant digits. The last entry
		 * in the array is used as a default group size for all subsequent
		 * groupings.
		 * 
		 * @param groupSizes
		 *            the group sizes, not null.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setNumberGroupSizes(int... groupSizes) {
			Objects.requireNonNull(groupSizes, "groupSizes required.");
			this.groupSizes = groupSizes;
			return this;
		}

		/**
		 * Sets the customized number group characters to be used for
		 * formatting. Hereby each value in the array represents a group
		 * character for a group, starting from the decimal point and going up
		 * the significant digits. The last entry in the array is used as a
		 * default group character for all subsequent groupings.
		 * 
		 * @param groupChars
		 *            the group characters, not null.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setNumberGroupChars(char... groupChars) {
			Objects.requireNonNull(groupChars, "groupChars required.");
			this.groupChars = groupChars;
			return this;
		}

		/**
		 * Set the {@link DecimalFormat} as defined by
		 * {@link DecimalFormat#getInstance(Locale)} by the given {@link Locale}
		 * .
		 * 
		 * @see {@link DecimalFormat#getInstance(Locale)}
		 * @param locale
		 *            The target {@link Locale}, not null.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setDecimalFormat(Locale locale) {
			Objects.requireNonNull(locale, "Locale required.");
			this.format = (DecimalFormat) DecimalFormat.getInstance(locale);
			return this;
		}

		/**
		 * Set the separating String between the numeric part of an amount and
		 * the {@link javax.money.CurrencyUnit} rendered.
		 * 
		 * @param currencySeparator
		 *            The currency separator, not {@code null} (but may be
		 *            empty).
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setCurrencySeparator(String currencySeparator) {
			Objects.requireNonNull(currencySeparator,
					"currencySeparator must not be null.");
			this.currencySeparator = currencySeparator;
			return this;
		}

		/**
		 * Set the {@link CurrencyStyle} to be used for renderering the
		 * {@link javax.money.CurrencyUnit}.
		 * 
		 * @param currencyStyle
		 *            The CurrencyStyle to be used, not {@code null} (but may be
		 *            {@link CurrencyStyle#OMIT}).
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setCurrencyStyle(CurrencyStyle currencyStyle) {
			Objects.requireNonNull(currencyStyle,
					"CurrencyStyle must not be null.");
			this.currencyStyle = currencyStyle;
			return this;
		}

		/**
		 * Set the {@link CurrencyStyle} to be used for renderering the
		 * {@link javax.money.CurrencyUnit}.
		 * 
		 * @param currencyPlacement
		 *            The {@link CurrencyPlacement} to be used, not {@code null}
		 *            . If {@link CurrencyStyle#OMIT} is the current
		 *            {@code currencyStyle}, this setting is ignored..
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setCurrencyPlacement(CurrencyPlacement currencyPlacement) {
			Objects.requireNonNull(currencyPlacement,
					"CurrencyPlacement must not be null.");
			this.currencyPlacement = currencyPlacement;
			return this;
		}

		/**
		 * Creates a new {@link AmountStyle}.
		 * 
		 * @return a new {@link AmountStyle} instance, never {@code null}.
		 * @throws IllegalStateException
		 *             if no {@link DecimalFormat} could be applied.
		 */
		public AmountStyle build() {
			Objects.requireNonNull(format, "DecimalFormat required.");
			Objects.requireNonNull(locale, "Locale required.");
			return new AmountStyle(locale, format, groupSizes, groupChars,
					formatConversion, parseConversion,
					currencyStyle, currencyPlacement, currencySeparator);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "FormatStyle.Builder [locale=" + locale + ", format="
					+ format.toPattern()
					+ ", currencyStyle=" + currencyStyle
					+ ", currencyPlacement="
					+ currencyPlacement + ", currencySeparator="
					+ currencySeparator + ", rounding="
					+ formatConversion
					+ ", groupSizes=" + Arrays.toString(groupSizes)
					+ ", groupChars=" + Arrays.toString(groupChars) + "]";
		}

	}

}
