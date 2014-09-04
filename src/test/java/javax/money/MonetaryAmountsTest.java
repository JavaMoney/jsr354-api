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

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/**
 * @author Anatole
 */
public class MonetaryAmountsTest{

    /**
     * Test method for
     * {@link javax.money.MonetaryAmounts#getAmountFactory(java.lang.Class)}.
     */
    @Test
    public void testGetFactory(){
        assertNotNull(MonetaryAmounts.getDefaultAmountFactory());
        assertNotNull(MonetaryAmounts.getAmountFactory(DummyAmount.class));
        assertTrue(MonetaryAmounts.getDefaultAmountFactory().getClass() ==
                           MonetaryAmounts.getAmountFactory(DummyAmount.class).getClass());
    }

    /**
     * Test method for {@link javax.money.MonetaryAmounts#getAmountTypes()}.
     */
    @Test
    public void testGetTypes(){
        assertNotNull(MonetaryAmounts.getAmountTypes());
        assertTrue(MonetaryAmounts.getAmountTypes().size() == 1);
        assertTrue(MonetaryAmounts.getAmountTypes().contains(DummyAmount.class));
    }

    /**
     * Test method for
     * {@link javax.money.MonetaryAmounts#getDefaultAmountType()}.
     */
    @Test
    public void testGetDefaultAmountType(){
        assertNotNull(MonetaryAmounts.getDefaultAmountFactory());
        assertEquals(DummyAmountBuilder.class, MonetaryAmounts.getDefaultAmountFactory().getClass());
    }

    //	/**
    //	 * Test method for
    //	 * {@link javax.money.MonetaryAmounts#getAmountType(MonetaryAmountFactoryQuery)}
    //	 * .
    //	 */
    //	@Test
    //	public void testGetAmountType() {
    //		assertNotNull(MonetaryAmounts.getAmountType(null));
    //	}

}
