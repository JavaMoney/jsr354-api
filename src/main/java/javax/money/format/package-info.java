/*
 * Copyright 2012-2016 Credit Suisse
 * Copyright 2018 Werner Keil, Otavio Santana, Trivadis AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Money and Currency format API. In more detail:
 * <ul>
 * <li>JSR 354 defines a minimal {@link javax.money.format.MonetaryAmountFormat} that adopts
 * existing formatting functionality, such as in <code>javax.text.DecimalFormat</code>.</li>
 * <li>Some of the functionality from <code>javax.text.DecimalFormat</code> are remodeled, to be
 * platform independent. Nevertheless the reference implementation may be built on top of existing JDK
 * functionality.</li>
 * <li>Additionally it adds customizable grouping sizes and characters as well as additional
 * (extensible) currency formatting capabilities.</li>
 * </ul>
 */
package javax.money.format;

