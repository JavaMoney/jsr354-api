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

import java.math.BigDecimal;

public final class DummyAmountFactory implements
		MonetaryAmountFactory<DummyAmount> {

	static MonetaryContext DUMMY_CONTEXT = new MonetaryContext.Builder()
			.setFixedScale(true).setMaxScale(0)
			.setPrecision(0).setAmountType(DummyAmount.class).build();

	@Override
	public Class<DummyAmount> getAmountType() {
		return DummyAmount.class;
	}

	@Override
	public MonetaryContext getDefaultMonetaryContext() {
		return DUMMY_CONTEXT;
	}

	@Override
	public MonetaryContext getMaximalMonetaryContext() {
		return DUMMY_CONTEXT;
	}

	@Override
	public DummyAmountFactory withCurrency(String currencyCode) {
		return this;
	}

	@Override
	public DummyAmountFactory with(CurrencyUnit currency) {
		return this;
	}

	@Override
	public DummyAmount create() {
		return new DummyAmount();
	}

	@Override
	public DummyAmountFactory with(double number) {
		return this;
	}

	@Override
	public DummyAmountFactory with(long number) {
		return this;
	}

	@Override
	public DummyAmountFactory with(Number number) {
		return this;
	}

	@Override
	public DummyAmountFactory with(MonetaryContext monetaryContext) {
		return this;
	}

	@Override
	public DummyAmountFactory with(MonetaryAmount amount) {
		return this;
	}

}
