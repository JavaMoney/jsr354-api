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

	private Map<Class, List<Object>> servicesLoaded = new ConcurrentHashMap<>();

	@Override
	public <T> T getService(Class<T> serviceType) {
		return getService(serviceType, null);
	}

	@Override
	public <T> T getService(Class<T> serviceType, T defaultInstance) {
		Collection<T> services = getServices(serviceType);
		if (services.isEmpty()) {
			return defaultInstance;
		}
		return services.iterator().next();
	}

	@Override
	public <T> Collection<T> getServices(Class<T> serviceType) {
		return getServices(serviceType, (Collection<T>) Collections.emptyList());
	}

	@Override
	public <T> Collection<T> getServices(Class<T> serviceType,
			Collection<T> defaultList) {
		List<T> found = (List<T>) servicesLoaded.get(serviceType);
		if (found != null) {
			return found;
		}
		return loadServices(serviceType, defaultList);
	}

	public <T> Collection<T> loadServices(Class<T> serviceType, Collection<T> defaultList) {
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
