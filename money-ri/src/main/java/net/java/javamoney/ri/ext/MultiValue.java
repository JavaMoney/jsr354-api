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
package net.java.javamoney.ri.ext;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.money.ext.CompoundValue;

/**
 * Defines a {@link MultiValue} containing T
 * instances.
 * 
 * @see CompoundValue
 * @author Anatole Tresch
 */
public class MultiValue implements CompoundValue {

	private final String id;
	private final Map<Object, Object> items = new HashMap<Object, Object>();

	protected MultiValue(String id) {
		if (id == null) {
			throw new IllegalArgumentException("id can not bve null.");
		}
		this.id = id;
	}

	protected MultiValue(String id, Map<Object, Object> items) {
		this(id);
		this.items.putAll(items);
	}

	/**
	 * A compound item may have a type identifier that helps to identify, what
	 * type of compound items object is returned.
	 * 
	 * @return the compound item's type, never null.
	 */
	public String getId() {
		return this.id;
	}

	public Enumeration<Object> getKeys() {
		return Collections.enumeration(this.items.keySet());
	}

	public boolean isKeyDefined(Object key) {
		return this.items.containsKey(key);
	}

	public <T> T get(Object key, Class<T> type) {
		@SuppressWarnings("unchecked")
		T r = (T) this.items.get(key);
		if (r == null) {
			throw new IllegalArgumentException("Key is not defined: " + key);
		}
		return r;
	}

	@Override
	public Class<?> getType(Object key) {
		Object o = this.items.get(key);
		if(o!=null){
			return o.getClass();
		}
		return null;
	}
	
	public Map<Object, ?> getAll() {
		return Collections.unmodifiableMap(this.items);
	}


	public Builder toBuilder(){
		return new Builder(this);
	}
	
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
	public static class Builder {

		private String id;

		private Map<Object, Object> items = new HashMap<Object, Object>();

		public Builder(String id) {
			setId(id);
		}

		public Builder(CompoundValue baseItem) {
			if (baseItem != null) {
				setId(baseItem.getId());
				this.items.putAll(baseItem.getAll());
			}
		}

		public String getId() {
			return this.id;
		}

		public void setId(String id) {
			if (id == null) {
				throw new IllegalArgumentException("id may not be null.");
			}
			this.id = id;
		}

		public Enumeration<Object> getKeys() {
			return Collections.enumeration(this.items.keySet());
		}

		public boolean isKeyDefined(Object key) {
			return this.items.containsKey(key);
		}

		@SuppressWarnings("unchecked")
		public <T> T get(Object key, Class<T> type) {
			return (T) this.items.get(key);
		}

		public Map<Object, ?> getAll() {
			return Collections.unmodifiableMap(this.items);
		}

		public Object set(Object key, Object item) {
			if (item == null) {
				throw new IllegalArgumentException("item may not be null.");
			}
			if (key == null) {
				throw new IllegalArgumentException("key may not be null.");
			}
			return this.items.put(key, item);
		}

		public void set(Map<Object, Object> items) {
			if (items == null) {
				throw new IllegalArgumentException("items may not be null.");
			}
			this.items.putAll(items);
		}

		public Object remove(Object key) {
			return this.items.remove(key);
		}

		public void removeAll() {
			this.items.clear();
		}

		public CompoundValue build(){
			return new MultiValue(this.id, this.items);
		}

	}

}
