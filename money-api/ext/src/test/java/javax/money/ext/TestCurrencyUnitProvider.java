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
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;
import javax.money.UnknownCurrencyException;
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
		return false;
	}

	@Override
	public Collection<String> getNamespaces(Long timestamp) {
		Set<String> result = new HashSet<String>();
		result.add(MoneyCurrency.ISO_NAMESPACE);
		return result;
	}

	@Override
	public boolean isAvailable(String namespace, String currencyCode, Long timestamp) {
		if (timestamp == null) {
			return MoneyCurrency.of(namespace, currencyCode) != null;
		}
		return false;
	}

	@Override
	public CurrencyUnit get(String namespace, String currencyCode,
			Long timestamp) {
		if (timestamp == null) {
			CurrencyUnit unit = MoneyCurrency.of(namespace, currencyCode);
			if(unit!=null){
				return unit;
			}
		}
		throw new UnknownCurrencyException(namespace, currencyCode);
	}

	@Override
	public Collection<CurrencyUnit> getAll(String namespace, Long timestamp) {
		if (timestamp != null) {
			return Collections.emptySet();
		}
		if (!isNamespaceAvailable(namespace, timestamp)) {
			Collections.emptySet();
		}
		Set<CurrencyUnit> currencyUnits = new HashSet<CurrencyUnit>();
		Set<Currency> currencies = Currency.getAvailableCurrencies();
		for (Currency currency : currencies) {
			currencyUnits.add(MoneyCurrency.of(currency));
		}
		return currencyUnits;
	}

}
