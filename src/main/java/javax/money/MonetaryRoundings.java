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
package javax.money;

import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryRoundingsSingletonSpi;
import javax.money.spi.RoundingProviderSpi;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class models the accessor for rounding instances, modeled as
 * {@link javax.money.MonetaryOperator}.
 * <p>
 * This class is thread-safe.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryRoundings{

    /**
     * An adaptive rounding instance that transparently looks up the correct
     * rounding.
     */
    private static final MonetaryRounding DEFAULT_ROUNDING = new DefaultCurrencyRounding();

    /**
     * The used {@link javax.money.spi.MonetaryCurrenciesSingletonSpi} instance.
     */
    private static final MonetaryRoundingsSingletonSpi monetaryRoundingsSpi = loadMonetaryRoundingsSingletonSpi();

    /**
     * Private singleton constructor.
     */
    private MonetaryRoundings(){
        // Singleton
    }

    /**
     * Loads the SPI backing bean.
     *
     * @return an instance of MonetaryRoundingsSingletonSpi.
     */
    private static MonetaryRoundingsSingletonSpi loadMonetaryRoundingsSingletonSpi(){
        try{
            return Bootstrap
                    .getService(MonetaryRoundingsSingletonSpi.class, new DefaultMonetaryRoundingsSingletonSpi());
        }
        catch(Exception e){
            Logger.getLogger(MonetaryCurrencies.class.getName())
                    .log(Level.SEVERE, "Failed to load MonetaryCurrenciesSingletonSpi, using default.", e);
            return new DefaultMonetaryRoundingsSingletonSpi();
        }
    }

    /**
     * Creates a rounding that can be added as {@link MonetaryOperator} to
     * chained calculations. The instance will lookup the concrete
     * {@link MonetaryOperator} instance from the {@link MonetaryRoundings}
     * based on the input {@link MonetaryAmount}'s {@link CurrencyUnit}.
     *
     * @return the (shared) default rounding instance.
     */
    public static MonetaryRounding getDefaultRounding(){
        return Optional.ofNullable(monetaryRoundingsSpi).orElseThrow(() -> new MonetaryException(
                                                                                         "No MonetaryRoundingsSpi loaded, query functionality is not available.")
        ).getDefaultRounding();
    }


    /**
     * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
     * instances given a currency.
     *
     * @param currencyUnit The currency, which determines the required precision. As
     *                     {@link java.math.RoundingMode}, by default, {@link java.math.RoundingMode#HALF_UP}
     *                     is sued.
     * @param providers    the providers and ordering to be used. By default providers and ordering as defined in
     *                     #getDefaultProviders is used.
     * @return a new instance {@link MonetaryOperator} implementing the
     * rounding, never {@code null}.
     */
    public static MonetaryRounding getRounding(CurrencyUnit currencyUnit, String... providers){
        return Optional.ofNullable(monetaryRoundingsSpi).orElseThrow(() -> new MonetaryException(
                                                                             "No MonetaryRoundingsSpi loaded, query functionality is not available.")
        ).getRounding(currencyUnit, providers);
    }

    /**
     * Access an {@link MonetaryOperator} for custom rounding
     * {@link MonetaryAmount} instances.
     *
     * @param roundingId The rounding identifier.
     * @param providers  the providers and ordering to be used. By default providers and ordering as defined in
     *                   #getDefaultProviders is used.
     * @return the corresponding {@link MonetaryOperator} implementing the
     * rounding, never {@code null}.
     * @throws IllegalArgumentException if no such rounding is registered using a
     *                                  {@link RoundingProviderSpi} instance.
     */
    public static MonetaryRounding getRounding(String roundingId, String... providers){
        return Optional.ofNullable(monetaryRoundingsSpi).orElseThrow(() -> new MonetaryException(
                                                                             "No MonetaryRoundingsSpi loaded, query functionality is not available.")
        ).getRounding(roundingId, providers);
    }

    /**
     * Access a {@link MonetaryRounding} using a possibly complex query.
     *
     * @param roundingQuery The {@link javax.money.RoundingQuery} that may contains arbitrary parameters to be evaluated.
     * @return the corresponding {@link javax.money.MonetaryRounding}, never {@code null}.
     * @throws IllegalArgumentException if no such rounding is registered using a
     *                                  {@link RoundingProviderSpi} instance.
     */
    public static MonetaryRounding getRounding(RoundingQuery roundingQuery){
        return Optional.ofNullable(monetaryRoundingsSpi).orElseThrow(() -> new MonetaryException(
                                                                             "No MonetaryRoundingsSpi loaded, query functionality is not available.")
        ).getRounding(roundingQuery);
    }

    /**
     * Access multiple {@link MonetaryRounding} instances using a possibly complex query
     *
     * @param roundingQuery The {@link javax.money.RoundingQuery} that may contains arbitrary parameters to be evaluated.
     * @return all {@link javax.money.MonetaryRounding} instances macthing the query, never {@code null}.
     */
    public static Collection<MonetaryRounding> getRoundings(RoundingQuery roundingQuery){
        return Optional.ofNullable(monetaryRoundingsSpi).orElseThrow(() -> new MonetaryException(
                                                                             "No MonetaryRoundingsSpi loaded, query functionality is not available.")
        ).getRoundings(roundingQuery);
    }


    /**
     * Allows to access the names of the current defined roundings.
     *
     * @param providers the providers and ordering to be used. By default providers and ordering as defined in
     *                  #getDefaultProviders is used.
     * @return the set of custom rounding ids, never {@code null}.
     */
    public static Set<String> getRoundingNames(String... providers){
        return Optional.ofNullable(monetaryRoundingsSpi).orElseThrow(() -> new MonetaryException(
                                                                             "No MonetaryRoundingsSpi loaded, query functionality is not available.")
        ).getRoundingNames(providers);
    }

    /**
     * Allows to access the names of the current registered providers.
     *
     * @return the set of provider names, never {@code null}.
     */
    public static Set<String> getProviderNames(){
        return Optional.ofNullable(monetaryRoundingsSpi).orElseThrow(() -> new MonetaryException(
                                                                             "No MonetaryRoundingsSpi loaded, query functionality is not available.")
        ).getProviderNames();
    }

    /**
     * Default Rounding that rounds a {@link MonetaryAmount} based on the
     * amount's {@link CurrencyUnit}.
     *
     * @author Anatole Tresch
     */
    private static final class DefaultCurrencyRounding implements MonetaryRounding, Serializable{

        private static final RoundingContext ROUNDING_CONTEXT = RoundingContextBuilder.create("default", "default").build();
        @Override
        public MonetaryAmount apply(MonetaryAmount amount){
            MonetaryRounding r = MonetaryRoundings.getRounding(amount.getCurrency());
            return r.apply(amount);
        }

        @Override
        public RoundingContext getRoundingContext(){
            return ROUNDING_CONTEXT;
        }
    }

    /**
     * This class models the accessor for rounding instances, modeled as
     * {@link MonetaryOperator}.
     * <p>
     * This class is thread-safe.
     *
     * @author Anatole Tresch
     * @author Werner Keil
     */
    private static final class DefaultMonetaryRoundingsSingletonSpi implements MonetaryRoundingsSingletonSpi{

        /**
         * Creates an rounding instance using {@link java.math.RoundingMode#UP} rounding.
         *
         * @return the corresponding {@link javax.money.MonetaryOperator} implementing the
         * rounding.
         * @throws javax.money.MonetaryException if no such rounding could be evaluated.
         */
        public RoundingQueryBuilder createRoundingQueryBuilder(){
            throw new IllegalStateException("No MonetaryRoundingsSingletonSpi registered.");
        }

        /**
         * Get the default rounding, which deleggates rounding dynamically dependenging on the current  currency.
         * @return the default rounding, never null.
         */
        public MonetaryRounding getDefaultRounding(){
            return DEFAULT_ROUNDING;
        }

        /**
         * Query all roundings matching the given {@link javax.money.RoundingQuery}.
         *
         * @param query the rounding query, not null.
         * @return the collection found, not null.
         */
        @Override
        public Collection<MonetaryRounding> getRoundings(RoundingQuery query){
            List<MonetaryRounding> result = new ArrayList<>();
            Collection<String> providerNames = query.getProviders();
            if(providerNames == null || providerNames.isEmpty()){
                providerNames = getDefaultProviders();
            }
            for(String providerName : providerNames){
                for(RoundingProviderSpi prov : Bootstrap.getServices(RoundingProviderSpi.class)){
                    if(providerName.equals(prov.getProviderName())){
                        try{
                            MonetaryRounding r = prov.getRounding(query);
                            if(r!=null){
                                result.add(r);
                            }
                        }
                        catch(Exception e){
                            Logger.getLogger(DefaultMonetaryRoundingsSingletonSpi.class.getName())
                                    .log(Level.SEVERE, "Error loading RoundingProviderSpi from provider: " + prov, e);
                        }
                    }
                }
            }
            return result;
        }


        /**
         * Get the names of all current registered providers.
         *
         * @return the names of all current registered providers, never null.
         */
        @Override
        public Set<String> getProviderNames(){
            Set<String> result = new HashSet<>();
            for(RoundingProviderSpi prov : Bootstrap.getServices(RoundingProviderSpi.class)){
                try{
                    result.add(prov.getProviderName());
                }
                catch(Exception e){
                    Logger.getLogger(DefaultMonetaryRoundingsSingletonSpi.class.getName())
                            .log(Level.SEVERE, "Error loading RoundingProviderSpi from provider: " + prov, e);
                }
            }
            return result;
        }

        /**
         * Get the default providers list to be used.
         *
         * @return the default provider list and ordering, not null.
         */
        @Override
        public List<String> getDefaultProviders(){
            List<String> result = new ArrayList<>();
            result.addAll(getProviderNames());
            Collections.sort(result);
            return result;
        }

        /**
         * Allows to access the identifiers of the current defined roundings.
         *
         * @param providers the providers and ordering to be used. By default providers and ordering as defined in
         *                  #getDefaultProviders is used, not null.
         * @return the set of custom rounding ids, never {@code null}.
         */
        public Set<String> getRoundingNames(String... providers){
            Set<String> result = new HashSet<>();
            String[] providerNames = providers;
            if(providerNames.length == 0){
                providerNames = getDefaultProviders().toArray(new String[getDefaultProviders().size()]);
            }
            for(String providerName : providerNames){
                for(RoundingProviderSpi prov : Bootstrap.getServices(RoundingProviderSpi.class)){
                    try{
                        if(prov.getProviderName().equals(providerName) || prov.getProviderName().matches(providerName)){
                            result.addAll(prov.getRoundingIds());
                        }
                    }
                    catch(Exception e){
                        Logger.getLogger(DefaultMonetaryRoundingsSingletonSpi.class.getName())
                                .log(Level.SEVERE, "Error loading RoundingProviderSpi from provider: " + prov, e);
                    }
                }
            }
            return result;
        }

    }

}