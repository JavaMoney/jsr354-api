/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    int getPriority();

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
