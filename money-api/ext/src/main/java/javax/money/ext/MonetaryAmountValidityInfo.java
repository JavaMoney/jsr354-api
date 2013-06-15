package javax.money.ext;

import javax.money.MonetaryAmount;

public class MonetaryAmountValidityInfo<T> extends ValidityInfo<MonetaryAmount, T> {

    public MonetaryAmountValidityInfo(MonetaryAmount item, T referenceItem, String validityType, Long from, Long to) {
	super(item, referenceItem, validityType, from, to);
    }

}
