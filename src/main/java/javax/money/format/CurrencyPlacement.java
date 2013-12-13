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

import javax.money.MonetaryAmount;

/**
 * Defines the placement of the currency related to the amount.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public enum CurrencyPlacement {

	/**
	 * The rendered currency output will be rendered before the numeric part of
	 * the {@link MonetaryAmount}, e.g. {@code CHF 100.20}.
	 */
	BEFORE,

	/**
	 * The rendered currency output will be rendered before the numeric part of
	 * the {@link MonetaryAmount}, e.g. {@code 100.20 CHF}.
	 */
	AFTER,

	/**
	 * The currency will not be rendered at all, e.g. {@code 100.20}.
	 */
	OMIT,
	
}