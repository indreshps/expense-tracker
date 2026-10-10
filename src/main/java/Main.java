import dao.ExpenseDAO;
import model.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ExpenseDAO expenseDAO = new ExpenseDAO();

        Scanner scanner = new Scanner(System.in);
        InputHelper input = new InputHelper(scanner);

        while (true) {

            System.out.println("\n===== EXPENSE TRACKER V2 =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Update Expense");
            System.out.println("4. Find Expense by ID");
            System.out.println("5. Search Expenses");
            System.out.println("6. Total Expense");
            System.out.println("7. Category-wise Summary");
            System.out.println("8. Delete Expense");
            System.out.println("9. Find Expenses by Date Range");
            System.out.println("0. Exit");

            int choice = input.getInt("Enter your choice: ");

            switch (choice) {

                case 1:

                    double amount = input.getPositiveDouble(
                            "Enter amount: "
                    );

                    String category = input.getNonEmptyString(
                            "Enter category: ",
                            "Category"
                    );

                    String description = input.getNonEmptyString(
                            "Enter description: ",
                            "Description"
                    );

                    LocalDate date = input.getDate(
                            "Enter date (YYYY-MM-DD): "
                    );

                    String paymentMethod = input.getString(
                            "Enter payment method: "
                    );

                    Expense expense = new Expense(
                            0,
                            amount,
                            category,
                            description,
                            date,
                            paymentMethod
                    );

                    expenseDAO.addExpense(expense);

                    break;


                case 2:

                    System.out.println("\n===== ALL EXPENSES =====");

                    List<Expense> expenses =
                            expenseDAO.getAllExpenses();

                    if (expenses.isEmpty()) {

                        System.out.println("No expenses found.");

                    } else {

                        for (Expense item : expenses) {

                            System.out.println(
                                    "ID: " + item.getId() +
                                    " | Amount: " + item.getAmount() +
                                    " | Category: " + item.getCategory() +
                                    " | Description: " + item.getDescription() +
                                    " | Date: " + item.getDate() +
                                    " | Payment: " + item.getPaymentMethod()
                            );
                        }
                    }

                    break;


               
                    case 3:

    int updateId = input.getInt(
            "Enter expense ID to update: "
    );

    Expense existingExpense =
            expenseDAO.findExpenseById(updateId);

    if (existingExpense == null) {

        System.out.println("Expense not found.");
        break;
    }

    double updateAmount = input.getPositiveDouble(
            "Enter new amount: "
    );

    String updateCategory = input.getNonEmptyString(
            "Enter new category: ",
            "Category"
    );

    String updateDescription = input.getNonEmptyString(
            "Enter new description: ",
            "Description"
    );

    LocalDate updateDate = input.getDate(
            "Enter new date (YYYY-MM-DD): "
    );

    String updatePaymentMethod = input.getString(
            "Enter new payment method: "
    );

    Expense updatedExpense = new Expense(
            updateId,
            updateAmount,
            updateCategory,
            updateDescription,
            updateDate,
            updatePaymentMethod
    );

    expenseDAO.updateExpense(updatedExpense);

    break;


                case 4:

                    int findId = input.getInt(
                            "Enter expense ID to find: "
                    );

                    Expense foundExpense =
                            expenseDAO.findExpenseById(findId);

                    if (foundExpense != null) {

                        System.out.println(
                                "\n===== EXPENSE FOUND ====="
                        );

                        System.out.println(
                                "ID: " + foundExpense.getId() +
                                " | Amount: " + foundExpense.getAmount() +
                                " | Category: " + foundExpense.getCategory() +
                                " | Description: " + foundExpense.getDescription() +
                                " | Date: " + foundExpense.getDate() +
                                " | Payment: " + foundExpense.getPaymentMethod()
                        );

                    } else {

                        System.out.println("Expense not found.");
                    }

                    break;


                case 5:

                    String keyword = input.getString(
                            "Enter category or description to search: "
                    );

                    List<Expense> searchResults =
                            expenseDAO.searchExpenses(keyword);

                    if (searchResults.isEmpty()) {

                        System.out.println("No expenses found.");

                    } else {

                        System.out.println(
                                "\n===== SEARCH RESULTS ====="
                        );

                        for (Expense item : searchResults) {

                            System.out.println(
                                    "ID: " + item.getId() +
                                    " | Amount: " + item.getAmount() +
                                    " | Category: " + item.getCategory() +
                                    " | Description: " + item.getDescription() +
                                    " | Date: " + item.getDate() +
                                    " | Payment: " + item.getPaymentMethod()
                            );
                        }
                    }

                    break;


                case 6:

                    double totalExpense =
                            expenseDAO.getTotalExpense();

                    System.out.println(
                            "Total Expense: " + totalExpense
                    );

                    break;


                case 7:

                    System.out.println(
                            "\n===== CATEGORY-WISE SUMMARY ====="
                    );

                    expenseDAO.showCategorySummary();

                    break;


                case 8:

                    int deleteId = input.getInt(
                            "Enter expense ID to delete: "
                    );

                    expenseDAO.deleteExpense(deleteId);

                    break;

                    case 9:

    LocalDate startDate = input.getDate(
            "Enter start date (YYYY-MM-DD): "
    );

    LocalDate endDate = input.getDate(
            "Enter end date (YYYY-MM-DD): "
    );

    if (endDate.isBefore(startDate)) {

        System.out.println(
                "End date cannot be before start date."
        );

        break;
    }

    List<Expense> dateRangeExpenses =
            expenseDAO.getExpensesByDateRange(
                    startDate,
                    endDate
            );

    if (dateRangeExpenses.isEmpty()) {

        System.out.println(
                "No expenses found in this date range."
        );

    } else {

        System.out.println(
                "\n===== EXPENSES BY DATE RANGE ====="
        );

        for (Expense item : dateRangeExpenses) {

            System.out.println(
                    "ID: " + item.getId() +
                    " | Amount: " + item.getAmount() +
                    " | Category: " + item.getCategory() +
                    " | Description: " + item.getDescription() +
                    " | Date: " + item.getDate() +
                    " | Payment: " + item.getPaymentMethod()
            );
        }
    }

    break;


                case 0:

                    System.out.println("Exiting...");

                    scanner.close();

                    return;


                default:

                    System.out.println("Invalid choice.");
            }
        }
    }
}