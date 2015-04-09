/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2015, Credit Suisse All rights
 * reserved.
 */
package javax.money.spi;

import java.util.List;

/**
 * This class models the component that is managing the lifecycle of the
 * monetary services used by the Money and Currency API.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public interface ServiceProvider {

    /**
     * This method allows to define a priority for a registered ServiceProvider instance. When multiple providers are
     * registered in the system the provider with the highest priority value is taken.
     *
     * @return the provider's priority (default is 0).
     */
    public int getPriority();

    /**
     * Access a list of services, given its type. The bootstrap mechanism should
     * order the instance for precedence, hereby the most significant should be
     * first in order. If no such services are found, an empty list should be
     * returned.
     *
     * @param serviceType the service type.
     * @return The instance to be used, never {@code null}
     */
    <T> List<T> getServices(Class<T> serviceType);

    /**
     * Access a single service, given its type. The bootstrap mechanism should
     * order the instance for precedence, hereby the most significant should be
     * first in order and returned. If no such services are found, null is
     * returned.
     *
     * @param serviceType the service type.
     * @return The instance, (with highest precedence) or {@code null}, if no such service is available.
     */
    default <T> T getService(Class<T> serviceType) {
        return getServices(serviceType).stream().findFirst().orElse(null);
    }
}
