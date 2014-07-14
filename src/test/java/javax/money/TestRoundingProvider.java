/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package javax.money;

import javax.money.spi.RoundingProviderSpi;

import java.util.*;

public final class TestRoundingProvider implements RoundingProviderSpi {

	@Override
	public Collection<MonetaryRounding> getRoundings(RoundingQuery roundingQuery){
        List<MonetaryRounding> result = new ArrayList<>();
        for(String name: roundingQuery.getRoundingNames()){
            result.add(getCustomRounding(name));
        }
        if(roundingQuery.getCurrencyUnit()!=null){
            result.add(getCustomRounding(roundingQuery.getCurrencyUnit().getCurrencyCode()));
        }
        result.add(getCustomRounding("test"));
        return result;
	}

	private MonetaryRounding getCustomRounding(final String customRoundingId) {
        return new MonetaryRounding(){

            private final RoundingContext CTX = new RoundingContext.Builder("TestRoundingProvider", customRoundingId).build();

            @Override
            public RoundingContext getRoundingContext(){
                return CTX;
            }

            @Override
            public MonetaryAmount apply(MonetaryAmount monetaryAmount){
                switch (customRoundingId) {
                    case "custom1":
                        return monetaryAmount.multiply(2);
                    case "custom2":
                        return monetaryAmount.multiply(3);
                    default:
                        return monetaryAmount;
                }
            }
        };
    }


	@Override
	public Set<String> getRoundingIds() {
        Set<String> result = new HashSet<>();
        result.add("custom1");
        result.add("custom2");
		return result;
	}

}
