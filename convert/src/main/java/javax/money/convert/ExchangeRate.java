/**
 * Copyright (c) 2005, 2012, Werner Keil.
 * All rights reserved. 
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package javax.money.convert;

import javax.money.CurrencyUnit;
import java.util.Date;

/**
 * @author Werner Keil
 * @version 0.2.2
 */
public class ExchangeRate<T> {

	/**
	 * Holds the source CurrencyUnit.
	 */
	private final CurrencyUnit source;

	/**
	 * Holds the target CurrencyUnit.
	 */
	private final CurrencyUnit target;

	/**
	 * Holds the exchange factor.
	 */
	private final T factor;
	
	/**
	 * Holds the exchange factor.
	 */
	private final T inverseFactor;

	/**
	 * Holds the effective (start) date.
	 */
	private final Date fromDate;

	//private final Date interval;
	
	public ExchangeRate(CurrencyUnit source, CurrencyUnit target, T factor,  T inverseFactor,
			Date fromDate, Date toDate) {
		super();
		this.source = source;
		this.target = target;
		this.factor = factor;
		this.inverseFactor = inverseFactor;
		this.fromDate = fromDate;
		//this.interval = new DateInterval(fromDate.getTime(), toDate.getTime());
	}
	
	public ExchangeRate(CurrencyUnit source, CurrencyUnit target, T factor, T inverseFactor,
			Date date) {
		this(source, target, factor, inverseFactor, date, date);
	}

	public ExchangeRate(CurrencyUnit source, CurrencyUnit target, T factor, T inverseFactor) {
		this(source, target, factor, inverseFactor, new Date());
	}

	public CurrencyUnit getSourceCurrency() {
		return source;
	}

	public CurrencyUnit getTargetCurrency() {
		return target;
	}

	public T getFactor() {
		return factor;
	}
	
	public T getInverseFactor() {
		return inverseFactor;
	}

	public boolean isConvertible(){
		return this.factor != null;
	}
	
	public boolean isInverseConvertible(){
		return this.inverseFactor != null;
	}
	
//	public DateInterval getInterval() {
//		return interval;
//	}
}
