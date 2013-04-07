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
package net.java.javamoney.ri.loader;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.money.provider.Monetary.ComponentLoader;

public class CDIStandaloneLoader implements ComponentLoader {

	private CDIContainer cdi;

	@Override
	public void init() {
		cdi = new CDIContainer();
		cdi.start();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getComponent(Class<T> type,
			Class<? extends Annotation>... annotations) {
		List<T> instancesFound = getComponents(type, annotations);
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

	private boolean annotationsMatch(Object comp,
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
	public <T> List<T> getComponents(Class<T> type,
			Class<? extends Annotation>... annotations) {
		cdi.start();
		List<T> instancesFound = new ArrayList<T>();
		for (Iterator iterator = cdi.getInstances(type); iterator.hasNext();) {
			T comp = (T) iterator.next();
			if (annotationsMatch(comp, annotations)) {
				instancesFound.add((T) comp);
			}
		}
		return sortComponents(instancesFound);
	}

	protected <T> List<T> sortComponents(List<T> list) {
		return list;
	}

	

}
