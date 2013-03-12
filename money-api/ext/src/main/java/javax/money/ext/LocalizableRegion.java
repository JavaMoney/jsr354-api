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
package javax.money.ext;

import java.util.Locale;

/**
 * This interface adds localizable support for regions.
 * 
 * @author Anatole Tresch
 */
public interface LocalizableRegion extends Region {

	/**
	 * Access the display name for this {@link Region}, the locale used is
	 * determined by {@link Locale#getDefault()}. If the display name is not
	 * defined, the according {@link Region} id is returned.
	 * 
	 * @return the display name, never null.
	 */
	public String getDisplayName();

	/**
	 * Access the display name for this {@link Region}. If the display name is
	 * not defined, the according {@link Region} id is returned.
	 * 
	 * @param locale
	 *            The target {@link Locale}.
	 * @return the display name, never null.
	 */
	public String getDisplayName(Locale locale);

}
