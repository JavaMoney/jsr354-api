package net.java.javamoney.ri.loader;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.money.provider.Monetary.ComponentLoader;

public class CDIStandaloneLoader implements ComponentLoader {

	private CDIContainer cdi = new CDIContainer();

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getInstance(Class<T> type,
			Class<? extends Annotation>... annotations) {
		cdi.start();
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

	private boolean annotationsMatch(Object comp,
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
		cdi.start();
		List<T> instancesFound = new ArrayList<T>();
		for (Iterator iterator = cdi.getInstances(type); iterator.hasNext();) {
			T comp = (T) iterator.next();
			if (annotationsMatch(comp, annotations)) {
				instancesFound.add((T) comp);
			}
		}
		return sortComponents(instancesFound);
	}

	protected <T> List<T> sortComponents(List<T> list) {
		return list;
	}

}
