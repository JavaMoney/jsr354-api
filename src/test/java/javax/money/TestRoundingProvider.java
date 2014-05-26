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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class TestRoundingProvider implements RoundingProviderSpi {

	@Override
	public MonetaryOperator getRounding(RoundingContext roundingContext){
        if (Objects.nonNull(roundingContext.getRoundingId())) {
            return getCustomRounding(roundingContext.getRoundingId());
        }
        return value -> value;
	}

	public MonetaryOperator getCustomRounding(String customRoundingId) {
        switch (customRoundingId) {
            case "custom1":
                return value -> value.multiply(2);
            case "custom2":
                return value -> value.multiply(3);
            default:
                return value -> value;
        }
    }


	@Override
	public Set<String> getRoundingIds() {
        Set<String> result = new HashSet<>();
        result.add("custom1");
        result.add("custom2");
		return result;
	}

}
