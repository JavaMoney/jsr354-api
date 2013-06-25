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
package javax.money.ext;

import java.io.Serializable;

/**
 * This abstract base class models a validity of an item S related to a
 * reference T.
 * 
 * @author Anatole
 * 
 * @param <T>
 *            the item, e.g. a CurrencyUnit
 * @param <R>
 *            the validity reference, e.g. a Region.
 */
public abstract class ValidityInfo<T, R> implements Serializable, Comparable<ValidityInfo<R, T>> {

    private static final long serialVersionUID = 1258686258819748870L;

    private T item;
    private R referenceItem;
    private Long from;
    private Long to;
    private String validitySource;

    /**
     * Creates an instance of ValidityInfo.
     * 
     * @param item
     *            the item, not null.
     * @param referenceItem
     *            , the reference, not null.
     * @param validitySource
     *            the validity source.
     * @param from
     *            the UTC timestamp, defining the start of the validity range.
     * @param to
     *            the UTC timestamp, defining the end of the validity range.
     */
    public ValidityInfo(T item, R referenceItem, String validitySource, Long from, Long to) {
	if (item == null) {
	    throw new IllegalArgumentException("Currency required.");
	}
	if (referenceItem == null) {
	    throw new IllegalArgumentException("Reference Item required.");
	}
	if (validitySource == null) {
	    throw new IllegalArgumentException("Validity Source required.");
	}
	this.item = item;
	this.referenceItem = referenceItem;
	this.from = from;
	this.to = to;
	this.validitySource = validitySource;
    }

    /**
     * Access the item for which the validity is defined.
     * 
     * @return the item, never null.
     */
    public T getItem() {
	return item;
    }

    /**
     * Access the type or range for which the item T is valid.
     * 
     * @return the ference instance, never null.
     */
    public R getReferenceItem() {
	return referenceItem;
    }

    /**
     * Access the starting UTC timestamp for which the item T is valid, related
     * to R.
     * 
     * @return the starting UTC timestamp, or null.
     */
    public Long getFrom() {
	return from;
    }

    /**
     * Access the ending UTC timestamp for which the item T is valid, related to
     * R.
     * 
     * @return the starting UTC timestamp, or null.
     */
    public Long getTo() {
	return to;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(ValidityInfo other) {
	// TODO Auto-generated method stub
	return 0;
    }

}
