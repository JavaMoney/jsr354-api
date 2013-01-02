/*
 * Copyright (c) 2012-2013,  Credit Suisse (Anatole Tresch), Werner Keil
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money.format;

import java.io.Serializable;
import java.util.Collections;
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
 * TODO check if this class can be moved to {@code java.util}.
 */
public final class LocalizationStyle implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8612440355369457473L;
	/** The internal key used for a time locale set. */
	private static final String TIME_LOCALE = "timeLocale";
	/** The internal key used for a date locale set. */
	private static final String DATE_LOCALE = "dateLocale";
	/** The internal key used for a number locale set. */
	private static final String NUMBER_LOCALE = "numberLocale";
	/** The internal key used for a translation locale set (default). */
	private static final String LOCALE = "locale";
	/** The internal key used for a formatting/parsing style. */
	private static final String DEFAULT_ID = "default";
	/** The style's name, by default ({@link #DEFAULT_ID}. */
	private String id;
	/** The style's generic properties. */
	private Map<String, Object> attributes = Collections
			.synchronizedMap(new HashMap<String, Object>());

	/**
	 * Flag to make a localization style read only, so it can be used (and
	 * cached) similar to a immutable object.
	 */
	private boolean readOnly = false;

	/**
	 * Creates a new instance of a style. This method will use the Locale
	 * returned from {@link Locale#getDefault()} as the style's default locale.
	 * 
	 * @param id
	 *            The style's identifier (not null).
	 */
	public LocalizationStyle(String id) {
		this(id, Locale.getDefault());
	}

	/**
	 * Creates a new instance of a style.
	 * 
	 * @param id
	 *            The style's identifier (not null).
	 * @param locale
	 *            the default locale to be used for all locale usages.
	 */
	public LocalizationStyle(String id, Locale locale) {
		this(id, locale, locale);
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
	public LocalizationStyle(String id, Locale translationLocale,
			Locale numberLocale) {
		if (id == null) {
			throw new IllegalArgumentException("ID must not be null.");
		}
		this.id = id;
	}

	/**
	 * Creates a new instance of a style. This method will copy all attributes
	 * and properties from the given style. The style created will not be
	 * read-only, even when the base style is read-only.
	 * 
	 * @param baseStyle
	 *            The style to be used as a base style.
	 */
	public LocalizationStyle(LocalizationStyle baseStyle) {
		this.attributes.putAll(baseStyle.getAttributes());
		this.id = baseStyle.getId();
	}

	/**
	 * This method allows to check, if the given style can be changed or, if it
	 * read only.
	 * 
	 * @return true, if the style is read-only.
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	private void throwsExceptionIfReadonly() {
		if (readOnly) {
			throw new IllegalStateException(
					"This instance is immutable and can not be ^changed.");
		}
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
		Locale locale = (Locale) getAttribute(LOCALE);
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
		Locale locale = (Locale) getAttribute(NUMBER_LOCALE);
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
		Locale locale = (Locale) getAttribute(DATE_LOCALE);
		if (locale != null) {
			return locale;
		}
		return getTranslationLocale();
	}

	/**
	 * Set the style's locale for formatting/parsing of dates.
	 * 
	 * @param locale
	 *            The date locale to be used, or null for falling back to the
	 *            translation locale.
	 * @return the date locale previously set, or null.
	 */
	public final Locale setDateLocale(Locale locale) {
		return (Locale) setAttribute(DATE_LOCALE, locale);
	}

	/**
	 * Set the style's locale for formatting/parsing of time.
	 * 
	 * @param locale
	 *            The time locale to be used, or null for falling back to the
	 *            translation locale.
	 * @return the time locale previously set, or null.
	 */
	public final Locale setTimeLocale(Locale locale) {
		return (Locale) setAttribute(TIME_LOCALE, locale);
	}

	/**
	 * Set the style's locale for formatting/parsing of numbers.
	 * 
	 * @param locale
	 *            The number locale to be used, or null for falling back to the
	 *            number locale.
	 * @return the number locale previously set, or null.
	 */
	public final Locale setNumberLocale(Locale locale) {
		return (Locale) setAttribute(NUMBER_LOCALE, locale);
	}

	/**
	 * Get the style's locale for formatting/parsing of time data.
	 * 
	 * @return the time locale
	 */
	public final Locale getTimeLocale() {
		Locale locale = (Locale) getAttribute(TIME_LOCALE);
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
		synchronized (attributes) {
			return new HashMap<String, Object>(attributes);
		}
	}

	/**
	 * Sets the given property. This method is meant for adding custom
	 * properties. Setting a predefined property, e.g. {@link #DATE_LOCALE} will
	 * throw an {@link IllegalArgumentException}.
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
	public Object setAttribute(String key, Serializable value) {
		throwsExceptionIfReadonly();
		synchronized (attributes) {
			return attributes.put(key, value);
		}
	}

	/**
	 * Read a property from this style.
	 * 
	 * @param key
	 *            The property's key
	 * @return the current property value, or null.
	 */
	public Object getAttribute(String key) {
		synchronized (attributes) {
			return attributes.get(key);
		}
	}

	/**
	 * Removes the given property. This method is meant for removing custom
	 * properties. Setting a predefined property, e.g. {@link #DATE_LOCALE} will
	 * throw an {@link IllegalArgumentException}.
	 * 
	 * @param key
	 *            The key to be removed
	 * @return The object previously set, or null.
	 * @throws IllegalArgumentException
	 *             if the key passed equals to a key used for a predefined
	 *             property.
	 */
	public Object removeAttribute(String key) {
		throwsExceptionIfReadonly();
		synchronized (attributes) {
			return attributes.remove(key);
		}
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
		synchronized (attributes) {
			result = prime * result
					+ ((attributes == null) ? 0 : attributes.hashCode());
		}
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
		synchronized (attributes) {
			if (attributes == null) {
				if (other.attributes != null)
					return false;
			} else if (!attributes.equals(other.attributes))
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
		synchronized (attributes) {
			return "LocalizationContext [id=" + id + ", properties="
					+ attributes + "]";
		}
	}

	/**
	 * Factory method to create a {@link LocalizationStyle} using a single
	 * {@link Locale}.
	 * 
	 * @param locale
	 * @return
	 */
	public static LocalizationStyle of(Locale locale) {
		return new LocalizationStyle(DEFAULT_ID, locale);
	}

}
