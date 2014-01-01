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
package javax.money.spi;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementation of a {@link MonetaryLogger}, using a JUL
 * {@link Logger} for logging.
 * 
 * @author Anatole Tresch
 */
@ServicePriority(ServicePriority.LOW_PRIORITY)
public class DefaultMonetaryLogger implements MonetaryLogger {
	/** The Logger used. */
	private static final Logger LOGGER = Logger
			.getLogger(DefaultMonetaryLogger.class
					.getName());

	/* (non-Javadoc)
	 * @see javax.money.spi.MonetaryLogger#logDebug(java.lang.String)
	 */
	@Override
	public void logDebug(String message) {
		LOGGER.finest(message);
	}

	/* (non-Javadoc)
	 * @see javax.money.spi.MonetaryLogger#logInfo(java.lang.String)
	 */
	@Override
	public void logInfo(String message) {
		LOGGER.info(message);
	}

	/* (non-Javadoc)
	 * @see javax.money.spi.MonetaryLogger#logWarning(java.lang.String)
	 */
	@Override
	public void logWarning(String message) {
		LOGGER.warning(message);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.money.spi.MonetaryLogger#logError(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void logError(String message, Throwable t) {
		LOGGER.log(Level.SEVERE, message, t);
	}

}
