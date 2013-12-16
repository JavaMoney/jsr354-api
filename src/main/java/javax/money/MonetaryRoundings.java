/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency
 * API ("Specification") Copyright (c) 2012-2013, Credit Suisse All rights
 * reserved.
 */
package javax.money;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.money.bootstrap.Bootstrap;
import javax.money.spi.RoundingProviderSpi;

/**
 * This class models the accessor for rounding instances, modeled by
 * {@link javax.money.MonetaryOperator}.
 * <p>
 * This class is thread-safe.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 */
public final class MonetaryRoundings {
	/**
	 * An adaptive rounding instance that transparently looks up the correct
	 * rounding.
	 */
	private static final MonetaryOperator DEFAULT_ROUNDING = new DefaultCurrencyRounding();

	/**
	 * Private singleton constructor.
	 */
	private MonetaryRoundings() {
		// Singleton
	}

	/**
	 * Creates a rounding that can be added as {@link MonetaryOperator} to
	 * chained calculations. The instance will lookup the concrete
	 * {@link MonetaryOperator} instance from the {@link MonetaryRoundings}
	 * based on the input {@link MonetaryAmount}'s {@link CurrencyUnit}.
	 * 
	 * @return the (shared) default rounding instance.
	 */
	public static MonetaryOperator getRounding() {
		return DEFAULT_ROUNDING;
	}

	/**
	 * Creates an rounding instance using {@link java.math.RoundingMode#UP} rounding.
	 * 
	 * @param monetaryContext
	 *            The {@link MonetaryContext} defining the required rounding.
	 * @return the corresponding {@link MonetaryOperator} implementing the
	 *         rounding.
	 * @throws MonetaryException
	 *             if no such rounding could be evaluated.
	 */
	public static MonetaryOperator getRounding(MonetaryContext<?> monetaryContext) {
		Objects.requireNonNull(monetaryContext, "MonetaryContext required.");
		for (RoundingProviderSpi prov : Bootstrap
				.getServices(
				RoundingProviderSpi.class)) {
			try {
				MonetaryOperator op = prov.getRounding(monetaryContext);
				if (op != null) {
					return op;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryRoundings.class.getName()).log(
						Level.SEVERE,
						"Error loading RoundingProviderSpi from ptovider: "
								+ prov, e);
			}
		}
		throw new MonetaryException("No Rounding found matching "
				+ monetaryContext);
	}

	/**
	 * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
	 * instances given a currency.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link java.math.RoundingMode}, by default, {@link java.math.RoundingMode#HALF_UP}
	 *            is sued.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding, never {@code null}.
	 */
	public static MonetaryOperator getRounding(CurrencyUnit currency) {
		Objects.requireNonNull(currency, "Currency required.");
		for (RoundingProviderSpi prov : Bootstrap
				.getServices(
				RoundingProviderSpi.class)) {
			try {
				MonetaryOperator op = prov.getRounding(currency);
				if (op != null) {
					return op;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryRoundings.class.getName()).log(
						Level.SEVERE,
						"Error loading RoundingProviderSpi from ptovider: "
								+ prov, e);
			}
		}
		throw new MonetaryException("No Rounding found for currency "
				+ currency);
	}

	/**
	 * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
	 * instances given a currency.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link java.math.RoundingMode}, by default, {@link java.math.RoundingMode#HALF_UP}
	 *            is sued.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding, never {@code null}.
	 */
	public static MonetaryOperator getCashRounding(CurrencyUnit currency) {
		Objects.requireNonNull(currency, "Currency required.");
		for (RoundingProviderSpi prov : Bootstrap
				.getServices(
				RoundingProviderSpi.class)) {
			try {
				MonetaryOperator op = prov.getCashRounding(currency);
				if (op != null) {
					return op;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryRoundings.class.getName()).log(
						Level.SEVERE,
						"Error loading RoundingProviderSpi from ptovider: "
								+ prov, e);
			}
		}
		throw new MonetaryException("No Cash Rounding found for currency "
				+ currency);
	}

