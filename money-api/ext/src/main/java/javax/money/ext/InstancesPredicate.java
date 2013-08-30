package javax.money.ext;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.money.MonetaryFunction;
//
//public class InstancesPredicate<T> implements
//		MonetaryFunction<T, Boolean> {
//
//	/** The base item. */
//	private Set<T> instances = new HashSet<T>();
//
//	/**
//	 * Sets the {@link #from} timestamp and {@link #targetTimezoneId} from
//	 * the given {@link Calendar}.
//	 * 
//	 * @param calendar
//	 *            The from calendar.
//	 * @return the instance, for chaining.
//	 */
//	public InstancesPredicate<T> withInstances(
//			T... instances) {
//		if (instances == null) {
//			throw new IllegalArgumentException(
//					"instances required");
//		}
//		this.instances.addAll(Arrays.asList(instances));
//		return this;
//	}
//
//	/**
//	 * Sets the {@link #from} timestamp and {@link #targetTimezoneId} from
//	 * the given {@link Calendar}.
//	 * 
//	 * @param calendar
//	 *            The from calendar.
//	 * @return the instance, for chaining.
//	 */
//	public InstancesPredicate<T> withInstances(
//			Collection<? extends T> instances) {
//		if (instances == null) {
//			throw new IllegalArgumentException(
//					"instances required");
//		}
//		this.instances.addAll(instances);
//		return this;
//	}
//
//	/**
//	 * Access the related items for which the validities are queried.
//	 * 
//	 * @see #getRelatedToType()
//	 * 
//	 * @return the item The item for which the validity information is
//	 *         queried.
//	 */
//	public final Set<T> getInstances() {
//		return instances;
//	}
//
//	@Override
//	public Boolean apply(T value) {
//		return instances.contains(value);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return "InstancesPredicate [instances=" + instances + "]";
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result
//				+ ((instances == null) ? 0 : instances.hashCode());
//		return result;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#equals(java.lang.Object)
//	 */
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		InstancesPredicate other = (InstancesPredicate) obj;
//		if (instances == null) {
//			if (other.instances != null)
//				return false;
//		} else if (!instances.equals(other.instances))
//			return false;
//		return true;
//	}
//
//}