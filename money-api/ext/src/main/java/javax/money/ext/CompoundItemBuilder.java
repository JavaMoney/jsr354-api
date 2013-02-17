/*
 * Copyright (c) 2012-2013, Credit Suisse
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
package javax.money.ext;

import java.util.Enumeration;
import java.util.Map;

/**
 * A {@link CompoundItemBuilder} T is an builder for building a {@link CompoundItem}.
 * 
 * @author Anatole Tresch
 */
public interface CompoundItemBuilder<T> {
// TODO consider moving this to ext
	/**
	 * A {@link CompoundItem} may have a type identifier that helps to identify,
	 * what type of items object is returned.
	 * 
	 * @return the {@link CompoundItem}'s type, never null.
	 */
	public String getType();

	/**
	 * Allows to set the type of the new {@link CompoundItem} created with this
	 * builder.
	 * 
	 * @param type
	 *            The new type.
	 */
	public void setType(String type);

	/**
	 * Access an instance of T with the leading item.
	 * 
	 * @return the leading instance of type T, or {@code null}.
	 */
	public T getLeadingItem();

	/**
	 * Sets the new leading item of this instance.
	 * 
	 * @param key
	 *            the leading key.
	 * @param item
	 *            the leading item, not {@code null}.
	 * @return The previously defined leading item, or {@code null}.
	 */
	public T setLeadingItem(Object key, T item);

	/**
	 * Removes the leading item from this instance.
	 * 
	 * @return The previously defined leading item, or {@code null}.
	 */
	public T removeLeadingItem();

	/**
	 * Get the available keys in this {@link CompoundItem}.
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
	public T get(Object key);

	/**
	 * Access all items within this {@link CompoundItem}.
	 * 
	 * @return all items as an immutable {@link Map}.
	 */
	public Map<Object, T> getAll();

	/**
	 * Map a {@link T} to the given target key passed.
	 * 
	 * @param key
	 *            The target key, never null.
	 * @return the instance previously set, or null.
	 */
	public T set(Object key, T item);

	/**
	 * Set all {@link T} instances for this builder, hereby the
	 * items passed extend or overriden items already registered.
	 * 
	 * @param MonetaryAmounts
	 *            the instances to be applied.
	 */
	public void set(Map<Object, T> items);

	/**
	 * Remove a {@link T} registered.
	 * 
	 * @param key
	 *            The target key, never null.
	 * @return the {@link T} instance removed, or null.
	 */
	public T remove(Object key);

	/**
	 * Remove all {@link T} instances registered, rendering this
	 * instance to an empty {@link T}.
	 * 
	 * @return an immutable map fo the instances previously defined (can be
	 *         empty).
	 */
	public void removeAll();

	/**
	 * Creates an immutable {@link T} from this builder.
	 * 
	 * @return the corresponding immutable type, never null.
	 */
	public CompoundItem<T> toCompoundItem();

}
