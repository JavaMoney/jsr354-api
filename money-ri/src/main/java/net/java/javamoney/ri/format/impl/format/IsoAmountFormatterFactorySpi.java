package net.java.javamoney.ri.format.impl.format;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;
import javax.money.format.spi.ItemFormatterFactorySpi;
import javax.money.provider.Monetary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IsoAmountFormatterFactorySpi implements
		ItemFormatterFactorySpi<MonetaryAmount> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IsoAmountFormatterFactorySpi.class);

	@Override
	public Class<MonetaryAmount> getTargetClass() {
		return MonetaryAmount.class;
	}

	@Override
	public Enumeration<String> getSupportedStyleIds() {
		Set<String> supportedRenderTypes = new HashSet<String>();
		supportedRenderTypes.add(LocalizationStyle.DEFAULT_ID);
		return Collections.enumeration(supportedRenderTypes);
	}

	@Override
	public ItemFormatter<MonetaryAmount> getItemFormatter(
			LocalizationStyle style) {
		String namespace = style.getAttribute("namespace", String.class);
		if(namespace==null){
			namespace = CurrencyUnit.ISO_NAMESPACE;
		}
		if (!CurrencyUnit.ISO_NAMESPACE.equals(namespace)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Not able to return a amount formatter for " + style);
			}
			return null;
		}
		String renderedFieldValue = (String) style.getAttribute(
				"currencyRendering", String.class);
		if (renderedFieldValue == null) {
			renderedFieldValue = "CODE";
		}
		LocalizationStyle currencyStyle = new LocalizationStyle(
				renderedFieldValue, style.getNumberLocale());
		return new IsoAmountFormatter(style, currencyStyle);
	}

}
