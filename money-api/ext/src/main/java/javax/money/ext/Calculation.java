/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 */
package javax.money.ext;

import javax.money.MonetaryAmount;

/**
 * This interface models a simple calculation that is based on a single
 * {@link MonetaryAmount}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the result type of the calculation.
 */
public interface Calculation<T> {

	/**
	 * Returns an literal non localized name, that identifies this type of
	 * calculation.
	 * 
	 * @return the identifier, not null.
	 */
	public String getId();

	/**
	 * Result type of this {@link Calculation}, rewuired to evaluate the type
	 * during runtime.
	 * 
	 * @return the result type.
	 */
	public Class<?> getResultType();

	/**
	 * Flag that defines if this calculation supports multiple input values.
	 * 
	 * @return true, if multiple input values are supported.
	 */
	public boolean isMultiValued();

	/**
	 * Returns a result calculated using the given {@link MonetaryAmount}.
	 * 
	 * @param amount
	 *            the amount to use, not null
	 * @return the calculation result, never null
	 * @throws ArithmeticException
	 *             if the adjustment fails
	 */
	public T calculate(MonetaryAmount... amounts);

}
