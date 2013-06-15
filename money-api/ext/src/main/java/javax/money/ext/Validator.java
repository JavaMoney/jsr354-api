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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.money.MonetaryFunction;

/**
 *
 * @author Anatole
 */
public final class Validator<T> implements MonetaryFunction<T, ValidationResult> {

    private List<Validation<T>> validations = new ArrayList<Validation<T>>();
    private Class<T> targetType;

    private Validator(Class<T> targetType,
            List<Validation<T>> validations) {
        if (targetType == null) {
            throw new IllegalArgumentException("targetType required.");
        }
        this.targetType = targetType;
        if (validations == null) {
            throw new IllegalArgumentException("validations required.");
        }
        this.validations = validations;
    }

    public Class<T> getTargetType() {
        return this.targetType;
    }

    public ValidationResult apply(T value) {
        ValidationResult result = new ValidationResult(value);
        for (Validation<T> validation : validations) {
            validation.validate(value, result);
        }
        return result;
    }

    public boolean isValid(T value) {
        return apply(value).isValid();
    }

    public static final class Builder<T> {

        private List<Validation<T>> validations = new ArrayList<Validation<T>>();
        private Class<T> targetType;

        public Builder() {
        }
        
        public Builder(Class<T> type) {
            setTargetType(type);
        }

        public Builder setTargetType(Class<T> type) {
            if (targetType == null) {
                throw new IllegalArgumentException("targetType required.");
            }
            this.targetType = targetType;
            return this;
        }

        public Builder addValidations(Validation<T>... validations) {
            for (Validation<T> validation : validations) {
                this.validations.add(validation);
            }
            return this;
        }

        public void clearValidations() {
            this.validations.clear();
        }

        public Builder setValidations(Validation<T>... validations) {
            this.validations.clear();
            this.validations.addAll(Arrays.asList(validations));
            return this;
        }

        public Builder setValidations(Collection<Validation<T>> validations) {
            this.validations.clear();
            this.validations.addAll(validations);
            return this;
        }

        public Validator build() {
            return new Validator(targetType, validations);
        }
    }

}
