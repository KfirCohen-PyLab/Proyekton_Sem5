package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import Server.EchoServer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectedClientsControl {

    private EchoServer server; // Reference to the EchoServer instance
    private Timer timer;       // Timer for refreshing client list

    @FXML
    private ListView<String> clientsListView;

    public void setServer(EchoServer server) {
        this.server = server;
        startClientMonitor();
    }

    private void startClientMonitor() {
        timer = new Timer(true); // Daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (server != null) {
                        // Check the connections and update the client list
                        server.checkConnections(); // Ensure the server checks client connections
                        ArrayList<String> connectedClients = server.getConnectedClients();
                        updateClientList(connectedClients);
                    }
                });
            }
        }, 0, 5000); // Monitor every 5 seconds
    }

    private void updateClientList(ArrayList<String> connectedClients) {
        clientsListView.getItems().setAll(connectedClients);
    }

    public void stopClientMonitor() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void getExitBtn(ActionEvent event) throws Exception {
        System.out.println("Server Exited");
        stopClientMonitor();
        if (server != null) {
            server.close();
        }
        System.exit(0);
    }
}
