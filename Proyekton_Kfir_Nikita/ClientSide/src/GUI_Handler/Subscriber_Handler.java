package GUI_Handler;

import java.util.ArrayList;

import Types_For_Table_view.Book_type_available;
import Types_For_Table_view.Subscriber_Type;
import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The `Subscriber_Handler` class provides utility methods for handling
 * subscriber-related operations. These operations include login, updating
 * subscriber information, and retrieving subscriber data from the database.
 *
 * The data transmission between the client and the server is performed using an
 * `ArrayList` of `String` values. The first index in the list always represents
 * the service type: - **"0"**: Login validation. - **"1"**: Update subscriber
 * information. - **"2"**: Retrieve subscriber data.
 *
 * Additional data follows the service type in the array, as described in each
 * method.
 */
public class Subscriber_Handler {

	/**
	 * Prepares data for the login operation.
	 * 
	 * @return An `ArrayList` containing the service type for login ("0").
	 * @throws Exception if an error occurs during data preparation.
	 */
	public static ArrayList<String> Login(String user, String pass) throws Exception {
		ArrayList<String> dataToSend = new ArrayList<>();
		dataToSend.add("0"); // Service type for login
		dataToSend.add(user); // Service type for login
		dataToSend.add(pass); // Service type for login

		return dataToSend;
	}

	/**
	 * Prepares data for updating subscriber information in the database. The
	 * operation requires the service type ("1"), subscriber ID, phone number, and
	 * email address.
	 *
	 * @param Id          The subscriber's ID.
	 * @param PhoneNumber The subscriber's phone number.
	 * @param Email       The subscriber's email address.
	 * @return An `ArrayList` containing the service type and the provided
	 *         subscriber details.
	 * @throws Exception if an error occurs during data preparation.
	 */
	public static ArrayList<String> SubDataChange(String Id, String PhoneNumber, String Email) throws Exception {
		ArrayList<String> dataToSend = new ArrayList<>();
		dataToSend.add("1");
		dataToSend.add(Id);
		dataToSend.add(PhoneNumber);
		dataToSend.add(Email);

		return dataToSend;
	}

	/**
	 * Prepares data for retrieving subscriber information from the database. The
	 * operation requires the service type ("2") and the subscriber ID.
	 *
	 * @param Id The subscriber's ID.
	 * @return An `ArrayList` containing the service type and the subscriber ID.
	 * @throws Exception if an error occurs during data preparation.
	 */
	public static ArrayList<String> DbDataGet(String Id) throws Exception {
		// Create array and fill it up with data
		ArrayList<String> dataToSend = new ArrayList<>();
		dataToSend.add("2"); // Service type for retrieving subscriber info
		dataToSend.add(Id); // Subscriber ID

		return dataToSend;
	}

	public static ArrayList<String> DbSerachData(String collumm, String info) throws Exception {

		ArrayList<String> dataToSend = new ArrayList<>();
		dataToSend.add("4");
		dataToSend.add(collumm);
		dataToSend.add(info);

		return dataToSend;
	}

	public static ObservableList<Subscriber_Type> DbBookInfoParse(ArrayList<String> listToParse) throws Exception {
		ObservableList<Subscriber_Type> Newlist = FXCollections.observableArrayList();
		for (int i = 0; i < (ChatClient.list.size() / 5); i++) {
			String id = ChatClient.list.get(i * 5);
			String name = ChatClient.list.get((i * 5) + 1);
			String history = ChatClient.list.get((i * 5) + 2);
			String phoneNumber = ChatClient.list.get((i * 5) + 3);
			String email = ChatClient.list.get((i * 5) + 4);
			Newlist.add(new Subscriber_Type(id, name, history, phoneNumber, email));
		}
		return Newlist;
	}

	public static ObservableList<Book_type_available> DbBookSearchAvailable(ArrayList<String> listToParse)
			throws Exception {
		ObservableList<Book_type_available> Newlist = FXCollections.observableArrayList();
		for (int i = 0; i < (ChatClient.listAvailable.size() / 2); i++) {
			// Loop through the list and construct the string for each subscriber
			String name = ChatClient.listAvailable.get(i * 2);
			String location = ChatClient.listAvailable.get((i * 2) + 1);
			Newlist.add(new Book_type_available(name, location, null));
		}
		return Newlist;
	}

	public static ObservableList<Book_type_available> DbBookSearchBorrowed(ArrayList<String> listToParse)
			throws Exception {
		ObservableList<Book_type_available> Newlist = FXCollections.observableArrayList();
		for (int i = 0; i < (ChatClient.listBorrowed.size() / 2); i++) {
			String name = ChatClient.listBorrowed.get(i * 2);
			String borrowed = ChatClient.listBorrowed.get((i * 2) + 1);
			Newlist.add(new Book_type_available(name, null, borrowed));
		}
		return Newlist;
	}
}
