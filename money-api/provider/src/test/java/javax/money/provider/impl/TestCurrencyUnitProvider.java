/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
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
