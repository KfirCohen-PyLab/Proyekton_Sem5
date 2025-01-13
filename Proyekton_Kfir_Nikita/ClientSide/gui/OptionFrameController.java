package gui;

import GUI_Handler.switchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OptionFrameController {

	@FXML
	private Button btnLoan = null; // Button to exit and switch to the Login screen

	@FXML
	private Button btnExit = null; // Button to exit and switch to the Login screen

	@FXML
	private Button btnUptade = null; // Button to switch to the Update Information screen

	@FXML
	private Button btnData = null; // Button to retrieve data from the server

	@FXML
	private Button btnSearch = null; // Button to Search

	// Method to start the Option menu screen
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/OptionFrame.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/OptionFrame.css").toExternalForm());

		primaryStage.setTitle("Option menu");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	// Method called when the "Update" button is clicked
	// Switches to the Update Information screen
	public void getUpdateBtn(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/UptadeForm", "Uptade Information");
	}

	// Method called when the "Data" button is clicked
	// Switches to the Information center screen
	public void btnData(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/InfoFrame", "Information center");
	}

	// Method called when the "Exit" button is clicked
	// Switches back to the Login screen
	public void getExitBtn(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/ExitFrame", "Bye");
	}

	// Method called when the "Loan" button is clicked
	// Switches to the Loan screen
	public void btnLoan(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/LoanFrame", "Loan screen");
	}

	// Method called when the "Search" button is clicked
	// Switches to the Loan screen
	public void btnSearch(ActionEvent event) throws Exception {
		switchScreen.switchTo(event, "/gui/SearchFrame", "Search screen");
	}
}
