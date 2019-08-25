package components.reflections;

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
				if(field == null) continue;
				field.setAccessible(true);
				return field.get(obj);
			} catch (NoSuchFieldException e) {}
		} while ((classy = classy.getSuperclass()) != null);
		
    	throw new NoSuchFieldException();
	}
	
	public static Field getField(String attribute, Object obj) throws SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
		Class<?> classy = obj.getClass();
		
		do {
			try {
				Field field = classy.getDeclaredField(attribute);
				if(field == null) continue;
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
		Class<?>[] classes = new Class<?>[objects.length];
		
		for(int i = 0; i < objects.length; i++)
			classes[i] = objects[i].getClass();
		
		List<Constructor<?>> constructors = new LinkedList<>();
		for(Constructor<?> c : classy.getConstructors()) {
			if(c.getParameterCount() != objects.length) continue;
			
			boolean shouldContinue = false;
			Parameter[] paramters = c.getParameters();
			
			for(int i = 0; i < paramters.length; i++) {
				if(!paramters[i].getType().isAssignableFrom(objects[i].getClass())) {
					shouldContinue = true;
					break;
				}
			}
			
			if(shouldContinue) continue;
			constructors.add(c);
		}
		
		if(constructors.isEmpty())
			return null;
		else if(constructors.size() == 1) {
			Constructor<?> constructor = constructors.get(0);
			constructor.trySetAccessible();
			return constructor.newInstance(objects);
		} else {
			System.out.println("Sorry not implemented yet. Found " + constructors.size() + " for class " + classy); //TODO
		}
		
		return null;
		
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
		for(Method m : classy.getMethods()) {
			if(!m.getName().equals(name)) continue;
			if(m.getParameterCount() != objects.length) continue;
			if(isStatic != Modifier.isStatic(m.getModifiers())) continue;
			methods.add(m);
			if(objects.length == 0) break;
		}
		
		if(methods.isEmpty())
			return null;
		else if(methods.size() == 1) {
			Method method = methods.get(0);
			method.trySetAccessible();
			if(isStatic)
				return method.invoke(classy, objects);
			return method.invoke(obj, objects);
		}
		
		Method bestFound = null;
		int lastBestValue = 0;
		
		Class<?>[] classes = new Class<?>[objects.length];
		for(int i = 0; i < objects.length; i++)
			classes[i] = objects[i].getClass();
		
		for(Method m : methods) {
			boolean shouldContinue = false;
			Parameter[] paramters = m.getParameters();
			int best = 0;
			
			for(int i = 0; i < paramters.length; i++) {
				Class<?> myClass = classes[i];
				Class<?> paramClass = paramters[i].getType();
				
				if(!paramClass.isAssignableFrom(myClass)) {
					shouldContinue = true;
					break;
				}
				
				while(paramClass != myClass) {
					myClass = myClass.getSuperclass();
					best++;
				}
			}
			
			if(shouldContinue) continue;
			if(best < lastBestValue || bestFound == null) {
				lastBestValue = best;
				bestFound = m;
			}
			if(best == 0) break;
		}
		
		if(bestFound == null) return null;
		
		bestFound.trySetAccessible();
		if(isStatic)
			return bestFound.invoke(classy, objects);
		return bestFound.invoke(obj, objects);
	}
	
	public static List<String> listAttributes(Object obj) {
		List<String> list = new LinkedList<>();
		
		for(Field field : obj.getClass().getDeclaredFields())
			list.add(field.getName());
		
		return list;
	}
	
	public static String listAttributesToString(Object obj) {
		StringBuilder builder = new StringBuilder();
		
		Class<?> classy = obj.getClass();
		
		do {
			builder.append("\n" + classy.getName() + " :");
			
			for(Field field : classy.getDeclaredFields())
				builder.append("\n  " + field.getType().getSimpleName() + " " + field.getName());
		} while((classy = classy.getSuperclass()) != null);
		
		return builder.toString();
	}
}
