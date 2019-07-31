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
package javax.money.format;

import org.testng.annotations.Test;

import javax.money.DummyAmountBuilder;
import javax.money.MonetaryAmountFactory;

import java.util.Locale;

import static org.testng.Assert.*;

public class AmountFormatContextTest {


    @Test
    public void testGetFormatName() throws Exception {
        AmountFormatContext ctx = AmountFormatContextBuilder.of("testest").build();
        assertEquals(ctx.getFormatName(), "testest");
    }

    @Test
    public void testGetLocale() throws Exception {
        AmountFormatContext ctx = AmountFormatContextBuilder.of(Locale.CANADA).build();
        assertEquals(ctx.getLocale(), Locale.CANADA);
    }

    @Test
    public void testGetParseFactory() throws Exception {
        @SuppressWarnings("rawtypes")
		MonetaryAmountFactory f = new DummyAmountBuilder();
        AmountFormatContext ctx = AmountFormatContextBuilder.of("blbl2").
                setMonetaryAmountFactory(f).build();
        assertEquals(ctx.getParseFactory(), f);
    }

    @Test
    public void testToBuilder() throws Exception {
        AmountFormatContext ctx = AmountFormatContextBuilder.of(Locale.CANADA).build();
        AmountFormatContextBuilder b = ctx.toBuilder();
        assertNotNull(b);
        assertEquals(b.build(), ctx);
    }
}