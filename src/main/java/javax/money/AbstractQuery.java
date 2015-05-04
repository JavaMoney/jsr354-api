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

import java.util.Collections;
import java.util.List;

/**
 * Represents a general context of data targeting an item of type {@code Q}. Contexts are used to add arbitrary
 * data that cannot be be mapped in a standard way to the money API, e.g. use case or customer specific
 * extensions os specialities.<p>
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
