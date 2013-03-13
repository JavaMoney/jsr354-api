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
