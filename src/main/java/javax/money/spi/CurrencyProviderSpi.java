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
package javax.money.spi;

import java.util.Locale;
import java.util.ServiceLoader;

import javax.money.CurrencyUnit;

/**
 * SPI to be registered using the {@link ServiceLoader}, which allows to
 * register/provide additional currencies into the system automatically on
 * startup. The implementation is allowed to be implemented in y contextual way,
 * so depending on the runtime context, different currencies may be available.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyProviderSpi {

	/**
	 * Return a (current) {@link CurrencyUnit} matching the given currency code.
	 * 
	 * @param currencyCode
	 *            the currency code. not null.
	 * @return the corresponding {@link CurrencyUnit}, or null, if no such unit
	 *         is provided by this provider.
	 */
	public CurrencyUnit getCurrencyUnit(String currencyCode);

	/**
	 * Return a (historic) {@link CurrencyUnit} matching the given currency
	 * code.
	 * 
	 * @param currencyCode
	 *            the currency code. not null.
	 * @param timestamp
	 *            the UTC timestamp of the time, when the {@link CurrencyUnit}
	 *            should be valid.
	 * @return the corresponding {@link CurrencyUnit}, or null, if no such unit
	 *         is provided by this provider.
	 */
	public CurrencyUnit getCurrencyUnit(String currencyCode, long timestamp);

	/**
	 * Return a (current) {@link CurrencyUnit} matching the given (country)
	 * {@link Locale}.
	 * 
	 * @param code
	 *            the currency code. not null.
	 * @return the corresponding {@link CurrencyUnit}, or null, if no such unit
	 *         is provided by this provider.
	 */
	public CurrencyUnit getCurrencyUnit(Locale locale);

	/**
	 * Return a (historic) {@link CurrencyUnit} matching the given (country)
	 * {@link Locale}.
	 * 
	 * @param locale
	 *            the currency {@link Locale}. not null.
	 * @param timestamp
	 *            the UTC timestamp of the time, when the {@link CurrencyUnit}
	 *            should be valid.
	 * @return the corresponding {@link CurrencyUnit}, or null, if no such unit
	 *         is provided by this provider.
	 */
	public CurrencyUnit getCurrencyUnit(Locale locale, long timestamp);

}
