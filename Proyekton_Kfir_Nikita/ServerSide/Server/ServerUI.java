package Server;

import javafx.application.Application;
import javafx.stage.Stage;
import gui.ServerPortFrameController;

public class ServerUI extends Application {

    // Default port for the server
    public static final int DEFAULT_PORT = 5555;

    public static void main(String args[]) throws Exception {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the frame to allow the user to set the port
        ServerPortFrameController serverPortFrame = new ServerPortFrameController(); // Initialize server port frame
        serverPortFrame.start(primaryStage); // Start the port setting frame
    }

    // Method to start the server with the given port
    public static void runServer(String port) {
        int serverPort = 0;

        try {
            // Convert the input string to an integer for the port
            serverPort = Integer.parseInt(port);
        } catch (Throwable t) {
            System.out.println("ERROR - Invalid port!");
            return;
        }

        // Initialize the server (EchoServer is just a placeholder for actual server logic)
        EchoServer server = new EchoServer(serverPort);

        try {
            // Start listening for client connections on the specified port
            server.listen();
        } catch (Exception ex) {
            System.out.println("ERROR - Could not start server!");
        }
    }
}
