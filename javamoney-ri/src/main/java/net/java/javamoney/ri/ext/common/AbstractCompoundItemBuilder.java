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
 * A CompoundItemBuilder T is an CompoundItemBuilder that holds several
 * instances of a type T. In financial applications this is very useful
 * regarding several aspects:<br/>
 * <li>Passing several instance of T as one parameter into methods <li>
 * Returning several instance of T as result, e.g. from a complex financial
 * calculation. <li>Accessing series of data, e.g. by time or other
 * classification. <li>
 * Accessing multiple data from different providers.
 * 
 * 
 * @author Anatole Tresch
 */
public abstract class AbstractCompoundItemBuilder<T> implements
		CompoundItemBuilder<T> {

	private String type;

	private Map<Object, T> items = new HashMap<Object, T>();

	private T leadingItem;

	public AbstractCompoundItemBuilder(String type) {
		setType(type);
	}
	
	public AbstractCompoundItemBuilder(CompoundItem<T> baseItem) {
		if (baseItem != null) {
			this.type = baseItem.getType();
			this.items.putAll(baseItem.getAll());
			this.leadingItem = baseItem.getLeadingItem();
		}
	}

	@Override
	public T getLeadingItem() {
		return this.leadingItem;
	}

	@Override
	public T setLeadingItem(Object key, T item) {
		if (item == null) {
			throw new IllegalArgumentException("item may not be null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("key may not be null.");
		}
		T oldLeadingItem = this.leadingItem;
		this.leadingItem = item;
		return oldLeadingItem;
	}

	@Override
	public T removeLeadingItem() {
		T oldLeadingItem = this.leadingItem;
		this.leadingItem = null;
		return oldLeadingItem;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setType(String type) {
		if (type == null) {
			throw new IllegalArgumentException("type may not be null.");
		}
		this.type = type;
	}

	@Override
	public Enumeration<Object> getKeys() {
		return Collections.enumeration(this.items.keySet());
	}

	@Override
	public boolean isKeyDefined(Object key) {
		return this.items.containsKey(key);
	}

	@Override
	public T get(Object key) {
		return this.items.get(key);
	}

	@Override
	public Map<Object, T> getAll() {
		return Collections.unmodifiableMap(this.items);
	}

	@Override
	public T set(Object key, T item) {
		if (item == null) {
			throw new IllegalArgumentException("item may not be null.");
		}
		if (key == null) {
			throw new IllegalArgumentException("key may not be null.");
		}
		return this.items.put(key, item);
	}

	@Override
	public void set(Map<Object, T> items) {
		if (items == null) {
			throw new IllegalArgumentException("items may not be null.");
		}
		this.items.putAll(items);
	}

	@Override
	public T remove(Object key) {
		return this.items.remove(key);
	}

	@Override
	public void removeAll() {
		this.items.clear();
	}

	@Override
	public abstract CompoundItem<T> toCompoundItem();

}
