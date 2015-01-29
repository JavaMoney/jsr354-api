/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import javax.money.MonetaryException;

/**
 * This singleton provides access to the services available in the current runtime environment and context. The
 * behaviour can be adapted, by calling {@link Bootstrap#init(ServiceProvider)} before accessing any moneteray
 * services.
 *
 * @author Anatole Tresch
 */
public final class Bootstrap {
    /**
     * The ServiceProvider used.
     */
    private static volatile ServiceProvider serviceProviderDelegate;
    /**
     * The shared lock instance user.
     */
    private static final Object LOCK = new Object();

    /**
     * Private singletons constructor.
     */
    private Bootstrap() {
    }

    /**
     * Load the {@link ServiceProvider} to be used.
     *
     * @return {@link ServiceProvider} to be used for loading the services.
     */
    private static ServiceProvider loadDefaultServiceProvider() {
        try {
            for (ServiceProvider sp : ServiceLoader.load(ServiceProvider.class)) {
                return sp;
            }
        } catch (Exception e) {
            Logger.getLogger(Bootstrap.class.getName()).info("No ServiceProvider loaded, using default.");
        }
        return new DefaultServiceProvider();
    }

    /**
     * Replace the current {@link ServiceProvider} in use.
     *
     * @param serviceProvider the new {@link ServiceProvider}
     * @return the removed , or null.
     */
    public static ServiceProvider init(ServiceProvider serviceProvider) {
        Objects.requireNonNull(serviceProvider);
        synchronized (LOCK) {
            if (Objects.isNull(Bootstrap.serviceProviderDelegate)) {
                Bootstrap.serviceProviderDelegate = serviceProvider;
                Logger.getLogger(Bootstrap.class.getName())
                        .info("Money Bootstrap: new ServiceProvider setTyped: " + serviceProvider.getClass().getName());
                return null;
            } else {
                ServiceProvider prevProvider = Bootstrap.serviceProviderDelegate;
                Bootstrap.serviceProviderDelegate = serviceProvider;
                Logger.getLogger(Bootstrap.class.getName())
                        .warning("Money Bootstrap: ServiceProvider replaced: " + serviceProvider.getClass().getName());
                return prevProvider;
            }
        }
    }

    /**
     * Ge {@link ServiceProvider}. If necessary the {@link ServiceProvider} will be laziliy loaded.
     *
     * @return the {@link ServiceProvider} used.
     */
    static ServiceProvider getServiceProvider() {
        if (Objects.isNull(serviceProviderDelegate)) {
            synchronized (LOCK) {
                if (Objects.isNull(serviceProviderDelegate)) {
                    serviceProviderDelegate = loadDefaultServiceProvider();
                }
            }
        }
        return serviceProviderDelegate;
    }

    /**
     * Delegate method for {@link ServiceProvider#getServices(Class)}.
     *
     * @param serviceType the service type.
     * @return the services found.
     * @see ServiceProvider#getServices(Class)
     */
    public static <T> Collection<T> getServices(Class<T> serviceType) {
        return getServiceProvider().getServices(serviceType);
    }

    /**
     * Delegate method for {@link ServiceProvider#getServices(Class)}.
     *
     * @param serviceType     the service type.
     * @param defaultServices the default service list.
     * @return the services found.
     * @see ServiceProvider#getServices(Class, List)
     */
    public static <T> List<T> getServices(Class<T> serviceType, List<T> defaultServices) {
        return getServiceProvider().getServices(serviceType, defaultServices);
    }

    /**
     * Delegate method for {@link ServiceProvider#getServices(Class)}.
     *
     * @param serviceType the service type.
     * @return the service found, never {@code null}.
     * @see ServiceProvider#getServices(Class)
     */
    public static <T> T getService(Class<T> serviceType) {
        List<T> services = getServiceProvider().getServices(serviceType);
        if (services.isEmpty()) {
            throw new MonetaryException("No such service found: " + serviceType);
        }
        return services.get(0);
    }

    /**
     * Delegate method for {@link ServiceProvider#getServices(Class)}.
     *
     * @param serviceType    the service type.
     * @param defaultService returned if no service was found.
     * @return the service found, only {@code null}, if no service was found and
     * {@code defaultService==null}.
     * @see ServiceProvider#getServices(Class, List)
     */
    public static <T> T getService(Class<T> serviceType, T defaultService) {
        List<T> services = getServiceProvider().getServices(serviceType);
        if (services.isEmpty()) {
            return defaultService;
        }
        return services.get(0);
    }

}