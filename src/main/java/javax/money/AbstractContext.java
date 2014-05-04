/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class models an abstract context that allows to define extendible and
 * portable contexts with any kind of attributes in a type safe way.
 * <p/>
 * Instances of this class should be immutable and thread-safe.
 *
 * @author Anatole Tresch
 */
public abstract class AbstractContext implements Serializable{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 5579720004786348255L;

    /**
     * Map with the attributes of this context.
     */
    protected final Map<Class<?>,Map<Object,Object>> attributes = new HashMap<>();

    /**
     * Private constructor, used by {@link AbstractBuilder}.
     *
     * @param builder the Builder.
     */
    protected AbstractContext(AbstractBuilder builder){
        this.attributes.putAll(builder.attributes);
    }

    /**
     * Set a value, use this method hereby only for initialization to keep
     * immutable semantics.
     *
     * @param attribute the attribute
     * @param key       the key
     * @param type      THE type
     * @return the value previously active, or null.
     */
    // type safe cast
    @SuppressWarnings("unchecked")
    protected final <T> T set(T attribute, Object key, Class<? extends T> type){
        Map<Object,Object> typedAttrs = attributes.get(type);
        if(typedAttrs == null){
            typedAttrs = new HashMap<>();
            attributes.put(type, typedAttrs);
        }
        return (T) typedAttrs.put(key, attribute);
    }

