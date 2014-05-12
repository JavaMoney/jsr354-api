/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryAmounts;
import javax.money.MonetaryContext;

/**
 * SPI (core): Implementations of this interface are used by the {@link MonetaryAmountsSingletonSpi} to evaluate the
 * correct {@link MonetaryAmountFactory} instances.
 * 
 * @param <T>
 *            the concrete amount type.
 * @author Anatole Tresch
 */
public interface MonetaryAmountFactoryProviderSpi<T extends MonetaryAmount> {

	/**
	 * Determines how the factory should be considered when querying for matching implementation
	 * types calling {@link MonetaryAmounts#queryAmountType(MonetaryContext)}.
	 * 
	 * @see MonetaryAmounts#queryAmountType(MonetaryContext)
	 */
	public static enum QueryInclusionPolicy {
		/**
		 * Always include this factory (and the corresponding amount type) within queries. This is
		 * the default for normal {@link MonetaryAmount} implementation types.
		 */
		ALWAYS,
		/**
		 * Only consider this factory, when the implementation type is specified explicitly in the
		 * {@link MonetaryContext} required.
		 */
		DIRECT_REFERENCE_ONLY,
		/**
		 * Never consider this factory in a query for a matching {@link MonetaryAmount}
		 * implementation.
		 */
		NEVER
	}


	/**
	 * Method that determines if this factory should be considered for general evaluation of
	 * matching {@link MonetaryAmount} implementation types when calling
	 * {@link MonetaryAmounts#queryAmountType(MonetaryContext)}.
	 * 
	 * @see MonetaryAmounts#queryAmountType(MonetaryContext)
	 * @return {@code true} to include this factory into the evaluation.
	 */
	default QueryInclusionPolicy getQueryInclusionPolicy(){
        return QueryInclusionPolicy.ALWAYS;
    }
	
	/**
	 * Get the concrete amount type created by {@link MonetaryAmountFactory} instances provided.
	 * 
	 * @return the concrete amount type created, never null.
	 */
	Class<T> getAmountType();

	/**
	 * Access a {@link MonetaryAmountFactory} given the required context.
	 * 
	 * @return the corresponding {@link MonetaryAmountFactory}, or {@code null}.
	 */
	MonetaryAmountFactory<T> createMonetaryAmountFactory();

	/**
	 * Returns the default {@link MonetaryContext} used, when no {@link MonetaryContext} is
	 * provided.
	 * <p>
	 * The default context is not allowed to exceed the capabilities of the maximal
	 * {@link MonetaryContext} supported.
	 * 
	 * @see #getMaximalMonetaryContext()
	 * @return the default {@link MonetaryContext}, never {@code null}.
	 */
	MonetaryContext getDefaultMonetaryContext();

	/**
	 * Returns the maximal {@link MonetaryContext} supported, for requests that exceed these maximal
	 * capabilities, an {@link ArithmeticException} must be thrown.
	 * 
	 * @return the maximal {@link MonetaryContext} supported, never {@code null}
	 */
	default MonetaryContext getMaximalMonetaryContext(){
        return getDefaultMonetaryContext();
    }

}
