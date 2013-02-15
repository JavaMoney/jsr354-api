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
package javax.money.convert;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class models the type of a given exchange rate. Basically the types
 * possible are determined by the concrete use cases and implementations.
 * Typical use cases is that exchange rates for different credit card systems or
 * debit/credit may differ. This class allows to distinguish these rates.
 * 
 * @author Anatole Tresch
 */
public final class ConversionType<S, T> implements Serializable,
		Comparable<ConversionType<?, ?>> {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7505497771490888058L;
	/** The id of this type. */
	private final String id;
	/** The source type. */
	private Class<S> sourceType;
	/** The target type. */
	private Class<T> targetType;
	/** The cache of types. */
	private static final Map<String, ConversionType<?, ?>> CACHED_INSTANCES = new ConcurrentHashMap<String, ConversionType<?, ?>>();

	public static <A> ConversionType<A, A> of(Class<A> type, String id) {
		return of(type, type, id);
	}

	public static <A, B> ConversionType<A, B> of(Class<A> sourceType,
			Class<B> targetType, String id) {
		if (sourceType == null) {
			throw new IllegalArgumentException("sourceType required.");
		}
		if (targetType == null) {
			throw new IllegalArgumentException("targetType required.");
		}
		if (id == null) {
			throw new IllegalArgumentException("id required.");
		}
		String key = sourceType.getName() + '_' + targetType.getName() + ':'
				+ id;
		@SuppressWarnings("unchecked")
		ConversionType<A, B> instance = (ConversionType<A, B>) CACHED_INSTANCES
				.get(key);
		if (instance == null) {
			instance = new ConversionType<A, B>(sourceType, targetType, id);
			CACHED_INSTANCES.put(key, instance);
		}
		return instance;
	}

	public static Enumeration<ConversionType<?, ?>> getTypes() {
		return Collections.enumeration(CACHED_INSTANCES.values());
	}

	/**
	 * Constructs a new instance of an ConversionType..
	 * 
	 * @param id
	 *            The id of this type instance, never null.
	 */
	public ConversionType(Class<S> sourceType, Class<T> targetType, String id) {
		if (id == null) {
			throw new IllegalArgumentException("Id must not be null.");
		}
		this.id = id;
		if (sourceType == null) {
			throw new IllegalArgumentException("sourceType must not be null.");
		}
		this.sourceType = sourceType;
		if (targetType == null) {
			throw new IllegalArgumentException("targetType must not be null.");
		}
		this.targetType = targetType;
	}

	/**
	 * Get the identifier of this instance.
	 * 
	 * @return The identifier, never null.
	 */
	public String getId() {
		return this.id;
	}

	public Class<S> getSourceType() {
		return sourceType;
	}

	public Class<T> getTargetType() {
		return targetType;
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
		result = prime * result
				+ ((sourceType == null) ? 0 : sourceType.hashCode());
		result = prime * result
				+ ((targetType == null) ? 0 : targetType.hashCode());
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
		ConversionType<?,?> other = (ConversionType<?,?>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sourceType == null) {
			if (other.sourceType != null)
				return false;
		} else if (!sourceType.equals(other.sourceType))
			return false;
		if (targetType == null) {
			if (other.targetType != null)
				return false;
		} else if (!targetType.equals(other.targetType))
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
		return "ConversionType [sourceType=" + sourceType + ", targetType="
				+ targetType + ", id=" + id + "]";
	}

	@Override
	public int compareTo(ConversionType<?, ?> o) {
		if (o == null) {
			return -1;
		}
		int compare = sourceType.getName().compareTo(o.sourceType.getName());
		if (compare == 0) {
			compare = targetType.getName().compareTo(o.targetType.getName());
		}
		if (compare == 0) {
			compare = id.compareTo(o.id);
		}
		return compare;
	}

}
