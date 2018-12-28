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

import java.util.Objects;


/**
 * Builder used to construct new instances of {@link }RoundingQuery}.
 * <p>
 * Note this class is NOT thread-safe.
 */
public final class RoundingQueryBuilder extends AbstractQueryBuilder<RoundingQueryBuilder, RoundingQuery> {

    /**
     * Default constructor.
     */
    private RoundingQueryBuilder() {
        super();
    }

    /**
     * Creates a new RoundingQueryBuilder.
     *
     * @param roundingQuery the rounding query, used as a template, not null.
     */
    private RoundingQueryBuilder(RoundingQuery roundingQuery) {
        importContext(roundingQuery);
    }

    /**
     * Sets the rounding names of the {@link MonetaryRounding} instances. This method allows to
     * access the {@link MonetaryRounding} instances by passing a name, which most of the time
     * identifies a certain rounding instance. Each entry is first matched as name on equality. If no instance
     * with such a name exists, the value passed is interpreted as a regular
     * expression to lookup roundings.
     *
     * @param roundingName the (custom) rounding name expression, not {@code null}.
     * @return this instance for chaining
     */
    public RoundingQueryBuilder setRoundingName(String roundingName) {
        Objects.requireNonNull(roundingName);
        set(RoundingQuery.KEY_QUERY_ROUNDING_NAME, roundingName);
        return this;
    }


    /**
     * Sets the target scale. This allows to define the scale required. If not specified as additional
     * attribute, by default, RoundingMode.HALF_EVEN is used hereby.
     *
     * @param scale the target scale, &gt;0.
     * @return this instance for chaining
     */
    public RoundingQueryBuilder setScale(int scale) {
        set(RoundingQuery.KEY_QUERY_SCALE, scale);
        return this;
    }

    /**
     * Sets the target {@link CurrencyUnit}, which defines a rounding targeting a concrete {@link
     * CurrencyUnit}.
     * Typically this determines all other properties, such as scale and the concrete rounding algorithm. With
     * rounding names, depending on the implementation, additional subselections are possible. Similarly
     * additional attributes can be used to select special rounding instances, e.g. for cash rounding.
     *
     * @param currencyUnit the target {@link CurrencyUnit} not null.
     * @return this instance for chaining
     */
    public RoundingQueryBuilder setCurrency(CurrencyUnit currencyUnit) {
        Objects.requireNonNull(currencyUnit);
        set(CurrencyUnit.class, currencyUnit);
        return this;
    }

    /**
     * Creates a new instance of {@link javax.money.RoundingQuery}.
     *
     * @return a new {@link javax.money.RoundingQuery} instance.
     */
    public RoundingQuery build() {
        return new RoundingQuery(this);
    }

    /**
     * @return Creates a new RoundingQueryBuilder.
     */
    public static RoundingQueryBuilder of() {
        return new RoundingQueryBuilder();
    }

    /**
     * Creates a new RoundingQueryBuilder.
     *
     * @param roundingQuery the rounding query, used as a template, not null.
     * @return a new {@link javax.money.RoundingQueryBuilder} instance, never null.
     */
    public static RoundingQueryBuilder of(RoundingQuery roundingQuery) {
        return new RoundingQueryBuilder(roundingQuery);
    }

}