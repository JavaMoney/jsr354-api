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

import javax.money.CurrencyUnit;
import javax.money.MonetaryOperator;
import javax.money.RoundingContext;
import java.util.Set;

/**
 * This class models the accessor for rounding instances, modeled as
 * {@link javax.money.MonetaryOperator}.
 * <p>
 * This class is thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface MonetaryRoundingsSingletonSpi{


	/**
	 * Creates a rounding that can be added as {@link javax.money.MonetaryOperator} to
	 * chained calculations. The instance will lookup the concrete
	 * {@link javax.money.MonetaryOperator} instance from the {@link javax.money.spi.MonetaryRoundingsSingletonSpi}
	 * based on the input {@link javax.money.MonetaryAmount}'s {@link javax.money.CurrencyUnit}.
	 *
	 * @return the (shared) default rounding instance.
	 */
	default MonetaryOperator getDefaultRounding(){
        MonetaryOperator op = getRounding(RoundingContext.DEFAULT_ROUNDING_CONTEXT);
        if(op==null){
            throw new IllegalStateException("No default rounding provided.");
        }
        return op;
    }

	/**
	 * Creates an rounding instance using {@link java.math.RoundingMode#UP} rounding.
	 *
	 * @param roundingContext
	 *            The {@link javax.money.RoundingContext} defining the required rounding.
	 * @return the corresponding {@link javax.money.MonetaryOperator} implementing the
	 *         rounding.
	 * @throws javax.money.MonetaryException
	 *             if no such rounding could be evaluated.
	 */
	MonetaryOperator getRounding(RoundingContext roundingContext);

	/**
	 * Creates an {@link javax.money.MonetaryOperator} for rounding {@link javax.money.MonetaryAmount}
	 * instances given a currency.
	 *
	 * @param currencyUnit
	 *            The currency, which determines the required precision. As
	 *            {@link java.math.RoundingMode}, by default, {@link java.math.RoundingMode#HALF_UP}
	 *            is sued.
	 * @return a new instance {@link javax.money.MonetaryOperator} implementing the
	 *         rounding, never {@code null}.
	 */
	default MonetaryOperator getRounding(CurrencyUnit currencyUnit){
        MonetaryOperator op = getRounding(RoundingContext.of(currencyUnit));
        if(op==null){
            throw new IllegalStateException("No rounding provided for CurrencyUnit: " + currencyUnit.getCurrencyCode());
        }
        return op;
    }


	/**
	 * Access an {@link javax.money.MonetaryOperator} for a named rounding
	 * {@link javax.money.MonetaryAmount} instances.
	 *
	 * @param roundingId
	 *            The rounding identifier.
	 * @return the corresponding {@link javax.money.MonetaryOperator} implementing the
	 *         rounding, never {@code null}.
	 * @throws IllegalArgumentException
	 *             if no such rounding is registered using a
	 *             {@link javax.money.spi.RoundingProviderSpi} instance.
	 */
	default MonetaryOperator getRounding(String roundingId){
        MonetaryOperator op = getRounding(RoundingContext.of(roundingId));
        if(op==null){
            throw new IllegalStateException("No rounding provided with rounding id: " + roundingId);
        }
        return op;
    }

	/**
	 * Allows to access the identifiers of the current defined custom roundings.
	 *
	 * @return the set of custom rounding ids, never {@code null}.
	 */
	public Set<String> getRoundingIds();

}
