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
package javax.money.format;

/**
 * This enumeration defines the possible placement variants of numeric signs as
 * used with the {@link AmountFormatterBuilder}.
 * 
 * @author Anatole Tresch
 * 
 */
public enum SignPlacement {
	/** The sign is placed before the numeric part, e.g. 'CHF -12.35'. */
	BEFORE,
	/** The sign is placed after the numeric part, e.g. 'USD 123,45-'. */
	AFTER,
	/** The sign is placed around the numeric part, e.g. '(123.45)'. */
	AROUND,
	/** The sign is completely omited from the formatted representation. */
	OMIT
}
