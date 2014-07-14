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

import java.io.Serializable;
import java.util.*;

/**
 * Represents a general context of data targeting an item of type {@code Q}. Contexts are used to add arbitrary
 * data that cannot be be mapped in a atandard way to the money API, e.g. use case or customer specific
 * extensions os specialities.<p>
 * Superclasses of this class must be final, immutable, serializable and thread-safe.
 */
public abstract class AbstractContext implements Serializable{

    /**
     * The data map containing all values.
     */
    private final Map<Class<?>,Map<Object,Object>> data = new HashMap<>();

    /**
     * Private constructor, used by {@link AbstractContextBuilder}.
     *
     * @param builder the Builder.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected AbstractContext(AbstractContextBuilder<?,?> builder){
        for(Map.Entry<Class<?>,Map<Object,Object>> en : builder.data.entrySet()){
            Map<Object,Object> presentMap = (Map<Object,Object>) this.data.get(en.getKey());
            if(presentMap == null){
                presentMap = new HashMap<>(en.getValue());
                this.data.put(en.getKey(), presentMap);
            }else{
                presentMap.putAll(en.getValue());
            }
        }
    }

    /**
     * Get the present keys for a given attribute type.
     *
     * @param type The attribute type, not null.
     * @return all present keys of attributes of the (exact) given type, never null.
     */
    public Set<Object> getKeys(Class<?> type){
        Map<Object,Object> values = this.data.get(type);
        if(values != null){
            return values.keySet();
        }
        return Collections.emptySet();
    }

    /**
     * Get all currently present attribute types.
     *
     * @return all currently present attribute types, never null.
     */
    public Set<Class<?>> getTypes(){
        return this.data.keySet();
    }

    /**
     * Access an attribute.
     *
     * @param type the attribute's type, not {@code null}
     * @param key  the attribute's key, not {@code null}
     * @return the attribute value, or {@code null}.
     */
    // Type safe cast
    @SuppressWarnings("unchecked")
    public <T> T getAny(Object key, Class<T> type, T defaultValue){
        Map<Object,Object> values = this.data.get(type);
        Object value = null;
        if(values != null){
            value = values.get(key);
        }
        if(value != null){
            return (T) value;
        }
        return defaultValue;
    }

    /**
     * Access an attribute.
     *
     * @param type the attribute's type, not {@code null}
     * @param key  the attribute's key, not {@code null}
     * @return the attribute value, or {@code null}.
     */
    public <T> T getAny(Object key, Class<T> type){
        return getAny(key, type, null);
    }

    /**
     * Checks if the current instance has no attributes set. This is often the cases, when used in default cases.
     *
     * @return true, if no attributes are set.
     */
    public boolean isEmpty(){
        return this.data.isEmpty();
    }

    /**
     * Access an attribute, hereby using the class name as key.
     *
     * @param type the attribute's type, not {@code null}
     * @return the attribute value, or {@code null}.
     */
    public <T> T get(Class<T> type){
        return getAny(type, type);
    }

    /**
     * Access a single attribute, also providing a default value.
     *
     * @param type         the attribute's type, not {@code null}.
     * @param defaultValue the default value, can also be {@code null}.
     * @return the attribute's value, or the {@code defaultValue} passed, if no
     * such attribute is present.
     */
    public <T> T get(Class<T> type, T defaultValue){
        return Optional.ofNullable(get(type)).orElse(defaultValue);
    }


