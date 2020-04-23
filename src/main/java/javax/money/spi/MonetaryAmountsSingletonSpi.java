/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
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
import javax.money.MonetaryException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * SPI (core) for the backing implementation of the {@link javax.money.Monetary} singleton. It
 * should load and manage (including contextual behavior), if needed) the different registered
 * {@link javax.money.MonetaryAmountFactory} instances.
 *
 * @author Anatole Tresch
 */
public interface MonetaryAmountsSingletonSpi{

    /**
     * Access the {@link javax.money.MonetaryAmountFactory} for the given {@code amountType} .
     *
     * @param amountType the {@link MonetaryAmount} implementation type, targeted by the factory.
     * @return the {@link javax.money.MonetaryAmountFactory}, or {@code null}, if no such
     * {@link javax.money.MonetaryAmountFactory} is available in the current context.
     */
    <T extends MonetaryAmount> MonetaryAmountFactory<T> getAmountFactory(Class<T> amountType);

    /**
     * Access the default {@link MonetaryAmount} implementation type.
     *
     * @return a the default {@link MonetaryAmount} type corresponding, never {@code null}.
     * @throws MonetaryException if no {@link MonetaryAmountFactoryProviderSpi} is available, or no
     *                           {@link MonetaryAmountFactoryProviderSpi} targeting the configured default
     *                           {@link MonetaryAmount} type.
     * @see javax.money.Monetary#getDefaultAmountType()
     */
    Class<? extends MonetaryAmount> getDefaultAmountType();

    /**
     * Get the currently registered {@link MonetaryAmount} implementation types.
     *
     * @return the {@link Set} if registered {@link MonetaryAmount} implementations, never
     * {@code null}.
     */
    Collection<Class<? extends MonetaryAmount>> getAmountTypes();


    /**
     * Access the default {@link javax.money.MonetaryAmountFactory}.
     *
     * @return a the default {@link MonetaryAmount} type corresponding, never {@code null}.
     * @throws MonetaryException if no {@link MonetaryAmountFactoryProviderSpi} is available, or no
     *                           {@link MonetaryAmountFactoryProviderSpi} targeting the configured default
     *                           {@link MonetaryAmount} type.
     * @see javax.money.Monetary#getDefaultAmountType()
     */
    default MonetaryAmountFactory<?> getDefaultAmountFactory(){
        return getAmountFactory(getDefaultAmountType());
    }

    /**
     * Get the currently registered {@link MonetaryAmount} implementation classes.
     *
     * @return the {@link Set} if registered {@link MonetaryAmount} implementations, never
     * {@code null}.
     */
    default Collection<MonetaryAmountFactory<?>> getAmountFactories(){
        List<MonetaryAmountFactory<?>> factories = new ArrayList<>();
        for(Class<? extends MonetaryAmount> type : getAmountTypes()){
            factories.add(getAmountFactory(type));
        }
        return factories;
    }

}