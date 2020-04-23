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

import static org.testng.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;

import org.testng.annotations.Test;

/**
 * Test for {@link CurrencyQueryBuilder}.
 *
 * @author Philippe Marschall
 */
public class CurrencyQueryBuilderTest {

    /**
     * Test for {@link CurrencyQueryBuilder#setCurrencyCodes(String...)}.
     */
    @Test
    public void setNumericCodesString() {
        CurrencyQuery query = CurrencyQueryBuilder.of().setCurrencyCodes("CHF", "EUR").build();

        Collection<String> currencyCodes = query.getCurrencyCodes();
        assertEquals(2, currencyCodes.size());
        Iterator<String> iterator = currencyCodes.iterator();
        assertEquals("CHF", iterator.next());
        assertEquals("EUR", iterator.next());
    }

    /**
     * Test for {@link CurrencyQueryBuilder#setNumericCodes(int...)}.
     */
    @Test
    public void setNumericCodesInt() {
        CurrencyQuery query = CurrencyQueryBuilder.of().setNumericCodes(756, 978).build();

        Collection<Integer> numericCodes = query.getNumericCodes();
        assertEquals(2, numericCodes.size());
        Iterator<Integer> iterator = numericCodes.iterator();
        assertEquals(Integer.valueOf(756), iterator.next());
        assertEquals(Integer.valueOf(978), iterator.next());
    }

    @Test
    public void testOf_CurrencyQuery() {
        CurrencyQuery query = CurrencyQueryBuilder.of().setNumericCodes(756, 978).build();
        assertEquals(CurrencyQueryBuilder.of(query).build(), query);

    }

}
