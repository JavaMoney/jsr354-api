/*
 *  Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
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
 *
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package net.java.javamoney.ri.common;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.money.util.AttributeProvider;

/**
 * Base class that provides functionalities as defined by
 * {@link AttributeProvider}, additionally allowing attributes to be set, until
 * the item is explicitly set to readonly, @see {@link AbstractConfigurableItem}
 * .
 * 
 * @author Anatole Tresch
 */
public abstract class AbstractAttributableItem extends AbstractConfigurableItem
		implements AttributeProvider {
	/** Attributes, initialized as required lazily. */
	private Map<String, Object> attributes;

	/**
	 * Access an attribute.
	 * 
	 * @param key
	 *            the attribute's key
	 * @param the
	 *            target type
	 * @return the attribute's value, or {@code null}
	 * @throws ClassCastException
	 *             if the item stored on the given {@code key} can not be cast
	 *             to {@code type}.
	 */
	@SuppressWarnings("unchecked")
	public final <A> A getAttribute(String key, Class<A> type) {
		if (attributes != null) {
			return (A) attributes.get(key);
		}
		return null;
	}

	/**
	 * Set an attribute.
	 * 
	 * @param key
	 *            the attribute's key
	 * @param value
	 *            the attribute's value
	 * @return any previous value, or {@code null}.
	 * @see #isReadOnloy()
	 * @throws IllegalStateException
	 *             if the instance is read-only.
	 */
	public final Object setAttribute(String key, Object value) {
		ensureWritable();
		if (attributes == null) {
			this.attributes = new HashMap<String, Object>();
		}
		return this.attributes.put(key, value);
	}

	/**
	 * Removes an attribute.
	 * 
	 * @param key
	 *            the attribute's key
	 * @return any previous value, or {@code null}.
	 * @see #isReadOnloy()
	 * @throws IllegalStateException
	 *             if the instance is read-only.
	 */
	public final Object removeAttribute(String key) {
		ensureWritable();
		if (attributes == null) {
			return null;
		}
		return this.attributes.remove(key);
	}

	/**
	 * Access all currently defined keys.
	 * 
	 * @return an enumeration containing the current keys, never {@code null}.
	 */
	public final Enumeration<String> getAttributeKeys() {
		if (attributes != null) {
			return Collections.enumeration(attributes.keySet());
		}
		return Collections.emptyEnumeration();
	}

	/**
	 * Access the type of an attribute given it's key.
	 * 
	 * @param key
	 *            the attribute's key
	 * @return the class of the atrtribute's value (if found), or {@code null},
	 *         if no such attribute is defined.
	 */
	public final Class<?> getAttributeType(String key) {
		if (attributes != null) {
			Object o = attributes.get(key);
			if (o != null) {
				return o.getClass();
			}
		}
		return null;
	}

	/**
	 * Access all attributes as an immutable {@link Map}.
	 * 
	 * @return an immutable {@link Map} containing the attribtues, never
	 *         {@code null}.
	 */
	public final Map<String, Object> getAttributes() {
		if (attributes != null) {
			return Collections.unmodifiableMap(this.attributes);
		}
		return Collections.emptyMap();
	}

	/**
	 * This method allows to clear all attributes.
	 * 
	 * @see #isReadOnloy()
	 * @throws IllegalStateException
	 *             if the instance is read-only.
	 */
	public final void clearAttributes() {
		ensureWritable();
		if (attributes != null) {
			this.attributes.clear();
		}
	}

}
