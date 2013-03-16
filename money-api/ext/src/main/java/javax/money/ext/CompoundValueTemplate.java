/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 */
package javax.money.ext;

import java.util.Enumeration;
import java.util.Map;

/**
 * Defines a {@link CompoundValueTemplate} containing several results. Hereby
 * the different results are identified by arbitrary keys. Additionally each
 * {@link CompoundValueTemplate} has a <i>leading</i> item that identifies the
 * type of result.<br/>
 * A {@link CompoundValueTemplate} instance is defined to be implemented as
 * immutable object and therefore is very useful for modeling multidimensional
 * results objects or input parameters as they are common in financial
 * applications.
 * 
 * @author Anatole Tresch
 */
public interface CompoundValueTemplate {

	/**
	 * A {@link CompoundValueTemplate}may have a type identifier that helps to
	 * identify, what type of items object is returned.
	 * 
	 * @return the {@link CompoundValueTemplate}'s type, never null.
	 */
	public String getId();

	/**
	 * Get the available keys in this {@link CompoundValueTemplate}.
	 * 
	 * @return the keys defined, never null, but may empty.
	 */
	public Enumeration<Object> getKeys();

	/**
	 * Checks if an instance of T can be accessed given the key passed.
	 * 
	 * @see #get(Object)
	 * @param key
	 *            the key required.
	 * @return true, if an instance can be accessed given this key
	 */
	public boolean isKeyDefined(Object key);

	/**
	 * Access an instance of T with the given key.
	 * 
	 * @see #isKeyDefined(Object)
	 * @param key
	 *            The key identifying the item T to be returned.
	 * @return the instance of type T found.
	 * @throws IllegalArgumentException
	 *             if no such a instance is defined.
	 */
	public Class<?> getType(Object key);

	/**
	 * Access all items within this {@link CompoundValueTemplate}.
	 * 
	 * @return all items as an immutable {@link Map}.
	 */
	public Map<Object, Class<?>> getAll();

	/**
	 * Validates if the given {@link CompoundValue} defines all the attributes
	 * as required by this {@link CompoundValueTemplate} instance.
	 * 
	 * @param value
	 *            the {@link CompoundValue} to be validated.
	 * @return true if the passed {@link CompoundValue} is valid.
	 */
	public boolean isValid(CompoundValue value);

	/**
	 * Validates if the given {@link CompoundValue} defines all the attributes
	 * as required by this {@link CompoundValueTemplate} instance.
	 * 
	 * @param value
	 *            the {@link CompoundValue} to be validated.
	 * @see #isValid(CompoundValue)
	 * @throws IllegalArgumentException
	 *             if validation fails.
	 */
	public void validate(CompoundValue value);
}
