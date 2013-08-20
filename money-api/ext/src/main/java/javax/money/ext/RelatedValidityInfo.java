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

import java.util.Calendar;

/**
 * This class models a validity of an item S related to a
 * reference T.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the item type, e.g. CurrencyUnit
 * @param <R>
 *            the validity reference type, e.g. Region.
 */
public class RelatedValidityInfo<T, R> extends ValidityInfo<T> {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1258686258819748871L;

	/**
	 * THe reference type to which this validity is related.
	 */
	private final R referenceItem;

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
	 *            the calendar instance, defining the start of the validity
	 *            range.
	 * @param to
	 *            the calendar instance, defining the end of the validity range.
	 */
	public RelatedValidityInfo(T item, R referenceItem, String validitySource,
			Calendar from, Calendar to, String targetTimezoneId) {
		super(item, validitySource, from, to, targetTimezoneId);
		if (referenceItem == null) {
			throw new IllegalArgumentException("Reference Item required.");
		}
		this.referenceItem = referenceItem;
	}

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
	public RelatedValidityInfo(T item, R referenceItem, String validitySource,
			Long from, Long to, String targetTimezoneId) {
		super(item, validitySource, from, to, targetTimezoneId);
		if (referenceItem == null) {
			throw new IllegalArgumentException("Reference Item required.");
		}
		this.referenceItem = referenceItem;
	}

	/**
	 * Access the type or range for which the item T is valid.
	 * 
	 * @return the ference instance, never null.
	 */
	public R getReferenceItem() {
		return referenceItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((referenceItem == null) ? 0 : referenceItem.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Calendar cal;
		@SuppressWarnings("rawtypes")
		RelatedValidityInfo other = (RelatedValidityInfo) obj;
		if (!super.equals(other)) {
			return false;
		}
		if (referenceItem == null) {
			if (other.referenceItem != null) {
				return false;
			}
		} else if (!referenceItem.equals(other.referenceItem)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int compareTo(ValidityInfo other) {
		if (this == other) {
			return 0;
		}
		if (other == null) {
			return -1;
		}
		int compare = super.compareTo(other);
		if (compare != 0) {
			return compare;
		}
		if (!(other instanceof RelatedValidityInfo)) {
			return -1;
		}
		RelatedValidityInfo otherRef = (RelatedValidityInfo) other;

		if (compare == 0 && referenceItem instanceof Comparable) {
			if (referenceItem == null) {
				if (otherRef.referenceItem != null) {
					compare = 1;
				}
			} else {
				if (otherRef.referenceItem == null) {
					compare = -1;
				} else {
					compare = ((Comparable) referenceItem)
							.compareTo(otherRef.referenceItem);
				}
			}
		}
		return compare;
	}

}
