package gui;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ExitController {
	@FXML
	private Button btnExit = null; // Button to exit the application

	// Method to start the Login screen
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ExitFrame.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/ExitFrame.css").toExternalForm());
		primaryStage.setTitle("Exit");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Method called when the "Exit" button is clicked
	// Closes the application
	public void getExitBtn(ActionEvent event) throws Exception {
		ClientUI.close();
	}
}
