/*
 *  Copyright 2012 Credit Suisse (Anatole Tresch)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package javax.money;

/**
 * @author Anatole Tresch
 */
public interface Amount extends Comparable<Amount> {

	/**
	 * Gets the currency.
	 * 
	 * @return the currency, never null
	 */
	public CurrencyUnit getCurrency();

	/**
	 * * Gets the monetary amount using the passed target type. This method
	 * allows to support different return types, depenending of the concrete
	 * implementation. E.g. BigDecimal is supported within SE environments,
	 * whereas on ME environments this may not be the case.
	 * <p>
	 * This returns the monetary value as a {@code T}. The scale will be the
	 * scale of this money.
	 * 
	 * @return the amount, never null
	 */
	public <T> T adapt(Class<T> targetClass);

	// -------------------- calculation methods

	/**
	 * Returns a {@code Amount} whose value is the absolute value of this
	 * {@code Amount}, and whose scale is {@code this.getgetScale()}.
	 * 
	 * @return {@code abs(this)}
	 */
	public Amount abs();

	/**
	 * Returns the minimum of this {@code Amount} and {@code val}.
	 * 
	 * @param val
	 *            value with which the minimum is to be computed.
	 * @return the {@code Amount} whose value is the lesser of this
	 *         {@code Amount} and {@code val}. If they are equal, as defined by
	 *         the {@link #compareTo(Amount) compareTo} method, {@code this} is
	 *         returned.
	 * @see #compareTo(java.math.Amount)
	 */
	public Amount min(Amount amount);

	/**
	 * Returns the maximum of this {@code Amount} and {@code val}.
	 * 
	 * @param val
	 *            value with which the maximum is to be computed.
	 * @return the {@code Amount} whose value is the greater of this
	 *         {@code Amount} and {@code val}. If they are equal, as defined by
	 *         the {@link #compareTo(Amount) compareTo} method, {@code this} is
	 *         returned.
	 * @see #compareTo(Amount)
	 */
	public Amount max(Amount amount);

	/**
	 * Returns a {@code Amount} whose value is {@code (this +
	 * augend)}, and whose scale is {@code max(this.getScale(),
	 * augend.getScale())}.
	 * 
	 * @param augend
	 *            value to be added to this {@code Amount}.
	 * @return {@code this + augend}
	 */
	public Amount add(Amount amount);

	/**
	 * Returns a {@code Amount} whose value is {@code (this +
	 * augend)}, and whose scale is {@code max(this.getScale(),
	 * augend.getScale())}.
	 * 
	 * @param augend
	 *            value to be added to this {@code Amount}.
	 * @return {@code this + augend}
	 */
	public Amount add(Number number);

	/**
	 * Returns a {@code Amount} whose value is {@code (this /
	 * divisor)}, and whose preferred scale is {@code (this.getScale() -
	 * divisor.getScale())}; if the exact quotient cannot be represented
	 * (because it has a non-terminating decimal expansion) an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @param divisor
	 *            value by which this {@code Amount} is to be divided.
	 * @throws ArithmeticException
	 *             if the exact quotient does not have a terminating decimal
	 *             expansion
	 * @return {@code this / divisor}
	 */
	public Amount divide(Amount divisor);

	/**
	 * Returns a {@code Amount} whose value is {@code (this /
	 * divisor)}, and whose preferred scale is {@code (this.getScale() -
	 * divisor.getScale())}; if the exact quotient cannot be represented
	 * (because it has a non-terminating decimal expansion) an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @param divisor
	 *            value by which this {@code Amount} is to be divided.
	 * @throws ArithmeticException
	 *             if the exact quotient does not have a terminating decimal
	 *             expansion
	 * @return {@code this / divisor}
	 */
	public Amount divide(Number divisor);

	/**
	 * Returns a {@code Amount} whose value is {@code (this /
	 * divisor)}, and whose preferred scale is {@code (this.getScale() -
	 * divisor.getScale())}; if the exact quotient cannot be represented
	 * (because it has a non-terminating decimal expansion) an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @param divisor
	 *            value by which this {@code Amount} is to be divided.
	 * @throws ArithmeticException
	 *             if the exact quotient does not have a terminating decimal
	 *             expansion
	 * @return {@code this / divisor}
	 */
	public Amount divide(Amount divisor, Rounding rounding);

