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
package javax.money.spi;

import java.util.*;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for {@link javax.money.spi.Bootstrap}.
 */
@SuppressWarnings("unchecked")
@Test
public class BootstrapTest {

    @Test
    public void testInit_InitTwice() throws Exception {
        TestServiceProvider testProv = new TestServiceProvider();
        ServiceProvider prov = Bootstrap.init(testProv);
        assertTrue(testProv == Bootstrap.init(prov));
    }

    @Test
    public void testInit() throws Exception {
        Collection<Object> services = Collection.class.cast(Bootstrap.getServices(String.class));
        assertNotNull(services);
        assertFalse(services.isEmpty());
        assertTrue(services.contains("service1"));
        assertTrue(services.contains("service2"));
        services = Collection.class.cast(Bootstrap.getServices(Runtime.class));
        assertNotNull(services);
        assertTrue(services.isEmpty());
    }

    @Test
    public void testGetServiceProvider() throws Exception {
        assertNotNull(Bootstrap.getServiceProvider());
        assertEquals(Bootstrap.getServiceProvider().getClass(), TestServiceProvider.class);
    }

    @Test
    public void testGetServices() throws Exception {
        Collection<Object> services = Collection.class.cast(Bootstrap.getServices(String.class));
        assertNotNull(services);
        assertFalse(services.isEmpty());
        assertTrue(services.contains("service1"));
        assertTrue(services.contains("service2"));
        services = Collection.class.cast(Bootstrap.getServices(Runtime.class));
        assertNotNull(services);
        assertTrue(services.isEmpty());
    }

    @Test
    public void testGetService() throws Exception {
        Integer num = Bootstrap.getService(Integer.class);
        assertNotNull(num);
        assertTrue(num.equals(5));
    }

    @Test
    public void testGetService_BadCase() throws Exception {
        assertNull(Bootstrap.getService(Locale.class));
    }

    public final static class TestServiceProvider extends DefaultServiceProvider
            implements ServiceProvider {

        @Override
        public <T> List<T> getServices(Class<T> serviceType) {
            if (String.class.equals(serviceType)) {
                return List.class.cast(Arrays.asList("service1", "service2"));
            } else if (Integer.class.equals(serviceType)) {
                return List.class.cast(Collections.singletonList(5));
            } else if (Long.class.equals(serviceType)) {
                return List.class.cast(Collections.singletonList((long) 111));
            }
            return super.getServices(serviceType);
        }

    }
}
