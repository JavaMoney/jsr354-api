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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * This base class models a validity of an item T related to a timeline. Hereby
 * a validity may be undefined, when it starts or when it ends.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the item type, e.g. a Region
 */
public class ValidityInfo<T> implements Serializable,
		Comparable<ValidityInfo<T>> {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1258686258819748870L;
	/* The item for which this ValidityInfo is for. */
	private final T item;
	/**
	 * The starting UTC timestamp for the validity period, or null.
	 */
	private final Long from;
	/**
	 * The ending UTC timestamp for the validity period, or null.
	 */
	private final Long to;
	/**
	 * The source that provides this validity data.
	 */
	private final String validitySource;

	/**
	 * The target timezone id of this validity instance, allowing to restore the
	 * correct local date.
	 */
	private final String targetTimezoneId;

	/**
	 * Additional user data associated with that {@link ValidityInfo} instance.
	 */
	private Object userData;

	/**
	 * Creates an instance of ValidityInfo.
	 * 
	 * @param item
	 *            the item, not null.
	 * @param validitySource
	 *            the validity source.
	 * @param from
	 *            the calendar instance, defining the start of the validity
	 *            range.
	 * @param to
	 *            the calendar instance, defining the end of the validity range.
	 */
	public ValidityInfo(T item, String validitySource, Calendar from,
			Calendar to, String targetTimezoneId) {
		if (item == null) {
			throw new IllegalArgumentException("Currency required.");
		}
		if (validitySource == null) {
			throw new IllegalArgumentException("Validity Source required.");
		}
		this.item = item;
		if (from != null) {
			this.from = from.getTimeInMillis();
		} else {
			this.from = null;
		}
		if (to != null) {
			this.to = to.getTimeInMillis();
		} else {
			this.to = null;
		}
		this.validitySource = validitySource;
		this.targetTimezoneId = targetTimezoneId;
	}

	/**
	 * Creates an instance of ValidityInfo.
	 * 
	 * @param item
	 *            the item, not null.
	 * @param validitySource
	 *            the validity source.
	 * @param from
	 *            the UTC timestamp, defining the start of the validity range.
	 * @param to
	 *            the UTC timestamp, defining the end of the validity range.
	 */
	public ValidityInfo(T item, String validitySource, Long from, Long to,
			String targetTimezoneId) {
		if (item == null) {
			throw new IllegalArgumentException("Currency required.");
		}
		if (validitySource == null) {
			throw new IllegalArgumentException("Validity Source required.");
		}
		this.item = item;
		this.from = from;
		this.to = to;
		this.validitySource = validitySource;
		this.targetTimezoneId = targetTimezoneId;
	}

	/**
	 * Access the user data.
	 * 
	 * @return the user data associated with this instance, or null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getUserData() {
		return (T) userData;
	}

	/**
	 * Sets the user data of this instance.
	 * 
	 * @param data
	 */
	public void setUserData(Object data) {
		this.userData = data;
	}

	/**
	 * Get the user data type, or null, if no user data is present.
	 * 
	 * @return the user data type, or null.
	 */
	public Class<?> getUserDataType() {
		if (userData != null) {
			return userData.getClass();
		}
		return null;
	}

	/**
	 * Method to quickly determine if a validity is not defined, meaning
	 * {@code from} as well as {@code to} is {@code null}.
	 * 
	 * @return {@code true} if the validity is undefined.
	 */
	public boolean isUndefined() {
		return from == null && to == null;
	}

	/**
	 * Method to quickly determine if a validity is valid for the current
	 * timestamp. A Validity is considered valid, if all the following is
	 * {@code true}:
	 * <ul>
	 * <li><@code from == null || from <= current UTC timestamp}</li>
	 * <li><@code to == null || to >= current UTC timestamp}</li>
	 * </ul>
	 * 
	 * @return {@code true} if the validity is currently valid.
	 */
	public boolean isValid() {
		long ts = System.currentTimeMillis();
		return (from == null || from <= ts)
				&& (to == null || to >= ts);
	}

	/**
	 * Method to easily check if the {@code from} is not {@code null}.
	 * 
	 * @return {@code true} if {@code from} is not {@code null}.
	 */
	public boolean isLowerBound() {
		return from != null;
	}

	/**
	 * Method to easily check if the {@code from} is not {@code null}.
	 * 
	 * @return {@code true} if {@code from} is not {@code null}.
	 */
	public boolean isUpperBound() {
		return to != null;
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
	 * Access the starting UTC timestamp from which the item T is valid, related
	 * to R.
	 * 
	 * @return the starting UTC timestamp, or null.
	 */
	public Long getFromTimeInMillis() {
		if (from != null) {
			return from;
		}
		return null;
	}

	/**
	 * Access the ending UTC timestamp until the item T is valid, related to R.
	 * 
	 * @return the ending UTC timestamp, or null.
	 */
	public Long getToTimeInMillis() {
		if (to != null) {
			return to;
		}
		return null;
	}

	/**
	 * Return the target time zone for the given validity, which allows to
	 * distinct validities that may embrace several timezones (e.g. for
	 * regions). Additionally the timezone, combined with the UTC timestamp
	 * allows to reconstruct the local date (e.g. within the according
	 * {@link Region}).
	 * 
	 * @return the target timezone id of this validity, never {@code null}.
	 */
	public String getTargetTimezoneId() {
		return targetTimezoneId;
	}

	/**
	 * Access the starting GregorianCalendar from which the item T is valid,
	 * related to R.
	 * 
	 * @return the starting GregorianCalendar, or null.
	 */
	public GregorianCalendar getFrom() {
		return getFrom(GregorianCalendar.class);
	}

	/**
	 * Access the starting Calendar from which the item T is valid, related to
	 * R.
	 * 
	 * @param type
	 *            The calendar type required. The type must have a public
	 *            parameterless constructor and must be initializable by calling
	 *            Calendar#setTimeInMillis(Long).
	 * @return the starting Calendar instance, or null.
	 */
	public <C extends Calendar> C getFrom(Class<C> type) {
		if (from != null) {
			C cal;
			try {
				cal = (C) type.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalArgumentException(
						"Calendar type is not instantiatable.", e);
			}
			cal.setTimeInMillis(from);
			cal.setTimeZone(TimeZone.getTimeZone(targetTimezoneId));
			return cal;
		}
		return null;
	}

	/**
	 * Access the ending GregorianCalendar until which the item T is valid,
	 * related to R.
	 * 
	 * @return the ending GregorianCalendar, or null.
	 */
	public GregorianCalendar getTo() {
		return getTo(GregorianCalendar.class);
	}

	/**
	 * Access the starting Calendar until which the item T is valid, related to
	 * R.
	 * 
	 * @param type
	 *            The calendar type required. The type must have a public
	 *            parameterless constructor and must be initializable by calling
	 *            Calendar#setTimeInMillis(Long).
	 * @return the ending Calendar instance, or null.
	 */
	public <C extends Calendar> C getTo(Class<C> type) {
		if (to != null) {
			C cal;
			try {
				cal = (C) type.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalArgumentException(
						"Calendar type is not instantiatable.", e);
			}
			cal.setTimeInMillis(to);
			cal.setTimeZone(TimeZone.getTimeZone(targetTimezoneId));
			return cal;
		}
		return null;
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
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result
				+ ((validitySource == null) ? 0 : validitySource.hashCode());
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
		@SuppressWarnings("rawtypes")
		ValidityInfo other = (ValidityInfo) obj;
		if (from == null) {
			if (other.from != null) {
				return false;
			}
		} else if (!from.equals(other.from)) {
			return false;
		}
		if (item == null) {
			if (other.item != null) {
				return false;
			}
		} else if (!item.equals(other.item)) {
			return false;
		}
		if (to == null) {
			if (other.to != null) {
				return false;
			}
		} else if (!to.equals(other.to)) {
			return false;
		}
		if (validitySource == null) {
			if (other.validitySource != null) {
				return false;
			}
		} else if (!validitySource.equals(other.validitySource)) {
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
		int compare = 0;
		if (item instanceof Comparable) {
			if (item == null) {
				if (other.item != null) {
					compare = 1;
				}
			} else {
				if (other.item == null) {
					compare = -1;
				} else {
					compare = ((Comparable) item).compareTo(other.item);
				}
			}
		}
		if (from == null) {
			if (other.from != null) {
				compare = 1;
			}
		} else {
			if (other.from == null) {
				compare = -1;
			} else {
				if (other.from == null) {
					compare = -1;
				} else {
					compare = ((Comparable) from).compareTo(other.from);
				}
			}
		}
		if (compare == 0) {
			if (to == null) {
				if (other.to != null) {
					compare = 1;
				}
			} else {
				if (other.to == null) {
					compare = -1;
				} else {
					compare = ((Comparable) to).compareTo(other.to);
				}
			}
		}
		if (compare == 0) {
			if (validitySource == null) {
				if (other.validitySource != null) {
					compare = 1;
				}
			} else {
				if (other.validitySource == null) {
					compare = -1;
				} else {
					compare = ((Comparable) validitySource)
							.compareTo(other.validitySource);
				}
			}
		}
		return compare;
	}
}
