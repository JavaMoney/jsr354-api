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
    public void setUp() throws Exception {
        monetaryContext = MonetaryContextBuilder.of().build();
        sut = new MonetaryAmountFactoryProviderSpiTestStub(monetaryContext);
    }

    @Test
    public void shouldReturnQueryInclusionPolicyALWAYS() throws Exception {
        // when
        QueryInclusionPolicy result = sut.getQueryInclusionPolicy();
        // then
        assertThat(result, is(ALWAYS));
    }

    @Test
    public void getMaximalMonetaryContextShouldReturnDefault() throws Exception {
        // given
        // when
        MonetaryContext result = sut.getMaximalMonetaryContext();
        // then
        assertThat(result, sameInstance(monetaryContext));
    }

    private static final class MonetaryAmountFactoryProviderSpiTestStub implements MonetaryAmountFactoryProviderSpi {

        private final MonetaryContext monetaryContext;

        public MonetaryAmountFactoryProviderSpiTestStub(MonetaryContext monetaryContext) {
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
