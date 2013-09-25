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

import java.util.ServiceLoader;

import javax.money.CurrencyUnit;

/**
 * This class models the component defined by JSR 354 that provides mappings for
 * {@link CurrencyUnit} instances. It is used by the
 * {@link MonetaryCurrenciesSingletonSpi} implementation.<br/>
 * It is the responsibility of the registered
 * {@link MonetaryCurrenciesSingletonSpi} to load the and manage the instances
 * of {@link CurrencyUnitMapperSpi}. Depending on the runtime environment,
 * implementations may be loaded using the {@link ServiceLoader}. But also
 * alternate mechanisms are possible, e.g. CDI.
 * <p>
 * Implementation of this interface must be thread-safe, but can be contextual
 * in a EE context.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyUnitMapperSpi {

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.
	 * 
	 * @param currencyUnit
	 *            The source unit, never {@code null}.
	 * @param targetNamespace
	 *            the target {@link CurrencyNamespace}, never {@code null}.
	 * @param timestamp
	 *            the target timestamp, may be {@code null}.
	 * @return The mapped {@link CurrencyUnit}, or {@code null}.
	 */
	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace,
			Long timestamp);
}
