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
