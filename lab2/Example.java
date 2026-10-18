package lab2;

import java.util.function.Consumer;

public class Example {

    private final Consumer<String> out;

    public Example(Consumer<String> out) {
        this.out = out;
    }

    private void methodTextA(String a)
    {
        out.accept("Строка A: " + a);
    }

    @MyAnnotation(count = 2)
    private void methodSum(int a, int b)
    {
        out.accept("Сумма A+B: " + (a + b));
    }

    @MyAnnotation(count = 3)
    private void methodArrayAndObject(int[] numbers, Person person)
    {
        out.accept("Массив чисел содержит " + numbers.length + " элементов, а переданный перс: " + person);
    }

    @MyAnnotation(count = 4)
    protected void methodBoolean(boolean c)
    {
        out.accept("Метка: " + c);
    }

    protected void methodMultiply(double a, double b)
    {
        out.accept("Произведение AxB: " + (a * b));
    }

    protected void methodInfo(String info, int code)
    {
        out.accept("Инфо: " + info + ", код: " + code);
    }

    @MyAnnotation(count = 6)
    public void methodDivision(int e, int f)
    {
        out.accept("Деление E/F: " + (e / f));
    }

    public void methodE(int e)
    {
        out.accept("Число E: " + e);
    }
}
