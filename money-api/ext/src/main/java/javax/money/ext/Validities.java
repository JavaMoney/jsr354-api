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
package javax.money.ext;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.ext.spi.RelatedValidityProviderSpi;
import javax.money.ext.spi.ValiditiesSingletonSpi;
import javax.money.ext.spi.ValidityProviderSpi;

/**
 * This is the service component for accessing {@link ValidityInfo} and
 * {@link RelatedValidityInfo} instances aka historic items.
 * <p>
 * This class is thread-safe, hereby delegating calls to the
 * {@link ValiditiesSingletonSpi} registered using the {@link ServiceLoader}.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9.1
 */
public final class Validities {

	/**
	 * The spi registered, to which this singleton delegstes all calls.
	 */
	private static final ValiditiesSingletonSpi VALIDITIES_SPI = loadValiditiesSpi();

	/**
	 * Singleton constructor.
	 */
	private Validities() {
	}

	/**
	 * Method that loads the {@link MonetaryConversionsSpi} on class loading.
	 * 
	 * @return the instance or be registered into the shared variable.
	 */
	private static ValiditiesSingletonSpi loadValiditiesSpi() {
		try {
			// try loading directly from ServiceLoader
			Iterator<ValiditiesSingletonSpi> instances = ServiceLoader
					.load(
							ValiditiesSingletonSpi.class).iterator();
			ValiditiesSingletonSpi spiLoaded = null;
			if (instances.hasNext()) {
				spiLoaded = instances.next();
				if (instances.hasNext()) {
					throw new IllegalStateException(
							"Ambigous reference to spi (only "
									+ "one can be registered: "
									+ ValiditiesSingletonSpi.class
											.getName());
				}
				return spiLoaded;
			}
		} catch (Throwable e) {
			Logger.getLogger(ValiditiesSingletonSpi.class.getName())
					.log(
							Level.INFO,
							"No staticValiditiesSingletonSpi registered, using  default.",
							e);
		}
		return new DefaultValiditiesSpi();
	}

	/**
	 * Access the ids of the providers, which deliver related validity data for
	 * the given types.
	 * 
	 * @param type
	 *            The item type
	 * @param relatedToType
	 *            The instance defining the relation
	 * @return the according {@link RelatedValidityInfo} provider ids, never
	 *         {@code null}.
	 */
	public static <T, R> Set<String> getRelatedValidityProviderIds(
			Class<T> type,
			Class<R> relatedToType) {
		return VALIDITIES_SPI
				.getRelatedValidityProviderIds(type, relatedToType);
	}

	/**
	 * Access the related types that are supported by the currently registered
	 * {@link RelatedValidityProviderSpi} instances.
	 * 
	 * @see RelatedValidityProviderSpi#getRelatedItemTypes(Class)
	 * @see RelatedValidityQuery#getRelatedToType()
	 * @param type
	 *            The item type
	 * @return the according relatedTo types, never {@code null}.
	 */
	public static <T> Set<Class> getRelatedValidityRelationTypes(Class<T> type) {
		return VALIDITIES_SPI.getRelatedValidityRelationTypes(type);
	}

	/**
	 * Access the related types that are supported by the currently registered
	 * {@link RelatedValidityProviderSpi} instances.
	 * 
	 * @see RelatedValidityProviderSpi#getItemTypes()
	 * @see RelatedValidityQuery#getItemType()
	 * @return the according relatedTo types, never {@code null}.
	 */
	public static <T> Set<Class> getRelatedValidityItemTypes() {
		return VALIDITIES_SPI.getRelatedValidityItemTypes();
	}

	/**
	 * Access the item types that are supported by the currently registered
	 * {@link ValidityProviderSpi} instances.
	 * 
	 * @see ValidityProviderSpi#getItemTypes()
	 * @see ValidityQuery#getItemType()
	 * @return the according relatedTo types, never {@code null}.
	 */
	public static <T> Set<Class> getValidityItemTypes() {
		return VALIDITIES_SPI.getValidityItemTypes();
	}

	/**
	 * Access the ids of the providers, which deliver validity data for the
	 * given type.
	 * 
	 * @param type
	 *            The item type
	 * @return the according {@link ValidityInfo} provider ids, never
	 *         {@code null}.
	 */
	public static <T> Set<String> getValidityProviderIds(Class<T> type) {
		return VALIDITIES_SPI.getValidityProviderIds(type);
	}

