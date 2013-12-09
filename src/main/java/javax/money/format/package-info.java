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
/**
 * This package defines the Money and Currency format API. In more detail:
 * <ul>
 * <li>JSR 354 defines a minimal {@link javax.money.format.MonetaryAmountFormat} that
 * uses the JDK's {@link java.text.DecimalFormat} internally, but adds some additional
 * functionality.</li>
 * <li>Hereby basically {@link java.text.DecimalFormat} is used for the number rendering, whereas
 * the rendering and placement of the currrency can be configured more explicitly.</li>
 * <li>Additionally the format supports also different grouping sizes and characters.</li>
 * </ul>
 */
package javax.money.format;

