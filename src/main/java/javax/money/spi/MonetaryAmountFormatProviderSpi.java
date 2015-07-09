/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2015, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import javax.money.format.AmountFormatQuery;
import javax.money.format.MonetaryAmountFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;

/**
 * SPI (formatting) providing {@link MonetaryAmountFormat} instances.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountFormatProviderSpi {

    /**
     * Access the provider's name.
     * @return this provider's name, not null.
     */
    default String getProviderName(){
        return getClass().getSimpleName();
    }

    /**
     * Create a new {@link MonetaryAmountFormat} for the given input.
     * 
     * @param formatQuery
     *            The {@link javax.money.format.AmountFormatContext} to be used.
     * @return An according {@link MonetaryAmountFormat} instance, or {@code null}, which delegates
     *         the request to subsequent {@link MonetaryAmountFormatProviderSpi} instances
     *         registered.
     */
    Collection<MonetaryAmountFormat> getAmountFormats(AmountFormatQuery formatQuery);

    /**
     * Gets a list with available locales for this format provider.
     * @return list of available locales, never null.
     */
    Set<Locale> getAvailableLocales();

    /**
     * Gets a list with available format names for this format provider.
     * @return list of available formats, never null.
     */
    Set<String> getAvailableFormatNames();
}
