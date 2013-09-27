/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch).
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * 
 * Contributors: Anatole Tresch - initial version.
 */
package javax.money.tck;

import java.lang.reflect.AccessibleObject;
import java.util.Collection;

public interface JSR354TestConfiguration {

	Collection<Class> getExceptionClasses();

	Collection<Class> getAmountClasses();

	Collection<Class> getCurrencyClasses();

	Collection<Class> getAdjusters();

	Collection<Class> getQueries();

	AccessibleObject getConstructionMethod(Class type, Class... paramTypes);

	<T> T create(Class<T> type, Object... params);

}
