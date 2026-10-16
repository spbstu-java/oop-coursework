package lab2;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParameterFactory {

	private static final Map<Class<?>, Object> defaults = Map.ofEntries(
	    Map.entry(byte.class, (byte) 1),
	    Map.entry(short.class, (short) 2),
	    Map.entry(int.class, 3),
	    Map.entry(long.class, 4L),
	    Map.entry(boolean.class, true),
	    Map.entry(float.class, 0.5f),
	    Map.entry(double.class, 3.14),
	    Map.entry(char.class, '-'),
	    Map.entry(Byte.class, (byte) 11),
	    Map.entry(Short.class, (short) 22),
	    Map.entry(Integer.class, 33),
	    Map.entry(Long.class, 44L),
	    Map.entry(Boolean.class, false),
	    Map.entry(Float.class, -0.5f),
	    Map.entry(Double.class, 3.14151926),
	    Map.entry(Character.class, '='),
	    Map.entry(String.class, "La la land")
	);
	
	private static Object[] make_parameters(Class<?>[] types, Set<Class<?>> recursive) throws IllegalArgumentException {
		Object[] result = new Object[types.length];
		for (int i=0; i<types.length; ++i) {
			if (recursive.contains(types[i]))
				throw new IllegalArgumentException(
					String.format("Impossible to create this type automatically because its constructor requires creation of other types that need to be created: %s.", types[i].toString())
				);
			result[i] = make_parameter(types[i], recursive);
		}
		return result;
	}
	public static Object[] make_parameters(Class<?>[] types) throws IllegalArgumentException {
		return make_parameters(types, Set.of());
	}
	public static Object make_parameter(Class<?> type) throws IllegalArgumentException {
		return make_parameter(type, Set.of());
	}
	private static Object _make_parameter(
		Constructor<?> constr, 
		Class<?> type, 
		Set<Class<?>> recursive
	) throws Exception {
		constr.setAccessible(true);
		var par_types = constr.getParameterTypes();
		var s = new HashSet<Class<?>>();
		s.addAll(recursive);
		s.add(type);
		var params = make_parameters(par_types, s);
		return constr.newInstance(params);
	}
	private static Object make_parameter(Class<?> type, Set<Class<?>> recursive) throws IllegalArgumentException {
		if (defaults.containsKey(type))
			return defaults.get(type);
		if (type.isArray())
			return Array.newInstance(type.getComponentType(), 0);
		if (type == Set.class || type == HashSet.class) 
			return new HashSet<>();
		if (type == List.class || type == ArrayList.class) 
			return new ArrayList<>();
		if (type == Map.class || type == HashMap.class) 
			return new HashMap<>();
		if (type.isInterface()) 
			throw new IllegalArgumentException(
				"Cannot create this interface automatically: "+ type.toString()
			);
		if (Modifier.isAbstract(type.getModifiers())) 
			throw new IllegalArgumentException(
				"Cannot create this abstract type automatically: "+ type.toString()
			);
		try {
			ArrayList<Exception> exceptions = new ArrayList<Exception>();

			for (int i=0; i<type.getDeclaredConstructors().length; ++i) {
				Constructor<?> constr = type.getDeclaredConstructors()[i];
				try
				{
					return _make_parameter(constr, type, recursive);
				} 
				catch(Exception e) {
					exceptions.add(e);
				}
			}
			throw new Exception("All constructors failed.", exceptions.isEmpty() ? null : exceptions.getLast());
		}
		catch (Exception e) {
			throw new IllegalArgumentException(
				"Impossible to create this type automatically: "+ type.toString(),
				e
			);
		}
	}
}
