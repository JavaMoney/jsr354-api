/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2019 Werner Keil, Otavio Santana, Trivadis AG
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
