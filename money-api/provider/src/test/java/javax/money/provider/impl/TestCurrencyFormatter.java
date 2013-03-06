package javax.money.provider.impl;

import java.io.IOException;

import javax.money.CurrencyUnit;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;

public class TestCurrencyFormatter implements ItemFormatter<CurrencyUnit> {

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
	public String format(CurrencyUnit item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void print(Appendable appendable, CurrencyUnit item)
			throws IOException {
		// TODO Auto-generated method stub

	}

}
