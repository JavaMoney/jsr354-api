package javax.money.provider.impl;

import javax.money.MonetaryAmount;
import javax.money.format.ItemParseException;
import javax.money.format.ItemParser;
import javax.money.format.LocalizationStyle;

public class TestAmountParser implements ItemParser<MonetaryAmount> {

	@Override
	public Class<MonetaryAmount> getTargetClass() {
		return MonetaryAmount.class;
	}

	@Override
	public LocalizationStyle getStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount parse(CharSequence text) throws ItemParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
