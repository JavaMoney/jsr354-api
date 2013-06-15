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
package javax.money.ext;

import java.io.Serializable;

import javax.money.CurrencyUnit;

public abstract class ValidityInfo<S,T> implements Serializable, Comparable<ValidityInfo<S,T>> {

    private static final long serialVersionUID = 1258686258819748870L;
    private S item;
    private T referenceItem;
    private Long from;
    private Long to;
    private String validityType;
    
    public ValidityInfo(S item, T referenceItem, String validityType, Long from, Long to) {
	if(item==null){
	    throw new IllegalArgumentException("Currency required.");
	}
	if(referenceItem==null){
	    throw new IllegalArgumentException("Reference Item required.");
	}
	if(validityType==null){
	    throw new IllegalArgumentException("Validity Type required.");
	}
	this.item = item;
	this.referenceItem = referenceItem;
	this.from = from;
	this.to = to;
	this.validityType = validityType;
    }

    public S getItem() {
	return item;
    }

    public void setItem(S item) {
	this.item = item;
    }

    public T getReferenceItem() {
	return referenceItem;
    }

    public void setReferenceItem(T referenceItem) {
	this.referenceItem = referenceItem;
    }

    public Long getFrom() {
	return from;
    }

    public void setFrom(Long from) {
	this.from = from;
    }

    public Long getTo() {
	return to;
    }

    public void setTo(Long to) {
	this.to = to;
    }

    @Override
    public int compareTo(ValidityInfo arg0) {
	// TODO Auto-generated method stub
	return 0;
    }

}
