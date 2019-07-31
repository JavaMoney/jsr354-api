/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2019 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.money.spi;

import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryContext;

/**
 * SPI (core): Implementations of this interface are used by the {@link MonetaryAmountsSingletonSpi} to evaluate the
 * correct {@link javax.money.MonetaryAmountFactory} instances.
 *
 * @param <T> the concrete amount type.
 * @author Anatole Tresch
 */
public interface MonetaryAmountFactoryProviderSpi<T extends MonetaryAmount>{

    /**
     * Determines how the factory should be considered when querying for matching implementation
     * types calling {@link javax.money.Monetary#getAmountFactory(javax.money.MonetaryAmountFactoryQuery)} .
     *
     * @see javax.money.Monetary#getAmountFactory(javax.money.MonetaryAmountFactoryQuery)
     */
    enum QueryInclusionPolicy{
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
     * {@link javax.money.Monetary#getAmountFactory(javax.money.MonetaryAmountFactoryQuery)}.
     *
     * @return {@code true} to include this factory into the evaluation.
     * @see javax.money.Monetary#getAmountFactory(javax.money.MonetaryAmountFactoryQuery)
     */
    default QueryInclusionPolicy getQueryInclusionPolicy(){
        return QueryInclusionPolicy.ALWAYS;
    }

    /**
     * Get the concrete amount type created by {@link javax.money.MonetaryAmountFactory} instances provided.
     *
     * @return the concrete amount type created, never null.
     */
    Class<T> getAmountType();

    /**
     * Access a {@link javax.money.MonetaryAmountFactory} given the required context.
     *
     * @return the corresponding {@link javax.money.MonetaryAmountFactory}, or {@code null}.
     */
    MonetaryAmountFactory<T> createMonetaryAmountFactory();

    /**
     * Returns the default {@link MonetaryContext} used, when no {@link MonetaryContext} is
     * provided.
     * <p>
     * The default context is not allowed to exceed the capabilities of the maximal
     * {@link MonetaryContext} supported.
     *
     * @return the default {@link MonetaryContext}, never {@code null}.
     * @see #getMaximalMonetaryContext()
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
