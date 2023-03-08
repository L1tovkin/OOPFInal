package project;

import java.io.IOException;
import java.util.Scanner;

public class UserInterfaceView {

    Controller controller = new Controller();
    public void runInterface() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите пункт меню:");
            String menu =
                    "  1: погода на сегодня\n" +
                    "  5: прогноз на 5 дней\n" +
                    "  0: выход из приложения";
            System.out.println(menu);
            System.out.print ("Введите пункт: ");
            String command = scanner.nextLine();

            if (command.equals("0")) {
                System.out.println("Всего доброго!");
                break;
            }
            System.out.print("Введите город: ");
            String city = scanner.nextLine();

            System.out.println("Выберите источник прогнозирования:");
            menu = "  1: AccuWeather.com\n" +
                   "  2: FreeWeatherApi.com";
            System.out.println(menu);
            System.out.print ("Введите номер ресурса: ");
            String source = scanner.nextLine();

            try {
                controller.getWeather(command, city, source);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        UserInterfaceView userInterfaceView = new UserInterfaceView();
        userInterfaceView.runInterface();
    }
}