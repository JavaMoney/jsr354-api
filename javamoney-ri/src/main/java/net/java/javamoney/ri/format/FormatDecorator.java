package net.java.javamoney.ri.format;

import javax.money.format.common.LocalizationStyle;

public interface FormatDecorator<T> extends Decoratable<T>{

	public String decorate(T item, String formattedString,
			LocalizationStyle style);

}
