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
package javax.money.ext;

import java.io.Serializable;
import java.util.TimeZone;

import javax.money.MonetaryFunction;
import javax.money.Predicate;
import javax.money.ext.spi.ValidityProviderSpi;

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
public class RelatedValidityQuery<T, R> extends ValidityQuery<T> {

	/** The base item. */
	private Class<R> relatedToType;
	/** Get the predicate to filter the validity items to be returned. */
	private MonetaryFunction<R, Boolean> relatedToPredicate;

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
	protected RelatedValidityQuery(ValidityType validityType,
			Class<T> itemType,
			T item, String validityProviderId, Long from, Long to,
			String targetTimezoneId, Class<R> relatedToType,
			Predicate<R> relatedToPredicate) {
		super(validityType, itemType, item, validityProviderId, from, from,
				targetTimezoneId);
		if (relatedToType == null) {
			throw new IllegalArgumentException("relatedToType required");
		}
		this.relatedToType = relatedToType;
		this.relatedToPredicate = relatedToPredicate;
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
	public static class Builder<T, R> extends ValidityQuery.Builder<T> {

		/** The base item. */
		protected Class<R> relatedToType;
		/** Get the predicate to filter the validity items to be returned. */
		protected Predicate<R> relatedToPredicate;

		/**
		 * Constructor.
		 * 
		 * @param validityType
		 *            the validity type, not {@code null}.
		 * @param itemType
		 *            the item type, not {@code null}.
		 */
		public Builder(ValidityType validityType,
				Class<T> itemType, Class<R> relatedToType) {
			super();
			withItemType(itemType);
			withValidityType(validityType);
			withRelatedToType(relatedToType);
		}

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
		public Builder() {
			super();
		}

		/**
		 * Sets the related item class for which the related validities are
		 * queried. It is also possible query only for a related {@link Class}
		 * instance, which will select all validities of all items of that type.<br/>
		 * If {@link #relatedToInstance} was set, this method returns the class
		 * of {@link #relatedToInstance}, so implementors of
		 * {@link ValidityProviderSpi} must additionally explicitly check, if
		 * {@link #getRelatedToInstance()} is returning any non {@code null}
		 * value.
		 * 
		 * @see #getRelatedToInstance()
		 * 
		 * @return the item The item for which the validity information is
		 *         queried.
		 */
		public Builder<T, R> withRelatedToType(Class<R> relatedToType) {
			if (relatedToType == null) {
				throw new IllegalArgumentException("relatedToType required");
			}
			this.relatedToType = relatedToType;
			return this;
		}

		/**
		 * Sets a predicate to constraint the relatedTo items returned.
		 * 
		 * @param relatedToPredicate
		 * @return the Builder, for chaining.
		 */
		public Builder<T, R> withRelatedToPredicate(
				Predicate<R> relatedToPredicate) {
			this.relatedToPredicate = relatedToPredicate;
			return this;
		}

		/**
		 * Builds a new instance of {@link RelatedValidityQuery}. throws
		 * IllegalArgumentException if the data provided is not sufficient zo
		 * build the instance.
		 */
		@Override
		public RelatedValidityQuery<T, R> build() {
			return new RelatedValidityQuery<T, R>(validityType, itemType, item,
					validitySource, from, to, targetTimezoneId,
					relatedToType, relatedToPredicate);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.ext.ValidityQuery.Builder#withFrom(long)
		 */
		@Override
		public RelatedValidityQuery.Builder<T, R> withFrom(long timestamp) {
			super.withFrom(timestamp);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.ext.ValidityQuery.Builder#withItem(java.lang.Object)
		 */
		@Override
		public RelatedValidityQuery.Builder<T, R> withItem(T item) {
			super.withItem(item);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.ValidityQuery.Builder#withItemType(java.lang.Class)
		 */
		@Override
		public RelatedValidityQuery.Builder<T, R> withItemType(
				Class<T> itemType) {
			super.withItemType(itemType);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.ValidityQuery.Builder#withTargetTimezone(java.util
		 * .TimeZone)
		 */
		@Override
		public RelatedValidityQuery.Builder<T, R> withTargetTimezone(
				TimeZone timezone) {
			super.withTargetTimezone(timezone);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.ValidityQuery.Builder#withTargetTimezoneID(java.lang
		 * .String)
		 */
		@Override
		public RelatedValidityQuery.Builder<T, R> withTargetTimezoneID(
				String timezoneÎD) {
			super.withTargetTimezoneID(timezoneÎD);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.ext.ValidityQuery.Builder#withTo(long)
		 */
		@Override
		public RelatedValidityQuery.Builder<T, R> withTo(long timestamp) {
			super.withTo(timestamp);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.ValidityQuery.Builder#withValiditySource(java.lang
		 * .String)
		 */
		@Override
		public RelatedValidityQuery.Builder<T, R> withValiditySource(
				String source) {
			super.withValiditySource(source);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.ext.ValidityQuery.Builder#withValidityType(javax.money
		 * .ext.ValidityType)
		 */
		@Override
		public RelatedValidityQuery.Builder<T, R> withValidityType(
				ValidityType validityType) {
			super.withValidityType(validityType);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.ext.ValidityQuery.Builder#toString()
		 */
		@Override
		public String toString() {
			return "RelatedValidityQuery.Builder [itemType=" + itemType
					+ ", item="
					+ item + "relatedType=" + relatedToType
					+ ", relatedPredicate=" + relatedToPredicate + ", from="
					+ from + ", to=" + to
					+ ", targetTimezoneId=" + targetTimezoneId
					+ ", validitySource=" + validitySource + ", validityType="
					+ validityType + "]";
		}
	}

}
