/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import java.util.Collections;
import java.util.Set;

import javax.money.AbstractContext;

/**
 * This class describes what kind of {@link javax.money.convert.ExchangeRate}s a {@link javax.money.convert
 * .ExchangeRateProvider} delivers, including the provider's name, rate type and additional data.
 * By default such a context supports the following attributes:
 * <ul>
 * <li>a unique non localizable provider name. This provider name is also used to identify a concrete instance of
 * ExchangeRateProvider.</li>
 * <li>a set of {@link javax.money.convert.RateType} an ExchangeRateProvider supports</li>
 * <li>a time range for which an ExchangeRateProvider delivers rates.</li
 * </ul>
 * Additionally a instance of ProviderContext can have arbitrary additional attributes describing more precisely
 * the capabilities of a concrete {@link }ExchangeRateProvider} implementation.
 * <p>
 * Instances of this class are immutable and thread-safe.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class ProviderContext extends AbstractContext {

    private static final long serialVersionUID = 3536713139786856877L;

    /**
     * Key used for the rate types attribute.
     */
    static final String KEY_RATE_TYPES = "rateTypes";


    /**
     * Private constructor, used by {@link ProviderContextBuilder}.
     *
     * @param builder the Builder.
     */
    ProviderContext(ProviderContextBuilder builder) {
        super(builder);
    }

    /**
     * Get the deferred flag. Exchange rates can be deferred or real.time.
     *
     * @return the deferred flag, or {code null}.
     */
    public Set<RateType> getRateTypes() {
        @SuppressWarnings("unchecked")
        Set<RateType> rateSet = get(KEY_RATE_TYPES, Set.class);
        if (rateSet == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(rateSet);
    }

    /**
     * Creates a {@link ProviderContextBuilder} initialized with this instance's data.
     *
     * @return a new {@link ProviderContextBuilder}, never {@code null}.
     */
    public ProviderContextBuilder toBuilder() {
        return ProviderContextBuilder.create(this);
    }

    /**
     * Creates a new ProviderContext based on the provider id and rate type(s).
     *
     * @param provider  the provider id, not null.
     * @param rateTypes the required {@link RateType}s, not null
     * @return a new {@link ProviderContext} instance.
     */
    public static ProviderContext of(String provider, RateType rateType, RateType... rateTypes) {
        return ProviderContextBuilder.of(provider, rateType, rateTypes).build();
    }

    /**
     * Creates a new ProviderContext based on the provider id and rate type(s).
     *
     * @param provider the provider id, not null.
     * @return a new {@link ProviderContext} instance.
     */
    public static ProviderContext of(String provider) {
        return ProviderContextBuilder.of(provider, RateType.ANY).build();
    }

}
