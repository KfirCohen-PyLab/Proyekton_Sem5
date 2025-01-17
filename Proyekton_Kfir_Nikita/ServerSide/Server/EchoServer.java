package Server;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ocsf.server.*;
import logic.dbHandler;

public class EchoServer extends AbstractServer {

    private final Set<ConnectionToClient> connectedClients = Collections.synchronizedSet(new HashSet<>());

    public EchoServer(int port) {
        super(port);
    }

    // Handle incoming messages and interact with dbHandler to process the request
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg + " from " + client + " (" + msg.getClass().getName() + ")");
        try {
            // The message is expected to be an ArrayList of Strings
            ArrayList<String> loanData = (ArrayList<String>) msg;

            // Check if the message is a loan request and process accordingly
            if (loanData.get(0).equals("loanRequest")) {
                // Extract individual arguments
                int bookId = Integer.parseInt(loanData.get(1)); // Assuming index 1 contains the book ID
                int borrowerId = Integer.parseInt(loanData.get(2)); // Assuming index 2 contains the subscriber ID
                String loanDate = loanData.get(3); // Assuming index 3 contains the loan date
                String returnDate = loanData.get(4); // Assuming index 4 contains the return date

                // Call processLoanRequest with the extracted values
                dbHandler.processLoanRequest(bookId, borrowerId, loanDate, returnDate);

                // Send response back to the client (example response)
                sendMessageToSpecificClient(client, "Loan request processed successfully.");
            } else {
                // Handle other types of requests if needed
                ArrayList<String> result = dbHandler.MessageHandler(loanData);
                sendMessageToSpecificClient(client, result);  // Send back the result to the client
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Send a response back to a specific client
    private void sendMessageToSpecificClient(ConnectionToClient client, Object msg) {
        try {
            client.sendToClient(msg);
        } catch (IOException e) {
            System.out.println("Error sending message to client: " + e.getMessage());
        }
    }

    @Override
    protected void serverStarted() {
        dbHandler.ConnectDB();  // Connect to the database when the server starts
        System.out.println("Server listening for connections on port " + getPort());
    }

    @Override
    protected void serverStopped() {
        System.out.println("Server has stopped listening for connections.");
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        connectedClients.add(client);  // Add client to the connected list
        System.out.println(
                "connected: " + client.getInetAddress().getHostName() + ":" + client.getInetAddress().getHostAddress());
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        connectedClients.remove(client);  // Remove client from the connected list
        System.out.println("disconnected: " + client.getInetAddress().getHostName() + ":" 
                + client.getInetAddress().getHostAddress());
    }

    protected synchronized void connectionException(ConnectionToClient client, Throwable exception) {
        System.out.println("Connection to client lost: " + client.getInetAddress().getHostAddress());
        clientDisconnected(client);  // Handle client disconnection due to error
    }

    public synchronized ArrayList<String> getConnectedClients() {
        ArrayList<String> clientInfoList = new ArrayList<>();
        synchronized (connectedClients) {
            for (ConnectionToClient client : connectedClients) {
                clientInfoList
                        .add(client.getInetAddress().getHostName() + ":" + client.getInetAddress().getHostAddress());
            }
        }
        return clientInfoList;
    }

    public synchronized void checkConnections() {
        Set<ConnectionToClient> clientsToRemove = new HashSet<>();
        synchronized (connectedClients) {
            for (ConnectionToClient client : connectedClients) {
                if (!client.isAlive()) {
                    clientsToRemove.add(client);  // Identify dead connections
                }
            }
            connectedClients.removeAll(clientsToRemove);  // Remove dead connections
        }
    }
}
