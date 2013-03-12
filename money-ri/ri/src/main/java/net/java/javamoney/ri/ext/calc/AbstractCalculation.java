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
package net.java.javamoney.ri.ext.calc;

import javax.money.MonetaryAmount;
import javax.money.ext.Calculation;

/**
 * Base class for calculations.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the result type.
 */
public abstract class AbstractCalculation<T> implements Calculation<T> {
	/** The calculation's id. */
	private String id;
	/** Th result type. */
	private Class<T> type;
	/** The multi value flag. */
	private boolean multiValued;

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            The calculation's id, not empty and not null.
	 * @param type
	 *            The calculation's return type.
	 * @param multiValued
	 *            the multiValued flag, denoting if the calculations supports
	 *            multiple values.
	 */
	public AbstractCalculation(String id, Class<T> type, boolean multiValued) {
		if (id == null || id.trim().isEmpty()) {
			throw new IllegalArgumentException("id may not be null or empty.");
		}
		if (type == null) {
			throw new IllegalArgumentException("type may not be null.");
		}
		this.id = id;
		this.type = type;
		this.multiValued = multiValued;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.Calculation#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.Calculation#getResultType()
	 */
	@Override
	public Class<?> getResultType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.ext.Calculation#isMultiValued()
	 */
	@Override
	public boolean isMultiValued() {
		return multiValued;
	}

	@Override
	public final T calculate(MonetaryAmount... amounts) {
		checkParams(amounts);
		return calculateImpl(amounts);
	}

	/**
	 * Check the input parameters.
	 * 
	 * @param amounts
	 *            The input passed from the clients.
	 */
	protected void checkParams(MonetaryAmount... amounts) {
		if (amounts == null || amounts.length == 0) {
			throw new IllegalArgumentException("Input is empty.");
		}
		if (!multiValued && amounts.length > 1) {
			throw new IllegalArgumentException(
					"Calculations does take only one input value.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", type=" + type
				+ ", multiValued=" + multiValued + "]";
	}

	protected abstract T calculateImpl(MonetaryAmount... amounts);

}
