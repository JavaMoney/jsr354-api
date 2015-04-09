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

import static org.testng.Assert.*;

/**
 * Tests for {@link javax.money.MonetaryException}.
 */
public class MonetaryExceptionTest{
    @Test
    public void testGetMessage() throws Exception{
        MonetaryException e = new  MonetaryException("message1");
        assertNotNull(e.getMessage());
        assertTrue(e.getMessage().contains("message1"));
        assertNull(e.getCause());
    }

    @Test
    public void testGetCause() throws Exception{
        MonetaryException e = new  MonetaryException("message1", new Exception("Test"));
        assertNotNull(e.getMessage());
        assertTrue(e.getMessage().contains("message1"));
        assertNotNull(e.getCause());
        assertEquals(e.getCause().getMessage(), "Test");
    }
}
