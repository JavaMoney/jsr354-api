/*
 * Copyright (c) 2012,  Credit Suisse (Anatole Tresch), Werner Keil
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
 *  * Neither the name of JSR-310 nor the names of its contributors
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

public final class LocalizationStyle implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8612440355369457473L;
	private static final String TIME_LOCALE = "timeLocale";
	private static final String DATE_LOCALE = "dateLocale";
	private static final String NUMBER_LOCALE = "numberLocale";
	private static final String LOCALE = "locale";
	private static final String DEFAULT_ID = "default";

	private final String id;
	private final Map<String, Object> properties = Collections
			.synchronizedMap(new HashMap<String, Object>());

	public LocalizationStyle(String id) {
		this(id, Locale.getDefault());
	}

	public LocalizationStyle(String id, Locale translationLocale) {
		this(id, translationLocale, translationLocale);
	}

	public LocalizationStyle(String id, Locale translationLocale,
			Locale numberLocale) {
		if (id == null) {
			throw new IllegalArgumentException("ID must not be null.");
		}
		this.id = id;
	}

	public String getID() {
		return id;
	}

	/**
	 * @return the translationLocale
	 */
	public final Locale getTranslationLocale() {
		Locale locale = (Locale) getProperty(LOCALE);
		if (locale != null) {
			return locale;
		}
		return Locale.getDefault();
	}

	/**
	 * @return the numberLocale
	 */
	public final Locale getNumberLocale() {
		Locale locale = (Locale) getProperty(NUMBER_LOCALE);
		if (locale != null) {
			return locale;
		}
		return getTranslationLocale();
	}

	/**
	 * @return the dateLocale
	 */
	public final Locale getDateLocale() {
		Locale locale = (Locale) getProperty(DATE_LOCALE);
		if (locale != null) {
			return locale;
		}
		return getTranslationLocale();
	}

	public final Locale setDateLocale(Locale locale) {
		return (Locale) setProperty(DATE_LOCALE, locale);
	}

	public final Locale setTimeLocale(Locale locale) {
		return (Locale) setProperty(TIME_LOCALE, locale);
	}

	public final Locale setNumberLocale(Locale locale) {
		return (Locale) setProperty(NUMBER_LOCALE, locale);
	}

	/**
	 * @return the timeLocale
	 */
	public final Locale getTimeLocale() {
		Locale locale = (Locale) getProperty(TIME_LOCALE);
		if (locale != null) {
			return locale;
		}
		return getDateLocale();
	}

	/**
	 * @return the properties
	 */
	public final Map<String, Object> getProperties() {
		synchronized (properties) {
			return new HashMap<String, Object>(properties);
		}
	}

	public Object setProperty(String key, Serializable value) {
		synchronized (properties) {
			return this.properties.put(key, value);
		}
	}

	public Object getProperty(String key) {
		synchronized (properties) {
			return this.properties.get(key);
		}
	}

	public Object removeProperty(String key) {
		synchronized (properties) {
			return this.properties.remove(key);
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
		synchronized (properties) {
			result = prime * result
					+ ((properties == null) ? 0 : properties.hashCode());
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
		synchronized (properties) {
			if (properties == null) {
				if (other.properties != null)
					return false;
			} else if (!properties.equals(other.properties))
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
		synchronized (properties) {
			return "LocalizationContext [id=" + id + ", properties="
					+ properties + "]";
		}
	}
	
	
	public static LocalizationStyle of(Locale locale){
		return new LocalizationStyle(DEFAULT_ID, locale);
	}
	
}
