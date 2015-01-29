/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money;

import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * This interface defines the common generic parts of a query. Queries are used to pass complex parameters sets
 * to lookup monetary artifacts, e.g. {@link MonetaryAmountFactory},
 * {@link javax.money.MonetaryRounding},
 * {@link javax.money.CurrencyUnit}, {@link javax.money.convert.ExchangeRateProvider} and {@link javax.money.convert
 * .CurrencyConversion}.
 * <p>
 * Instances of this class are not thread-safe and not serializable.
 */
public abstract class AbstractContextBuilder<B extends AbstractContextBuilder, C extends AbstractContext>{

    /**
     * The data map containing all values.
     */
    final Map<Object, Object> data = new HashMap<>();

    /**
     * Apply all attributes on the given context.
     *
     * @param context             the context to be applied, not null.
     * @param overwriteDuplicates flag, if existing entries should be overwritten.
     * @return this Builder, for chaining
     */
    public B importContext(AbstractContext context, boolean overwriteDuplicates){
        for (Map.Entry<Object, Object> en : context.data.entrySet()) {
            if (overwriteDuplicates) {
                this.data.put(en.getKey(), en.getValue());
            }else{
                this.data.putIfAbsent(en.getKey(), en.getValue());
            }
        }
        return (B) this;
    }

    /**
     * Apply all attributes on the given context, hereby existing entries are preserved.
     *
     * @param context the context to be applied, not null.
     * @return this Builder, for chaining
     * @see #importContext(AbstractContext, boolean)
     */
    public B importContext(AbstractContext context){
        Objects.requireNonNull(context);
        return importContext(context, false);
    }

    /**
     * Sets an Integer attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, int value){
        this.data.put(key, Objects.requireNonNull(value));
        return (B) this;
    }


    /**
     * Sets an Boolean attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, boolean value){
        this.data.put(key, Objects.requireNonNull(value));
        return (B) this;
    }


    /**
     * Sets an Long attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, long value){
        this.data.put(key, Objects.requireNonNull(value));
        return (B) this;
    }


    /**
     * Sets an Float attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, float value){
        this.data.put(key, Objects.requireNonNull(value));
        return (B) this;
    }

    /**
     * Sets an Double attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, double value){
        this.data.put(key, Objects.requireNonNull(value));
        return (B) this;
    }


    /**
     * Sets an Character attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, char value){
        this.data.put(key, Objects.requireNonNull(value));
        return (B) this;
    }


    /**
     * Sets an attribute, using {@code attribute.getClass()} as attribute
     * <i>type</i> and {@code attribute.getClass().getName()} as attribute
     * <i>name</i>.
     *
     * @param value the attribute value
     * @return this Builder, for chaining
     */
    public B setTyped(Object value) {
        data.put(value.getClass(), value);
        return (B) this;
    }

    /**
     * Sets an attribute, using {@code attribute.getClass()} as attribute
     * <i>type</i>.
     *
     * @param value the attribute value
     * @param key   the attribute's key, not {@code null}
     * @return this Builder, for chaining
     */
    public B set(Object key, Object value){
        data.put(key, value);
        return (B) this;
    }

    /**
     * Sets the provider.
     *
     * @param provider the provider, not null.
     * @return the Builder for chaining
     */
    public B setProvider(String provider){
        Objects.requireNonNull(provider);
        set(AbstractContext.KEY_PROVIDER, provider);
        return (B) this;
    }

    /**
     * Set the target timestamp in UTC millis. This allows to select historical roundings that were valid in the
     * past. Its implementation specific, to what extend historical roundings are available. By default if this
     * property is not setTyped always current {@link  javax.money.MonetaryRounding} instances are provided.
     *
     * @param timestamp the target timestamp
     * @return this instance for chaining
     * @see #setTimestamp(java.time.temporal.TemporalAccessor)
     */
    public B setTimestampMillis(long timestamp){
        set(AbstractContext.KEY_TIMESTAMP, timestamp);
        return (B) this;
    }

    /**
     * Set the target timestamp as {@link java.time.temporal.TemporalAccessor}. This allows to select historical
     * roundings that were valid in the past. Its implementation specific, to what extend historical roundings
     * are available. By default if this property is not setTyped always current {@link  javax.money.MonetaryRounding}
     * instances are provided.
     *
     * @param timestamp the target timestamp
     * @return this instance for chaining
     * @see #setTimestampMillis(long)
     */
    public B setTimestamp(TemporalAccessor timestamp){
        set(AbstractContext.KEY_TIMESTAMP, Objects.requireNonNull(timestamp));
        return (B) this;
    }

    /**
     * Removes an entry of a certain keys. This can be useful, when a context is initialized with another
     * existing context, but only subset of the entries should be visible. For example {@code removeAttributes
     * ("a", "b", "c")} removes all attributes named 'a','b' and 'c'.
     *
     * @param keys the keys
     * @return this Builder, for chaining
     */
    public B removeAttributes(Object... keys) {
        for (Object key : keys) {
            this.data.remove(key);
        }
        return (B) this;
    }

    /**
     * Removes all entries.
     *
     * @return this Builder, for chaining
     */
    public B removeAll() {
        this.data.clear();
        return (B) this;
    }

    /**
     * Creates a new {@link AbstractContext} with the data from this Builder
     * instance.
     *
     * @return a new {@link AbstractContext}. never {@code null}.
     */
    public abstract C build();

    /*
     * (non-Javadoc)
     *
     * @see Object#toString()
     */

    @Override
    public String toString(){
        return getClass().getSimpleName() + " [attributes:\n" + new TreeMap<>(data).toString() + ']';
    }
}