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

import javax.money.MonetaryAmount;

/**
 * This interface models a simple calculation that is based on a single
 * {@link MonetaryAmount}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the result type of the calculation.
 */
public interface Calculation<T> {

	/**
	 * Returns an literal non localized name, that identifies this type of
	 * calculation.
	 * 
	 * @return the identifier, not null.
	 */
	public String getId();

	/**
	 * Result type of this {@link Calculation}, rewuired to evaluate the type
	 * during runtime.
	 * 
	 * @return the result type.
	 */
	public Class<?> getResultType();

	/**
	 * Flag that defines if this calculation supports multiple input values.
	 * 
	 * @return true, if multiple input values are supported.
	 */
	public boolean isMultiValued();

	/**
	 * Returns a result calculated using the given {@link MonetaryAmount}.
	 * 
	 * @param amount
	 *            the amount to use, not null
	 * @return the calculation result, never null
	 * @throws ArithmeticException
	 *             if the adjustment fails
	 */
	public T calculate(MonetaryAmount... amounts);

}
