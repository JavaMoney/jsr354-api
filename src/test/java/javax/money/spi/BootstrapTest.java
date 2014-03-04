package javax.money.spi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Anatole on 04.03.14.
 */
public class BootstrapTest{

    private ServiceProvider prov;

    @Before
    public void initBootstrap() throws Exception{
        Field f = Bootstrap.class.getDeclaredField("serviceProviderDelegate");
        f.setAccessible(true);
        prov = (ServiceProvider) f.get(null);
        f.set(null, null);
    }

    private ServiceProvider removeServiceProvider(){
        return null;
    }

    @After
    public void restore() throws Exception{
        if(prov != null){
            Field f = Bootstrap.class.getDeclaredField("serviceProviderDelegate");
            f.setAccessible(true);
            f.set(null, prov);
            prov = null;
        }
    }

    @Test(expected=IllegalStateException.class)
    public void testInit_InitTwice() throws Exception{
        Bootstrap.init(new TestServiceProvider());
        Bootstrap.init(new TestServiceProvider());
    }

    @Test
    public void testInit() throws Exception{
        Bootstrap.init(new TestServiceProvider());
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
    public void testGetServiceProvider() throws Exception{
        Bootstrap.init(new TestServiceProvider());
        assertNotNull(Bootstrap.getServiceProvider());
        assertEquals(Bootstrap.getServiceProvider().getClass(), TestServiceProvider.class);
    }

    @Test
    public void testGetServices() throws Exception{
        Bootstrap.init(new TestServiceProvider());
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
    public void testGetServicesWithDefault() throws Exception{
        Bootstrap.init(new TestServiceProvider());
        Collection<Object> services = Collection.class
                .cast(Bootstrap.getServices(Boolean.class, Arrays.asList(new Boolean[]{Boolean.FALSE, Boolean.TRUE})));
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
    public void testGetService() throws Exception{
        Bootstrap.init(new TestServiceProvider());
        Integer num = Bootstrap.getService(Integer.class);
        assertNotNull(num);
        assertTrue(num.equals(5));
    }

    @Test
    public void testGetService1() throws Exception{
        Bootstrap.init(new TestServiceProvider());
        Long num = Bootstrap.getService(Long.class, Long.valueOf(111L));
        assertNotNull(num);
        assertTrue(num.equals(111L));
    }

    private final static class TestServiceProvider implements ServiceProvider{

        @Override
        public <T> List<T> getServices(Class<T> serviceType){
            if(String.class.equals(serviceType)){
                return List.class.cast(Arrays.asList(new String[]{"service1", "service2"}));
            }
            else if(Integer.class.equals(serviceType)){
                return List.class.cast(Arrays.asList(new Integer[]{Integer.valueOf(5)}));
            }
            else if(Long.class.equals(serviceType)){
                return List.class.cast(Arrays.asList(new Long[]{Long.valueOf(111)}));
            }
            return Collections.emptyList();
        }

        @Override
        public <T> List<T> getServices(Class<T> serviceType, List<T> defaultList){
            if(String.class.equals(serviceType)){
                return List.class.cast(Arrays.asList(new String[]{"service1", "service2"}));
            }
            else if(Integer.class.equals(serviceType)){
                return List.class.cast(Arrays.asList(new Integer[]{Integer.valueOf(5)}));
            }
            else if(Long.class.equals(serviceType)){
                return List.class.cast(Arrays.asList(new Long[]{Long.valueOf(111)}));
            }
            return defaultList;
        }
    }
}
