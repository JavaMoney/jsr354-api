/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import javax.money.AbstractContext;

import java.util.*;

/**
 * This class describes what kind of {@link javax.money.convert.ExchangeRate}s a {@link javax.money.convert
 * .ExchangeRateProvider} delivers, including the provider's name, rate type and additional data.
 * By default such a context supports the following attributes:
 * <ul>
 * <li>a unique nont localizable provider name. This provider name is also used to identify a concrete instance of
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
public final class ProviderContext extends AbstractContext{

    private static final long serialVersionUID = 3536713139786856877L;
    public static final String RATE_TYPES = "rateTypes";
    public static final String PROVIDER = "provider";


    /**
     * Private constructor, used by {@link Builder}.
     *
     * @param builder the Builder.
     */
    @SuppressWarnings("unchecked")
    private ProviderContext(Builder builder){
        super(builder);
        Set<RateType> rateTypes = getNamedAttribute(RATE_TYPES, Set.class);
        Set<RateType> newRateTypes = new HashSet<>();
        newRateTypes.addAll(rateTypes);
        set(RATE_TYPES, newRateTypes, Set.class);
    }


    /**
     * Get the provider of this rate. The provider of a rate can have different
     * contexts in different usage scenarios, such as the service type or the
     * stock exchange.
     *
     * @return the provider, or {code null}.
     */
    public String getProvider(){
        return getText(PROVIDER);
    }

    /**
     * Get the deferred flag. Exchange rates can be deferred or real.time.
     *
     * @return the deferred flag, or {code null}.
     */
	public Set<RateType> getRateTypes() {
		@SuppressWarnings("unchecked")
		Set<RateType> rateSet = getNamedAttribute(RATE_TYPES, Set.class,
				new HashSet<>());
		return Collections.unmodifiableSet(rateSet);
	}

    /**
     * Creates a {@link Builder} initialized with this instance's data.
     *
     * @return a new {@link Builder}, not {@code null}.
     */
    public Builder toBuilder(){
        return new Builder(this);
    }

    /**
     * Creates a new ProviderContext based on the provider id and rate type(s).
     *
     * @param provider  the provider id, not null.
     * @param rateTypes the required {@link RateType}s, not null
     * @return a new {@link ProviderContext} instance.
     */
    public static ProviderContext of(String provider, RateType rateType, RateType... rateTypes){
        return new Builder(provider, rateType, rateTypes).build();
    }

    /**
     * Creates a new ProviderContext based on the provider id and rate type(s).
     *
     * @param provider the provider id, not null.
     * @return a new {@link ProviderContext} instance.
     */
    public static ProviderContext of(String provider){
        return new Builder(provider, RateType.ANY).build();
    }


    /**
     * Builder class to create {@link ProviderContext} instances. Instances of
     * this class are not thread-safe.
     *
     * @author Anatole Tresch
     */
    public static final class Builder extends AbstractBuilder<Builder>{


        /**
         * Create a new Builder instance.
         *
         * @param provider the provider name, not {@code null}.
         * @param rateTypes the rate types, not null and not empty.
         */
        public Builder(String provider, RateType rateType, RateType... rateTypes){
            Objects.requireNonNull(rateType, "At least one RateType is required.");
            Objects.requireNonNull(rateTypes);
            setProviderName(provider);
            Set<RateType> rts = new HashSet<>();
            rts.add(rateType);
            Collections.addAll(rts, rateTypes);
            setAttribute(RATE_TYPES, rts, Set.class);
        }

        /**
         * Create a new Builder instance.
         *
         * @param provider  the provider name, not {@code null}.
         * @param rateTypes the rate types, not null and not empty.
         */
        public Builder(String provider, Collection<RateType> rateTypes){
            Objects.requireNonNull(rateTypes);
            if(rateTypes.isEmpty()){
                throw new IllegalArgumentException("At least one RateType is required.");
            }
            setProviderName(provider);
            Set<RateType> rts = new HashSet<>();
            rts.addAll(rateTypes);
            setAttribute("rateTypes", rts, Set.class);
        }

        /**
         * Create a new Builder, hereby using the given {@link ProviderContext}
         * 's values as defaults. This allows changing an existing
         * {@link ProviderContext} easily.
         *
         * @param context the context, not {@code null}
         */
        public Builder(ProviderContext context){
            super(context);
            Set<RateType> rts = new HashSet<>();
            rts.addAll(context.getRateTypes());
            setAttribute("rateTypes", rts, Set.class);
        }

        /**
         * Sets the provider name.
         *
         * @param provider the new provider name
         * @return this, for chaining.
         */
        public Builder setProviderName(String provider){
            Objects.requireNonNull(provider);
            setText(PROVIDER, provider);
            return this;
        }

        /**
         * Set the rate types.
         *
         * @param rateTypes the rate types, not null and not empty.
         * @return this, for chaining.
         * @throws IllegalArgumentException when not at least one {@link RateType} is provided.
         */
        public Builder setRateTypes(RateType... rateTypes){
            return setRateTypes(Arrays.asList(rateTypes));
        }

        /**
         * Set the rate types.
         *
         * @param rateTypes the rate types, not null and not empty.
         * @return this, for chaining.
         * @throws IllegalArgumentException when not at least one {@link RateType} is provided.
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
		public Builder setRateTypes(Collection<RateType> rateTypes){
            Objects.requireNonNull(rateTypes);
            if(rateTypes.size() == 0){
                throw new IllegalArgumentException("At least one RateType is required.");
            }
            Set rtSet = new HashSet<>();
            rtSet.addAll(rateTypes);
            setAttribute(RATE_TYPES, rtSet, Set.class);
            return this;
        }

        /**
         * Creates a new {@link ProviderContext} with the data from this Builder
         * instance.
         *
         * @return a new {@link ProviderContext}. never {@code null}.
         */
        public ProviderContext build(){
            return new ProviderContext(this);
        }


    }


}
