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


import static org.testng.Assert.*;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests for {@link javax.money.convert.ProviderContext}.
 */
public class ProviderContextTest {

    @Test
    public void testGetRateTypes() throws Exception {
        ProviderContext ctx = ProviderContextBuilder.of("myprov", RateType.DEFERRED, RateType.HISTORIC).build();
        assertEquals("myprov", ctx.getProviderName());
    }

    @Test
    public void testToBuilder() throws Exception {
        ProviderContext ctx = ProviderContextBuilder.of("myprov", RateType.ANY).build();
        assertEquals(ctx, ctx.toBuilder().build());
    }

    @Test
    public void testOf() throws Exception {
        ProviderContext ctx = ProviderContext.of("testprov");
        ProviderContext ctx2 = ProviderContext.of("testprov");
        assertEquals(ctx, ctx2);
        assertEquals("testprov", ctx.getProviderName());
    }

    @Test
    public void testOfWithRateType() throws Exception {
        ProviderContext ctx = ProviderContext.of("test", RateType.REALTIME);
        ProviderContext ctx2 = ProviderContext.of("test", RateType.REALTIME);
        assertEquals(ctx, ctx2);
        assertNotNull(ctx.getRateTypes());
        assertTrue(ctx.getRateTypes().size() == 1);
        assertTrue(ctx.getRateTypes().contains(RateType.REALTIME));
    }

    @Test
    public void testProviderContextBuilder() {
        Set<RateType> types = new HashSet<>();
        types.add(RateType.DEFERRED);
        types.add(RateType.HISTORIC);
        ProviderContextBuilder b = ProviderContextBuilder.of("prov", types);
        ProviderContext ctx = b.build();
        assertEquals(ctx.getRateTypes(), types);
    }

}
