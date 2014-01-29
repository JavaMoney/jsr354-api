/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import java.util.Objects;

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
 */
public final class ConversionContext extends AbstractContext{
	
	/**
	 * Common context attributes, using this attributes ansures interoperability
	 * on property key level. Where possible according type safe methods are
	 * also definedon this class.
	 * 
	 * @author Anatole Tresch
	 */
	public static enum AttributeType {
		/** The provider serving the conversion data. */
		PROVIDER,
		/**
		 * The timestamp of a rate, this may be extended by a valid from/to
		 * range.
		 */
		TIMESTAMP,
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
		/** Flag, if the rates provided are historic rates. */
		HISTORIC
	}


	/**
	 * Private constructor, used by {@link Builder}.
	 * 
	 * @param builder
	 *            the Builder.
	 */
	private ConversionContext(Builder builder) {
		super(builder);
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
	public final <T> T getValidFrom(Class<T> type) {
		return getNamedAttribute(type, AttributeType.VALID_FROM);
	}

	/**
	 * Returns the UTC timestamp defining from what date/time this rate is
	 * valid.
	 * <p>
	 * This is modelled as {@link Long} instaed of {@code long}, since it is
	 * possible, that an {@link ExchangeRate} does not have starting validity
	 * range. This also can be queried by calling {@link #isLowerBound()}.
	 * 
	 * @return The UTC timestamp of the rate, defining valid from, or
	 *         {@code null}, if no starting validity constraint is set.
	 */
	public final Long getValidFromMillis() {
		return getNamedAttribute(Long.class, AttributeType.VALID_FROM);
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
	public final <T> T getValidTo(Class<T> type) {
		return getNamedAttribute(type, AttributeType.VALID_TO);
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
	public final Long getValidToMillis() {
		return getNamedAttribute(Long.class, AttributeType.VALID_TO);
	}

	/**
	 * Returns the UTC timestamp of this rate.
	 * 
	 * @return The UTC timestamp of the rate, or {@code null}.
	 */
	public final Long getTimestamp() {
		return getNamedAttribute(Long.class, AttributeType.TIMESTAMP);
	}

	/**
	 * Method to quickly check if an {@link ExchangeRate} is valid for a given
	 * UTC timestamp.
	 * 
	 * @param timestamp
	 *            the UTC timestamp.
	 * @return {@code true}, if the rate is valid.
	 */
	public boolean isValid(long timestamp) {
		Long validTo = getValidTo(Long.class);
		Long validFrom = getValidFrom(Long.class);
		if (validTo != null && validTo.longValue() < timestamp) {
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
	 * Get the provider of this rate. The provider of a rate can have different
	 * contexts in different usage scenarios, such as the service type or the
	 * stock exchange.
	 * 
	 * @return the provider, or {code null}.
	 */
	public String getProvider() {
		return getNamedAttribute(String.class, AttributeType.PROVIDER);
	}

	/**
	 * Get the deferred flag. Exchange rates can be deferred or real.time.
	 * 
	 * @return the deferred flag, or {code null}.
	 */
	public Boolean isHistoric() {
		return getNamedAttribute(Boolean.class, AttributeType.HISTORIC);
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
	 * Simple factory method for {@link ConversionContext}. For more
	 * possibilities to initialize a {@link ConversionContext}, please use a
	 * {@link Builder},
	 * 
	 * @param provider
	 *            the provider name, not {@code null}
	 * @param attributes
	 *            Any additional attributes
	 * @return a new instance of {@link ConversionContext}
	 */
	public static ConversionContext of(String provider, Object... attributes) {
		Builder b = new Builder(provider);
		for (int i = 0; i < attributes.length; i++) {
			b.set(attributes[i]);
		}
		return b.create();
	}

	/**
	 * Builder class to create {@link ConversionContext} instances. Instances of
	 * this class are not thread-safe.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder extends AbstractBuilder<Builder>{
		
		/**
		 * Create a new Builder instance without any provider, e.g. for creating
		 * new {@link ConversionContext} instances for querying.
		 */
		public Builder() {
		}

		/**
		 * Create a new Builder instance.
		 * 
		 * @param provider
		 *            the provider name, not {@code null}.
		 */
		public Builder(String provider) {
			Objects.requireNonNull(provider);
			set(provider, AttributeType.PROVIDER);
		}

		/**
		 * Create a new Builder, hereby using the given
		 * {@link ConversionContext}'s values as defaults. This allows changing
		 * an existing {@link ConversionContext} easily.
		 * 
		 * @param context
		 *            the context, not {@code null}
		 */
		public Builder(ConversionContext context) {
			super(context);
		}

		/**
		 * Creates a new {@link ConversionContext} with the data from this
		 * Builder instance.
		 * 
		 * @return a new {@link ConversionContext}. never {@code null}.
		 */
		public ConversionContext create() {
			return new ConversionContext(this);
		}
	}

}
