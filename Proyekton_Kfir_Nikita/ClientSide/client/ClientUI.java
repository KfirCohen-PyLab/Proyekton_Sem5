package client;

import javafx.application.Application;

import javafx.stage.Stage;
import gui.LoginController;

public class ClientUI extends Application {
	public static ClientController chat; // only one instance

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginController aFrame = new LoginController(); // create Login Frame
		aFrame.start(primaryStage);
	}

	public static void connectToServer(String ip, int port) throws Exception {
		chat = new ClientController(ip, port); // Establish connection
	}

	public static void close() throws Exception {
		chat.client.quit();
	}

}
