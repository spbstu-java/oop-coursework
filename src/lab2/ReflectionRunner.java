package lab2;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionRunner {

    public static String run(Object obj) {
        StringBuilder report = new StringBuilder();
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (isAnnotatedNonPublic(method)) {
                executeAnnotated(obj, method, report);
            }
        }
        return report.toString();
    }

    private static boolean isAnnotatedNonPublic(Method method) {
        if (!method.isAnnotationPresent(RepeatCount.class)) {
            return false;
        }
        int modifiers = method.getModifiers();
        return Modifier.isProtected(modifiers) || Modifier.isPrivate(modifiers);
    }

    private static void executeAnnotated(Object obj, Method method, StringBuilder report) {
        RepeatCount annotation = method.getAnnotation(RepeatCount.class);
        int times = annotation.value();
        Object[] args = generateArguments(method.getParameterTypes());

        report.append("Метод: ").append(method.getName())
                .append(", повторов: ").append(times).append('\n');

        method.setAccessible(true);
        for (int i = 0; i < times; i++) {
            report.append("Вызов №").append(i + 1).append(": ");
            try {
                Object result = method.invoke(obj, args);
                report.append(method.getReturnType() == void.class ? "void" : result);
            } catch (IllegalAccessException | InvocationTargetException e) {
                report.append("Ошибка: ").append(e.getMessage());
            }
            report.append('\n');
        }
        report.append('\n');
    }

    private static Object[] generateArguments(Class<?>[] paramTypes) {
        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            args[i] = generateArgument(paramTypes[i]);
        }
        return args;
    }

    private static Object generateArgument(Class<?> type) {
        if (type.isArray()) {
            return Array.newInstance(type.getComponentType(), 0);
        }

        String typeName = type.getName();

        switch (typeName) {
            case "int":
            case "java.lang.Integer":
                return 1;
            case "double":
            case "java.lang.Double":
                return 1.1;
            case "boolean":
            case "java.lang.Boolean":
                return true;
            case "java.lang.String":
                return "string";
            default:
                if (Modifier.isAbstract(type.getModifiers())) {
                    return null;
                }

                var constructors = type.getDeclaredConstructors();
                boolean hasDefaultConstructor = false;
                for (var c : constructors) {
                    if (c.getParameterCount() == 0) {
                        hasDefaultConstructor = true;
                        break;
                    }
                }

                try {
                    if (hasDefaultConstructor) {
                        var constructor = type.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        return constructor.newInstance();
                    } else if (constructors.length > 0) {
                        var constructor = constructors[0];
                        constructor.setAccessible(true);
                        Object[] constructorArgs = generateArguments(constructor.getParameterTypes());
                        return constructor.newInstance(constructorArgs);
                    }
                } catch (Exception e) {
                    return null;
                }

                return null;
        }
    }
}
