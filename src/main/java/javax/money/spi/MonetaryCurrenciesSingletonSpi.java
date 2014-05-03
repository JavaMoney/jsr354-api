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

import javax.money.CurrencyUnit;
import java.util.*;

/**
 * Factory singleton backing interface for {@link javax.money.MonetaryCurrencies} that provides access to
 * different registered {@link javax.money.spi.CurrencyProviderSpi} instances.
 * <p>
 * Implementations of this interface must be thread safe.
 *
 * @version 0.8
 * @author Anatole Tresch
 */
public interface MonetaryCurrenciesSingletonSpi{

	/**
	 * Access a new instance based on the currency code. Currencies are
	 * available as provided by {@link javax.money.spi.CurrencyProviderSpi} instances registered
	 * with the {@link Bootstrap}.
	 *
	 * @param currencyCode
	 *            the ISO currency code, not {@code null}.
	 * @return the corresponding {@link javax.money.CurrencyUnit} instance.
	 * @throws javax.money.UnknownCurrencyException
	 *             if no such currency exists.
	 */
	public CurrencyUnit getCurrency(String currencyCode);

	/**
	 * Access a new instance based on the {@link java.util.Locale}. Currencies are
	 * available as provided by {@link javax.money.spi.CurrencyProviderSpi} instances registered
	 * with the {@link Bootstrap}.
	 *
	 * @param locale
	 *            the target {@link java.util.Locale}, typically representing an ISO
	 *            country, not {@code null}.
	 * @return the corresponding {@link javax.money.CurrencyUnit} instance.
	 * @throws javax.money.UnknownCurrencyException
	 *             if no such currency exists.
	 */
	public CurrencyUnit getCurrency(Locale locale);

	/**
	 * Allows to check if a {@link javax.money.CurrencyUnit} instance is defined, i.e.
	 * accessible from {@link javax.money.spi.MonetaryCurrenciesSingletonSpi#getCurrency(String)}.
	 *
	 * @param code
	 *            the currency code, not {@code null}.
	 * @return {@code true} if {@link javax.money.spi.MonetaryCurrenciesSingletonSpi#getCurrency(String)}
	 *         would return a result for the given code.
	 */
	public boolean isCurrencyAvailable(String code);

	/**
	 * Allows to check if a {@link javax.money.CurrencyUnit} instance is
	 * defined, i.e. accessible from {@link #getCurrency(String)}.
	 *
	 * @param locale
	 *            the target {@link java.util.Locale}, not {@code null}.
	 * @return {@code true} if {@link #getCurrency(java.util.Locale)} would return a
	 *         result for the given code.
	 */
	public boolean isCurrencyAvailable(Locale locale);

    /**
     * Provide access to all currently known currencies.
     * @return a collection of all known currencies, never null.
     */
    public Collection<CurrencyUnit> getCurrencies();
}
