package javax.money.function.predicates;

import java.util.ArrayList;
import java.util.List;


/**
 * This predicate implements the logic {@code and} operations, where
 * {@code AndPredicate(p1,p2) == p1 && p2}.
 * 
 * @author Anatole Tresch
 */
final class AndPredicate<T> implements Predicate<T> {
	/** The child predicates. */
	private List<Predicate<? super T>> predicates = new ArrayList<Predicate<? super T>>();

	@SafeVarargs
	AndPredicate(Iterable<? extends Predicate<? super T>>... predicates) {
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
			if (!predicate.apply(value)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

}