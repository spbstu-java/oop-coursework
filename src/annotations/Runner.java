package annotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Runner {

    public static void run(Object target) {
        Method[] methods = target.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(MyAnnotation.class)) {
                continue;
            }

            int mod = method.getModifiers();
            if (Modifier.isPublic(mod)) {
                continue;
            }
            if (!Modifier.isProtected(mod) && !Modifier.isPrivate(mod)) {
                continue;
            }

            int times = method.getAnnotation(MyAnnotation.class).value();
            Class[] types = method.getParameterTypes();
            Object[] args = new Object[types.length];
            for (int i = 0; i < types.length; i++) {
                args[i] = valueFor(types[i]);
            }

            method.setAccessible(true);
            System.out.println(method.getName() + " — " + times + " раз(а)");

            for (int i = 0; i < times; i++) {
                try {
                    method.invoke(target, args);
                } catch (ReflectiveOperationException e) {
                    System.err.println("ошибка вызова " + method.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    private static Object valueFor(Class type) {
        if (type == boolean.class || type == Boolean.class) {
            return true;
        }
        if (type == byte.class || type == Byte.class) {
            return (byte) 1;
        }
        if (type == short.class || type == Short.class) {
            return (short) 2;
        }
        if (type == int.class || type == Integer.class) {
            return 5;
        }
        if (type == long.class || type == Long.class) {
            return 10L;
        }
        if (type == float.class || type == Float.class) {
            return 1.5f;
        }
        if (type == double.class || type == Double.class) {
            return 2.5;
        }
        if (type == char.class || type == Character.class) {
            return 'A';
        }
        if (type == String.class) {
            return "text";
        }
        return createInstance(type);
    }

    private static Object createInstance(Class type) {
        Constructor[] constructors = type.getDeclaredConstructors();
        if (constructors.length == 0) {
            System.err.println("не удалось создать " + type.getSimpleName() + ", передаём null");
            return null;
        }

        Constructor constructor = constructors[0];
        for (int i = 1; i < constructors.length; i++) {
            if (constructors[i].getParameterCount() < constructor.getParameterCount()) {
                constructor = constructors[i];
            }
        }

        Class[] types = constructor.getParameterTypes();
        Object[] args = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            args[i] = valueFor(types[i]);
        }

        try {
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            System.err.println("не удалось создать " + type.getSimpleName() + ", передаём null");
            return null;
        }
    }
}
