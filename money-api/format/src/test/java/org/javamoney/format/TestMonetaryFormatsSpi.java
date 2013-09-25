/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package org.javamoney.format;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.javamoney.format.ItemFormat;
import org.javamoney.format.ItemFormatException;
import org.javamoney.format.ItemParseException;
import org.javamoney.format.LocalizationStyle;
import org.javamoney.format.MonetaryFormats;
import org.javamoney.format.spi.MonetaryFormatsSingletonSpi;

/**
 * Tests class registered into {@link MonetaryFormats} to test
 * {@link MonetaryFormats}.
 * 
 * @author Anatole Tresch
 * 
 */
public class TestMonetaryFormatsSpi implements MonetaryFormatsSingletonSpi {

	@Override
	public Collection<String> getSupportedStyleIds(Class<?> targetType) {
		Set<String> res = new HashSet<String>();
		res.add(targetType.getSimpleName());
		return res;
	}

	@Override
	public boolean isSupportedStyle(Class<?> targetType, String styleId) {
		if (styleId.equals(targetType.getSimpleName())) {
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> ItemFormat<T> getItemFormat(Class<T> targetType,
			LocalizationStyle style) throws ItemFormatException {
		if (style.isDefaultStyle()) {
			return new DummyItemFormatter(style, targetType);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static final class DummyItemFormatter implements ItemFormat {

		private LocalizationStyle style;

		private Class targetClass;

		public DummyItemFormatter(LocalizationStyle style, Class type) {
			this.style = style;
			this.targetClass = type;
		}

		@Override
		public Class getTargetClass() {
			return targetClass;
		}

		@Override
		public LocalizationStyle getStyle() {
			return style;
		}

		@Override
		public String format(Object item, Locale locale) {
			return String.valueOf(item);
		}

		@Override
		public void print(Appendable appendable, Object item, Locale locale)
				throws IOException {
			appendable.append(String.valueOf(item));
		}

		@Override
		public Object parse(CharSequence text, Locale locale) throws ItemParseException {
			try {
				return targetClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	@Override
	public LocalizationStyle getLocalizationStyle(Class<?> targetType, String styleId) {
	    if(targetType.getSimpleName().equals(styleId)){
		LocalizationStyle style = LocalizationStyle.of(targetType, styleId);
		if(style==null){
		    style = new LocalizationStyle.Builder(targetType, styleId).build(true);
		}
		return style;
	    }
	    return null;
	}

}
