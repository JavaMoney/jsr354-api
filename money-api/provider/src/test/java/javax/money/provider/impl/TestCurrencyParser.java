package javax.money.provider.impl;

import javax.money.CurrencyUnit;
import javax.money.format.ItemParseException;
import javax.money.format.ItemParser;
import javax.money.format.LocalizationStyle;

public class TestCurrencyParser implements ItemParser<CurrencyUnit> {

	@Override
	public Class<CurrencyUnit> getTargetClass() {
		return CurrencyUnit.class;
	}

	@Override
	public LocalizationStyle getStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CurrencyUnit parse(CharSequence text) throws ItemParseException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
