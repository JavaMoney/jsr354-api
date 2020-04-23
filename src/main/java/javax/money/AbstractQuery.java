/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018-2020 Werner Keil, Otavio Santana, Trivadis AG
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

import java.util.Collections;
import java.util.List;

/**
 * Represents a general context of data targeting an item of type {@code Q}. Contexts are used to add arbitrary
 * data that cannot be be mapped in a standard way to the money API, e.g. use case or customer specific
 * extensions or specialities.<p>
 * Superclasses of this class must be final, immutable, serializable and thread-safe.
 */
public abstract class AbstractQuery extends AbstractContext {

	private static final long serialVersionUID = 5309190820845869327L;

	/**
     * Key for storing the target providers to be queried
     */
    protected static final String KEY_QUERY_PROVIDERS = "Query.providers";

    /**
     * Key name for the timestamp attribute.
     */
    protected static final String KEY_QUERY_TIMESTAMP = "Query.timestamp";

    /**
     * Key name for the target type attribute.
     */
    protected static final String KEY_QUERY_TARGET_TYPE = "Query.targetType";


    /**
     * Constructor, using a builder.
     *
     * @param builder the builder, not null.
     */
    protected AbstractQuery(@SuppressWarnings("rawtypes") AbstractQueryBuilder builder) {
        super(builder);
    }

    /**
     * Returns the providers and their ordering to be considered. This information typically must be interpreted by the
     * singleton SPI implementations, which are backing the singleton accessors.
     * If the list returned is empty, the default provider list,
     * determined by methods like {@code getDefaultProviderNames()} should be used.
     *
     * @return the ordered providers, never null.
     */
    public List<String> getProviderNames() {

        @SuppressWarnings("unchecked") List<String> result = get(KEY_QUERY_PROVIDERS, List.class);
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    /**
     * Gets the target implementation type required. This can be used to explicitly acquire a specific implementation
     * type and use a query to configure the instance or factory to be returned.
     *
     * @return this Builder for chaining.
     */
    public Class<?> getTargetType() {
        return get(KEY_QUERY_TARGET_TYPE, Class.class);
    }

}
