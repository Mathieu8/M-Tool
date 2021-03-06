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
import java.util.Arrays;
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
				ServerGUI.print("#" + clientNo + " MultiThreadServer started at " + new Date());

				while (true) {

					ServerGUI.print("#" + clientNo + " serverSocket.isClosed() " + serverSocket.isClosed());

					// Listen for a new connection request
					Socket socket = serverSocket.accept();
					Socket objectSocket = objectServerSocket.accept();
					ServerGUI.print("#" + clientNo + " Socket.isClosed() " + socket.isClosed());

					// Increment clientNo
					clientNo++;

					// Display the client number
					ServerGUI.print("#" + clientNo + " Starting thread for client " + clientNo + " at " + new Date());
					ServerGUI.print("#" + clientNo + " Amount of Threads active " + Thread.activeCount());

					// Find the client's host name, and IP address
					InetAddress inetAddress = socket.getInetAddress();
					InetAddress inetAddressO = objectSocket.getInetAddress();
					ServerGUI.print(
							"#" + clientNo + " Client " + clientNo + "'s host name is " + inetAddress.getHostName());
					ServerGUI.print("#" + clientNo + " Client " + clientNo + "'s IP Address is "
							+ inetAddress.getHostAddress());
					ServerGUI.print("#" + clientNo + " Object Client " + clientNo + "'s IP Address is "
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
			ServerOptions options= new ServerOptions();
			ServerGUI.print(threadName + " in run()");
//			 ConnectionToDB.createConn();
//			ServerGUI.print("after ConnectionToDB.createConn()");
			// Create data input and output streams
			try (DataInputStream input = new DataInputStream(socket.getInputStream());
					DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					ObjectInputStream inputFromClient = new ObjectInputStream(objectSocket.getInputStream());) {
				socket.setSoTimeout(100000);

				Optional<Long> sessionID = Optional.empty();

				outer: while (true) {
					ServerGUI.print("\n" + threadName + " running while again \n" + new Date());

					ServerGUI.print(threadName + " input.available(): " + input.available());
					ServerGUI.print(threadName + " waiting for option");
					String option = input.readUTF();
					ServerGUI.print(threadName + " option is " + option);
					switch (option) {
					case "Token":
						ServerGUI.print(threadName + " in token");
						sessionID = options.checkToken(input, output);
						break;
					case "Password":
						ServerGUI.print(threadName + " in password");
						sessionID = options.checkPW(input, output, socket.getInetAddress().getHostAddress());
						break;
					case "BasicMeasurements":
						ServerGUI.print(threadName + " in measurements");
						options.saveMeasurement(sessionID, inputFromClient);
//						break outer;
						break;
					case "sendNewAccount":
						ServerGUI.print(threadName + " in sendNewAccount");
						sessionID = options.sendNewAccount(input, output);
//						break outer;
						break;
					case "ChangePW":
						options.changePW(input,output,sessionID.get());
						
						break;
					case "Close":
					default:
						ServerGUI.print(threadName + " Close");
						break outer;
					}

				}
			} catch (ClassNotFoundException | IOException e) {
				ServerGUI.print("Error " + e);
				e.printStackTrace();

			} finally {
				ServerGUI.print(threadName + " number of active threads: " + Thread.activeCount());

				ServerGUI.print(threadName + " interrupting thread");
				clientNo--;
				Thread.interrupted();
			}
		}

		

		
	}
}
