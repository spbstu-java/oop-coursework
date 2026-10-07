import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.function.Supplier;

public class AnnotatedRunner
{
    private static final int MAX_DEPTH = 10;
    private final String text;
    private final int integer;
    private final double decimal;
    private final boolean flag;
    private final Map<Class<?>, Supplier<?>> factories;

    public AnnotatedRunner(String text, int integer, double decimal, boolean flag,
                           Map<Class<?>, Supplier<?>> factories) {
        this.text = text;
        this.integer = integer;
        this.decimal = decimal;
        this.flag = flag;
        this.factories = Map.copyOf(factories);
    }

    public void invokeAnnotatedMethods(Object object)
    {
        for (Method method : object.getClass().getDeclaredMethods())
        {
            Repeat repeat = method.getAnnotation(Repeat.class);
            if (repeat == null)
                continue;
            int mod = method.getModifiers();
            if (!Modifier.isProtected(mod) && !Modifier.isPrivate(mod))
                continue;
            int count = repeat.value();
            if (count <= 0)
                continue;
            method.setAccessible(true);
            Class<?>[] paramTypes = method.getParameterTypes();
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++)
                args[i] = createArgument(paramTypes[i]);
            for (int i = 0; i < count; i++)
            {
                try
                {
                    method.invoke(object, args);
                } catch (ReflectiveOperationException e)
                {
                    throw new RuntimeException("Не удалось вызвать метод " + method.getName(), e);
                }
            }
        }
    }

    private Object createArgument(Class<?> type)
    {
        return createArgument(type, 0);
    }

    private Object createArgument(Class<?> type, int depth)
    {
        Supplier<?> factory = factories.get(type);
        if (factory != null)
            return java.util.Objects.requireNonNull(factory.get(), "Фабрика вернула null");
        if (depth > MAX_DEPTH)
            throw new IllegalArgumentException(
                    "Слишком глубокая цепочка создания объектов: " + type.getName());

        if (type == String.class)
            return text;
        if (type == boolean.class || type == Boolean.class)
            return flag;
        if (type == byte.class || type == Byte.class)
            return (byte) 1;
        if (type == short.class || type == Short.class)
            return (short) 2;
        if (type == int.class || type == Integer.class)
            return integer;
        if (type == long.class || type == Long.class)
            return 0L;
        if (type == float.class || type == Float.class)
            return 0.0f;
        if (type == double.class || type == Double.class)
            return decimal;
        if (type == char.class || type == Character.class)
            return 'A';

        if (type.isInterface() || Modifier.isAbstract(type.getModifiers()))
            throw new IllegalArgumentException("Для типа " + type.getName() + " требуется конкретная реализация");

        Constructor<?>[] constructors = type.getDeclaredConstructors();
        if (constructors.length == 0)
            throw new IllegalArgumentException("Нет конструкторов у типа " + type.getName());

        Constructor<?> shortest = constructors[0];
        for (Constructor<?> constructor : constructors)
        {
            if (constructor.getParameterCount() < shortest.getParameterCount())
                shortest = constructor;
        }

        Class<?>[] parameterTypes = shortest.getParameterTypes();
        Object[] arguments = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++)
            arguments[i] = createArgument(parameterTypes[i], depth + 1);

        try
        {
            shortest.setAccessible(true);
            return shortest.newInstance(arguments);
        } catch (ReflectiveOperationException e)
        {
            throw new IllegalArgumentException("Невозможно создать аргумент типа " + type.getName(), e);
        }
    }
}
