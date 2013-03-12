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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.money.provider.Monetary.ComponentLoader;
import javax.money.provider.ext.MonetaryExtension;

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
