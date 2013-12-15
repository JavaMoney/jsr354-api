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
 * This package defines the Money and Currency format API bootstrap artifacts.
 * In more detail:
 * <ul>
 * <li>By default all dependent services are loaded using the JDK
 * {@link java.util.ServiceLoader}.</li>
 * <li>This default loader can be changed by reimplementing
 * {@link javax.money.bootstrap.ServiceProvider} and calling
 * {@link javax.money.bootstrap.Bootstrap#setServiceProvider(ServiceProvider)}
 * before the Money & Currency API is used the first time.
 * </ul>
 */
package javax.money.bootstrap;

