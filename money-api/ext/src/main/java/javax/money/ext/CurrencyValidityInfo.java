package javax.money.ext;

import javax.money.CurrencyUnit;

/**
 * This class models a validity info for a CurrencyUnit.
 * 
 * @author Anatole Tresch
 * 
 * @param <R>
 *            the type of reference, for which the CurrencyUnit is valid.
 */
public class CurrencyValidityInfo extends ValidityInfo<CurrencyUnit, Region> {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new Validity info for a CurrencyUnit.
     * 
     * @param item
     *            the CurrencyUnit
     * @param referenceItem
     *            the reference instance
     * @param validitySource
     *            the source of the validity information
     * @param from
     *            the starting UTC timestamp
     * @param to
     *            the ending UTC timestamp
     */
    public CurrencyValidityInfo(CurrencyUnit item, Region region, String validitySource, Long from, Long to) {
	super(item, region, validitySource, from, to);
    }

}
