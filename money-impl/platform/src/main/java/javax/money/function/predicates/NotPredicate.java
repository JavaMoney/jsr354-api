package javax.money.function.predicates;


/**
 * This predicate implements the logic {@code or and xor} operations, where
 * {@code OrPredicate(p1,p2) == p1 || p2} or
 * {@code OrPredicate(p1,p2) == (p1 || p2) && !(p1 && p2)}.
 * 
 * @author Anatole Tresch
 */
final class NotPredicate<T> implements Predicate<T> {
	/** The child predicates. */
	private Predicate<? super T> predicate;

	/**
	 * Creates an NOT predicate.
	 * 
	 * @param predicate
	 *            The predicate to be inversed.
	 */
	NotPredicate(Predicate<? super T> predicate) {
		if (predicate == null) {
			throw new IllegalArgumentException("predicate required.");
		}
		this.predicate = predicate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.money.MonetaryFunction#apply(java.lang.Object)
	 */
	@Override
	public Boolean apply(T value) {
		return !predicate.apply(value);
	}

}