/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money.format;

/**
 * Default implementation of {@link ItemFactory} that looks up resulting item
 * under the {@link Class} or {@link Class#getName()} key.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the item type.
 */
public final class DefaultItemFactory<T> implements ItemFactory<T> {
	/** The item class. */
	private Class<T> itemClass;

	/**
	 * Constructor.
	 * 
	 * @param itemClass
	 *            The item class, not {@code null}.
	 */
	public DefaultItemFactory(Class<T> itemClass) {
		this.itemClass = itemClass;
	}

	/**
	 * Accesses the final item from the {@link ParseContext}.
	 * 
	 * @param context
	 *            the {@link ParseContext}.
	 * @return the item parsed.
	 * @throws IllegalStateException
	 *             , if the item could not be found.
	 * @see #isComplete(ParseContext)
	 */
	@Override
	public T apply(ParseContext<T> context) {
		T item = context.getResult(itemClass, itemClass);
		if (item == null) {
			item = context.getResult(itemClass.getName(), itemClass);
		}
		if (item == null) {
			throw new IllegalStateException("Parsing is not complete.");
		}
		return item;
	}

	/**
	 * CHecks if the required item is available within the {@link ParseContext},
	 * using the class or fully qualified class name as a key.
	 * 
	 * @param context
	 *            the {@link ParseContext}.
	 * @return {@code true}, if the item parsed was found or can be created.
	 * @see #apply(ParseContext)
	 */
	@Override
	public boolean isComplete(ParseContext<T> context) {
		return context.getResult(itemClass, itemClass) != null
				|| context.getResult(itemClass.getName(), itemClass) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DefaultItemFactory [itemClass=" + itemClass + "]";
	}

}