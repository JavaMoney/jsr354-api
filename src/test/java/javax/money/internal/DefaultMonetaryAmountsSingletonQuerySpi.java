package javax.money.internal;

import javax.money.DummyAmount;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.spi.MonetaryAmountsSingletonQuerySpi;
import javax.money.spi.MonetaryAmountsSingletonSpi;

/**
 * Created by Anatole on 03.05.2014.
 */
public class DefaultMonetaryAmountsSingletonQuerySpi implements MonetaryAmountsSingletonQuerySpi{

    @Override
    public Class<? extends MonetaryAmount> queryAmountType(MonetaryAmountsSingletonSpi amountSpi,
                                                           MonetaryContext requiredContext) {
        return DummyAmount.class;
    }

}
