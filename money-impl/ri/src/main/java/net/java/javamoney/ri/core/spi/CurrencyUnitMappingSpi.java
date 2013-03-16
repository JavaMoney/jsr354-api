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
package net.java.javamoney.ri.core.spi;

import java.util.ServiceLoader;

import javax.money.CurrencyUnit;

/**
 * Implementation of this interface define the mappings available for
 * currencies.
 * <p>
 * Registration of the implementations is done using the {@link ServiceLoader}.
 * 
 * @author Anatole Tresch
 */
public interface CurrencyUnitMappingSpi {

	/**
	 * This method maps the given {@link CurrencyUnit} to another
	 * {@link CurrencyUnit} with the given target namespace.
	 * 
	 * @param unit
	 *            The source unit, never {@code null}. Hereby the unit will
	 *            match the namespace as defined by {@link #getNamespace()}.
	 * @param targetNamespace
	 *            the target namespace, never {@code null}.
	 * @return The mapped {@link CurrencyUnit}, or null.
	 */
	public CurrencyUnit map(CurrencyUnit unit, String targetNamespace);

}
