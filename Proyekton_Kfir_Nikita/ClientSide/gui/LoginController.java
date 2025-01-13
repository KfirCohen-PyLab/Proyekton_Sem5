package gui;

import java.util.ArrayList;

import GUI_Handler.Subscriber_Handler;
import GUI_Handler.switchScreen;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Button btnExit = null; // Button to exit the application
	@FXML
	private Button btnDone = null; // Button to proceed with login and connect to the server
	@FXML
	private TextField txtIP = null; // TextField for entering the server sP address
	@FXML
	private TextField txtPort = null; // TextField for entering the server port number
	@FXML
	private Label info; // Label to display messages or errors to the user

	// Getter method for retrieving the port number
	private String getport() {
		return txtPort.getText();
	}

	// Getter method for retrieving the IP address
	private String getip() {
		return txtIP.getText();
	}

	// Method called when the "Done" button is clicked
	// Attempts to establish a connection to the server using the provided IP and
	// port
	public void Done(ActionEvent event) throws Exception {
		try {
			info.setText(" ");
			String ip = getip(); // Retrieve IP from the txtIP TextField
			String port = getport(); // Retrieve Port from the txtPort TextField

			// Validate inputs: check if fields are empty or if port is out of valid range
			if (ip.trim().isEmpty() || port.trim().isEmpty()) {
				info.setText("Input Error, IP and Port fields cannot be empty.");
			} else if (Integer.parseInt(port) < 0 || Integer.parseInt(port) > 65535) {
				info.setText("Input Error, Port field incorrect.");
			} else {
				// Attempt to connect to the server with the provided IP and port
				int portNumber = Integer.parseInt(port); // Convert port string to integer
				ClientUI.connectToServer(ip, portNumber); // Attempt connection to the server
				try {
					ArrayList<String> dataToSend = new ArrayList<>();
					dataToSend = Subscriber_Handler.Login(); // Fetch login data
					ClientUI.chat.accept(dataToSend); // Send data to the server
				} catch (Exception E) {
					info.setText("Connection Error, couldn't reach to server"); // Display error if server connection
																				// fails
				}

				// If all validations pass, switch to the Option menu screen
				switchScreen.switchTo(event, "/gui/OptionFrame", "Option Menu");
			}
		} catch (NumberFormatException e) {
			info.setText("Invalid Input, Port must be a valid number");
		} catch (Exception e) {
			info.setText(e.toString());
		}
	}

	// Method to start the Login screen
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/LoginFrame.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/LoginFrame.css").toExternalForm());
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Method called when the "Exit" button is clicked
	// Closes the application
	public void getExitBtn(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/ExitFrame", "Bye");
	}
}
