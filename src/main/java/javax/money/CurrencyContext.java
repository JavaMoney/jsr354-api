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
package javax.money;

import java.io.Serializable;

/**
 * This class models the attributable context of {@link javax.money.CurrencyUnit} instances. It
 * provides information about
 * <ul>
 * <li>the provider that provided the instance (required)
 * <li>the target timestamp / temporal unit
 * <li>any other attributes, identified by the attribute type, e.g. regions, tenants etc.
 * </ul>
 * <p>Instances of this class must be created using the {@link javax.money.CurrencyContextBuilder}. Typically the
 * contexts are created and assigned by the classes that implement the {@link javax.money.spi.CurrencyProviderSpi}.
 * The according implementation classes should document, which attributes are available.</p>
 * <p>
 * This class is immutable, serializable and thread-safe.
 *
 * @author Anatole Tresch
 */
public final class CurrencyContext extends AbstractContext implements Serializable{

	private static final long serialVersionUID = 8450310852172607016L;


	/**
     * Constructor, used from the {@link javax.money.CurrencyContextBuilder}.
     *
     * @param builder the corresponding builder, not null.
     */
    CurrencyContext(CurrencyContextBuilder builder){
        super(builder);
    }


    /**
     * Allows to convert a instance into the corresponding {@link javax.money.CurrencyContextBuilder}, which allows
     * to change the values and of another {@link javax.money.CurrencyContext} instance.
     *
     * @return a new Builder instance, preinitialized with the values from this instance.
     */
    public CurrencyContextBuilder toBuilder(){
        return CurrencyContextBuilder.of(this);
    }


}