	/**
	 * Returns a {@code Amount} whose value is {@code (this /
	 * divisor)}, and whose preferred scale is {@code (this.getScale() -
	 * divisor.getScale())}; if the exact quotient cannot be represented
	 * (because it has a non-terminating decimal expansion) an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @param divisor
	 *            value by which this {@code Amount} is to be divided.
	 * @throws ArithmeticException
	 *             if the exact quotient does not have a terminating decimal
	 *             expansion
	 * @return {@code this / divisor}
	 */
	public Amount divide(Number divisor, Rounding rounding);

	/**
	 * Returns a two-element {@code Amount} array containing the result of
	 * {@code divideToIntegralValue} followed by the result of {@code remainder}
	 * on the two operands.
	 * 
	 * <p>
	 * Note that if both the integer quotient and remainder are needed, this
	 * method is faster than using the {@code divideToIntegralValue} and
	 * {@code remainder} methods separately because the division need only be
	 * carried out once.
	 * 
	 * @param divisor
	 *            value by which this {@code Amount} is to be divided, and the
	 *            remainder computed.
	 * @return a two element {@code Amount} array: the quotient (the result of
	 *         {@code divideToIntegralValue}) is the initial element and the
	 *         remainder is the final element.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 * @see #divideToIntegralValue(Amount)
	 * @see #remainder(Amount)
	 */
	public Amount[] divideAndRemainder(Amount divisor);

	/**
	 * Returns a two-element {@code Amount} array containing the result of
	 * {@code divideToIntegralValue} followed by the result of {@code remainder}
	 * on the two operands.
	 * 
	 * <p>
	 * Note that if both the integer quotient and remainder are needed, this
	 * method is faster than using the {@code divideToIntegralValue} and
	 * {@code remainder} methods separately because the division need only be
	 * carried out once.
	 * 
	 * @param divisor
	 *            value by which this {@code Amount} is to be divided, and the
	 *            remainder computed.
	 * @return a two element {@code Amount} array: the quotient (the result of
	 *         {@code divideToIntegralValue}) is the initial element and the
	 *         remainder is the final element.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 * @see #divideToIntegralValue(Amount)
	 * @see #remainder(Amount)
	 */
	public Amount[] divideAndRemainder(Number divisor);

	/**
	 * Returns a {@code Amount} whose value is the integer part of the quotient
	 * {@code (this / divisor)} rounded down. The preferred scale of the result
	 * is {@code (this.getScale() -
	 * divisor.getScale())}.
	 * 
	 * @param divisor
	 *            value by which this {@code Amount} is to be divided.
	 * @return The integer part of {@code this / divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 */
	public Amount divideToIntegralValue(Amount divisor);

	/**
	 * Returns a {@code Amount} whose value is the integer part of the quotient
	 * {@code (this / divisor)} rounded down. The preferred scale of the result
	 * is {@code (this.getScale() -
	 * divisor.getScale())}.
	 * 
	 * @param divisor
	 *            value by which this {@code Amount} is to be divided.
	 * @return The integer part of {@code this / divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 */
	public Amount divideToIntegralValue(Number divisor);

	/**
	 * Returns a {@code Amount} whose value is <tt>(this &times;
	 * multiplicand)</tt>, and whose scale is {@code (this.getScale() +
	 * multiplicand.getScale())}.
	 * 
	 * @param multiplicand
	 *            value to be multiplied by this {@code Amount}.
	 * @return {@code this * multiplicand}
	 */
	public Amount multiply(Amount multiplicand);

	/**
	 * Returns a {@code Amount} whose value is <tt>(this &times;
	 * multiplicand)</tt>, and whose scale is {@code (this.getScale() +
	 * multiplicand.getScale())}.
	 * 
	 * @param multiplicand
	 *            value to be multiplied by this {@code Amount}.
	 * @return {@code this * multiplicand}
	 */
	public Amount multiply(Number multiplicand);

	/**
	 * Returns a {@code Amount} whose value is {@code (-this)}, and whose scale
	 * is {@code this.getScale()}.
	 * 
	 * @return {@code -this}.
	 */
	public Amount negate();

	/**
	 * Returns a {@code Amount} whose value is {@code (+this)}, and whose scale
	 * is {@code this.getScale()}.
	 * 
	 * <p>
	 * This method, which simply returns this {@code Amount} is included for
	 * symmetry with the unary minus method {@link #negate()}.
	 * 
	 * @return {@code this}.
	 * @see #negate()
	 */
	public Amount plus();

