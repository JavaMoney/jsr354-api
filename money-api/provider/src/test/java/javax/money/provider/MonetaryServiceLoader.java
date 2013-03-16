/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  <JSR-354  Money and Currency API > ("Specification")
 */
package javax.money.provider;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.money.provider.Monetary.ComponentLoader;

public class MonetaryServiceLoader implements ComponentLoader {

	private ServiceLoader<MonetaryExtension> monetaryComponents = ServiceLoader
			.load(MonetaryExtension.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getInstance(Class<T> type,
			Class<? extends Annotation>... annotations) {
		List<T> instancesFound = getInstances(type, annotations);
		if (instancesFound.isEmpty()) {
			return null;
		} else if (instancesFound.size() == 1) {
			return instancesFound.get(0);
		} else {
			return resolveAmbigousComponents(instancesFound);
		}
	}

	protected <T> T resolveAmbigousComponents(List<T> instancesFound) {
		// or throw exception!
		return instancesFound.get(0);

	}

	private boolean annotationsMatch(MonetaryExtension comp,
			Class<? extends Annotation>[] annotations) {
		if (annotations == null) {
			return true;
		}
		for (Class<? extends Annotation> annotType : annotations) {
			if (comp.getClass().getAnnotation(annotType) == null) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getInstances(Class<T> type,
			Class<? extends Annotation>... annotations) {
		List<T> instancesFound = new ArrayList<T>();
		for (MonetaryExtension comp : monetaryComponents) {
			if (type.isAssignableFrom(comp.getClass())) {
				if (annotationsMatch(comp, annotations)) {
					instancesFound.add((T) comp);
				}
			}
		}
		return sortComponents(instancesFound);
	}

	protected <T> List<T> sortComponents(List<T> list) {
		return list;
	}

}
