package logic;

import java.sql.*;
import java.util.ArrayList;

public class dbHandler {

    private static Connection conn;

    // Connect to the database
    public static void ConnectDB() {
        try {
            String url = "jdbc:mysql://localhost/project?serverTimezone=IST&useSSL=false&allowPublicKeyRetrieval=true"; // Database URL
            String username = "root"; // Your database username
            String password = "Aa123456"; // Your database password
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR - Could not connect to the database.");
        }
    }

    // Process loan request with parameters
    public static void processLoanRequest(int subscriberID, int barcode , String loanDate, String returnDate, String bookName) throws SQLException {
        String query = "UPDATE books set(book_id, book_name, book_subjects, borrower_id, book_id, loan_date, return_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, barcode);
            stmt.setString(2, bookName);
            stmt.setString(3, loanDate);
            stmt.setString(4, returnDate);
            stmt.setInt(6, subscriberID);
            stmt.executeUpdate();
        }
    }

    // Handle messages from the client
    public static ArrayList<String> MessageHandler(ArrayList<String> msg) throws SQLException {
        ArrayList<String> response = new ArrayList<>();
        
        if (msg == null || msg.isEmpty()) {
            response.add("Invalid message");
            return response;
        }

        // Process different types of messages
        String action = msg.get(0);

        switch (action) {
            case "getAvailableBooks":
                response.addAll(getAvailableBooks());  // Retrieve list of available books
                break;

            case "borrowBook":
                int bookId = Integer.parseInt(msg.get(1));  // Book ID
                int subscriberId = Integer.parseInt(msg.get(2));  // Subscriber ID
                updateBookStatus(bookId, "borrowed", subscriberId);  // Update book status to borrowed
                response.add("Book borrowed successfully.");
                break;

            case "loanRequest":
                // Process loan request with parameters
                int loanSubscriberID = Integer.parseInt(msg.get(1)); // Extract subscriber ID
                int loanBarcode = Integer.parseInt(msg.get(2)); // Extract book barcode
                String loanDate = msg.get(3); // Extract loan date
                String returnDate = msg.get(4); // Extract return date
                String bookName = msg.get(5);
                processLoanRequest(loanSubscriberID, loanBarcode, loanDate, returnDate, bookName);  // Process loan request
                response.add("Loan request processed successfully.");
                break;

            default:
                response.add("Unknown action: " + action);
        }

        return response;
    }

    // Get list of available books from the database
    private static ArrayList<String> getAvailableBooks() throws SQLException {
        ArrayList<String> books = new ArrayList<>();
        String query = "SELECT * FROM Books WHERE status = 'available'";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String bookName = rs.getString("book_name");
                books.add("ID: " + bookId + ", Name: " + bookName);
            }
        }

        return books;
    }

    // Update the status of a book in the database
    private static void updateBookStatus(int bookId, String status, int borrowerId) throws SQLException {
        String query = "UPDATE Books SET status = ?, borrower_id = ? WHERE book_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, borrowerId);
            stmt.setInt(3, bookId);
            stmt.executeUpdate();
        }
    }
}
