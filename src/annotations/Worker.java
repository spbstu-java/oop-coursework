package annotations;

import java.util.Arrays;
import java.util.function.Consumer;

public class Worker {
    private final Consumer<String> output;

    public Worker(Consumer<String> output) {
        this.output = output;
    }
    public void greet(String name) {
        output.accept("Привет, " + name);
    }

    @Repeat(2)
    public void printSum(int a, int b) {
        output.accept("Сумма: " + (a + b));
    }

    public void printFirstLetter(String text) {
        printSymbol(text.charAt(0));
    }

    @Repeat(3)
    protected void printProduct(double a, double b) {
        output.accept("Произведение: " + (a * b));
    }

    @Repeat(1)
    protected void describe(String name, int age, boolean isStudent) {
        output.accept(name + ", возраст - " + age + ", студент - " + isStudent);
    }

    protected void printUpperCase(String text) {
        output.accept(text.toUpperCase());
    }

    @Repeat(2)
    private void printSquare(Integer number) {
        output.accept("Квадрат: " + number * number);
    }

    @Repeat(4)
    private void printArray(int[] numbers) {
        output.accept("Массив: " + Arrays.toString(numbers));
    }

    private void printSymbol(char symbol) {
        output.accept("Символ: " + symbol);
    }
}
