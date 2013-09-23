/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.math.BigDecimal;

/**
 * Interface defining a monetary amount. The effective internal representation
 * of an amount may vary depending on the implementation used. JSR 354
 * explicitly supports different types of MonetaryAmount to be implemented and
 * used. Reason behind is that the requirements to an implementation heavily
 * vary for different usage scenarios. E.g. product calculations may require
 * high precision and scale, whereas low latency order and trading systems
 * require high calculation performance for algorithmic operations.
 * <p>
 * This JSR additionally defines a couple of interoperability rules that each
 * implementation must follow:
 * <ul>
 * <li>Rounding is never done automatically, exception internal rounding implied
 * by the numeric implementation type.</li>
 * <li>Each implementation must at least provide two constructors, one taking
 * {@code (BigDecimal, CurrencyUnit)}, one taking
 * {@code (BigDecimal, CurrencyUnit)}.</li>
 * <li>External rounding must be applied, when accessing the numeric part of an
 * amount, if implied by the required target type. E.g. externalizing to a
 * double may truncate precision. If the target type supports the current
 * scale/precision, it is required that no external rounding is performed.</li>
 * <li>It is required that each implementation supports externalization to
 * {@link BigDecimal}.</li>
 * <li>Since implementations are required to be immutable, an operation must
 * never change any internal state of an instance. Given an instance, all
 * operations are required to be fully reproducible.</li>
 * <li>Finally the result of calling {@link #with(MonetaryOperator)} must be of
 * the same type as type on which {@code with} was called. The {@code with}
 * method also defines additional interoperability requirements.</li>
 * </ul>
 * Nevertheless basically an amount provides a functionality similar to
 * {@link BigDecimal}. Also it is required that implementations of this
 * interface are
 * <ul>
 * <li>immutable</li>
 * <li>thread-safe</li>
 * <li>final</li>
 * <li>serializable, hereby writing the numeric representation, e.g.
 * {@link BigDecimal} and a serialized {@link CurrencyUnit}.</li>
 * </ul>
 * <p>
 * Since {@link Number} is not an interface, this type is not extending
 * {@link Number}.
 * 
 * @see #with(MonetaryOperator)
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface MonetaryAmount {

	/**
	 * Gets the amount's currency.
	 * 
	 * @return the currency, never {@code null}
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
	 * Returns a {@code MonetaryAmount} whose value is
	 * <tt>(this<sup>n</sup>)</tt>, The power is computed exactly, to unlimited
	 * precision.
	 * 
	 * <p>
	 * The parameter {@code n} must be in the range 0 through 999999999,
	 * inclusive. {@code ZERO.pow(0)} returns {@code 1}.
	 * 
	 * Note that future releases may expand the allowable exponent range of this
	 * method.
	 * 
	 * @param n
	 *            power to raise this {@code MonetaryAmount} to.
	 * @return <tt>this<sup>n</sup></tt>
	 * @throws ArithmeticException
	 *             if {@code n} is out of range.
	 * @since 1.0
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
	 * {@code [1, this.getgetScale()]}.
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
	 * Checks if the amount is zero.
	 * 
	 * @return true if the amount is zero
	 */
	public boolean isZero();

	/**
	 * Checks if the amount is greater than zero. This is a convenience method
	 * for {@code  x.signum() > 0}.
	 * 
	 * @return true if the amount is greater than zero
	 */
	public boolean isPositive();

	/**
	 * Checks if the amount is zero or greater. This is a convenience method for
	 * {@code  x.signum() >= 0}.
	 * 
	 * @return true if the amount is zero or greater
	 */
	public boolean isPositiveOrZero();

	/**
	 * Checks if the amount is less than zero. This is a convenience method for
	 * {@code  x.signum() < 0}.
	 * 
	 * @return true if the amount is less than zero
	 */
	public boolean isNegative();

	/**
	 * Checks if the amount is zero or less. This is a convenience method for
	 * {@code  x.signum() <= 0}.
	 * 
	 * @return true if the amount is zero or less
	 */
	public boolean isNegativeOrZero();

	// -----------------------------------------------------------------------
	/**
	 * Returns a copy of this monetary value with the specified amount.
	 * <p>
	 * The returned instance will have a currency as returned by
	 * {@link #getCurrency()} and the new amount. No rounding is performed on
	 * the amount to be added.<br/>
	 * The instance created also inherits the precision/scale properties from
	 * this instance.
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

	/**
	 * Returns a copy of this monetary value with the specified amount.
	 * <p>
	 * The returned instance will have a currency as returned by
	 * {@link #getCurrency()} and the new amount. No rounding is performed on
	 * the amount to be added.<br/>
	 * The instance created also inherits the precision/scale properties from
	 * this instance.
	 * <p>
	 * This instance is immutable and unaffected by this method.
	 * 
	 * @param currency
	 *            the target currency
	 * @param amount
	 *            the amount to set in the returned instance, not null
	 * @return the new instance with the input amount and currency set, never
	 *         null
	 * @throws ArithmeticException
	 *             if the scale of the amount is too large
	 */
	public MonetaryAmount with(CurrencyUnit currency, Number amount);

	/**
	 * Applies the given {@link MonetaryOperator} to this {@link MonetaryAmount}
	 * . Hereby the following constaints are implied:
	 * <ul>
	 * <li>The instance type returned must be the same as
	 * {@code this.getClass()}.</li>
	 * <li>No significant truncation of the result should happen, meaning the
	 * numeric precision/scale of the result should not be less than the numeric
	 * precision/scale of this instance. This ensures that no implicit rounding
	 * is happening that would by out of control of the programmer. But we
	 * aware: applying a {@link MonetaryOperator}, which creates a result that
	 * the current instance is not able, to represent, still leads to
	 * truncation.</li>
	 * </ul>
	 * 
	 * @param operator
	 *            the operator, not null.
	 * @return the result of the operation, never null.
	 */
	public MonetaryAmount with(MonetaryOperator operator);

	// -------------------- Introspection and value methods, similar to
	// java.lang.Number; java.lang.BigDecimal

	/**
	 * * Gets the scale of the amount.
	 * <p>
	 * The scale has the same meaning as in {@link java.math.BigDecimal}.
	 * Positive values represent the number of decimal places in use. For
	 * example, a scale of 2 means that the money will have two decimal places
	 * such as 'USD 43.25'.
	 * <p>
	 * For {@code MonetaryAmount}, the scale is fixed and always matches that of
	 * the currency.
	 * 
	 * @return the scale in use, typically 2 but could be 0, 1 and 3
	 */
	public int getScale();

	/**
	 * Returns the <i>internal precision</i> of this {@code MonetaryAmount}. The
	 * precision is the number of digits in the unscaled value.
	 * 
	 * <p>
	 * The precision of a zero value is 1.
	 * 
	 * @return the precision of this {@code MonetaryAmount}.
	 */
	public int getPrecision();

	// /**
	// * Returns the value of the specified number as an <code>int</code>. This
	// * may involve rounding or truncation.
	// *
	// * @return the numeric value represented by this object after conversion
	// to
	// * type <code>int</code>.
	// */
	// public int intValue();
	//
	// /**
	// * Converts this {@code MonetaryAmount} to an {@code int}, checking for
	// lost
	// * information. If this {@code MonetaryAmount} has a nonzero fractional
	// part
	// * or is out of the possible range for an {@code int} result then an
	// * {@code ArithmeticException} is thrown.
	// *
	// * @return this {@code MonetaryAmount} converted to an {@code int}.
	// * @throws ArithmeticException
	// * if {@code this} has a nonzero fractional part, or will not
	// * fit in an {@code int}.
	// */
	// public int intValueExact();

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

	// /**
	// * Returns the value of the specified number as a <code>float</code>. This
	// * may involve rounding.
	// *
	// * @return the numeric value represented by this object after conversion
	// to
	// * type <code>float</code>.
	// */
	// public float floatValue();

	/**
	 * Returns the value of the specified number as a <code>double</code>. This
	 * may involve rounding.
	 * 
	 * @return the numeric value represented by this object after conversion to
	 *         type <code>double</code>.
	 */
	public double doubleValue();

	// /**
	// * Returns the value of the specified number as a <code>byte</code>. This
	// * may involve rounding or truncation.
	// *
	// * @return the numeric value represented by this object after conversion
	// to
	// * type <code>byte</code>.
	// */
	// public byte byteValue();
	//
	// /**
	// * Returns the value of the specified number as a <code>short</code>. This
	// * may involve rounding or truncation.
	// *
	// * @return the numeric value represented by this object after conversion
	// to
	// * type <code>short</code>.
	// */
	// public short shortValue();
	//
	// /**
	// * Converts this {@code MonetaryAmount} to a {@code short}, checking for
	// * lost information. If this {@code MonetaryAmount} has a nonzero
	// fractional
	// * part or is out of the possible range for a {@code short} result then an
	// * {@code ArithmeticException} is thrown.
	// *
	// * @return this {@code MonetaryAmount} converted to a {@code short}.
	// * @throws ArithmeticException
	// * if {@code this} has a nonzero fractional part, or will not
	// * fit in a {@code short} .
	// */
	// public short shortValueExact();

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
	 * @return {@code true}, if this amount is less compared to the amount
	 *         passed.
	 */
	public boolean isLessThan(MonetaryAmount amount);

	/**
	 * Checks if this amount is less or the same compared to the amount passed.
	 * 
	 * @param amount
	 *            The amount to compare to.
	 * @return {@code true}, if this amount is less or the same compared to the
	 *         amount passed.
	 */
	public boolean isLessThanOrEqualTo(MonetaryAmount amount);

	/**
	 * Checks if this amount is greater compared to the amount passed.
	 * 
	 * @param amount
	 *            The amount to compare to.
	 * @return {@code true}, if this amount is greater compared to the amount
	 *         passed.
	 */
	public boolean isGreaterThan(MonetaryAmount amount);

	/**
	 * Checks if this amount is greater or the same compared to the amount
	 * passed.
	 * 
	 * @param amount
	 *            The amount to compare to.
	 * @return {@code true}, if this amount is greater or the same compared to
	 *         the amount passed.
	 */
	public boolean isGreaterThanOrEqualTo(MonetaryAmount amount);

	/**
	 * Checks if this amount is the same compared to the amount passed. Two
	 * amounts ore considered equal here, when
	 * <ul>
	 * <li>when both currencies are equal
	 * <li>their numeric values are equal, when converted to the corresponding
	 * {@link Number} types, using {@link #asNumber()};
	 * </ul>
	 * 
	 * @param amount
	 *            The amount to compare to.
	 * @return {@code true}, if this amount's value is the same compared to the
	 *         number passed.
	 */
	public boolean isEqualTo(MonetaryAmount amount);

	/**
	 * Checks if this amount is not the same compared to the amount passed.
	 * 
	 * @param amount
	 *            The amount to compare to.
	 * @return {@code true}, if this amount's value is not the same compared to
	 *         the number passed, or the {@link CurrencyUnit} of the amount
	 *         passed does not match this instances {@link CurrencyUnit}.
	 * @see #hasSameCurrencyAs(MonetaryAmount)
	 */
	public boolean isNotEqualTo(MonetaryAmount amount);

	// -------------------------------------------- Misc

	/**
	 * Gets the monetary amount using the passed target type. This method allows
	 * to support different return types, depending of the concrete
	 * implementation. E.g. {@link BigDecimal}, {@link java.lang.Number} and the
	 * numeric wrapper types must be supported within SE environments, whereas
	 * on other environments, it may be different.
	 * <p>
	 * Summarizing an implementation must support the following types:
	 * <ul>
	 * <li>{@code java.math.BigDecimal}</li>
	 * <li>{@code java.math.BigInteger}</li>
	 * <li>{@code java.lang.Byte}</li>
	 * <li>{@code java.lang.Short}</li>
	 * <li>{@code java.lang.Integer}</li>
	 * <li>{@code java.lang.Long}</li>
	 * <li>{@code java.lang.Float}</li>
	 * <li>{@code java.lang.Double}</li>
	 * <li>{@code java.lang.Number}, hereby returning the best Number
	 * representation that is matching to the internal representation.</li>
	 * </ul>
	 * {@code java.lang.String} is not supported, since this should be done
	 * using formatting.
	 * <p>
	 * This method must never truncate parts of the internal numeric value, so
	 * that it fits into the target type. Instead an
	 * {@link IllegalArgumentException} must be thrown.
	 * <p>
	 * Note since a {@link MonetaryAmount} is immutable, any values returned by
	 * this method must not expose mutability, i.e. they must be immutable or
	 * copies of the internal state.
	 * 
	 * @return the amount represented as T, never {@code null}. Hereby T must
	 *         have the same scale/precision as the internal numeric value.
	 * @throws IllegalArgumentException
	 *             if the representation type is not supported, or the target
	 *             type can not provide the precision required.
	 */
	public <T> T asType(Class<T> type);

	/**
	 * Access the implementation class of the numeric part of the amount. The
	 * internal implementation type can be accessed by calling
	 * {@link #asType(Class)} passing the result type as returned by this
	 * method.
	 * 
	 * @return The class that represents the numeric representation, never
	 *         {@code null}.
	 * @see #asType(Class)
	 */
	public Class<?> getNumberType();

}
