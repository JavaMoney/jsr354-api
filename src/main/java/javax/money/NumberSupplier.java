/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
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
package javax.money;


/**
 * Represents a supplier of {@link NumberValue}-valued results. This is the
 * {@link NumberValue}-producing specialization of {@code Supplier} (as in Java 8).
 * <p>
 * <p>
 * There is no requirement that a distinct result be returned each time the
 * supplier is invoked.
 * <p>
 * <p>
 * This is a <b>functional interface</b> whose
 * functional method is {@link #getNumber()}.
 * This class does not extend {@link java.util.function.Supplier} since {@link javax.money.MonetaryAmount} implements
 * both supplier interfaces, {@link javax.money.NumberSupplier} and {@link javax.money.CurrencySupplier},
 * which will lead
 * to method name conflicts.
 * </p>
 *
 * @author Werner Keil
 * @author Anatole Tresch
 * @version 0.6
 * @see java.util.function.Supplier
 * @since 0.8
 */
@FunctionalInterface
public interface NumberSupplier{

    /**
     * Gets the corresponding {@link javax.money.NumberValue}.
     *
     * @return the corresponding {@link javax.money.NumberValue}, not null.
     */
    NumberValue getNumber();
}
