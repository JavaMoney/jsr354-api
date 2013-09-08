/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package javax.money;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.money.Money;
import javax.money.Percent;

import org.junit.Test;

/**
 * @author Werner Keil
 * 
 */
public class PercentTest {

    @Test
    public void testOf() {
    	final Percent perc = Percent.of(BigDecimal.ONE);
    	assertNotNull(perc);
    }

    @Test
    public void testApply() {
    	final Money m = Money.of("CHF", BigDecimal.valueOf(2.35d));
    	assertEquals(Money.of("CHF", BigDecimal.valueOf(0.235d)), Percent.of(BigDecimal.TEN).apply(m));
    }

    @Test
    public void testApply10() {
    	final Money m = Money.of("CHF", 3);
    	assertEquals(Money.of("CHF", BigDecimal.valueOf(0.3d)), Percent.of(BigDecimal.TEN).apply(m));
    }

    @Test
    public void testApply20() {
    	final Money m = Money.of("CHF", 120);
    	assertEquals(Money.of("CHF", BigDecimal.valueOf(24d)), Percent.of(BigDecimal.valueOf(20)).apply(m));
    }

    @Test
    public void testApply30() {
    	final Money m = Money.of("CHF", 120);
    	assertEquals(Money.of("CHF", BigDecimal.valueOf(36d)), Percent.of(BigDecimal.valueOf(30)).apply(m));
    }

    @Test
    public void testToString() {
    	final String compareTo = NumberFormat.getPercentInstance().format(0.15);
    	assertEquals(compareTo, Percent.of(BigDecimal.valueOf(15)).toString());
    }
}