    /**
     * Sets an attribute, using {@code attribute.getClass()} as attribute
     * <i>type</i> and {@code attribute.getClass().getName()} as attribute
     * <i>name</i>.
     *
     * @param value the attribute value
     * @return this Builder, for chaining
     */
    // type safe cast
    @SuppressWarnings("unchecked")
    protected final <T> T set(T value){
        return (T) set(value, value.getClass(), value.getClass());
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
    public <T> T getNamedAttribute(Object key, Class<T> type){
        return getNamedAttribute(key, type, null);
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
    public <T> T getNamedAttribute(Object key, Class<T> type, T defaultValue){
        Map<Object,?> typedAttrs = attributes.get(type);
        if(typedAttrs != null){
            T val = (T) typedAttrs.get(key);
            if(val != null){
                return val;
            }
        }
        return defaultValue;
    }

    /**
     * Access an attribute, hereby using the class name as key.
     *
     * @param type the attribute's type, not {@code null}
     * @return the attribute value, or {@code null}.
     */
    // safe cast
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(Class<T> type){
        return getNamedAttribute(type, type);
    }

    /**
     * Access a single attribute, also providing a default value.
     *
     * @param type         the attribute's type, not {@code null}.
     * @param defaultValue the default value, can also be {@code null}.
     * @return the attribute's value, or the {@code defaultValue} passed, if no
     * such attribute is present.
     */
    public <T> T getAttribute(Class<T> type, T defaultValue){
        T t = getAttribute(type);
        if(t == null){
            return defaultValue;
        }
        return t;
    }

    /**
     * Access the types of the attributes of this {@link MonetaryContext}.
     *
     * @return the types of the attributes of this {@link MonetaryContext},
     * never {@code null}.
     */
    @SuppressWarnings("rawtypes")
    public Set<Class<?>> getAttributeTypes(){
        return this.attributes.keySet();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        AbstractContext other = (AbstractContext) obj;
        if(attributes == null){
            if(other.attributes != null){
                return false;
            }
        }else if(!attributes.equals(other.attributes)){
            return false;
        }
        return true;
    }

    /**
     * Access an Long attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Long getLong(Object key){
        return getLong(key, null);
    }

    /**
     * Access an Long attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Long getLong(Object key, Long defaultValue){
        return getNamedAttribute(key, Long.class, defaultValue);
    }


    /**
     * Access an Float attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Float getFloat(Object key){
        return getFloat(key, null);
    }

    /**
     * Access an Float attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Float getFloat(Object key, Float defaultValue){
        return getNamedAttribute(key, Float.class, defaultValue);
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
        return getNamedAttribute(key, Integer.class, defaultValue);
    }

    /**
     * Access an Boolean attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Boolean getBoolean(Object key){
        return getBoolean(key, null);
    }

    /**
     * Access an Boolean attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Boolean getBoolean(Object key, Boolean defaultValue){
        return getNamedAttribute(key, Boolean.class, defaultValue);
    }

    /**
     * Access an Double attribute.
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
        return getNamedAttribute(key, Double.class, defaultValue);
    }

    /**
     * Access an String attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public String getText(Object key){
        return getText(key, null);
    }

    /**
     * Access an String attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public String getText(Object key, String defaultValue){
        return getNamedAttribute(key, String.class, defaultValue);
    }


    /**
     * Access an String attribute.
     *
     * @param key the attribute's key, not null.
     * @return the value, or null.
     */
    public Character getChar(Object key){
        return getChar(key, null);
    }

    /**
     * Access an Character attribute.
     *
     * @param key          the attribute's key, not null.
     * @param defaultValue the default value returned, if the attribute is not present.
     * @return the value, or default value.
     */
    public Character getChar(Object key, Character defaultValue){
        return getNamedAttribute(key, Character.class, defaultValue);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + " [attributes=" + attributes + "]";
    }

    /**
     * Builder class to create {@link AbstractContext} instances. Instances of
     * this class are not thread-safe.
     *
     * @author Anatole Tresch
     */
    public static abstract class AbstractBuilder<B extends AbstractBuilder<B>>{

        /**
         * Map with the attributes of this context.
         */
        protected final Map<Class<?>,Map<Object,Object>> attributes = new HashMap<>();

        /**
         * Create a new Builder instance without any provider, e.g. for creating
         * new {@link AbstractContext} instances for querying.
         */
        public AbstractBuilder(){
        }

        /**
         * Create a new Builder, hereby using the given {@link AbstractContext}
         * 's values as defaults. This allows changing an existing
         * {@link AbstractContext} easily.
         *
         * @param context the context, not {@code null}
         */
        public AbstractBuilder(AbstractContext context){
            Objects.requireNonNull(context);
            this.attributes.putAll(context.attributes);
        }

        /**
         * Sets an attribute, using {@code attribute.getClass()} as attribute
         * <i>type</i> and {@code attribute.getClass().getName()} as attribute
         * <i>name</i>.
         *
         * @param value the attribute value
         * @return this Builder, for chaining
         */
        public B setObject(Object value){
            return setAttribute(value.getClass(), value, value.getClass());
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
        public <T> B setObject(T value, Class<? extends T> type){
            Objects.requireNonNull(type);
            Objects.requireNonNull(value);
            return setAttribute(type, value, type);
        }

        /**
         * Sets an Integer attribute.
         *
         * @param key   the key, non null.
         * @param value the value
         * @return the Builder, for chaining.
         */
        public B setInt(Object key, Integer value){
            return setAttribute(key, value, Integer.class);
        }


        /**
         * Sets an Boolean attribute.
         *
         * @param key   the key, non null.
         * @param value the value
         * @return the Builder, for chaining.
         */
        public B setBoolean(Object key, Boolean value){
            return setAttribute(key, value, Boolean.class);
        }


        /**
         * Sets an Long attribute.
         *
         * @param key   the key, non null.
         * @param value the value
         * @return the Builder, for chaining.
         */
        public B setLong(Object key, Long value){
            return setAttribute(key, value, Boolean.class);
        }


        /**
         * Sets an Float attribute.
         *
         * @param key   the key, non null.
         * @param value the value
         * @return the Builder, for chaining.
         */
        public B setFloat(Object key, Float value){
            Objects.requireNonNull(value);
            return setAttribute(key, value, Boolean.class);
        }

        /**
         * Sets an Double attribute.
         *
         * @param key   the key, non null.
         * @param value the value
         * @return the Builder, for chaining.
         */
        public B setDouble(Object key, Double value){
            Objects.requireNonNull(value);
            return setAttribute(key, value, Double.class);
        }


        /**
         * Sets an Character attribute.
         *
         * @param key   the key, non null.
         * @param value the value
         * @return the Builder, for chaining.
         */
        public B setChar(Object key, Character value){
            Objects.requireNonNull(value);
            return setAttribute(key, value, Double.class);
        }

        /**
         * Sets an String attribute.
         *
         * @param key   the key, non null.
         * @param value the value
         * @return the Builder, for chaining.
         */
        public B setText(Object key, String value){
            Objects.requireNonNull(value);
            return setAttribute(key, value, String.class);
        }


        /**
         * Removes all attributes of a given type, using {@code attribute.getClass()} as attribute
         * <i>type</i> and {@code attribute.getClass().getName()} as attribute
         * <i>name</i>.
         *
         * @param type the attribute's type, not {@code null}
         * @return this Builder, for chaining
         */
        public B removeAttribute(Class type){
            return removeAttribute(type, type);
        }

        /**
         * Sets an attribute, using {@code attribute.getClass()} as attribute
         * <i>type</i>.
         *
         * @param value the attribute value
         * @param key   the attribute's key, not {@code null}
         * @return this Builder, for chaining
         */
        public B setAttribute(Object key, Object value){
            return setAttribute(key, value, value.getClass());
        }

        /**
         * Removes an attribute, using {@code attribute.getClass()} as attribute
         * <i>type</i>.
         *
         * @param type the attribute's type to be removed
         * @param key  the attribute's key, not {@code null}
         * @return this Builder, for chaining
         */
        public B removeAttribute(Object key, Class type){
            Map<Object,Object> typedAttrs = attributes.get(type);
            if(typedAttrs != null){
                attributes.remove(key);
            }
            return (B) this;
        }


        /**
         * Sets an attribute.
         *
         * @param attribute the attribute's value
         * @param key       the attribute's key
         * @param type      the attribute's type
         * @return this Builder, for chaining
         */
        public <T> B setAttribute(Object key, T attribute, Class<? extends T> type){
            Map<Object,Object> typedAttrs = attributes.get(type);
            if(typedAttrs == null){
                typedAttrs = new HashMap<>();
                attributes.put(type, typedAttrs);
            }
            typedAttrs.put(key, attribute);
            return (B) this;
        }


        /**
         * Apply all attributes on the given context.
         *
         * @param context the context to be applied, not null.
         * @return this Builder, for chaining
         */
        public B setAll(AbstractContext context){
            this.attributes.putAll(context.attributes);
            return (B) this;
        }

        /**
         * Creates a new {@link AbstractContext} with the data from this Builder
         * instance.
         *
         * @return a new {@link AbstractContext}. never {@code null}.
         */
        public abstract AbstractContext build();

        @Override
        public String toString(){
            return getClass().getSimpleName() + " [attributes=" + attributes + "]";
        }

    }

}
