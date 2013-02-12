/*
 *  Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Contributors:
 *    Anatole Tresch - initial implementation
 */
package net.java.javamoney.ri.format.token;

import javax.money.format.SignPlacement;
import javax.money.format.common.LocalizationStyle;

import net.java.javamoney.ri.format.FormatDecorator;
import net.java.javamoney.ri.format.common.AbstractFormatDecorator;

/**
 * {@link FormatDecorator} that models the representation of an arbitrary sign
 * for a positive {@link Number}. This decorator will not affect any output,
 * when the {@link Number}'s value is zero or negative.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            The concrete {@link Number} type.
 */
public class PositiveSign<T extends Number> extends AbstractFormatDecorator<T> {

	private String beforeSign;
	private String afterSign;
	private SignPlacement signPlacement = SignPlacement.BEFORE;

	public PositiveSign<T> setBeforeSign(String sign) {
		this.beforeSign = sign;
		this.signPlacement = SignPlacement.BEFORE;
		return this;
	}
	
	public PositiveSign<T> setAfterSign(String sign) {
		this.beforeSign = sign;
		this.signPlacement = SignPlacement.AFTER;
		return this;
	}
	
	public PositiveSign<T> setAroundSigns(String beforeSign, String afterSign) {
		this.beforeSign = beforeSign;
		this.afterSign = afterSign;
		this.signPlacement = SignPlacement.AROUND;
		return this;
	}
	
	public String getBeforeSign(){
		return beforeSign;
	}
	
	public String getAfterSign(){
		return afterSign;
	}

	public SignPlacement getSignPlacement() {
		return this.signPlacement;
	}

	@Override
	protected String decorateInternal(Number item, String formattedString,
			LocalizationStyle style) {
		switch (signPlacement) {
		case OMIT:
			return formattedString;
		case BEFORE:
			return getSign(item, beforeSign) + formattedString;
		case AFTER:
			return formattedString +getSign(item, afterSign);
		case AROUND:
			return getSign(item, beforeSign) + formattedString + getSign(item, afterSign);
		}
		return formattedString;
	}
	
	private String getSign(Number item, String sign) {
		if(item.doubleValue()>0){
			return sign;
		}
		return "";
	}

}
