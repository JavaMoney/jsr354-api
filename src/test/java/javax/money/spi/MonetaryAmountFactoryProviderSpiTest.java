/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
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
package javax.money.spi;

import static javax.money.spi.MonetaryAmountFactoryProviderSpi.QueryInclusionPolicy;
import static javax.money.spi.MonetaryAmountFactoryProviderSpi.QueryInclusionPolicy.ALWAYS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryContext;
import javax.money.MonetaryContextBuilder;

/**
 * unit test.
 */
public class MonetaryAmountFactoryProviderSpiTest {

    private MonetaryAmountFactoryProviderSpiTestStub sut;
    private MonetaryContext monetaryContext;

    @BeforeMethod
    public void setUp(){
        monetaryContext = MonetaryContextBuilder.of().build();
        sut = new MonetaryAmountFactoryProviderSpiTestStub(monetaryContext);
    }

    @Test
    public void shouldReturnQueryInclusionPolicyALWAYS() {
        // when
        QueryInclusionPolicy result = sut.getQueryInclusionPolicy();
        // then
        assertThat(result, is(ALWAYS));
    }

    @Test
    public void getMaximalMonetaryContextShouldReturnDefault(){
        // given
        // when
        MonetaryContext result = sut.getMaximalMonetaryContext();
        // then
        assertThat(result, sameInstance(monetaryContext));
    }

    @SuppressWarnings("rawtypes")
	private static final class MonetaryAmountFactoryProviderSpiTestStub implements MonetaryAmountFactoryProviderSpi {

        private final MonetaryContext monetaryContext;

        MonetaryAmountFactoryProviderSpiTestStub(MonetaryContext monetaryContext) {
            this.monetaryContext = monetaryContext;
        }

        @Override
        public Class getAmountType() {
            return null;
        }

        @Override
        public MonetaryAmountFactory createMonetaryAmountFactory() {
            return null;
        }

        @Override
        public MonetaryContext getDefaultMonetaryContext() {
            return monetaryContext;
        }
    }
}
