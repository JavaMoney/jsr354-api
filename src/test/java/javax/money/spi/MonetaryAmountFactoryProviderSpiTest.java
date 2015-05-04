/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
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
