/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import javax.money.format.AmountFormatContext;
import javax.money.format.MonetaryAmountFormat;
import java.util.Locale;
import java.util.Set;

/**
 * SPI (formatting) providing {@link MonetaryAmountFormat} instances.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountFormatProviderSpi {



	/**
	 * Create a new {@link MonetaryAmountFormat} for the given input.
	 * 
	 * @param formatStyle
	 *            The {@link javax.money.format.AmountFormatContext} to be used.
	 * @return An according {@link MonetaryAmountFormat} instance, or {@code null}, which delegates
	 *         the request to subsequent {@link MonetaryAmountFormatProviderSpi} instances
	 *         registered.
	 */
	public MonetaryAmountFormat getAmountFormat(AmountFormatContext formatStyle);

    /**
     * Gets a list with available locales for this format provider.
     * @return list of available locales, never null.
     */
    public Set<Locale> getAvailableLocales();

}