	/**
	 * Returns a {@code Amount} whose value is {@code (this -
	 * subtrahend)}, and whose scale is {@code max(this.getScale(),
	 * subtrahend.getScale())}.
	 * 
	 * @param subtrahend
	 *            value to be subtracted from this {@code Amount}.
	 * @return {@code this - subtrahend}
	 */
	public Amount subtract(Amount subtrahend);

	/**
	 * Returns a {@code Amount} whose value is {@code (this -
	 * subtrahend)}, and whose scale is {@code max(this.getScale(),
	 * subtrahend.getScale())}.
	 * 
	 * @param subtrahend
	 *            value to be subtracted from this {@code Amount}.
	 * @return {@code this - subtrahend}
	 */
	public Amount subtract(Number subtrahend);

	/**
	 * Returns a {@code Amount} whose value is <tt>(this<sup>n</sup>)</tt>, The
	 * power is computed exactly, to unlimited precision.
	 * 
	 * <p>
	 * The parameter {@code n} must be in the range 0 through 999999999,
	 * inclusive. {@code ZERO.pow(0)} returns {@link #ONE}.
	 * 
	 * Note that future releases may expand the allowable exponent range of this
	 * method.
	 * 
	 * @param n
	 *            power to raise this {@code Amount} to.
	 * @return <tt>this<sup>n</sup></tt>
	 * @throws ArithmeticException
	 *             if {@code n} is out of range.
	 * @since 1.5
	 */
	public Amount pow(int n);

	/**
	 * Returns the size of an ulp, a unit in the last place, of this
	 * {@code Amount}. An ulp of a nonzero {@code Amount} value is the positive
	 * distance between this value and the {@code Amount} value next larger in
	 * magnitude with the same number of digits. An ulp of a zero value is
	 * numerically equal to 1 with the scale of {@code this}. The result is
	 * stored with the same scale as {@code this} so the result for zero and
	 * nonzero values is equal to {@code [1,
	 * this.getgetScale()]}.
	 * 
	 * @return the size of an ulp of {@code this}
	 */
	public Amount ulp();

	/**
	 * Returns a {@code Amount} whose value is {@code (this % divisor)}.
	 * 
	 * <p>
	 * The remainder is given by
	 * {@code this.subtract(this.divideToIntegralValue(divisor).multiply(divisor))}
	 * . Note that this is not the modulo operation (the result can be
	 * negative).
	 * 
	 * @param divisor
	 *            value by which this {@code Amount} is to be divided.
	 * @return {@code this % divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 */
	public Amount remainder(Amount divisor);

	/**
	 * Returns a {@code Amount} whose value is {@code (this % divisor)}.
	 * 
	 * <p>
	 * The remainder is given by
	 * {@code this.subtract(this.divideToIntegralValue(divisor).multiply(divisor))}
	 * . Note that this is not the modulo operation (the result can be
	 * negative).
	 * 
	 * @param divisor
	 *            value by which this {@code Amount} is to be divided.
	 * @return {@code this % divisor}.
	 * @throws ArithmeticException
	 *             if {@code divisor==0}
	 */
	public Amount remainder(Number divisor);

	/**
	 * Returns a {@code Amount} rounded according to the {@code Rounding}
	 * settings. If the precision setting is 0 then no rounding takes place.
	 * 
	 * @param rounding
	 *            the rounding to use.
	 * @return a {@code Amount} rounded according to the {@code MathContext}
	 *         settings.
	 * @throws ArithmeticException
	 *             if the rounding mode is {@code UNNECESSARY} and the
	 *             {@code Amount} operation would require rounding.
	 */
	public Amount toRoundedValue(Rounding rounding);

	/**
	 * Returns a {@code Amount} rounded according to the {@code Rounding}
	 * settings. If the precision setting is 0 then no rounding takes place.
	 * 
	 * @param rounding
	 *            the rounding to use.
	 * @return a {@code BigDecimal} rounded according to the {@code MathContext}
	 *         settings.
	 * @throws ArithmeticException
	 *             if the rounding mode is {@code UNNECESSARY} and the
	 *             {@code BigDecimal} operation would require rounding.
	 */
	public Amount round(Rounding rounding);

	/**
	 * Returns a Amount whose numerical value is equal to ({@code this} *
	 * 10<sup>n</sup>). The scale of the result is {@code (this.getScale() - n)}
	 * .
	 * 
	 * @throws ArithmeticException
	 *             if the scale would be outside the range supported.
	 */
	public Amount scaleByPowerOfTen(int n);

