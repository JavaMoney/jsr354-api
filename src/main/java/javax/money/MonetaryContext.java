/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class models the numeric capabilities of a {@link MonetaryAmount} in a
 * platform independent way. It provides information about
 * <ul>
 * <li>the maximal precision supported (0, for unlimited precision).
 * <li>the minimum scale (>=0)
 * <li>the maximal scale (>= -1, -1 for unlimited scale).
 * <li>the numeric representation class.
 * <li>any other attributes, identified by the attribute type, e.g.
 * {@link java.math.RoundingMode}.
 * </ul>
 * <p>
 * This class is serializable and thread-safe.
 * 
 * @author Anatole Tresch
 */
public final class MonetaryContext implements Serializable {

	private static final long serialVersionUID = 5579720004786848255L;

	/**
	 * The number of digits to be used for an operation. A value of 0 indicates
	 * that unlimited precision (as many digits as are required) will be used.
	 * Note that leading zeros (in the coefficient of a number) are never
	 * significant.
	 * <p>
	 * {@code precision} will always be non-negative.
	 */
	private final int precision;

	/**
	 * The numeric representation type of the amount implementation. In most
	 * cases the representation type equals also the numeric implementation type
	 * used, whereas this is not required. Though only instances of this
	 * representation type can be accessed from from
	 * {@link MonetaryAmount#getNumber()}.
	 */
	private final Class<?> numberType;

	/**
	 * This map contains arbitrary attributes, identified by class. This allows
	 * to store additional context data in a platform independent way, e.g. when
	 * using {@link BigDecimal} as a representation type on SE, the
	 * {@link RoundingMode} used can be stored as an attribute. Adding it as
	 * part of the API would break compatibility with SE.
	 */
	@SuppressWarnings("rawtypes")
	private final Map<Class, Object> attributes = new HashMap<>();

	/**
	 * Flag, if the scale is fixed. Fixed scaled numbers will always have a
	 * scale of {@link #maxScale}.
	 */
	private final boolean fixedScale;

	/**
	 * The maximal scale supported, always >= -1. Fixed scaled numbers will have
	 * the same value for {@link #minScale} and {@link #maxScale}. -1 declares
	 * the maximal scale to be unlimited.
	 */
	private final int maxScale;

	/**
	 * Constructs a new {@code MonetaryContext} with the specified precision and
	 * rounding mode.
	 * 
	 * @param numberType
	 *            The numeric representation class.
	 * @param precision
	 *            The non-negative {@code int} precision setting.
	 * @param convertibleTypes
	 *            The convertible target classes for this type.
	 * @throws IllegalArgumentException
	 *             if the {@code setPrecision} parameter is less than zero.
	 */
	@SuppressWarnings("rawtypes")
	private MonetaryContext(Class numberType, int precision, int maxScale,
			boolean fixedScale,
			Map<Class, Object> attributes) {
		Objects.requireNonNull(numberType);
		if (precision < 0)
			throw new IllegalArgumentException("precision < 0");
		if (maxScale < -1)
			throw new IllegalArgumentException("maxScale < -1");
		this.precision = precision;
		this.fixedScale = fixedScale;
		this.maxScale = maxScale;
		this.numberType = numberType;
		if (attributes != null) {
			this.attributes.putAll(attributes);
		}
	}

