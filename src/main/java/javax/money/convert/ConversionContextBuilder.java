/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import javax.money.AbstractContextBuilder;
import java.util.Objects;

/**
 * Builder class to of {@link ConversionContext} instances. Instances of
 * this class are not thread-safe.
 *
 * @author Anatole Tresch
 */
public final class ConversionContextBuilder extends AbstractContextBuilder<ConversionContextBuilder, ConversionContext> {

    /**
     * Create a new Builder, hereby using the given
     * {@link ConversionContext}'s values as defaults. This allows changing
     * an existing {@link ConversionContext} easily.
     *
     * @param context the context, not {@code null}
     */
    private ConversionContextBuilder(ConversionContext context) {
        importContext(context);
    }

    /**
     * Create a new Builder, hereby using the given
     * {@link ConversionContext}'s values as defaults. This allows changing
     * an existing {@link ConversionContext} easily.
     *
     * @param context  the provider context, not {@code null}
     * @param rateType the rate type, not null.
     */
    private ConversionContextBuilder(ProviderContext context, RateType rateType) {
        importContext(context);
        setRateType(rateType);
    }

    /**
     * Set the historic value.
     *
     * @param rateType the rate type
     * @return this, for chaining.
     */
    public ConversionContextBuilder setRateType(RateType rateType) {
        Objects.requireNonNull(rateType);
        set(rateType);
        return this;
    }

    /**
     * Creates a new {@link ConversionContext} with the data from this
     * Builder instance.
     *
     * @return a new {@link ConversionContext}. never {@code null}.
     */
    public ConversionContext build() {
        return new ConversionContext(this);
    }

    /**
     * Create a new Builder instance without any provider, e.g. for creating
     * new {@link ConversionContext} instances for querying.
     */
    public ConversionContextBuilder() {
        set(RateType.ANY);
    }

    /**
     * Creates a new {@link javax.money.convert.ConversionContextBuilder} instance.
     * @param conversionContext the conversion context to be used to initialize the new builder instance, not null.
     * @return a new {@link javax.money.convert.ConversionContextBuilder} instance, never null.
     */
    public static ConversionContextBuilder of(ConversionContext conversionContext) {
        return new ConversionContextBuilder(conversionContext);
    }

    /**
     * Creates a new {@link javax.money.convert.ConversionContextBuilder} instance.
     *
     * @return a new {@link javax.money.convert.ConversionContextBuilder} instance, never null.
     */
    public static ConversionContextBuilder of() {
        return new ConversionContextBuilder();
    }

    /**
     * Create a new Builder, hereby using the given
     * {@link ConversionContext}'s values as defaults. This allows changing
     * an existing {@link ConversionContext} easily.
     *
     * @param context  the provider context, not {@code null}
     * @param rateType the rate type, not null.
     * @return a new {@link javax.money.convert.ConversionContextBuilder} instance, never null.
     */
    public static ConversionContextBuilder create(ProviderContext context, RateType rateType) {
        return new ConversionContextBuilder(context, rateType);
    }

}
