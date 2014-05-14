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
package javax.money;

import javax.money.spi.Bootstrap;
import javax.money.spi.CurrencyProviderSpi;
import javax.money.spi.MonetaryCurrenciesSingletonSpi;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory singleton for {@link CurrencyUnit} instances as provided by the
 * different registered {@link CurrencyProviderSpi} instances.
 * <p>
 * This class is thread safe.
 * 
 * @version 0.8
 * @author Anatole Tresch
 */
public final class MonetaryCurrencies {
    /**
     * The used {@link javax.money.spi.MonetaryCurrenciesSingletonSpi} instance.
     */
    private static final MonetaryCurrenciesSingletonSpi monetaryCurrenciesSpi = loadMonetaryCurrenciesSingletonSpi();

    /**
	 * Required for deserialization only.
	 */
	private MonetaryCurrencies() {
	}

    /**
     * Loads the SPI backing bean.
     * @return
     */
    private static MonetaryCurrenciesSingletonSpi loadMonetaryCurrenciesSingletonSpi(){
        try{
            return Bootstrap.getService(MonetaryCurrenciesSingletonSpi.class, new DefaultMonetaryCurrenciesSingletonSpi());
        }
        catch(Exception e){
            Logger.getLogger(MonetaryCurrencies.class.getName()).log(Level.SEVERE, "Failed to load MonetaryCurrenciesSingletonSpi, using default.", e);
            return new DefaultMonetaryCurrenciesSingletonSpi();
        }
    }

	/**
	 * Access a new instance based on the currency code. Currencies are
	 * available as provided by {@link CurrencyProviderSpi} instances registered
	 * with the {@link javax.money.spi.Bootstrap}.
	 * 
	 * @param currencyCode
	 *            the ISO currency code, not {@code null}.
	 * @return the corresponding {@link CurrencyUnit} instance.
	 * @throws UnknownCurrencyException
	 *             if no such currency exists.
	 */
	public static CurrencyUnit getCurrency(String currencyCode) {
        if(monetaryCurrenciesSpi==null){
            throw new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup.");
        }
        return monetaryCurrenciesSpi.getCurrency(currencyCode);
	}

	/**
	 * Access a new instance based on the {@link Locale}. Currencies are
	 * available as provided by {@link CurrencyProviderSpi} instances registered
	 * with the {@link javax.money.spi.Bootstrap}.
	 * 
	 * @param locale
	 *            the target {@link Locale}, typically representing an ISO
	 *            country, not {@code null}.
	 * @return the corresponding {@link CurrencyUnit} instance.
	 * @throws UnknownCurrencyException
	 *             if no such currency exists.
	 */
	public static CurrencyUnit getCurrency(Locale locale) {
        if(monetaryCurrenciesSpi==null){
            throw new MonetaryException("No MonetaryCurrenciesSingletonSpi loaded, check your system setup.");
        }
        return monetaryCurrenciesSpi.getCurrency(locale);
	}

	/**
	 * Allows to check if a {@link CurrencyUnit} instance is defined, i.e.
	 * accessible from {@link MonetaryCurrencies#getCurrency(String)}.
	 * 
	 * @param code
	 *            the currency code, not {@code null}.
	 * @return {@code true} if {@link MonetaryCurrencies#getCurrency(String)}
	 *         would return a result for the given code.
	 */
	public static boolean isCurrencyAvailable(String code) {
        if(monetaryCurrenciesSpi==null){
            return false;
        }
        return monetaryCurrenciesSpi.isCurrencyAvailable(code);
	}

	/**
	 * Allows to check if a {@link javax.money.CurrencyUnit} instance is
	 * defined, i.e. accessible from {@link #getCurrency(String)}.
	 * 
	 * @param locale
	 *            the target {@link Locale}, not {@code null}.
	 * @return {@code true} if {@link #getCurrency(Locale)} would return a
	 *         result for the given code.
	 */
	public static boolean isCurrencyAvailable(Locale locale) {
        if(monetaryCurrenciesSpi==null){
            return false;
        }
        return monetaryCurrenciesSpi.isCurrencyAvailable(locale);
	}

    public static Collection<CurrencyUnit> getCurrencies(){
        if(monetaryCurrenciesSpi==null){
            return Collections.emptySet();
        }
        return monetaryCurrenciesSpi.getCurrencies();
    }


    /**
     * Factory singleton for {@link javax.money.CurrencyUnit} instances as provided by the
     * different registered {@link javax.money.spi.CurrencyProviderSpi} instances.
     * <p>
     * This class is thread safe.
     *
     * @version 0.8
     * @author Anatole Tresch
     */
    private static final class DefaultMonetaryCurrenciesSingletonSpi implements MonetaryCurrenciesSingletonSpi{

