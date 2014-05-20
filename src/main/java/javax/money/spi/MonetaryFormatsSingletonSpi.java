/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import java.util.Locale;
import java.util.Set;

import javax.money.MonetaryException;
import javax.money.format.AmountFormatContext;
import javax.money.format.MonetaryAmountFormat;

/**
 * This interface models the singleton functionality of {@link javax.money.format.MonetaryFormats}.
 * <p/>
 * Implementations of this interface must be thread-safe.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface MonetaryFormatsSingletonSpi{

    /**
     * Access the default {@link MonetaryAmountFormat} given a {@link Locale}.
     *
     * @param locale the target {@link Locale}, not {@code null}.
     * @return the matching {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public default MonetaryAmountFormat getAmountFormat(Locale locale){
        return getAmountFormat(AmountFormatContext.of(locale));
    }

    /**
     * Access an {@link MonetaryAmountFormat} given a {@link AmountFormatContext}.
     *
     * @param style the target {@link AmountFormatContext}, not {@code null}.
     * @return the corresponding {@link MonetaryAmountFormat}
     * @throws MonetaryException if no registered {@link MonetaryAmountFormatProviderSpi} can provide a
     *                           corresponding {@link MonetaryAmountFormat} instance.
     */
    public MonetaryAmountFormat getAmountFormat(AmountFormatContext style);
    
    /**
     * Get all available locales. This equals to {@link MonetaryAmountFormatProviderSpi#getAvailableLocales()}.
     *
     * @return all available locales, never {@code null}.
     */
    public Set<Locale> getAvailableLocales();

}
