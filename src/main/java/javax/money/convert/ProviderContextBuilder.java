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
import java.util.*;

/**
 * Builder class to of {@link ProviderContext} instances. Instances of
 * this class are not thread-safe.
 *
 * @author Anatole Tresch
 */
public final class ProviderContextBuilder extends AbstractContextBuilder<ProviderContextBuilder, ProviderContext> {

    /**
     * Create a new Builder instance.
     *
     * @param provider  the provider name, not {@code null}.
     * @param rateTypes the rate types, not null and not empty.
     */
    private ProviderContextBuilder(String provider, RateType rateType, RateType... rateTypes) {
        Objects.requireNonNull(rateType, "At least one RateType is required.");
        Objects.requireNonNull(rateTypes);
        setProviderName(provider);
        Set<RateType> rts = new HashSet<>();
        rts.add(rateType);
        Collections.addAll(rts, rateTypes);
        set(ProviderContext.KEY_RATE_TYPES, rts);
    }

    /**
     * Create a new Builder instance.
     *
     * @param provider  the provider name, not {@code null}.
     * @param rateTypes the rate types, not null and not empty.
     */
    private ProviderContextBuilder(String provider, Collection<RateType> rateTypes) {
        Objects.requireNonNull(rateTypes);
        if (rateTypes.isEmpty()) {
            throw new IllegalArgumentException("At least one RateType is required.");
        }
        setProviderName(provider);
        Set<RateType> rts = new HashSet<>();
        rts.addAll(rateTypes);
        set("rateTypes", rts);
    }

    /**
     * Create a new Builder, hereby using the given {@link ProviderContext}
     * 's values as defaults. This allows changing an existing
     * {@link ProviderContext} easily.
     *
     * @param context the context, not {@code null}
     */
    private ProviderContextBuilder(ProviderContext context) {
        importContext(context);
        Set<RateType> rts = new HashSet<>();
        rts.addAll(context.getRateTypes());
        set(ProviderContext.KEY_RATE_TYPES, rts);
    }

    /**
     * Set the rate types.
     *
     * @param rateTypes the rate types, not null and not empty.
     * @return this, for chaining.
     * @throws IllegalArgumentException when not at least one {@link RateType} is provided.
     */
    public ProviderContextBuilder setRateTypes(RateType... rateTypes) {
        return setRateTypes(Arrays.asList(rateTypes));
    }

    /**
     * Set the rate types.
     *
     * @param rateTypes the rate types, not null and not empty.
     * @return this, for chaining.
     * @throws IllegalArgumentException when not at least one {@link RateType} is provided.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ProviderContextBuilder setRateTypes(Collection<RateType> rateTypes) {
        Objects.requireNonNull(rateTypes);
        if (rateTypes.isEmpty()) {
            throw new IllegalArgumentException("At least one RateType is required.");
        }
        Set rtSet = new HashSet<>();
        rtSet.addAll(rateTypes);
        set(ProviderContext.KEY_RATE_TYPES, rtSet);
        return this;
    }

    /**
     * Creates a new {@link ProviderContext} with the data from this Builder
     * instance.
     *
     * @return a new {@link ProviderContext}. never {@code null}.
     */
    public ProviderContext build() {
        return new ProviderContext(this);
    }

    /**
     * Create a new ProviderContextBuilder, hereby using the given {@link ProviderContext}
     * 's values as defaults. This allows changing an existing
     * {@link ProviderContext} easily.
     *
     * @param context the context, not {@code null}
     * @return a new {@link javax.money.convert.ProviderContextBuilder} instance, never null.
     */
    public static ProviderContextBuilder create(ProviderContext context) {
        return new ProviderContextBuilder(context);
    }

    /**
     * Create a new ProviderContextBuilder instance.
     *
     * @param provider  the provider name, not {@code null}.
     * @param rateTypes the rate types, not null and not empty.
     * @return a new {@link javax.money.convert.ProviderContextBuilder} instance, never null.
     */
    public static ProviderContextBuilder of(String provider, RateType rateType, RateType... rateTypes) {
        return new ProviderContextBuilder(provider, rateType, rateTypes);
    }

    /**
     * Create a new ProviderContextBuilder instance.
     *
     * @param provider  the provider name, not {@code null}.
     * @param rateTypes the rate types, not null and not empty.
     * @return a new {@link javax.money.convert.ProviderContextBuilder} instance, never null.
     */
    public static ProviderContextBuilder of(String provider, Collection<RateType> rateTypes) {
        return new ProviderContextBuilder(provider, rateTypes);
    }

}