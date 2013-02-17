/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
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
 *    Anatole Tresch - initial implementation
 */
package net.java.javamoney.ri.format.common;

import javax.money.format.common.Targeted;

/**
 * Base class when implementing {@link Targeted}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The target type.
 */
public abstract class AbstractTargeted<T> implements Targeted<T> {

	private final Class<T> type;

	public AbstractTargeted(Class<T> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type may not be null.");
		}
		this.type = type;
	}

	@Override
	public final Class<T> getTargetClass() {
		return this.type;
	}

}
