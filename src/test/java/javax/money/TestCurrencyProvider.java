/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyProviderSpi;

public final class TestCurrencyProvider implements CurrencyProviderSpi{

	@Override
	public CurrencyUnit getCurrencyUnit(String currencyCode) {

        switch (currencyCode) {
            case "test1":
                return new TestCurrency("test1", 1, 2);
            case "error":
                throw new IllegalArgumentException("error encountered!");
            case "invalid":
                return new TestCurrency("invalid2", 1, 2);

            default:
                return null;
        }


	}


	@Override
	public CurrencyUnit getCurrencyUnit(Locale locale) {
		if("TEST1L".equals(locale.getCountry())){
			return new TestCurrency("TEST1L",1,2);
		}
        else if(Locale.CHINA.equals(locale)){
            throw new IllegalArgumentException("CHINA error encountered!");
        }
        else if(Locale.CHINESE.equals(locale)){
            return new TestCurrency("invalid2",1,2);
        }
		return null;
	}

    @Override
    public Collection<CurrencyUnit> getCurrencies(){
        List<CurrencyUnit> result = new ArrayList<>();
        result.add(new TestCurrency("test1",1,2));
        result.add(new TestCurrency("invalid2",1,2));
        return result;
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

        @Override
        public int compareTo(CurrencyUnit o){
            return getCurrencyCode().compareTo(o.getCurrencyCode());
        }
    }

}
