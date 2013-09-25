/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package org.javamoney.format;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.javamoney.format.spi.ItemFormatFactorySpi;

/**
 * This class enhances the localization mechanisms as defined by {@link Locale}.
 * It is used to configure formatting/parsing ( {@link ItemFormat} instances )
 * with arbitrary parameters, thus also supporting very complex formatting
 * scenarios. The configuration parameters possible are either determined
 * <ul>
 * <li>by the several {@link FormatToken} added in sequence, and the
 * {@link ItemFactory}, when using an {@link ItemFormat} instance created with
 * the {@link ItemFormatBuilder}.
 * <li>by the preoconfigured and provided {@link ItemFormat} instance, provided
 * by an implementation of the {@link ItemFormatFactorySpi}.
<<<<<<< HEAD
=======
 * <p>
 * Further more when parsing amounts, it is often desirable to control the
 * checks for the required decimals of the given target currency (aka lenient
 * fraction parsing). In even more advanced use cases, also additional
 * configuration attributes may be necessary to be passed to a formatter/parser
 * instance.
>>>>>>> branch 'master' of https://github.com/JavaMoney/javamoney.git
 * <p>
 * Finally instances of {@link LocalizationStyle} can be registered to the
 * internal style cache, which allows to share the according styles, by
 * accessing them using {@link #of(Class)} of {@link #of(Class, String)}.
 * <p>
 * This class is thread safe, immutable and {@link Serializable}. The containing
 * {@link Builder} class however is NOT thread-safe.
 * 
 * @author Anatole Tresch
 */
