/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

/**
 * Instances of this class allow to externalize the numeric value of a {@link MonetaryAmount}. <h2>
 * Implementation specification</h2> Implementations of this interface must be
 * <ul>
 * <li>Comparable, hereby comparing the numeric value.</li>
 * <li>immutable</li>
 * <li>serializable</li>
 * <li>thread-safe</li>
 * </ul>
 * 
 * @author Anatole Tresch
 */
public abstract class NumberValue extends Number {

	/** serialVersionUID. */
	private static final long serialVersionUID = -6410309081241720626L;

	/**
	 * Get the numeric implementation type, that is the base of this number.
	 * 
	 * @return the numeric implementation type, not {@code null}.
	 */
	public abstract Class<?> getNumberType();

	/**
	 * Returns the <i>precision</i> of this {@code MonetaryAmount}. (The precision is the number of
	 * digits in the unscaled value.)
	 * 
	 * <p>
	 * The precision of a zero value is 1.
	 * 
	 * @return the precision of this {@code MonetaryAmount}.
	 */
	public abstract int getPrecision();

	/**
	 * Returns the <i>scale</i> of this {@code MonetaryAmount}. If zero or positive, the scale is
	 * the number of digits to the right of the decimal point. If negative, the unscaled value of
	 * the number is multiplied by ten to the power of the negation of the scale. For example, a
	 * scale of {@code -3} means the unscaled value is multiplied by 1000.
	 * 
	 * @return the scale of this {@code MonetaryAmount}.
	 */
	public abstract int getScale();

	/**
	 * Access the numeric value as {@code int}. Hereby no truncation will be performed to fit the
	 * value into the target data type.
	 * 
	 * @throws ArithmeticException
	 *             If the value must be truncated to fit the target datatype.
	 * @return the (possibly) truncated value of the {@link MonetaryAmount}.
	 */
	public abstract int intValueExact();

	/**
	 * Access the numeric value as {@code long}. Hereby no truncation will be performed to fit the
	 * value into the target data type.
	 * 
	 * @throws ArithmeticException
	 *             If the value must be truncated to fit the target datatype.
	 * @return the (possibly) truncated value of the {@link MonetaryAmount}.
	 */
	public abstract long longValueExact();

	/**
	 * Access the numeric value as {@code double}. Hereby no truncation will be performed to fit the
	 * value into the target data type.
	 * 
	 * @throws ArithmeticException
	 *             If the value must be truncated to fit the target datatype.
	 * @return the (possibly) truncated value of the {@link MonetaryAmount}.
	 */
	public abstract double doubleValueExact();

	/**
	 * Access the numeric value as {@code Number}. Hereby truncation may be performed as needed to
	 * fit the value into the target data type.
	 * 
	 * @param numberType
	 *            The concrete number class to be returned. Basically the following Number types,
	 *            must be supported if available on the corresponding runtime platform:
	 *            <ul>
	 *            <li>{@code java.lang.Long}</li>
	 *            <li>{@code java.lang.Double}</li>
	 *            <li>{@code java.lang.Number} hereby returning the same values as
	 *            {@link #getNumberValue()}.</li>
	 *            <li>{@code java.math.BigInteger}, currently not available on all platforms.</li>
	 *            <li>{@code java.math.BigDecimal}, currently not available on all platforms.</li>
	 *            </ul>
	 * @return the (possibly) truncated value of the {@link MonetaryAmount}.
	 */
	public abstract <T extends Number> T numberValue(Class<T> numberType);

	/**
	 * Access the numeric value as {@code Number}. Hereby no truncation will be performed to fit the
	 * value into the target data type.
	 * 
	 * @param numberType
	 *            The concrete number class to be returned. Basically the following Number types,
	 *            must be supported if available on the corresponding runtime platform:
	 *            <ul>
	 *            <li>{@code java.lang.Long}</li>
	 *            <li>{@code java.lang.Double}</li>
	 *            <li>{@code java.lang.Number} hereby returning the same values as
	 *            {@link #getNumberValue()}.</li>
	 *            <li>{@code java.math.BigInteger}, currently not available on all platforms.</li>
	 *            <li>{@code java.math.BigDecimal}, currently not available on all platforms.</li>
	 *            </ul>
	 * @throws ArithmeticException
	 *             If the value must be truncated to fit the target datatype.
	 * @return the (possibly) truncated value of the {@link MonetaryAmount}.
	 */
	public abstract <T extends Number> T numberValueExact(Class<T> numberType);

}
