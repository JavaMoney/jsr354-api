/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * Created by Anatole on 05.03.14.
 */
public class MonetaryParseExceptionTest{
    @Test
    public void testGetErrorIndex() throws Exception{
        MonetaryParseException e = new MonetaryParseException("testInput", 5);
        Assert.assertEquals(e.getErrorIndex(), 5);
        e = new MonetaryParseException("message", "testInput", 5);
        Assert.assertEquals(e.getErrorIndex(), 5);
        Assert.assertEquals(e.getMessage(),"message");
    }

    @Test
    public void testGetInput() throws Exception{
        MonetaryParseException e = new MonetaryParseException("testInput", 5);
        Assert.assertEquals(e.getInput(),"testInput");
        e = new MonetaryParseException("message", "testInput", 5);
        Assert.assertEquals(e.getInput(),"testInput");
        Assert.assertEquals(e.getMessage(),"message");
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void testCreateIllegalInput(){
        MonetaryParseException e = new MonetaryParseException("testInput", 500);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void testCreateIllegalInputWithMessage(){
        MonetaryParseException e = new MonetaryParseException("message", "testInput", 500);
    }
}
