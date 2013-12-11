/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money.bootstrap;

import java.util.Collection;

/**
 * This singleton provides access to the services available in the current
 * context. The behaviour can be adapted, by calling
 * {@link Bootstrap#init(ServiceProvider)} before accessing any
 * moneteray services.
 * 
 * @author Anatole Tresch
 */
public final class Bootstrap {

	private static volatile ServiceProvider services;
	private static final Object LOCK = new Object();

	public Bootstrap() {
	}

	private static ServiceProvider loadDefaultServiceProvider() {
		// TODO Implement more flexible variant...
		return new DefaultServiceProvider();
	}

	public static void init(ServiceProvider services) {
		synchronized (LOCK) {
			if (services == null) {
				Bootstrap.services = services;
			}
			else {
				throw new IllegalStateException(
						"Services are already initialized.");
			}
		}
	}

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

	public static <T> Collection<T> getServices(Class<T> serviceType) {
		return getServiceProvider().getServices(serviceType);
	}
	
	public static <T> Collection<T> getServices(Class<T> serviceType, Collection<T> defaultServices) {
		return getServiceProvider().getServices(serviceType, defaultServices);
	}
	
	public static <T> T getService(Class<T> serviceType) {
		return getServiceProvider().getService(serviceType);
	}
	
	public static <T> T getService(Class<T> serviceType, T defaultService) {
		return getServiceProvider().getService(serviceType, defaultService);
	}

}
