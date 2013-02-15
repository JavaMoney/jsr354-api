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
package net.java.javamoney.ri.ext.common;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.money.ext.CompoundItem;
import javax.money.ext.CompoundItemBuilder;

/**
 * Defines a {@link AbstractCompoundItem} containing T
 * instances.
 * 
 * @see CompoundItem
 * @author Anatole Tresch
 */
public abstract class AbstractCompoundItem<T> implements CompoundItem<T> {

	private final String type;
	private final T leadingItem;
	private final Map<Object, T> items = new HashMap<Object, T>();

	protected AbstractCompoundItem(String type) {
		if (type == null) {
			throw new IllegalArgumentException("type can not bve null.");
		}
		this.type = type;
		this.leadingItem = null;
	}

	protected AbstractCompoundItem(String type, Map<Object, T> items) {
		if (type == null) {
			throw new IllegalArgumentException("type can not bve null.");
		}
		this.type = type;
		this.items.putAll(items);
		this.leadingItem = null;
	}

	protected AbstractCompoundItem(String type, Map<Object, T> items,
			T leadingItem) {
		if (type == null) {
			throw new IllegalArgumentException("type can not bve null.");
		}
		this.type = type;
		this.leadingItem = leadingItem;
		this.items.putAll(items);
	}

	/**
	 * A compound item may have a type identifier that helps to identify, what
	 * type of compound items object is returned.
	 * 
	 * @return the compound item's type, never null.
	 */
	public String getType() {
		return this.type;
	}

	public Enumeration<Object> getKeys() {
		return Collections.enumeration(this.items.keySet());
	}

	public boolean isKeyDefined(Object key) {
		return this.items.containsKey(key);
	}

	public T get(Object key) {
		T r = this.items.get(key);
		if (r == null) {
			throw new IllegalArgumentException("Key is not defined: " + key);
		}
		return r;
	}

	public Map<Object, T> getAll() {
		return Collections.unmodifiableMap(this.items);
	}

	@Override
	public T getLeadingItem() {
		return this.leadingItem;
	}

	public abstract CompoundItemBuilder<T> toBuilder();
}
