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
package net.java.javamoney.ri.convert;

import java.util.Enumeration;
import java.util.Map;

import javax.money.convert.CompoundItem;
import javax.money.convert.CompoundItemBuilder;

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

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub

	}

	@Override
	public Enumeration<Object> getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isKeyDefined(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Object, T> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T set(Object key, T item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(Map<Object, T> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public T remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Object, T> removeAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompoundItem<T> toCompoundItem() {
		// TODO Auto-generated method stub
		return null;
	}

}
