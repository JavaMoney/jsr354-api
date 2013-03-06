package net.java.javamoney.ri.format.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;
import javax.money.format.spi.ItemFormatterFactorySpi;

import net.java.javamoney.ri.format.impl.IsoCurrencyFormatter.RenderedField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IsoCurrencyFormatterFactorySpi implements
		ItemFormatterFactorySpi<CurrencyUnit> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IsoCurrencyFormatterFactorySpi.class);

	@Override
	public Class<CurrencyUnit> getTargetClass() {
		return CurrencyUnit.class;
	}

	@Override
	public Enumeration<String> getSupportedStyleIds() {
		Set<String> supportedRenderTypes = new HashSet<String>();
		for (IsoCurrencyFormatter.RenderedField f : IsoCurrencyFormatter.RenderedField
				.values()) {
			supportedRenderTypes.add(f.name());
		}
		return Collections.enumeration(supportedRenderTypes);
	}

	@Override
	public ItemFormatter<CurrencyUnit> getItemFormatter(LocalizationStyle style) {
		String renderedFieldValue = style.getId();
		try {
			IsoCurrencyFormatter.RenderedField.valueOf(renderedFieldValue
					.toUpperCase());
		} catch (Exception e) {
			throw new IllegalArgumentException("style's ID must one of "
					+ Arrays.toString(RenderedField.values()));
		}
		String namespace = style.getAttribute("namespace", String.class);
		if (CurrencyUnit.ISO_NAMESPACE.equals(namespace)) {
			return new IsoCurrencyFormatter(style);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Not able to return a currency formatter for " + style);
		}
		return null;
	}

}
