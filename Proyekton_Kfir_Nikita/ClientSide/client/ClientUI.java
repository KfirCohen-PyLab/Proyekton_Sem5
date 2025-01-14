package client;

import javafx.application.Application;
import javafx.stage.Stage;
import gui.LoginController;

public class ClientUI extends Application {
    public static ClientController chat; // Only one instance for the connection

    public static void main(String args[]) throws Exception {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginController loginFrame = new LoginController(); // Create the login frame
        loginFrame.start(primaryStage); // Start the login frame UI
    }

    // Method to establish connection to the server
    public static void connectToServer(String ip, int port) throws Exception {
        if (chat == null) {
            chat = new ClientController(ip, port); // Establish connection with the server
        }
    }

    // Method to close the client connection
    public static void close() throws Exception {
        if (chat != null && chat.client != null) {
            chat.client.quit(); // Close the connection and terminate the client
            chat = null; // Set chat to null after quitting
        }
    }
}
