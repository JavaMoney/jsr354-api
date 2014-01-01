/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class models the numeric capabilities of a {@link MonetaryAmount} in a platform independent
 * way. It provides information about
 * <ul>
 * <li>the maximal precision supported (0, for unlimited precision).
 * <li>the minimum scale (>=0)
 * <li>the maximal scale (>= -1, -1 for unlimited scale).
 * <li>the numeric representation class.
 * <li>any other attributes, identified by the attribute type, e.g. {@link java.math.RoundingMode}.
 * </ul>
 * <p>
 * This class is serializable and thread-safe.
 * 
 * @author Anatole Tresch
 */
public final class MonetaryContext implements
		Serializable {
	/** serialVersionUID. */
	private static final long serialVersionUID = 5579720004786848255L;

	/**
	 * Defines the common flavors of {@link MonetaryAmount} implementations. This information can be
	 * additionally used to determine which implementation type should be used, additionally to the
	 * other properties and attributes in {@link MonetaryContext}.
	 * 
	 * @author Anatole Tresch
	 */
	public static enum AmountFlavor {
		/**
		 * The implementation is optimized for precise results, not primarly for performance.
		 */
		PRECISION,
		/**
		 * The implementation is optimized for fast results, but reduced precision and scale, may be
		 * possible.
		 */
		PERFORMANCE,
		/**
		 * These implementations decorate, extend or adapt another registered implementation.
		 * Implementation with this flavor should not be considered by the algorithm performed when
		 * calling {@link MonetaryAmounts#queryAmountType(MonetaryContext)}, unless the target type
		 * is set explcitly in {@link MonetaryContext#amountType}.
		 */
		DECORATION,
		/** The implementation has no defined flavor. */
		UNDEFINED,
	}

	/**
	 * The number of digits to be used for an operation. A value of 0 indicates that unlimited
	 * precision (as many digits as are required) will be used. Note that leading zeros (in the
	 * coefficient of a number) are never significant.
	 * <p>
	 * {@code precision} will always be non-negative.
	 */
	private final int precision;

	/**
	 * The numeric representation type of the amount implementation. In most cases the
	 * representation type equals also the numeric implementation type used, whereas this is not
	 * required. Though only instances of this representation type can be accessed from from
	 * {@link MonetaryAmount#getNumber()}.
	 */
	private final Class<? extends MonetaryAmount> amountType;

	/** The generic amount bahavioral characteristics. */
	private final AmountFlavor flavor;

	/**
	 * This map contains arbitrary attributes, identified by class. This allows to store additional
	 * context data in a platform independent way, e.g. when using {@link java.math.BigDecimal} as a
	 * representation type on SE, the {@link java.math.RoundingMode} used can be stored as an
	 * attribute. Adding it as part of the API would break compatibility with SE.
	 */
	@SuppressWarnings("rawtypes")
	private final Map<Class, Object> attributes = new HashMap<>();

	/**
	 * Flag, if the scale is fixed. Fixed scaled numbers will always have a scale of
	 * {@link #maxScale}.
	 */
	private final boolean fixedScale;

	/**
	 * The maximal scale supported, always >= -1. Fixed scaled numbers a fixed value for
	 * {@link #maxScale}. -1 declares the maximal scale to be unlimited.
	 */
	private final int maxScale;

	/**
	 * Constructs a new {@code MonetaryContext} with the specified precision and rounding mode.
	 * 
	 * @param amountType
	 *            The {@link MonetaryAmount} implementation class.
	 * @param precision
	 *            The non-negative {@code int} precision setting.
	 * @param maxScale
	 *            the maximal scale.
	 * @param fixedScale
	 *            Flag for determining a fixed scale context.
	 * @param flavor
	 *            the {@link AmountFlavor} set.
	 * @param attributes
	 *            Any additional attributes.
	 * @throws IllegalArgumentException
	 *             if the {@code setPrecision} parameter is less than zero.
	 */
	@SuppressWarnings("rawtypes")
	private MonetaryContext(Class<? extends MonetaryAmount> amountType,
			int precision,
			int maxScale,
			boolean fixedScale,
			AmountFlavor flavor,
			Map<Class, Object> attributes) {
		if (precision < 0) {
			throw new IllegalArgumentException("precision < 0");
		}
		if (maxScale < -1) {
			throw new IllegalArgumentException("maxScale < -1");
		}
		this.precision = precision;
		this.fixedScale = fixedScale;
		this.maxScale = maxScale;
		this.amountType = amountType;
		this.flavor = flavor;
		if (attributes != null) {
			this.attributes.putAll(attributes);
		}
	}

	/**
	 * Returns the {@code precision} setting. This value is always non-negative.
	 * 
	 * @return an {@code int} which is the value of the {@code precision} setting
	 */
	public int getPrecision() {
		return precision;
	}

	/**
	 * Allows to check if {@code minScale == maxScale}.
	 * 
	 * @return {@code true} if {@code minScale == maxScale}.
	 */
	public boolean isFixedScale() {
		return fixedScale;
	}

	/**
	 * Get the maximal scale supported, always {@code >= -1}. Fixed scaled numbers will have
	 * {@code scale==maxScale} for all values. {@code -1} declares the maximal scale to be
	 * <i>unlimited</i>.
	 * 
	 * @return the maximal scale supported, always {@code >= -1}
	 */
	public int getMaxScale() {
		return maxScale;
	}

	/**
	 * Access the amount implementation type for the {@link MonetaryAmount} implementation.
	 * 
	 * @return the amount representation type, never {@code null}.
	 */
	public Class<? extends MonetaryAmount> getAmountType() {
		return amountType;
	}

	/**
	 * Get the {@link AmountFlavor}.
	 * 
	 * @return the {@link MonetaryAmount}s {@link AmountFlavor}.
	 */
	public AmountFlavor getAmountFlavor() {
		return flavor;
	}

	/**
	 * Access the additional attributes. This map contains arbitrary attributes, identified by
	 * class. This allows to store additional context data in a platform independent way, e.g. when
	 * using {@link java.math.BigDecimal} as a representation type on SE, the
	 * {@link java.math.RoundingMode} used can be stored as an attribute. Adding it as part of the
	 * API would break compatibility with SE.
	 * <p>
	 * The complete listing of the supported attributes depends on the concrete
	 * {@link MonetaryAmount} implementation classes and must be documented there.
	 * 
	 * @return the immutable additional attributes map, never {@code null}.
	 */
	@SuppressWarnings("rawtypes")
	public Map<Class, Object> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	/**
	 * Access a single attribute.
	 * 
	 * @param type
	 *            the attribute's type, not {@code null}.
	 * @return the attribute's value, or {@code null}, if no such attribute is present.
	 */
	@SuppressWarnings("unchecked")
	public <A> A getAttribute(Class<A> type) {
		return (A) this.attributes.get(type);
	}

	/**
	 * Access a single attribute, also providing a default value.
	 * 
	 * @param type
	 *            the attribute's type, not {@code null}.
	 * @param defaultValue
	 *            the default value, can also be {@code null}.
	 * @return the attribute's value, or the {@code defaultValue} passed, if no such attribute is
	 *         present.
	 */
	public <A> A getAttribute(Class<A> type,
			A defaultValue) {
		A t = getAttribute(type);
		if (t == null) {
			return defaultValue;
		}
		return t;
	}

	/**
	 * Access the types of the attributes of this {@link MonetaryContext}.
	 * 
	 * @return the types of the attributes of this {@link MonetaryContext}, never {@code null}.
	 */
	@SuppressWarnings("rawtypes")
	public Set<Class> getAttributeTypes() {
		return this.attributes.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + (fixedScale ? 1231 : 1237);
		result = prime * result + maxScale;
		result = prime
				* result
				+ ((amountType == null) ? 0 : amountType
						.hashCode());
		result = prime * result + precision;
		return result;
	}

	/*
	 * (non-Javadoc)
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
		MonetaryContext other = (MonetaryContext) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (fixedScale != other.fixedScale)
			return false;
		if (maxScale != other.maxScale)
			return false;
		if (amountType == null) {
			if (other.amountType != null)
				return false;
		} else if (!amountType.equals(other.amountType))
			return false;
		if (precision != other.precision)
			return false;
		return true;
	}

	/**
	 * Creates a new {@link MonetaryContext} targeting the the given amount type, using the given
	 * {@link MonetaryContext} (so converting it).
	 * 
	 * @param context
	 *            the {@link MonetaryContext} to be used.
	 * @param amountType
	 *            the target amount type.
	 * @return the {@link MonetaryContext}, not {@code null}.
	 */
	public static MonetaryContext from(
			MonetaryContext context, Class<? extends MonetaryAmount> amountType) {
		return new MonetaryContext.Builder(context).setAmountType(amountType)
				.build();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MonetaryContext [amountType=" + amountType
				+ ", precision="
				+ precision + ", maxScale=" + maxScale + ", fixedScale="
				+ fixedScale
				+ ", attributes=" + attributes + "]";
	}

	/**
	 * Reconstitute the {@code MonetaryContext} instance from a stream (that is, deserialize it).
	 * 
	 * @param s
	 *            the stream being read.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject(); // read in all fields
		// validate possibly bad fields
		if (precision < 0) {
			String message = "NumericContext: invalid digits in stream";
			throw new java.io.StreamCorruptedException(message);
		}
	}

	/**
	 * This class allows to build and create instances of {@link MonetaryContext} using a fluent
	 * API.
	 * <p>
	 * This class is not serializable and not thread-safe.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder {
		/** The maximal precision of the {@link MonetaryContext} built. */
		private int precision = 0;
		/**
		 * The amount's implementationType type for the {@link MonetaryContext} built.
		 */
		private Class<? extends MonetaryAmount> amountType;
		/**
		 * The basic required {@link AmountFlavor} of the {@link MonetaryAmount} implementation.
		 */
		private AmountFlavor amountFlavor = AmountFlavor.UNDEFINED;
		/** Any additional attributes of the {@link MonetaryContext} built. */
		@SuppressWarnings("rawtypes")
		private Map<Class, Object> attributes = new HashMap<>();
		/**
		 * Flag that the scale of the {@link MonetaryContext} built is fixed to #maxScale.
		 */
		private boolean fixedScale;
		/** Any maximal scale of the {@link MonetaryContext} built. */
		private int maxScale = -1;

		/**
		 * Creates a new {@link Builder}.
		 * 
		 * @param amountType
		 *            the numeric representation type, not {@code null}.
		 */
		public Builder(Class<? extends MonetaryAmount> amountType) {
			Objects.requireNonNull(amountType);
			this.amountType = amountType;
		}

		/**
		 * Creates a new {@link Builder}.
		 */
		public Builder() {
			this.amountType = MonetaryAmount.class; // unspecified
		}

		/**
		 * Creates a new {@link Builder} and uses the given context to initialize this instance.
		 * 
		 * @param context
		 *            the base {@link MonetaryContext} to be used.
		 */
		public Builder(MonetaryContext context) {
			this.amountType = context.getAmountType();
			this.maxScale = context.getMaxScale();
			this.fixedScale = context.isFixedScale();
			this.precision = context.getPrecision();
			this.attributes.putAll(context.getAttributes());
		}

		/**
		 * Sets a fixed scale, hereby setting both {@code minScale, maxScale} to {@code fixedScale}.
		 * 
		 * @param amountType
		 *            the amount type to be used, not {@code null}.
		 * @return the {@link Builder}, for chaining.
		 */
		public Builder setAmountType(
				Class<? extends MonetaryAmount> amountType) {
			Objects.requireNonNull(amountType);
			this.amountType = amountType;
			return this;
		}

		/**
		 * Sets the maximal precision supported.
		 * 
		 * @param precision
		 *            the maximal precision, aleays {@code >=0}, whereas 0 declares unlimited
		 *            precision.
		 * @return the {@link Builder}, for chaining.
		 */
		public Builder setPrecision(int precision) {
			if (precision < 0)
				throw new IllegalArgumentException("precision < -1");
			this.precision = precision;
			return this;
		}

		/**
		 * Sets a fixed scale, hereby setting both {@code minScale, maxScale} to {@code fixedScale}.
		 * 
		 * @param fixedScale
		 *            the min/max scale to be used, which must be {@code >=0}.
		 * @return the {@link Builder}, for chaining.
		 */
		public Builder setFixedScale(boolean fixedScale) {
			this.fixedScale = fixedScale;
			return this;
		}

		/**
		 * Sets a fixed scale, hereby setting both {@code minScale, maxScale} to {@code fixedScale}.
		 * 
		 * @param flavor
		 *            the {@link AmountFlavor} to be used, not {@code null}.
		 * @return the {@link Builder}, for chaining.
		 */
		public Builder setFlavor(AmountFlavor flavor) {
			Objects.requireNonNull(flavor);
			this.amountFlavor = flavor;
			return this;
		}

		/**
		 * Sets a maximal scale.
		 * 
		 * @param maxScale
		 *            the maximal scale to be used, which must be {@code >=-1}. {@code -1} means
		 *            unlimited maximal scale.
		 * @return the {@link Builder}, for chaining.
		 */
		public Builder setMaxScale(int maxScale) {
			if (maxScale < -1)
				throw new IllegalArgumentException("maxScale < -1");
			this.maxScale = maxScale;
			return this;
		}

		/**
		 * Adds the given attribute to the {@link MonetaryContext} to be built, hereby replacing
		 * every existing attribute with the same type.
		 * 
		 * @param attribute
		 *            the attribute to set, not {@code null}.
		 * @return the {@link Builder}, for chaining.
		 */
		public Builder setAttribute(Object attribute) {
			Objects.requireNonNull(attribute);
			this.attributes.put(attribute.getClass(), attribute);
			return this;
		}

		/**
		 * Adds the given attribute to the {@link MonetaryContext} to be built, hereby replacing
		 * every existing attribute with the same type.
		 * 
		 * @param type
		 *            the attribute's type, not {@code null}.
		 * @param attribute
		 *            the attribute to set, not {@code null}.
		 * @return the {@link Builder}, for chaining.
		 */
		public <T> Builder setAttribute(Class<T> type, T attribute) {
			Objects.requireNonNull(attribute);
			this.attributes.put(type, attribute);
			return this;
		}

		/**
		 * Builds a new instance of {@link MonetaryContext}.
		 * 
		 * @return a new instance of {@link MonetaryContext}, never {@code null}
		 * @throws IllegalArgumentException
		 *             if building of the {@link MonetaryContext} fails.
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public MonetaryContext build() {
			return new MonetaryContext(amountType, precision,
					maxScale, fixedScale,
					amountFlavor, attributes);
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "MonetaryContext.Builder [amountType=" + amountType
					+ ", precision="
					+ precision + ", maxScale=" + maxScale + ", fixedScale="
					+ fixedScale + ", attributes=" + attributes + "]";
		}

	}

}
