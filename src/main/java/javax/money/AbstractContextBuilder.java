package javax.money;

import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * This interface defines the common generic parts of a query. Queries are used to pass complex parameters sets
 * to lookup monetary artifacts, e.g. {@link javax.money.MonetaryAmountFactory},
 * {@link javax.money.MonetaryRounding},
 * {@link javax.money.CurrencyUnit}, {@link javax.money.convert.ExchangeRateProvider} and {@link javax.money.convert
 * .CurrencyConversion}.
 * <p>
 * Instances of this class are not thread-safe and not serializable.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class AbstractContextBuilder<B extends AbstractContextBuilder, C extends AbstractContext>{

    /**
     * The data map containing all values.
     */
    final Map<Class,Map<Object,Object>> data = new HashMap<>();

    /**
     * Apply all attributes on the given context.
     *
     * @param context             the context to be applied, not null.
     * @param overwriteDuplicates flag, if existing entries should be overwritten.
     * @return this Builder, for chaining
     */
    public B importContext(AbstractContext context, boolean overwriteDuplicates){
        for(Class<?> type : context.getTypes()){
            Map<Object,Object> values = context.getValues(type);
            Map<Object,Object> presentMap = this.data.get(type);
            if(presentMap == null){
                this.data.put(type, new HashMap<>(values));
            }else{
                if(overwriteDuplicates){
                    presentMap.putAll(values);
                }else{
                    for(Map.Entry<Object,Object> en : values.entrySet()){
                        presentMap.putIfAbsent(en.getKey(), en.getValue());
                    }
                }
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
        Objects.requireNonNull(value);
        return set(key, value, Integer.class);
    }


    /**
     * Sets an Boolean attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, boolean value){
        Objects.requireNonNull(value);
        return set(key, value, Boolean.class);
    }


    /**
     * Sets an Long attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, long value){
        Objects.requireNonNull(value);
        return set(key, value, Long.class);
    }


    /**
     * Sets an Float attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, float value){
        Objects.requireNonNull(value);
        return set(key, value, Float.class);
    }

    /**
     * Sets an Double attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, double value){
        Objects.requireNonNull(value);
        return set(key, value, Double.class);
    }


    /**
     * Sets an Character attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, char value){
        Objects.requireNonNull(value);
        return set(key, value, Character.class);
    }

    /**
     * Sets an String attribute.
     *
     * @param key   the key, non null.
     * @param value the value
     * @return the Builder, for chaining.
     */
    public B set(Object key, String value){
        Objects.requireNonNull(value);
        return set(key, value, String.class);
    }


    /**
     * Sets an attribute, using {@code attribute.getClass()} as attribute
     * <i>type</i> and {@code attribute.getClass().getName()} as attribute
     * <i>name</i>.
     *
     * @param value the attribute value
     * @return this Builder, for chaining
     */
    public B set(Object value){
        return set(value.getClass(), value, value.getClass());
    }

    /**
     * Sets an attribute, using {@code attribute.getClass()} as attribute
     * <i>type</i> and {@code attribute.getClass().getName()} as attribute
     * <i>name</i>.
     *
     * @param value the attribute value
     *              * @param type
     *              the attribute's type
     * @return this Builder, for chaining
     */
    public <T> B set(T value, Class<? extends T> type){
        Objects.requireNonNull(type);
        Objects.requireNonNull(value);
        return set(type, value, type);
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
        return set(key, value, value.getClass());
    }

    /**
     * Sets a collection as attribute.
     *
     * @param value the attribute value
     * @param key   the attribute's key, not {@code null}
     * @return this Builder, for chaining
     */
    public B setCollection(Object key, Collection<?> value){
        return set(key, value, Collection.class);
    }

    /**
     * Sets a List as attribute.
     *
     * @param value the attribute value
     * @param key   the attribute's key, not {@code null}
     * @return this Builder, for chaining
     */
    public B setList(Object key, List<?> value){
        return set(key, value, List.class);
    }

    /**
     * Sets a Map as attribute.
     *
     * @param value the attribute value
     * @param key   the attribute's key, not {@code null}
     * @return this Builder, for chaining
     */
    public B setMap(Object key, Map<?,?> value){
        return set(key, value, Map.class);
    }

    /**
     * Sets a Set as attribute.
     *
     * @param value the attribute value
     * @param key   the attribute's key, not {@code null}
     * @return this Builder, for chaining
     */
    public B setSet(Object key, Set<?> value){
        return set(key, value, Set.class);
    }

    /**
     * Sets the provider.
     *
     * @param provider the provider, not null.
     * @return the Builder for chaining
     */
    public B setProvider(String provider){
        Objects.requireNonNull(provider);
        set(AbstractContext.PROVIDER, provider);
        return (B) this;
    }

    /**
     * Set the target timestamp in UTC millis. This allows to select historical roundings that were valid in the
     * past. Its implementation specific, to what extend historical roundings are available. By default if this
     * property is not set always current {@link  javax.money.MonetaryRounding} instances are provided.
     *
     * @param timestamp the target timestamp
     * @return this instance for chaining
     * @see #setTimestamp(java.time.temporal.TemporalAccessor)
     */
    public B setTimestampMillis(long timestamp){
        set(AbstractContext.TIMESTAMP, timestamp);
        return (B)this;
    }

    /**
     * Set the target timestamp as {@link java.time.temporal.TemporalAccessor}. This allows to select historical
     * roundings that were valid in the past. Its implementation specific, to what extend historical roundings
     * are available. By default if this property is not set always current {@link  javax.money.MonetaryRounding}
     * instances are provided.
     *
     * @param timestamp the target timestamp
     * @return this instance for chaining
     * @see #setTimestampMillis(long)
     */
	public B setTimestamp(TemporalAccessor timestamp){
        Objects.requireNonNull(timestamp);
        set(AbstractContext.TIMESTAMP, timestamp, TemporalAccessor.class);
        return (B)this;
    }

    /**
     * Removes an entry of a certain type. This can be useful, when a context is initialized with another
     * existing context, but only subset of the entries should be visible. FOr example {@code removeAttributes
     * (String.class, "a", "b", "c")} removes all textual attributes named 'a','b' and 'c'.
     *
     * @param keys the keys
     * @param type the attribute's type.
     * @return this Builder, for chaining
     */
    public B removeAttributes(Class<?> type, Object... keys){
        Map<Object,Object> values = this.data.get(type);
        if(!Objects.isNull(values)){
            for(Object key : keys){
                values.remove(key);
            }
        }
        return (B) this;
    }

    /**
     * Removes all entries of a certain type. This can be useful, when a context is initialized with another
     * existing context, but only
     * subset of the entries should be visible.
     *
     * @param type the attribute's type.
     * @return this Builder, for chaining
     */
    public B removeAll(Class<?> type){
        this.data.remove(type);
        return (B) this;
    }

    /**
     * Sets an attribute.
     *
     * @param value the attribute's value
     * @param key   the attribute's key
     * @param type  the attribute's type
     * @return this Builder, for chaining
     */
    public <T> B set(Object key, T value, Class<? extends T> type){
        Map<Object,Object> values = this.data.get(type);
        if(Objects.isNull(values)){
            values = new HashMap<>();
            this.data.put(type, values);
        }
        values.put(key, value);
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
        StringBuilder attrsBuilder = new StringBuilder();
        for(Map.Entry<Class,Map<Object,Object>> en : ((Map<Class,Map<Object,Object>>) this.data).entrySet()){
        	
            Map<Object,Object> sortedMap = new TreeMap<>(Comparator.comparing(Object::toString));
            sortedMap.putAll(en.getValue());
            for(Map.Entry<Object,Object> entry : sortedMap.entrySet()){
                Object key = entry.getKey();
                attrsBuilder.append("  ");
                if(key.getClass() == Class.class){
                    String className = ((Class) key).getName();
                    if(className.startsWith("java.lang.")){
                        className = className.substring("java.lang.".length());
                    }
                    attrsBuilder.append(className);
                }else{
                    attrsBuilder.append(key);
                }
                attrsBuilder.append('[');
                String typeName = en.getKey().getName();
                if(typeName.startsWith("java.lang.")){
                    typeName = typeName.substring("java.lang.".length());
                }
                attrsBuilder.append(typeName);
                attrsBuilder.append("]=");
                attrsBuilder.append(entry.getValue()).append('\n');
            }
        }
        return getClass().getSimpleName() + " [attributes:\n" + attrsBuilder.toString() + ']';
    }
}