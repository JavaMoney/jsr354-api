/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 */
package javax.money.provider.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.format.ItemFormatter;
import javax.money.format.ItemFormatterFactory;
import javax.money.format.LocalizationStyle;

/**
 * Empty pseudo implementation for testing only.
 * 
 * @author Anatole Tresch
 * 
 */
public class TestItemFormatterFactory implements ItemFormatterFactory {

	@Override
	public Enumeration<String> getSupportedStyleIds(Class<?> type) {
		return Collections.enumeration(Arrays
				.asList(new String[] { LocalizationStyle.DEFAULT_ID }));
	}

	@Override
	public boolean isSupportedStyle(Class<?> type, String styleId) {
		return LocalizationStyle.DEFAULT_ID.equals(styleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ItemFormatter<T> getItemFormatter(Class<T> type,
			LocalizationStyle style) {
		return null;
	}

	@Override
	public <T> ItemFormatter<T> getItemFormatter(Class<T> targetType,
			Locale locale) {
		return getItemFormatter(targetType, LocalizationStyle.of(locale));
	}

}
