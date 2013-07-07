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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Value type modeling a container for validation results. This allows to
 * multiple validations results from different validator be collected.
 * 
 * @author Anatole Tresch
 */
public final class ValidationResult<T> implements Serializable {

    private T item;
    private Map<String, Object[]> issues = new HashMap<String, Object[]>();

    public ValidationResult(T item) {
	if (item == null) {
	    throw new IllegalArgumentException("item required.");
	}
	this.item = item;
    }

    public ValidationResult(ValidationResult<T> base) {
	if (base == null) {
	    throw new IllegalArgumentException("base required.");
	}
	this.item = base.getItem();
	this.issues.putAll(base.issues);
    }

    public void add(ValidationResult<T> result) {
	if (result == null) {
	    throw new IllegalArgumentException("result required.");
	}
	if (!(this.item == result.getItem())) {
	    throw new IllegalArgumentException("Incompatible results.");
	}
	this.issues.putAll(result.issues);
    }

    public Set<String> getIssueKeys() {
	return Collections.unmodifiableSet(issues.keySet());
    }

    public Object[] getIssueContext(String key) {
	return this.issues.get(key).clone();
    }

    public boolean isValid() {
	return issues.isEmpty();
    }

    public void addIssue(String key, Object... context) {
	if (key == null) {
	    throw new IllegalArgumentException("key required.");
	}
	if (context == null) {
	    throw new IllegalArgumentException("context required.");
	}
	this.issues.put(key, context.clone());
    }

    public T getItem() {
	return item;
    }

}
