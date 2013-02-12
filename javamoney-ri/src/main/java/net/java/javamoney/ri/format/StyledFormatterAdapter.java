package net.java.javamoney.ri.format;

import java.io.IOException;

import javax.money.format.common.LocalizationStyle;
import javax.money.format.common.StyleableFormatter;
import javax.money.format.common.StyledFormatter;

import net.java.javamoney.ri.format.common.AbstractTargeted;

public class StyledFormatterAdapter<T> extends AbstractTargeted<T> implements
		StyledFormatter<T> {

	private StyleableFormatter<T> baseFormatter;
	private LocalizationStyle style;

	public StyledFormatterAdapter(Class<T> type,
			StyleableFormatter<T> baseFormatter, LocalizationStyle style) {
		super(type);
		if (baseFormatter == null) {
			throw new IllegalArgumentException(
					"BaseFormatter must not be null.");
		}
		if (style == null) {
			throw new IllegalArgumentException(
					"LocalizationStyle must not be null.");
		}
		this.baseFormatter = baseFormatter;
		this.style = style;
		this.style.setImmutable();
	}

	@Override
	public LocalizationStyle getStyle() {
		return this.style;
	}

	@Override
	public String print(T item) {
		StringBuilder builder = new StringBuilder();
		try {
			print(builder, item);
			return builder.toString();
		} catch (IOException e) {
			throw new IllegalStateException("Error during formatting.", e);
		}

	}

	@Override
	public void print(Appendable appendable, T item) throws IOException {
		this.baseFormatter.print(appendable, item, this.style);
	}

}