	/**
	 * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
	 * instances given a currency, hereby the rounding must be valid for the
	 * given timestamp.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link java.math.RoundingMode}, by default, {@link java.math.RoundingMode#HALF_UP}
	 *            is used.
	 * @param timestamp
	 *            the UTC timestamp.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding, or {@code null}.
	 */
	public static MonetaryOperator getRounding(CurrencyUnit currency,
			long timestamp) {
		Objects.requireNonNull(currency, "Currency required.");
		for (RoundingProviderSpi prov : Bootstrap
				.getServices(
				RoundingProviderSpi.class)) {
			try {
				MonetaryOperator op = prov.getRounding(currency, timestamp);
				if (op != null) {
					return op;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryRoundings.class.getName()).log(
						Level.SEVERE,
						"Error loading RoundingProviderSpi from provider: "
								+ prov, e);
			}
		}
		throw new MonetaryException("No Rounding found for currency "
				+ currency + ", timestamp=" + timestamp);
	}

	/**
	 * Creates an {@link MonetaryOperator} for rounding {@link MonetaryAmount}
	 * instances given a currency, hereby the rounding must be valid for the
	 * given timestamp.
	 * 
	 * @param currency
	 *            The currency, which determines the required precision. As
	 *            {@link java.math.RoundingMode}, by default, {@link java.math.RoundingMode#HALF_UP}
	 *            is sued.
	 * @param timestamp
	 *            the UTC timestamp.
	 * @return a new instance {@link MonetaryOperator} implementing the
	 *         rounding, or {@code null}.
	 */
	public static MonetaryOperator getCashRounding(CurrencyUnit currency,
			long timestamp) {
		Objects.requireNonNull(currency, "Currency required.");
		for (RoundingProviderSpi prov : Bootstrap
				.getServices(
				RoundingProviderSpi.class)) {
			try {
				MonetaryOperator op = prov.getCashRounding(currency, timestamp);
				if (op != null) {
					return op;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryRoundings.class.getName()).log(
						Level.SEVERE,
						"Error loading RoundingProviderSpi from ptovider: "
								+ prov, e);
			}
		}
		throw new MonetaryException("No Rounding found for currency "
				+ currency + ", timestamp=" + timestamp);
	}

	/**
	 * Access an {@link MonetaryOperator} for custom rounding
	 * {@link MonetaryAmount} instances.
	 * 
	 * @param customRoundingId
	 *            The customRounding identifier.
	 * @return the corresponding {@link MonetaryOperator} implementing the
	 *         rounding, never {@code null}.
	 * @throws IllegalArgumentException
	 *             if no such rounding is registered using a
	 *             {@link RoundingProviderSpi} instance.
	 */
	public static MonetaryOperator getRounding(String customRoundingId) {
		Objects.requireNonNull(customRoundingId, "CustomRoundingId required.");
		for (RoundingProviderSpi prov : Bootstrap
				.getServices(
				RoundingProviderSpi.class)) {
			try {
				MonetaryOperator op = prov.getCustomRounding(customRoundingId);
				if (op != null) {
					return op;
				}
			} catch (Exception e) {
				Logger.getLogger(MonetaryRoundings.class.getName()).log(
						Level.SEVERE,
						"Error loading RoundingProviderSpi from provider: "
								+ prov, e);
			}
		}
		throw new MonetaryException("No Custom Rounding found with id "
				+ customRoundingId);
	}

	/**
	 * Allows to access the identifiers of the current defined custom roundings.
	 * 
	 * @return the set of custom rounding ids, never {@code null}.
	 */
	public static Set<String> getCustomRoundingIds() {
		Set<String> result = new HashSet<String>();
		for (RoundingProviderSpi prov : Bootstrap
				.getServices(
				RoundingProviderSpi.class)) {
			try {
				result.addAll(prov.getCustomRoundingIds());
			} catch (Exception e) {
				Logger.getLogger(MonetaryRoundings.class.getName()).log(
						Level.SEVERE,
						"Error loading RoundingProviderSpi from provider: "
								+ prov, e);
			}
		}
		return result;
	}

	/**
	 * Default Rounding that rounds a {@link MonetaryAmount} based on the
	 * amount's {@link CurrencyUnit} {@link Currency}.
	 * 
	 * @author Anatole Tresch
	 */
	private static final class DefaultCurrencyRounding implements
			MonetaryOperator {
		@Override
		public <T extends MonetaryAmount<T>> T apply(T amount) {
			MonetaryOperator r = MonetaryRoundings.getRounding(amount
					.getCurrency());
			return r.apply(amount);
		}
	}
}
