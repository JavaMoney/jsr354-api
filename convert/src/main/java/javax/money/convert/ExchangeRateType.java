/*
 *  Copyright 2012 Credit Suisse (Anatole Tresch)
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
 */
package javax.money.convert;

/**
 * Enumeration to identify if a given exchange rate is a direct rate, or a
 * derived rate, using several exchanges steps.
 * 
 * @author Anatole Tresch
 */
public enum ExchangeRateType {
	/** This value represent a direct exchange rate. */
	DIRECT,
	/** This value represents a indorect/derived exchange rate. */
	DERIVED
}
