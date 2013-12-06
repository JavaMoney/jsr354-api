package javax.money;

import java.util.Locale;

import javax.money.spi.CurrencyProviderSpi;

public final class TestCurrencyProvider implements CurrencyProviderSpi{

	@Override
	public CurrencyUnit getCurrencyUnit(String currencyCode) {
		if("test1".equals(currencyCode)){
			return new TestCurrency("test1",1,2);
		}
		return null;
	}

	@Override
	public CurrencyUnit getCurrencyUnit(String currencyCode, long timestamp) {
		if("test1".equals(currencyCode)){
			return new TestCurrency("test1",1,100);
		}
		return null;
	}

	@Override
	public CurrencyUnit getCurrencyUnit(Locale locale) {
		if("TEST1L".equals(locale.getCountry())){
			return new TestCurrency("TEST1L",1,2);
		}
		return null;
	}

	@Override
	public CurrencyUnit getCurrencyUnit(Locale locale, long timestamp) {
		if("TEST1L".equals(locale.getCountry())){
			return new TestCurrency("TEST1L",1,100);
		}
		return null;
	}
	
	private static final class TestCurrency implements CurrencyUnit{

		private String code;
		private int numCode;
		private int digits;
		
		public TestCurrency(String code, int numCode, int digits){
			this.code = code;
			this.numCode = numCode;
			this.digits = digits;
		}
		
		@Override
		public String getCurrencyCode() {
			return code;
		}

		@Override
		public int getNumericCode() {
			return numCode;
		}

		@Override
		public int getDefaultFractionDigits() {
			return digits;
		}
		
	}

}
