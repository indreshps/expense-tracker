import java.time.LocalDate;
import java.util.Scanner;

public class InputHelper {

    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    
    public int getInt(String message) {

        while (true) {

            System.out.print(message);

            if (!scanner.hasNextInt()) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );

                scanner.nextLine();
                continue;
            }

            int value = scanner.nextInt();
            scanner.nextLine();

            return value;
        }
    }


    public double getPositiveDouble(String message) {

        while (true) {

            System.out.print(message);

            if (!scanner.hasNextDouble()) {

                System.out.println(
                        "Invalid amount. Please enter a number."
                );

                scanner.nextLine();
                continue;
            }

            double value = scanner.nextDouble();
            scanner.nextLine();

            if (value <= 0) {

                System.out.println(
                        "Amount must be greater than 0."
                );

                continue;
            }

            return value;
        }
    }

  
    public String getNonEmptyString(String message, String fieldName) {

        while (true) {

            System.out.print(message);

            String value = scanner.nextLine();

            if (value.trim().isEmpty()) {

                System.out.println(
                        fieldName + " cannot be empty."
                );

                continue;
            }

            return value;
        }
    }

    public LocalDate getDate(String message) {

        while (true) {

            System.out.print(message);

            try {

                return LocalDate.parse(
                        scanner.nextLine()
                );

            } catch (Exception e) {

                System.out.println(
                        "Invalid date. Please use YYYY-MM-DD."
                );
            }
        }
    }


    public String getString(String message) {

        System.out.print(message);

        return scanner.nextLine();
    }
}