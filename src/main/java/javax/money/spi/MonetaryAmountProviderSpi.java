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
package javax.money.spi;

import java.util.ServiceLoader;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;

/**
 * Factory SPI that creates instances of {@link MonetaryAmount}. Implementation
 * classes should be registered using the {@link ServiceLoader}, hereby multiple
 * implementation classes can be registered at the same time. The registered
 * providers are prioritized based on an (optional) @Priority annotation on the
 * implementation class.
 * <p>
 * Implementations of this interface must be
 * <ul>
 * <li>thread-safe
 * <li>not require loading of other resources during instantiation.
 * </ul>
 * If required, it is possible to implement this interface in a contextual way,
 * e.g. providing different amounts depending on the current EE application
 * context.
 * 
 * @version 0.8
 * @author Anatole Tresch
 */
public interface MonetaryAmountProviderSpi {

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the given
	 * {@link MonetaryContext}, {@link CurrencyUnit} and {@code number}. If the
	 * {@link MonetaryAmountProviderSpi} does not support the required
	 * {@link MonetaryContext}, {@code null} must be returned, to allow
	 * subsequent {@link MonetaryAmountProviderSpi} to serve the request.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return a {@code MonetaryAmount} instance based on the
	 *         {@link MonetaryContext} with the given {@code number} and
	 *         {@link CurrencyUnit}, or {@code null} if this provider does not
	 *         provide a {@link MonetaryAmount} for the given
	 *         {@link MonetaryContext} or runtime state.
	 */
	public MonetaryAmount getAmount(CurrencyUnit currency, long number,
			MonetaryContext monetaryContext);

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the given
	 * {@link MonetaryContext}, {@link CurrencyUnit} and {@code number}. If the
	 * {@link MonetaryAmountProviderSpi} does not support the required
	 * {@link MonetaryContext}, {@code null} must be returned, to allow
	 * subsequent {@link MonetaryAmountProviderSpi} to serve the request.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return a {@code MonetaryAmount} instance based on the
	 *         {@link MonetaryContext} with the given {@code number} and
	 *         {@link CurrencyUnit}, or {@code null} if this provider does not
	 *         provide a {@link MonetaryAmount} for the given
	 *         {@link MonetaryContext} or runtime state.
	 */
	public MonetaryAmount getAmount(CurrencyUnit currency, double number,
			MonetaryContext monetaryContext);

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the given
	 * {@link MonetaryContext}, {@link CurrencyUnit} and {@code number}. If the
	 * {@link MonetaryAmountProviderSpi} does not support the required
	 * {@link MonetaryContext}, {@code null} must be returned, to allow
	 * subsequent {@link MonetaryAmountProviderSpi} to serve the request.
	 * 
	 * @param number
	 *            numeric value, not {@code null}.
	 * @param currency
	 *            currency unit, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return a {@code MonetaryAmount} instance based on the
	 *         {@link MonetaryContext} with the given {@code number} and
	 *         {@link CurrencyUnit}, or {@code null} if this provider does not
	 *         provide a {@link MonetaryAmount} for the given
	 *         {@link MonetaryContext} or runtime state.
	 */
	public MonetaryAmount getAmount(CurrencyUnit currency, Number number,
			MonetaryContext monetaryContext);

	/**
	 * Creates a new instance of {@link MonetaryAmount}, using the given
	 * {@link MonetaryContext} and {@link MonetaryAmount}. If the
	 * {@link MonetaryAmountProviderSpi} does not support the required
	 * {@link MonetaryContext}, {@code null} must be returned, to allow
	 * subsequent {@link MonetaryAmountProviderSpi} to serve the request.
	 * 
	 * @param amount
	 *            the base {@link MonetaryAmount}, not {@code null}.
	 * @param monetaryContext
	 *            the {@link MonetaryContext} to be used, if {@code null} the
	 *            default {@link MonetaryContext} is used.
	 * @return a {@code MonetaryAmount} instance based on the
	 *         {@link MonetaryContext} with the given {@link MonetaryAmount}'s
	 *         currency and numeric value, or {@code null} if this provider does
	 *         not provide a {@link MonetaryAmount} for the given
	 *         {@link MonetaryContext} or runtime state.
	 */
	public MonetaryAmount getAmountFrom(MonetaryAmount<?> amt,
			MonetaryContext monetaryContext);


}
