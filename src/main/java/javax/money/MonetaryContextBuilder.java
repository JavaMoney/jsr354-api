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


/**
 * Builder class for creating new instances of {@link MonetaryContext} adding detailed information
 * about a {@link MonetaryAmount} instance.
 * <p>
 * Note this class is NOT thread-safe.
 *
 * @see javax.money.MonetaryAmount#getContext()
 */
public final class MonetaryContextBuilder extends AbstractContextBuilder<MonetaryContextBuilder, MonetaryContext> {

    private MonetaryContextBuilder(MonetaryContext monetaryContext) {
        importContext(monetaryContext);
    }

    /**
     * Creates a new builder, hereby the target implementation type is required. This can be used to explicitly
     * acquire a specific amount type and additionally configure the amount factory with the attributes in this
     * query.
     *
     * @param amountType the target amount type, not null.
     */
    private MonetaryContextBuilder(Class<? extends MonetaryAmount> amountType) {
        set(MonetaryContext.AMOUNT_TYPE, amountType);
    }


    /**
     * Set the maximal scale to be supported.
     *
     * @param maxScale the max scale, &gt;= 0.
     * @return this builder for chaining.
     */
    public MonetaryContextBuilder setMaxScale(int maxScale) {
        return set("maxScale", maxScale);
    }

    /**
     * Set the required precision.
     *
     * @param precision the precision, &gt;= 0, 0 meaning unlimited.
     * @return this builder for chaining.
     */
    public MonetaryContextBuilder setPrecision(int precision) {
        return set("precision", precision);
    }

    /**
     * Set the flag if the scale should fixed.
     *
     * @param fixedScale the fixed scale flag.
     * @return this builder for chaining.
     */
    public MonetaryContextBuilder setFixedScale(boolean fixedScale) {
        return set("fixedScale", fixedScale);
    }

    /**
     * Set the MonetaryAmount implementation class.
     *
     * @param amountType the target amount type, not null.
     * @return the implementation class of the containing amount instance, never null.
     * @see javax.money.MonetaryAmount#getContext()
     */
    public MonetaryContextBuilder setAmountType(Class<? extends MonetaryAmount> amountType) {
        return set(MonetaryContext.AMOUNT_TYPE, amountType);
    }

    /**
     * Creates a new instance of {@link MonetaryAmountFactoryQuery}.
     *
     * @return a new {@link MonetaryAmountFactoryQuery} instance.
     */
    public MonetaryContext build() {
        return new MonetaryContext(this);
    }

    /**
     * Creates a new builder, hereby the target implementation type is required. This can be used to explicitly
     * acquire a specific amount type and additionally configure the amount factory with the attributes in this
     * query.
     *
     * @return a new {@link javax.money.MonetaryContextBuilder} instance, never null.
     */
    public static MonetaryContextBuilder of() {
        return of(MonetaryAmount.class);
    }

    /**
     * Creates a new builder, using an existing {@link javax.money.MonetaryContext} as a template.
     *
     * @return a new {@link javax.money.MonetaryContextBuilder} instance, never null.
     */
    public static MonetaryContextBuilder of(MonetaryContext monetaryContext) {
        return new MonetaryContextBuilder(monetaryContext);
    }

    /**
     * Creates a new builder, hereby the target implementation type is required. This can be used to explicitly
     * acquire a specific amount type and additionally configure the amount factory with the attributes in this
     * query.
     *
     * @param amountType the target amount type, not null.
     * @return a new {@link javax.money.MonetaryContextBuilder} instance, never null.
     */
    public static MonetaryContextBuilder of(Class<? extends MonetaryAmount> amountType) {
        return new MonetaryContextBuilder(amountType);
    }

}