/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money;


/**
 * This interface adds {@link Rounding} to a {@link MonetaryAmount}. Hereby a
 * {@link Rounding} can be applied in two distinct flavors:
 * <ul>
 * <li>Rounding is performed implicitly, whenever an amount is
 * <i>externalized</i>, e.g. by calling {@link #asType(Class)}. This will
 * directly constrain the external precision, to the one implied by the
 * {@link Rounding} applied to this instance.</li>
 * <li>Rounding is never applied implicitly, so on externalization, the full
 * precision is applied if possible. Nevertheless a <i>rounded instance</i> can
 * be accessed easily by calling {@link #rounded()}.</li>
 * </ul>
 * 
 * @author Anatole Tresch
 */
public interface RoundedMonetaryAmount extends MonetaryAmount {
	/**
	 * Method to check, if the {@link Rounding} applied is automatically
	 * applied, whenever the instances value is accessed. If not performed
	 * automatically the {@link Rounding} is only applied, when
	 * {@link #rounded()} is called.
	 * 
	 * @return true, if {@link Rounding} is always implicitly performed.
	 */
	public boolean isRoundedImplicitly();

	/**
	 * Access a <i>rounded</i> instance of this amount. If
	 * {@link #isRoundedImplicitly()} then {@code this} should be returned,
	 * otherwise the result equals to {@code getRounding()#round(this)}.
	 * 
	 * @see #isRoundingImplicitly()
	 * @see #unrounded()
	 * @return the rounded amount (within the same currency).
	 */
	public MonetaryAmount rounded();

	/**
	 * Access the <i>unrounded</i> instance of this amount. If
	 * {@link #isRoundedImplicitly()} then this enables access to the internal
	 * unrounded {@link MonetaryAmount}, whereas all other externalization
	 * methods will return rounded values only. If
	 * {@link #isRoundedImplicitly()} is {@code false} the result equals to
	 * {@code this}.
	 * 
	 * @see #isRoundedImplicitly()
	 * @see #rounded()
	 * @return the unrounded amount (within the same currency).
	 */
	public MonetaryAmount unrounded();

	/**
	 * Access the {@link Rounding} to be applied implicitly or explicitly.
	 * 
	 * @see #isRoundedImplicitly()
	 * @see #rounded()
	 * @return the {@link Rounding} applied, never {@code null}.
	 */
	public Rounding getRounding();

}
