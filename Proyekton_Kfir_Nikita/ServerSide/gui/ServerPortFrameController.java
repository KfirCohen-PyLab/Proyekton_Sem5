package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Server.EchoServer;

import java.io.IOException;

public class ServerPortFrameController {

    private EchoServer server; // Reference to the EchoServer instance

    @FXML
    private Button btnExit;
    @FXML
    private Button btnDone;
    @FXML
    private Label lbllist;
    @FXML
    private TextField portxt;

    private String getPort() {
        return portxt.getText();
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));
        Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void Done(ActionEvent event) {
        String p = getPort();

        if (p.trim().isEmpty()) {
            System.out.println("You must enter a port number");
        } else {
            try {
                int port = Integer.parseInt(p);
                server = new EchoServer(port); // Create EchoServer instance
                server.listen(); // Start the server
                System.out.println("Server started on port: " + port);

                openConnectedClientsView(event); // Open Connected Clients View
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number. Please enter a valid number.");
            } catch (IOException e) {
                System.out.println("Error starting the server: " + e.getMessage());
            }
        }
    }

    private void openConnectedClientsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ServerStatus.fxml"));
        Parent root = loader.load();

        // Pass the EchoServer instance to ConnectedClientsControl
        ConnectedClientsControl controller = loader.getController();
        controller.setServer(server);

        Stage stage = new Stage();

        stage.setTitle("Connected Clients");
        stage.setScene(new Scene(root));
        stage.show();

        // Hide the current window
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("Server Exited");
        if (server != null) {
            server.close();
        }
        System.exit(0);
    }
}
