/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import javax.money.MonetaryException;
import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryAmountFormatProviderSpi;
import javax.money.spi.MonetaryFormatsSingletonSpi;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class models the singleton accessor for {@link MonetaryAmountFormat} instances.
 * <p>
 * This class is thread-safe.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryFormats{

    private static final MonetaryFormatsSingletonSpi monetaryFormatsSingletonSpi = loadMonetaryFormatsSingletonSpi();

    /**
     * Private singleton constructor.
     */
    private MonetaryFormats(){
        // Singleton
    }

    /**
     * Loads the SPI backing bean.
     *
     * @return the instance of MonetaryFormatsSingletonSpi to be used by this singleton.
     */
    private static MonetaryFormatsSingletonSpi loadMonetaryFormatsSingletonSpi(){
        try{
            return Bootstrap.getService(MonetaryFormatsSingletonSpi.class);
        }
        catch(Exception e){
            Logger.getLogger(MonetaryFormats.class.getName())
                    .log(Level.SEVERE, "Failed to load MonetaryFormatsSingletonSpi, using default.", e);
            return new DefaultMonetaryFormatsSingletonSpi();
        }
    }

    /**
     * Access the default {@link MonetaryAmountFormat} given a {@link Locale}.
     *
     * @param locale the target {@link Locale}, not {@code null}.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public static MonetaryAmountFormat getAmountFormat(Locale locale){
        return getAmountFormat(AmountFormatQueryBuilder.create(locale).setLocale(locale).build());
    }

    /**
     * Access the default {@link MonetaryAmountFormat} given a {@link Locale}.
     *
     * @param formatQuery the required {@link AmountFormatQuery}, not {@code null}.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public static MonetaryAmountFormat getAmountFormat(AmountFormatQuery formatQuery){
        return monetaryFormatsSingletonSpi.getAmountFormat(formatQuery);
    }

    /**
     * Access the default {@link MonetaryAmountFormat} given a {@link Locale}.
     *
     * @param formatQuery the required {@link AmountFormatQuery}, not {@code null}.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public static Collection<MonetaryAmountFormat> getAmountFormats(AmountFormatQuery formatQuery){
        return monetaryFormatsSingletonSpi.getAmountFormats(formatQuery);
    }

    /**
     * Access the a {@link MonetaryAmountFormat} given its styleId.
     *
     * @param styleId the target styleId, not {@code null}.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public static MonetaryAmountFormat getAmountFormat(String styleId){
        return getAmountFormat(AmountFormatQueryBuilder.create(styleId).setStyleId(styleId).build());
    }

    /**
     * Get all available locales. This equals to {@link MonetaryAmountFormatProviderSpi#getAvailableLocales()}.
     *
     * @return all available locales, never {@code null}.
     */
    public static final Set<Locale> getAvailableLocales(){
        return monetaryFormatsSingletonSpi.getAvailableLocales();
    }

    /**
     * This class models the singleton accessor for {@link javax.money.format.MonetaryAmountFormat} instances.
     * <p>
     * This class is thread-safe.
     *
     * @author Anatole Tresch
     * @author Werner Keil
     */
    public static final class DefaultMonetaryFormatsSingletonSpi implements MonetaryFormatsSingletonSpi{

        /**
         * Access an {@link javax.money.format.MonetaryAmountFormat} given a {@link javax.money.format
         * .AmountFormatContext}.
         *
         * @param formatQuery The format query defining the requirements of the formatter.
         * @return the corresponding {@link javax.money.format.MonetaryAmountFormat}
         * @throws javax.money.MonetaryException if no registered {@link javax.money.spi
         *                                       .MonetaryAmountFormatProviderSpi} can provide a
         *                                       corresponding {@link javax.money.format.MonetaryAmountFormat} instance.
         */
        public Collection<MonetaryAmountFormat> getAmountFormats(AmountFormatQuery formatQuery){
            Collection<MonetaryAmountFormat> result = new ArrayList<>();
            for(MonetaryAmountFormatProviderSpi spi : Bootstrap.getServices(MonetaryAmountFormatProviderSpi.class)){
                Collection<MonetaryAmountFormat> formats = spi.getAmountFormats(formatQuery);
                if(Objects.nonNull(formats)){
                    result.addAll(formats);
                }
            }
            return result;
        }


        /**
         * Get all available locales. This equals to {@link javax.money.spi
         * .MonetaryAmountFormatProviderSpi#getAvailableLocales()}.
         *
         * @return all available locales, never {@code null}.
         */
        public Set<Locale> getAvailableLocales(String... providerNames){
            Set<Locale> locales = new HashSet<>();
            for(MonetaryAmountFormatProviderSpi spi : Bootstrap.getServices(MonetaryAmountFormatProviderSpi.class)){
                locales.addAll(spi.getAvailableLocales());
            }
            return locales;
        }

    }
}
