/*
 *  Copyright (c) 2013, Werner Keil.
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
 *    Werner Keil - initial implementation.
 */
package net.java.javamoney.ri;

import java.util.Currency;

import javax.money.CurrencyUnit;

import net.java.javamoney.ri.core.JDKCurrencyAdapter;

/**
 * @author Werner Keil
 *
 */
public abstract class RITestBase {
	protected static final CurrencyUnit EURO = JDKCurrencyAdapter.getInstance("EUR");
	protected static final CurrencyUnit DOLLAR = JDKCurrencyAdapter.getInstance("USD");
}
