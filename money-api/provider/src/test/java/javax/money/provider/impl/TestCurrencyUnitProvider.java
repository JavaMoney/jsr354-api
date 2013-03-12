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
package javax.money.provider.impl;

import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.provider.CurrencyUnitProvider;

/**
 * Empty pseudo implementation for testing only.
 * @author Anatole Tresch
 *
 */
public class TestCurrencyUnitProvider implements CurrencyUnitProvider {
// TODO try mocking, could use mock framework for test code
	@Override
	public CurrencyUnit get(String namespace, String code) {
		// empty implementation
		return null;
	}

	@Override
	public CurrencyUnit get(String namespace, String code, Long timestamp) {
		// empty implementation
		return null;
	}

	@Override
	public CurrencyUnit[] getAll() {
		// empty implementation
		return null;
	}

	@Override
	public CurrencyUnit[] getAll(Long timstamp) {
		// empty implementation
		return null;
	}

	@Override
	public CurrencyUnit[] getAll(String namespace, Long timestamp) {
		// empty implementation
		return null;
	}

	@Override
	public CurrencyUnit[] getAll(String namespace) {
		// empty implementation
		return null;
	}

	@Override
	public boolean isAvailable(String namespace, String code) {
		// empty implementation
		return false;
	}

	@Override
	public boolean isAvailable(String namespace, String code, Long timestamp) {
		// empty implementation
		return false;
	}

	@Override
	public boolean isAvailable(String namespace, String code, Long start,
			Long end) {
		// empty implementation
		return false;
	}

	@Override
	public boolean isNamespaceAvailable(String namespace) {
		// empty impplementation
		return false;
	}

	@Override
	public String[] getNamespaces() {
		return new String[]{"Test only"};
	}

	@Override
	public CurrencyUnit[] getAll(Locale locale) {
		// empty implementation
		return null;
	}

	@Override
	public CurrencyUnit[] getAll(Locale locale, Long timestamp) {
		// empty implementation
		return null;
	}

	@Override
	public CurrencyUnit map(CurrencyUnit unit, String targetNamespace) {
		// empty implementation
		return null;
	}

	@Override
	public CurrencyUnit[] mapAll(CurrencyUnit[] units, String targetNamespace) {
		// empty implementation
		return null;
	}

	@Override
	public String getDefaultNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrencyUnit get(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrencyUnit get(String code, Long timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAvailable(String code) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAvailable(String code, Long timestamp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAvailable(String code, Long start, Long end) {
		// TODO Auto-generated method stub
		return false;
	}

}
