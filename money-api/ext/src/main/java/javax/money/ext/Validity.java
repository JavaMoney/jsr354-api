/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE 
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. 
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY 
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE 
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" 
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

/**
 * This class models a validity for something, e.g. {@link CurrencyUnit},
 * {@link MonetaryAmount}, ExchangeRate or other areas. Reason behind is that
 * for monetary artifacts the validity may vary depending on legal aspects,
 * country, or even the data provider.<br/>
 * Hereby a validity is modeled as a weak reference, meaning that it does not
 * contain a reference to the instance for which it defines the validity range.
 * As a consequence instances of this class must always b e contained by its
 * owning instance or in a artifact that also provides the owning instance along
 * with the validities, e.g. as modelled by {@link Validateable}.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the reference type's type
 */
public final class Validity<T> implements Comparable<Validity<T>> {

	/** The reference, never {@code null}. */
	private T reference;

	/** The start of this validity, as UTC timestamp. */
	private Long validFrom;

	/** The end of this validity, as UTC timestamp. */
	private Long validUntil;

	/**
	 * Creates a new validity instance.
	 * 
	 * @param reference
	 *            The reference this validity is referring, e.g. a country or
	 *            legal unit.
	 * @param validFrom
	 *            the timestamp from when the owning instance is valid, or
	 *            {@code null}.
	 * @param validUntil
	 *            the timestamp until the owning instance is valid, or
	 *            {@code null}.
	 */
	public Validity(T reference, Long validFrom, Long validUntil) {
		if (reference == null) {
			throw new IllegalArgumentException("reference required.");
		}
		this.reference = reference;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
	}

	/**
	 * Returns the reference this validity instance is based on, e.g. a country
	 * or legal unit.
	 * 
	 * @return the reference type, never {@code null}.
	 */
	public T getReference() {
		return reference;
	}

	/**
	 * Get the timestamp from when the owning instance instance is valid,
	 * according to the reference.
	 * 
	 * @see #getReference()
	 * 
	 * @return the UTC timestamp from where this instance is valid. If not
	 *         defined {@code null} should be returned.
	 */
	public Long getValidFrom() {
		return validFrom;
	}

	/**
	 * Get the timestamp until the reference owning instance instance is valid,
	 * according to the reference.
	 * 
	 * @see #getReference()
	 * 
	 * @return the UTC timestamp until when this instance is valid. If not
	 *         defined {@code null} should be returned.
	 */
	public Long getValidUntil() {
		return validUntil;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((reference == null) ? 0 : reference.hashCode());
		result = prime * result
				+ ((validFrom == null) ? 0 : validFrom.hashCode());
		result = prime * result
				+ ((validUntil == null) ? 0 : validUntil.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Validity<?> other = (Validity<?>) obj;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (validFrom == null) {
			if (other.validFrom != null)
				return false;
		} else if (!validFrom.equals(other.validFrom))
			return false;
		if (validUntil == null) {
			if (other.validUntil != null)
				return false;
		} else if (!validUntil.equals(other.validUntil))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Validity [reference=" + reference + ", validFrom="
				+ validFrom + ", validUntil=" + validUntil + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Validity<T> other) {
		if (other == null) {
			return -1;
		}
		int compare = reference.getClass().getName()
				.compareTo(other.getClass().getName());
		if (compare == 0) {
			if (validFrom == null) {
				if (other.validFrom != null)
					compare = 1;
			} else if (other.validFrom != null)
				compare = validFrom.compareTo(other.validFrom);
			else
				compare = -1;
		}
		if (compare == 0) {
			if (validUntil == null) {
				if (other.validUntil != null)
					compare = 1;
			} else if (other.validUntil != null)
				compare = validUntil.compareTo(other.validUntil);
			else
				compare = -1;
		}
		return compare;
	}

}
