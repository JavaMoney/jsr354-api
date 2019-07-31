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
package javax.money;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RoundingQueryBuilderTest {

    @Test
    public void testSetScale() throws Exception {
        RoundingQueryBuilder b = RoundingQueryBuilder.of().setScale(6);
        assertEquals(b.build().getScale(), Integer.valueOf(6));
    }

    @Test
    public void testOf() throws Exception {
        RoundingQueryBuilder b1 = RoundingQueryBuilder.of().setScale(6);
        RoundingQueryBuilder b2 = RoundingQueryBuilder.of(b1.build());
        assertEquals(b1.build().getScale(), Integer.valueOf(6));
        assertEquals(b2.build().getScale(), Integer.valueOf(6));
    }
}