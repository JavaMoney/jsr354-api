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

import static org.testng.Assert.*;

public class RoundingContextTest {

    @Test
    public void testGetRoundingName() throws Exception {
        RoundingContext ctx = RoundingContextBuilder.of("prov", "r1").build();
        assertNotNull(ctx);
        assertEquals(ctx.getRoundingName(), "r1");
    }

    @Test
    public void testGetCurrency() throws Exception {
        CurrencyUnit cu = TestCurrency.of("CHF");
        RoundingContext ctx = RoundingContextBuilder.of("prov", "r1").
                setCurrency(cu).build();
        assertNotNull(ctx);
        assertEquals(ctx.getCurrency(), cu);
    }

    @Test
    public void testToBuilder() throws Exception {
        CurrencyUnit cu = TestCurrency.of("CHF");
        RoundingContext ctx = RoundingContextBuilder.of("prov1", "rounding2")
                .setCurrency(cu).build();
        RoundingContextBuilder b = ctx.toBuilder();
        assertNotNull(b);
        assertEquals(b.build(), ctx);
    }
}