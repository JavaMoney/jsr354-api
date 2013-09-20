package javax.money.function.predicates;

import java.util.ArrayList;
import java.util.List;


/**
 * This predicate implements the logic {@code or and xor} operations, where
 * {@code OrPredicate(p1,p2) == p1 || p2} or
 * {@code OrPredicate(p1,p2) == (p1 || p2) && !(p1 && p2)}.
 * 
 * @author Anatole Tresch
 */
final class XOrPredicate<T> implements Predicate<T> {
	/** The child predicates. */
	private List<Predicate<? super T>> predicates = new ArrayList<Predicate<? super T>>();

	/**
	 * Creates an XOR predicate.
	 * 
	 * @param predicates
	 *            The child predicates.
	 */
	@SafeVarargs
	XOrPredicate(Iterable<? extends Predicate<? super T>>... predicates) {
		if (predicates != null) {
			for (Iterable<? extends Predicate<? super T>> iterable : predicates) {
				for (Predicate<? super T> predicate : iterable) {
					this.predicates.add(predicate);
				}
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
		boolean state = false;
		for (Predicate<? super T> predicate : predicates) {
			if (predicate.apply(value)) {
				if (!state) {
					state = true;
				}
				else {
					return Boolean.FALSE;
				}
			}
		}
		return state;
	}

}