/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money;


/**
 * Interface defining a monetary amount. The effective internal representation
 * of an amount may vary depending on the implementation used. Nevertheless
 * basically an amount provides a functionality similar to {@link BigDecimal}.
 * <p>
 * Since {@link Number} is not an interface this type is not extending
 * {@link Number}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface MonetaryAmount {

	/**
	 * Gets the currency.
	 * 
	 * @return the currency, never null
	 */
	public CurrencyUnit getCurrency();

	// -------------------- calculation methods

	/**
	 * Returns a {@code MonetaryAmount} whose value is the absolute value of
	 * this {@code MonetaryAmount}, and whose scale is
	 * {@code this.getgetScale()}.
	 * 
	 * @return {@code abs(this)}
	 */
	public MonetaryAmount abs();

	/**
	 * Returns the minimum of this {@code MonetaryAmount} and {@code amount}.
	 * 
	 * @param amount
	 *            value with which the minimum is to be computed.
	 * @return the {@code MonetaryAmount} whose value is the lesser of this
	 *         {@code MonetaryAmount} and {@code val}. If they are equal, as
	 *         defined by the {@link #compareTo(MonetaryAmount) compareTo}
	 *         method, {@code this} is returned.
	 * @see #compareTo(java.math.MonetaryAmount)
	 */
	public MonetaryAmount min(MonetaryAmount amount);

	/**
	 * Returns the maximum of this {@code MonetaryAmount} and {@code amount}.
	 * 
	 * @param amount
	 *            value with which the maximum is to be computed.
	 * @return the {@code MonetaryAmount} whose value is the greater of this
	 *         {@code MonetaryAmount} and {@code val}. If they are equal, as
	 *         defined by the {@link #compareTo(MonetaryAmount) compareTo}
	 *         method, {@code this} is returned.
	 * @see #compareTo(MonetaryAmount)
	 */
	public MonetaryAmount max(MonetaryAmount amount);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this +
	 * augend)}, and whose scale is {@code max(this.getScale(),
	 * augend.getScale())}.
	 * 
	 * @param augend
	 *            value to be added to this {@code MonetaryAmount}.
	 * @return {@code this + augend}
	 */
	public MonetaryAmount add(MonetaryAmount augend);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this +
	 * augend)}, and whose scale is {@code max(this.getScale(),
	 * augend.getScale())}.
	 * 
	 * @param augend
	 *            value to be added to this {@code MonetaryAmount}.
	 * @return {@code this + augend}
	 */
	public MonetaryAmount add(Number augend);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this /
	 * divisor)}, and whose preferred scale is {@code (this.getScale() -
	 * divisor.getScale())}; if the exact quotient cannot be represented
	 * (because it has a non-terminating decimal expansion) an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @throws ArithmeticException
	 *             if the exact quotient does not have a terminating decimal
	 *             expansion
	 * @return {@code this / divisor}
	 */
	public MonetaryAmount divide(MonetaryAmount divisor);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this /
	 * divisor)}, and whose preferred scale is {@code (this.getScale() -
	 * divisor.getScale())}; if the exact quotient cannot be represented
	 * (because it has a non-terminating decimal expansion) an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @throws ArithmeticException
	 *             if the exact quotient does not have a terminating decimal
	 *             expansion
	 * @return {@code this / divisor}
	 */
	public MonetaryAmount divide(Number divisor);

	/**
	 * Returns a two-element {@code MonetaryAmount} array containing the result
	 * of {@code divideToIntegralValue} followed by the result of
	 * {@code remainder} on the two operands.
	 * 
	 * <p>
	 * Note that if both the integer quotient and remainder are needed, this
	 * method is faster than using the {@code divideToIntegralValue} and
	 * {@code remainder} methods separately because the division need only be
	 * carried out once.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided,
	 *            and the remainder computed.
	 * @return a two element {@code MonetaryAmount} array: the quotient (the
	 *         result of {@code divideToIntegralValue}) is the initial element
	 *         and the remainder is the final element.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 * @see #divideToIntegralValue(MonetaryAmount)
	 * @see #remainder(MonetaryAmount)
	 */
	public MonetaryAmount[] divideAndRemainder(MonetaryAmount divisor);

	/**
	 * Returns a two-element {@code MonetaryAmount} array containing the result
	 * of {@code divideToIntegralValue} followed by the result of
	 * {@code remainder} on the two operands.
	 * 
	 * <p>
	 * Note that if both the integer quotient and remainder are needed, this
	 * method is faster than using the {@code divideToIntegralValue} and
	 * {@code remainder} methods separately because the division need only be
	 * carried out once.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided,
	 *            and the remainder computed.
	 * @return a two element {@code MonetaryAmount} array: the quotient (the
	 *         result of {@code divideToIntegralValue}) is the initial element
	 *         and the remainder is the final element.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 * @see #divideToIntegralValue(MonetaryAmount)
	 * @see #remainder(MonetaryAmount)
	 */
	public MonetaryAmount[] divideAndRemainder(Number divisor);

	/**
	 * Returns a {@code MonetaryAmount} whose value is the integer part of the
	 * quotient {@code (this / divisor)} rounded down. The preferred scale of
	 * the result is {@code (this.getScale() -
	 * divisor.getScale())}.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @return The integer part of {@code this / divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 */
	public MonetaryAmount divideToIntegralValue(MonetaryAmount divisor);

	/**
	 * Returns a {@code MonetaryAmount} whose value is the integer part of the
	 * quotient {@code (this / divisor)} rounded down. The preferred scale of
	 * the result is {@code (this.getScale() -
	 * divisor.getScale())}.
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @return The integer part of {@code this / divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 */
	public MonetaryAmount divideToIntegralValue(Number divisor);

	/**
	 * Returns a {@code MonetaryAmount} whose value is <tt>(this &times;
	 * multiplicand)</tt>, and whose scale is {@code (this.getScale() +
	 * multiplicand.getScale())}.
	 * 
	 * @param multiplicand
	 *            value to be multiplied by this {@code MonetaryAmount}.
	 * @return {@code this * multiplicand}
	 */
	public MonetaryAmount multiply(MonetaryAmount multiplicand);

	/**
	 * Returns a {@code MonetaryAmount} whose value is <tt>(this &times;
	 * multiplicand)</tt>, and whose scale is {@code (this.getScale() +
	 * multiplicand.getScale())}.
	 * 
	 * @param multiplicand
	 *            value to be multiplied by this {@code MonetaryAmount}.
	 * @return {@code this * multiplicand}
	 */
	public MonetaryAmount multiply(Number multiplicand);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (-this)}, and
	 * whose scale is {@code this.getScale()}.
	 * 
	 * @return {@code -this}.
	 */
	public MonetaryAmount negate();

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (+this)}, and
	 * whose scale is {@code this.getScale()}.
	 * 
	 * <p>
	 * This method, which simply returns this {@code MonetaryAmount} is included
	 * for symmetry with the unary minus method {@link #negate()}.
	 * 
	 * @return {@code this}.
	 * @see #negate()
	 */
	public MonetaryAmount plus();

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this -
	 * subtrahend)}, and whose scale is {@code max(this.getScale(),
	 * subtrahend.getScale())}.
	 * 
	 * @param subtrahend
	 *            value to be subtracted from this {@code MonetaryAmount}.
	 * @return {@code this - subtrahend}
	 */
	public MonetaryAmount subtract(MonetaryAmount subtrahend);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this -
	 * subtrahend)}, and whose scale is {@code max(this.getScale(),
	 * subtrahend.getScale())}.
	 * 
	 * @param subtrahend
	 *            value to be subtracted from this {@code MonetaryAmount}.
	 * @return {@code this - subtrahend}
	 */
	public MonetaryAmount subtract(Number subtrahend);

	/**
	 * Returns a {@code MonetaryAmount} whose value is
	 * <tt>(this<sup>n</sup>)</tt>, The power is computed exactly, to unlimited
	 * precision.
	 * 
	 * <p>
	 * The parameter {@code n} must be in the range 0 through 999999999,
	 * inclusive. {@code ZERO.pow(0)} returns {@link #ONE}.
	 * 
	 * Note that future releases may expand the allowable exponent range of this
	 * method.
	 * 
	 * @param n
	 *            power to raise this {@code MonetaryAmount} to.
	 * @return <tt>this<sup>n</sup></tt>
	 * @throws ArithmeticException
	 *             if {@code n} is out of range.
	 * @since 1.5
	 */
	public MonetaryAmount pow(int n);

	/**
	 * Returns the size of an ulp, a unit in the last place, of this
	 * {@code MonetaryAmount}. An ulp of a nonzero {@code MonetaryAmount} value
	 * is the positive distance between this value and the
	 * {@code MonetaryAmount} value next larger in magnitude with the same
	 * number of digits. An ulp of a zero value is numerically equal to 1 with
	 * the scale of {@code this}. The result is stored with the same scale as
	 * {@code this} so the result for zero and nonzero values is equal to
	 * {@code [1,
	 * this.getgetScale()]}.
	 * 
	 * @return the size of an ulp of {@code this}
	 */
	public MonetaryAmount ulp();

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this % divisor)}.
	 * 
	 * <p>
	 * The remainder is given by
	 * {@code this.subtract(this.divideToIntegralValue(divisor).multiply(divisor))}
	 * . Note that this is not the modulo operation (the result can be
	 * negative).
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @return {@code this % divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 */
	public MonetaryAmount remainder(MonetaryAmount divisor);

	/**
	 * Returns a {@code MonetaryAmount} whose value is {@code (this % divisor)}.
	 * 
	 * <p>
	 * The remainder is given by
	 * {@code this.subtract(this.divideToIntegralValue(divisor).multiply(divisor))}
	 * . Note that this is not the modulo operation (the result can be
	 * negative).
	 * 
	 * @param divisor
	 *            value by which this {@code MonetaryAmount} is to be divided.
	 * @return {@code this % divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 */
	public MonetaryAmount remainder(Number divisor);

	/**
	 * Returns a Amount whose numerical value is equal to ({@code this} *
	 * 10<sup>n</sup>). The scale of the result is {@code (this.getScale() - n)}
	 * .
	 * 
	 * @throws ArithmeticException
	 *             if the scale would be outside the range supported.
	 */
	public MonetaryAmount scaleByPowerOfTen(int n);

	/**
	 * Returns a copy of this amount adjusted by the {@link AmountAdjuster},
	 * e.g. a {@link Rounding}.
	 * <p>
	 * This instance is immutable and unaffected by this method.
	 * 
	 * @param adjusters
	 *            the adjusters to use, not null
	 * @return the adjusted instance, never null
	 * @throws ArithmeticException
	 *             if the adjustment fails
	 */
	public MonetaryAmount with(AmountAdjuster... adjusters);

	/**
	 * Gets the amount in major units as a {@code MonetaryAmount} with scale 0.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 2, and 'BHD -1.345' will return -1.
	 * <p>
	 * This is returned as a {@code MonetaryAmount} rather than a
	 * {@code BigInteger} . This is to allow further calculations to be
	 * performed on the result. Should you need a {@code BigInteger}, simply
	 * call {@link BigDecimal#toBigInteger()}.
	 * 
	 * @return the major units part of the amount, never null
	 */
	public MonetaryAmount getMajorPart();

	/**
	 * Gets the amount in major units as a {@code long}.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 2, and 'BHD -1.345' will return -1.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the major units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for a {@code long}
	 */
	public long getMajorLong();

	/**
	 * Gets the amount in major units as an {@code int}.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 2, and 'BHD -1.345' will return -1.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the major units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for an {@code int}
	 */
	public int getMajorInt();

	/**
	 * Gets the amount in minor units as a {@code MonetaryAmount} with scale 0.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 235, and 'BHD -1.345' will return -1345.
	 * <p>
	 * This is returned as a {@code MonetaryAmount} rather than a
	 * {@code BigInteger} . This is to allow further calculations to be
	 * performed on the result. Should you need a {@code BigInteger}, simply
	 * call {@link BigDecimal#toBigInteger()}.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount, never null
	 */
	public MonetaryAmount getMinorPart();

	/**
	 * Gets the amount in minor units as a {@code long}.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 235, and 'BHD -1.345' will return -1345.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for a {@code long}
	 */
	public long getMinorLong();

	/**
	 * Gets the amount in minor units as an {@code int}.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 235, and 'BHD -1.345' will return -1345.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for an {@code int}
	 */
	public int getMinorInt();

	/**
	 * Checks if the amount is zero.
	 * 
	 * @return true if the amount is zero
	 */
	public boolean isZero();

	/**
	 * Checks if the amount is greater than zero.
	 * 
	 * @return true if the amount is greater than zero
	 */
	public boolean isPositive();

	/**
	 * Checks if the amount is zero or greater.
	 * 
	 * @return true if the amount is zero or greater
	 */
	public boolean isPositiveOrZero();

	/**
	 * Checks if the amount is less than zero.
	 * 
	 * @return true if the amount is less than zero
	 */
	public boolean isNegative();

	/**
	 * Checks if the amount is zero or less.
	 * 
	 * @return true if the amount is zero or less
	 */
	public boolean isNegativeOrZero();

	// -----------------------------------------------------------------------
	/**
	 * Returns a copy of this monetary value with the specified amount.
	 * <p>
	 * The returned instance will have this currency and the new amount. No
	 * rounding is performed on the amount to be added, so it must have a scale
	 * compatible with the currency.
	 * <p>
	 * This instance is immutable and unaffected by this method.
	 * 
	 * @param amount
	 *            the amount to set in the returned instance, not null
	 * @return the new instance with the input amount set, never null
	 * @throws ArithmeticException
	 *             if the scale of the amount is too large
	 */
	public MonetaryAmount with(Number amount);

	// -------------------- Introspection and value methods, similar to
	// java.lang.Number; java.lang.BigDecimal

	/**
	 * * Gets the scale of the amount.
	 * <p>
	 * The scale has the same meaning as in {@link java.math.BigDecimal}. Positive
	 * values represent the number of decimal places in use. For example, a
	 * scale of 2 means that the money will have two decimal places such as 'USD
	 * 43.25'.
	 * <p>
	 * For {@code MonetaryAmount}, the scale is fixed and always matches that of
	 * the currency.
	 * 
	 * @return the scale in use, typically 2 but could be 0, 1 and 3
	 */
	public int getScale();

	/**
	 * Returns the <i>internal precision</i> of this {@code MonetaryAmount}.
	 * (The precision is the number of digits in the unscaled value.)
	 * 
	 * <p>
	 * The precision of a zero value is 1.
	 * 
	 * @return the precision of this {@code MonetaryAmount}.
	 */
	public int getPrecision();

	/**
	 * Returns the value of the specified number as an <code>int</code>. This
	 * may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion to
	 *         type <code>int</code>.
	 */
	public int intValue();

	/**
	 * Converts this {@code MonetaryAmount} to an {@code int}, checking for lost
	 * information. If this {@code MonetaryAmount} has a nonzero fractional part
	 * or is out of the possible range for an {@code int} result then an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @return this {@code MonetaryAmount} converted to an {@code int}.
	 * @throws ArithmeticException
	 *             if {@code this} has a nonzero fractional part, or will not
	 *             fit in an {@code int}.
	 */
	public int intValueExact();

	/**
	 * Returns the value of the specified number as a <code>long</code>. This
	 * may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion to
	 *         type <code>long</code>.
	 */
	public long longValue();

	/**
	 * Converts this {@code MonetaryAmount} to a {@code long}, checking for lost
	 * information. If this {@code MonetaryAmount} has a nonzero fractional part
	 * or is out of the possible range for a {@code long} result then an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @return this {@code MonetaryAmount} converted to a {@code long}.
	 * @throws ArithmeticException
	 *             if {@code this} has a nonzero fractional part, or will not
	 *             fit in a {@code long}.
	 */
	public long longValueExact();

	/**
	 * Returns the value of the specified number as a <code>float</code>. This
	 * may involve rounding.
	 * 
	 * @return the numeric value represented by this object after conversion to
	 *         type <code>float</code>.
	 */
	public float floatValue();

	/**
	 * Returns the value of the specified number as a <code>double</code>. This
	 * may involve rounding.
	 * 
	 * @return the numeric value represented by this object after conversion to
	 *         type <code>double</code>.
	 */
	public double doubleValue();

	/**
	 * Returns the value of the specified number as a <code>byte</code>. This
	 * may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion to
	 *         type <code>byte</code>.
	 */
	public byte byteValue();

	/**
	 * Returns the value of the specified number as a <code>short</code>. This
	 * may involve rounding or truncation.
	 * 
	 * @return the numeric value represented by this object after conversion to
	 *         type <code>short</code>.
	 */
	public short shortValue();

	/**
	 * Converts this {@code MonetaryAmount} to a {@code short}, checking for
	 * lost information. If this {@code MonetaryAmount} has a nonzero fractional
	 * part or is out of the possible range for a {@code short} result then an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @return this {@code MonetaryAmount} converted to a {@code short}.
	 * @throws ArithmeticException
	 *             if {@code this} has a nonzero fractional part, or will not
	 *             fit in a {@code short} .
	 */
	public short shortValueExact();

	/**
	 * Returns the signum function of this {@code MonetaryAmount}.
	 * 
	 * @return -1, 0, or 1 as the value of this {@code MonetaryAmount} is
	 *         negative, zero, or positive.
	 */
	public int signum();

	/**
	 * Checks if this amount is less compared to the amount passed.
	 * 
	 * @param amount
	 *            The amount to compare to.
	 * @return TRUE, if this amount is less compared to the amount passed.
	 */
	public boolean lessThan(MonetaryAmount amount);

	/**
	 * Checks if this amount's value is less compared to the number passed.
	 * 
	 * @param number
	 *            The number to compare to.
	 * @return TRUE, if this amount's value is less compared to the number
	 *         passed.
	 */
	public boolean lessThan(Number number);

	/**
	 * Checks if this amount is less or the same compared to the amount passed.
	 * 
	 * @param amount
	 *            The amount to compare to.
	 * @return TRUE, if this amount is less or the same compared to the amount
	 *         passed.
	 */
	public boolean lessThanOrEqualTo(MonetaryAmount amount);

	/**
	 * Checks if this amount's value is less or the same compared to the number
	 * passed.
	 * 
	 * @param number
	 *            The number to compare to.
	 * @return TRUE, if this amount's value is less or the same compared to the
	 *         number passed.
	 */
	public boolean lessThanOrEqualTo(Number number);

	/**
	 * Checks if this amount is greater compared to the amount passed.
	 * 
	 * @param amount
	 *            The amount to compare to.
	 * @return TRUE, if this amount is greater compared to the amount passed.
	 */
	public boolean greaterThan(MonetaryAmount amount);

	/**
	 * Checks if this amount's value is greater compared to the number passed.
	 * 
	 * @param number
	 *            The number to compare to.
	 * @return TRUE, if this amount's value is greater compared to the number
	 *         passed.
	 */
	public boolean greaterThan(Number number);

	/**
	 * Checks if this amount is greater or the same compared to the amount
	 * passed.
	 * 
	 * @param amount
	 *            The amount to compare to.
	 * @return TRUE, if this amount is greater or the same compared to the
	 *         amount passed.
	 */
	public boolean greaterThanOrEqualTo(MonetaryAmount amount);

	/**
	 * Checks if this amount's value is greater or the same compared to the
	 * number passed.
	 * 
	 * @param number
	 *            The number to compare to.
	 * @return TRUE, if this amount's value is greater or the same compared to
	 *         the number passed.
	 */
	public boolean greaterThanOrEqualTo(Number number);

	/**
	 * Checks if this amount is the same compared to the amount passed. This is
	 * a convenience method to reflect {@link #same(Number)} also for amounts,
	 * but basically should behave similarly as {@link #equals(Object)}.
	 * 
	 * @param number
	 *            The number to compare to.
	 * @return TRUE, if this amount's value is the same compared to the number
	 *         passed.
	 */
	public boolean isEqualTo(MonetaryAmount amount);
	
	/**
	 * Allows to check, if the currency of the two amounts are the same. This
	 * means that corresponding currency's namespace and code must match.
	 * 
	 * @param amount
	 *            The amount to comapre to, not {@code null}.
	 * @return true, if the {@link CurrencyUnit} of this instance has the same
	 *         namespace and code.
	 */
	public boolean hasSameCurrencyAs(MonetaryAmount amount);

	/**
	 * Checks if this amount's value is the same compared to the number passed.
	 * 
	 * @param number
	 *            The number to compare to.
	 * @return TRUE, if this amount's value is the same compared to the number
	 *         passed.
	 */
	public boolean hasSameNumberAs(Number number);

	/**
	 * Checks if this amount is not the same compared to the amount passed.
	 * 
	 * @param amount
	 *            The amount to compare to.
	 * @return TRUE, if this amount's value is not the same compared to the
	 *         number passed.
	 */
	public boolean isNotEqualTo(MonetaryAmount amount);

	/**
	 * Checks if this amount's value is not the same compared to the number
	 * passed.
	 * 
	 * @param number
	 *            The number to compare to.
	 * @return TRUE, if this amount's value is not the same compared to the
	 *         number passed.
	 */
	public boolean isNotEqualTo(Number number);

	// -------------------------------------------- Misc

	/**
	 * Returns a string representation of this {@code MonetaryAmount}, using
	 * engineering notation if an exponent is needed.
	 * 
	 * <p>
	 * Returns a string that represents the {@code MonetaryAmount} as described
	 * in the {@link #toString()} method, except that if exponential notation is
	 * used, the power of ten is adjusted to be a multiple of three (engineering
	 * notation) such that the integer part of nonzero values will be in the
	 * range 1 through 999. If exponential notation is used for zero values, a
	 * decimal point and one or two fractional zero digits are used so that the
	 * scale of the zero value is preserved. Note that unlike the output of
	 * {@link #toString()}, the output of this method is <em>not</em> guaranteed
	 * to recover the same [integer, scale] pair of this {@code MonetaryAmount}
	 * if the output string is converting back to a {@code MonetaryAmount} using
	 * the {@linkplain #Amount(String) string constructor}. The result of this
	 * method meets the weaker constraint of always producing a numerically
	 * equal result from applying the string constructor to the method's output.
	 * 
	 * @return string representation of this {@code MonetaryAmount}, using
	 *         engineering notation if an exponent is needed.
	 */
	public String toEngineeringString();

	/**
	 * Returns a string representation of this {@code MonetaryAmount} without an
	 * exponent field. For values with a positive scale, the number of digits to
	 * the right of the decimal point is used to indicate scale. For values with
	 * a zero or negative scale, the resulting string is generated as if the
	 * value were converted to a numerically equal value with zero scale and as
	 * if all the trailing zeros of the zero scale value were present in the
	 * result.
	 * 
	 * The entire string is prefixed by a minus sign character '-' (
	 * <tt>'&#92;u002D'</tt>) if the unscaled value is less than zero. No sign
	 * character is prefixed if the unscaled value is zero or positive.
	 * 
	 * Note that if the result of this method is passed to the
	 * {@linkplain #Amount(String) string constructor}, only the numerical value
	 * of this {@code MonetaryAmount} will necessarily be recovered; the
	 * representation of the new {@code MonetaryAmount} may have a different
	 * scale. In particular, if this {@code MonetaryAmount} has a negative
	 * scale, the string resulting from this method will have a scale of zero
	 * when processed by the string constructor.
	 * 
	 * @return a string representation of this {@code MonetaryAmount} without an
	 *         exponent field.
	 * @see #toString()
	 * @see #toEngineeringString()
	 */
	public String toPlainString();

	/**
	 * * Gets the monetary amount using the passed target type. This method
	 * allows to support different return types, depending of the concrete
	 * implementation. E.g. {@link BigDecimal}, {@link java.lang.Number} and the
	 * all numeric wrapper types should be supported within SE environments,
	 * whereas on other environments it may be different.
	 * <p>
	 * This returns the monetary value as a {@code T}. No scaling will be
	 * affected. for additional scaling based on the currency use
	 * {@link #valueOf(Class, boolean)} instead of.
	 * 
	 * @return the amount represented as T, never null
	 * @throws IllegalArgumentException
	 *             if the representation type is not supported.
	 */
	public <T> T asType(Class<T> type);

	/**
	 * Get the amount's value, without any modification. By default, a numeric
	 * value of an Amount will be rounded as defined by
	 * {@link CurrencyUnit#getDefaultFractionDigits()}.
	 * 
	 * @param type
	 *            the required target type
	 * @param adjustments
	 *            The adjustments to be applied. Hereby the order of the
	 *            {@link AmountAdjuster} instances within (@code adjustments)
	 *            implies to order of adjustments performed.
	 * @return the representation of this amount, adjusted using the given
	 *         adjustment.
	 */
	public <T> T asType(Class<T> type, AmountAdjuster... adjustments);

	/**
	 * Access the class that models the representation of the numeric part of
	 * the amount. The internal value can be accessed by calling
	 * {@link #valueOf(Class)} passing the result of this method.
	 * 
	 * @return The class that represents the numeric representation, never null.
	 */
	public Class<?> getNumberType();

}
