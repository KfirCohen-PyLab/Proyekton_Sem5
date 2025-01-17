
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.ChatIF;

import java.io.*;
import java.util.ArrayList;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	/**
	 * A static boolean flag used to indicate whether the client is waiting for a
	 * response from the server. This is utilized to manage synchronous
	 * communication between the client and server.
	 */
	public static boolean awaitResponse = false;
	/**
	 * A static `ArrayList` that stores strings representing data received from the
	 * server. This can hold multiple pieces of data for processing or display. for
	 * function infoController
	 */
	public static ArrayList<String> list = new ArrayList<String>();

	public static ArrayList<String> listAvailable = new ArrayList<String>(); // for search function

	public static ArrayList<String> listBorrowed = new ArrayList<String>(); // for search function
	/**
	 * A static string variable used to store the server's response. This is used to
	 * provide feedback or status messages to the client.
	 */
	public static String answer;

	public static String status;
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		// openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		answer = "";
		awaitResponse = false;
		if (msg instanceof ArrayList<?>) {
			ArrayList<?> list_2 = (ArrayList<?>) msg;
			switch ((String) list_2.get(0)) {
			case "admin":
				status = "admin";
				break;
			case "active":
				status = "active";
				break;
			case "books":
				answer = "good";
				listBorrowed = new ArrayList<String>();
				listAvailable = new ArrayList<String>();
				for (int i = 0; i < (list_2.size() / 5); i++) {
					if (list_2.get((i * 5) + 2).equals("borrowed")) {
						listBorrowed.add((String) list_2.get((i * 5) + 1));
						listBorrowed.add((String) list_2.get((i * 5) + 3));
					} else {
						listAvailable.add((String) list_2.get((i * 5) + 1));
						listAvailable.add((String) list_2.get((i * 5) + 4));
					}
				}
				break;
			case "good":
				answer = "Information has been sent";
				break;
			case "fail":
				answer = "Wrong Parameters, can't find in DB";
				break;
			case "result":
				list = new ArrayList<String>();
				answer = "Id found";
				for (int i = 0; i < (list_2.size() / 8); i++) {
					list.add((String) list_2.get((i * 8) + 1));
					list.add((String) list_2.get((i * 8) + 2));
					list.add((String) list_2.get((i * 8) + 3));
					list.add((String) list_2.get((i * 8) + 4));
					list.add((String) list_2.get((i * 8) + 5));
				}
				break;
			case "Wrong Credentials":
				answer = "Wrong Credentials";
				break;
			default:
				break;
			}
		}

	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(Object message) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);
			// wait for response
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

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
