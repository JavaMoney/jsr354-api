/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
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
     * @param scale the target scale, >0.
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
     * Creates a new RoundingQueryBuilder.
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