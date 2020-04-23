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
        
        private final Map<Class<?>, List<?>> services = new HashMap<>();

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
