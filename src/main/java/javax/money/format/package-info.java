/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2013, Credit Suisse All rights reserved.
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

