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
package org.javamoney.ext;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

import org.javamoney.ext.spi.ValidityProviderSpi;

/**
 * For accessing {@link ValidityInfo} instances from the {@link Validities}
 * singleton, instances of this class must be created an configured.
 * <p>
 * This class and its subclasses should be immutable, thread-safe and
 * {@link Serializable}.
 * <p>
 * nOTE: The class and its builder are available for subclassing, since more
 * advanced queries may be created with them.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the item type, on which validities are defined.
 */
public class ValidityQuery<T> {
	/** The base item. */
	private T item;
	/** The base item. */
	private Class<T> itemType;
	/**
	 * The starting UTC timestamp for the validity period, or null.
	 */
	private Long from;
	/**
	 * The ending UTC timestamp for the validity period, or null.
	 */
	private Long to;
	/**
	 * The source that provides this validity data.
	 */
	private String validitySource;

	/**
	 * The type of Validity, by default {@link ValidityType#EXISTENCE}.
	 */
	private ValidityType validityType = ValidityType.EXISTENCE;

	/**
	 * The target timezone id of this validity instance, allowing to restore the
	 * correct local date.
	 */
	private String targetTimezoneId;

	/**
	 * Constructor.
	 * 
	 * @param validityType
	 *            the validity type, not {@code null}.
	 * @param itemType
	 *            the item type, not {@code null}.
	 * @param item
	 *            the item constraint.
	 * @param validitySource
	 *            the validity source id.
	 * @param from
	 *            the starting UTC timestamp.
	 * @param to
	 *            the ending UTC timestamp.
	 * @param targetTimezoneId
	 *            the target timezone ID.
	 */
	protected ValidityQuery(ValidityType validityType, Class<T> itemType,
			T item, String validitySource, Long from, Long to,
			String targetTimezoneId) {
		if (itemType == null) {
			throw new IllegalArgumentException("ItemClass required");
		}
		if (validityType == null) {
			throw new IllegalArgumentException("ValidityType required.");
		}
		this.validityType = validityType;
		this.itemType = itemType;
		this.validitySource = validitySource;
		this.from = from;
		this.targetTimezoneId = targetTimezoneId;
		this.item = item;
		this.to = to;
	}

	/**
	 * Sets the {@link ValidityType} required, by default
	 * {@link ValidityType#EXISTENCE} is used.
	 * 
	 * @return the validity type used, not {@code null}.
	 */
	public ValidityType getValidityType() {
		return this.validityType;
	}

	/**
	 * Access the item class for which the validities are queried. It is also
	 * possible query only for a related {@link Class} instance, which will
	 * select all validities of all items of that type.<br/>
	 * If {@link #item} was set, this method returns the class of {@link #item},
	 * so implementors of {@link ValidityProviderSpi} must additionally
	 * explicitly check, if {@link #getItem()} is returning any non {@code null}
	 * value.
	 * 
	 * @see #getRelatedToInstance()
	 * 
	 * @return the item The item for which the validity information is queried.
	 */
	public final Class<T> getItemType() {
		return itemType;
	}

	/**
	 * Access the item for which the validities are queried. It is also possible
	 * pass a {@link Class} instance here, which will select all validities of
	 * all items of that type.
	 * 
	 * @return the item The item for which the validity information is queried.
	 */
	public final T getItem() {
		return item;
	}

	/**
	 * Get the starting range, for which validities are queried.0
	 * 
	 * @return the from timestamp, or {@code null].
	 */
	public final Long getFrom() {
		return from;
	}

	/**
	 * Get the ending range, for which validities are queried.0
	 * 
	 * @return the to timestamp, or {@code null].
	 */
	public final Long getTo() {
		return to;
	}

	/**
	 * Get the validity source, thus effectively asking for information provided
	 * by a specific source.
	 * 
	 * @return the validitySource
	 */
	public final String getValiditySource() {
		return validitySource;
	}

	/**
	 * Get the target timezone Id, to be returned by the {@link Validities}
	 * singleton.
	 * 
	 * @return the targetTimezoneId
	 */
	public final String getTargetTimezoneId() {
		return targetTimezoneId;
	}

