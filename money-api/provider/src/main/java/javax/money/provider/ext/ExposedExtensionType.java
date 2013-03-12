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
package javax.money.provider.ext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.money.provider.Monetary;

/**
 * This is a interface that defines the API type exposed by
 * {@link MonetaryExtension} classes. Extensions are loaded automatically on
 * startup. This allows to keep all money related functionality bundled within
 * one central catalog within {@link Monetary}.
 * 
 * @author Anatole Tresch
 */
@Target({ ElementType.TYPE })
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExposedExtensionType {

	/**
	 * This method allows an extension to define the type that is exposed as
	 * API. It is highly recommended that extensions provide an according usage
	 * interface instead of returning implementation classes.
	 * 
	 * @return The exposed type, never null.
	 */
	public Class<?> value();

}
