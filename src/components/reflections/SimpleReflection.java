package components.reflections;

import java.lang.reflect.Field;
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
