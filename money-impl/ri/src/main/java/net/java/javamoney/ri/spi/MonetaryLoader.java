/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package net.java.javamoney.ri.spi;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the main accessor component for Java Money. Is is responsible for
 * loading the API top level providers using the {@link ServiceLoader}:
 * <ul>
 * <li>{@code javax.money.convert.ConversionProvider}</li>
 * <li>{@code javax.money.format.ItemFormatterFactory}</li>
 * <li>{@code javax.money.format.ItemParserFactory}</li>
 * <li>{@code javax.money.provider.CurrencyUnitProvider}</li>
 * <li>{@code javax.money.provider.HistoricCurrencyUnitProvider}</li>
 * <li>{@code javax.money.provider.RoundingProvider}</li>
 * </ul>
 * 
 * Additionally it is also responsible for loading the
 * {@link AnnotationServiceSpi} support SPI component, which provides annotation
 * lookup/search functionality.
 * <ul>
 * <li>{@code javax.money.provider.spi.AnnotationServiceSpi}</li>
 * </ul>
 * 
 * The top level API implementations then are required to load the annotated
 * interfaces automatically.<br/>
 * The SPIs supported are determined by the different modules. JSR 354 includes
 * the following spi packages:
 * <ul>
 * <li>{@code javax.money.convert.spi}</li>
 * <li>{@code javax.money.format.spi}</li>
 * <li>{@code javax.money.provider.spi}</li>
 * <li>{@code javax.money.ext.spi}</li>
 * </ul>
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9
 * 
 */
public final class MonetaryLoader {
	private static final Logger LOGGER = Logger.getLogger(MonetaryLoader.class
			.getName());

	private static final ComponentLoader LOADER = initLoader();


	/**
	 * Singleton constructor.
	 */
	private MonetaryLoader() {
	}

	private static ComponentLoader initLoader() {
		ComponentLoader loader = null;
		try {
			// try loading directly from ServiceLoader
			Iterator<ComponentLoader> loaders = ServiceLoader.load(
					ComponentLoader.class).iterator();
			if (loaders.hasNext()) {
				loader = loaders.next();
				loader.init();
				return loader;
			}
		} catch (Exception e) {
			LOGGER.log(Level.INFO,
					"No ComponentLoader found, using ServiceLoader default.", e);
		}
		return new DefaultServiceLoader();
	}


	/**
	 * Access the {@link MonetaryLoader} that is used by this accessor
	 * instance.
	 * 
	 * @return the loder in use, never null.
	 */
	public static ComponentLoader getLoader() {
		return LOADER;
	}

	/**
	 * This is the loader that is used to load the different providers and spi
	 * to be used by {@link Monetary} and its services. The
	 * {@link MonetaryLoader} can also be accessed from the {@link Monetary}
	 * singleton, so it can also be used by the monetary service
	 * implementations.
	 * 
	 * @author Anatole Tresch
	 */
	public static interface ComponentLoader {

		/**
		 * Method to initialize the component loader instance.
		 */
		public void init();

		/**
		 * Access a singleton instance.
		 * 
		 * @param type
		 *            The target type.
		 * @param annotations
		 *            The annotations that must be present on the type.
		 * @return the instance found, or null.
		 * @throws IllegalStateException
		 *             , when the instances are ambiguous.
		 */
		@SuppressWarnings("unchecked")
		public <T> T getComponent(Class<T> type,
				Class<? extends Annotation>... annotations);

		/**
		 * Access a list of instances.
		 * 
		 * @param type
		 *            The target type.
		 * @param annotations
		 *            The annotations that must be present on the types.
		 * @return the instances matching, never null.
		 */
		@SuppressWarnings("unchecked")
		public <T> List<T> getComponents(Class<T> type,
				Class<? extends Annotation>... annotations);

	}

	public final static class DefaultServiceLoader implements ComponentLoader {

		@Override
		public void init() {
			// Nothing todo here
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getComponent(Class<T> type,
				Class<? extends Annotation>... annotations) {
			List<T> instancesFound = getComponents(type, annotations);
			if (instancesFound.isEmpty()) {
				return null;
			} else if (instancesFound.size() == 1) {
				return instancesFound.get(0);
			} else {
				return resolveAmbigousComponents(instancesFound);
			}
		}

		protected <T> T resolveAmbigousComponents(List<T> instancesFound) {
			// or throw exception!
			return instancesFound.get(0);

		}

		protected boolean annotationsMatch(Object comp,
				Class<? extends Annotation>[] annotations) {
			if (annotations == null) {
				return true;
			}
			for (Class<? extends Annotation> annotType : annotations) {
				if (comp.getClass().getAnnotation(annotType) == null) {
					return false;
				}
			}
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> List<T> getComponents(Class<T> type,
				Class<? extends Annotation>... annotations) {
			List<T> instancesFound = new ArrayList<T>();
			ServiceLoader<T> components = ServiceLoader.load(type);
			for (T comp : components) {
				if (annotationsMatch(comp, annotations)) {
					instancesFound.add((T) comp);
				}
			}
			sortComponents(instancesFound);
			return instancesFound;
		}

		protected void sortComponents(List<?> list) {
		}

	}

}
