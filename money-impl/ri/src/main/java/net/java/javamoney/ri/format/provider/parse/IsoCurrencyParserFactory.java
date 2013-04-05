/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 */
package net.java.javamoney.ri.format.provider.parse;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MoneyCurrency;
import javax.money.format.ItemParseException;
import javax.money.format.ItemParser;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.provider.format.IsoCurrencyFormatter;
import net.java.javamoney.ri.format.spi.ItemParserFactorySpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IsoCurrencyParserFactory implements
		ItemParserFactorySpi<CurrencyUnit> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IsoCurrencyParserFactory.class);

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
	public ItemParser<CurrencyUnit> getItemParser(LocalizationStyle style)
			throws ItemParseException {
		String namespace = style.getAttribute("namespace", String.class);
		if (namespace == null) {
			namespace = MoneyCurrency.ISO_NAMESPACE;
		}
		if (MoneyCurrency.ISO_NAMESPACE.equals(namespace)
				&& style.isDefaultStyle()) {
			return new IsoCurrencyParser(style);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Not able to return a currency parser for " + style);
		}
		return null;
	}

}
