
package gui;

import java.net.URL;
import java.util.ResourceBundle;

import GUI_Handler.Subscriber_Handler;
import GUI_Handler.switchScreen;
import Types_For_Table_view.Book_type_available;
import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchController implements Initializable {
	private Object dataToSend;

	@FXML
	private TableView<Book_type_available> table;

	@FXML
	private TableColumn<Book_type_available, String> name;

	@FXML
	private TableColumn<Book_type_available, String> loc;

	@FXML
	private TableColumn<Book_type_available, String> retdate;

	@FXML
	private TextField txtName = null;

	@FXML
	private TextField txtSubject = null;

	@FXML
	private TextField txtInfo = null;

	@FXML
	private Button btnExit2 = null;

	@FXML
	private Button send1 = null;

	@FXML
	private Button send2 = null;

	@FXML
	private Button send3 = null;

	@FXML
	private Label info; // Button to send the update request to the server

	// Getter method for retrieving the ID
	private String getName() {
		return txtName.getText();
	}

	// Getter method for retrieving the phone number
	private String getSubject() {
		return txtSubject.getText();
	}

	// Getter method for retrieving the email
	private String getInfo() {
		return txtInfo.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		loc.setCellValueFactory(new PropertyValueFactory<>("Location"));
		retdate.setCellValueFactory(new PropertyValueFactory<>("borrowed"));
	}

	// Method to start the Option menu screen
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/SearchFrame.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/SearchFrame.css").toExternalForm());

		primaryStage.setTitle("Search");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	// Method called when the "Close" button is clicked
	// Switches the screen back to the Option menu screen
	public void btnExit2(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/OptionFrame", "Option menu");
	}

	// Method called when the "Send" button is clicked
	// Sends the updated data (ID, phone number, and email) to the server for
	public void getsendbtn1(ActionEvent event) throws Exception {
		dataToSend = Subscriber_Handler.DbSerachData("book_name", getName());
		ClientUI.chat.accept(dataToSend);
		if (ChatClient.answer.equals("good")) {
			ObservableList<Book_type_available> NewAvailable = FXCollections.observableArrayList();
			if (ChatClient.listAvailable.size() != 0) {
				for (int i = 0; i < (ChatClient.listAvailable.size() / 2); i++) {
					// Loop through the list and construct the string for each subscriber
					String name = ChatClient.listAvailable.get(i * 2);
					String location = ChatClient.listAvailable.get((i * 2) + 1);
					NewAvailable.add(new Book_type_available(name, location, null));
				}
				table.setItems(NewAvailable);
			} else {
				if (ChatClient.listBorrowed.size() != 0)
					for (int i = 0; i < (ChatClient.listBorrowed.size() / 2); i++) {
						// Loop through the list and construct the string for each subscriber
						String name = ChatClient.listBorrowed.get(i * 2);
						String borrowed = ChatClient.listBorrowed.get((i * 2) + 1);
						NewAvailable.add(new Book_type_available(name, null, borrowed));
					}
				table.setItems(NewAvailable);
			}
		} else {
			info.setText("Error, Data couldn't be found in DB, Check your inputs");
		}
	}

	public void getsendbtn2(ActionEvent event) throws Exception {

		dataToSend = Subscriber_Handler.DbSerachData("book_subjects", getSubject());
		ClientUI.chat.accept(dataToSend);
		if (ChatClient.answer.equals("good")) {
			ObservableList<Book_type_available> NewAvailable = FXCollections.observableArrayList();
			if (ChatClient.listAvailable.size() != 0) {
				for (int i = 0; i < (ChatClient.listAvailable.size() / 2); i++) {
					// Loop through the list and construct the string for each subscriber
					String name = ChatClient.listAvailable.get(i * 2);
					String location = ChatClient.listAvailable.get((i * 2) + 1);
					NewAvailable.add(new Book_type_available(name, location, null));
				}
				table.setItems(NewAvailable);
			} else {
				if (ChatClient.listBorrowed.size() != 0)
					for (int i = 0; i < (ChatClient.listBorrowed.size() / 2); i++) {
						// Loop through the list and construct the string for each subscriber
						String name = ChatClient.listBorrowed.get(i * 2);
						String borrowed = ChatClient.listBorrowed.get((i * 2) + 1);
						NewAvailable.add(new Book_type_available(name, null, borrowed));
					}
				table.setItems(NewAvailable);
			}
		} else {
			info.setText("Error, Data couldn't be found in DB, Check your inputs");
		}
	}

	public void getsendbtn3(ActionEvent event) throws Exception {

		dataToSend = Subscriber_Handler.DbSerachData("book_description", getInfo());
		ClientUI.chat.accept(dataToSend);
		if (ChatClient.answer.equals("good")) {
			ObservableList<Book_type_available> NewAvailable = FXCollections.observableArrayList();
			if (ChatClient.listAvailable.size() != 0) {
				for (int i = 0; i < (ChatClient.listAvailable.size() / 2); i++) {
					// Loop through the list and construct the string for each subscriber
					String name = ChatClient.listAvailable.get(i * 2);
					String location = ChatClient.listAvailable.get((i * 2) + 1);
					NewAvailable.add(new Book_type_available(name, location, null));
				}
				table.setItems(NewAvailable);
			} else {
				if (ChatClient.listBorrowed.size() != 0)
					for (int i = 0; i < (ChatClient.listBorrowed.size() / 2); i++) {
						// Loop through the list and construct the string for each subscriber
						String name = ChatClient.listBorrowed.get(i * 2);
						String borrowed = ChatClient.listBorrowed.get((i * 2) + 1);
						NewAvailable.add(new Book_type_available(name, null, borrowed));
					}
				table.setItems(NewAvailable);
			}
		} else {
			info.setText("Error, Data couldn't be found in DB, Check your inputs");
		}
	}
}
