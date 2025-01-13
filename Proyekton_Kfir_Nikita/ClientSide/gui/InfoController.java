package gui;

import java.net.URL;
import java.util.ResourceBundle;

import GUI_Handler.Subscriber_Handler;
import GUI_Handler.switchScreen;
import Types_For_Table_view.Subscriber_Type;
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

public class InfoController implements Initializable {
	// Holds the data that will be sent to the database
	private Object dataToSend;

	// FXML injected elements
	@FXML
	private TextField txtId; // TextField where the user will enter the ID to look up in the database

	@FXML
	private Button btnclose = null; // Button to close the current screen

	@FXML
	private Button btnsend = null; // Button to send the ID to the database

	@FXML
	private Button btnall = null; // Button to retrieve all data from the database

	@FXML
	private Label info; // Label for displaying all retrieved information or status

	// Getter method for retrieving the ID from the text field
	private String getID() {
		return txtId.getText(); // Returns the text from the input field
	}

	@FXML
	private TableView<Subscriber_Type> table;
	@FXML
	private TableColumn<Subscriber_Type, String> id;
	@FXML
	private TableColumn<Subscriber_Type, String> name;
	@FXML
	private TableColumn<Subscriber_Type, String> history;
	@FXML
	private TableColumn<Subscriber_Type, String> phonenumber;
	@FXML
	private TableColumn<Subscriber_Type, String> email;

	public void btnclose(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/OptionFrame", "Option menu"); // Switches to Option menu screen
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id.setCellValueFactory(new PropertyValueFactory<>("ID"));
		name.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
		history.setCellValueFactory(new PropertyValueFactory<>("Name"));
		phonenumber.setCellValueFactory(new PropertyValueFactory<>("History"));
		email.setCellValueFactory(new PropertyValueFactory<>("Email"));
	}

	// Method to start the Option menu screen
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/InfoFrame.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/InfoFrame.css").toExternalForm());

		primaryStage.setTitle("Infromation Center");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	/**
	 * Method called when the "Send" button is clicked. It retrieves the data
	 * associated with the entered ID and displays it.
	 * 
	 * @param event The ActionEvent triggered by the button click
	 * @throws Exception If an error occurs while retrieving data or interacting
	 *                   with the server
	 */
	public void getsendbtn(ActionEvent event) throws Exception {
		if (!(getID() == null)) {
			dataToSend = Subscriber_Handler.DbDataGet(getID()); // Retrieves the data for the entered ID from the //
																// database
			ClientUI.chat.accept(dataToSend); // Sends the request to the server
			if (!(ChatClient.answer.equals("fail")) && ChatClient.list.size() != 0) {
				ObservableList<Subscriber_Type> Newlist = FXCollections.observableArrayList();
				for (int i = 0; i < (ChatClient.list.size() / 5); i++) {
					String id = ChatClient.list.get(i * 5);
					String name = ChatClient.list.get((i * 5) + 1);
					String history = ChatClient.list.get((i * 5) + 2);
					String phoneNumber = ChatClient.list.get((i * 5) + 3);
					String email = ChatClient.list.get((i * 5) + 4);
					Newlist.add(new Subscriber_Type(id, name, history, phoneNumber, email));
				}
				table.setItems(Newlist);
			} else {
				info.setText("Error, Couldn't find in DB");
			}
		} else {
			info.setText("ID is empty");
		}
	}

	/**
	 * Method called when the "Get All" button is clicked. It retrieves all data
	 * from the database and displays it in the Biglabel.
	 * 
	 * @param event The ActionEvent triggered by the button click
	 * @throws Exception If an error occurs while retrieving data or interacting
	 *                   with the server
	 */
	public void getalldbtn(ActionEvent event) throws Exception {
		dataToSend = Subscriber_Handler.DbDataGet("all"); // Retrieve all data
		ClientUI.chat.accept(dataToSend); // Sends the request to the server
		ObservableList<Subscriber_Type> Newlist = FXCollections.observableArrayList();
		for (int i = 0; i < (ChatClient.list.size() / 5); i++) {
			String id = ChatClient.list.get(i * 5);
			String name = ChatClient.list.get((i * 5) + 1);
			String history = ChatClient.list.get((i * 5) + 2);
			String phoneNumber = ChatClient.list.get((i * 5) + 3);
			String email = ChatClient.list.get((i * 5) + 4);
			Newlist.add(new Subscriber_Type(id, name, history, phoneNumber, email));
		}
		table.setItems(Newlist);
	}
}
