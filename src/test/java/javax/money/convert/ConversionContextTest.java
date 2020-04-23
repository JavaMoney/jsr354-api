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
package javax.money.convert;


import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Tests for {@link ConversionContext}.
 */
public class ConversionContextTest {
    @Test
    public void testGetRateType() throws Exception {
        ConversionContext ctx = ConversionContextBuilder.of().setRateType(RateType.DEFERRED).build();
        assertEquals(RateType.DEFERRED, ctx.getRateType());
    }

    @Test
    public void testGetProvider() throws Exception {
        ConversionContext ctx = ConversionContextBuilder.of().setProviderName("myprov").build();
        assertEquals("myprov", ctx.getProviderName());
    }

    @Test
    public void testToBuilder() throws Exception {
        ConversionContext ctx = ConversionContextBuilder.of().setProviderName("myprov").build();
        assertEquals(ctx, ctx.toBuilder().build());
    }

    @Test
    public void testOf() throws Exception {
        ConversionContext ctx = ConversionContext.of();
        ConversionContext ctx2 = ConversionContext.of();
        assertEquals(ctx, ctx2);
    }

    @Test
    public void testOf1() throws Exception {
        ConversionContext ctx = ConversionContext.of(RateType.REALTIME);
        ConversionContext ctx2 = ConversionContext.of(RateType.REALTIME);
        assertEquals(ctx, ctx2);
        assertEquals(RateType.REALTIME, ctx.getRateType());
    }

    @Test
    public void testOf2() throws Exception {
        ConversionContext ctx = ConversionContext.of("prov", RateType.REALTIME);
        ConversionContext ctx2 = ConversionContext.of("prov", RateType.REALTIME);
        assertEquals(ctx, ctx2);
        assertEquals(RateType.REALTIME, ctx.getRateType());
        assertEquals("prov", ctx.getProviderName());
    }
}
