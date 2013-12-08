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

import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

/**
 * The amount style defines how a {@link MonetaryAmount} should be formatted.
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
	/** THe rounding used (optional). */
	private MonetaryOperator rounding;
	/** The customized group sizes. */
	private int[] groupSizes;
	/** The customized group characters. */
	private char[] groupChars;

	/**
	 * Constructor.
	 * 
	 * @param format
	 *            The {@link DecimalFormat} used.
	 * @param groupSizes
	 *            the customized group sizes.
	 * @param groupChars
	 *            the customized group characters.
	 * @param rounding
	 *            the custom rounding.
	 */
	private AmountStyle(DecimalFormat format, int[] groupSizes,
			char[] groupChars, MonetaryOperator rounding) {
		this.groupSizes = groupSizes;
		this.groupChars = groupChars;
		this.rounding = rounding;
		this.format = format;
	}

	/**
	 * Get the rounding used.
	 * 
	 * @return the rounding used, or null.
	 */
	public MonetaryOperator getMoneyRounding() {
		return this.rounding;
	}

	/**
	 * Get the number groups sizes used, or an empty array if no custom sizes
	 * are configured.
	 * 
	 * @return the groupings sizes, never null.
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



	/**
	 * Builder for creating a new {@link AmountStyle}
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder {
		/** The underlying {@link DecimalFormat}. */
		private DecimalFormat format;
		/** The rounding operator, if any. */
		private MonetaryOperator rounding;
		/** The customized goup sizes, if any. */
		private int[] groupSizes;
		/** The customized group characters, if any. */
		private char[] groupChars;

		/**
		 * Creates a new {@link Builder}.
		 * 
		 * @param locale
		 *            the target {@link Locale}, not {@code null}.
		 */
		public Builder(Locale locale) {
			if (locale == null) {
				throw new IllegalArgumentException("Locale required.");
			}
			this.format = (DecimalFormat) DecimalFormat.getInstance(locale);
		}

		/**
		 * Sets the rounding to be used for formatting.
		 * 
		 * @param rounding
		 *            the rounding, not null.
		 * @return the {@link Builder} for chaining.
		 */
		public Builder setRounding(MonetaryOperator rounding) {
			this.rounding = rounding;
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
			if (locale == null) {
				throw new IllegalArgumentException("locale required.");
			}
			this.format = (DecimalFormat) DecimalFormat.getInstance(locale);
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
			if (format == null) {
				throw new IllegalStateException("DecimalFormat required.");
			}
			return new AmountStyle(format, groupSizes, groupChars, rounding);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "AmountStyle.Builder [format=" + format + ", rounding="
					+ rounding
					+ ", groupSizes=" + Arrays.toString(groupSizes)
					+ ", groupChars=" + Arrays.toString(groupChars) + "]";
		}

	}
}
