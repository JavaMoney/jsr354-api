/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.format;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.money.format.MonetaryFormat.MonetaryFormatSpi;

/**
 * Tests class registered into {@link MonetaryFormat} to test
 * {@link MonetaryFormat}.
 * 
 * @author Anatole Tresch
 * 
 */
public class TestMonetaryFormatSpi implements MonetaryFormatSpi {

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
		if (style.isDefault()) {
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
		public String format(Object item) {
			return String.valueOf(item);
		}

		@Override
		public void print(Appendable appendable, Object item)
				throws IOException {
			appendable.append(String.valueOf(item));
		}

		@Override
		public Object parse(CharSequence text) throws ItemParseException {
			try {
				return targetClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

	}

}
