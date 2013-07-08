/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.money.convert.spi;

import java.util.Collection;
import javax.money.convert.ConversionProvider;
import javax.money.convert.ExchangeRateType;

/**
 * This is the SPI to be implemented that determines how the different
 * components are loaded and managed.
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
