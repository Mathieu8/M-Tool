package src.server.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
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

	public Server() {
	}

	// Number a client
	private int clientNo = 0;

	public void runServer() {
		new Thread(() -> {
			try {
				// Create a server socket
				ServerSocket serverSocket = new ServerSocket(8002);
				ServerSocket objectServerSocket = new ServerSocket(8001);
				ServerGUI.print("MultiThreadServer started at " + new Date());

				while (true) {
					// Listen for a new connection request
					Socket socket = serverSocket.accept();
					Socket objectSocket = objectServerSocket.accept();

					// Increment clientNo
					clientNo++;

					// Display the client number
					ServerGUI.print("Starting thread for client " + clientNo + " at " + new Date());
					ServerGUI.print("Amount of Threads active " + Thread.activeCount());

					// Find the client's host name, and IP address
					InetAddress inetAddress = socket.getInetAddress();
					InetAddress inetAddressO = objectSocket.getInetAddress();
					ServerGUI.print("Client " + clientNo + "'s host name is " + inetAddress.getHostName());
					ServerGUI.print("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress());
					ServerGUI.print("Object Client " + clientNo + "'s IP Address is " + inetAddressO.getHostAddress());

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
		final ConnectionToDB conn = ConnectionToDB.createConn();
		String threadName;

		/**
		 * Construct a thread
		 * 
		 * @param socket
		 * @param objectSocket
		 */
		public HandleAClient(Socket socket, Socket objectSocket) {
			this.socket = socket;
			this.objectSocket = objectSocket;
			ServerGUI.print("in HandleClient()");

		}

		/** Run a thread */
		public void run() {
			threadName = Thread.currentThread().getName();
			ServerGUI.print(threadName + " in run()");
//			 ConnectionToDB.createConn();
//			ServerGUI.print("after ConnectionToDB.createConn()");
			// Create data input and output streams
			try (DataInputStream input = new DataInputStream(socket.getInputStream());
					DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					ObjectInputStream inputFromClient = new ObjectInputStream(objectSocket.getInputStream());) {

				Optional<Integer> sessionID = Optional.empty();
				outer: while (true) {
					ServerGUI.print("\n" + threadName + " running while again \n");

					ServerGUI.print(threadName + " waiting for option");
					String option = input.readUTF();
					ServerGUI.print(threadName + " option is " + option);
					switch (option) {
					case "Token":
						ServerGUI.print(threadName + " in token");
						sessionID = checkToken(input, output);
						break;
					case "Password":
						ServerGUI.print(threadName + " in password");
						sessionID = checkPW(input, output);
						break;
					case "BasicMeasurements":
						ServerGUI.print(threadName + " in measurements");
						saveMeasurement(sessionID, inputFromClient);
						break outer;
					default:
						ServerGUI.print(threadName + "not an valid option: " + option);
					}

				}
			} catch (ClassNotFoundException | IOException e) {
				ServerGUI.print("Error " + e);
				e.printStackTrace();

			} finally {
				ServerGUI.print(threadName + " number of active threads: " + Thread.activeCount());

				ServerGUI.print(threadName + " interrupting thread");
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
				ServerGUI.print(threadName + " reading object");
				// Continuously serve the client
				BasicMeasurements object = (BasicMeasurements) inputFromClient.readObject();
				ServerGUI.print(threadName + " read object");

				ServerGUI.print(threadName + " TableName received from client: " + object.getTableName());
				ServerGUI.print(threadName + " first item in the data set: " + object.toString());

				SaveMethod sm = new SaveMethod(conn);
				sm.SaveData(object, sessionID.get());
			} else {
				throw new IllegalArgumentException(
						"Optional<Ineger> sessionID is required. please make sure it pass thru the password check or token test");
			}
		}
	}
}
