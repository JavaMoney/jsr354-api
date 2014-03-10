/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.money.AbstractContext;

/**
 * This class models a context for which a {@link CurrencyConversion} is valid,
 * or an {@link ExchangeRateProvider} provides rates. Such a context allows to
 * model simple implementations as well as more complex conversion
 * architectures.
 * <p>
 * Instances of this class are immutable and thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class ProviderContext extends AbstractContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3536713139786856877L;

	/**
	 * Common context attributes, using this attributes ensures interoperability
	 * on property key level. Where possible according type safe methods are
	 * also defined on this class.
	 * 
	 * @author Anatole Tresch
	 */
	private static enum ProviderAttribute {
		/** The provider serving the conversion data. */
		PROVIDER,
		/**
		 * The starting range, where a rate is valid or a converter can deliver
		 * useful results.
		 */
		VALID_FROM,
		/**
		 * The ending range, where a rate is valid or a converter can deliver
		 * useful results.
		 */
		VALID_TO,
		/** The type of rates provided or requested. */
		RATE_TYPES,
	}

	/**
	 * Private constructor, used by {@link Builder}.
	 * 
	 * @param builder
	 *            the Builder.
	 */
	private ProviderContext(Builder builder) {
		super(builder);
	}

	/**
	 * Get the provider of this rate. The provider of a rate can have different
	 * contexts in different usage scenarios, such as the service type or the
	 * stock exchange.
	 * 
	 * @return the provider, or {code null}.
	 */
	public String getProviderName() {
		return getNamedAttribute(String.class, ProviderAttribute.PROVIDER);
	}

	/**
	 * Get the deferred flag. Exchange rates can be deferred or real.time.
	 * 
	 * @return the deferred flag, or {code null}.
	 */
	public Set<RateType> getRateTypes() {
		return getNamedAttribute(Set.class, ProviderAttribute.RATE_TYPES);
	}

	/**
	 * Returns the starting date/time this rate is valid. The result can also be
	 * {@code null}, since it is possible, that an {@link ExchangeRate} does not
	 * have starting validity range. This also can be queried by calling
	 * {@link #isLowerBound()}.
	 * <p>
	 * Basically all date time types that are available on a platform must be
	 * supported. On SE this includes Date, Calendar and the new 310 types
	 * introduced in JDK8). Additionally calling this method with
	 * {@code Long.class} returns the POSIX/UTC timestamp in milliseconds.
	 * 
	 * @return The starting timestamp of the rate, defining valid from, or
	 *         {@code null}, if no starting validity constraint is set.
	 */
	public <T> T getValidFrom(Class<T> type) {
		return getNamedAttribute(type, ProviderAttribute.VALID_FROM);
	}

	/**
	 * Returns the UTC timestamp defining from what date/time this rate is
	 * valid.
	 * <p>
	 * This is modeled as {@link Long} instead of {@code long}, since it is
	 * possible, that an {@link ExchangeRate} does not have starting validity
	 * range. This also can be queried by calling {@link #isLowerBound()}.
	 * 
	 * @return The UTC timestamp of the rate, defining valid from, or
	 *         {@code null}, if no starting validity constraint is set.
	 */
	public Long getValidFromMillis() {
		return getNamedAttribute(Long.class, ProviderAttribute.VALID_FROM);
	}

	/**
	 * Returns the ending date/time this rate is valid. The result can also be
	 * {@code null}, since it is possible, that an {@link ExchangeRate} does not
	 * have ending validity range. This also can be queried by calling
	 * {@link #isUpperBound()}.
	 * <p>
	 * Basically all date time types that are available on a platform must be
	 * supported. On SE this includes Date, Calendar and the new 310 types
	 * introduced in JDK8). Additionally calling this method with
	 * {@code Long.class} returns the POSIX/UTC timestamp in milliseconds.
	 * 
	 * @return The ending timestamp of the rate, defining valid until, or
	 *         {@code null}, if no ending validity constraint is set.
	 */
	public <T> T getValidTo(Class<T> type) {
		return getNamedAttribute(type, ProviderAttribute.VALID_TO);
	}

	/**
	 * Get the data validity timestamp of this rate in milliseconds. This can be
	 * useful, when a rate in a system only should be used within some specified
	 * time. *
	 * <p>
	 * This is modelled as {@link Long} instaed of {@code long}, since it is
	 * possible, that an {@link ExchangeRate} does not have ending validity
	 * range. This also can be queried by calling {@link #isUpperBound()}.
	 * 
	 * @return the duration of validity in milliseconds, or {@code null} if no
	 *         ending validity constraint is set.
	 */
	public Long getValidToMillis() {
		return getNamedAttribute(Long.class, ProviderAttribute.VALID_TO);
	}

	/**
	 * Method to quickly check if an {@link ExchangeRate} is valid for a given
	 * UTC timestamp.
	 * 
	 * @param timestamp
	 *            the UTC timestamp.
	 * @return {@code true}, if the rate is valid.
	 */
	public boolean isInScope(long timestamp) {
		Long validTo = getValidTo(Long.class);
		Long validFrom = getValidFrom(Long.class);
		if (validTo != null && validTo.longValue() <= timestamp) {
			return false;
		}
		if (validFrom != null && validFrom.longValue() > timestamp) {
			return false;
		}
		return true;
	}

	/**
	 * Method to easily check if the {@link #getValidFromMillis()} is not
	 * {@code null}.
	 * 
	 * @return {@code true} if {@link #getValidFromMillis()} is not {@code null}
	 *         .
	 */
	public boolean isLowerBound() {
		return getValidFrom(Long.class) != null;
	}

	/**
	 * Method to easily check if the {@link #getValidToMillis()} is not
	 * {@code null}.
	 * 
	 * @return {@code true} if {@link #getValidToMillis()} is not {@code null}.
	 */
	public boolean isUpperBound() {
		return getValidTo(Long.class) != null;
	}

	/**
	 * Creates a {@link Builder} initialized with this instance's data.
	 * 
	 * @return a new {@link Builder}, not {@code null}.
	 */
	public Builder toBuilder() {
		return new Builder(this);
	}

	/**
	 * Creates a new ProviderContext based on the provider id and rate type(s).
	 * 
	 * @param provider
	 *            the provider id, not null.
	 * @param rateTypes
	 *            the required {@link RateType}s, not null
	 * @return a new {@link ProviderContext} instance.
	 */
	public static ProviderContext of(String provider, RateType... rateTypes) {
		return new Builder(provider).setRateTypes(rateTypes).create();
	}

	/**
	 * Creates a new ProviderContext based on the provider id and rate type(s).
	 * 
	 * @param provider
	 *            the provider id, not null.
	 * @return a new {@link ProviderContext} instance.
	 */
	public static ProviderContext of(String provider) {
		return new Builder(provider).setRateTypes(RateType.ANY).create();
	}

	/**
	 * Builder class to create {@link ProviderContext} instances. Instances of
	 * this class are not thread-safe.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder extends AbstractBuilder<Builder> {

		/**
		 * Create a new Builder instance.
		 * 
		 * @param providerName
		 *            the provider name, not {@code null}.
		 */
		public Builder(String providerName) {
			setProviderName(providerName);
		}

		/**
		 * Create a new Builder, hereby using the given {@link ProviderContext}
		 * 's values as defaults. This allows changing an existing
		 * {@link ProviderContext} easily.
		 * 
		 * @param context
		 *            the context, not {@code null}
		 */
		public Builder(ProviderContext context) {
			super(context);
		}

		/**
		 * Sets the provider name.
		 * 
		 * @param providerName
		 *            the new provider name
		 * @return this, for chaining.
		 */
		public Builder setProviderName(String providerName) {
			Objects.requireNonNull(providerName);
			set(providerName, ProviderAttribute.PROVIDER);
			return this;
		}

		/**
		 * Set the rate types.
		 * 
		 * @param rateTypes
		 *            the rate types, not null and not empty.
		 * @return this, for chaining.
		 * @throws IllegalArgumentException
		 *             when not at least one {@link RateType} is provided.
		 */
		public Builder setRateTypes(RateType... rateTypes) {
			Set<RateType> types = new HashSet<>();
			Objects.requireNonNull(rateTypes);
			if (rateTypes.length == 0) {
				throw new IllegalArgumentException(
						"At least one RateType is required.");
			}
			types.addAll(Arrays.asList(rateTypes));
			set(types, ProviderAttribute.RATE_TYPES, Set.class);
			return this;
		}

		/**
		 * Set the starting range timestamp value.
		 * 
		 * @param timestamp
		 *            the starting range timestamp value
		 * @return this, for chaining.
		 */
		public Builder setValidFrom(long timestamp) {
			set(Long.valueOf(timestamp), ProviderAttribute.VALID_FROM);
			return this;
		}

		/**
		 * Set the starting range timestamp value.
		 * 
		 * @param dateTime
		 *            the starting range timestamp value
		 * @return this, for chaining.
		 */
		public Builder setValidFrom(Object dateTime) {
			set(dateTime, ProviderAttribute.VALID_FROM);
			return this;
		}

		/**
		 * Set the ending range timestamp value.
		 * 
		 * @param timestamp
		 *            the ending range timestamp value
		 * @return this, for chaining.
		 */
		public Builder setValidTo(long timestamp) {
			set(Long.valueOf(timestamp), ProviderAttribute.VALID_TO);
			return this;
		}

		/**
		 * Set the ending range timestamp value.
		 * 
		 * @param dateTime
		 *            the ending range timestamp value
		 * @return this, for chaining.
		 */
		public Builder setValidTo(Object dateTime) {
			set(dateTime, ProviderAttribute.VALID_TO);
			return this;
		}

		/**
		 * Creates a new {@link ProviderContext} with the data from this Builder
		 * instance.
		 * 
		 * @return a new {@link ProviderContext}. never {@code null}.
		 */
		public ProviderContext create() {
			return new ProviderContext(this);
		}

	}

	public static ProviderContext from(ConversionContext conversionContext) {
		return new Builder(conversionContext.getProvider())
				.setRateTypes(conversionContext.getRateType())
				.setAll(conversionContext).setProviderName(conversionContext.getProvider()).create();
	}

}
