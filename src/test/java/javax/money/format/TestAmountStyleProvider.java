/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import org.junit.Test;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.money.spi.AmountFormatSymbolsProviderSpi;
import javax.money.spi.AmountStyleProviderSpi;

public class TestAmountStyleProvider implements
		AmountStyleProviderSpi {

	private Set<Locale> testSet = new HashSet<Locale>();
	public TestAmountStyleProvider() {
		testSet.add(Locale.ENGLISH);
	}

	@Override
	public Set<Locale> getSupportedLocales() {
		return testSet;
	}

	@Override
	public AmountStyle getAmountStyle(Locale locale) {
		if (Locale.ENGLISH.equals(locale)) {
			return new AmountStyle.Builder(locale).setPattern("test").build();
		}
		return null;
	}

}
