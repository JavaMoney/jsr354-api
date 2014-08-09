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
package javax.money.spi;

import java.util.Set;

import javax.money.*;

/**
 * This SPI allows to extends/override the roundings available for
 * {@link CurrencyUnit}. The JSRs implementation already provides default
 * roundings. By registering instances of this interface using the
 * {@link javax.money.spi.Bootstrap}, the default behaviour can be
 * overridden and extended, e.g. for supporting also special roundings.
 * <p>
 * Implementations of this interface must be
 * <ul>
 * <li>thread-safe
 * <li>not require loading of other resources.
 * </ul>
 * If required, it is possible to implement this interface in a contextual way,
 * e.g. providing different roundings depending on the current EE application
 * context. Though in most cases rounding should be a general concept that does
 * not require contextual handling.
 * 
 * @author Anatole Tresch
 */
public interface RoundingProviderSpi {

    /**
     * Evaluate the rounding that match the given query.
     *
     * @return the matching rounding instance, or {@code null}.
     */
    MonetaryRounding getRounding(RoundingQuery query);

	/**
	 * Access the ids of the roundings defined by this provider.
	 * 
	 * @return the ids of the defined roundings, never {@code null}.
	 */
	Set<String> getRoundingNames();

    /**
     * Get the provider's unique name.
     * @return the provider's unique name, not {@code null}.
     */
    default String getProviderName(){
        return getClass().getSimpleName();
    }


}
