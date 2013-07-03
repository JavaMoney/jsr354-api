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
package javax.money.ext;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.CurrencyUnit;

/**
 * This singleton defines access to the exchange and currency conversion logic
 * of JavaMoney.
 * 
 * @author Anatole Tresch
 */
public final class MonetaryRegions {

    /**
     * The spi currently active, use {@link ServiceLoader} to register an
     * alternate implementation.
     */
    private static final MonetaryRegionsSpi MONETARY_REGION_SPI = loadMonetaryRegionSpi();

    /**
     * Private singleton constructor.
     */
    private MonetaryRegions() {
    }

    /**
     * Get a set of region providers registered.
     * 
     * @return the ids of the registered region providers.
     */
    public static Set<String> getRegionProviders() {
	return MONETARY_REGION_SPI.getRegionProviders();
    }

    /**
     * Access all a region info, determined by the given provider.
     * 
     * @param provider
     *            the region provider. * @return all {@link Region}s available
     *            without a parent, never null.
     */
    public static RegionInfo getRegionInfo(String provider) {
	return MONETARY_REGION_SPI.getRegionInfo(provider);
    }

    /**
     * This is the spi interface to be implemented that determines how the
     * different components are loaded and managed.
     * 
     * @author Anatole Tresch
     */
    public static interface MonetaryRegionsSpi {

	/**
	 * Get a set of region providers registered.
	 * 
	 * @return the ids of the registered region providers.
	 */
	public Set<String> getRegionProviders();

	/**
	 * Access all a region info, determined by the given provider.
	 * 
	 * @param provider
	 *            the region provider. * @return all {@link Region}s
	 *            available without a parent, never null.
	 */
	public RegionInfo getRegionInfo(String provider);

    }

    /**
     * Method that loads the {@link MonetaryConversionSpi} on class loading.
     * 
     * @return the instance ot be registered into the shared variable.
     */
    private static MonetaryRegionsSpi loadMonetaryRegionSpi() {
	try {
	    // try loading directly from ServiceLoader
	    Iterator<MonetaryRegionsSpi> instances = ServiceLoader.load(MonetaryRegionsSpi.class).iterator();
	    if (instances.hasNext()) {
		return instances.next();
	    }
	} catch (Throwable e) {
	    Logger.getLogger(MonetaryRegionsSpi.class.getName()).log(Level.INFO,
		    "No MonetaryRegionSpi registered, using  default.", e);
	}
	return new DefaultMonetaryRegionsSpi();
    }

    /**
     * This class represents the default implementation of
     * {@link MonetaryConversionSpi} used always when no alternative is
     * registered within the {@link ServiceLoader}.
     * 
     * @author Anatole Tresch
     * 
     */
    private final static class DefaultMonetaryRegionsSpi implements MonetaryRegionsSpi {

	@Override
	public Set<String> getRegionProviders() {
	    return Collections.emptySet();
	}

	@Override
	public RegionInfo getRegionInfo(String provider) {
	    return null;
	}
	
    }

}
