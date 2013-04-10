package javax.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MonetaryCalculations {

	private MonetaryCalculations() {
	}

	public static MonetaryAmount min(MonetaryAmount... amounts) {
		if (amounts.length == 1) {
			return amounts[0];
		}
		return min(Arrays.asList(amounts));
	}

	public static MonetaryAmount min(Iterable<MonetaryAmount> amounts) {
		if (amounts == null || !amounts.iterator().hasNext()) {
			throw new IllegalArgumentException("amoiunts required.");
		}
		MonetaryAmount am = null;
		for (MonetaryAmount amount : amounts) {
			if (am == null) {
				am = amount;
				continue;
			}
			if (am.isGreaterThan(amount)) {
				am = amount;
			}
		}
		return am;
	}

	public static MonetaryAmount max(MonetaryAmount... amounts) {
		if (amounts.length == 1) {
			return amounts[0];
		}
		return max(Arrays.asList(amounts));
	}

	public static MonetaryAmount max(Iterable<MonetaryAmount> amounts) {
		if (amounts == null || !amounts.iterator().hasNext()) {
			throw new IllegalArgumentException("amoiunts required.");
		}
		MonetaryAmount am = null;
		for (MonetaryAmount amount : amounts) {
			if (am == null) {
				am = amount;
				continue;
			}
			if (am.isLessThan(amount)) {
				am = amount;
			}
		}
		return am;
	}

	public static MonetaryAmount average(MonetaryAmount... amounts) {
		if (amounts.length == 1) {
			return amounts[0];
		}
		MonetaryAmount total = total(amounts);
		return total.divide(amounts.length);
	}

	public static MonetaryAmount average(Collection<MonetaryAmount> amounts) {
		MonetaryAmount total = total(amounts);
		return total.divide(amounts.size());
	}

	public static MonetaryAmount roundedTotal(Rounding rounding,
			MonetaryAmount... amounts) {
		MonetaryAmount total = total(amounts);
		return rounding.adjust(total);
	}

	public static MonetaryAmount roundedTotal(Rounding rounding,
			Iterable<MonetaryAmount> amounts) {
		MonetaryAmount total = total(amounts);
		return rounding.adjust(total);
	}

	public static MonetaryAmount total(MonetaryAmount... amounts) {
		if(amounts==null){
			throw new IllegalArgumentException("Amounts required.");
		}
		if (amounts.length == 1) {
			return amounts[0];
		}
		return total(Arrays.asList(amounts));
	}

	public static MonetaryAmount total(Iterable<MonetaryAmount> amounts) {
		if(amounts==null){
			throw new IllegalArgumentException("Amounts required.");
		}
		MonetaryAmount am = null;
		for (MonetaryAmount amount : amounts) {
			if (am == null) {
				am = amount;
				continue;
			}
			am = am.add(amount);
		}
		return am;
	}

	public static Collection<MonetaryAmount> filter(CurrencyUnit currency,
			MonetaryAmount... amounts) {
		if(amounts==null){
			throw new IllegalArgumentException("Amounts required.");
		}
		if(currency==null){
			throw new IllegalArgumentException("currency required.");
		}
		List<MonetaryAmount> result = new ArrayList<MonetaryAmount>();
		for (int i = 1; i < amounts.length; i++) {
			if (amounts[i].getCurrency().equals(currency)) {
				result.add(amounts[i]);
			}
		}
		return result;
	}

	public static Collection<MonetaryAmount> filter(CurrencyUnit currency,
			Iterable<MonetaryAmount> amounts) {
		if(amounts==null){
			throw new IllegalArgumentException("Amounts required.");
		}
		if(currency==null){
			throw new IllegalArgumentException("currency required.");
		}
		List<MonetaryAmount> result = new ArrayList<MonetaryAmount>();
		for (MonetaryAmount am : amounts) {
			if (am.getCurrency().equals(currency)) {
				result.add(am);
			}
		}
		return result;
	}

	public static MonetaryAmount total(CurrencyUnit currency,
			MonetaryAmount... amounts) {
		return total(filter(currency, amounts));
	}

	public static MonetaryAmount total(CurrencyUnit currency,
			Iterable<MonetaryAmount> amounts) {
		return total(filter(currency, amounts));
	}

	public static Map<CurrencyUnit, Collection<MonetaryAmount>> separateCurrencies(
			MonetaryAmount... amounts) {
		if(amounts==null){
			throw new IllegalArgumentException("Amounts required.");
		}
		Map<CurrencyUnit, Collection<MonetaryAmount>> result = new HashMap<CurrencyUnit, Collection<MonetaryAmount>>();
		for (MonetaryAmount am : amounts) {
			CurrencyUnit cu = am.getCurrency();
			Collection<MonetaryAmount> list = result.get(cu);
			if (list == null) {
				list = new ArrayList<MonetaryAmount>();
				result.put(cu, list);
			}
			list.add(am);
		}
		return result;
	}

	public static Map<CurrencyUnit, Collection<MonetaryAmount>> separateCurrencies(
			Iterable<MonetaryAmount> amounts) {
		if(amounts==null){
			throw new IllegalArgumentException("Amounts required.");
		}
		Map<CurrencyUnit, Collection<MonetaryAmount>> result = new HashMap<CurrencyUnit, Collection<MonetaryAmount>>();
		for (MonetaryAmount am : amounts) {
			CurrencyUnit cu = am.getCurrency();
			Collection<MonetaryAmount> list = result.get(cu);
			if (list == null) {
				list = new ArrayList<MonetaryAmount>();
				result.put(cu, list);
			}
			list.add(am);
		}
		return result;
	}
	
	/**
	 * Allows to check, if the currency of the two amounts are the same, meaning
	 * that corresponding currency name spaces and currency codes must be equal.
	 * 
	 * @see CurrencyUnit#getNamespace()
	 * @see CurrencyUnit#getCurrencyCode()
	 * @param amount
	 *            The amount to compare to, not {@code null}.
	 * @return {@code true}, if the {@link CurrencyUnit} of this instance has
	 *         the same name space and code.
	 */
	public static boolean hasSameCurrency(MonetaryAmount... amounts){
		if(amounts==null){
			throw new IllegalArgumentException("Amounts required.");
		}
		CurrencyUnit unit = null;
		for(MonetaryAmount am: amounts){
			if(unit==null){
				unit = am.getCurrency();
				continue;
			}
			if(!unit.getNamespace().equals(am.getCurrency().getNamespace())){
				return false;
			}
			if(!unit.getCurrencyCode().equals(am.getCurrency().getCurrencyCode())){
				return false;
			}
		}
		return true;
	}

	/**
	 * Allows to check, if the currency of the two amounts are the same, meaning
	 * that corresponding currency name spaces and currency codes must be equal.
	 * 
	 * @see CurrencyUnit#getNamespace()
	 * @see CurrencyUnit#getCurrencyCode()
	 * @param amount
	 *            The amount to compare to, not {@code null}.
	 * @return {@code true}, if the {@link CurrencyUnit} of this instance has
	 *         the same name space and code.
	 */
	public static boolean hasSameCurrency(Iterable<MonetaryAmount> amounts){
		if(amounts==null){
			throw new IllegalArgumentException("Amounts required.");
		}
		CurrencyUnit unit = null;
		for(MonetaryAmount am: amounts){
			if(unit==null){
				unit = am.getCurrency();
				continue;
			}
			if(!unit.getNamespace().equals(am.getCurrency().getNamespace())){
				return false;
			}
			if(!unit.getCurrencyCode().equals(am.getCurrency().getCurrencyCode())){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Gets the amount in major units as a {@code long}.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 2, and 'BHD -1.345' will return -1.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the major units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for a {@code long}
	 */
	public static long getMajorLong(MonetaryAmount amount) {
		if(amount==null){
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return number.setScale(0, RoundingMode.DOWN).longValueExact();
	}

	/**
	 * Gets the amount in major units as an {@code int}.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 2, and 'BHD -1.345' will return -1.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the major units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for an {@code int}
	 */
	public static int getMajorInt(MonetaryAmount amount) {
		if(amount==null){
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return number.setScale(0, RoundingMode.DOWN).intValueExact();
	}

	/**
	 * Gets the amount in minor units as a {@code long}.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 235, and 'BHD -1.345' will return -1345.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for a {@code long}
	 */
	public static long getMinorLong(MonetaryAmount amount) {
		if(amount==null){
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return number.movePointRight(number.precision())
				.longValueExact();
	}

	/**
	 * Gets the amount in minor units as an {@code int}.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 235, and 'BHD -1.345' will return -1345.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount
	 * @throws ArithmeticException
	 *             if the amount is too large for an {@code int}
	 */
	public static int getMinorInt(MonetaryAmount amount) {
		if(amount==null){
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return amount.asType(BigDecimal.class).movePointRight(amount.asType(BigDecimal.class).precision())
				.intValueExact();
	}
	
	/**
	 * Gets the amount in major units as a {@code MonetaryAmount} with scale 0.
	 * <p>
	 * This returns the monetary amount in terms of the major units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 'EUR 2', and 'BHD -1.345' will return 'BHD -1'.
	 * <p>
	 * This is returned as a {@code MonetaryAmount} rather than a
	 * {@code BigInteger} . This is to allow further calculations to be
	 * performed on the result. Should you need a {@code BigInteger}, simply
	 * call {@code asType(BigInteger.class)}.
	 * 
	 * @return the major units part of the amount, never {@code null}
	 */
	public static MonetaryAmount getMajorPart(MonetaryAmount amount){
		if(amount==null){
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return amount.from(number.setScale(0, RoundingMode.DOWN));
	}


	/**
	 * Gets the amount in minor units as a {@code MonetaryAmount} with scale 0.
	 * <p>
	 * This returns the monetary amount in terms of the minor units of the
	 * currency, truncating the amount if necessary. For example, 'EUR 2.35'
	 * will return 'EUR 235', and 'BHD -1.345' will return 'BHD -1345'.
	 * <p>
	 * This is returned as a {@code MonetaryAmount} rather than a
	 * {@code BigInteger} . This is to allow further calculations to be
	 * performed on the result. Should you need a {@code BigInteger}, simply
	 * call {@link asType(BigInteger.class)}.
	 * <p>
	 * This method matches the API of {@link java.math.BigDecimal}.
	 * 
	 * @return the minor units part of the amount, never null
	 */
	public static MonetaryAmount getMinorPart(MonetaryAmount amount){
		if(amount==null){
			throw new IllegalArgumentException("Amount required.");
		}
		BigDecimal number = amount.asType(BigDecimal.class);
		return amount.from(number.movePointRight(amount.asType(BigDecimal.class).precision()).longValueExact());
	}
}
