/**
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
package javax.money.convert;

import javax.money.convert.spi.MonetaryConversionsSingletonSpi;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This singleton defines access to the exchange and currency conversion logic
 * of JavaMoney.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryConversions {

    /**
     * The SPI currently active, use {@link ServiceLoader} to register an
     * alternate implementation.
     */
    private static final MonetaryConversionsSingletonSpi MONETARY_CONVERSION_SPI = loadMonetaryConversionSpi();

    /**
     * Private singleton constructor.
     */
    private MonetaryConversions() {
    }

    /**
     * Method that loads the {@link MonetaryConversionsSpi} on class loading.
     *
     * @return the instance ot be registered into the shared variable.
     */
    private static MonetaryConversionsSingletonSpi loadMonetaryConversionSpi() {
        try {
            // try loading directly from ServiceLoader
            Iterator<MonetaryConversionsSingletonSpi> instances = ServiceLoader.load(
                    MonetaryConversionsSingletonSpi.class).iterator();
            MonetaryConversionsSingletonSpi spiLoaded = null;
            if (instances.hasNext()) {
                spiLoaded = instances.next();
                if (instances.hasNext()) {
                    throw new IllegalStateException("Ambigous reference to spi (only "
                        + "one can be registered: " + MonetaryConversionsSingletonSpi.class.getName());
                }
                return spiLoaded;
            }
        } catch (Throwable e) {
            Logger.getLogger(MonetaryConversions.class.getName()).log(
                    Level.INFO,
                    "No MonetaryConversionSpi registered, using  default.", e);
        }
        return new DefaultMonetaryConversionsSpi();
    }

    /**
     * Access an instance of {@link ConversionProvider} for the given
     * {@link ExchangeRateType}.
     *
     * @param type the exchange rate type that this provider instance is
     * providing data for, not {@code null}.
     *
     * @return the {@link ExchangeRateType} if this instance.
     */
    public static ConversionProvider getConversionProvider(ExchangeRateType type) {
        return MONETARY_CONVERSION_SPI.getConversionProvider(type);
    }

    /**
     * Return all supported {@link ExchangeRateType} instances for which
     * {@link ConversionProvider} instances can be obtained.
     *
     * @return all supported {@link ExchangeRateType} instances, never
     * {@code null}.
     */
    public static Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
        return MONETARY_CONVERSION_SPI.getSupportedExchangeRateTypes();
    }

    /**
     * CHecks if a {@link ConversionProvider} can be accessed for the given
     * {@link ExchangeRateType}.
     *
     * @param type the required {@link ExchangeRateType}, not {@code null}.
     * @return true, if a {@link ConversionProvider} for this
     * {@link ExchangeRateType} can be obtained from this
     * {@link MonetaryConversions} instance.
     */
    public static boolean isSupportedExchangeRateType(ExchangeRateType type) {
        return MONETARY_CONVERSION_SPI.isSupportedExchangeRateType(type);
    }

    /**
     * This class represents the default implementation of
     * {@link MonetaryConversionsSpi} used always when no alternative is
     * registered within the {@link ServiceLoader}.
     *
     * @author Anatole Tresch
     *
     */
    private final static class DefaultMonetaryConversionsSpi implements
            MonetaryConversionsSingletonSpi {

        /**
         * The default does not provide any {@link ConversionProvider} as of
         * now.
         */
        @Override
        public ConversionProvider getConversionProvider(ExchangeRateType type) {
//            TODO Check by TCK!
//            if (type == null) {
//                throw new IllegalArgumentException("Unsupported Conversion Type: "
//                    + type);
//            }
            throw new IllegalArgumentException(
                    "Unsupported ExchangeRateType type: " + type);
        }

        /**
         * Returns always an empty collection.
         */
        @Override
        public Collection<ExchangeRateType> getSupportedExchangeRateTypes() {
//            TODO Check by TCK!
//            Collection<ExchangeRateType> rates = MONETARY_CONVERSION_SPI
//                .getSupportedExchangeRateTypes();
//        if (rates == null) {
//            throw new IllegalStateException(
//                    "Invalid MonetaryConversionSpi: must return non null collection for supported rate types.");
//        }
//        return rates;
            return Collections.emptySet();
        }

        /**
         * Returns always false.
         */
        @Override
        public boolean isSupportedExchangeRateType(ExchangeRateType type) {
            return false;
        }
    }
}
