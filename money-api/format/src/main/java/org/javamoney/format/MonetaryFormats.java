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
package org.javamoney.format;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javamoney.format.spi.MonetaryFormatsSingletonSpi;

/**
 * This interface provides access to the formatting logic of JavaMoney. *
 * <p>
 * {@link ItemFormat} instances are not required to be thread-safe. Basically
 * when accessing an {@link ItemFormat} from the {@link MonetaryFormats}
 * singleton a new instance should be created on each access.<br/>
 * This class itself is thread-safe, delegating its calls to the
 * {@link MonetaryFormatsSingletonSpi} registered using the
 * {@link ServiceLoader}.
 * 
 * @author Anatole Tresch
 */
public final class MonetaryFormats {
	/** SPI implementation loaded loaded from ServiceLodaer. */
	private static MonetaryFormatsSingletonSpi monetaryFormatSpi = loadMonetaryFormatSpi();

	/**
	 * Singleton constructor.
	 */
	private MonetaryFormats() {
	}

	/**
	 * Loads the MonetaryFormatSpi the {@link MonetaryFormatsSingletonSpi} used.
	 * 
	 * @return the MonetaryFormatSpi to be used.
	 */
	private static MonetaryFormatsSingletonSpi loadMonetaryFormatSpi() {
		MonetaryFormatsSingletonSpi spi = null;
		try {
			// try loading directly from ServiceLoader
			Iterator<MonetaryFormatsSingletonSpi> instances = ServiceLoader
					.load(MonetaryFormatsSingletonSpi.class).iterator();
			if (instances.hasNext()) {
				spi = instances.next();
				if (instances.hasNext()) {
					throw new IllegalStateException(
							"Ambigous reference to spi (only "
									+ "one can be registered: "
									+ MonetaryFormatsSingletonSpi.class
											.getName());
				}
				return spi;
			}
		} catch (Exception e) {
			Logger.getLogger(MonetaryFormats.class.getName()).log(Level.INFO,
					"No MonetaryFormatSpi found, using  default.", e);
		}
		return new DefaultMonetaryFormatsSpi();
	}

	/**
	 * Return the style id's supported by this {@link ItemFormatterFactorySpi}
	 * instance.
	 * 
	 * @see LocalizationStyle#getId()
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @return the supported style ids, never {@code null}.
	 */
	public static Collection<String> getSupportedStyleIds(Class<?> targetType) {
		Collection<String> styleIDs = monetaryFormatSpi
				.getSupportedStyleIds(targetType);
		if (styleIDs == null) {
			Logger.getLogger(MonetaryFormats.class.getName()).log(
					Level.WARNING,
					"MonetaryFormatSpi.getSupportedStyleIds returned null for "
							+ targetType);
			return Collections.emptySet();
		}
		return styleIDs;
	}

	/**
	 * Method allows to check if a named style is supported.
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param styleId
	 *            The style id.
	 * @return true, if a spi implementation is able to provide an
	 *         {@link ItemFormat} for the given style.
	 */
	public static boolean isSupportedStyle(Class<?> targetType, String styleId) {
		return monetaryFormatSpi.isSupportedStyle(targetType, styleId);
	}

	/**
	 * Access a default {@link LocalizationStyle}.
	 * 
	 * @param targetType
	 *            the target type.
	 * @return the default style, as defined for the targetType.
	 */
	public static LocalizationStyle getLocalizationStyle(Class<?> targetType) {
		return monetaryFormatSpi.getLocalizationStyle(targetType,
				LocalizationStyle.DEFAULT_ID);
	}

	/**
	 * Access the configuring {@link LocalizationStyle},
	 * 
	 * @param targetType
	 *            the target type.
	 * @param styleId
	 *            the style's identifier.
	 * @return the style instance found.
	 */
	public static LocalizationStyle getLocalizationStyle(Class<?> targetType,
			String styleId) {
		return monetaryFormatSpi.getLocalizationStyle(targetType, styleId);
	}

