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
import javax.money.format.ItemParser;
import javax.money.format.ItemParserFactory;
import javax.money.format.LocalizationStyle;

/**
 * Empty pseudo implementation for testing only.
 * 
 * @author Anatole Tresch
 * 
 */
public class TestItemParserFactory implements ItemParserFactory {

	@Override
	public Enumeration<String> getSupportedStyleIds(Class<?> type) {
		return Collections.enumeration(Arrays
				.asList(new String[] { LocalizationStyle.DEFAULT_ID }));
	}

	@Override
	public boolean isSupportedStyle(Class<?> targetType, String styleId) {
		return LocalizationStyle.DEFAULT_ID.equals(styleId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ItemParser<T> getItemParser(Class<T> targetType,
			LocalizationStyle style) {
		return null;
	}

	@Override
	public <T> ItemParser<T> getItemParser(Class<T> targetType, Locale locale) {
		return getItemParser(targetType, LocalizationStyle.of(locale));
	}

}
