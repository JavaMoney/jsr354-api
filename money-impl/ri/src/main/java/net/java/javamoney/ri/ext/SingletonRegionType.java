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
package net.java.javamoney.ri.ext;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.money.ext.RegionType;
//
///**
// * Defines the different region types available. This allows to segregate
// * different grouping strategy types.
// * <p>
// * The class is implemented as reusable and thread safe value type.
// * 
// * @author Anatole Tresch
// */
//public final class SingletonRegionType implements RegionType, Serializable,
//		Comparable<RegionType> {
//
//	/**
//	 * serialVersionUID.
//	 */
//	private static final long serialVersionUID = 1508384106763048881L;
//
//	/** The id of this type. */
//	private final String id;
//	/** The cache of types. */
//	private static final Map<String, RegionType> CACHED_INSTANCES = new ConcurrentHashMap<String, RegionType>();
//
//	/**
//	 * Access a region type with the given identifier. If such a type is already
//	 * cached, the cached instance is returned. If no such instance is in the
//	 * cache, a new instance is created and registered in the cache accordingly.
//	 * 
//	 * @param id
//	 *            The region type identifier.
//	 * @return The {@link RegionType} instance cached or created, never
//	 *         {@code null}.
//	 */
//	public static SingletonRegionType of(String id) {
//		if (id == null) {
//			throw new IllegalArgumentException("id required.");
//		}
//		RegionType instance = CACHED_INSTANCES.get(id);
//		if (instance == null) {
//			instance = new SingletonRegionType(id);
//			CACHED_INSTANCES.put(id, instance);
//		}
//		return instance;
//	}
//
//	/**
//	 * Get a list of all {@link RegionType} instances currently cached.
//	 * Basically it is recommended to use {@link #of(String)} to access/create
//	 * instance of {@link RegionType}. In this case the according type will be
//	 * included in the enumeration returned by this method.
//	 * 
//	 * @return the {@link RegionType} instances created using the
//	 *         {@link #of(String)} method.
//	 */
//	public static Enumeration<RegionType> getTypes() {
//		return Collections.enumeration(CACHED_INSTANCES.values());
//	}
//
//	/**
//	 * Constructs a new instance of an {@link SingletonRegionType}.
//	 * 
//	 * @param id
//	 *            The id of this type instance, never null.
//	 */
//	public SingletonRegionType(String id) {
//		if (id == null) {
//			throw new IllegalArgumentException("Id must not be null.");
//		}
//		this.id = id;
//	}
//
//	/**
//	 * Get the identifier of this instance.
//	 * 
//	 * @return The identifier, never null.
//	 */
//	public String getId() {
//		return this.id;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		return result;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		SingletonRegionType other = (SingletonRegionType) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		return true;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return "RegionType [id=" + id + ", class=" + getClass().getName() + "]";
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Comparable#compareTo(java.lang.Object)
//	 */
//	@Override
//	public int compareTo(RegionType o) {
//		if (o == null) {
//			return -1;
//		}
//		int compare = id.compareTo(o.getId());
//		return compare;
//	}
//
//}