	// -------------------- Introspection and value methods, similar to
	// java.lang.Number; java.lang.BigDecimal

	/**
	 * * Gets the scale of the amount.
	 * <p>
	 * The scale has the same meaning as in {@link Amount}. * Positive values
	 * represent the number of decimal places in use. * For example, a scale of
	 * 2 means that the money will have two decimal places * such as 'USD
	 * 43.25'.
	 * <p>
	 * For {@code Amount}, the scale is fixed and always matches that of the
	 * currency.
	 * 
	 * @return the scale in use, typically 2 but could be 0, 1 and 3
	 */
	public int getScale();

	/**
	 * * Gets the scale of the {@code Amount}.
	 * <p>
	 * * The scale has the same meaning as in {@link Amount}. Positive values
	 * represent the number of decimal places in use. For example, a scale of 2
	 * means that the money will have two decimal places * such as 'USD 43.25'.
	 * *
	 * <p>
	 * * For {@code Amount}, the scale is fixed and always matches that of the
	 * currency.
	 * 
	 * @return the scale in use, typically 2 but could be 0, 1 and 3
	 */
	public int getDecimals();

	/**
	 * Returns the <i>precision</i> of this {@code Amount}. (The precision is
	 * the number of digits in the unscaled value.)
	 * 
	 * <p>
	 * The precision of a zero value is 1.
	 * 
	 * @return the precision of this {@code Amount}.
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
	 * Converts this {@code Amount} to an {@code int}, checking for lost
	 * information. If this {@code Amount} has a nonzero fractional part or is
	 * out of the possible range for an {@code int} result then an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @return this {@code Amount} converted to an {@code int}.
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
	 * Converts this {@code Amount} to a {@code long}, checking for lost
	 * information. If this {@code Amount} has a nonzero fractional part or is
	 * out of the possible range for a {@code long} result then an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @return this {@code Amount} converted to a {@code long}.
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
	 * Converts this {@code Amount} to a {@code short}, checking for lost
	 * information. If this {@code Amount} has a nonzero fractional part or is
	 * out of the possible range for a {@code short} result then an
	 * {@code ArithmeticException} is thrown.
	 * 
	 * @return this {@code Amount} converted to a {@code short}.
	 * @throws ArithmeticException
	 *             if {@code this} has a nonzero fractional part, or will not
	 *             fit in a {@code short} .
	 */
	public short shortValueExact();

	/**
	 * Returns the signum function of this {@code Amount}.
	 * 
	 * @return -1, 0, or 1 as the value of this {@code Amount} is negative,
	 *         zero, or positive.
	 */
	public int signum();

	// -------------------------------------------- Misc

	/**
	 * Returns a string representation of this {@code Amount}, using engineering
	 * notation if an exponent is needed.
	 * 
	 * <p>
	 * Returns a string that represents the {@code BigDecimal} as described in
	 * the {@link #toString()} method, except that if exponential notation is
	 * used, the power of ten is adjusted to be a multiple of three (engineering
	 * notation) such that the integer part of nonzero values will be in the
	 * range 1 through 999. If exponential notation is used for zero values, a
	 * decimal point and one or two fractional zero digits are used so that the
	 * scale of the zero value is preserved. Note that unlike the output of
	 * {@link #toString()}, the output of this method is <em>not</em> guaranteed
	 * to recover the same [integer, scale] pair of this {@code Amount} if the
	 * output string is converting back to a {@code BigDecimal} using the
	 * {@linkplain #Amount(String) string constructor}. The result of this
	 * method meets the weaker constraint of always producing a numerically
	 * equal result from applying the string constructor to the method's output.
	 * 
	 * @return string representation of this {@code Amount}, using engineering
	 *         notation if an exponent is needed.
	 */
	public String toEngineeringString();

	/**
	 * Returns a string representation of this {@code Amount} without an
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
	 * of this {@code Amount} will necessarily be recovered; the representation
	 * of the new {@code Amount} may have a different scale. In particular, if
	 * this {@code Amount} has a negative scale, the string resulting from this
	 * method will have a scale of zero when processed by the string
	 * constructor.
	 * 
	 * (This method behaves analogously to the {@code toString} method in 1.4
	 * and earlier releases.)
	 * 
	 * @return a string representation of this {@code Amount} without an
	 *         exponent field.
	 * @see #toString()
	 * @see #toEngineeringString()
	 */
	public String toPlainString();
}
