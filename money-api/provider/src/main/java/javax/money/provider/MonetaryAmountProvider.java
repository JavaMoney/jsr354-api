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
