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
import java.util.Iterator;
import java.util.Set;

import javax.inject.Named;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public final class CDIContainer {

	private static boolean hookRegistered = false;
	private Weld weld;
	private WeldContainer weldContainer;

	
	public <T> T getInstance(Class<T> instanceType, Annotation... qualifiers) {
		return weldContainer.instance().select(instanceType, qualifiers).get();
	}

	public <T> Iterator<T> getInstances(Class<T> instanceType,
			Annotation... qualifiers) {
		return weldContainer.instance().select(instanceType, qualifiers)
				.iterator();
	}

	public Set<?> getInstances(String name) {
		return weldContainer.getBeanManager().getBeans(name);
	}

	public void fireEvent(Object evt, Annotation... qualifiers) {
		weldContainer.getBeanManager().fireEvent(evt, qualifiers);
	}

	public synchronized void start() {
		if (weld == null) {
			System.out.println("*** Starting CDIContainer ...");
			weld = new Weld();
			weldContainer = weld.initialize();
			weldContainer.instance().select(WeldContainer.class).get();
			if (!hookRegistered) {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						CDIContainer.this.stop();
					}
				});
				hookRegistered = true;
			}
			System.out.println("*** CDIContainer started.");
		}
	}

	public synchronized void stop() {
		if (weld != null) {
			System.out.println("*** Stopping CDIContainer ...");
			weld.shutdown();
			weld = null;
			System.out.println("*** CDIContainer stopped.");
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getNamedInstance(Class<T> type, String id) {
		Set<?> found = weldContainer.getBeanManager().getBeans(id);
		if (found.isEmpty()) {
			return null;
		}
		for (Object object : found) {
			if (type.isAssignableFrom(object.getClass())) {
				return (T) object;
			}
		}
		return null;
	}

	public String getName(Object o) {
		if (o == null) {
			return "<null>";
		}
		Named named = o.getClass().getAnnotation(Named.class);
		if (named != null) {
			return named.value();
		}
		return o.getClass().getSimpleName();
	}
}