package net.java.javamoney.ri.format.impl;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.format.ItemParser;
import javax.money.format.LocalizationStyle;
import javax.money.format.spi.ItemParserFactorySpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IsoCurrencyParserFactorySpi implements
		ItemParserFactorySpi<CurrencyUnit> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IsoCurrencyParserFactorySpi.class);

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
	public ItemParser<CurrencyUnit> getItemParser(LocalizationStyle style) {
		String namespace = style.getAttribute("namespace", String.class);
		if (namespace == null) {
			namespace = CurrencyUnit.ISO_NAMESPACE;
		}
		if (CurrencyUnit.ISO_NAMESPACE.equals(namespace)
				&& style.isDefaultStyle()) {
			return new IsoCurrencyParser(style);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Not able to return a currency parser for " + style);
		}
		return null;
	}

}
