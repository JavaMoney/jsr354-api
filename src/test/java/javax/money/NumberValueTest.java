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

import javax.money.convert.TestNumberValue;

import static org.testng.Assert.*;

public class NumberValueTest {

    @Test
    public void testCompareTo() throws Exception {
        NumberValue nv1a = new TestNumberValue(1);
        NumberValue nv2 = new TestNumberValue(2);
        NumberValue nv1b = new TestNumberValue(1);

        assertEquals(nv1a.compareTo(nv2), -1);
        assertEquals(nv2.compareTo(nv1a), 1);
        assertEquals(nv1a.compareTo(nv1b), 0);
        assertEquals(nv2.compareTo(nv2), 0);
    }
}