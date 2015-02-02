/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2014, Credit SUISSE All rights
 * reserved.
 */
package javax.money;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
	public static final String KEY_PROVIDER = "provider";

	/**
	 * Key name for the timestamp attribute.
	 */
	public static final String KEY_TIMESTAMP = "timestamp";

    /**
     * The data map containing all values.
     */
    final Map<Object, Object> data = new HashMap<>();

    /**
     * Private constructor, used by {@link AbstractContextBuilder}.
     *
     * @param builder the Builder.
     */
	@SuppressWarnings("rawtypes")
	protected AbstractContext(AbstractContextBuilder<?, ?> builder) {
        data.putAll(builder.data);
    }

    /**
     * Get the present keys of all entries with a given type, checking hereby if assignable.
     *
     * @param type The attribute type, not null.
     * @return all present keys of attributes being assignable to the type, never null.
     */
    public Set<Object> getKeys(Class<?> type) {
        Set<Object> keys = new HashSet<>();
        for (Map.Entry<Object, Object> val : data.entrySet()) {
            if (type.isAssignableFrom(val.getValue().getClass())) {
                keys.add(val.getKey());
            }
        }
        return keys;
    }

    /**
     * Get the current attribute type.
     *
     * @return the current attribute type, or null, if no such attribute exists.
     */
    public Class<?> getType(Object key) {
        Object val = this.data.get(key);
        return val == null ? null : val.getClass();
    }

    /**
     * Access an attribute.
     *
     * @param type the attribute's type, not {@code null}
     * @param key  the attribute's key, not {@code null}
     * @param defaultValue the default value, or {@code null}.
     * @return the attribute value, or {@code null}.
     */
    public <T> T get(Object key, Class<T> type, T defaultValue) {
        return (T) data.getOrDefault(key, defaultValue);
    }

    /**
     * Access an attribute.
     *
     * @param type the attribute's type, not {@code null}
     * @param key  the attribute's key, not {@code null}
     * @return the attribute value, or {@code null}.
     */
    public <T> T get(Object key, Class<T> type) {
		return get(String.valueOf(key), type, null);
    }

    /**
     * Access an attribute, hereby using the class name as key.
     *
     * @param type the type, not {@code null}
     * @return the type attribute value, or {@code null}.
     */
    public <T> T getTyped(Class<T> type) {
        return get(type, type);
    }

    /**
     * Access a single attribute, also providing a default value.
     *
     * @param type         the attribute's type, not {@code null}.
     * @param defaultValue the default value, can also be {@code null}.
     * @return the attribute's value, or the {@code defaultValue} passed, if no
     * such attribute is present.
     */
    public <T> T getTyped(Class<T> type, T defaultValue) {
        return Optional.ofNullable(getTyped(type)).orElse(defaultValue);
    }


    /**
     * Access a Long attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Long getLong(Object key) {
        return getLong(key, null);
    }

    /**
     * Access a Long attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Long getLong(Object key, Long defaultValue) {
        return get(key, Long.class, defaultValue);
    }


    /**
     * Access a Float attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Float getFloat(Object key) {
        return getFloat(key, null);
    }

    /**
     * Access a Float attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Float getFloat(Object key, Float defaultValue) {
        return get(key, Float.class, defaultValue);
    }

    /**
     * Access an Integer attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Integer getInt(Object key) {
        return getInt(key, null);
    }

    /**
     * Access an Integer attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Integer getInt(Object key, Integer defaultValue) {
        return get(key, Integer.class, defaultValue);
    }

    /**
     * Access a Boolean attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Boolean getBoolean(Object key) {
        return getBoolean(key, null);
    }

    /**
     * Access a Boolean attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Boolean getBoolean(Object key, Boolean defaultValue) {
        return get(key, Boolean.class, defaultValue);
    }

    /**
     * Access a Double attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Double getDouble(Object key) {
        return getDouble(key, null);
    }

    /**
     * Access an Double attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Double getDouble(Object key, Double defaultValue) {
        return get(key, Double.class, defaultValue);
    }

    /**
     * Access a String attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public String getText(Object key) {
        return getText(key, null);
    }

    /**
     * Access a String attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public String getText(Object key, String defaultValue) {
        return get(key, String.class, defaultValue);
    }


    /**
     * Access a String attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Character getChar(Object key) {
        return getChar(key, null);
    }

    /**
     * Access a Character attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Character getChar(Object key, Character defaultValue) {
        return get(key, Character.class, defaultValue);
    }

    /**
     * Get the provider name of this context.
     *
     * @return the provider name, or null.
     */
    public String getProvider() {
        return getText(KEY_PROVIDER, null);
    }

    /**
     * Get the current target timestamp of the query in UTC milliseconds.  If not setTyped it tries to of an
     * UTC timestamp from #getTimestamp(). This allows to select historical roundings that were valid in the
     * past. Its implementation specific, to what extend historical roundings are available. By default if this
     * property is not setTyped always current {@link  javax.money.MonetaryRounding} instances are provided.
     *
     * @return the timestamp in millis, or null.
     */
    public Long getTimestampMillis() {
        Long value = get(KEY_TIMESTAMP, Long.class, null);
        if (Objects.isNull(value)) {
            TemporalAccessor acc = getTimestamp();
            if (Objects.nonNull(acc)) {
                return (acc.getLong(ChronoField.INSTANT_SECONDS) * 1000L) + acc.getLong(ChronoField.MILLI_OF_SECOND);
            }
        }
        return value;
    }

    /**
     * Get the current target timestamp of the query. If not setTyped it tries to of an Instant from
     * #getTimestampMillis(). This allows to select historical roundings that were valid in the
     * past. Its implementation specific, to what extend historical roundings are available. By default if this
     * property is not setTyped always current {@link  javax.money.MonetaryRounding} instances are provided.
     *
     * @return the current timestamp, or null.
     */
    public TemporalAccessor getTimestamp() {
        TemporalAccessor acc = get(KEY_TIMESTAMP, TemporalAccessor.class, null);
        if (Objects.isNull(acc)) {
            Long value = get(KEY_TIMESTAMP, Long.class, null);
            if (Objects.nonNull(value)) {
                acc = Instant.ofEpochMilli(value);
            }
        }
        return acc;
    }

    /**
     * Checks if the current instance has no attributes setTyped. This is often the cases, when used in default cases.
     *
     * @return true, if no attributes are setTyped.
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

    /**
     * Access all the key/values present, filtered by the values that are assignable to the given type.
     *
     * @param type the value type, not null.
     * @return return all key/values with values assignable to a given value type.
     */
    public <T> Map<Object, T> getValues(Class<T> type) {
        Map<Object, T> result = new HashMap<>();
        for (Map.Entry<Object, Object> en : data.entrySet()) {
            if (type.isAssignableFrom(en.getValue().getClass())) {
                result.put(en.getKey(), type.cast(en.getValue()));
            }
        }
        return result;
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
