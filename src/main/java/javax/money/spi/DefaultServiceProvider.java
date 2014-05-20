/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the (default) {@link ServiceProvider} interface and hereby uses the JDK
 * {@link ServiceLoader} to load the services required.
 * 
 * @author Anatole Tresch
 */
public class DefaultServiceProvider implements ServiceProvider {
	/** List of services loaded, per class. */
	@SuppressWarnings("rawtypes")
	private Map<Class, List<Object>> servicesLoaded = new ConcurrentHashMap<>();


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
	public <T> List<T> getServices(Class<T> serviceType,
			List<T> defaultList) {
		@SuppressWarnings("unchecked")
		List<T> found = (List<T>) servicesLoaded.get(serviceType);
		if (found != null) {
			return found;
		}
		return loadServices(serviceType);
	}

	/**
	 * Loads and registers services.
	 * 
	 * @param serviceType
	 *            The service type.
	 * @return the items found, never {@code null}.
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> loadServices(Class<T> serviceType) {
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

	/**
	 * Comparator used for ordering the services provided.
	 * 
	 * @author Anatole Tresch
	 */
	public static final class ProviderComparator implements
			Comparator<Object> {
		@Override
		public int compare(Object p1, Object p2) {
			return p1.getClass().getSimpleName()
					.compareTo(p2.getClass().getSimpleName());
		}
	}

}