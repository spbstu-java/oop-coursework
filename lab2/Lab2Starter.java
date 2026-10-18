package lab2;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Lab2Starter
{
    private final Consumer<String> out;

    public Lab2Starter(Consumer<String> out)
    {
        this.out = out;
    }
    public void run(Example example)
    {
        try
        {
            var declaredMethods = example.getClass().getDeclaredMethods();

            var found = false;

            for (var method : declaredMethods)
            {
                if (!method.isAnnotationPresent(MyAnnotation.class))
                    continue;

                var modifiers = method.getModifiers();
                if (!(Modifier.isPrivate(modifiers) || (Modifier.isProtected(modifiers))))
                    continue;

                found = true;

                var parameterTypes = method.getParameterTypes();

                var arguments = new Object[parameterTypes.length];

                for (var j = 0; j < parameterTypes.length; j++)
                {
                    arguments[j] = createDefaultValue(parameterTypes[j]);
                }

                var count = method.getAnnotation(MyAnnotation.class).count();

                method.setAccessible(true);

                out.accept("");
                out.accept("Метод " + method.getName() + " выполнится " + count + " раз:");

                for (var j = 0; j < count; j++)
                {
                    try
                    {
                        method.invoke(example, arguments);
                    }
                    catch (Exception ex)
                    {
                        out.accept("Метод " + method.getName() + " не удалось вызвать: " + ex.getMessage());
                    }
                }
            }
            if (!found)
                out.accept("Подходящих методов не найдено");
        }
        catch (Exception ex)
        {
            out.accept("Поймано исключение при работе программы: " + ex.getMessage());
        }
    }

    private static Object createDefaultValue(Class<?> type)
    {
        if (type.isPrimitive())
        {
            if (type == boolean.class) return false;
            if (type == char.class) return '\0';
            if (type == byte.class) return (byte) 0;
            if (type == short.class) return (short) 1;
            if (type == int.class) return 2;
            if (type == long.class) return 3L;
            if (type == float.class) return 4.0f;
            if (type == double.class) return 5.0;
        }

        if (type == String.class)
            return "строка по умолчанию";

        if (type.isArray())
            return Array.newInstance(type.getComponentType(), 0);

        var constructors = new ArrayList<>(List.of(type.getDeclaredConstructors()));
        constructors.sort(Comparator.comparingInt(Constructor::getParameterCount));

        var exceptions = new ArrayList<Exception>();

        for (var ctor : constructors)
        {
            try
            {
                ctor.setAccessible(true);

                var paramTypes = ctor.getParameterTypes();
                var args = new Object[paramTypes.length];
                for (var i = 0; i < paramTypes.length; i++) {
                    args[i] = createDefaultValue(paramTypes[i]);
                }

                return ctor.newInstance(args);
            } catch (Exception ex)
            {
                exceptions.add(ex);
            }
        }

        var message = new StringBuilder("Не удалось создать значение для типа: ").append(type.getName());

        for (var ex : exceptions)
        {
            message.append("\n  - ")
                    .append(ex.getClass())
                    .append(": ")
                    .append(ex.getMessage());
        }

        throw new IllegalArgumentException(message.toString());
    }
}
