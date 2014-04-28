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
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * This class models the singleton accessor for {@link MonetaryAmountFormat} instances.
 * <p/>
 * This class is thread-safe.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryFormats{

    /**
     * Private singleton constructor.
     */
    private MonetaryFormats(){
        // Singleton
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
        Objects.requireNonNull(locale, "Locale required");
        AmountFormatContext format = AmountFormatContext.of(locale);
        for(MonetaryAmountFormatProviderSpi spi : Bootstrap.getServices(MonetaryAmountFormatProviderSpi.class)){
            MonetaryAmountFormat f = spi.getAmountFormat(format);
            if(f != null){
                return f;
            }
        }
        throw new MonetaryException("No MonetaryAmountFormat for locale " + locale);
    }

    /**
     * Access an {@link MonetaryAmountFormat} given a {@link AmountFormatContext}.
     *
     * @param style the target {@link AmountFormatContext}, not {@code null}.
     * @return the corresponding {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public static MonetaryAmountFormat getAmountFormat(AmountFormatContext style){
        Objects.requireNonNull(style, "AmountFormatContext required");
        for(MonetaryAmountFormatProviderSpi spi : Bootstrap.getServices(MonetaryAmountFormatProviderSpi.class)){
            MonetaryAmountFormat f = spi.getAmountFormat(style);
            if(f != null){
                return f;
            }
        }
        throw new MonetaryException("No MonetaryAmountFormat for style " + style);
    }

    /**
     * Get all available locales. This equals to {@link MonetaryAmountFormatProviderSpi#getAvailableLocales()}.
     *
     * @return all available locales, never {@code null}.
     */
    public static final Set<Locale> getAvailableLocales(){
        Set<Locale> locales = new HashSet<>();
        for(MonetaryAmountFormatProviderSpi spi : Bootstrap.getServices(MonetaryAmountFormatProviderSpi.class)){
            locales.addAll(spi.getAvailableLocales());
        }
        return locales;
    }

}
