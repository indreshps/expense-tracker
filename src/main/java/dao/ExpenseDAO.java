package dao;

import database.DBConnection;
import model.Expense;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ExpenseDAO {

    public void addExpense(Expense expense) {

        String sql = "INSERT INTO expenses " +
                     "(amount, category, description, expense_date, payment_method) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setDouble(1, expense.getAmount());
            statement.setString(2, expense.getCategory());
            statement.setString(3, expense.getDescription());
            statement.setDate(4, java.sql.Date.valueOf(expense.getDate()));
            statement.setString(5, expense.getPaymentMethod());

            statement.executeUpdate();

            statement.executeUpdate();

try (ResultSet resultSet = statement.getGeneratedKeys()) {

    if (resultSet.next()) {

        int generatedId = resultSet.getInt(1);

        System.out.println(
                "Expense added successfully! ID: " + generatedId
        );
    }
}

        } catch (SQLException e) {
            System.out.println("Failed to add expense.");
            e.printStackTrace();
        }
    }


    public List<Expense> getAllExpenses() {

    List<Expense> expenses = new ArrayList<>();

    String sql = "SELECT * FROM expenses ORDER BY expense_date DESC";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            double amount = resultSet.getDouble("amount");
            String category = resultSet.getString("category");
            String description = resultSet.getString("description");
            LocalDate date = resultSet.getDate("expense_date").toLocalDate();
            String paymentMethod = resultSet.getString("payment_method");

            Expense expense = new Expense(
                    id,
                    amount,
                    category,
                    description,
                    date,
                    paymentMethod
            );

            expenses.add(expense);
        }

    } catch (SQLException e) {
        System.out.println("Failed to fetch expenses.");
        e.printStackTrace();
    }

    return expenses;
}


public Expense findExpenseById(int id) {

    String sql = "SELECT * FROM expenses WHERE id = ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, id);

        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                return new Expense(
                        resultSet.getInt("id"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("category"),
                        resultSet.getString("description"),
                        resultSet.getDate("expense_date").toLocalDate(),
                        resultSet.getString("payment_method")
                );
            }
        }

    } catch (SQLException e) {
        System.out.println("Failed to find expense.");
        e.printStackTrace();
    }

    return null;
}


public List<Expense> searchExpenses(String keyword) {

    List<Expense> expenses = new ArrayList<>();

    String sql = "SELECT * FROM expenses " +
                 "WHERE category LIKE ? OR description LIKE ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        String searchKeyword = "%" + keyword + "%";

        statement.setString(1, searchKeyword);
        statement.setString(2, searchKeyword);

        try (ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Expense expense = new Expense(
                        resultSet.getInt("id"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("category"),
                        resultSet.getString("description"),
                        resultSet.getDate("expense_date").toLocalDate(),
                        resultSet.getString("payment_method")
                );

                expenses.add(expense);
            }
        }

    } catch (SQLException e) {
        System.out.println("Failed to search expenses.");
        e.printStackTrace();
    }

    return expenses;
}

public double getTotalExpense() {

    String sql = "SELECT SUM(amount) AS total FROM expenses";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        if (resultSet.next()) {
            return resultSet.getDouble("total");
        }

    } catch (SQLException e) {
        System.out.println("Failed to calculate total expense.");
        e.printStackTrace();
    }

    return 0;
}


public void showCategorySummary() {

    String sql = "SELECT category, SUM(amount) AS total " +
                 "FROM expenses " +
                 "GROUP BY category";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {

            String category = resultSet.getString("category");
            double total = resultSet.getDouble("total");

            System.out.println(
                    category + ": " + total
            );
        }

    } catch (SQLException e) {
        System.out.println("Failed to generate category summary.");
        e.printStackTrace();
    }
}
   

public void updateExpense(Expense expense) {

    String sql = "UPDATE expenses SET " +
                 "amount = ?, " +
                 "category = ?, " +
                 "description = ?, " +
                 "expense_date = ?, " +
                 "payment_method = ? " +
                 "WHERE id = ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setDouble(1, expense.getAmount());
        statement.setString(2, expense.getCategory());
        statement.setString(3, expense.getDescription());
        statement.setDate(4, java.sql.Date.valueOf(expense.getDate()));
        statement.setString(5, expense.getPaymentMethod());
        statement.setInt(6, expense.getId());

        int rowsUpdated = statement.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Expense updated successfully!");
        } else {
            System.out.println("Expense not found.");
        }

    } catch (SQLException e) {
        System.out.println("Failed to update expense.");
        e.printStackTrace();
    }
}

public void deleteExpense(int id) {

    String sql = "DELETE FROM expenses WHERE id = ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, id);

        int rowsDeleted = statement.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Expense deleted successfully!");
        } else {
            System.out.println("Expense not found.");
        }

    } catch (SQLException e) {
        System.out.println("Failed to delete expense.");
        e.printStackTrace();
    }
}

public List<Expense> getExpensesByDateRange(
        LocalDate startDate,
        LocalDate endDate) {

    List<Expense> expenses = new ArrayList<>();

    String sql = "SELECT * FROM expenses " +
                 "WHERE expense_date BETWEEN ? AND ?";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setDate(
                1,
                java.sql.Date.valueOf(startDate)
        );

        statement.setDate(
                2,
                java.sql.Date.valueOf(endDate)
        );

        try (ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Expense expense = new Expense(
                        resultSet.getInt("id"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("category"),
                        resultSet.getString("description"),
                        resultSet.getDate("expense_date").toLocalDate(),
                        resultSet.getString("payment_method")
                );

                expenses.add(expense);
            }
        }

    } catch (SQLException e) {

        System.out.println(
                "Failed to find expenses by date range."
        );

        e.printStackTrace();
    }

    return expenses;
}


}