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

import java.util.List;

import static org.testng.Assert.*;

/**
 * Test class for {@link javax.money.AbstractQuery} and  {@link javax.money.AbstractQueryBuilder}.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class AbstractQueryTest {


	private AbstractQueryBuilder<AbstractQueryBuilder, AbstractQuery> createBuilder() {
        return new AbstractQueryBuilder() {
            @Override
            public AbstractQuery build() {
                return new AbstractQuery(this) {
					private static final long serialVersionUID = 7131918476096875127L;
                };
            }
        };
    }


    @Test
    public void testGetProviders() throws Exception {
        AbstractQueryBuilder b = createBuilder();
        b.setProviderNames("1", "2", "3");
        AbstractQuery query = b.build();
        assertNotNull(query);
        List<String> providers = query.getProviderNames();
        assertNotNull(providers);
        assertTrue(providers.size() == 3);
        assertTrue(providers.contains("1"));
        assertTrue(providers.contains("2"));
        assertTrue(providers.contains("3"));
    }

    @Test
    public void testGetTargetType() throws Exception {
        AbstractQueryBuilder b = createBuilder();
        b.setTargetType(String.class);
        AbstractQuery query = b.build();
        assertEquals(query.getTargetType(), String.class);
    }

//    @Test
//    public void testGetTimestampMillis() throws Exception {
//        AbstractQueryBuilder b = createBuilder();
//        b.setTimestampMillis(200L);
//        AbstractQuery query = b.build();
//        assertEquals(query.getTimestampMillis().longValue(), 200L);
//		LocalDateTime localDateTime = LocalDateTime
//                .parse("1970-01-01T01:00:00.200");
//        assertEquals(query.getTimestamp(), localDateTime);
//    }
//
//    @Test
//    public void testGetTimestamp() throws Exception {
//        AbstractQueryBuilder b = createBuilder();
//		LocalDateTime instant = LocalDateTime.now();
//        b.setTimestamp(instant);
//        AbstractQuery query = b.build();
//        assertEquals(query.getTimestamp(), instant);
//		assertEquals(query.getTimestamp(), instant);
//    }
}