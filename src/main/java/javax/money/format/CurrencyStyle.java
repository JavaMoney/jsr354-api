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
package javax.money.format;

import javax.money.CurrencyUnit;

/**
 * Defines the different variants of currency formatting.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public enum CurrencyStyle {

	/**
	 * The currency will be rendered as its (non localized) currency code.
	 * 
	 * @see CurrencyUnit#getCurrencyCode()
	 */
	CODE,

	/**
	 * The currency will be rendered as its localized diplay name. If no display
	 * name is known for the required {@link CurrencyUnit}, the currency code
	 * should be used as a fall-back.
	 * 
	 * @see CurrencyUnit#getCurrencyCode()
	 */
	NAME,

	/**
	 * The currency will be rendered as its (non localized) numeric code.
	 * 
	 * @see CurrencyUnit#getNumericCode()
	 */
	NUMERIC_CODE,

	/**
	 * The currency will be rendered as its localized currency symbol. If no
	 * symbol name is known for the required {@link CurrencyUnit}, the currency
	 * code should be used as a fall-back.
	 * 
	 * @see CurrencyUnit#getCurrencyCode()
	 */
	SYMBOL,

	/**
	 * The currency will be not rendered at all.
	 */
	OMIT
}