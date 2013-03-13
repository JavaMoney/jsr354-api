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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.money.provider.Monetary.ComponentLoader;

public class MonetaryServiceLoader implements ComponentLoader {

	private ServiceLoader<MonetaryExtension> monetaryComponents = ServiceLoader
			.load(MonetaryExtension.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getInstance(Class<T> type,
			Class<? extends Annotation>... annotations) {
		List<T> instancesFound = getInstances(type, annotations);
		if (instancesFound.isEmpty()) {
			return null;
		} else if (instancesFound.size() == 1) {
			return instancesFound.get(0);
		} else {
			return resolveAmbigousComponents(instancesFound);
		}
	}

	protected <T> T resolveAmbigousComponents(List<T> instancesFound) {
		// or throw exception!
		return instancesFound.get(0);

	}

	private boolean annotationsMatch(MonetaryExtension comp,
			Class<? extends Annotation>[] annotations) {
		if (annotations == null) {
			return true;
		}
		for (Class<? extends Annotation> annotType : annotations) {
			if (comp.getClass().getAnnotation(annotType) == null) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getInstances(Class<T> type,
			Class<? extends Annotation>... annotations) {
		List<T> instancesFound = new ArrayList<T>();
		for (MonetaryExtension comp : monetaryComponents) {
			if (type.isAssignableFrom(comp.getClass())) {
				if (annotationsMatch(comp, annotations)) {
					instancesFound.add((T) comp);
				}
			}
		}
		return sortComponents(instancesFound);
	}

	protected <T> List<T> sortComponents(List<T> list) {
		return list;
	}

}
