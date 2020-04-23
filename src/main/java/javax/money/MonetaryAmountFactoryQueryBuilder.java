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
package javax.money;

import java.util.Objects;

/**
 * Builder class for creating new instances of {@link MonetaryAmountFactoryQuery} that can be passed
 * to access {@link MonetaryAmountFactory} instances using a possible complex query.
 * <p>
 * Note this class is NOT thread-safe.
 *
 * @see javax.money.Monetary#getAmountFactory(MonetaryAmountFactoryQuery)
 * @see MonetaryAmountFactory
 */
public final class MonetaryAmountFactoryQueryBuilder
        extends AbstractQueryBuilder<MonetaryAmountFactoryQueryBuilder,MonetaryAmountFactoryQuery>{

    /**
     * Creates a new instance of {@link javax.money.CurrencyQueryBuilder}.
     *
     * @param monetaryAmountFactoryQuery {@link MonetaryAmountFactoryQuery} used for initializing this
     */
    private MonetaryAmountFactoryQueryBuilder(MonetaryAmountFactoryQuery monetaryAmountFactoryQuery){
        Objects.requireNonNull(monetaryAmountFactoryQuery);
        importContext(monetaryAmountFactoryQuery);
    }

    /**
     * Default constructor.
     */
    private MonetaryAmountFactoryQueryBuilder(){
    }

    /**
     * Sets the maximal scale to be supported.
     *
     * @param maxScale the max scale, &gt;= 0.
     * @return this Builder for chaining.
     */
    public MonetaryAmountFactoryQueryBuilder setMaxScale(int maxScale){
        return set("maxScale", maxScale);
    }

    /**
     * Sets the required precision, the value 0 models unlimited precision.
     *
     * @param precision the precision, &gt;= 0, 0 meaning unlimited.
     * @return this Builder for chaining.
     */
    public MonetaryAmountFactoryQueryBuilder setPrecision(int precision){
        return set("precision", precision);
    }

    /**
     * Sets the flag if the scale should fixed, meaning minimal scale and maximal scale are always equally sized.
     *
     * @param fixedScale the fixed scale flag.
     * @return this Builder for chaining.
     */
    public MonetaryAmountFactoryQueryBuilder setFixedScale(boolean fixedScale){
        return set("fixedScale", fixedScale);
    }

    /**
     * Creates a new instance of {@link MonetaryAmountFactoryQuery} based on the values of this
     * Builder. Note that
     * the Builder supports creation of several Builder instances from the a common Builder instance. But be aware
     * that the keys and values contained are themselves not recursively cloned (deep-copy).
     *
     * @return a new {@link MonetaryAmountFactoryQuery} instance.
     */
    public MonetaryAmountFactoryQuery build(){
        return new MonetaryAmountFactoryQuery(this);
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyQueryBuilder}.
     *
     * @return a new {@link javax.money.CurrencyQueryBuilder} instance, never null.
     */
    public static MonetaryAmountFactoryQueryBuilder of(){
        return new MonetaryAmountFactoryQueryBuilder();
    }

    /**
     * Creates a new instance of {@link javax.money.CurrencyQueryBuilder}.
     *
     * @param monetaryAmountFactoryQuery {@link MonetaryAmountFactoryQuery} used for initializing this
     *                                   builder.
     * @return a new {@link MonetaryAmountFactoryQueryBuilder} instance, never null.
     */

    public static MonetaryAmountFactoryQueryBuilder of(MonetaryAmountFactoryQuery monetaryAmountFactoryQuery){
        return new MonetaryAmountFactoryQueryBuilder(monetaryAmountFactoryQuery);
    }

}