/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.money;

/**
 * Dummy amount factory only used for testing of the {@link Monetary} singleton's delegation
 * logic.
 *
 * @author Anatole Tresch
 */
public final class DummyAmountBuilder implements MonetaryAmountFactory<DummyAmount> {
    /**
     * The {@link MonetaryContext} used.
     */
    private static final MonetaryContext DUMMY_CONTEXT = MonetaryContextBuilder.of(MonetaryAmount.class)
            .set("dummy", true)
            .build();

    /**
     * The signum for the created DummyAmount.
     */
    private int signum = 0;

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
    public DummyAmountBuilder setCurrency(String currencyCode) {
        return this;
    }

    /*
     * (non-Javadoc)
     * @see javax.money.MonetaryAmountFactory#setCurrency(javax.money.CurrencyUnit)
     */
    @Override
    public DummyAmountBuilder setCurrency(CurrencyUnit currency) {
        return this;
    }

    /*
     * (non-Javadoc)
     * @see javax.money.MonetaryAmountFactory#of()
     */
    @Override
    public DummyAmount create() {
        return new DummyAmount(signum, DUMMY_CONTEXT);
    }

    /**
     * Sets the signum for the DummyAmount to be created.
     *
     * @param signum the signum value to set
     */
    public DummyAmountBuilder setSignum(final int signum) {
        this.signum = signum;
        return this;
    }

    /*
         * (non-Javadoc)
         * @see javax.money.MonetaryAmountFactory#setNumber(double)
         */
    @Override
    public DummyAmountBuilder setNumber(double number) {
        return this;
    }

    /*
     * (non-Javadoc)
     * @see javax.money.MonetaryAmountFactory#setNumber(long)
     */
    @Override
    public DummyAmountBuilder setNumber(long number) {
        return this;
    }

    /*
     * (non-Javadoc)
     * @see javax.money.MonetaryAmountFactory#setNumber(java.lang.Number)
     */
    @Override
    public DummyAmountBuilder setNumber(Number number) {
        return this;
    }

    @Override
    public NumberValue getMaxNumber() {
        return null;
    }

    @Override
    public NumberValue getMinNumber() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see javax.money.MonetaryAmountFactory#setContext(javax.money.MonetaryContext)
     */
    @Override
    public DummyAmountBuilder setContext(MonetaryContext monetaryContext) {
        return this;
    }

    /*
     * (non-Javadoc)
     * @see javax.money.MonetaryAmountFactory#setAmount(javax.money.MonetaryAmount)
     */
    @Override
    public DummyAmountBuilder setAmount(MonetaryAmount amount) {
        return this;
    }

}
