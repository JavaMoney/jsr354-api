/*
 *  Copyright 2012 Credit Suisse (Anatole Tresch)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

	private String id;
	private Map<String, Object> properties = Collections
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
