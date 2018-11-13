package src.server.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import javafx.application.Platform;
import measurements.BasicMeasurements;
import src.server.database.CheckPW;
import src.server.database.SaveMethod;
import src.server.database.connection.ConnectionToDB;
import version.Version;

/**
 * start of the server side of the application. It does 2 things, beside
 * regulating the server. It also makes a basic GUI that shows that all the
 * connection to the server apart from all the errors and other println's.
 * 
 * 
 * @author Mathieu
 * @version 09/27/2018
 *
 */
public class Server {

	ServerGUI gui = null;

	public Server(ServerGUI gui) {
		this.gui = gui;
	}

	// Number a client
	private int clientNo = 0;

	public void runServer() {
		new Thread(() -> {
			try {
				// Create a server socket
				ServerSocket serverSocket = new ServerSocket(8000);
				ServerSocket objectServerSocket = new ServerSocket(8001);
				gui.ta.appendText("MultiThreadServer started at " + new Date() + '\n');

				while (true) {
					// Listen for a new connection request
					Socket socket = serverSocket.accept();
					Socket objectSocket = objectServerSocket.accept();

					// Increment clientNo
					clientNo++;

					Platform.runLater(() -> {
						// Display the client number
						gui.ta.appendText("Starting thread for client " + clientNo + " at " + new Date() + '\n');

						// Find the client's host name, and IP address
						InetAddress inetAddress = socket.getInetAddress();
						InetAddress inetAddressO = objectSocket.getInetAddress();
						gui.ta.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
						gui.ta.appendText(
								"Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");
						gui.ta.appendText("Object Client " + clientNo + "'s IP Address is "
								+ inetAddressO.getHostAddress() + "\n");
					});

					// Create and start a new thread for the connection
					new Thread(new HandleAClient(socket, objectSocket)).start();
				}
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}).start();
	}

	// Define the thread class for handling new connection
	class HandleAClient implements Runnable {
		private Socket socket; // A connected socket
		private Socket objectSocket; // A connected socket
		ConnectionToDB conn = null;

		/**
		 * Construct a thread
		 * 
		 * @param socket
		 */
		public HandleAClient(Socket socket, Socket objectSocket) {
			this.socket = socket;
			this.objectSocket = objectSocket;
		}

		/** Run a thread */
		public void run() {
			conn = ConnectionToDB.createConn();
			// Create data input and output streams
			try (DataInputStream input = new DataInputStream(socket.getInputStream());
					DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					ObjectInputStream inputFromClient = new ObjectInputStream(objectSocket.getInputStream())) {

				outer: while (true) {
					String option = input.readUTF();
					ServerGUI.print("option is " + option);
					Optional<Integer> sessionID = Optional.empty();
					switch (option) {
					case "Token":
						ServerGUI.print("in token");
						sessionID = checkToken(input, output);
						break;
					case "Password":
						ServerGUI.print("in password");
						sessionID = checkPW(input, output);
						break;
					case "BasicMeasurements":
						ServerGUI.print("in measurements");
						saveMeasurement(sessionID,inputFromClient);
						break outer;
					}
					ServerGUI.print("\n");

				}
//			Thread.interrupted();
			} catch (SocketException e) {
//				 System.out.println("IOException");

				e.printStackTrace();
			} catch (EOFException e) {
//				 System.out.println("IOException");

				e.printStackTrace();
			} catch (IOException e) {

				// System.out.println("IOException");

				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			} finally {

				Thread.interrupted();
			}
		}

	private Optional<Integer> checkToken(DataInputStream input, DataOutputStream output) throws IOException {
		ConnectionToDB conn = ConnectionToDB.createConn();
		CheckPW cpw = new CheckPW(conn);

		int length = input.readInt();
		char[] c = new char[length];
		for (int i = 0; i < length; i++) {
			c[i] = input.readChar();
//						ServerGUI.print(""+c[i]);
		}

		Optional<Integer> sessionID = Optional.ofNullable(null);
		try {
			sessionID = cpw.checkToken(c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c = null;
//		ServerGUI.print("checking version; " + Version.VERSION + '\n');
//		ServerGUI.print("SessionID; " + sessionID + '\n');

		if (sessionID.isPresent()) {
//			ServerGUI.print(" Correct token ");
			output.writeUTF("Correct Token");
			output.flush();
			return sessionID;
		} else {
//			output.writeUTF("Wrong token");
			output.flush();
			ServerGUI.print(" wrong token");
			return Optional.empty();

		}
	}

	private Optional<Integer> checkPW(DataInputStream input, DataOutputStream output) throws IOException {
		ConnectionToDB conn = ConnectionToDB.createConn();
		CheckPW cpw = new CheckPW(conn);
		String username = input.readUTF();
		int length = input.readInt();
		char[] c = new char[length];
		for (int i = 0; i < length; i++) {
			c[i] = input.readChar();
//						ServerGUI.print(""+c[i]);
		}

		// TODO Auto-generated method stub
		return null;
	}

	private void saveMeasurement(Optional<Integer> sessionID, ObjectInputStream inputFromClient)
			throws ClassNotFoundException, IOException {
		if (sessionID.isPresent()) {

			// Continuously serve the client
			BasicMeasurements object = (BasicMeasurements) inputFromClient.readObject();

			Platform.runLater(() -> {
				gui.ta.appendText("TableName received from client: " + object.getTableName() + '\n');
				gui.ta.appendText("first item in the data set: " + object.toString() + '\n');
			});

			SaveMethod sm = new SaveMethod(conn);
			sm.SaveData(object, sessionID.get());
		}
	}
}}
