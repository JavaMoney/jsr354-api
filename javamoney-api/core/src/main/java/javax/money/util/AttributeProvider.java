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
package javax.money.util;

import java.util.Enumeration;

/**
 * This interface is used by several parts within JSR 354, since many other the
 * artifacts defined must be extensible by arbitrary attributes.
 * 
 * @author Anatole Tresch
 */
public interface AttributeProvider {

	/**
	 * Access additional attributes of this currency instance. This allows to
	 * add additional codes or extended information by SPI providers. For
	 * instance there are ISO currency codes existing that may represented by
	 * different country specific currencies. The detailed country can be added
	 * as an attribute here.
	 * 
	 * @param key
	 *            The attribute's key, never null.
	 * @return the according attribute value, or null.
	 */
	public <T> T getAttribute(String key, Class<T> type);

	/**
	 * Access the extended attributes defined.
	 * 
	 * @return the attribute key available, never null.
	 */
	public Enumeration<String> getAttributeKeys();

	/**
	 * Access the type of an attribute.
	 * 
	 * @param key
	 *            The attribute key
	 * @return the attribute's value class, or null.
	 */
	public Class<?> getAttributeType(String key);
}
