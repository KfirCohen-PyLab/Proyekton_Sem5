package client;

import ocsf.client.AbstractClient;
import common.ChatIF;

import java.io.*;
import java.util.ArrayList;

public class ChatClient extends AbstractClient {
    ChatIF clientUI;
    public static boolean awaitResponse = false;
    public static ArrayList<String> list = new ArrayList<>();
    public static ArrayList<String> listAvailable = new ArrayList<>();
    public static ArrayList<String> listBorrowed = new ArrayList<>();
    public static String answer;
    private java.util.function.Consumer<String> responseHandler;

    public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
        super(host, port);
        this.clientUI = clientUI;
    }

    @Override
    public void handleMessageFromServer(Object msg) {
        answer = "";
        awaitResponse = false;

        if (msg instanceof ArrayList<?>) {
            ArrayList<?> list_2 = (ArrayList<?>) msg;
            switch ((String) list_2.get(0)) {
                case "books":
                    processBooksData(list_2);  // Calls the method to process books data
                    break;
                case "result":
                    processSearchResult(list_2);  // Calls the method to process search result
                    break;
                case "good":
                    answer = "Information has been sent";
                    break;
                case "fail":
                    answer = "Wrong Parameters, can't find in DB";
                    break;
                default:
                    break;
            }
        }

        // If a response handler exists, process the response
        if (responseHandler != null) {
            responseHandler.accept(answer);  // Call the response handler with the result
        }
    }

    // Sets the response handler
    public void setResponseHandler(java.util.function.Consumer<String> responseHandler) {
        this.responseHandler = responseHandler;
    }

    public void handleMessageFromClientUI(Object message) {
        try {
            openConnection();  // Ensure connection is open
            awaitResponse = true;
            sendToServer(message);
            while (awaitResponse) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            clientUI.display("Could not send message to server: Terminating client." + e);
            quit();
        }
    }

    public static String getString() {
        return answer;
    }

    public static ArrayList<String> getArray() {
        return list;
    }

    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    // Method to process book data response from server
    private void processBooksData(ArrayList<?> data) {
        // Assuming the data contains book names or identifiers
        listAvailable.clear();
        for (int i = 1; i < data.size(); i++) {
            listAvailable.add((String) data.get(i));  // Add books to available list
        }
        clientUI.display("Books data processed successfully.");
    }

    // Method to process search result from server
    private void processSearchResult(ArrayList<?> data) {
        // Assuming the data contains search results
        listBorrowed.clear();
        for (int i = 1; i < data.size(); i++) {
            listBorrowed.add((String) data.get(i));  // Add results to borrowed list
        }
        clientUI.display("Search results processed successfully.");
    }
}