	/**
	 * This method returns an instance of an {@link ItemFormat} .
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param style
	 *            the {@link LocalizationStyle} to be attached to this
	 *            {@link ItemFormat}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes configuring this instance.
	 * @return the formatter required, if available.
	 * @throws ItemFormatException
	 *             if the {@link LocalizationStyle} passed can not be used for
	 *             configuring the {@link ItemFormat} and no matching
	 *             {@link ItemFormat} could be provided.
	 */
	public static <T> ItemFormat<T> getItemFormat(Class<T> targetType)
			throws ItemFormatException {
		LocalizationStyle style = getLocalizationStyle(targetType,
				LocalizationStyle.DEFAULT_ID);
		if (style == null) {
			throw new ItemFormatException("No default style present for "
					+ targetType);
		}
		return getItemFormat(targetType, style);
	}

	/**
	 * This method returns an instance of an {@link ItemFormat} .
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param style
	 *            the {@link LocalizationStyle} to be attached to this
	 *            {@link ItemFormat}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes configuring this instance.
	 * @return the formatter required, if available.
	 * @throws ItemFormatException
	 *             if the {@link LocalizationStyle} passed can not be used for
	 *             configuring the {@link ItemFormat} and no matching
	 *             {@link ItemFormat} could be provided.
	 */
	public static <T> ItemFormat<T> getItemFormat(Class<T> targetType,
			String styleId) throws ItemFormatException {

		return getItemFormat(targetType,
				getLocalizationStyle(targetType, styleId));
	}

	/**
	 * This method returns an instance of an {@link ItemFormat} .
	 * 
	 * @param targetType
	 *            the target type, never {@code null}.
	 * @param style
	 *            the {@link LocalizationStyle} to be attached to this
	 *            {@link ItemFormat}, which also contains the target
	 *            {@link Locale} instances to be used, as well as other
	 *            attributes configuring this instance.
	 * @return the formatter required, if available.
	 * @throws ItemFormatException
	 *             if the {@link LocalizationStyle} passed can not be used for
	 *             configuring the {@link ItemFormat} and no matching
	 *             {@link ItemFormat} could be provided.
	 */
	public static <T> ItemFormat<T> getItemFormat(Class<T> targetType,
			LocalizationStyle style)
			throws ItemFormatException {
		if (style == null) {
			style = LocalizationStyle.of(targetType);
		}
		if (targetType == null) {
			throw new IllegalArgumentException("targetType required.");
		}
		try {
			ItemFormat<T> f = monetaryFormatSpi
					.getItemFormat(targetType, style);
			if (f != null) {
				return f;
			}
			throw new ItemFormatException("No formatter available for "
					+ targetType + " and " + style);
		} catch (Exception e) {
			throw new ItemFormatException("Error accessing formatter for "
					+ targetType + " and " + style, e);
		}
	}

	/**
	 * Default SPI implementation of {@link MonetaryFormatsSingletonSpi}, only
	 * used when no corresponding SPI was registered into {@link ServiceLoader}.
	 * 
	 * @author Anatole Tresch
	 */
	private static final class DefaultMonetaryFormatsSpi implements
			MonetaryFormatsSingletonSpi {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.format.spi.MonetaryFormatsSingletonSpi#getSupportedStyleIds
		 * (java.lang.Class)
		 */
		@Override
		public Collection<String> getSupportedStyleIds(Class<?> targetType) {
			return LocalizationStyle.getSupportedStyleIds(targetType);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.format.spi.MonetaryFormatsSingletonSpi#isSupportedStyle
		 * (java.lang.Class, java.lang.String)
		 */
		@Override
		public boolean isSupportedStyle(Class<?> targetType, String styleId) {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.format.spi.MonetaryFormatsSingletonSpi#getItemFormat(
		 * java.lang.Class, javax.money.format.LocalizationStyle)
		 */
		@Override
		public <T> ItemFormat<T> getItemFormat(Class<T> targetType,
				LocalizationStyle style) throws ItemFormatException {
			throw new ItemFormatException("No MonetaryFormatSpi registered.");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.format.spi.MonetaryFormatsSingletonSpi#getLocalizationStyle
		 * (java.lang.Class, java.lang.String)
		 */
		@Override
		public LocalizationStyle getLocalizationStyle(Class<?> targetType,
				String styleId) {
			return LocalizationStyle.of(targetType, styleId);
		}

	}
}
