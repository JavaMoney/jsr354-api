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
package javax.money.bootstrap;

import java.util.List;

/**
 * This class models the component that is managing the lifecycle of the
 * monetary services used by the Money and Currency API.
 * 
 * @author Anatole Tresch
 */
public interface ServiceProvider {

	public <T> T getService(Class<T> serviceType);

	public <T> T getService(Class<T> serviceType, T defaultInstance);

	public <T> List<T> getServices(Class<T> serviceType);

	public <T> List<T> getServices(Class<T> serviceType, List<T> defaultList);

}
