/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.provider;

import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryOperator;

/**
 * This instance provides default {@link Rounding}, e.g. for ISO currencies.
 * 
 * @author Anatole Tresch
 */
public interface RoundingProvider {

	/**
	 * Access the {@link Rounding} by id.
	 * 
	 * @param name
	 *            the rounding's id. not null.
	 * @return the {@link Rounding}. If no explicit {@link Rounding} is defined,
	 *         {@code null} is returned.
	 */
	public MonetaryOperator getRounding(String name);

	/**
	 * Access the available rounding ids.
	 * 
	 * @return the ids available, never {@code null}.
	 */
	public Set<String> getRoundingIds();

	/**
	 * Allows to determine if a named {@link Rounding} is available.
	 * 
	 * @param id
	 *            the {@link Rounding}'s identifier.
	 * @return true, if such a {@link Rounding} is defined.
	 */
	public boolean isRoundingDefined(String id);

	/**
	 * Access the {@link Rounding} for a given {@link CurrencyUnit}.
	 * 
	 * @param currency
	 *            the currency instance. not null.
	 * @return the {@link Rounding}. If no explicit {@link Rounding} is defined,
	 *         it should be created/registered based on
	 *         {@link CurrencyUnit#getDefaultFractionDigits()}.
	 */
	public MonetaryOperator getRounding(CurrencyUnit currency);

	/**
	 * Access the {@link Rounding} for a given {@link CurrencyUnit} and
	 * timestamp.
	 * 
	 * @param currency
	 *            the currency instance. not null.
	 * @param timestamp
	 *            the target timestamp for the {@link Rounding}, or {@code null}
	 *            for the current UTC time.
	 * @return the {@link Rounding}. If no explicit {@link Rounding} is defined,
	 *         it should be created/registered based on
	 *         {@link CurrencyUnit#getDefaultFractionDigits()}.
	 */
	public MonetaryOperator getRounding(CurrencyUnit currency, Long timestamp);

}
