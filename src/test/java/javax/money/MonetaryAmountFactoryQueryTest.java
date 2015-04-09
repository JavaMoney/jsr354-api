/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Anatole Tresch
 */
public class MonetaryAmountFactoryQueryTest {

    private static final int MAX_SCALE = 5;
    private static final int PRECISION = 3;
    private static final boolean FIXED_SCALE = true;

    private final MonetaryAmountFactoryQuery query = new MonetaryAmountFactoryQuery(MonetaryAmountFactoryQueryBuilder
            .of()
            .setMaxScale(MAX_SCALE)
            .setPrecision(PRECISION)
            .setFixedScale(FIXED_SCALE));

    /**
     * Test method for
     * {@link MonetaryAmountFactoryQuery#getMaxScale()}.
     */
    @Test
    public void testGetMaxScaleReturnsConfiguredValue() throws Exception {
        //when
        final int retVal = query.getMaxScale();

        //then
        assertEquals(retVal, MAX_SCALE);

    }

    /**
     * Test method for
     * {@link MonetaryAmountFactoryQuery#getPrecision()}.
     */
    @Test
    public void testGetPrecision() throws Exception {
        //when
        final int retVal = query.getPrecision();

        //then
        assertEquals(retVal, PRECISION);
    }

    /**
     * Test method for
     * {@link MonetaryAmountFactoryQuery#isFixedScale()}.
     */
    @Test
    public void testGetFixedScale() throws Exception {
        //when
        final boolean retVal = query.isFixedScale();

        //then
        assertEquals(retVal, FIXED_SCALE);
    }

    /**
     * Test method for
     * {@link MonetaryAmountFactoryQuery#toBuilder()}.
     */
    @Test
    public void testToBuilder() throws Exception {
        //when
        final MonetaryAmountFactoryQueryBuilder builder = query.toBuilder();

        //then
        assertNotNull(builder);
    }
}
