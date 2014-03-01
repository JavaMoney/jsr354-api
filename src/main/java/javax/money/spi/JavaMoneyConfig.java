/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU
 * ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS
 * AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF
 * THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE
 * BOTTOM OF THIS PAGE. Specification: JSR-354 Money and Currency API ("Specification") Copyright
 * (c) 2012-2014, Credit Suisse All rights reserved.
 */
package javax.money.spi;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loader for the Java Money configuration.
 * 
 * @author Anatole Tresch
 */
public final class JavaMoneyConfig {

	private static final Logger LOG = Logger
			.getLogger(JavaMoneyConfig.class.getName());

	private static final JavaMoneyConfig INSTANCE = new JavaMoneyConfig();
	
	private Map<String, String> config = new HashMap<>();
	private Map<String, Integer> priorities = new HashMap<>();

	private JavaMoneyConfig() {
		try {
			Enumeration<URL> urls = getClass().getClassLoader().getResources(
					"javamoney.properties");
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				try {
					Properties props = new Properties();
					props.load(url.openStream());
					updateConfig(props);
				} catch (Exception e) {
					LOG.log(Level.SEVERE,
							"Error loading javamoney.properties, ignoring "
									+ url, e);
				}
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error loading javamoney.properties.", e);
		}
	}

	private void updateConfig(Properties props) {
		for (Map.Entry<Object, Object> en : props.entrySet()) {
			String key = en.getKey().toString();
			String value = en.getValue().toString();
			int prio = 0;
			if (value.contains("{prio=")) {
				int index = value.indexOf('}');
				if (index > 0) {
					String prioString = value.substring("{prio=".length(),
							index - 1);
					value = value.substring(index + 1);
					prio = Integer.parseInt(prioString);
					this.priorities.put(key, prio);
				}
			}
			Integer existingPrio = priorities.get(key);
			if (existingPrio == null) {
				this.config.put(key, value);
			} else if (existingPrio < prio) {
				this.config.put(key, value);
			} else if (existingPrio == prio) {
				throw new IllegalStateException(
						"AmbigousConfiguration detected for '" + key + "'.");
			}
			// else ignore entry with lower prio!
		}
	}

	public static Map<String, String> getConfig() {
		return Collections.unmodifiableMap(INSTANCE.config);
	}

}
