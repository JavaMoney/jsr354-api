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
 * 
 * Contributors:
 *    Anatole Tresch - initial version.
 */
package javax.money.ext;

import java.util.Enumeration;
import java.util.Map;

/**
 * Defines a {@link CompoundValueTemplate} containing several results. Hereby
 * the different results are identified by arbitrary keys. Additionally each
 * {@link CompoundValueTemplate} has a <i>leading</i> item that identifies the
 * type of result.<br/>
 * A {@link CompoundValueTemplate} instance is defined to be implemented as
 * immutable object and therefore is very useful for modeling multidimensional
 * results objects or input parameters as they are common in financial
 * applications.
 * 
 * @author Anatole Tresch
 */
public interface CompoundValueTemplate {

	/**
	 * A {@link CompoundValueTemplate}may have a type identifier that helps to
	 * identify, what type of items object is returned.
	 * 
	 * @return the {@link CompoundValueTemplate}'s type, never null.
	 */
	public String getId();

	/**
	 * Get the available keys in this {@link CompoundValueTemplate}.
	 * 
	 * @return the keys defined, never null, but may empty.
	 */
	public Enumeration<Object> getKeys();

	/**
	 * Checks if an instance of T can be accessed given the key passed.
	 * 
	 * @see #get(Object)
	 * @param key
	 *            the key required.
	 * @return true, if an instance can be accessed given this key
	 */
	public boolean isKeyDefined(Object key);

	/**
	 * Access an instance of T with the given key.
	 * 
	 * @see #isKeyDefined(Object)
	 * @param key
	 *            The key identifying the item T to be returned.
	 * @return the instance of type T found.
	 * @throws IllegalArgumentException
	 *             if no such a instance is defined.
	 */
	public Class<?> getType(Object key);

	/**
	 * Access all items within this {@link CompoundValueTemplate}.
	 * 
	 * @return all items as an immutable {@link Map}.
	 */
	public Map<Object, Class<?>> getAll();

	/**
	 * Validates if the given {@link CompoundValue} defines all the attributes
	 * as required by this {@link CompoundValueTemplate} instance.
	 * 
	 * @param value
	 *            the {@link CompoundValue} to be validated.
	 * @return true if the passed {@link CompoundValue} is valid.
	 */
	public boolean isValid(CompoundValue value);

	/**
	 * Validates if the given {@link CompoundValue} defines all the attributes
	 * as required by this {@link CompoundValueTemplate} instance.
	 * 
	 * @param value
	 *            the {@link CompoundValue} to be validated.
	 * @see #isValid(CompoundValue)
	 * @throws IllegalArgumentException
	 *             if validation fails.
	 */
	public void validate(CompoundValue value);
}
