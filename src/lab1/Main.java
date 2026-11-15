package lab1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hero hero = new Hero();

        while (true) {
            System.out.println("\nУкажите, как должен двигаться герой:");
            System.out.println("1 — пешком");
            System.out.println("2 — на лошади");
            System.out.println("3 — лететь");
            System.out.println("0 — выход");
            System.out.print("Ваш выбор: ");

            int choice;

            while (!scanner.hasNextInt()) {
                System.out.println("Ошибка! Введите только цифру.");
                System.out.print("Ваш выбор: ");
                scanner.next();
            }

            choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Выход из программы. Пока!");
                break;
            }

            switch (choice) {
                case 1:
                    hero.setStrategy(new Walking());
                    break;
                case 2:
                    hero.setStrategy(new HorseRiding());
                    break;
                case 3:
                    hero.setStrategy(new Flying());
                    break;
                default:
                    System.out.println("Неверный выбор, герой пойдёт пешком.");
                    hero.setStrategy(new Walking());
                    break;
            }

            hero.Move();
        }

        scanner.close();
    }
}
