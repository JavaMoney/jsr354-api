/*
 * Copyright (c) 2012-2013, Credit Suisse
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-354 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.money.ext;

import javax.money.CurrencyUnit;
import javax.money.provider.MonetaryExtension;

/**
 * This class models the component defined by JSR 354 that provides accessors
 * for {@link CurrencyUnit} using {@link Region}s. It is provided by the
 * Monetary singleton.
 * 
 * @author Anatole Tresch
 */
public interface RegionalCurrencyUnitProvider extends MonetaryExtension {

	/**
	 * Access all currencies matching a {@link Region}.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @return the currencies found, never null.
	 */
	public CurrencyUnit[] getAll(Region region);

	/**
	 * Access all currencies matching a {@link Region}, valid at the given
	 * timestamp.
	 * 
	 * @param locale
	 *            the target locale, not null.
	 * @param timestamp
	 *            The target UTC timestamp, or -1 for the current UTC timestamp.
	 * @return the currencies found, never null.
	 */
	public CurrencyUnit[] getAll(Region region, long timestamp);

	/**
	 * This method allows to evaluate if a {@link CurrencyUnit} is a legal
	 * tender for a certain region, or country. For example Indian rupee are
	 * accepted also as legal tender in Nepal and Buthan, whereas Nepalese rupee
	 * or Bhutanese ngultrum are not accepted as legal tender in India.
	 * 
	 * @param currency
	 *            The currency to be requested, not null.
	 * @param region
	 *            The region to be requested, not null.
	 * @return true if the currency is accepted as legal tender in the current
	 *         region.
	 */
	public boolean isLegalTender(CurrencyUnit currency, Region region);

	/**
	 * This method allows to evaluate if a {@link CurrencyUnit} is a legal
	 * tender for a certain region, or country. For example Indian rupee are
	 * accepted also as legal tender in Nepal and Buthan, whereas Nepalese rupee
	 * or Bhutanese ngultrum are not accepted as legal tender in India.
	 * 
	 * @param currency
	 *            The currency to be requested, not null.
	 * @param region
	 *            The region to be requested, not null.
	 * @param timestamp
	 *            the UTC timestamp, or null for the current time.
	 * @return true if the currency is accepted as legal tender in the current
	 *         region.
	 */
	public boolean isLegalTender(CurrencyUnit currency, Region region,
			Long timestamp);

	/**
	 * This method allows to evaluate the {@link CurrencyUnit} accepted as legal
	 * tenders for a {@link Region}.
	 * 
	 * @param region
	 *            The region to be requested, not null.
	 * @return the {@link CurrencyUnit} to be known as legal tenders for the
	 *         given region, never null.
	 */
	public CurrencyUnit[] getLegalTenders(Region region);

	/**
	 * This method allows to evaluate the {@link CurrencyUnit} accepted as legal
	 * tenders for a {@link Region}.
	 * 
	 * @param region
	 *            The region to be requested, not null.
	 * @param timestamp
	 *            the UTC timestamp, or null for the current time.
	 * @return the {@link CurrencyUnit} to be known as legal tenders for the
	 *         given region, never null.
	 */
	public CurrencyUnit[] getLegalTenders(Region region, Long timestamp);

}
