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

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.Assert.*;

/**
 * Tests for {@link javax.money.UnknownCurrencyException}.
 */
public class UnknownCurrencyExceptionTest {

    private static final Logger LOGGER = Logger.getLogger(UnknownCurrencyExceptionTest.class.getName());
    @Test
    public void testGetCurrencyCode() throws Exception{
        UnknownCurrencyException e = new UnknownCurrencyException("GGG");
        assertEquals("GGG", e.getCurrencyCode());
        assertNull(e.getLocale());
        assertTrue(e.toString().contains("GGG"));
        assertTrue(e.toString().contains("UnknownCurrencyException"));
    }

    @Test
    public void testGetLocale() throws Exception{
        UnknownCurrencyException e = new UnknownCurrencyException(Locale.CANADA_FRENCH);
        assertEquals(Locale.CANADA_FRENCH, e.getLocale());
        assertNull(e.getCurrencyCode());
        LOGGER.log(Level.INFO, e.getMessage(), e);
        assertTrue(e.toString().contains(Locale.CANADA_FRENCH.toString()));
        assertTrue(e.toString().contains("UnknownCurrencyException"));
    }

}
