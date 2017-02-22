/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.money.spi.Bootstrap;
import javax.money.spi.CurrencyProviderSpi;
import javax.money.spi.ServiceProvider;

import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Abstract test supporting to switch SPI implementations by using a ServiceProvider providing manually registered SPIs only. 
 * @author Matthias Hanisch
 */
public abstract class AbstractDynamicServiceProviderTest {

    private ServiceProvider originalServiceProvider;
    private TestServiceProvider testServiceProvider;
    
    /**
     * Initialized Bootstrap so that default ServiceProvider is enabled. Then stores the default ServiceProvider in
     * {@link #originalServiceProvider}. Initializes Bootstrap with default ServiceProvider again so that it is
     * usable in the Test. Also initialized {@link #testServiceProvider}.
     */
    @BeforeMethod
    public void prepare() {
        Bootstrap.getService(CurrencyProviderSpi.class);
        ServiceProvider empty = Mockito.mock(ServiceProvider.class);
        originalServiceProvider= Bootstrap.init(empty);
        Bootstrap.init(originalServiceProvider);
        testServiceProvider = new TestServiceProvider();
    }
    
    /**
     * Restores the default ServiceProvider.
     */
    @AfterMethod
    public void restore() {
        testServiceProvider.clearServices();
        Bootstrap.init(originalServiceProvider);
    }
    
    protected final void initTestServiceProvider() {
        Bootstrap.init(testServiceProvider);
    }
    
    protected final void initOriginalServiceProvider() {
        Bootstrap.init(originalServiceProvider);
    }

    /**
     * Registers a SPI service so that it is accessible via {@link Bootstrap#getService(Class)} when using {@link #testServiceProvider}.
     * @param serviceType The SPI type.
     * @param service The SPI instance.
     */
    protected final <T> void registerService(Class<T> serviceType, T service) {
        testServiceProvider.registerService(serviceType, service);
    }
    
    class TestServiceProvider implements ServiceProvider {
        
        private Map<Class<?>, List<?>> services = new HashMap<>();

        @Override
        public int getPriority() {
            return 0;
        }

        @Override
        public <T> List<T> getServices(Class<T> serviceType) {
            return (List<T>) services.get(serviceType);
        }

        @Override
        public <T> T getService(Class<T> serviceType) {
            List<T> servicesOfType = getServices(serviceType);
            if(servicesOfType==null||servicesOfType.isEmpty()) {
                return null;
            } else {
                return servicesOfType.get(0);
            }
        }
        
        public <T> void registerService(Class<T> serviceType, T service) {
            List<T> servicesOfType = (List<T>) services.get(serviceType);
            if(servicesOfType==null) {
                servicesOfType = new ArrayList<>();
                services.put(serviceType, servicesOfType);
            }
            servicesOfType.add(service);
        }
        
        public void clearServices() {
            services.clear();
        }
    }
}
