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
 *    Wernner Keil - extensions and adaptions.
 */
package net.java.javamoney.ri.spi;

import java.lang.annotation.Annotation;
import java.util.List;


/**
 * This is the loader that is used to load the different providers and spi
 * to be used by {@link Monetary} and its services. The
 * {@link MonetaryLoader} can also be accessed from the {@link Monetary}
 * singleton, so it can also be used by the monetary service
 * implementations.
 * 
 * @author Anatole Tresch
 */
public interface ComponentLoader {

		/**
		 * Access a singleton instance.
		 * 
		 * @param type
		 *            The target type.
		 * @param annotations
		 *            The annotations that must be present on the type.
		 * @return the instance found, or null.
		 * @throws IllegalStateException
		 *             , when the instances are ambiguous.
		 */
		@SuppressWarnings("unchecked")
		public <T> T getComponent(Class<T> type,
				Class<? extends Annotation>... annotations);

		/**
		 * Access a list of instances.
		 * 
		 * @param type
		 *            The target type.
		 * @param annotations
		 *            The annotations that must be present on the types.
		 * @return the instances matching, never null.
		 */
		@SuppressWarnings("unchecked")
		public <T> List<T> getComponents(Class<T> type,
				Class<? extends Annotation>... annotations);

}
