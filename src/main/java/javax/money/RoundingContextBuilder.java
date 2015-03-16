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
 * Builder class for creating new instances of {@link RoundingContext} adding detailed information
 * about a {@link MonetaryRounding} instance.
 * <p>
 * Note this class is NOT thread-safe.
 *
 * @see javax.money.MonetaryRounding#getRoundingContext() ()
 */
public final class RoundingContextBuilder extends AbstractContextBuilder<RoundingContextBuilder, RoundingContext> {

    /**
     * Creates a new builder.
     *
     * @param provider   the provider name, creating the corresponding {@link MonetaryRounding}
     *                   containing, not null.
     *                   the final {@link RoundingContext} created by this builder, not null.
     * @param roundingId The name of the rounding, not null.
     */
    private RoundingContextBuilder(String provider, String roundingId) {
        Objects.requireNonNull(provider);
        set(RoundingContext.KEY_PROVIDER, provider);
        Objects.requireNonNull(roundingId);
        set(RoundingContext.KEY_ROUNDING_NAME, roundingId);
    }

    /**
     * Creates a new RoundingContextBuilder.
     *
     * @param roundingContext the rounding context, used as a template, not null.
     */
    private RoundingContextBuilder(RoundingContext roundingContext) {
        importContext(roundingContext);
    }

    /**
     * Get the basic {@link CurrencyUnit}, which is based for this rounding type.
     *
     * @return the target CurrencyUnit, or null.
     */
    public RoundingContextBuilder setCurrency(CurrencyUnit currencyUnit) {
        Objects.requireNonNull(currencyUnit);
        return set(CurrencyUnit.class, currencyUnit);
    }

    /**
     * Creates a new instance of {@link javax.money.RoundingContext}.
     *
     * @return a new {@link javax.money.RoundingContext} instance.
     */
    @Override
    public RoundingContext build() {
        return new RoundingContext(this);
    }

    /**
     * Creates a new RoundingContextBuilder.
     *
     * @param provider   the provider name, creating the corresponding {@link MonetaryRounding}
     *                   containing, not null.
     *                   the final {@link RoundingContext} created by this builder, not null.
     * @param roundingId The name of the rounding, not null.
     * @return a new {@link javax.money.MonetaryContextBuilder} instance, never null.
     */
    public static RoundingContextBuilder of(String provider, String roundingId) {
        return new RoundingContextBuilder(provider, roundingId);
    }

    /**
     * Creates a new RoundingContextBuilder.
     *
     * @param roundingContext the rounding context, used as a template, not null.
     * @return a new {@link javax.money.MonetaryContextBuilder} instance, never null.
     */
    public static RoundingContextBuilder of(RoundingContext roundingContext) {
        return new RoundingContextBuilder(roundingContext);
    }

}