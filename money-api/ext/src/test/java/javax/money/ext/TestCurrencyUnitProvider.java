/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import java.util.Collection;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;
import javax.money.ext.MonetaryCurrencies.CurrencyUnitProviderSpi;

/**
 * Empty pseudo implementation for testing only.
 * 
 * @author Anatole Tresch
 * 
 */
public class TestCurrencyUnitProvider implements CurrencyUnitProviderSpi {

	@Override
	public String getDefaultNamespace() {
		return MoneyCurrency.ISO_NAMESPACE;
	}

	@Override
	public boolean isNamespaceAvailable(String namespace, Long timestamp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<String> getNamespaces(Long timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrencyUnit get(String namespace, String code, Long timestamp) {
		if (MoneyCurrency.ISO_NAMESPACE.equals(namespace) && timestamp == null) {
			return MoneyCurrency.of(code);
		}
		return null;
	}

	@Override
	public boolean isAvailable(String namespace, String code, Long timestamp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<CurrencyUnit> getAll(String namespace, Long timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<CurrencyUnit> getAll(Locale locale, Long timestamp) {
		// TODO Auto-generated method stub
		return null;
	}

}
