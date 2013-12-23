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

/**
 * Interface defining a monetary amount. The effective internal representation
 * of an amount may vary depending on the implementation used. JSR 354
 * explicitly supports different types of monetary amounts to be implemented and
 * used. Reason behind is that the requirements to an implementation heavily
 * vary for different usage scenarios. E.g. product calculations may require
 * high precision and scale, whereas low latency order and trading systems
 * require high calculation performance for algorithmic operations.
 * <p>
 * Each instance of an amount provides additional meta-data in form of a
 * {@link MonetaryContext}. This context contains detailed information on the
 * numeric capabilities, e.g. the supported precision and scale, as well as the
 * common implementation flavor.
 * <br/>Also a {@link MonetaryValue} provides a NumberBinding, which allows
 * easily to extract the numeric value, of the amount. And finally {@link #getFactory()}
 * provides a {@link MonetaryAmountFactory}, which allows to create instances of {@link MonetaryValue}
 * based on the same numeric implementation.
 * <p>
 * This JSR additionally recommends to consider the following aspects:
 * <ul>
 * <li>Arithmetic operations should throw an {@link ArithmeticException}, if
 * performing arithmetic operations between amounts exceeds the capabilities of
 * the numeric representation type used. Any implicit truncating, that would
 * lead to complete invalid and useless results, should be avoided. This
 * recommendation does not affect internal rounding, as required by the internal
 * numeric representation of a monetary amount.
 * <li>Monetary amounts should allow numbers as argument for arithmetic
 * operations like division and multiplication. Adding or subtracting of amounts
 * must only be possible by passing instances of {@link MonetaryValue}.</li>
 * <li>If the numeric representation of a monetary amount exceeds the numeric
 * capabilities of the concrete type {@code T extends MonetaryAmount}, an
 * implementation should throw an {@code ArithemticOperationException}.</li>
 * <li>On the other hand, when the numeric value can not be mapped into the
 * numeric exchange format defined by this interface, by default also an
 * {@code ArithmeticException} should be thrown. Never should truncation be
 * performed implicitly.</li>
 * <li>Nevertheless truncation is also explicitly supported when calling
 * {@link #getNumber(Class)}, whereas the <i>exact</i> counterpart,
 * {@link #getNumberExact(Class)}, works similar to
 * {@link java.math.BigDecimal#longValueExact()}.
 * <li>Rounding should never be done automatically, exception internal rounding
 * implied by the numeric implementation type.</li>
 * <li>Since implementations are recommended to be immutable, an operation
 * should never change any internal state of an instance. Given an instance, all
 * operations are required to be fully reproducible.</li>
 * <li>Finally the result of calling {@link #with(MonetaryOperator)} should be
 * of the same type as type on which {@code with} was called. The {@code with}
 * method also defines additional interoperability requirements.</li>
 * <li>To enable further interoperability a static method
 * {@code from(MonetaryAmount)} is recommended to be implemented on each implementation class {@code M}, that allows
 * conversion of a {@code MonetaryAmount} to a concrete instance of {@code M extends MonetaryAmount}:<br/>
 * 
 * <pre>
 * public final class M implements MonetaryAmount{
 *   ...
 *   public static M from(MonetaryAmount amount)(...)
 * }
 * </pre>
 * 
 * </li>
 * <li>Finally implementations should not implement a method {@code getAmount()}
 * . This method is reserved for future integration into the JDK.</li>
 * </ul>
 * <h4>Implementation specification</h4>
 * Implementations of this interface should be
 * <ul>
 * <li>immutable</li>
 * <li>thread-safe</li>
 * <li>final</li>
 * <li>serializable, hereby writing the numeric value, the
 * {@link MonetaryContext} and a serialized {@link CurrencyUnit}.</li>
 * </ul>
 * Implementations of this interface must be
 * <ul>
 * <li>comparable</li>
 * <li>must implement {@code equals/hashCode}, hereby considering
 * <ul>
 * <li>Implementation type
 * <li>CurrencyUnit
 * <li>Numeric value.
 * </ul>
 * This also means that two different implementations types with the same
 * currency and numeric value are NOT equal.</li>
 * </ul>
 * <p>
 * Since {@link Number} is not an interface, this type is not extending
 * {@link Number}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * 
 * @version 0.1
 */
public interface MonetaryValue extends CurrencySupplier, NumberSupplier {

	/**
	 * Returns the <i>precision</i> of this {@code MonetaryAmount}. (The
	 * precision is the number of digits in the unscaled value.)
	 * 
	 * <p>
	 * The precision of a zero value is 1.
	 * 
	 * @return the precision of this {@code MonetaryAmount}.
	 */
	public int getPrecision();

	/**
	 * Returns the <i>scale</i> of this {@code MonetaryAmount}. If zero or
	 * positive, the scale is the number of digits to the right of the decimal
	 * point. If negative, the unscaled value of the number is multiplied by ten
	 * to the power of the negation of the scale. For example, a scale of
	 * {@code -3} means the unscaled value is multiplied by 1000.
	 * 
	 * @return the scale of this {@code MonetaryAmount}.
	 */
	public int getScale();

}
