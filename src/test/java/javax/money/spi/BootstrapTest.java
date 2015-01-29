package javax.money.spi;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.testng.annotations.Test;

import javax.money.MonetaryException;

/**
 * Created by Anatole on 04.03.14.
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
    public void testGetServicesWithDefault() throws Exception {
        Collection<Object> services = Collection.class
                .cast(Bootstrap.getServices(Boolean.class, Arrays.asList(Boolean.FALSE, Boolean.TRUE)));
        assertNotNull(services);
        assertFalse(services.isEmpty());
        assertTrue(services.size() == 2);
        assertTrue(services.contains(Boolean.FALSE));
        assertTrue(services.contains(Boolean.TRUE));
        services = Collection.class.cast(Bootstrap.getServices(String.class, null));
        assertNotNull(services);
        assertFalse(services.isEmpty());
        assertTrue(services.contains("service1"));
        assertTrue(services.contains("service2"));
    }

    @Test
    public void testGetService() throws Exception {
        Integer num = Bootstrap.getService(Integer.class);
        assertNotNull(num);
        assertTrue(num.equals(5));
    }

    @Test(expectedExceptions = {MonetaryException.class})
    public void testGetService_BadCase() throws Exception {
        Bootstrap.getService(Locale.class);
    }

    @Test
    public void testGetService1() throws Exception {
        Long num = Bootstrap.getService(Long.class, 111L);
        assertNotNull(num);
        assertTrue(num.equals(111L));
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

        @Override
        public <T> List<T> getServices(Class<T> serviceType, List<T> defaultList) {
            if (String.class.equals(serviceType)) {
                return List.class.cast(Arrays.asList("service1", "service2"));
            } else if (Integer.class.equals(serviceType)) {
                return List.class.cast(Arrays.asList(5));
            } else if (Long.class.equals(serviceType)) {
                return List.class.cast(Arrays.asList((long) 111));
            }
            return super.getServices(serviceType, defaultList);
        }
    }
}
