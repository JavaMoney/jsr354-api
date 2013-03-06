package javax.money.provider.impl;

import java.io.IOException;

import javax.money.MonetaryAmount;
import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;

public class TestAmountFormatter implements ItemFormatter<MonetaryAmount> {

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
	public String format(MonetaryAmount item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void print(Appendable appendable, MonetaryAmount item)
			throws IOException {
		// TODO Auto-generated method stub

	}

}
