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
package javax.money.provider;

import java.util.Enumeration;

import javax.money.MonetaryAmount;

/**
 * Component for accessing {@link MonetaryAmount} instances. It is provided by
 * the Money singleton.
 * 
 * @author Anatole Tresch
 */
public interface MonetaryAmountProvider {

	/**
	 * This method defines the number class used by this factory instances. The
	 * Monetary singleton allows to use monetary amounts using different numeric
	 * representations.
	 * 
	 * @return The number representation class used by this factory, never null.
	 */
	public Enumeration<Class<?>> getSupportedNumberClasses();

	/**
	 * Allows to check if a specific number representation is currently
	 * supported.
	 * 
	 * @param numberClass
	 *            the required number representation class.
	 * @return true, if a {@link MonetaryAmountFactory} can be accessed for the
	 *         given class.
	 * @see #getMonetaryAmountFactory()
	 */
	public boolean isNumberClassSupported(Class<?> numberClass);

	/**
	 * Access the number class used for representation by default.
	 * 
	 * @return the default number class, never null.
	 */
	public Class<?> getDefaultNumberClass();

	/**
	 * Create an amount given the parameters.
	 * 
	 * @param numberClass
	 *            The required number representation class.
	 * @return The {@link MonetaryAmountFactory} instance.
	 * @throws IllegalArgumentException
	 *             , if the required {@code numberClass} is not supported.
	 * @see #getSupportedNumberClasses()
	 * @see #isNumberClassSupported(Class)
	 */
	public MonetaryAmountFactory getMonetaryAmountFactory(Class<?> numberClass);

	/**
	 * Access a {@link MonetaryAmountFactory}, based on the default number
	 * representation class.
	 * 
	 * @see #getDefaultNumberClass()
	 * 
	 * @return The {@link MonetaryAmountFactory} instance.
	 */
	public MonetaryAmountFactory getMonetaryAmountFactory();

}
