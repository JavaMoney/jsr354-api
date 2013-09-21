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
package javax.money.ext;

import java.util.Collection;

import javax.money.CurrencyNamespace;
import javax.money.CurrencyUnit;
import javax.money.TestCurrency;
import javax.money.UnknownCurrencyException;
import javax.money.ext.spi.MonetaryCurrenciesSingletonSpi;

/**
 * Empty pseudo implementation for testing only.
 * 
 * @author Anatole Tresch
 * 
 */
public class TestMonetaryCurrenciesSingletonSpi implements
		MonetaryCurrenciesSingletonSpi {

	@Override
	public CurrencyNamespace getDefaultNamespace() {
		return CurrencyNamespace.ISO_NAMESPACE;
	}

	@Override
	public boolean isAvailable(String namespace, String currencyCode) {
		return TestCurrency.of(CurrencyNamespace.of(namespace), currencyCode) != null;
	}

	@Override
	public CurrencyUnit get(CurrencyNamespace namespace, String currencyCode) {
		CurrencyUnit unit = TestCurrency.of(namespace, currencyCode);
		if (unit != null) {
			return unit;
		}
		throw new UnknownCurrencyException(namespace.getId(), currencyCode);
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
	public Collection<CurrencyNamespace> getNamespaces() {
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
	public boolean isAvailable(String code) {
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
	public CurrencyUnit get(String code) {
		return get(getDefaultNamespace(), code);
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
	public Collection<CurrencyUnit> getAll(CurrencyNamespace namespace) {
		return TestCurrency.allFromNamespace(namespace);
	}

}
