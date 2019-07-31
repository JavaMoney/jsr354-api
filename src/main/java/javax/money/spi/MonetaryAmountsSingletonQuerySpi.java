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
import javax.money.MonetaryAmountFactoryQuery;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * SPI (core) for the backing implementation of the {@link javax.money.Monetary} singleton, implementing
 * the query functionality for amounts.
 *
 * @author Anatole Tresch
 */
public interface MonetaryAmountsSingletonQuerySpi {

    /**
     * Get the {@link javax.money.MonetaryAmountFactory} implementation class, that best matches to cover the given
     * {@link javax.money.MonetaryContext}.
     * <p>
     * The evaluation order should consider the following aspects:
     * <ul>
     * <li>If {@link javax.money.MonetaryContext#getAmountType()} is explicitly defined, it should be considered.
     * Nevertheless if precision/scale cannot be met, a {@link javax.money.MonetaryException} should
     * be thrown.
     * <li>The remaining implementation class candidates must cover the required precision.
     * <li>The remaining implementation class candidates must cover the required max scale.
     * <li>If max scale is met, but {@code precision==0} (unlimited precision), the
     * {@link javax.money.MonetaryAmount} implementation candidate should be chosen with highest possible
     * precision.
     * <li>If still multiple implementation candidates qualify, the ones with
     * {@code Flavor.PERFORMANCE} are preferred.
     * <li>After this point the selection may be arbitrary.
     * </ul>
     *
     * @param query the factory query, not null.
     * @return the {@link javax.money.MonetaryAmount} implementation class, that best matches to cover the given
     * {@link javax.money.MonetaryContext}, never {@code null}.
     * @throws javax.money.MonetaryException if no {@link javax.money.MonetaryAmount} implementation class can cover
     *                                       the required
     *                                       {@link javax.money.MonetaryContext}.
     */
    Collection<MonetaryAmountFactory<? extends MonetaryAmount>> getAmountFactories(MonetaryAmountFactoryQuery query);

    /**
     * Checks if an {@link javax.money.MonetaryAmountFactory} is matching the given query.
     *
     * @param query the factory query, not null.
     * @return true, if at least one {@link javax.money.MonetaryAmountFactory} matches the query.
     */
    default boolean isAvailable(MonetaryAmountFactoryQuery query) {
        return !getAmountFactories(query).isEmpty();
    }

    /**
     * Executes the query and returns the {@link javax.money.MonetaryAmount} implementation type found,
     * if there is only one type.
     * If multiple types match the query, the first one is selected.
     *
     * @param query the factory query, not null.
     * @return the type found, or null.
     */
    default Class<? extends MonetaryAmount> getAmountType(MonetaryAmountFactoryQuery query) {
        MonetaryAmountFactory<?> f = getAmountFactory(query);
        if (f != null) {
            return f.getAmountType();
        }
        return null;
    }

    /**
     * Executes the query and returns the {@link javax.money.MonetaryAmount} implementation types found.
     *
     * @param query the factory query, not null.
     * @return the type found, or null.
     */
    default Collection<Class<? extends MonetaryAmount>> getAmountTypes(MonetaryAmountFactoryQuery query) {
        Collection<MonetaryAmountFactory<? extends MonetaryAmount>> factories = getAmountFactories(query);
        return factories.stream().map(MonetaryAmountFactory::getAmountType).collect(Collectors.toList());
    }

    /**
     * Executes the query and returns the {@link javax.money.MonetaryAmountFactory} implementation type found,
     * if there is only one type. If multiple types match the query, the first one is selected.
     *
     * @param query the factory query, not null.
     * @return the type found, or null.
     */
    @SuppressWarnings("rawtypes")
	default MonetaryAmountFactory getAmountFactory(MonetaryAmountFactoryQuery query) {
        Collection<MonetaryAmountFactory<?>> factories = getAmountFactories(query);
        if (factories.isEmpty()) {
            return null;
        }
        return factories.iterator().next();
    }

}
