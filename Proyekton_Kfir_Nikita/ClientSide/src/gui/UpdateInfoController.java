package gui;

import GUI_Handler.Subscriber_Handler;
import GUI_Handler.switchScreen;
import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateInfoController {
	private Object dataToSend;

	@FXML
	private TextField txtId; // TextField where the user enters the ID

	@FXML
	private TextField txtPhoneNumber; // TextField where the user enters the phone number to update

	@FXML
	private TextField txtEmail; // TextField where the user enters the email to update

	@FXML
	private Button btnclose = null; // Button to close and return to the Option menu screen

	@FXML
	private Button btnsend = null; // Button to send the update request to the server

	@FXML
	private Label info; // Button to send the update request to the server

	// Getter method for retrieving the ID
	private String getID() {
		return txtId.getText();
	}

	// Getter method for retrieving the phone number
	private String getPN() {
		return txtPhoneNumber.getText();
	}

	// Getter method for retrieving the email
	private String getEmail() {
		return txtEmail.getText();
	}

	// Method called when the "Close" button is clicked
	// Switches the screen back to the Option menu screen
	public void btnclose(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/OptionFrame", "Option menu");
	}

	// Method called when the "Send" button is clicked
	// Sends the updated data (ID, phone number, and email) to the server for
	public void getsendbtn(ActionEvent event) throws Exception {
		if (!(getID().equals(null))) {
			if (getEmail().contains("@")) {
				if (getPN().length() == 10) {
					dataToSend = Subscriber_Handler.SubDataChange(getID(), getPN(), getEmail());
					ClientUI.chat.accept(dataToSend);
					info.setText(ChatClient.answer);
				} else {
					info.setText("Error in Phone Number");
				}
			} else {
				info.setText("Error in Email");
			}
		} else {
			info.setText("ID filed is empty");
		}
	}
}