	/**
	 * Access the supported {@link ValidityType} instances for a given item
	 * type.
	 * 
	 * @param type
	 *            The item type
	 * @return the current {@link ValidityType} supported, never {@code null}.
	 */
	public static <T> Set<ValidityType> getValidityTypes(Class<T> type) {
		return VALIDITIES_SPI.getValidityTypes(type);
	}

	/**
	 * Access the supported {@link ValidityType} instances for a given item type
	 * and related type, used for {@link RelatedValidityQuery}..
	 * 
	 * @param type
	 *            The item type
	 * @param relationType
	 *            the relation type
	 * @return the current {@link ValidityType} supported, never {@code null}.
	 */
	public static <T, R> Set<ValidityType> getValidityTypes(Class<T> type,
			Class<R> relationType) {
		return VALIDITIES_SPI.getRelatedValidityTypes(type, relationType);
	}

	/**
	 * Access all {@link RelatedValidityInfo} for the given {@code item} related
	 * to the {@code relatedType}.
	 * 
	 * @param query
	 *            the query that defines which validity information should be
	 *            returned.
	 * @return the {@link RelatedValidityInfo} instances matching, never
	 *         {@code null}.
	 */
	public static <T, R> Collection<RelatedValidityInfo<T, R>> getRelatedValidityInfo(
			RelatedValidityQuery<T, R> query) {
		return VALIDITIES_SPI.getRelatedValidityInfo(query);
	}

	/**
	 * Access all currencies matching a {@link Region}, valid at the given
	 * timestamp.
	 * 
	 * @param query
	 *            the query that defines which {@link ValidityInfo} should be
	 *            returned.
	 * @return the {@link ValidityInfo} instances matching, never {@code null}.
	 */
	public static <T> Collection<ValidityInfo<T>> getValidityInfo(
			ValidityQuery<T> query) {
		return VALIDITIES_SPI.getValidityInfo(query);
	}

	/**
	 * This is the default {@link ValiditiesSingletonSpi} implementation used,
	 * if no {@link ValiditiesSingletonSpi} instance was registered using the
	 * {@link ServiceLoader}.
	 * 
	 * @author Anatole Tresch
	 */
	private static final class DefaultValiditiesSpi implements
			ValiditiesSingletonSpi {
		/** info message logged on creation. */
		private static final String INFO_MESSAGE = "No "
				+ ValiditiesSingletonSpi.class.getName()
				+ " registered.";

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.ValiditiesSingletonSpi#getRelatedValiditySources
		 * (java.lang.Class, java.lang.Class)
		 */
		@Override
		public <T, R> Set<String> getRelatedValidityProviderIds(Class<T> type,
				Class<R> relatedType) {
			return Collections.emptySet();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.ValiditiesSingletonSpi#getValiditySources(java
		 * .lang.Class)
		 */
		@Override
		public <T> Set<String> getValidityProviderIds(Class<T> type) {
			return Collections.emptySet();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.ValiditiesSingletonSpi#getRelatedValidityInfo
		 * (javax.money.ext.RelatedValidityQuery)
		 */
		@Override
		public <T, R> Collection<RelatedValidityInfo<T, R>> getRelatedValidityInfo(
				RelatedValidityQuery<T, R> query) {
			return Collections.emptySet();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.ValiditiesSingletonSpi#getValidityInfo(javax.
		 * money.ext.ValidityQuery)
		 */
		@Override
		public <T> Collection<ValidityInfo<T>> getValidityInfo(
				ValidityQuery<T> query) {
			return Collections.emptySet();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.ext.spi.ValiditiesSingletonSpi#
		 * getRelatedValidityRelationTypes(java.lang.Class)
		 */
		@Override
		public <T> Set<Class> getRelatedValidityRelationTypes(Class<T> type) {
			return Collections.emptySet();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.ext.spi.ValiditiesSingletonSpi#
		 * getRelatedValidityTypes(java.lang.Class)
		 */
		@Override
		public <T, R> Set<ValidityType> getRelatedValidityTypes(Class<T> type,
				Class<R> relatedType) {
			return Collections.emptySet();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.ext.spi.ValiditiesSingletonSpi#
		 * getValidityTypes(java.lang.Class)
		 */
		@Override
		public <T> Set<ValidityType> getValidityTypes(Class<T> type) {
			return Collections.emptySet();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.ValiditiesSingletonSpi#getRelatedValidityItemTypes
		 * ()
		 */
		@Override
		public Set<Class> getRelatedValidityItemTypes() {
			return Collections.emptySet();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.spi.ValiditiesSingletonSpi#getValidityItemTypes()
		 */
		@Override
		public Set<Class> getValidityItemTypes() {
			return Collections.emptySet();
		}
	}

}
