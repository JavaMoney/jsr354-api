/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
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
