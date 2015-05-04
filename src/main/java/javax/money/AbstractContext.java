/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2015, Credit SUISSE All rights
 * reserved.
 */
package javax.money;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a general context of data targeting an item of type {@code Q}. Contexts are used to add arbitrary
 * data that cannot be be mapped in a standard way to the money API, e.g. use case or customer specific
 * extensions os specialities.<p>
 * Superclasses of this class must be final, immutable, serializable and thread-safe.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractContext implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Key for storing the target providers to be queried
	 */
    protected static final String KEY_PROVIDER = "provider";


    /**
     * The data map containing all values.
     */
    final Map<String, Object> data = new HashMap<>();

    /**
     * Private constructor, used by {@link AbstractContextBuilder}.
     *
     * @param builder the Builder.
     */
	protected AbstractContext(AbstractContextBuilder<?, ?> builder) {
        data.putAll(builder.data);
    }

    /**
     * Get the present keys of all entries with a given type, checking hereby if assignable.
     *
     * @param type The attribute type, not null.
     * @return all present keys of attributes being assignable to the type, never null.
     */
    public Set<String> getKeys(Class<?> type) {
        return data.entrySet().stream().filter(val -> type.isAssignableFrom(val.getValue()
                .getClass())).map(Map.Entry::getKey).collect(Collectors
                .toSet());
    }

    /**
     * Get the current attribute type.
     * @param key the entry's key, not null
     * @return the current attribute type, or null, if no such attribute exists.
     */
    public Class<?> getType(String key) {
        Object val = this.data.get(key);
        return val == null ? null : val.getClass();
    }


    /**
     * Access an attribute.
     *
     * @param type the attribute's type, not {@code null}
     * @param key  the attribute's key, not {@code null}
     * @return the attribute value, or {@code null}.
     */
    public <T> T get(String key, Class<T> type) {
        Object value = this.data.get(key);
        if (value != null && type.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }

    /**
     * Access an attribute, hereby using the class name as key.
     *
     * @param type the type, not {@code null}
     * @return the type attribute value, or {@code null}.
     */
    public <T> T get(Class<T> type) {
        return get(type.getName(), type);
    }


    /**
     * Access a Long attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Long getLong(String key) {
        return get(key, Long.class);
    }


    /**
     * Access a Float attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Float getFloat(String key) {
        return get(key, Float.class);
    }

    /**
     * Access an Integer attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Integer getInt(String key) {
        return get(key, Integer.class);
    }

    /**
     * Access a Boolean attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Boolean getBoolean(String key) {
        return get(key, Boolean.class);
    }

    /**
     * Access a Double attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Double getDouble(String key) {
        return get(key, Double.class);
    }

    /**
     * Access a String attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public String getText(String key) {
        return get(key, String.class);
    }

    /**
     * Get the provider name of this context.
     *
     * @return the provider name, or null.
     */
    public String getProviderName() {
        return getText(KEY_PROVIDER);
    }


    /**
     * Checks if the current instance has no attributes set. This is often the cases, when used in default cases.
     *
     * @return true, if no attributes are set.
     */
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractContext) {
            AbstractContext other = (AbstractContext) obj;
            return Objects.equals(data, other.data);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " (\n" + data + ')';
    }
}
