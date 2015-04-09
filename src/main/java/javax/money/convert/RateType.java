/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money.convert;

/**
 * This enumeration defines the different type of rates that can be provided by {@link javax.money.convert
 * .ExchangeRateProvider} implementations. Hereby the rate provider's {@link javax.money.convert.ProviderContext} can
 * contain
 * additional information about the rates provided. Similarly, when accessing {@link javax.money.convert
 * .ExchangeRateProvider} or {@link javax.money.convert.CurrencyConversion} instances corresponding attributes can
 * be passed within an (optional) {@link javax.money.convert.ConversionContext}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public enum RateType{
    /**
     * Historic rates, e.g. from the day before or older.
     */
    HISTORIC,
    /**
     * Real-time rates should be as adequate as possible, basically not more than a few milliseconds late.
     */
    REALTIME,
    /**
     * Deferred rates are basically also current rates, but have some fixed delay, e.g. 20 minutes.
     */
    DEFERRED,
    /**
     * Any other type of rates. You may use the {@link javax.money.convert.ProviderContext},
     * {@link javax.money.convert.ConversionContext} to define additional details.
     */
    OTHER,
    /**
     * Any type of rates. This can be used, where any type of rate is suitable. This may be feasible if you
     * access a specific chain of rate providers,
     * where the order of providers already implies a prioritization and the rate type in that scenario is not relevant.
     */
    ANY
} 