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
package javax.money.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the {@link ServiceProvider} interface and hereby uses
 * the JDK {@link ServiceLoader} to load the services required.
 * 
 * @author Anatole Tresch
 */
public class DefaultServiceProvider implements ServiceProvider {
	/** List of services loaded, per class. */
	@SuppressWarnings("rawtypes")
	private Map<Class, List<Object>> servicesLoaded = new ConcurrentHashMap<>();

	/**
	 * Access the given service by type.
	 * 
	 * @param serviceType
	 *            the service type.
	 * @param <T>
	 *            the concrete type.
	 * @return the required service, or {@code null}.
	 */
	@Override
	public <T> T getService(Class<T> serviceType) {
		return getService(serviceType, null);
	}

	/**
	 * Access the given service by type.
	 * 
	 * @param serviceType
	 *            the service type.
	 * @param <T>
	 *            the concrete type.
	 * @param defaultInstance
	 *            The instance to be returned, if no service is registered, also
	 *            {@code null} is a valid default value.
	 * @return the required service, or {@code defaultInstance}.
	 */
	@Override
	public <T> T getService(Class<T> serviceType, T defaultInstance) {
		Collection<T> services = getServices(serviceType);
		if (services.isEmpty()) {
			return defaultInstance;
		}
		return services.iterator().next();
	}

	/**
	 * Access all services available by type.
	 * 
	 * @param serviceType
	 *            the service type.
	 * @param <T>
	 *            the concrete type.
	 * @return all services available, never {@code null}.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> getServices(Class<T> serviceType) {
		return getServices(serviceType, (Collection<T>) Collections.emptyList());
	}

	/**
	 * Access all services available by type.
	 * 
	 * @param serviceType
	 *            the service type.
	 * @param <T>
	 *            the concrete type.
	 * @param defaultList
	 *            the list of items returned, if no services were found.
	 * @return all services available, never {@code defaultList}.
	 */
	@Override
	public <T> Collection<T> getServices(Class<T> serviceType,
			Collection<T> defaultList) {
		@SuppressWarnings("unchecked")
		List<T> found = (List<T>) servicesLoaded.get(serviceType);
		if (found != null) {
			return found;
		}
		return loadServices(serviceType, defaultList);
	}

	/**
	 * Loads and registers services.
	 * 
	 * @param serviceType
	 *            The service type.
	 * @param <T>
	 *            the concrete type.
	 * @param defaultList
	 *            the list of items returned, if no services were found.
	 * @return the items found, never {@code null}.
	 */
	@SuppressWarnings("unchecked")
	public <T> Collection<T> loadServices(Class<T> serviceType,
			Collection<T> defaultList) {
		List<T> found = null;
		synchronized (servicesLoaded) {
			found = (List<T>) servicesLoaded.get(serviceType);
			if (found != null) {
				return found;
			}
			found = new ArrayList<>();
			servicesLoaded.put(serviceType, (List<Object>) found);
		}
		try {
			for (T t : ServiceLoader.load(serviceType)) {
				found.add(t);
			}
		} catch (Exception e) {
			Logger.getLogger(DefaultServiceProvider.class.getName()).log(
					Level.WARNING,
					"Error loading services of type " + serviceType, e);
		}
		return found;
	}
}