	/**
	 * Checks if no explicit time range was defined for the query.
	 * 
	 * @return true, if both {@link #from} and {@link #to} are {@code null}.
	 */
	public boolean isTimeUnbounded() {
		return from == null && to == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ValidityQuery [itemType=" + itemType + ", item=" + item
				+ ", from=" + from + ", to=" + to
				+ ", targetTimezoneId=" + targetTimezoneId
				+ ", validitySource=" + validitySource + ", validityType="
				+ validityType + "]";
	}

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
	public static class Builder<T> {
		/** The base item. */
		protected T item;
		/** The base item. */
		protected Class<T> itemType;
		/**
		 * The starting UTC timestamp for the validity period, or null.
		 */
		protected Long from;
		/**
		 * The ending UTC timestamp for the validity period, or null.
		 */
		protected Long to;
		/**
		 * The source that provides this validity data.
		 */
		protected String validitySource;

		/**
		 * The type of Validity, by default {@link ValidityType#EXISTENCE}.
		 */
		protected ValidityType validityType = ValidityType.EXISTENCE;

		/**
		 * The target timezone id of this validity instance, allowing to restore
		 * the correct local date.
		 */
		protected String targetTimezoneId;

		/**
		 * Creates a new validity query.
		 * 
		 * @param validityType
		 *            The validity type.
		 * @param itemClass
		 *            the type for which validities are queried.
		 */
		public Builder(ValidityType validityType, Class<T> itemType) {
			if (validityType == null) {
				throw new IllegalArgumentException("validityType required");
			}
			this.validityType = validityType;
		}

		/**
		 * Creates a new validity query.
		 * 
		 * @param itemClass
		 *            the type for which validities are queried.
		 */
		public Builder() {
		}

		/**
		 * Builds a new {@link ValidityQuery}.
		 * 
		 * @return the new query, never {@code null}.
		 * @throws IllegalArgumentException
		 *             if the query could not be built.
		 */
		public ValidityQuery<T> build() {
			return new ValidityQuery<T>(validityType, itemType, item,
					validitySource, from, to, targetTimezoneId);
		}

		/**
		 * Sets the target item.
		 * 
		 * @param item
		 *            the target item
		 * @return the instance, for chaining.
		 */
		public Builder<T> withItem(T item) {
			this.item = item;
			return this;
		}

		/**
		 * Sets the target item type.
		 * 
		 * @param item
		 *            the target item type, not {@code null}.
		 * @return the instance, for chaining.
		 */
		public Builder<T> withItemType(Class<T> itemType) {
			if (itemType == null) {
				throw new IllegalArgumentException("ItemClass required");
			}
			this.itemType = itemType;
			return this;
		}

		/**
		 * Sets the target timezone ID.
		 * 
		 * @param timezoneÎD
		 *            the target timezone ID
		 * @return the instance, for chaining.
		 */
		public Builder<T> withTargetTimezoneID(String timezoneÎD) {
			this.targetTimezoneId = timezoneÎD;
			return this;
		}

		/**
		 * Sets the target timezone.
		 * 
		 * @param timezone
		 *            the target timezone
		 * @return the instance, for chaining.
		 */
		public Builder<T> withTargetTimezone(TimeZone timezone) {
			this.targetTimezoneId = timezone.getID();
			return this;
		}

		/**
		 * Sets the {@link #from} timestamp.
		 * 
		 * @param timestamp
		 *            The from timestamp.
		 * @return the instance, for chaining.
		 */
		public Builder<T> withFrom(long timestamp) {
			this.from = timestamp;
			return this;
		}

		/**
		 * Sets the {@link #to} timestamp.
		 * 
		 * @param timestamp
		 *            The to timestamp.
		 * @return the instance, for chaining.
		 */
		public Builder<T> withTo(long timestamp) {
			this.to = timestamp;
			return this;
		}

		/**
		 * Sets the validity provider to be queried. If not set all registered
		 * and matching providers may return a result. The first non null result
		 * is considered the final result in such a scenario.
		 * 
		 * @param source
		 *            the source identifier
		 * @return the instance, for chaining.
		 */
		public Builder<T> withValiditySource(String source) {
			this.validitySource = source;
			return this;
		}

		/**
		 * Sets the {@link ValidityType} required. It is also possible to pass
		 * {@link ValidityType#ANY} to query all validities known for an item.
		 * 
		 * @param validityType
		 * @return
		 */
		public Builder<T> withValidityType(
				ValidityType validityType) {
			if (validityType == null) {
				throw new IllegalArgumentException("ValidityType required.");
			}
			this.validityType = validityType;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ValidityQuery.Builder [itemType=" + itemType + ", item="
					+ item
					+ ", from=" + from + ", to=" + to
					+ ", targetTimezoneId=" + targetTimezoneId
					+ ", validitySource=" + validitySource + ", validityType="
					+ validityType + "]";
		}

	}
}
