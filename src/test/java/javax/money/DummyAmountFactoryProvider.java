/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import javax.money.spi.MonetaryAmountFactoryProviderSpi;

/**
 * Dummy amount factory only used for testing of the {@link MonetaryAmounts} singleton's delegation
 * logic.
 *
 * @author Anatole Tresch
 */
public final class DummyAmountFactoryProvider implements MonetaryAmountFactoryProviderSpi<DummyAmount> {

    /**
     * The {@link MonetaryContext} used.
     */
    private static final MonetaryContext DUMMY_CONTEXT = MonetaryContextBuilder.of(MonetaryAmount.class)
            .set("dummy", true)
            .build();

    @Override
    public MonetaryAmountFactory<DummyAmount> createMonetaryAmountFactory() {
        return new DummyAmountBuilder();
    }

    @Override
    public Class<DummyAmount> getAmountType() {
        return DummyAmount.class;
    }

    @Override
    public MonetaryContext getMaximalMonetaryContext() {
        return DUMMY_CONTEXT;
    }

    /*
     * (non-Javadoc)
     * @see javax.money.MonetaryAmountFactory#getQueryInclusionPolicy()
     */
    @Override
    public QueryInclusionPolicy getQueryInclusionPolicy() {
        return QueryInclusionPolicy.ALWAYS;
    }

    @Override
    public MonetaryContext getDefaultMonetaryContext() {
        return DUMMY_CONTEXT;
    }

}
