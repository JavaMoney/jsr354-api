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
package javax.money;

import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryRoundingsSingletonSpi;
import javax.money.spi.RoundingProviderSpi;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class models the accessor for rounding instances, modeled as
 * {@link javax.money.MonetaryOperator}.
 * <p/>
 * This class is thread-safe.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 */
final class DefaultMonetaryRoundingsSingletonSpi implements MonetaryRoundingsSingletonSpi {

    /**
     * An adaptive rounding instance that transparently looks up the correct
     * rounding.
     */
    private static final MonetaryRounding DEFAULT_ROUNDING = new DefaultCurrencyRounding();

    /**
     * Creates an rounding instance using {@link java.math.RoundingMode#UP} rounding.
     *
     * @return the corresponding {@link MonetaryOperator} implementing the
     * rounding.
     * @throws MonetaryException if no such rounding could be evaluated.
     */
    public RoundingQueryBuilder createRoundingQueryBuilder() {
        throw new IllegalStateException("No MonetaryRoundingsSingletonSpi registered.");
    }

    /**
     * Get the default rounding, which delegates rounding dynamically depending on the current  currency.
     *
     * @return the default rounding, never null.
     */
    @Override
	public MonetaryRounding getDefaultRounding() {
        return DEFAULT_ROUNDING;
    }


    /**
     * Query all roundings matching the given {@link RoundingQuery}.
     *
     * @param query the rounding query, not null.
     * @return the collection found, not null.
     */
    @Override
    public Collection<MonetaryRounding> getRoundings(RoundingQuery query) {
        List<MonetaryRounding> result = new ArrayList<>();
        Collection<String> providerNames = query.getProviderNames();
        if (providerNames == null || providerNames.isEmpty()) {
            providerNames = getDefaultProviderChain();
        }
        for (String providerName : providerNames) {
            Bootstrap.getServices(RoundingProviderSpi.class).stream()
                    .filter(prov -> providerName.equals(prov.getProviderName())).forEach(prov -> {
                try {
                    MonetaryRounding r = prov.getRounding(query);
                    if (r != null) {
                        result.add(r);
                    }
                } catch (Exception e) {
                    Logger.getLogger(DefaultMonetaryRoundingsSingletonSpi.class.getName())
                            .log(Level.SEVERE, "Error loading RoundingProviderSpi from provider: " + prov, e);
                }
            });
        }
        return result;
    }

    /**
     * Get the names of all current registered providers.
     *
     * @return the names of all current registered providers, never null.
     */
    @Override
    public Set<String> getProviderNames() {
        Set<String> result = new HashSet<>();
        for (RoundingProviderSpi prov : Bootstrap.getServices(RoundingProviderSpi.class)) {
            try {
                result.add(prov.getProviderName());
            } catch (Exception e) {
                Logger.getLogger(Monetary.class.getName())
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
    public List<String> getDefaultProviderChain() {
        List<String> result = new ArrayList<>();
        result.addAll(Monetary.getRoundingProviderNames());
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
    @Override
	public Set<String> getRoundingNames(String... providers) {
        Set<String> result = new HashSet<>();
        String[] providerNames = providers;
        if (providerNames.length == 0) {
            providerNames = Monetary.getDefaultRoundingProviderChain().toArray(new String[Monetary.getDefaultRoundingProviderChain().size()]);
        }
        for (String providerName : providerNames) {
            for (RoundingProviderSpi prov : Bootstrap.getServices(RoundingProviderSpi.class)) {
                try {
                    if (prov.getProviderName().equals(providerName) || prov.getProviderName().matches(providerName)) {
                        result.addAll(prov.getRoundingNames());
                    }
                } catch (Exception e) {
                    Logger.getLogger(DefaultMonetaryRoundingsSingletonSpi.class.getName())
                            .log(Level.SEVERE, "Error loading RoundingProviderSpi from provider: " + prov, e);
                }
            }
        }
        return result;
    }

    /**
     * Default Rounding that rounds a {@link MonetaryAmount} based on the
     * amount's {@link CurrencyUnit}.
     *
     * @author Anatole Tresch
     */
    private static final class DefaultCurrencyRounding implements MonetaryRounding, Serializable {

		private static final long serialVersionUID = 8641545296538357839L;

		private static final RoundingContext ROUNDING_CONTEXT = RoundingContextBuilder.of("default", "default").build();

        @Override
        public MonetaryAmount apply(MonetaryAmount amount) {
            MonetaryRounding r = Monetary.getRounding(amount.getCurrency());
            return r.apply(amount);
        }

        @Override
        public RoundingContext getRoundingContext() {
            return ROUNDING_CONTEXT;
        }
    }

}
