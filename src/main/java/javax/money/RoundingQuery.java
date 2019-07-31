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
package javax.money;

/**
 * Query for accessing instances of {@link javax.money.MonetaryRounding}. In general it is determined by the
 * implementation, what roundings are provided. Nevertheless the following queries must be supported:
 * <ul>
 * <li>Accessing roundings using rounding names and/or regular expressions.</li>
 * <li>Accessing mathematical rounding by setting a scale and (optionally) a {@link java.math.MathContext} as
 * additional generic attribute.</li>
 * <li>Accessing default roundings for a {@link javax.money.CurrencyUnit}. This method should return the most
 * appropriate rounding for a currency. If no
 * currency specific rounding is available, it should return a rounding with {@code scale==currency
 * .getDefaultFractionUnits(), java.math.RoundingMode = RoundingMode.HALF_EVEN}.</li>
 * </ul>
 * All other roundings including cash rounding and historical roundings are optional.
 * <p>
 * This class is immutable, thread-safe and serializable.
 */
public final class RoundingQuery extends AbstractQuery implements CurrencySupplier {

	private static final long serialVersionUID = -9088736532066489061L;

	/**
     * Attribute key used for the rounding name attribute.
     */
    static final String KEY_QUERY_ROUNDING_NAME = "Query.roundingName";

    /**
     * Attribute key used for the scale attribute.
     */
    static final String KEY_QUERY_SCALE = "Query.scale";

    /**
     * Constructor, used from the {@link javax.money.RoundingQueryBuilder}.
     *
     * @param builder the corresponding builder, not null.
     */
    RoundingQuery(RoundingQueryBuilder builder) {
        super(builder);
    }

    /**
     * Gets the target rounding name. This method allows to
     * access the {@link javax.money.MonetaryRounding} instances by passing a name, which most of the time
     * identifies a certain rounding instance. Each entry is first matched as name on equality. If no instance
     * with such a name exists, the value passed is interpreted as a regular
     * expression to lookup roundings.
     *
     * @return the rounding id  or null.
     */
    public String getRoundingName() {
        return getText(KEY_QUERY_ROUNDING_NAME);
    }

    /**
     * Gets the target scale. This allows to define the scale required. If not specified as additional
     * attribute, by default, RoundingMode.HALF_EVEN is used hereby.
     *
     * @return the target scale or null.
     */
    public Integer getScale() {
        return getInt(KEY_QUERY_SCALE);
    }

    /**
     * Sets the target CurrencyUnit. Typically this determines all other properties,
     * such as scale and the concrete rounding algorithm. With
     * rounding names, depending on the implementation, additional sub-selections are possible. Similarly
     * additional attributes can be used to select special rounding instances, e.g. for cash rounding.
     *
     * @return the CurrencyUnit, or null.
     */
    @Override
	public CurrencyUnit getCurrency() {
        return get(CurrencyUnit.class);
    }

    /**
     * Creates a new builder instances, initialized with the data from this one.
     *
     * @return a new {@link javax.money.RoundingQueryBuilder} instance, never null.
     */
    public RoundingQueryBuilder toBuilder() {
        return RoundingQueryBuilder.of(this);
    }

}
