/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import java.util.Collection;
import java.util.Locale;

import javax.money.format.AmountFormatSymbols;

/**
 * SPI providing {@link AmountFormatSymbols} instances. <h3>Implementation specification</h3>
 * Instances of this interfaces must be
 * <ul>
 * <li>thread-safe
 * </ul>
 * Instances of this interfaces can deliver different results for different runtime scenarios
 * (behave contextual dependent).
 * 
 * @author Anatole Tresch
 */
public interface AmountFormatSymbolsProviderSpi {

	/**
	 * Create a new {@link AmountFormatSymbols} for the given {@link Locale}.
	 * 
	 * @param locale
	 *            The {@link Locale} to be used, not {@code null}.
	 * @return An according {@link AmountFormatSymbols} instance, or {@code null}, which delegates
	 *         the request to subsequent {@link AmountFormatSymbolsProviderSpi} instances
	 *         registered.
	 */
	public AmountFormatSymbols getAmountFormatSymbols(Locale locale);

	/**
	 * Access the locales supported by this instance.
	 * 
	 * @return the {@link Locale} instances, never {@code null}.
	 */
	public Collection<Locale> getSupportedLocales();

}
