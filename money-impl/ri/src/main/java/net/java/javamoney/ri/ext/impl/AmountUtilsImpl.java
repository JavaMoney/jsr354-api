package net.java.javamoney.ri.ext.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.Rounding;
import javax.money.ext.AmountUtils;
import javax.money.provider.ExposedExtensionType;
import javax.money.provider.MonetaryExtension;

@Singleton
@ExposedExtensionType(AmountUtils.class)
public class AmountUtilsImpl implements AmountUtils, MonetaryExtension {


	@Override
	public MonetaryAmount min(MonetaryAmount... amounts) {
		if (amounts.length == 1) {
			return amounts[0];
		}
		MonetaryAmount am = amounts[0];
		for (int i = 1; i < amounts.length; i++) {
			if(am.isGreaterThan(amounts[i]))
			am = amounts[i];
		}
		return am;
	}

	@Override
	public MonetaryAmount min(Iterable<MonetaryAmount> amounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount average(MonetaryAmount... amounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount average(Iterable<MonetaryAmount> amounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount max(MonetaryAmount... amounts) {
		if (amounts.length == 1) {
			return amounts[0];
		}
		MonetaryAmount am = amounts[0];
		for (int i = 1; i < amounts.length; i++) {
			if(am.isLessThan(amounts[i]))
			am = amounts[i];
		}
		return am;
	}

	@Override
	public MonetaryAmount max(Iterable<MonetaryAmount> amounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount roundedTotal(Rounding rounding,
			MonetaryAmount... amounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount roundedTotal(Rounding rounding,
			Iterable<MonetaryAmount> amounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount total(MonetaryAmount... amounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount total(Iterable<MonetaryAmount> amounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount total(CurrencyUnit currency,
			MonetaryAmount... amounts) {
		if (amounts.length == 1) {
			return amounts[0];
		}
		MonetaryAmount am = amounts[0];
		for (int i = 1; i < amounts.length; i++) {
			am = am.add(amounts[i]);
		}
		return am;
	}

	@Override
	public MonetaryAmount total(CurrencyUnit currency,
			Iterable<MonetaryAmount> amounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount nonNull(MonetaryAmount amount, CurrencyUnit currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount[] divideAndSeparate(MonetaryAmount total,
			Number divisor, boolean addDifferenceToLastValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonetaryAmount[] divideAndSeparate(MonetaryAmount total,
			Number divisor, Rounding rounding, boolean addDifferenceToLastValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<CurrencyUnit, List<MonetaryAmount>> separateCurrencies(
			MonetaryAmount... amounts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<CurrencyUnit, List<MonetaryAmount>> separateCurrencies(
			Iterable<MonetaryAmount> amounts) {
		// TODO Auto-generated method stub
		return null;
	}

}
