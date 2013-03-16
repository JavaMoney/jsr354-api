/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */

package javax.money.provider.impl;

import java.util.Enumeration;

import javax.money.provider.MonetaryAmountFactory;
import javax.money.provider.MonetaryAmountProvider;

/**
 * Empty pseudo implementation for testing only.
 * @author Anatole Tresch
 *
 */
public class TestMonetaryAmountProvider implements MonetaryAmountProvider {

	@Override
	public Enumeration<Class<?>> getSupportedNumberClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNumberClassSupported(Class<?> numberClass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Class<?> getDefaultNumberClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmountFactory getMonetaryAmountFactory(Class<?> numberClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmountFactory getMonetaryAmountFactory() {
		// TODO Auto-generated method stub
		return null;
	}

}
