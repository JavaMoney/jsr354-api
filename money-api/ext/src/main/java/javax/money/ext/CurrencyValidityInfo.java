package javax.money.ext;

import javax.money.CurrencyUnit;

public class CurrencyValidityInfo<T> extends ValidityInfo<CurrencyUnit, T> {

    private static final long serialVersionUID = 1L;

    public CurrencyValidityInfo(CurrencyUnit item, T referenceItem, String validityType, Long from, Long to) {
	super(item, referenceItem, validityType, from, to);
    }

}
