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
package javax.money;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryCurrenciesSingletonSpi;
import javax.money.spi.MonetaryRoundingsSingletonSpi;
import javax.money.spi.RoundingProviderSpi;

/**
 * This class models the accessor for rounding instances, modeled as
 * {@link javax.money.MonetaryOperator}.
 * <p>
 * This class is thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryRoundings {

    /**
     * An adaptive rounding instance that transparently looks up the correct
     * rounding.
     */
    private static final MonetaryOperator DEFAULT_ROUNDING = new DefaultCurrencyRounding();

    /**
     * The used {@link javax.money.spi.MonetaryCurrenciesSingletonSpi} instance.
     */
    private static final MonetaryRoundingsSingletonSpi monetaryRoundingsSpi = loadMonetaryRoundingsSingletonSpi();

	/**
	 * Private singleton constructor.
	 */
	private MonetaryRoundings() {
		// Singleton
	}

    /**
     * Loads the SPI backing bean.
     * @return an instance of MonetaryRoundingsSingletonSpi.
     */
    private static MonetaryRoundingsSingletonSpi loadMonetaryRoundingsSingletonSpi(){
        try{
            return Bootstrap.getService(MonetaryRoundingsSingletonSpi.class, new DefaultMonetaryRoundingsSingletonSpi());
        }
        catch(Exception e){
            Logger.getLogger(MonetaryCurrencies.class.getName()).log(Level.SEVERE, "Failed to load MonetaryCurrenciesSingletonSpi, using default.", e);
            return new DefaultMonetaryRoundingsSingletonSpi();
        }
    }

	/**
	 * Creates a rounding that can be added as {@link MonetaryOperator} to
	 * chained calculations. The instance will lookup the concrete
	 * {@link MonetaryOperator} instance from the {@link MonetaryRoundings}
	 * based on the input {@link MonetaryAmount}'s {@link CurrencyUnit}.
	 * 
	 * @return the (shared) default rounding instance.
	 */
	public static MonetaryOperator getRounding() {
		return monetaryRoundingsSpi.getDefaultRounding();
	}

	/**
	 * Creates an rounding instance using {@link java.math.RoundingMode#UP} rounding.
	 * 
	 * @param roundingContext
	 *            The {@link RoundingContext} defining the required rounding.
	 * @return the corresponding {@link MonetaryOperator} implementing the
	 *         rounding.
	 * @throws MonetaryException
	 *             if no such rounding could be evaluated.
	 */
	public static MonetaryOperator getRounding(RoundingContext roundingContext) {
        return monetaryRoundingsSpi.getRounding(roundingContext);
	}

	/**
	 * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
	 * instances given a currency.
	 * 
	 * @param currencyUnit
	 *            The currency, which determines the required precision. As
	 *            {@link java.math.RoundingMode}, by default, {@link java.math.RoundingMode#HALF_UP}
	 *            is sued.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding, never {@code null}.
	 */
	public static MonetaryOperator getRounding(CurrencyUnit currencyUnit) {
		return monetaryRoundingsSpi.getRounding(currencyUnit);
	}


	/**
	 * Access an {@link MonetaryOperator} for custom rounding
	 * {@link MonetaryAmount} instances.
	 * 
	 * @param roundingId
	 *            The rounding identifier.
	 * @return the corresponding {@link MonetaryOperator} implementing the
	 *         rounding, never {@code null}.
	 * @throws IllegalArgumentException
	 *             if no such rounding is registered using a
	 *             {@link RoundingProviderSpi} instance.
	 */
	public static MonetaryOperator getRounding(String roundingId) {
        return monetaryRoundingsSpi.getRounding(roundingId);
	}

	/**
	 * Allows to access the identifiers of the current defined custom roundings.
	 * 
	 * @return the set of custom rounding ids, never {@code null}.
	 */
	public static Set<String> getRoundingIds() {
		return monetaryRoundingsSpi.getRoundingIds();
	}

	/**
	 * Default Rounding that rounds a {@link MonetaryAmount} based on the
	 * amount's {@link CurrencyUnit}.
	 * 
	 * @author Anatole Tresch
	 */
	private static final class DefaultCurrencyRounding implements
			MonetaryOperator {
		@Override
		public MonetaryAmount apply(MonetaryAmount amount) {
			MonetaryOperator r = MonetaryRoundings.getRounding(amount
					.getCurrency());
			return r.apply(amount);
		}
	}

    /**
     * This class models the accessor for rounding instances, modeled as
     * {@link MonetaryOperator}.
     * <p>
     * This class is thread-safe.
     *
     * @author Anatole Tresch
     * @author Werner Keil
     */
    private static final class DefaultMonetaryRoundingsSingletonSpi implements MonetaryRoundingsSingletonSpi{

        /**
         * Creates an rounding instance using {@link java.math.RoundingMode#UP} rounding.
         *
         * @param roundingContext The {@link javax.money.RoundingContext} defining the required rounding.
         * @return the corresponding {@link javax.money.MonetaryOperator} implementing the
         * rounding.
         * @throws javax.money.MonetaryException if no such rounding could be evaluated.
         */
        public MonetaryOperator getRounding(RoundingContext roundingContext){
            Objects.requireNonNull(roundingContext, "RoundingContext required.");
            if(RoundingContext.DEFAULT_ROUNDING_CONTEXT.equals(roundingContext)){
                return DEFAULT_ROUNDING;
            }

            for(RoundingProviderSpi prov : Bootstrap.getServices(RoundingProviderSpi.class)){
                try{
                    MonetaryOperator op = prov.getRounding(roundingContext);
                    if(op != null){
                        return op;
                    }
                }
                catch(Exception e){
                    Logger.getLogger(DefaultMonetaryRoundingsSingletonSpi.class.getName())
                            .log(Level.SEVERE, "Error loading RoundingProviderSpi from ptovider: " + prov, e
                            );
                }
            }
            throw new MonetaryException("No Rounding found matching " + roundingContext);
        }

        /**
         * Allows to access the identifiers of the current defined custom roundings.
         *
         * @return the set of custom rounding ids, never {@code null}.
         */
        public Set<String> getRoundingIds(){
            Set<String> result = new HashSet<>();
            for(RoundingProviderSpi prov : Bootstrap.getServices(RoundingProviderSpi.class)){
                try{
                    result.addAll(prov.getRoundingIds());
                }
                catch(Exception e){
                    Logger.getLogger(DefaultMonetaryRoundingsSingletonSpi.class.getName())
                            .log(Level.SEVERE, "Error loading RoundingProviderSpi from provider: " + prov, e
                            );
                }
            }
            return result;
        }
    }

}