	/**
	 * Returns the {@code precision} setting. This value is always non-negative.
	 * 
	 * @return an {@code int} which is the value of the {@code precision}
	 *         setting
	 */
	public int getMaxPrecision() {
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
	 * Get the maximal scale supported, always {@code >= -1}. Fixed scaled
	 * numbers will have the same value for {@link #minScale} and
	 * {@link #maxScale}. {@code -1} declares the maximal scale to be
	 * <i>unlimited</i>.
	 * 
	 * @return the maximal scale supported, always {@code >= -1}
	 */
	public int getMaxScale() {
		return maxScale;
	}

	/**
	 * Access the numeric representation type for the {@link MonetaryAmount}
	 * implementation. In most cases the representation type equals also the
	 * numeric implementation type used, whereas this is not required. Though
	 * only instances of this representation type can be accessed from from
	 * {@link MonetaryAmount#getNumber()}.
	 * 
	 * @return the numeric representation type, never {@code null}.
	 */
	public Class<?> getNumberType() {
		return numberType;
	}

	/**
	 * Access the additional attributes. This map contains arbitrary attributes,
	 * identified by class. This allows to store additional context data in a
	 * platform independent way, e.g. when using {@link BigDecimal} as a
	 * representation type on SE, the {@link RoundingMode} used can be stored as
	 * an attribute. Adding it as part of the API would break compatibility with
	 * SE.
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
	 * @return the attribute's value, or {@code null}, if no such attribute is
	 *         present.
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
	 * @return the attribute's value, or the {@code defaultValue} passed, if no
	 *         such attribute is present.
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
	 * @return the types of the attributes of this {@link MonetaryContext},
	 *         never {@code null}.
	 */
	@SuppressWarnings("rawtypes")
	public Set<Class> getAttributeTypes() {
		return this.attributes.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MonetaryContext [numberType=" + numberType + ", precision="
				+ precision + ", maxScale=" + maxScale + ", fixedScale="
				+ fixedScale
				+ ", attributes=" + attributes + "]";
	}

	/**
	 * Reconstitute the {@code MonetaryContext} instance from a stream (that is,
	 * deserialize it).
	 * 
	 * @param s
	 *            the stream being read.
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
	 * This class allows to build and create instances of
	 * {@link MonetaryContext} using a fluent API.
	 * <p>
	 * This class is not serializable and not thread-safe.
	 * 
	 * @author Anatole Tresch
	 */
	public final static class Builder {
		/** The maximal precision of the {@link MonetaryContext} built. */
		private int precision;
		/**
		 * The numeric representation type of the {@link MonetaryContext} built.
		 */
		private Class<?> numberType;
		/** Any additional attributes of the {@link MonetaryContext} built. */
		@SuppressWarnings("rawtypes")
		private Map<Class, Object> attributes = new HashMap<>();
		/**
		 * Flag that the scale of the {@link MonetaryContext} built is fixed to
		 * #maxScale.
		 */
		private boolean fixedScale;
		/** Any maximal scale of the {@link MonetaryContext} built. */
		private int maxScale = -1;

		/**
		 * Creates a new {@link Builder}.
		 * 
		 * @param numberType
		 *            the numeric representation type, not {@code null}.
		 */
		public Builder(Class<?> numberType) {
			Objects.requireNonNull(numberType);
			this.numberType = numberType;
		}

		/**
		 * Sets the maximal precision supported.
		 * 
		 * @param precision
		 *            the maximal precision, aleays {@code >=0}, whereas 0
		 *            declares unlimited precision.
		 * @return the {@link Builder}, for chaining.
		 */
		public Builder setPrecision(int precision) {
			if (precision < 0)
				throw new IllegalArgumentException("precision < -1");
			this.precision = precision;
			return this;
		}

		/**
		 * Sets a fixed scale, hereby setting both {@code minScale, maxScale} to
		 * {@code fixedScale}.
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
		 * Sets a maximal scale.
		 * 
		 * @param maxScale
		 *            the maximal scale to be used, which must be {@code >=-1}.
		 *            {@code -1} means unlimited maximal scale.
		 * @return the {@link Builder}, for chaining.
		 */
		public Builder setMaxScale(int maxScale) {
			if (maxScale < -1)
				throw new IllegalArgumentException("maxScale < -1");
			this.maxScale = maxScale;
			return this;
		}

		/**
		 * Adds the given attribute to the {@link MonetaryContext} to be built,
		 * hereby replacing every existing attribute with the same type.
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
		 * Adds the given attribute to the {@link MonetaryContext} to be built,
		 * hereby replacing every existing attribute with the same type.
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
		public MonetaryContext build() {
			return new MonetaryContext(numberType, precision,
					maxScale, fixedScale,
					attributes);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "MonetaryContext.Builder [numberType=" + numberType
					+ ", precision="
					+ precision + ", maxScale=" + maxScale + ", fixedScale="
					+ fixedScale + ", attributes=" + attributes + "]";
		}

	}

}
