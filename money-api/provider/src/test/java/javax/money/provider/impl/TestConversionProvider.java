/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.provider.impl;

import java.util.Enumeration;

import javax.money.convert.ConversionProvider;
import javax.money.convert.CurrencyConverter;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.ExchangeRateType;

/**
 * Empty pseudo implementation for testing only.
 * 
 * @author Anatole Tresch
 */
public class TestConversionProvider implements ConversionProvider {

	@Override
	public CurrencyConverter getCurrencyConverter(ExchangeRateType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExchangeRateProvider getExchangeRateProvider(ExchangeRateType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<ExchangeRateType> getSupportedExchangeRateTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSupportedExchangeRateType(ExchangeRateType type) {
		// TODO Auto-generated method stub
		return false;
	}

}
