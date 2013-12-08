package javax.money;

import javax.money.spi.MonetaryAmountProviderSpi;

public final class TestMonetaryAmountProvider implements MonetaryAmountProviderSpi{

	@Override
	public MonetaryAmount getAmount(CurrencyUnit currency, long number,
			MonetaryContext monetaryContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount getAmount(CurrencyUnit currency, double number,
			MonetaryContext monetaryContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount getAmount(CurrencyUnit currency, Number number,
			MonetaryContext monetaryContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount getAmountFrom(MonetaryAmount amt,
			MonetaryAmount monetaryContext) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
