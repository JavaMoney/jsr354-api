package javax.money.internal;

import javax.money.*;
import javax.money.spi.MonetaryAmountsSingletonQuerySpi;
import javax.money.spi.MonetaryAmountsSingletonSpi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Anatole on 03.05.2014.
 */
public class DefaultMonetaryAmountsSingletonQuerySpi implements MonetaryAmountsSingletonQuerySpi{

    private List<MonetaryAmountFactory<?>> factories = new ArrayList<>();

    public DefaultMonetaryAmountsSingletonQuerySpi(){
        factories.add(new DummyAmountFactory());
        factories = Collections.unmodifiableList(factories);
    }

    @Override
    public Collection<MonetaryAmountFactory<?>> getAmountFactories(MonetaryAmountFactoryQuery query){
        return factories;
    }
}