    /**
     * Access a Long attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Long getLong(Object key){
        return getLong(key, null);
    }

    /**
     * Access a Long attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Long getLong(Object key, Long defaultValue){
        return getAny(key, Long.class, defaultValue);
    }


    /**
     * Access a Float attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Float getFloat(Object key){
        return getFloat(key, null);
    }

    /**
     * Access a Float attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Float getFloat(Object key, Float defaultValue){
        return getAny(key, Float.class, defaultValue);
    }

    /**
     * Access an Integer attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Integer getInt(Object key){
        return getInt(key, null);
    }

    /**
     * Access an Integer attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Integer getInt(Object key, Integer defaultValue){
        return getAny(key, Integer.class, defaultValue);
    }

    /**
     * Access a Boolean attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Boolean getBoolean(Object key){
        return getBoolean(key, null);
    }

    /**
     * Access a Boolean attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Boolean getBoolean(Object key, Boolean defaultValue){
        return getAny(key, Boolean.class, defaultValue);
    }

    /**
     * Access a Double attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Double getDouble(Object key){
        return getDouble(key, null);
    }

    /**
     * Access an Double attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Double getDouble(Object key, Double defaultValue){
        return getAny(key, Double.class, defaultValue);
    }

    /**
     * Access a String attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public String getText(Object key){
        return getText(key, null);
    }

    /**
     * Access a String attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public String getText(Object key, String defaultValue){
        return getAny(key, String.class, defaultValue);
    }


    /**
     * Access a String attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Character getChar(Object key){
        return getChar(key, null);
    }

    /**
     * Access a Character attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Character getChar(Object key, Character defaultValue){
        return getAny(key, Character.class, defaultValue);
    }

    /**
     * Access a Collection attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Collection<?> getCollection(Object key){
        return getCollection(key, null);
    }

    /**
     * Access a Collection attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public <T> Collection<T> getCollection(Object key, Collection<T> defaultValue){
        return getAny(key, Collection.class, defaultValue);
    }

    /**
     * Access a List attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public List<?> getList(Object key){
        return getList(key, null);
    }

    /**
     * Access a Collection attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public <T> List<T> getList(Object key, List<T> defaultValue){
        return getAny(key, List.class, defaultValue);
    }

    /**
     * Access a Set attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Set<?> getSet(Object key){
        return getSet(key, null);
    }

    /**
     * Access a Set attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public <T> Set<T> getSet(Object key, Set<T> defaultValue){
        return getAny(key, Set.class, defaultValue);
    }

    /**
     * Access a Map attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Map<?,?> getMap(Object key){
        return getMap(key, null);
    }

    /**
     * Access a Map attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public <K, V> Map<K,V> getMap(Object key, Map<K,V> defaultValue){
        return getAny(key, Map.class, defaultValue);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode(){
        return Objects.hash(data);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj instanceof AbstractContext){
            AbstractContext other = (AbstractContext) obj;
            return Objects.equals(data, other.data);
        }
        return false;
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + " [attributes=" + data + "]";
    }


    /**
     * This interface defines the common generic parts of a query. Queries are used to pass complex parameters sets
     * to lookup monetary artifacts, e.g. {@link javax.money.MonetaryAmountFactory},
     * {@link javax.money.MonetaryRounding},
     * {@link javax.money.CurrencyUnit}, {@link javax.money.convert.ExchangeRateProvider} and {@link javax.money.convert
     * .CurrencyConversion}.
     * <p>
     * Instances of this class are not thread-safe and not serializable.
     */
    public static abstract class AbstractContextBuilder<B extends AbstractContextBuilder, C extends AbstractContext>{

        /**
         * serialVersionUID.
         */
        private static final long serialVersionUID = 5579720004786348255L;

        /**
         * The data map containing all values.
         */
        private final Map<Class<?>,Map<Object,Object>> data = new HashMap<>();

        /**
         * Apply all attributes on the given context.
         *
         * @param context the context to be applied, not null.
         * @return this Builder, for chaining
         */
        @SuppressWarnings("unchecked")
        public B setAll(AbstractContext context){
            for(Map.Entry<Class<?>,Map<Object,Object>> en : context.data.entrySet()){
                Map<Object,Object> presentMap = this.data.get(en.getKey());
                if(presentMap == null){
                    this.data.put(en.getKey(), new HashMap<>(en.getValue()));
                }else{
                    presentMap.putAll(en.getValue());
                }
            }
            return (B) this;
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
        @SuppressWarnings("unchecked")
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
         * Creates a new {@link javax.money.AbstractContext} with the data from this Builder
         * instance.
         *
         * @return a new {@link javax.money.AbstractContext}. never {@code null}.
         */
        public abstract C build();

        @Override
        public String toString(){
            return getClass().getSimpleName() + " [attributes=" + data + "]";
        }
    }

}
