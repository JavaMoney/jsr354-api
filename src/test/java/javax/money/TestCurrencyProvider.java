package javax.money;

import java.util.Locale;

import javax.money.CurrencyUnit;
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
	public CurrencyUnit getCurrencyUnit(Locale locale) {
		if("TEST1L".equals(locale.getCountry())){
			return new TestCurrency("TEST1L",1,2);
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

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((code == null) ? 0 : code.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TestCurrency other = (TestCurrency) obj;
			if (code == null) {
				if (other.code != null)
					return false;
			} else if (!code.equals(other.code))
				return false;
			return true;
		}
		
	}

}
