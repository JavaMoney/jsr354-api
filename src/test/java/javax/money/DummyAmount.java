/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.util.Objects;

/**
 * Amount pseudo (non functional) implementation, for testing only.
 * 
 * @author Anatole Tresch
 */
public final class DummyAmount implements
		MonetaryAmount {

    private static final CurrencyContext DUMMY_CURRENCYCONTEXT = CurrencyContextBuilder.create("dummy").build();

    @Override
    public CurrencyUnit getCurrency() {
        return new CurrencyUnit() {
            @Override
            public String getCurrencyCode() {
                return "DMY";
            }

            @Override
            public int getNumericCode() {
                return 0;
            }

            @Override
            public int getDefaultFractionDigits() {
                return 2;
            }

            @Override
            public CurrencyContext getCurrencyContext(){
                return DUMMY_CURRENCYCONTEXT;
            }

            @Override
            public int compareTo(CurrencyUnit o){
                return 0;
            }
        };
    }

	@Override
	public MonetaryContext getMonetaryContext() {
		return DummyAmountFactory.DUMMY_CONTEXT;
	}

	@Override
	public NumberValue getNumber() {
		return new NumberValue() {

			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public int getPrecision() {
				return 0;
			}

			@Override
            public long getAmountFractionNumerator(){
                return 0;
            }

            @Override
            public long getAmountFractionDenominator(){
                return 0;
            }

			@Override
			public int getScale() {

				return 0;
			}

			@Override
			public int intValue() {
				return 0;
			}

			@Override
			public int intValueExact() {
				return 0;
			}

			@Override
			public long longValue() {
				return 0;
			}

			@Override
			public long longValueExact() {
				return 0;
			}

			@Override
			public double doubleValue() {
				return 0;
			}

			@Override
			public double doubleValueExact() {
				return 0;
			}

			@Override
			public <T extends Number> T numberValue(Class<T> numberType) {
				return null;
			}

			@Override
			public <T extends Number> T numberValueExact(
					Class<T> numberType) {
				return null;
			}

			@Override
			public float floatValue() {
				return 0;
			}

			@Override
			public Class<?> getNumberType() {
				return Void.class;
			}
		};
	}

	@Override
	public <R> R query(MonetaryQuery<R> query) {
		return null;
	}

	@Override
	public DummyAmount with(MonetaryOperator operator) {

		return new DummyAmount();
	}

	@Override
	public boolean isGreaterThan(MonetaryAmount amount) {

		return false;
	}

	@Override
	public boolean isGreaterThanOrEqualTo(MonetaryAmount amt) {

		return false;
	}

	@Override
	public boolean isLessThan(MonetaryAmount amt) {

		return false;
	}

	@Override
	public boolean isLessThanOrEqualTo(MonetaryAmount amt) {

		return false;
	}

	@Override
	public boolean isEqualTo(MonetaryAmount amount) {

		return false;
	}

	@Override
	public boolean isNegative() {

		return false;
	}

	@Override
	public boolean isNegativeOrZero() {

		return false;
	}

	@Override
	public boolean isPositive() {

		return false;
	}

	@Override
	public boolean isPositiveOrZero() {

		return false;
	}

	@Override
	public boolean isZero() {

		return false;
	}

	@Override
	public int signum() {

		return 0;
	}

	@Override
	public DummyAmount add(MonetaryAmount amount) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount subtract(MonetaryAmount amount) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount multiply(long multiplicand) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount multiply(double multiplicand) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount multiply(Number multiplicand) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount divide(long amount) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount divide(double amount) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount divide(Number amount) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount remainder(long amount) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount remainder(double amount) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount remainder(Number amount) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount[] divideAndRemainder(long amount) {
		return new DummyAmount[] { new DummyAmount(), new DummyAmount() };
	}

	@Override
	public DummyAmount[] divideAndRemainder(double amount) {
		return new DummyAmount[] { new DummyAmount(), new DummyAmount() };
	}

	@Override
	public DummyAmount[] divideAndRemainder(Number amount) {
		return new DummyAmount[] { new DummyAmount(), new DummyAmount() };
	}

	@Override
	public DummyAmount divideToIntegralValue(long divisor) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount divideToIntegralValue(double divisor) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount divideToIntegralValue(Number divisor) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount scaleByPowerOfTen(int power) {

		return new DummyAmount();
	}

	@Override
	public DummyAmount abs() {

		return new DummyAmount();
	}

	@Override
	public DummyAmount negate() {

		return new DummyAmount();
	}

	@Override
	public DummyAmount plus() {

		return new DummyAmount();
	}

	@Override
	public DummyAmount stripTrailingZeros() {

		return new DummyAmount();
	}

	@Override
	public MonetaryAmountFactory<DummyAmount> getFactory() {
		return new DummyAmountFactory();
	}

    @Override
    public int compareTo(MonetaryAmount o){
    	Objects.requireNonNull(o);
        return 0;
    }
}