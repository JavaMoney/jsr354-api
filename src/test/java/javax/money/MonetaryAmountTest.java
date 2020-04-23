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
package javax.money;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class MonetaryAmountTest {


    /**
     * Test method for
     * {@link MonetaryAmount#query(MonetaryQuery)}.
     */
    @Test
    public void testQuery() throws Exception {

        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().create();

        //when
        final MonetaryAmount retVal = monetaryAmount.query(amount -> amount);

        //then
        assertSame(retVal, monetaryAmount);
    }

    /**
     * Test method for
     * {@link MonetaryAmount#with(MonetaryOperator)}.
     */
    @Test
    public void testWith() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().create();

        //when
        final MonetaryAmount retVal = monetaryAmount.with(amount -> amount);

        //then
        assertSame(retVal, monetaryAmount);
    }

    /**
     * Test method for
     * {@link MonetaryAmount#isNegative()}}.
     */
    @Test
    public void testIsNegativeWithNegativeSignumReturnsTrue() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(-1).create();

        //when
        final boolean retVal = monetaryAmount.isNegative();

        //then
        assertTrue(retVal);
    }

    /**
     * Test method for
     * {@link MonetaryAmount#isNegative()}}.
     */
    @Test
    public void testIsNegativeWithZeroSignumReturnsTrue() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(0).create();

        //when
        final boolean retVal = monetaryAmount.isNegative();

        //then
        assertFalse(retVal);
    }

    /**
     * Test method for
     * {@link MonetaryAmount#isNegative()}}.
     */
    @Test
    public void testIsNegativeWithPositiveSignumReturnsTrue() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(1).create();

        //when
        final boolean retVal = monetaryAmount.isNegative();

        //then
        assertFalse(retVal);
    }


    /**
     * Test method for
     * {@link javax.money.MonetaryAmount#isNegativeOrZero()}}.
     */
    @Test
    public void testIsNegativeOrZeroWithNegativeSignumReturnsTrue() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(-1).create();

        //when
        final boolean retVal = monetaryAmount.isNegativeOrZero();

        //then
        assertTrue(retVal);
    }

    /**
     * Test method for
     * {@link javax.money.MonetaryAmount#isNegativeOrZero()}}.
     */
    @Test
    public void testIsNegativeOrZeroWithZeroSignumReturnsTrue() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(0).create();

        //when
        final boolean retVal = monetaryAmount.isNegativeOrZero();

        //then
        assertTrue(retVal);
    }

    /**
     * Test method for
     * {@link javax.money.MonetaryAmount#isNegativeOrZero()}}.
     */
    @Test
    public void testIsNegativeOrZeroWithPositiveSignumReturnsFalse() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(1).create();

        //when
        final boolean retVal = monetaryAmount.isNegativeOrZero();

        //then
        assertFalse(retVal);
    }

    /**
     * Test method for
     * {@link MonetaryAmount#isPositive()}.
     */
    @Test
    public void testIsPositiveWithPositiveSignumReturnsTrue() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(1).create();

        //when
        final boolean retVal = monetaryAmount.isPositive();

        //then
        assertTrue(retVal);
    }

    /**
     * Test method for
     * {@link MonetaryAmount#isPositive()}.
     */
    @Test
    public void testIsPositiveWithZeroSignumReturnsFalse() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(0).create();

        //when
        final boolean retVal = monetaryAmount.isPositive();

        //then
        assertFalse(retVal);
    }

    /**
     * Test method for
     * {@link MonetaryAmount#isPositive()}.
     */
    @Test
    public void testIsPositiveWithNegativeSignumReturnsTrue() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(-1).create();

        //when
        final boolean retVal = monetaryAmount.isPositive();

        //then
        assertFalse(retVal);
    }


    /**
     * Test method for
     * {@link javax.money.MonetaryAmount#isPositiveOrZero()}.
     */
    @Test
    public void testIsPositiveOrZeroWithPositiveSignumReturnsTrue() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(1).create();

        //when
        final boolean retVal = monetaryAmount.isPositiveOrZero();

        //then
        assertTrue(retVal);
    }

    /**
     * Test method for
     * {@link javax.money.MonetaryAmount#isPositiveOrZero()}.
     */
    @Test
    public void testIsPositiveOrZeroWithZeroSignumReturnsTrue() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(0).create();

        //when
        final boolean retVal = monetaryAmount.isPositiveOrZero();

        //then
        assertTrue(retVal);
    }

    /**
     * Test method for
     * {@link javax.money.MonetaryAmount#isPositiveOrZero()}.
     */
    @Test
    public void testIsPositiveOrZeroWithNegativeSignumReturnsFalse() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(-1).create();

        //when
        final boolean retVal = monetaryAmount.isPositiveOrZero();

        //then
        assertFalse(retVal);
    }

    /**
     * Test method for
     * {@link javax.money.MonetaryAmount#isZero()}.
     */
    @Test
    public void testIsZeroWithPositiveSignumReturnsFalse() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(1).create();

        //when
        final boolean retVal = monetaryAmount.isZero();

        //then
        assertFalse(retVal);
    }

    /**
     * Test method for
     * {@link javax.money.MonetaryAmount#isZero()}.
     */
    @Test
    public void testIsZeroWithZeroSignumReturnsTrue() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(0).create();

        //when
        final boolean retVal = monetaryAmount.isZero();

        //then
        assertTrue(retVal);
    }

    /**
     * Test method for
     * {@link javax.money.MonetaryAmount#isZero()}.
     */
    @Test
    public void testIsZeroWithNegativeSignumReturnsFalse() throws Exception {
        //given
        final MonetaryAmount monetaryAmount = new DummyAmountBuilder().setSignum(-1).create();

        //when
        final boolean retVal = monetaryAmount.isZero();

        //then
        assertFalse(retVal);
    }
}
