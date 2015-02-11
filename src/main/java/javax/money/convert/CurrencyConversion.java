/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import javax.money.*;

/**
 * This interface defines a {@link CurrencyConversion} that is converting a {@link MonetaryAmount} to another
 * {@link MonetaryAmount} with a different target {@link CurrencyUnit}. Each instance of this class is bound to
 * a specific {@link ExchangeRateProvider} (or a chain of rate providers), a term {@link CurrencyUnit} and
 * (optionally) a target timestamp. Additionally the {@link javax.money.convert.ConversionContext} can have additional
 * attributes set that are passed to the rate provider (chain).
 * <p>
 * This interface serves a an API for the clients, but also must be implemented
 * and registered as SPI to the mechanisms required by the
 * {@link javax.money.spi.MonetaryConversionsSingletonSpi} implementation.
 * <p>
 * By extending {@link MonetaryOperator} currency conversion can simply be applied on each {@link MonetaryAmount}
 * calling the amount'0s with method:
 * <pre>
 *     MonetaryAmount amount = ...;
 *     CurrencyConversion conversion = MonetaryConversions.getConversion("CHF");
 *     MonetaryAmount amountInCHF = amount.with(conversion);
 * </pre>
 * <p>
 * The terminating {@link CurrencyUnit} of this conversion instance can be
 * accessed from {@code getCurrency()}, inherited from {@code CurrencySupplier}.
 * <p>
 * Instances of this class are required to be thread-safe, but it is not a
 * requirement that they are serializable. In a EE context they can be
 * implemented using contextual beans.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface CurrencyConversion extends MonetaryOperator, CurrencySupplier {

    /**
     * Access the target {@link ConversionContext} of this conversion instance.
     *
     * @return the target {@link ConversionContext}.
     */
    ConversionContext getContext();

    /**
     * Get the {@link ExchangeRate} applied for the given {@link MonetaryAmount}
     * .
     *
     * @param sourceAmount the amount to be converted.
     * @return the {@link ExchangeRate} applied.
     * @throws MonetaryException if the amount can not be converted.
     */
    ExchangeRate getExchangeRate(MonetaryAmount sourceAmount);

    /**
     * Access the underlying {@link javax.money.convert.ExchangeRateProvider}.
     *
     * @return the underlying {@link javax.money.convert.ExchangeRateProvider}, never null.
     */
    ExchangeRateProvider getExchangeRateProvider();

}
