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

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryContext;
import javax.money.MonetaryOperator;
import javax.money.spi.CurrencyProviderSpi;
import javax.money.spi.RoundingProviderSpi;

public final class TestRoundingProvider implements RoundingProviderSpi {

	@Override
	public MonetaryOperator getRounding(CurrencyUnit currency) {
		return new MonetaryOperator() {
			@Override
			public MonetaryAmount apply(MonetaryAmount value) {
				return value;
			}
		};
	}

	@Override
	public MonetaryOperator getRounding(CurrencyUnit currency, long timestamp) {
		return new MonetaryOperator() {
			@Override
			public MonetaryAmount apply(MonetaryAmount value) {
				return value;
			}
		};
	}

	@Override
	public MonetaryOperator getCashRounding(CurrencyUnit currency) {
		return new MonetaryOperator() {
			@Override
			public MonetaryAmount apply(MonetaryAmount value) {
				return value;
			}
		};
	}

	@Override
	public MonetaryOperator getCashRounding(CurrencyUnit currency,
			long timestamp) {
		return new MonetaryOperator() {
			@Override
			public MonetaryAmount apply(MonetaryAmount value) {
				return value;
			}
		};
	}

	@Override
	public MonetaryOperator getCustomRounding(String customRoundingId) {
		if ("custom1".equals(customRoundingId)) {
            return new MonetaryOperator() {
                @Override
                public MonetaryAmount apply(MonetaryAmount value) {
                    return value;
                }
            };
        }
        else if ("custom2".equals(customRoundingId)) {
            return new MonetaryOperator() {
                @Override
                public MonetaryAmount apply(MonetaryAmount value) {
                    return value;
                }
            };
        }
		return null;
	}

	@Override
	public MonetaryOperator getRounding(MonetaryContext monetaryContext) {
		return new MonetaryOperator() {
			@Override
			public <T extends MonetaryAmount> T apply(T value){
				return value;
			}
		};
	}

	@Override
	public Set<String> getCustomRoundingIds() {
        Set<String> result = new HashSet<>();
        result.add("custom1");
        result.add("custom2");
		return result;
	}

}
