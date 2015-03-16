/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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
                return List.class.cast(Arrays.asList(5));
            } else if (Long.class.equals(serviceType)) {
                return List.class.cast(Arrays.asList((long) 111));
            }
            return super.getServices(serviceType);
        }

    }
}
