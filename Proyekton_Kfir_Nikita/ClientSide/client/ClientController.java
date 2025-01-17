package client;

import java.io.IOException;
import common.ChatIF;

public class ClientController implements ChatIF {
    ChatClient client;

    public ClientController(String host, int port) {
        try {
            client = new ChatClient(host, port, this);
        } catch (IOException exception) {
            System.out.println("Error: Can't setup connection!" + " Terminating client.");
            System.exit(1);
        }
    }

    // Accepts messages from the UI and sends them to the server
    public void accept(Object str) {
        client.handleMessageFromClientUI(str);
    }

    // This method displays messages from the server
    public void display(String message) {
        // Update UI here
        System.out.println("> " + message);  // For debugging or to log messages
    }

    // Sends the loan request to the server
    public void sendLoanRequest(String barcode, String borrowerID, String loanDate, String returnDate) {
        // Prepare the loan request message (example format)
        String message = String.format("loanRequest %s %s %s %s", barcode, borrowerID, loanDate, returnDate);

        // Send the message to the server
        client.handleMessageFromClientUI(message);  // This sends the request through the client
    }

    // Sets the response handler to process server responses asynchronously
    public void setResponseHandler(java.util.function.Consumer<String> responseHandler) {
        client.setResponseHandler(responseHandler);  // Pass the response handler to the client
    }
}
