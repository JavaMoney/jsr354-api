/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
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
package javax.money.format;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for {@link javax.money.format.MonetaryParseException}.
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
        //noinspection ThrowableInstanceNeverThrown
        new MonetaryParseException("testInput", 500);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void testCreateIllegalInputWithMessage(){
        //noinspection ThrowableInstanceNeverThrown
        new MonetaryParseException("message", "testInput", 500);
    }
}
