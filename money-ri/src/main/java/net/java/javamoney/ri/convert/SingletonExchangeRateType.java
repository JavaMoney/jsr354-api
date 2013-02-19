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
package net.java.javamoney.ri.convert;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.convert.ExchangeRateType;

/**
 * This class models the type of a given exchange rate as immutable value type.
 * Basically the types possible are determined by the concrete use cases and
 * implementations. Typical use cases is that exchange rates for different
 * credit card systems or debit/credit may differ. This class allows to
 * distinguish these rates.
 * 
 * @author Anatole Tresch
 */
public final class SingletonExchangeRateType implements ExchangeRateType, Serializable,
		Comparable<SingletonExchangeRateType> {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7505497771490888058L;
	/** The id of this type. */
	private final String id;
	/** The cache of types. */
	private static final Map<String, ExchangeRateType> CACHED_INSTANCES = new ConcurrentHashMap<String, ExchangeRateType>();

	public static ExchangeRateType of(String id) {
		if (id == null) {
			throw new IllegalArgumentException("id required.");
		}
		ExchangeRateType instance = CACHED_INSTANCES.get(id);
		if (instance == null) {
			instance = new SingletonExchangeRateType(id);
			CACHED_INSTANCES.put(id, instance);
		}
		return instance;
	}

	public static Enumeration<ExchangeRateType> getTypes() {
		return Collections.enumeration(CACHED_INSTANCES.values());
	}

	/**
	 * Constructs a new instance of an ExchangeRateType..
	 * 
	 * @param id
	 *            The id of this type instance, never null.
	 */
	public SingletonExchangeRateType(String id) {
		if (id == null) {
			throw new IllegalArgumentException("Id must not be null.");
		}
		this.id = id;
	}

	/**
	 * Get the identifier of this instance.
	 * 
	 * @return The identifier, never null.
	 */
	public String getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SingletonExchangeRateType other = (SingletonExchangeRateType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExchangeRateType [id=" + id + ", class=" + getClass().getName() + "]";
	}

	@Override
	public int compareTo(SingletonExchangeRateType o) {
		if (o == null) {
			return -1;
		}
		int compare = id.compareTo(o.id);
		return compare;
	}

}