        /**
         * Access a new instance based on the currency code. Currencies are
         * available as provided by {@link javax.money.spi.CurrencyProviderSpi} instances registered
         * with the {@link javax.money.spi.Bootstrap}.
         *
         * @param currencyCode
         *            the ISO currency code, not {@code null}.
         * @return the corresponding {@link javax.money.CurrencyUnit} instance.
         * @throws javax.money.UnknownCurrencyException
         *             if no such currency exists.
         */
        public CurrencyUnit getCurrency(String currencyCode) {
            Objects.requireNonNull(currencyCode);
            CurrencyUnit cu = null;
            for (CurrencyProviderSpi spi : Bootstrap
                    .getServices(
                            CurrencyProviderSpi.class)) {
                try {
                    cu = spi.getCurrencyUnit(currencyCode);
                    if (cu != null) {
                        if (!currencyCode.equals(cu.getCurrencyCode())) {
                            throw new IllegalStateException(
                                    "Provider("
                                            + spi.getClass().getName()
                                            + ") returned an invalid CurrencyUnit for '"
                                            + currencyCode + "': "
                                            + cu.getCurrencyCode());
                        }
                        return cu;
                    }
                } catch (Exception e) {
                    Logger.getLogger(DefaultMonetaryCurrenciesSingletonSpi.class.getName()).log(
                            Level.SEVERE,
                            "Error loading Currency '" + currencyCode
                                    + "' from provider: "
                                    + spi.getClass().getName(), e);
                }
            }
            if (cu == null) {
                throw new UnknownCurrencyException(currencyCode);
            }
            return cu;
        }

        /**
         * Access a new instance based on the {@link java.util.Locale}. Currencies are
         * available as provided by {@link javax.money.spi.CurrencyProviderSpi} instances registered
         * with the {@link javax.money.spi.Bootstrap}.
         *
         * @param locale
         *            the target {@link java.util.Locale}, typically representing an ISO
         *            country, not {@code null}.
         * @return the corresponding {@link javax.money.CurrencyUnit} instance.
         * @throws javax.money.UnknownCurrencyException
         *             if no such currency exists.
         */
        public CurrencyUnit getCurrency(Locale locale) {
            Objects.requireNonNull(locale);
            CurrencyUnit cu = null;
            for (CurrencyProviderSpi spi : Bootstrap.getServices(CurrencyProviderSpi.class)) {
                try {
                    cu = spi.getCurrencyUnit(locale);
                    if (cu != null) {
                        return cu;
                    }
                } catch (Exception e) {
                    Logger.getLogger(DefaultMonetaryCurrenciesSingletonSpi.class.getName()).log(
                            Level.SEVERE,
                            "Error loading Currency for Locale '" + locale
                                    + " from provider: "
                                    + spi.getClass().getName(), e);
                }
            }
            throw new UnknownCurrencyException(locale);
        }

        /**
         * Allows to check if a {@link javax.money.CurrencyUnit} instance is defined, i.e.
         * accessible from {@link javax.money.spi.MonetaryCurrenciesSingletonSpi#getCurrency(String)}.
         *
         * @param code
         *            the currency code, not {@code null}.
         * @return {@code true} if {@link javax.money.spi.MonetaryCurrenciesSingletonSpi#getCurrency(String)}
         *         would return a result for the given code.
         */
        public boolean isCurrencyAvailable(String code) {
            Objects.requireNonNull(code);
            try {
                getCurrency(code);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * Allows to check if a {@link CurrencyUnit} instance is
         * defined, i.e. accessible from {@link #getCurrency(String)}.
         *
         * @param locale
         *            the target {@link java.util.Locale}, not {@code null}.
         * @return {@code true} if {@link #getCurrency(java.util.Locale)} would return a
         *         result for the given code.
         */
        public boolean isCurrencyAvailable(Locale locale) {
            Objects.requireNonNull(locale);
            try {
                getCurrency(locale);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public Collection<CurrencyUnit> getCurrencies(){
            List<CurrencyUnit> result = new ArrayList<>();
            for (CurrencyProviderSpi spi : Bootstrap.getServices(CurrencyProviderSpi.class)) {
                try {
                    Collection<CurrencyUnit> cus = spi.getCurrencies();
                    if (cus != null) {
                        result.addAll(cus);
                    }
                } catch (Exception e) {
                    Logger.getLogger(DefaultMonetaryCurrenciesSingletonSpi.class.getName()).log(
                            Level.SEVERE,
                            "Error loading all Currencies from provider: "
                                    + spi.getClass().getName(), e);
                }
            }
            return result;
        }

    }

}
