package javax.money.tck.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import javax.money.tck.TCKValidationException;

import junit.framework.Assert;

public class ClassTester {

	private ClassTester() {

	}

	public static void testSerializable(Class c) {
		if (!Serializable.class.isAssignableFrom(c)) {
			throw new TCKValidationException("Class must be serializable: "
					+ c.getName());
		}
	}

	public static void testImmutable(Class c) {
		if (c.isInterface()) {
			throw new TCKValidationException(
					"Class is an interface, instead of a class: "
							+ c.getName());
		}
		if (c.isPrimitive()) {
			return;
		}
		if ((c.getModifiers() & Modifier.FINAL) == 0) {
			throw new TCKValidationException(
					"Class must be immutable, so it must be final: "
							+ c.getName());
		}
		Class current = c;
		while (current != null) {
			for (Field f : current.getDeclaredFields()) {
				if ((f.getModifiers() & Modifier.PRIVATE) == 0) {
					if ((f.getModifiers() & Modifier.FINAL) == 0) {
						throw new TCKValidationException(
								"Class must be immutable, but field is not private: "
										+ current.getName() + '#' + f.getName());
					}
				}
			}
			for (Method m : current.getDeclaredMethods()) {
				if ((m.getModifiers() & Modifier.PRIVATE) == 0) {
					checkImmutableMethod(m);
				}
			}
			current = current.getSuperclass();
		}
	}

	private static void checkImmutableMethod(Method m) {
		if (m.getParameterTypes().length == 0) {
			return;
		}
		if (m.getName().startsWith("set")) {
			throw new TCKValidationException(
					"Class must be immutable, but suspicious method was found: "
							+ m.getDeclaringClass().getName() + '#'
							+ m.getName()
							+ Arrays.toString(m.getParameterTypes()));
		}
	}

	private static <T> void checkReturnTypeIsConcrete(Method m,
			Class<T> ifaceType, Class<? extends T> concreteType) {
		Class c = m.getReturnType();
		if (Void.class.equals(c)) {
			return;
		}
		if (ifaceType.equals(c)) {
			throw new TCKValidationException(
					"Class must return concrete type(" + concreteType.getName()
							+ "), not interface type(" + ifaceType.getName()
							+ "): "
							+ m.getDeclaringClass().getName() + '#'
							+ m.getName()
							+ Arrays.toString(m.getParameterTypes()));
		}
	}

	public static void testSerializable(Object o) {
		if (!(o instanceof Serializable)) {
			throw new TCKValidationException("Class must be serializable: "
					+ o.getClass().getName());
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(
				new ByteArrayOutputStream())) {
			oos.writeObject(o);
		} catch (Exception e) {
			throw new TCKValidationException(
					"Class must be serializable, but serialization failed: "
							+ o.getClass().getName(), e);
		}
	}

	public static void testImplementsInterface(Class type, Class iface) {
		Class current = type;
		for (Class ifa : type.getInterfaces()) {
			if (ifa.equals(iface)) {
				return;
			}
		}
		throw new TCKValidationException(
				"Class must implement " + iface.getName() + ", but does not: "
						+ type.getName());
	}

	public static void testHasPublicMethod(Class type, Class returnType,
			String name, Class... paramTypes) {
		Class current = type;
		while (current != null) {
			for (Method m : current.getDeclaredMethods()) {
				if (returnType.equals(returnType) &&
						m.getName().equals(name) &&
						Arrays.equals(m.getParameterTypes(), paramTypes)) {
					return;
				}
			}
			current = current.getSuperclass();
		}
		throw new TCKValidationException(
				"Class must implement method " + name + '('
						+ Arrays.toString(paramTypes) + "): "
						+ returnType.getName() + ", but does not: "
						+ type.getName());
	}

	public static void testHasNotPublicMethod(Class type, Class returnType,
			String name, Class... paramTypes) {
		Class current = type;
		while (current != null) {
			for (Method m : current.getDeclaredMethods()) {
				if (returnType.equals(returnType) &&
						m.getName().equals(name) &&
						Arrays.equals(m.getParameterTypes(), paramTypes)) {
					throw new TCKValidationException(
							"Class must NOT implement method " + name + '('
									+ Arrays.toString(paramTypes) + "): "
									+ returnType.getName() + ", but does: "
									+ type.getName());
				}
			}
			current = current.getSuperclass();
		}
	}

	public static void testComparable(Class type) {
		testImplementsInterface(type, Comparable.class);
	}

	public static void assertValue(Object value, String methodName,
			Object instance) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method m = instance.getClass().getDeclaredMethod(methodName);
		Assert.assertEquals(value, m.invoke(instance));

	}
}
