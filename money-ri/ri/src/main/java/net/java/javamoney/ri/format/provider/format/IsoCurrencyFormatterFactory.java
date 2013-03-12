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
package net.java.javamoney.ri.format.provider.format;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.format.ItemFormatException;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;
import javax.money.format.spi.ItemFormatterFactorySpi;

import net.java.javamoney.ri.format.provider.format.IsoCurrencyFormatter.RenderedField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IsoCurrencyFormatterFactory implements
		ItemFormatterFactorySpi<CurrencyUnit> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IsoCurrencyFormatterFactory.class);

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
	public ItemFormatter<CurrencyUnit> getItemFormatter(LocalizationStyle style) throws ItemFormatException{
		String renderedFieldValue = style.getId();
		try {
			IsoCurrencyFormatter.RenderedField.valueOf(renderedFieldValue
					.toUpperCase());
		} catch (Exception e) {
			throw new IllegalArgumentException("style's ID must one of "
					+ Arrays.toString(RenderedField.values()));
		}
		String namespace = style.getAttribute("namespace", String.class);
		if(namespace==null){
			namespace = CurrencyUnit.ISO_NAMESPACE;
		}
		if (CurrencyUnit.ISO_NAMESPACE.equals(namespace)) {
			return new IsoCurrencyFormatter(style);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Not able to return a currency formatter for " + style);
		}
		return null;
	}

}
