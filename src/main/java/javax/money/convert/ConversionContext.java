/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.convert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
public final class ConversionContext {
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
	 * Map with the attributes of this context.
	 */
	private Map<Class<?>, Map<Object, Object>> attributes = new HashMap<>();

	/**
	 * Private constructor, used by {@link Builder}.
	 * 
	 * @param builder
	 *            the Builder.
	 */
	private ConversionContext(Builder builder) {
		this.attributes = new HashMap<>(builder.attributes);
	}

	/**
	 * Access an attribute.
	 * 
	 * @param type
	 *            the attribute's type, not {@code null}
	 * @param key
	 *            the attribute's key, not {@code null}
	 * @return the attribute value, or {@code null}.
	 */
	// Type safe cast
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Class<T> type, Object key) {
		Map<Object, ?> typedAttrs = attributes.get(type);
		if (typedAttrs != null) {
			return (T) typedAttrs.get(key);
		}
		return null;
	}

	/**
	 * Access an attribute, using the {@link AttributeType}.
	 * 
	 * @param type
	 *            the attribute's type, not {@code null}
	 * @param attributeType
	 *            the {@link AttributeType}, not {@code null}
	 * @return the attribute value, or {@code null}.
	 */
	// Type safe cast
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Class<T> type, AttributeType attributeType) {
		Map<Object, ?> typedAttrs = attributes.get(type);
		if (typedAttrs != null) {
			return (T) typedAttrs.get(attributeType);
		}
		return null;
	}

	/**
	 * Access an attribute, hereby using the class name as key.
	 * 
	 * @param type
	 *            the attribute's type, not {@code null}
	 * @return the attribute value, or {@code null}.
	 */
	// safe cast
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Class<T> type) {
		return getAttribute(type, type.getName());
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
		return getAttribute(type, AttributeType.VALID_FROM);
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
		return getAttribute(Long.class, AttributeType.VALID_FROM);
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
		return getAttribute(type, AttributeType.VALID_TO);
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
		return getAttribute(Long.class, AttributeType.VALID_TO);
	}

	/**
	 * Returns the UTC timestamp of this rate.
	 * 
	 * @return The UTC timestamp of the rate, or {@code null}.
	 */
	public final Long getTimestamp() {
		return getAttribute(Long.class, AttributeType.TIMESTAMP);
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
		return getAttribute(String.class, AttributeType.PROVIDER);
	}

	/**
	 * Get the deferred flag. Exchange rates can be deferred or real.time.
	 * 
	 * @return the deferred flag, or {code null}.
	 */
	public Boolean isHistoric() {
		return getAttribute(Boolean.class, AttributeType.HISTORIC);
	}

	/**
	 * Creates a {@link Builder} initialized with this instance's data.
	 * 
	 * @return a new {@link Builder}, not {@code null}.
	 */
	public Builder toBuilder() {
		return new Builder(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConversionContext other = (ConversionContext) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		return true;
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
	public static final class Builder {
		/**
		 * Map with the attributes of this context.
		 */
		private Map<Class<?>, Map<Object, Object>> attributes = new HashMap<>();

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
			Objects.requireNonNull(context);
			this.attributes.putAll(context.attributes);
		}

		/**
		 * Sets an attribute, using {@code attribute.getClass()} as attribute
		 * <i>type</i> and {@code attribute.getClass().getName()} as attribute
		 * <i>name</i>.
		 * 
		 * @param value
		 *            the attribute value
		 * @return this Builder, for chaining
		 */
		public Builder set(Object value) {
			return set(value, value.getClass().getName(), value.getClass());
		}

		/**
		 * Sets an attribute, using {@code attribute.getClass()} as attribute
		 * <i>type</i>.
		 * 
		 * @param value
		 *            the attribute value
		 * @param key
		 *            the attribute's key, not {@code null}
		 * @return this Builder, for chaining
		 */
		public Builder set(Object value, Object key) {
			return set(value, key, value.getClass());
		}

		/**
		 * Sets an attribute.
		 * 
		 * @param value
		 *            the attribute's value
		 * @param key
		 *            the attribute's key
		 * @param type
		 *            the attribute's type
		 * @return this Builder, for chaining
		 */
		public <T> Builder set(T attribute, Object key, Class<? extends T> type) {
			Map<Object, Object> typedAttrs = attributes.get(type);
			if (typedAttrs == null) {
				typedAttrs = new HashMap<>();
				attributes.put(type, typedAttrs);
			}
			typedAttrs.put(key, attribute);
			return this;
		}

		/**
		 * Sets the provider attribute as {@link String} value.
		 * 
		 * @param provider
		 *            the value, not {@code null}
		 * @return this Builder, for chaining
		 */
		public Builder setProvider(String provider) {
			return set(provider, AttributeType.PROVIDER, String.class);
		}

		/**
		 * Sets the historic attribute as {@link Boolean} value.
		 * 
		 * @param historic
		 *            the value
		 * @return this Builder, for chaining
		 */
		public Builder setHistoric(boolean historic) {
			return set(Boolean.valueOf(historic), AttributeType.HISTORIC,
					Boolean.class);
		}

		/**
		 * Sets the timestamp attribute as UTC timestamp in millis.
		 * 
		 * @param timestamp
		 *            the timestamp value
		 * @return this Builder, for chaining
		 */
		public Builder setTimestamp(long timestamp) {
			return set(timestamp, AttributeType.TIMESTAMP, Long.class);
		}

		/**
		 * Sets the validFrom attribute.
		 * 
		 * @param validFrom
		 *            the value, not {@code null}
		 * @return this Builder, for chaining
		 */
		public Builder setValidFrom(Object validFrom) {
			return set(validFrom, AttributeType.VALID_FROM);
		}

		/**
		 * Sets the validFrom attribute as UTC timestamp in millis.
		 * 
		 * @param validFrom
		 *            the value, not {@code null}
		 * @return this Builder, for chaining
		 */
		public Builder setValidFromMillis(long validFrom) {
			return set(validFrom, AttributeType.VALID_FROM, Long.class);
		}

		/**
		 * Sets the validTo attribute.
		 * 
		 * @param validTo
		 *            the value, not {@code null}
		 * @return this Builder, for chaining
		 */
		public Builder setValidTo(Object validTo) {
			return set(validTo, AttributeType.VALID_TO);
		}

		/**
		 * Sets the validTo attribute as UTC timestamp in millis.
		 * 
		 * @param validTo
		 *            the value, not {@code null}
		 * @return this Builder, for chaining
		 */
		public Builder setValidToMillis(long validTo) {
			return set(validTo, AttributeType.VALID_TO, Long.class);
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
