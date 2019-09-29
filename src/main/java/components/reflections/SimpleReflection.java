package components.reflections;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

public class SimpleReflection {
	public static Object getObject(String attribute, Object obj) throws SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		Class<?> classy = obj.getClass();

		do {
			try {
				Field field = classy.getDeclaredField(attribute);
				if (field == null) continue;
				field.setAccessible(true);
				return field.get(obj);
			} catch (NoSuchFieldException e) {
			}
		} while ((classy = classy.getSuperclass()) != null);

		throw new NoSuchFieldException();
	}
	
	public static Field getField(String attribute, Object obj) throws SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		return getField(attribute, obj.getClass());
	}

	public static Field getField(String attribute, Class<?> classy) throws SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		do {
			try {
				Field field = classy.getDeclaredField(attribute);
				if (field == null) continue;
				field.trySetAccessible();
				return field;
			} catch (NoSuchFieldException e) {}
		} while ((classy = classy.getSuperclass()) != null);

		throw new NoSuchFieldException();
	}

	public static <T extends Enum<T>> T getEnum(Class<T> classy, String name) {
		return Enum.valueOf(classy, name);
	}

	public static Object createObject(Class<?> classy, Object... objects) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Constructor<?>> constructors = new LinkedList<>();
		for (Constructor<?> c : classy.getConstructors()) {
			if (c.getParameterCount() != objects.length && (c.getParameterCount() == 0 || !c.getParameters()[c.getParameterCount() - 1].isVarArgs())) continue;
			constructors.add(c);
			if (objects.length == 0) break;
		}
		
		if (constructors.isEmpty())
			return null;
		else if (constructors.size() == 1) {
			Constructor<?> constructor = constructors.get(0);
			constructor.trySetAccessible();
			
			Object[] args = null;
			int startPos = constructor.getParameterCount() - 1;
			if(constructor.getParameterCount() > 0 && constructor.getParameters()[startPos].isVarArgs()
				&& !(constructor.getParameterCount() == objects.length && objects[objects.length - 1].getClass() == constructor.getParameters()[startPos].getType())
			) {
				Class<?> type = constructor.getParameters()[startPos].getType().getComponentType();
				
				Object varargs = Array.newInstance(type, objects.length - startPos);
				for(int i = startPos; i < objects.length; i++)
					Array.set(varargs, i - startPos, objects[i]);
				
				args = new Object[startPos + 1];
				for(int i = 0; i < startPos; i++)
					args[i] = objects[i];
				args[startPos] = varargs;
			}
			
			return constructor.newInstance(args != null ? args : objects);
		}

		Constructor<?> bestFound = null;
		int lastBestValue = 0;

		Class<?>[] classes = new Class<?>[objects.length];
		for (int i = 0; i < objects.length; i++)
			classes[i] = objects[i].getClass();

		for (Constructor<?> c : constructors) {
			boolean shouldContinue = false;
			Parameter[] paramters = c.getParameters();
			int best = 0;

			for (int i = 0; i < paramters.length; i++) {
				Class<?> myClass = getDataTypesSuperclass(classes[i]);
				Class<?> paramClass = paramters[i].getType();

				if (!paramClass.isAssignableFrom(myClass)) {
					shouldContinue = true;
					break;
				}

				while (paramClass != myClass) {
					myClass = myClass.getSuperclass();
					best++;
				}
			}

			if (shouldContinue) continue;
			if (best < lastBestValue || bestFound == null) {
				lastBestValue = best;
				bestFound = c;
			}
			if (best == 0) break;
		}

		if (bestFound == null) return null;

		bestFound.trySetAccessible();
		return bestFound.newInstance(objects);
	}

	public static Object callStaticMethod(Class<?> classy, String name, Object... objects) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return callMethodHelper(classy, null, name, objects);
	}

	public static Object callMethod(Object obj, String name, Object... objects) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return callMethodHelper(obj.getClass(), obj, name, objects);
	}

	public static Object callMethodHelper(Class<?> classy, Object obj, String name, Object... objects) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean isStatic = obj == null;

		List<Method> methods = new LinkedList<>();
		for (Method m : classy.getMethods()) {
			if (!m.getName().equals(name)) continue;
			if (m.getParameterCount() != objects.length && (m.getParameterCount() == 0 || !m.getParameters()[m.getParameterCount() - 1].isVarArgs())) continue;
			if (isStatic != Modifier.isStatic(m.getModifiers())) continue;
			methods.add(m);
			if (objects.length == 0) break;
		}

		if (methods.isEmpty())
			return null;
		else if (methods.size() == 1) {
			Method method = methods.get(0);
			method.trySetAccessible();
			
			Object[] args = null;
			int startPos = method.getParameterCount() - 1;
			if(method.getParameterCount() > 0 && method.getParameters()[startPos].isVarArgs()
				&& !(method.getParameterCount() == objects.length && objects[objects.length - 1].getClass() == method.getParameters()[startPos].getType())	
			) {
				Class<?> type = method.getParameters()[startPos].getType().getComponentType();
				
				Object varargs = Array.newInstance(type, objects.length - startPos);
				for(int i = startPos; i < objects.length; i++)
					Array.set(varargs, i - startPos, objects[i]);
				
				args = new Object[startPos + 1];
				for(int i = 0; i < startPos; i++)
					args[i] = objects[i];
				args[startPos] = varargs;
			}
			
			if (isStatic)
				return method.invoke(classy, args != null ? args : objects);
			return method.invoke(obj, args != null ? args : objects);
		}

		Method bestFound = null;
		int lastBestValue = 0;

		Class<?>[] classes = new Class<?>[objects.length];
		for (int i = 0; i < objects.length; i++)
			classes[i] = objects[i].getClass();

		for (Method m : methods) {
			boolean shouldContinue = false;
			Parameter[] paramters = m.getParameters();
			int best = 0;

			for (int i = 0; i < paramters.length; i++) {
				Class<?> myClass = getDataTypesSuperclass(classes[i]);
				Class<?> paramClass = paramters[i].getType();

				if (!paramClass.isAssignableFrom(myClass)) {
					shouldContinue = true;
					break;
				}

				while (paramClass != myClass) {
					myClass = myClass.getSuperclass();
					best++;
				}
			}

			if (shouldContinue) continue;
			if (best < lastBestValue || bestFound == null) {
				lastBestValue = best;
				bestFound = m;
			}
			if (best == 0) break;
		}

		if (bestFound == null) return null;

		bestFound.trySetAccessible();
		if (isStatic)
			return bestFound.invoke(classy, objects);
		return bestFound.invoke(obj, objects);
	}

	public static Method getMethod(Class<?> classy, boolean isStatic, String name, Class<?>... classParameters) {
		List<Method> methods = new LinkedList<>();
		for (Method m : classy.getMethods()) {
			if (!m.getName().equals(name)) continue;
			if (m.getParameterCount() != classParameters.length) continue;
			if (isStatic != Modifier.isStatic(m.getModifiers())) continue;
			methods.add(m);
			if (classParameters.length == 0) break;
		}

		if (methods.isEmpty())
			return null;
		else if (methods.size() == 1)
			return methods.get(0);

		Method bestFound = null;
		int lastBestValue = 0;

		for (Method m : methods) {
			boolean shouldContinue = false;
			Parameter[] paramters = m.getParameters();
			int best = 0;

			for (int i = 0; i < paramters.length; i++) {
				Class<?> myClass = getDataTypesSuperclass(classParameters[i]);
				Class<?> paramClass = paramters[i].getType();

				if (!paramClass.isAssignableFrom(myClass)) {
					shouldContinue = true;
					break;
				}

				while (paramClass != myClass) {
					myClass = myClass.getSuperclass();
					best++;
				}
			}

			if (shouldContinue) continue;
			if (best < lastBestValue || bestFound == null) {
				lastBestValue = best;
				bestFound = m;
			}
			if (best == 0) break;
		}

		return bestFound;
	}

	public static Class<?> getDataTypesSuperclass(Class<?> myClass) {
		if (myClass == Integer.class)
			return int.class;
		else if (myClass == Byte.class)
			return byte.class;
		else if (myClass == Short.class)
			return short.class;
		else if (myClass == Long.class)
			return long.class;
		else if (myClass == Double.class)
			return double.class;
		else if (myClass == Float.class)
			return float.class;
		else if (myClass == Boolean.class)
			return boolean.class;
		else if (myClass == Character.class)
			return char.class;
		return myClass;
	}

	public static Class<?>[] getInterfacesArray(Class<?> classy) {
		List<Class<?>> list = getInterfaces(classy);
		Class<?>[] array = new Class<?>[list.size()];
		return list.toArray(array);
	}

	public static List<Class<?>> getInterfaces(Class<?> classy) {
		if (classy == null) return null;

		List<Class<?>> list = new LinkedList<>();

		while (classy != Object.class && classy != null) {
			for (Class<?> classe : classy.getInterfaces()) {
				if (!list.contains(classe))
					list.add(classe);

				for (Class<?> classe2 : getInterfaces(classe)) {
					if (!list.contains(classe2))
						list.add(classe2);
				}
			}

			classy = classy.getSuperclass();
		}

		return list;
	}

	public static List<String> listAttributes(Object obj) {
		List<String> list = new LinkedList<>();

		for (Field field : obj.getClass().getDeclaredFields())
			list.add(field.getName());

		return list;
	}

	public static String listAttributesToString(Object obj) {
		StringBuilder builder = new StringBuilder();

		Class<?> classy = obj.getClass();

		do {
			builder.append("\n").append(classy.getName()).append(" :");

			for (Field field : classy.getDeclaredFields())
				builder.append("\n  ").append(field.getType().getSimpleName()).append(" ").append(field.getName());
		} while ((classy = classy.getSuperclass()) != null);

		return builder.toString();
	}
}
