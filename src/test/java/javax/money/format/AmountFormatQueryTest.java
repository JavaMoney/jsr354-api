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

import org.testng.annotations.Test;

import javax.money.MonetaryOperator;
import java.text.DecimalFormat;
import java.util.*;

import static org.testng.Assert.*;

/**
 * Tests for {@link AmountFormatQuery}.
 */
public class AmountFormatQueryTest {
    @Test
    public void testOf() throws Exception{
        AmountFormatQuery style = AmountFormatQuery.of(Locale.ENGLISH);
        assertNotNull(style);
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
        assertNotNull(df);
    }

    @Test
    public void testToBuilder() throws Exception{
        AmountFormatQuery style = AmountFormatQueryBuilder.of(Locale.ENGLISH).build();
        AmountFormatQueryBuilder builder = style.toBuilder();
        assertNotNull(builder);
        assertEquals(style, builder.build());
    }

    @Test
    public void testHashCode(){
        MonetaryOperator op = value -> value.multiply(2.0d);
        assertNotNull(op);
        List<AmountFormatContext> contexts = new ArrayList<>();
        contexts.add(AmountFormatContextBuilder.of(Locale.GERMAN).build());
        contexts.add(AmountFormatContextBuilder.of(Locale.ENGLISH).build());
        Set<Integer> hashCodes = new HashSet<>();
        contexts.forEach(amountFormatContext -> hashCodes.add(amountFormatContext.hashCode()));
        // Check we have 5 distinct hash codes...
        assertTrue(hashCodes.size() == 2);
    }

    @Test
    public void testEquals(){
        MonetaryOperator op = value -> value.multiply(2.0d);
        assertNotNull(op);
        List<AmountFormatContext> contexts = new ArrayList<>();
        contexts.add(AmountFormatContextBuilder.of(Locale.ENGLISH).build());
        contexts.add(AmountFormatContextBuilder.of(Locale.GERMAN).build());
        Set<AmountFormatContext> checkContexts = new HashSet<>(contexts);
        // Check we have 5 distinct hash codes...
        assertTrue(checkContexts.size() == 2);
    }

    @Test
    public void testToString() throws Exception{
        AmountFormatContext style =
                AmountFormatContextBuilder.of(Locale.GERMAN).set("groupSizes", new int[]{1, 2, 3, 4})
                        .set("currencyStyle", "NUMERIC_CODE").set("pattern", "###").build();
        String toString = style.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("DE"));
        assertTrue(toString.contains("java.util.Locale"));
        assertTrue(toString.contains("groupSizes"));
        assertTrue(toString.contains("###"));
        assertTrue(toString.contains("NUMERIC_CODE"));
        assertTrue(toString.contains("currencyStyle"));
        assertTrue(toString.contains("AmountFormatContext"));
    }
}
