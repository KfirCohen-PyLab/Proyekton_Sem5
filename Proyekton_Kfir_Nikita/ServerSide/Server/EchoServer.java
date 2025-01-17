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

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("Message received: " + msg + " from " + client + " (" + msg.getClass().getName() + ")");
		try {
			ArrayList<String> result = dbHandler.MessageHandler((ArrayList<String>) msg);

			sendMessageToSpecificClient(client, result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void sendMessageToSpecificClient(ConnectionToClient client, Object msg) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			System.out.println("Error sending message to client: " + e.getMessage());
		}
	}

	@Override
	protected void serverStarted() {
		dbHandler.ConnectDB();
		System.out.println("Server listening for connections on port " + getPort());
	}

	@Override
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		connectedClients.add(client);
		System.out.println(
				"connected: " + client.getInetAddress().getHostName() + ":" + client.getInetAddress().getHostAddress());
	}

	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		connectedClients.remove(client);
		System.out.println("disconnected: " + client.getInetAddress().getHostName() + ":"
				+ client.getInetAddress().getHostAddress());
	}

	protected synchronized void connectionException(ConnectionToClient client, Throwable exception) {
		System.out.println("Connection to client lost: " + client.getInetAddress().getHostAddress());
		clientDisconnected(client);
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
					clientsToRemove.add(client);
				}

			}
			connectedClients.removeAll(clientsToRemove);
		}
	}
}
