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
     * @param maxScale the max scale, >= 0.
     * @return this Builder for chaining.
     */
    public MonetaryAmountFactoryQueryBuilder setMaxScale(int maxScale){
        return set("maxScale", maxScale);
    }

    /**
     * Sets the required precision, the value 0 models unlimited precision.
     *
     * @param precision the precision, >= 0, 0 meaning unlimited.
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