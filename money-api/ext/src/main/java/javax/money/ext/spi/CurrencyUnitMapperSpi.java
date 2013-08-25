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
package javax.money.ext.spi;

import java.util.ServiceLoader;

import javax.money.CurrencyUnit;

/**
 * This class models the component defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit}. It is provided by the Monetary singleton.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyUnitMapperSpi {

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.<br/>
	 * It is the responsibility of the registered
	 * {@link MonetaryCurrenciesSingletonSpi} to load the and manage the
	 * instances of {@link CurrencyUnitMapperSpi}. Depending on the runtime
	 * environment, implementations may be loaded using the
	 * {@link ServiceLoader}. But also alternate mechanisms are possible, e.g.
	 * CDI.
	 * 
	 * @param unit
	 *            The source unit, never {@code null}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @param timestamp
	 *            the target timestamp, may be null.
	 * @return The mapped {@link CurrencyUnit}, or null.
	 */
	public CurrencyUnit map(String targetNamespace, CurrencyUnit currencyUnit,
			Long timestamp);
}
