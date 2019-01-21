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

	// Number a client
	private int clientNo = 0;

	public void runServer() {
		var test = true;
		new Thread(() -> {
			try (
					// Create a server socket
					ServerSocket serverSocket = new ServerSocket(8002);
					ServerSocket objectServerSocket = new ServerSocket(8001);) {
				System.out.println("#" + clientNo + " MultiThreadServer started at " + new Date());

				while (true) {

					System.out.println("#" + clientNo + " serverSocket.isClosed() " + serverSocket.isClosed());

					// Listen for a new connection request
					Socket socket = serverSocket.accept();
					Socket objectSocket = objectServerSocket.accept();
					System.out.println("#" + clientNo + " Socket.isClosed() " + socket.isClosed());

					// Increment clientNo
					clientNo++;

					// Display the client number
					System.out.println("#" + clientNo + " Starting thread for client " + clientNo + " at " + new Date());
					System.out.println("#" + clientNo + " Amount of Threads active " + Thread.activeCount());

					// Find the client's host name, and IP address
					InetAddress inetAddress = socket.getInetAddress();
					InetAddress inetAddressO = objectSocket.getInetAddress();
					System.out.println(
							"#" + clientNo + " Client " + clientNo + "'s host name is " + inetAddress.getHostName());
					System.out.println("#" + clientNo + " Client " + clientNo + "'s IP Address is "
							+ inetAddress.getHostAddress());
					System.out.println("#" + clientNo + " Object Client " + clientNo + "'s IP Address is "
							+ inetAddressO.getHostAddress());

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
			System.out.println("in HandleClient()");

		}

		/** Run a thread */
		public void run() {
			threadName = Thread.currentThread().getName();
			System.out.println(threadName + " in run()");
//			 ConnectionToDB.createConn();
//			System.out.println("after ConnectionToDB.createConn()");
			// Create data input and output streams
			try (DataInputStream input = new DataInputStream(socket.getInputStream());
					DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					ObjectInputStream inputFromClient = new ObjectInputStream(objectSocket.getInputStream());) {

				Optional<Integer> sessionID = Optional.empty();

				outer: while (true) {
					System.out.println("\n" + threadName + " running while again \n");

					System.out.println(threadName + " input.available(): " + input.available());
					System.out.println(threadName + " waiting for option");
					String option = input.readUTF();
					System.out.println(threadName + " option is " + option);
					switch (option) {
					case "Token":
						System.out.println(threadName + " in token");
						sessionID = checkToken(input, output);
						break;
					case "Password":
						System.out.println(threadName + " in password");
						sessionID = checkPW(input, output);
						break;
					case "BasicMeasurements":
						System.out.println(threadName + " in measurements");
						saveMeasurement(sessionID, inputFromClient);
						break outer;
					case "Close":
						System.out.println(threadName + " Close");
						break outer;
					default:
						System.out.println(threadName + "not an valid option: " + option);
					}

				}
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Error " + e);
				e.printStackTrace();

			} finally {
				System.out.println(threadName + " number of active threads: " + Thread.activeCount());

				System.out.println(threadName + " interrupting thread");
				clientNo--;
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
//						System.out.println(""+c[i]);
			}

			Optional<Integer> sessionID = Optional.ofNullable(null);
			try {
				sessionID = cpw.checkToken(c);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c = null;
//		System.out.println("checking version; " + Version.VERSION + '\n');
//		System.out.println("SessionID; " + sessionID + '\n');

			if (sessionID.isPresent()) {
				System.out.println(" Correct token ");
				output.writeUTF("Correct Token");
				output.flush();
				return sessionID;
			} else {
				output.writeUTF("Wrong token");
				output.flush();
				System.out.println(" wrong token");
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
//						System.out.println(""+c[i]);
			}

			// TODO Auto-generated method stub
			return null;
		}

		private void saveMeasurement(Optional<Integer> sessionID, ObjectInputStream inputFromClient)
				throws ClassNotFoundException, IOException {
			if (sessionID.isPresent()) {
				System.out.println(threadName + " reading object");
				// Continuously serve the client
				BasicMeasurements object = (BasicMeasurements) inputFromClient.readObject();
				System.out.println(threadName + " read object");

				System.out.println(threadName + " TableName received from client: " + object.getTableName());
				System.out.println(threadName + " first item in the data set: " + object.toString());

				SaveMethod sm = new SaveMethod(conn);
				sm.SaveData(object, sessionID.get());
			} else {
				throw new IllegalArgumentException(
						"Optional<Ineger> sessionID is required. please make sure it pass thru the password check or token test");
			}
		}
	}
}