public final class LocalizationStyle implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8612440355369457473L;
	/**
	 * The default style id used.
	 */
	public static final String DEFAULT_ID = "default";
	/**
	 * The style's name, by default ({@link #DEFAULT_ID}.
	 */
	private String id;
	/**
	 * The style's target type.
	 */
	private Class<?> targetType;
	/**
	 * The style's generic attributes.
	 */
	private Map<String, Object> attributes = new HashMap<String, Object>();

	/**
	 * The shared map of LocalizationStyle instances.
	 */
	private static final Map<String, LocalizationStyle> STYLE_MAP = new ConcurrentHashMap<String, LocalizationStyle>();

	/**
	 * Access a cached <i>default</i> style for a type. This equals to
	 * {@link #of(Class, String)}, hereby passing
	 * {@link LocalizationStyle#DEFAULT_ID} as {@code styleId}.
	 * 
	 * @param targetType
	 *            The target type, not {@code null}.
	 * @param styleId
	 *            The style's id, not {@code null}.
	 * @return the according style, if a corresponding style is cached, or
	 *         {@code null].

	 */
	public static final LocalizationStyle of(Class<?> targetType, String styleId) {
		return STYLE_MAP.get(getKey(targetType, styleId));
	}

	/**
	 * Access a cached <i>default</i> style for a type. This equals to
	 * {@link #of(Class, String)}, hereby passing
	 * {@link LocalizationStyle#DEFAULT_ID} as {@code styleId}.
	 * 
	 * @param targetType
	 *            The target type, not {@code null}.
	 * @return the according style, if a corresponding style is cached, or
	 *         {@code null].

	 */
	public static final LocalizationStyle of(Class<?> targetType) {
		return of(targetType, LocalizationStyle.DEFAULT_ID);
	}

	/**
	 * Collects all styles currently registered within the style cache for the
	 * given type.
	 * 
	 * @param targetType
	 *            the target type, not {@code null}.
	 * @return a set of style identifiers for the given type, never null.
	 */
	public static Collection<String> getSupportedStyleIds(Class<?> targetType) {
		Set<String> result = new HashSet<String>();
		String className = targetType.getName();
		for (String key : STYLE_MAP.keySet()) {
			int index = key.indexOf('_');
			if (className.equals(key.substring(0, index))) {
				result.add(key.substring(index + 1));
			}
		}
		return result;
	}

	/**
	 * Access a cached style for a type.
	 * 
	 * @param targetType
	 *            The target type, not {@code null}.
	 * @param styleId
	 *            The style's id, not {@code null}.
	 * @return the according style, if a corresponding style is cached, or
	 *         {@code null].

	 */
	private static String getKey(Class<?> targetType, String styleId) {
		return targetType.getName() + "_"
				+ (styleId != null ? styleId : "default");
	}

	/**
	 * Creates a new instance of a style.
	 * 
	 * @param id
	 *            The style's identifier (not null).
	 */
	private LocalizationStyle(Class<?> targetType, String id,
			Map<String, Object> attributes) {
		this.id = id;
		this.targetType = targetType;
		this.attributes.putAll(attributes);
	}

	/**
	 * Get the style's identifier, not null.
	 * 
	 * @return the style's id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the style's target type used.
	 * 
	 * @return the translation (default) locale
	 */
	public final Class<?> getTargetType() {
		return this.targetType;
	}

	/**
	 * Get the current defined attributes for this style.
	 * 
	 * @return the attributes defined
	 */
	public final Map<String, Object> getAttributes() {
		return new HashMap<String, Object>(attributes);
	}

	/**
	 * Read an attribute from this style.
	 * 
	 * @param key
	 *            The attribute key
	 * @return the current property value, or null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key, Class<T> type) {
		return (T) attributes.get(key);
	}

	/**
	 * Read a typed attribute from this style.
	 * 
	 * @param key
	 *            The attribute's type, resolving to a key representing the
	 *            fully qualified class name of the given type.
	 * @return the current attribute value, or {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Class<T> type) {
		return (T) attributes.get(type.getName());
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LocalizationStyle other = (LocalizationStyle) obj;
		if (attributes == null) {
			if (other.attributes != null) {
				return false;
			}
		} else if (!attributes.equals(other.attributes)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocalizationContext [id=" + id + ", attributes=" + attributes
				+ "]";
	}

	/**
	 * Method allows to check, if a given style is a default style, which is
	 * equivalent to a style id equal to {@link #DEFAULT_ID}.
	 * 
	 * @return true, if the instance is a default style.
	 */
	public boolean isDefaultStyle() {
		return DEFAULT_ID.equals(getId());
	}

	/**
	 * Builder to create new instances of {@link LocalizationStyle}.
	 * <p>
	 * This class is not thread-safe and should not be used in multiple threads.
	 * However {@link LocalizationStyle} instances created can securely shared
	 * among threads.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder {

		/**
		 * The formated type.
		 */
		private Class<?> targetType;

		/**
		 * The style's name, by default ({@link #DEFAULT_ID}.
		 */
		private String id;

		/**
		 * The style's attributes.
		 */
		private Map<String, Object> attributes = new HashMap<String, Object>();

		/**
		 * Constructor.
		 * 
		 * @param targetType
		 *            the target type, not null.
		 */
		public Builder(Class<?> targetType) {
			this.targetType = targetType;
			setId(LocalizationStyle.DEFAULT_ID);
		}

		/**
		 * Constructor.
		 * 
		 * @param styleId
		 *            The style's id.
		 * @param locale
		 *            The target {@link Locale}
		 * @return the {@link LocalizationStyle} created.
		 */
		public Builder(Class<?> targetType, String styleId) {
			setId(styleId);
			this.targetType = targetType;
		}

		/**
		 * Creates a new instance of a style. This method will copy all
		 * attributes and properties from the given style. The style created
		 * will not be read-only, even when the base style is read-only.
		 * 
		 * @param baseStyle
		 *            The style to be used as a base style.
		 */
		public Builder(LocalizationStyle baseStyle) {
			this.attributes.putAll(baseStyle.getAttributes());
			this.id = baseStyle.getId();
			this.targetType = baseStyle.getTargetType();
		}

		/**
		 * Creates a new instance of {@link LocalizationStyle}.
		 * 
		 * @return a new instance of {@link LocalizationStyle}, never
		 *         {@code null}
		 * @throws IllegalStateException
		 *             if this builder can not create a new instance.
		 * @see #isBuildable()
		 */
		public LocalizationStyle build() {
			return build(false);
		}

		/**
		 * Creates a new instance of {@link LocalizationStyle}.
		 * 
		 * @param register
		 *            flag for registering the style into the global cache.
		 * @return a new instance of {@link LocalizationStyle}, never
		 *         {@code null}
		 * @throws IllegalStateException
		 *             if this builder can not create a new instance.
		 * @see #isBuildable()
		 */
		public LocalizationStyle build(boolean register) {
			if (!isBuildable()) {
				throw new IllegalStateException(
						"Builder can not build a LocalizationStyle.");
			}
			LocalizationStyle style = new LocalizationStyle(this.targetType,
					this.id, this.attributes);
			if (register) {
				STYLE_MAP.put(getKey(this.targetType, this.id), style);
			}
			return style;
		}

		/**
		 * Checks if all required data is set to create a new
		 * {@link LocalizationStyle}.
		 * 
		 * @return true, if a new instance of {@link LocalizationStyle} can be
		 *         built.
		 */
		public boolean isBuildable() {
			if (this.id == null || this.id.isEmpty()) {
				return false;
			}
			if (targetType == null) {
				return false;
			}
			return true;
		}

		/**
		 * Constructor for a <i>default</i> style.
		 */
		public Builder() {
		}

		/**
		 * Method allows to check, if a given style is a default style, which is
		 * equivalent to a style {@code id} equal to {@link #DEFAULT_ID}.
		 * 
		 * @return {@code true}, if the instance is a <i>default</i> style.
		 */
		public boolean isDefaultStyle() {
			return DEFAULT_ID.equals(getId());
		}

		/**
		 * Sets the style's id.
		 * 
		 * @param id
		 *            the style's id, not {@code null}.
		 * @return this instance, for chaining.
		 */
		public Builder setId(String id) {
			if (id == null) {
				throw new IllegalArgumentException("style id required.");
			}
			this.id = id;
			return this;
		}

		public Class<?> getTargetType() {
			return this.targetType;
		}

		/**
		 * Sets the given targetType.
		 * 
		 * @param targetType
		 *            The instance's targetType, not {@code null}.
		 * @return The Builder instance for chaining.
		 */
		public <T> Builder withTargetType(Class<?> targetType) {
			if (targetType == null) {
				throw new IllegalArgumentException("targetType required.");
			}
			this.targetType = targetType;
			return this;
		}

		/**
		 * Sets the given styleId.
		 * 
		 * @param styleId
		 *            The instance's styleId, not {@code null}.
		 * @return The Builder instance for chaining.
		 */
		public <T> Builder withId(String id) {
			if (id == null) {
				throw new IllegalArgumentException("id required.");
			}
			this.id = id;
			return this;
		}

		/**
		 * Get the style's identifier, not {@code null}.
		 * 
		 * @return the style's id.
		 */
		public String getId() {
			return id;
		}

		/**
		 * Get the current defined attributes for this instance.
		 * 
		 * @return the attributes defined.
		 */
		public final Map<String, Object> getAttributes() {
			return new HashMap<>(attributes);
		}

		/**
		 * Sets the given property. This method is meant for adding custom
		 * properties. Setting a predefined property, e.g. {@link #DATE_LOCALE}
		 * will throw an {@link IllegalArgumentException}.
		 * 
		 * @param key
		 *            The target key, not {@code null}.
		 * @param value
		 *            The target value, not {@code null}.
		 * @return The Builder instance for chaining.
		 */
		public Builder withAttribute(String key, Object value) {
			attributes.put(key, value);
			return this;
		}

		/**
		 * Sets the given value as a property, using the fully qualified class
		 * name given as the key.
		 * 
		 * @param key
		 *            The instance's class, or subclass to be registered, not
		 *            {@code null}.
		 * @param value
		 *            The target value, not {@code null}.
		 * @return The Builder instance for chaining.
		 */
		public <T> Builder withAttribute(Class<T> key, T value) {
			attributes.put(key.getName(), value);
			return this;
		}

		/**
		 * Sets the given value as a property, using the fully qualified class
		 * name of the given instance as the key.
		 * 
		 * @see #getAttribute(Class)
		 * 
		 * @param value
		 *            The attribute value
		 * @return The Builder instance for chaining.
		 */
		public <T> Builder withAttribute(Object value) {
			attributes.put(value.getClass().getName(), value);
			return this;
		}

		/**
		 * Read a property from this style.
		 * 
		 * @param key
		 *            The property's key
		 * @return the current property value, or {@code null}.
		 */
		@SuppressWarnings("unchecked")
		public <T> T getAttribute(String key, Class<T> type) {
			return (T) attributes.get(key);
		}

		/**
		 * Read a typed property from this style.
		 * 
		 * @param key
		 *            The property's type, resolving to a key representing the
		 *            fully qualified class name of the given type.
		 * @return the current property value, or {@code null}.
		 */
		@SuppressWarnings("unchecked")
		public <T> T getAttribute(Class<T> type) {
			return (T) attributes.get(type.getName());
		}

		/**
		 * Removes the given property.
		 * 
		 * @param key
		 *            The key to be removed, not {@code null}
		 * @return The Builder instance for chaining.
		 */
		public Builder removeAttribute(String key) {
			attributes.remove(key);
			return this;
		}

		/**
		 * Removes the given property.
		 * 
		 * @param key
		 *            The key to be removed, not {@code null}
		 * @return The Builder instance for chaining.
		 */
		public <T> Builder removeAttribute(Class<T> key) {
			return removeAttribute(key.getName());
		}

	}

}
