package javax.money.convert;

import java.util.Arrays;
import java.util.Collection;

import javax.money.CurrencyUnit;
import javax.money.convert.spi.MonetaryConversionsSingletonSpi;

public class TestMonetaryConversionSpi implements
		MonetaryConversionsSingletonSpi {

	private ConversionProvider dummyProvider = new DummyConversionProvider();

	@Override
	public ConversionProvider getConversionProvider(String type) {
		if ("test".equals(type)) {
			return dummyProvider;
		}
		return null;
	}

	@Override
	public Collection<String> getSupportedExchangeRateTypes() {
		return Arrays.asList(new String[] { "test" });
	}

	@Override
	public boolean isSupportedExchangeRateType(String type) {
		return "test".equals(type);
	}

	public static final class DummyConversionProvider implements
			ConversionProvider {

		@Override
		public String getExchangeRateType() {
			return "test";
		}

		@Override
		public boolean isAvailable(CurrencyUnit src, CurrencyUnit target) {
			return false;
		}

		@Override
		public boolean isAvailable(CurrencyUnit CurrencyUnit,
				CurrencyUnit target, long timestamp) {
			return false;
		}

		@Override
		public ExchangeRate getExchangeRate(CurrencyUnit sourceCurrency,
				CurrencyUnit targetCurrency, long timestamp) {
			return null;
		}

		@Override
		public ExchangeRate getExchangeRate(CurrencyUnit source,
				CurrencyUnit target) {
			return null;
		}

		@Override
		public ExchangeRate getReversed(ExchangeRate rate) {
			return null;
		}

		@Override
		public CurrencyConverter getConverter() {
			throw new UnsupportedOperationException();
		}

	}

}
