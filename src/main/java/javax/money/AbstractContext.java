/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class models a context. Such a context allows to define extendible and
 * portable contexts with any kind of attributes in a type safe way.
 * <p>
 * Instances of this class should be immutable and thread-safe.
 * 
 * @author Anatole Tresch
 */
public abstract class AbstractContext implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 5579720004786348255L;

	/**
	 * Map with the attributes of this context.
	 */
	protected final Map<Class<?>, Map<Object, Object>> attributes = new HashMap<>();

	/**
	 * Private constructor, used by {@link AbstractBuilder}.
	 * 
	 * @param builder
	 *            the Builder.
	 */
	protected AbstractContext(AbstractBuilder builder) {
		this.attributes.putAll(builder.attributes);
	}

	/**
	 * Set a value, use this method hereby only for initialization to keep
	 * immutable semantics.
	 * 
	 * @param attribute
	 *            the attribute
	 * @param key
	 *            the key
	 * @param type
	 *            THE type
	 * @return the value previously active, or null.
	 */
	// type safe cast
	@SuppressWarnings("unchecked")
	protected final <T> T set(T attribute, Object key, Class<? extends T> type) {
		Map<Object, Object> typedAttrs = attributes.get(type);
		if (typedAttrs == null) {
			typedAttrs = new HashMap<>();
			attributes.put(type, typedAttrs);
		}
		return (T) typedAttrs.put(key, attribute);
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
	// type safe cast
	@SuppressWarnings("unchecked")
	protected final <T> T set(T value) {
		return (T) set(value, value.getClass(), value.getClass());
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
	public <T> T getNamedAttribute(Class<T> type, Object key) {
		return getNamedAttribute(type, key, null);
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
	public <T> T getNamedAttribute(Class<T> type, Object key, T defaultValue) {
		Map<Object, ?> typedAttrs = attributes.get(type);
		if (typedAttrs != null) {
			T val = (T) typedAttrs.get(key);
			if (val != null) {
				return val;
			}
		}
		return defaultValue;
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
		return getNamedAttribute(type, type);
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
	public <T> T getAttribute(Class<T> type, T defaultValue) {
		T t = getAttribute(type);
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
	public Set<Class<?>> getAttributeTypes() {
		return this.attributes.keySet();
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
		AbstractContext other = (AbstractContext) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [attributes=" + attributes + "]";
	}

	/**
	 * Builder class to create {@link AbstractContext} instances. Instances of
	 * this class are not thread-safe.
	 * 
	 * @author Anatole Tresch
	 */
	public static abstract class AbstractBuilder<B extends AbstractBuilder<B>> {

		/**
		 * Map with the attributes of this context.
		 */
		protected final Map<Class<?>, Map<Object, Object>> attributes = new HashMap<>();

		/**
		 * Create a new Builder instance without any provider, e.g. for creating
		 * new {@link AbstractContext} instances for querying.
		 */
		public AbstractBuilder() {
		}

		/**
		 * Create a new Builder, hereby using the given {@link AbstractContext}
		 * 's values as defaults. This allows changing an existing
		 * {@link AbstractContext} easily.
		 * 
		 * @param context
		 *            the context, not {@code null}
		 */
		public AbstractBuilder(AbstractContext context) {
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
		public B set(Object value) {
			return set(value, value.getClass(), value.getClass());
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
		public B set(Object value, Object key) {
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
		public <T> B set(T attribute, Object key, Class<? extends T> type) {
			Map<Object, Object> typedAttrs = attributes.get(type);
			if (typedAttrs == null) {
				typedAttrs = new HashMap<>();
				attributes.put(type, typedAttrs);
			}
			typedAttrs.put(key, attribute);
			return (B) this;
		}

		/**
		 * Creates a new {@link AbstractContext} with the data from this Builder
		 * instance.
		 * 
		 * @return a new {@link AbstractContext}. never {@code null}.
		 */
		public abstract AbstractContext create();

		@Override
		public String toString() {
			return getClass().getSimpleName() + " [attributes=" + attributes + "]";
		}
		
		
	}

}
