/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE 
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. 
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY 
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE 
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" 
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.ext;

import javax.money.CurrencyUnit;

public class CurrencyValidity {

	private CurrencyUnit currencyUnit;
	private Region region;
	private Long from;
	private Long to;
	private int fractionUnits;
	private int rounding;
	private int cashRounding;
	private boolean tender;

	public CurrencyUnit getCurrencyUnit() {
		return this.currencyUnit;
	}

	public int getFractionDigits() {
		return fractionUnits;
	}

	public int getRounding() {
		return rounding;
	}

	public int getCashRounding() {
		return cashRounding;
	}

	public Region getRegion() {
		return region;
	}

	public Long getFrom() {
		return from;
	}

	public Long getTo() {
		return to;
	}

	public boolean isTender() {
		return tender;
	}
}
