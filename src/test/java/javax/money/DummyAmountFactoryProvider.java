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

import javax.money.spi.MonetaryAmountFactoryProviderSpi;

/**
 * Dummy amount factory only used for testing of the {@link Monetary} singleton's delegation
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
