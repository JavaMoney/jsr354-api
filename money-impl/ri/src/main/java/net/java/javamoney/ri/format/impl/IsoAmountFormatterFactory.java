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
package net.java.javamoney.ri.format.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.money.MonetaryAmount;
import javax.money.MoneyCurrency;
import javax.money.format.ItemFormat;
import javax.money.format.ItemFormatException;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.spi.ItemFormatFactorySpi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IsoAmountFormatterFactory implements
		ItemFormatFactorySpi<MonetaryAmount> {

	private static final Logger LOG = LoggerFactory
			.getLogger(IsoAmountFormatterFactory.class);

	@Override
	public Class<MonetaryAmount> getTargetClass() {
		return MonetaryAmount.class;
	}

	@Override
	public Collection<String> getSupportedStyleIds() {
		Set<String> supportedRenderTypes = new HashSet<String>();
		supportedRenderTypes.add(LocalizationStyle.DEFAULT_ID);
		return supportedRenderTypes;
	}

	@Override
	public boolean isSupportedStyle(String styleId) {
		return getSupportedStyleIds().contains(styleId);
	}

	@Override
	public ItemFormat<MonetaryAmount> getItemFormat(LocalizationStyle style)
			throws ItemFormatException {
		String namespace = style.getAttribute("namespace", String.class);
		if (namespace == null) {
			LOG.debug("Using default namespace " + MoneyCurrency.ISO_NAMESPACE
					+ " for style: " + style);
			namespace = MoneyCurrency.ISO_NAMESPACE;
		}
		if (!MoneyCurrency.ISO_NAMESPACE.equals(namespace)) {
			throw new ItemFormatException("Only " + MoneyCurrency.ISO_NAMESPACE
					+ " is supported as namespace, was:" + style);
		}
		String renderedFieldValue = (String) style.getAttribute(
				"currencyRendering", String.class);
		if (renderedFieldValue == null) {
			renderedFieldValue = "CODE";
		}
		LocalizationStyle currencyStyle = new LocalizationStyle.Builder(
				renderedFieldValue, style.getNumberLocale()).build();
		return new IsoAmountFormatter(style, currencyStyle);
	}

}
