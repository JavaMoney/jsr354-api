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
package javax.money.convert.spi;

import java.util.Collection;
import javax.money.convert.ConversionProvider;
import javax.money.convert.ExchangeRateType;

/**
 * This is the spi to be implemented that is registered into the
 * {@code MonetaryConversion} singleton accessor using {@code ServiceLoader}.
 * This interface allows to implement the component loading differently, e.g.
 * using the {@code ServiceLoader} or by using aan IoC container.
 *
 * @author Anatole Tresch
 */
public interface MonetaryConversionsSingletonSpi {

    /**
     * Access an instance of {@link ConversionProvider}.
     *
     * @param type The rate type.
     * @return the provider, if it is a registered rate type, never null.
     * @see #isSupportedExchangeRateType(ExchangeRateType)
     */
    ConversionProvider getConversionProvider(ExchangeRateType type);

    /**
     * Get all currently registered rate types.
     *
     * @return all currently registered rate types
     */
    Collection<ExchangeRateType> getSupportedExchangeRateTypes();

    /**
     * Allows to quickly check, if a rate type is supported.
     *
     * @param type the rate type
     * @return true, if the rate is supported, meaning an according
     * {@link ConversionProvider} can be loaded.
     * @see #getConversionProvider(ExchangeRateType)
     */
    boolean isSupportedExchangeRateType(ExchangeRateType type);
}
