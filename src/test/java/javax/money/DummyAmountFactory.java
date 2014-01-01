/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

/**
 * Dummy amount factory only used for testing of the {@link MonetaryAmounts} singleton's delegation
 * logic.
 * 
 * @author Anatole Tresch
 */
public final class DummyAmountFactory implements
		MonetaryAmountFactory<DummyAmount> {
	/**
	 * The {@link MonetaryContext} used.
	 */
	static MonetaryContext DUMMY_CONTEXT = new MonetaryContext.Builder()
			.setFixedScale(true).setMaxScale(0)
			.setPrecision(0).setAmountType(DummyAmount.class).build();

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#getAmountType()
	 */
	@Override
	public Class<DummyAmount> getAmountType() {
		return DummyAmount.class;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#getDefaultMonetaryContext()
	 */
	@Override
	public MonetaryContext getDefaultMonetaryContext() {
		return DUMMY_CONTEXT;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#getMaximalMonetaryContext()
	 */
	@Override
	public MonetaryContext getMaximalMonetaryContext() {
		return DUMMY_CONTEXT;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#setCurrency(java.lang.String)
	 */
	@Override
	public DummyAmountFactory setCurrency(String currencyCode) {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#setCurrency(javax.money.CurrencyUnit)
	 */
	@Override
	public DummyAmountFactory setCurrency(CurrencyUnit currency) {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#create()
	 */
	@Override
	public DummyAmount create() {
		return new DummyAmount();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#setNumber(double)
	 */
	@Override
	public DummyAmountFactory setNumber(double number) {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#setNumber(long)
	 */
	@Override
	public DummyAmountFactory setNumber(long number) {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#setNumber(java.lang.Number)
	 */
	@Override
	public DummyAmountFactory setNumber(Number number) {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#setContext(javax.money.MonetaryContext)
	 */
	@Override
	public DummyAmountFactory setContext(MonetaryContext monetaryContext) {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#setAmount(javax.money.MonetaryAmount)
	 */
	@Override
	public DummyAmountFactory setAmount(MonetaryAmount amount) {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.MonetaryAmountFactory#getQueryInclusionPolicy()
	 */
	@Override
	public QueryInclusionPolicy getQueryInclusionPolicy() {
		return QueryInclusionPolicy.ALWAYS;
	}

}
