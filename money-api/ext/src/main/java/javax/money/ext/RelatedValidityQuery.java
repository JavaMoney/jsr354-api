/**
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
package javax.money.ext;

import java.io.Serializable;

import javax.money.MonetaryFunction;
import javax.money.ext.spi.ValidityProviderSpi;

/**
 * For accessing {@link ValidityInfo} instances from the {@link Validities}
 * singleton, instances of this class must be created an configured.
 * <p>
 * This class is immutable, thread-safe and {@link Serializable}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the item type, on which validities are defined.
 */
public class RelatedValidityQuery<T, R> extends ValidityQuery<T> {

	/** The base item. */
	private Class<R> relatedToType;
	/** Get the predicate to filter the validity items to be returned. */
	private MonetaryFunction<R, Boolean> relatedToPredicate;

	/**
	 * Creates a new validity query.
	 * 
	 * @param itemClass
	 *            the type for which validities are queried.
	 */
	public RelatedValidityQuery(Class<T> item, Class<R> relatedToType) {
		super(item);
		if (relatedToType == null) {
			throw new IllegalArgumentException("relatedToType required");
		}
		this.relatedToType = relatedToType;
	}

	/**
	 * Access the related items for which the validities are queried.
	 * 
	 * @see #getRelatedToType()
	 * 
	 * @return the item The item for which the validity information is queried.
	 */
	public final MonetaryFunction<R, Boolean> getRelatedToPredicate() {
		return relatedToPredicate;
	}

	/**
	 * Access the item class for which the validities are queried. It is also
	 * possible query only for a related {@link Class} instance, which will
	 * select all validities of all items of that type.<br/>
	 * If {@link #relatedToInstance} was set, this method returns the class of
	 * {@link #relatedToInstance}, so implementors of
	 * {@link ValidityProviderSpi} must additionally explicitly check, if
	 * {@link #getRelatedToInstance()} is returning any non {@code null} value.
	 * 
	 * @see #getRelatedToInstance()
	 * 
	 * @return the item The item for which the validity information is queried.
	 */
	public final Class<R> getRelatedToType() {
		return relatedToType;
	}

	public RelatedValidityQuery<T, R> withRelatedToPredicate(
			MonetaryFunction<R, Boolean> relatedToPredicate) {
		this.relatedToPredicate = relatedToPredicate;
		return this;
	}

	@Override
	public String toString() {
		return "RelatedValidityQuery [itemType=" + getItemType() + ", item="
				+ getItem() + "relatedType=" + getItemType()
				+ ", relatedPredicate=" + getRelatedToPredicate() + ", from="
				+ getFrom() + ", to=" + getTo()
				+ ", targetTimezoneId=" + getTargetTimezoneId()
				+ ", validitySource=" + getValiditySource() + ", validityType="
				+ getValidityType() + "]";
	}
}
