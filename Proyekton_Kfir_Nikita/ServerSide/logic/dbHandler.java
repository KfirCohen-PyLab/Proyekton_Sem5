package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class dbHandler {

	static Connection conn;

	public static void ConnectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/project?serverTimezone=IST&useSSL=false&allowPublicKeyRetrieval=true",
					"root", "Aa123456");

			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	private static ArrayList<String> Fail(ArrayList<String> fail) {
		fail.add("fail");
		return fail;
	}

	public static ArrayList<String> MessageHandler(ArrayList<String> inputs) throws SQLException {

		ArrayList<String> Response = new ArrayList<>();

		if (!inputs.isEmpty()) {
			// Access which action to do
			String actionString = inputs.get(0);

			// Handle the value of the first string using switch-case
			switch (actionString) {
			case "0": // login
				// return Login(inputs);
				Response.add("200ok");
				return Response;

			case "1": // change data

				return UpdateInfo(inputs);

			case "2": // send user data
				String id = inputs.get(1);
				return UserInfo(id);
			case "3": // book levy

				return null;
			case "4": // search books info
				return BookInfo(inputs);
			case "5": // :(
				return null;
			case "6":
				return ReturnDatesCheck(inputs);
			default:
				Response.add("close");
				return Response;

			}

		} else {
			System.out.println("Received an empty ArrayList!");
		}
		return null;

	}

	private static ArrayList<String> Login(ArrayList<String> inputs) throws SQLException {
		ArrayList<String> result = new ArrayList<>();
		String idString = inputs.get(1); // get id
		String passwordString = inputs.get(2); // get password

		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT password,admin_status FROM Subscriber WHERE subscriber_id = ?");
			ps.setString(1, idString);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) { // tests password
				String dbPassword = rs.getString("password"); // gets the password from the query

				if (dbPassword.equals(passwordString)) {

					result.add(rs.getString("admin_status")); // id and password match
					return result;
				}
			} // end if 1st
		} // end try
		catch (SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
			throw new SQLException("Error checking login users");
		}

		result.add("Wrong Credentials"); // id or password dont match
		return result;
	}

	private static ArrayList<String> UpdateInfo(ArrayList<String> inputs) throws SQLException {
		ArrayList<String> result = new ArrayList<>();
		String idString = inputs.get(1); // get id
		String phoneString = inputs.get(2); // get phone
		String emailString = inputs.get(3); // get email

		try {
			// Check if the ID exists
			String checkQuery = "SELECT COUNT(*) FROM Subscriber WHERE subscriber_id = ?";
			PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
			checkStmt.setString(1, idString);
			ResultSet rs = checkStmt.executeQuery();

			rs.next(); // Move to the first row of the result
			if (rs.getInt(1) == 0) {
				// If no matching ID is found, return "fail"
				result.add("fail");
				return result;
			}

			// If the ID exists, proceed with the update
			String updateQuery = "UPDATE Subscriber SET subscriber_phone_number = ?, subscriber_email = ? WHERE subscriber_id = ?";
			PreparedStatement ps = conn.prepareStatement(updateQuery);
			ps.setString(1, phoneString);
			ps.setString(2, emailString);
			ps.setString(3, idString);
			ps.executeUpdate();

			result.add("good");
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return Fail(result);
		}
	}

	private static ArrayList<String> UserInfo(String id) throws SQLException {
		ArrayList<String> userDetails = new ArrayList<>();

		try {
			PreparedStatement ps;
			System.out.println(id);

			if (id.equals("all")) {
				ps = conn.prepareStatement("SELECT * FROM Subscriber");
			} else {
				ps = conn.prepareStatement("SELECT * FROM Subscriber WHERE subscriber_id = ?");
				ps.setString(1, id);
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();

				userDetails.add("result");
				for (int i = 1; i <= columnCount; i++) {
					userDetails.add(rs.getString(i)); // Add each column value to the list
				}
			}
			if (userDetails.isEmpty()) {
				return Fail(userDetails);
			}
			return userDetails;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error fetching user info");

		}

	}

	private static ArrayList<String> BookInfo(ArrayList<String> inputs) throws SQLException {
		ArrayList<String> bookDetails = new ArrayList<>();
		String columName = inputs.get(1); // get method
		String searchParameter = inputs.get(2); // get search info
		try {

			PreparedStatement ps;

			switch (columName) {
			case "book_name": // sql query for name
				ps = conn.prepareStatement(
						"SELECT book_name,status,return_date,location FROM Books WHERE book_name=? and next_borrower_id IS NULL"); // sql
																																	// injection
				break;

			case "book_subjects": // sql query to choose just one of each book available > date > later date
				ps = conn.prepareStatement(
						"WITH ranked_books AS (SELECT b.book_name, b.status, b.return_date, b.location, ROW_NUMBER() OVER (PARTITION BY b.book_name ORDER BY CASE WHEN b.status = 'available' THEN 1 ELSE 2 END, b.return_date ASC) AS row_num FROM Books b WHERE FIND_IN_SET(?, b.book_subjects) > 0 AND b.next_borrower_id IS NULL) SELECT rb.book_name, rb.status, rb.return_date, rb.location FROM ranked_books rb WHERE rb.row_num = 1");
				break;

			case "book_description": // sql query to choose just one of each book available > date > later date
				ps = conn.prepareStatement(
						"WITH ranked_books AS (SELECT b.book_name, b.status, b.return_date, b.location, ROW_NUMBER() OVER (PARTITION BY b.book_name ORDER BY CASE WHEN b.status = 'available' THEN 1 ELSE 2 END, b.return_date ASC) AS row_num FROM Books b WHERE FIND_IN_SET(?, b.book_description) > 0 AND b.next_borrower_id IS NULL) SELECT rb.book_name, rb.status, rb.return_date, rb.location FROM ranked_books rb WHERE rb.row_num = 1");
				break;

			default:
				ps = conn.prepareStatement("SELECT ? FROM false"); // do nothing but dont error

			}

			ps.setString(1, searchParameter);

			ResultSet rs = ps.executeQuery();
			System.out.println(rs);

			while (rs.next()) {
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				bookDetails.add("books");
				for (int i = 1; i <= columnCount; i++) {
					bookDetails.add(rs.getString(i)); // Add each column value to the list
				}
				System.out.println(bookDetails);

			}
			if (bookDetails.isEmpty()) {
				return Fail(bookDetails);
			}

			return bookDetails;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error fetching user info");

		}

	}

	private static ArrayList<String> ReturnDatesCheck(ArrayList<String> date) throws SQLException {
		ArrayList<String> MailerList = new ArrayList<>();
		try {
			String TmrDate = date.get(1); // get method

			PreparedStatement ps;

			ps = conn.prepareStatement("SELECT book_name,borrower_id FROM Books where return_date = ?"); // the checker

			ps.setString(1, TmrDate);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					MailerList.add(rs.getString(i)); // Add each column value to the list
				}

			}
			if (MailerList.isEmpty()) {
				return Fail(MailerList);
			}

			return MailerList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Error fetching user info");
		}
	}
}
