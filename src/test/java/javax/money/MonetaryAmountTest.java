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
