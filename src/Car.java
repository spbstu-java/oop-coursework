import java.util.function.Consumer;

public class Car
{
    private final Consumer<String> output;

    public Car(Consumer<String> output) {
        this.output = output;
    }


    @Repeat(5)
    public void showModel(String model)
    {
        output.accept("Модель автомобиля: " + model);
    }

    public void showYear(int year)
    {
        output.accept("Год выпуска: " + year);
    }

    @Repeat(2)
    protected void showMaxSpeed(int speed)
    {
        output.accept("Максимальная скорость автомобиля " + speed + " км/ч");
    }

    @Repeat(3)
    protected void brake(boolean smoothly)
    {
        output.accept("Плавное торможение: " + smoothly);
    }

    @Repeat(2)
    private void refuel(String fuel, double liters)
    {
        output.accept("Заправка: " + fuel + ", объём: " + liters + " л");
    }

    @Repeat(1)
    private void changeWheels(Wheel wheel)
    {
        output.accept("Установка колёс: " + wheel);
    }

    public static class Wheel
    {
        private final double price;

        public Wheel(double price)
        {
            this.price = price;
        }
        @Override
        public String toString()
        {
            return "Зимнее колесо, цена: " + price + " т.р.";
        }
    }
}
