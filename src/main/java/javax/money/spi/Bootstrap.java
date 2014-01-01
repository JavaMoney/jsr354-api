/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Logger;

/**
 * This singleton provides access to the services available in the current
 * context. The behaviour can be adapted, by calling
 * {@link Bootstrap#init(ServiceProvider)} before accessing any moneteray
 * services.
 * 
 * @author Anatole Tresch
 */
public final class Bootstrap {
	/** The ServiceProvider used. */
	private static volatile ServiceProvider services;
	/** The shared lock instance user. */
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
			List<ServiceProvider> providers = new ArrayList<>();
			for (ServiceProvider sp : ServiceLoader.load(ServiceProvider.class)) {
				providers.add(sp);
			}
			Collections.sort(providers, new ProviderComparator());
			return providers.get(0);
		} catch (Exception e) {
			Logger.getLogger(Bootstrap.class.getName()).info(
					"No ServiceProvider loaded, using default.");
			return new DefaultServiceProvider();
		}
	}

	/**
	 * Replace the current {@link ServiceProvider} in use.
	 * 
	 * @param serviceProvider
	 *            the new {@link ServiceProvider}
	 */
	public static void init(ServiceProvider serviceProvider) {
		synchronized (LOCK) {
			if (serviceProvider == null) {
				Bootstrap.services = serviceProvider;
			}
			else {
				throw new IllegalStateException(
						"Services are already initialized.");
			}
		}
	}

	/**
	 * Ge {@link ServiceProvider}. If necessary the {@link ServiceProvider} will
	 * be laziliy loaded.
	 * 
	 * @return the {@link ServiceProvider} used.
	 */
	static ServiceProvider getServiceProvider() {
		if (services == null) {
			synchronized (LOCK) {
				if (services == null) {
					services = loadDefaultServiceProvider();
				}
			}
		}
		return services;
	}

	/**
	 * Delegate method for {@link ServiceProvider#getServices(Class)}.
	 * 
	 * @see ServiceProvider#getServices(Class)
	 * @param serviceType
	 *            the service type.
	 * @return the services found.
	 */
	public static <T> Collection<T> getServices(Class<T> serviceType) {
		return getServiceProvider().getServices(serviceType);
	}

	/**
	 * Delegate method for {@link ServiceProvider#getServices(Class)}.
	 * 
	 * @see ServiceProvider#getServices(Class, Collection)
	 * @param serviceType
	 *            the service type.
	 * @param defaultServices
	 *            the default service list.
	 * @return the services found.
	 */
	public static <T> Collection<T> getServices(Class<T> serviceType,
			Collection<T> defaultServices) {
		return getServiceProvider().getServices(serviceType, defaultServices);
	}

	/**
	 * Delegate method for {@link ServiceProvider#getService(Class)}.
	 * 
	 * @see ServiceProvider#getService(Class)
	 * @param serviceType
	 *            the service type.
	 * @return the service found, never {@code null}.
	 */
	public static <T> T getService(Class<T> serviceType) {
		return getServiceProvider().getService(serviceType);
	}

	/**
	 * Delegate method for {@link ServiceProvider#getService(Class)}.
	 * 
	 * @see ServiceProvider#getService(Class, Object)
	 * @param serviceType
	 *            the service type.
	 * @param defaultService
	 *            returned if no service was found.
	 * @return the service found, only {@code null}, if no service was found and
	 *         {@code defaultService==null}.
	 */
	public static <T> T getService(Class<T> serviceType, T defaultService) {
		return getServiceProvider().getService(serviceType, defaultService);
	}

	/**
	 * Comparator used for ordering the services provided.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class ProviderComparator implements
			Comparator<Object> {
		@Override
		public int compare(Object p1, Object p2) {
			ServicePriority prioAnnot = p1.getClass().getAnnotation(ServicePriority.class);
			int prio1 = 0;
			if (prioAnnot != null) {
				prio1 = prioAnnot.value();
			}
			prioAnnot = p2.getClass().getAnnotation(ServicePriority.class);
			int prio2 = 0;
			if (prioAnnot != null) {
				prio2 = prioAnnot.value();
			}
			return prio2 - prio1;
		}
	}

}