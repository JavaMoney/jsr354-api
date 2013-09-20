package javax.money.function.predicates;

import java.util.ArrayList;
import java.util.List;

import javax.money.Predicate;


/**
 * This predicate implements the logic {@code or} operations, where
 * {@code OrPredicate(p1,p2) == p1 || p2} .
 * 
 * @author Anatole Tresch
 */
final class OrPredicate<T> implements Predicate<T> {
	/** The child predicates. */
	private List<Predicate<? super T>> predicates = new ArrayList<Predicate<? super T>>();

	/**
	 * Creates an OR predicate.
	 * 
	 * @param predicates
	 *            The child predicates.
	 */
	@SafeVarargs
	OrPredicate(Iterable<? extends Predicate<? super T>>... predicates) {
		for (Iterable<? extends Predicate<? super T>> iterable : predicates) {
			for (Predicate<? super T> predicate : iterable) {
				this.predicates.add(predicate);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryFunction#apply(java.lang.Object)
	 */
	@Override
	public Boolean apply(T value) {
		for (Predicate<? super T> predicate : predicates) {
			if (predicate.apply(value)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

}