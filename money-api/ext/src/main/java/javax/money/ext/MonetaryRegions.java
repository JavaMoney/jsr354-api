/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE 
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. 
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY 
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE 
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" 
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
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
     * Access all regions available, that have no parent region. It is possible
     * to define different regional hierarchies at the same time, whereas the
     * ids of the root regions must be unique among all root regions
     *
     * @return all {@link Region}s available without a parent, never null.
     */
    public static Collection<Region> getRootRegions() {
        return MONETARY_REGION_SPI.getRootRegions();
    }

    /**
     * Since ids of root regions must be unique a regional tree can be accessed
     * using this id.
     *
     * @see #getRootRegions()
     * @param id the root region's id
     * @return the root region, or null.
     */
    public static Region getRootRegion(String id) {
        return MONETARY_REGION_SPI.getRootRegion(id);
    }

    /**
     * Access all currencies matching a {@link Region}.
     *
     * @param locale the target locale, not null.
     * @return the currencies found, never null.
     */
    public static Set<CurrencyUnit> getAll(Region region) {
        return MONETARY_REGION_SPI.getAll(region, null);
    }

    /**
     * Access all currencies matching a {@link Region}, valid at the given
     * timestamp.
     *
     * @param locale the target locale, not null.
     * @param timestamp The target UTC timestamp, or -1 for the current UTC
     * timestamp.
     * @return the currencies found, never null.
     */
    public static Set<CurrencyUnit> getAll(Region region, Long timestamp) {
        return MONETARY_REGION_SPI.getAll(region, timestamp);
    }

    /**
     * This method allows to evaluate if a {@link CurrencyUnit} is a legal
     * tender for a certain region, or country. For example Indian rupee are
     * accepted also as legal tender in Nepal and Buthan, whereas Nepalese rupee
     * or Bhutanese ngultrum are not accepted as legal tender in India.
     *
     * @param currency The currency to be requested, not null.
     * @param region The region to be requested, not null.
     * @return true if the currency is accepted as legal tender in the current
     * region.
     */
    public static boolean isLegalCurrencyUnit(CurrencyUnit currency,
            Region region) {
        return MONETARY_REGION_SPI.isLegalCurrencyUnit(currency, region, null);
    }

    /**
     * This method allows to evaluate if a {@link CurrencyUnit} is a legal
     * tender for a certain region, or country. For example Indian rupee are
     * accepted also as legal tender in Nepal and Buthan, whereas Nepalese rupee
     * or Bhutanese ngultrum are not accepted as legal tender in India.
     *
     * @param currency The currency to be requested, not null.
     * @param region The region to be requested, not null.
     * @param timestamp the UTC timestamp, or null for the current time.
     * @return true if the currency is accepted as legal tender in the current
     * region.
     */
    public static boolean isLegalCurrencyUnit(CurrencyUnit currency,
            Region region, long timestamp) {
        return MONETARY_REGION_SPI.isLegalCurrencyUnit(currency, region,
                timestamp);
    }

    /**
     * This method allows to evaluate the {@link CurrencyUnit} accepted as legal
     * tenders for a {@link Region}.
     *
     * @param region The region to be requested, not null.
     * @return the {@link CurrencyUnit} to be known as legal tenders for the
     * given region, never null.
     */
    public static Set<CurrencyUnit> getLegalCurrencyUnits(Region region) {
        return MONETARY_REGION_SPI.getLegalCurrencyUnits(region, null);
    }

    /**
     * This method allows to evaluate the {@link CurrencyUnit} accepted as legal
     * tenders for a {@link Region}.
     *
     * @param region The region to be requested, not null.
     * @param timestamp the UTC timestamp, or null for the current time.
     * @return the {@link CurrencyUnit} to be known as legal tenders for the
     * given region, never null.
     */
    public static Set<CurrencyUnit> getLegalCurrencyUnits(Region region,
            Long timestamp) {
        return MONETARY_REGION_SPI.getLegalCurrencyUnits(region, timestamp);
    }

    /**
     * This is the spi interface to be implemented that determines how the
     * different components are loaded and managed.
     *
     * @author Anatole Tresch
     */
    public static interface MonetaryRegionsSpi {

        /**
         * Access all regions available, that have no parent region. It is
         * possible to define different regional hierarchies at the same time,
         * whereas the ids of the root regions must be unique among all root
         * regions
         *
         * @return all {@link Region}s available without a parent, never null.
         */
        public Collection<Region> getRootRegions();

        /**
         * Since ids of root regions must be unique a regional tree can be
         * accessed using this id.
         *
         * @see #getRootRegions()
         * @param id the root region's id
         * @return the root region, or null.
         */
        public Region getRootRegion(String id);

        /**
         * Access all currencies matching a {@link Region}, valid at the given
         * timestamp.
         *
         * @param locale the target locale, not null.
         * @param timestamp The target UTC timestamp, or -1 for the current UTC
         * timestamp.
         * @return the currencies found, never null.
         */
        public Set<CurrencyUnit> getAll(Region region, Long timestamp);

        /**
         * This method allows to evaluate if a {@link CurrencyUnit} is a legal
         * tender for a certain region, or country. For example Indian rupee are
         * accepted also as legal tender in Nepal and Buthan, whereas Nepalese
         * rupee or Bhutanese ngultrum are not accepted as legal tender in
         * India.
         *
         * @param currency The currency to be requested, not null.
         * @param region The region to be requested, not null.
         * @param timestamp the UTC timestamp, or null for the current time.
         * @return true if the currency is accepted as legal tender in the
         * current region.
         */
        public boolean isLegalCurrencyUnit(CurrencyUnit currency,
                Region region, Long timestamp);

        /**
         * This method allows to evaluate the {@link CurrencyUnit} accepted as
         * legal tenders for a {@link Region}.
         *
         * @param region The region to be requested, not null.
         * @param timestamp the UTC timestamp, or null for the current time.
         * @return the {@link CurrencyUnit} to be known as legal tenders for the
         * given region, never null.
         */
        public Set<CurrencyUnit> getLegalCurrencyUnits(Region region,
                Long timestamp);
    }

    /**
     * Method that loads the {@link MonetaryConversionSpi} on class loading.
     *
     * @return the instance ot be registered into the shared variable.
     */
    private static MonetaryRegionsSpi loadMonetaryRegionSpi() {
        try {
            // try loading directly from ServiceLoader
            Iterator<MonetaryRegionsSpi> instances = ServiceLoader.load(
                    MonetaryRegionsSpi.class).iterator();
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
    private final static class DefaultMonetaryRegionsSpi implements
            MonetaryRegionsSpi {
        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.money.ext.MonetaryRegions.MonetaryRegionSpi#getRootRegions()
         */

        @Override
        public Collection<Region> getRootRegions() {
            // TODO Provide minimal implementation of regions here
            return Collections.emptySet();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.money.ext.MonetaryRegions.MonetaryRegionSpi#getRootRegion(java
         * .lang.String)
         */
        @Override
        public Region getRootRegion(String id) {
            // TODO Provide minimal implementation of regions here
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.money.ext.MonetaryRegions.MonetaryRegionSpi#getAll(javax.money
         * .ext.Region, java.lang.Long)
         */
        @Override
        public Set<CurrencyUnit> getAll(Region region, Long timestamp) {
            // TODO Provide minimal implementation of regions here
            return Collections.emptySet();
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.money.ext.MonetaryRegions.MonetaryRegionSpi#isLegalCurrencyUnit
         * (javax.money.CurrencyUnit, javax.money.ext.Region, java.lang.Long)
         */
        @Override
        public boolean isLegalCurrencyUnit(CurrencyUnit currency,
                Region region, Long timestamp) {
            // TODO Provide minimal implementation of regions here
            throw new UnsupportedOperationException("Not implemented.");
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.money.ext.MonetaryRegions.MonetaryRegionSpi#getLegalCurrencyUnits
         * (javax.money.ext.Region, java.lang.Long)
         */
        @Override
        public Set<CurrencyUnit> getLegalCurrencyUnits(Region region,
                Long timestamp) {
            // TODO Provide minimal implementation of regions here
            return Collections.emptySet();
        }

    }

}
