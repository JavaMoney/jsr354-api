/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package org.javamoney.ext;

import java.util.Collection;

import javax.money.CurrencyUnit;

import org.javamoney.ext.spi.MonetaryCurrenciesSingletonSpi;

/**
 * Empty pseudo implementation for testing only.
 * 
 * @author Anatole Tresch
 * 
 */
public class TestMonetaryCurrenciesSingletonSpi implements
		MonetaryCurrenciesSingletonSpi {


	@Override
	public boolean isAvailable(String currencyCode) {
		return TestCurrency.of(currencyCode) != null;
	}

	@Override
	public CurrencyUnit get(String currencyCode) {
		CurrencyUnit unit = TestCurrency.of(currencyCode);
		if (unit != null) {
			return unit;
		}
		throw new IllegalArgumentException(currencyCode);
	}

	@Override
	public boolean isNamespaceAvailable(String namespace) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public Collection<String> getNamespaces() {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}


	@Override
	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public CurrencyUnit map(CurrencyUnit currencyUnit, String targetNamespace,
			long timestamp) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Collection<CurrencyUnit> getAll(String namespace) {
		return TestCurrency.getAllMatching(namespace);
	}

	@Override
	public String getNamespace(String code) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
