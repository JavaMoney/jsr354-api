/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.format;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class extends the default localization mechanisms as defined by s
 * {@link Locale}, since for more complex usage scenarios different locale
 * settings for date, time and number may be applied within one
 * parsing/formatting usage scenario.
 * <p>
 * Further more when parsing of amount, it is often desirable to easily switch
 * of, the checks for the required decimals of the given target currency (called
 * lenient fraction parsing). In even more advanced us cases, also additional
 * information may be necessary to be passed to a formatter/parser instance,
 * that may be not reflected in explicitly modeled interface. Therefore a
 * generic mechanisms is required that allows to cover also these use cases.
 * <p>
 * For convenience the a simple instance if {@link Locale} can easily be
 * converted into a {@link LocalizationStyle} using the method
 * {@link #of(Locale)}.
 * 
 * @author Anatole Tresch
 */
public class LocalizationStyle implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8612440355369457473L;
	/** The internal key used for a time locale set. */
	public static final String TIME_LOCALE = "timeLocale";
	/** The internal key used for a date locale set. */
	public static final String DATE_LOCALE = "dateLocale";
	/** The internal key used for a number locale set. */
	public static final String NUMBER_LOCALE = "numberLocale";
	/** The internal key used for a translation locale set (default). */
	public static final String TRANSLATION_LOCALE = "locale";
	/** The internal key used for a formatting/parsing style. */
	public static final String DEFAULT_ID = "default";
	/** The style's name, by default ({@link #DEFAULT_ID}. */
	private String id;
	/** The style's generic properties. */
	private Map<String, Object> attributes = new HashMap<String, Object>();

	/**
	 * Creates a new instance of a style. This method will use the Locale
	 * returned from {@link Locale#getDefault()} as the style's default locale.
	 * 
	 * @param id
	 *            The style's identifier (not null).
	 */
	private LocalizationStyle(String id, Map<String, Object> attributes) {
		this.id = id;
		this.attributes.putAll(attributes);
	}

	/**
	 * Allows to evaluate if a style is a default style. A style is a default
	 * style, if its id equals to {@link #DEFAULT_ID}.
	 * <p>
	 * Note that nevertheless multiple default style instances may be defined
	 * that are not equal, since its attributes may differ.
	 * 
	 * @return true, if this style is a default style.
	 */
	public boolean isDefault() {
		return DEFAULT_ID.equals(getId());
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
	 * Get the style's (default) locale used for translation of textual values,
	 * and (if not specified explicitly as a fallback) for date, time and
	 * numbers.
	 * 
	 * @return the translation (default) locale
	 */
	public final Locale getTranslationLocale() {
		Locale locale = getAttribute(TRANSLATION_LOCALE, Locale.class);
		if (locale != null) {
			return locale;
		}
		return Locale.getDefault();
	}

	/**
	 * Get the style's locale used for formatting/parsing of numbers.
	 * 
	 * @return the number locale
	 */
	public final Locale getNumberLocale() {
		Locale locale = getAttribute(NUMBER_LOCALE, Locale.class);
		if (locale != null) {
			return locale;
		}
		return getTranslationLocale();
	}

	/**
	 * Get the style's locale for formatting/parsing of date instances.
	 * 
	 * @return the date locale
	 */
	public final Locale getDateLocale() {
		Locale locale = getAttribute(DATE_LOCALE, Locale.class);
		if (locale != null) {
			return locale;
		}
		return getTranslationLocale();
	}

	/**
	 * Get the style's locale for formatting/parsing of time data.
	 * 
	 * @return the time locale
	 */
	public final Locale getTimeLocale() {
		Locale locale = getAttribute(TIME_LOCALE, Locale.class);
		if (locale != null) {
			return locale;
		}
		return getDateLocale();
	}

	/**
	 * Get the current defined properties fo this style.
	 * 
	 * @return the properties defined
	 */
	public final Map<String, Object> getAttributes() {
		return new HashMap<String, Object>(attributes);
	}

	/**
	 * Read a property from this style.
	 * 
	 * @param key
	 *            The property's key
	 * @return the current property value, or null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key, Class<T> type) {
		return (T) attributes.get(key);
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
		LocalizationStyle other = (LocalizationStyle) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocalizationContext [id=" + id + ", properties=" + attributes
				+ "]";
	}

	/**
	 * Factory method to create a {@link LocalizationStyle} using a single
	 * {@link Locale}.
	 * 
	 * @param locale
	 *            The target {@link Locale}
	 * @return the {@link LocalizationStyle} created.
	 */
	public static LocalizationStyle of(Locale locale) {
		return new Builder(DEFAULT_ID, locale).build();
	}

	/**
	 * Factory method to create a {@link LocalizationStyle} using a single
	 * {@link Locale}.
	 * 
	 * @param styleId
	 *            The style's id.
	 * @param locale
	 *            The target {@link Locale}
	 * @return the {@link LocalizationStyle} created.
	 */
	public static LocalizationStyle of(String styleId, Locale locale) {
		return new Builder(styleId, locale).build();
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
	 * 
	 * @author Anatole Tresch
	 */
	public static final class Builder {

		/** The style's name, by default ({@link #DEFAULT_ID}. */
		private String id;

		/** The style's generic properties. */
		private Map<String, Object> attributes = new HashMap<String, Object>();

		/**
		 * Constructor.
		 * 
		 * @param locale
		 *            The target {@link Locale}
		 * @return the {@link LocalizationStyle} created.
		 */
		public Builder(Locale locale) {
			this(DEFAULT_ID, locale);
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
			if (!isBuildable()) {
				throw new IllegalStateException(
						"Builder can not build a LocalizationStyle.");
			}
			return new LocalizationStyle(this.id, this.attributes);
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
			if (getTranslationLocale() == null) {
				return false;
			}
			return true;
		}

		/**
		 * Constructor.
		 * 
		 * @param style
		 *            the style's identifier, not null.
		 */
		public Builder(String style) {
			this(style, Locale.getDefault());
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
		public Builder(String styleId, Locale locale) {
			setId(styleId);
			setTranslationLocale(locale);
		}

		/**
		 * Creates a new instance of a style.
		 * 
		 * @param id
		 *            The style's identifier (not null).
		 * @param translationLocale
		 *            the default locale (translation locale) to be used for all
		 *            locale usages.
		 * @param numberLocale
		 *            the locale to be used for numbers.
		 */
		public Builder(String id, Locale translationLocale, Locale numberLocale) {
			if (id == null) {
				throw new IllegalArgumentException("ID must not be null.");
			}
			this.id = id;
			if (translationLocale != null) {
				setTranslationLocale(translationLocale);
			}
			if (numberLocale != null) {
				setNumberLocale(numberLocale);
			}
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
		 * Sets the style's id.
		 * 
		 * @param id
		 * @return
		 */
		public Builder setId(String id) {
			if (id == null) {
				throw new IllegalArgumentException("style id required.");
			}
			this.id = id;
			return this;
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
		 * Get the style's (default) locale used for translation of textual
		 * values, and (if not specified explicitly as a fallback) for date,
		 * time and numbers.
		 * 
		 * @return the translation (default) locale
		 */
		public final Locale getTranslationLocale() {
			Locale locale = getAttribute(TRANSLATION_LOCALE, Locale.class);
			if (locale != null) {
				return locale;
			}
			return Locale.getDefault();
		}

		/**
		 * Get the style's locale used for formatting/parsing of numbers.
		 * 
		 * @return the number locale
		 */
		public final Locale getNumberLocale() {
			Locale locale = getAttribute(NUMBER_LOCALE, Locale.class);
			if (locale != null) {
				return locale;
			}
			return getTranslationLocale();
		}

		/**
		 * Get the style's locale for formatting/parsing of date instances.
		 * 
		 * @return the date locale
		 */
		public final Locale getDateLocale() {
			Locale locale = getAttribute(DATE_LOCALE, Locale.class);
			if (locale != null) {
				return locale;
			}
			return getTranslationLocale();
		}

		/**
		 * Get the style's locale for formatting/parsing of time data.
		 * 
		 * @return the time locale
		 */
		public final Locale getTimeLocale() {
			Locale locale = getAttribute(TIME_LOCALE, Locale.class);
			if (locale != null) {
				return locale;
			}
			return getDateLocale();
		}

		/**
		 * Get the current defined properties fo this style.
		 * 
		 * @return the properties defined
		 */
		public final Map<String, Object> getAttributes() {
			return new HashMap<String, Object>(attributes);
		}

		/**
		 * Sets the given property. This method is meant for adding custom
		 * properties. Setting a predefined property, e.g. {@link #DATE_LOCALE}
		 * will throw an {@link IllegalArgumentException}.
		 * 
		 * @param key
		 *            The target key
		 * @param value
		 *            The target value
		 * @return The object previously set, or null.
		 * @throws IllegalArgumentException
		 *             if the key passed equals to a key used for a predefined
		 *             property.
		 */
		public Builder setAttribute(String key, Object value) {
			attributes.put(key, value);
			return this;
		}

		/**
		 * Read a property from this style.
		 * 
		 * @param key
		 *            The property's key
		 * @return the current property value, or null.
		 */
		@SuppressWarnings("unchecked")
		public <T> T getAttribute(String key, Class<T> type) {
			return (T) attributes.get(key);
		}

		/**
		 * Removes the given property. This method is meant for removing custom
		 * properties. Setting a predefined property, e.g. {@link #DATE_LOCALE}
		 * will throw an {@link IllegalArgumentException}.
		 * 
		 * @param key
		 *            The key to be removed
		 * @return The object previously set, or null.
		 * @throws IllegalArgumentException
		 *             if the key passed equals to a key used for a predefined
		 *             property.
		 */
		public Builder removeAttribute(String key) {
			attributes.remove(key);
			return this;
		}

		/**
		 * Set the style's locale for formatting/parsing of numbers.
		 * 
		 * @param locale
		 *            The number locale to be used, or null for falling back to
		 *            the number locale.
		 * @return the number locale previously set, or null.
		 */
		public final Builder setTranslationLocale(Locale locale) {
			this.attributes.put(TRANSLATION_LOCALE, locale);
			return this;
		}

		/**
		 * Set the style's locale for formatting/parsing of dates.
		 * 
		 * @param locale
		 *            The date locale to be used, or null for falling back to
		 *            the translation locale.
		 * @return the date locale previously set, or null.
		 */
		public final Builder setDateLocale(Locale locale) {
			this.attributes.put(DATE_LOCALE, locale);
			return this;
		}

		/**
		 * Set the style's locale for formatting/parsing of time.
		 * 
		 * @param locale
		 *            The time locale to be used, or null for falling back to
		 *            the translation locale.
		 * @return the time locale previously set, or null.
		 */
		public final Builder setTimeLocale(Locale locale) {
			this.attributes.put(TIME_LOCALE, locale);
			return this;
		}

		/**
		 * Set the style's locale for formatting/parsing of numbers.
		 * 
		 * @param locale
		 *            The number locale to be used, or null for falling back to
		 *            the number locale.
		 * @return the number locale previously set, or null.
		 */
		public final Builder setNumberLocale(Locale locale) {
			this.attributes.put(NUMBER_LOCALE, locale);
			return this;
		}

	}

}
