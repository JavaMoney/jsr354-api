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
package org.javamoney.ext.spi;

import java.util.Collection;
import java.util.ServiceLoader;

import javax.money.CurrencyUnit;

/**
 * This class models the component defined by JSR 354 that provides accessory
 * for {@link CurrencyUnit}.<br/>
 * It is the responsibility of the registered
 * {@link MonetaryCurrenciesSingletonSpi} to load the and manage the instances
 * of {@link CurrencyUnitProviderSpi}. Depending on the runtime environment,
 * implementations may be loaded using the {@link ServiceLoader}. But also
 * alternate mechanisms are possible, e.g. CDI.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyUnitProviderSpi {

	/**
	 * Access the namespace this provider defines. An instance of
	 * {@link CurrencyUnitProviderSpi} alwyas provides {@link CurrencyUnit} for
	 * exact one namespace. Nevertheless multiple implementations of
	 * {@link CurrencyUnitProviderSpi} may serve currencies for the same
	 * namespace, if they do not conflict related to their currency code or
	 * numericCode (if defined).
	 * 
	 * @return the namespace of this provider, never {@code null}.
	 */
	public String getNamespace();

	/**
	 * Access all {@link CurrencyUnit} instances.
	 * 
	 * @return the {@link CurrencyUnit} instances known to this provider
	 *         instance, never {@code null}. If the provider can not provide a
	 *         full list of all currencies an empoty {@link Collection} should
	 *         be returned.
	 */
	public Collection<CurrencyUnit> getAll();

	/**
	 * Access a {@link CurrencyUnit} by code.
	 * <p>
	 * Note that the SPI is defined to return {@code null} instead of throwing a
	 * {@link UnknownCurrencyException}, since several SPI implementation may
	 * serve a namespace at the same time.
	 * 
	 * @param code
	 *            the {@link CurrencyUnit} found, or {@code null}.
	 * @return the {@link CurrencyUnit} found, or {@code null}.
	 */
	public CurrencyUnit get(String code);

	/**
	 * Checks if a currency is defined using its code.
	 * 
	 * @param code
	 *            The code that, together with the namespace of this provider
	 *            identifies the currency.
	 * @return {@code true}, if the currency is defined.
	 */
	public boolean isAvailable(String code);
}
