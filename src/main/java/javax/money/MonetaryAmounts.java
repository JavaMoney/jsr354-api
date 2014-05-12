/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import javax.money.spi.Bootstrap;
import javax.money.spi.MonetaryAmountFactoryProviderSpi;
import javax.money.spi.MonetaryAmountsSingletonQuerySpi;
import javax.money.spi.MonetaryAmountsSingletonSpi;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory singleton for {@link MonetaryAmount} instances as provided by the different registered
 * {@link MonetaryAmountFactory} instances.
 * <p/>
 * This singleton allows to get {@link MonetaryAmountFactory} instances for the registered
 * {@link MonetaryAmount} implementation classes or depending on the precision and scale
 * requirements.
 *
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.6.1
 */
public final class MonetaryAmounts{
    /**
     * The used {@link javax.money.spi.MonetaryAmountsSingletonSpi} instance.
     */
    private static MonetaryAmountsSingletonSpi monetaryAmountsSingletonSpi = loadMonetaryAmountsSingletonSpi();

    /**
     * The used {@link javax.money.spi.MonetaryAmountsSingletonSpi} instance.
     */
    private static MonetaryAmountsSingletonQuerySpi monetaryAmountsSingletonQuerySpi =
            loadMonetaryAmountsSingletonQuerySpi();

    /**
     * Private singleton constructor.
     */
    private MonetaryAmounts(){
    }

    /**
     * Loads the SPI backing bean.
     *
     * @return
     */
    private static MonetaryAmountsSingletonSpi loadMonetaryAmountsSingletonSpi(){
        try{
            return Bootstrap.getService(MonetaryAmountsSingletonSpi.class);
        }
        catch(Exception e){
            Logger.getLogger(MonetaryCurrencies.class.getName())
                    .log(Level.SEVERE, "Failed to load MonetaryAmountsSingletonSpi.", e);
            return null;
        }
    }

    /**
     * Loads the SPI backing bean.
     *
     * @return
     */
    private static MonetaryAmountsSingletonQuerySpi loadMonetaryAmountsSingletonQuerySpi(){
        try{
            return Bootstrap.getService(MonetaryAmountsSingletonQuerySpi.class);
        }
        catch(Exception e){
            Logger.getLogger(MonetaryCurrencies.class.getName()).log(Level.SEVERE, "Failed to load " +
                                                                             "MonetaryAmountsSingletonQuerySpi, " +
                                                                             "query functionality will not be " +
                                                                             "available.", e
            );
            return null;
        }
    }

    /**
     * Access an {@link MonetaryAmountFactory} for the given {@link MonetaryAmount} implementation
     * type.
     *
     * @param amountType {@link MonetaryAmount} implementation type, nor {@code null}.
     * @return the corresponding {@link MonetaryAmountFactory}, never {@code null}.
     * @throws MonetaryException if no {@link MonetaryAmountFactory} targeting the given {@link MonetaryAmount}
     *                           implementation class is registered.
     */
    public static <T extends MonetaryAmount> MonetaryAmountFactory<T> getAmountFactory(Class<T> amountType){
        if(monetaryAmountsSingletonSpi == null){
            throw new MonetaryException("No MonetaryAmountsSingletonSpi loaded.");
        }
        MonetaryAmountFactory<T> factory = monetaryAmountsSingletonSpi.getAmountFactory(amountType);
        if(factory == null){
            throw new MonetaryException("No AmountFactory available for type: " + amountType.getName());
        }
        return factory;
    }

    /**
     * Access the default {@link MonetaryAmountFactory} as defined by
     * {@link #getDefaultAmountType()}.
     *
     * @return the {@link MonetaryAmountFactory} corresponding to {@link #getDefaultAmountType()},
     * never {@code null}.
     * @throws MonetaryException if no {@link MonetaryAmountFactory} targeting the {@link #getDefaultAmountType()}
     *                           implementation class is registered.
     */
    public static MonetaryAmountFactory<?> getAmountFactory(){
        if(monetaryAmountsSingletonSpi == null){
            throw new MonetaryException("No MonetaryAmountsSingletonSpi loaded.");
        }
        MonetaryAmountFactory<?> factory = monetaryAmountsSingletonSpi.getAmountFactory(getDefaultAmountType());
        if(factory == null){
            throw new MonetaryException("No default AmountFactory available.");
        }
        return factory;
    }

    /**
     * Access the default {@link MonetaryAmount} type.
     *
     * @return a the default {@link MonetaryAmount} type corresponding, never {@code null}.
     * @throws MonetaryException if no {@link MonetaryAmountFactoryProviderSpi} is available, or no
     *                           {@link MonetaryAmountFactoryProviderSpi} targeting the configured default
     *                           {@link MonetaryAmount} type.
     */
    public static Class<? extends MonetaryAmount> getDefaultAmountType(){
        if(monetaryAmountsSingletonSpi == null){
            throw new MonetaryException("No MonetaryAmountsSingletonSpi loaded.");
        }
        return monetaryAmountsSingletonSpi.getDefaultAmountType();
    }

    /**
     * Access all currently available {@link MonetaryAmount} implementation classes that are
     * accessible from this {@link MonetaryAmount} singleton.
     *
     * @return all currently available {@link MonetaryAmount} implementation classes that have
     * corresponding {@link MonetaryAmountFactory} instances provided, never {@code null}
     */
    public static Set<Class<? extends MonetaryAmount>> getAmountTypes(){
        if(monetaryAmountsSingletonSpi == null){
            throw new MonetaryException("No MonetaryAmountsSingletonSpi loaded.");
        }
        return monetaryAmountsSingletonSpi.getAmountTypes();
    }

    /**
     * Get the {@link MonetaryAmount} implementation class, that best matches to cover the given
     * {@link MonetaryContext}.
     *
     * @param requiredContext the {@link MonetaryContext} to be queried for a matching {@link MonetaryAmount}
     *                        implementation, not{@code null}.
     * @return the {@link MonetaryAmount} implementation class, that best matches to cover the given
     * {@link MonetaryContext}, never {@code null}.
     * @throws MonetaryException if no {@link MonetaryAmount} implementation class can cover the required
     *                           {@link MonetaryContext}.
     */
    public static Class<? extends MonetaryAmount> queryAmountType(MonetaryContext requiredContext){
        if(monetaryAmountsSingletonSpi == null){
            throw new MonetaryException("No MonetaryAmountsSingletonSpi loaded.");
        }
        if(monetaryAmountsSingletonQuerySpi == null){
            throw new MonetaryException(
                    "No MonetaryAmountsSingletonQuerySpi loaded, query functionality is not available.");
        }
        return monetaryAmountsSingletonQuerySpi.queryAmountType(monetaryAmountsSingletonSpi, requiredContext);
    }

    /**
     * Get the {@link javax.money.MonetaryAmountFactory} implementation, that best matches to cover the given
     * {@link MonetaryContext}.
     *
     * @param requiredContext the {@link MonetaryContext} to be queried for a matching {@link MonetaryAmount}
     *                        implementation, not{@code null}.
     * @return the {@link javax.money.MonetaryAmountFactory} implementation, that best matches to cover the given
     * {@link MonetaryContext}, never {@code null}.
     * @throws MonetaryException if no {@link MonetaryAmount} implementation class can cover the required
     *                           {@link MonetaryContext}.
     */
    public static MonetaryAmountFactory<?> queryAmountFactory(MonetaryContext requiredContext){
        Class<? extends MonetaryAmount> type = queryAmountType(requiredContext);
        return getAmountFactory(type);
    }


}