/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */

package javax.money;

/**
 * Exception thrown when the currency of a {@link MonetaryAmount} passed to
 * arithmetic operations on another {@link MonetaryAmount} is not compatible.
 * <p>
 * For example, this exception would be thrown when trying to multiply a
 * {@link MonetaryAmount} in (ISO-4217) CHF with a a {@link MonetaryAmount} in
 * (ISO-4217) USD.
 * 
 * @author Werner Keil
 * @author Anatole Tresch
 */
public class CurrencyMismatchException extends MonetaryException {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3277879391197687869L;

	/** The source currrency */
	private CurrencyUnit source;

	/** The target currrency */
	private CurrencyUnit target;

	/**
	 * Constructor.
	 * 
	 * @param source
	 *            the source currency, not {@code null}.
	 * @param target
	 *            the mismatching target currency, not {@code null}.
	 */
	public CurrencyMismatchException(CurrencyUnit source, CurrencyUnit target) {
		super("Currency mismatch: " + source + " != " + target);
		if (source == null || target == null) {
			throw new IllegalArgumentException(
					"Source or target currency may not be null.");
		}
		this.source = source;
		this.target = target;
	}

	/**
	 * Access the source {@link CurrencyUnit} instance.
	 * 
	 * @return the source currency, not {@code null}
	 */
	public CurrencyUnit getSource() {
		return source;
	}

	/**
	 * Access the target {@link CurrencyUnit} instance.
	 * 
	 * @return the target currency, not {@code null}
	 */
	public CurrencyUnit getTarget() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CurrencyMismatchException [source=" + source + ", target="
				+ target + "]";
	}

}
