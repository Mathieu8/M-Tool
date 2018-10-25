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
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import measurements.BasicMeasurements;
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
				gui.ta.appendText("MultiThreadServer started at " + new Date() + '\n');

				while (true) {
					// Listen for a new connection request
					Socket socket = serverSocket.accept();

					// Increment clientNo
					clientNo++;

					Platform.runLater(() -> {
						// Display the client number
						gui.ta.appendText("Starting thread for client " + clientNo + " at " + new Date() + '\n');

						// Find the client's host name, and IP address
						InetAddress inetAddress = socket.getInetAddress();
						gui.ta.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
						gui.ta.appendText(
								"Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");
					});

					// Create and start a new thread for the connection
					new Thread(new HandleAClient(socket)).start();
				}
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}).start();
	}

	// Define the thread class for handling new connection
	class HandleAClient implements Runnable {
		private Socket socket; // A connected socket
		ConnectionToDB conn = null;

		/**
		 * Construct a thread
		 * 
		 * @param socket
		 */
		public HandleAClient(Socket socket) {
			this.socket = socket;
		}

		/** Run a thread */
		public void run() {
			conn = ConnectionToDB.createConn();
			try (
					// Create data input and output streams
					DataInputStream input = new DataInputStream(socket.getInputStream());
					DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream())) {
				SaveMethod sm = new SaveMethod(conn);

				while (true) {
					String i = input.readUTF();
					if (checkLogin(i, output)) {
						

//						Thread.sleep(0);
//						while (true) {
						// Continuously serve the client
						BasicMeasurements object = (BasicMeasurements) inputFromClient.readObject();

						Platform.runLater(() -> {
							gui.ta.appendText("TableName received from client: " + object.getTableName() + '\n');
							gui.ta.appendText("first item in the data set: " + object.toString() + '\n');
						});

						sm.SaveData(object);

//						}
					}
				}
			} catch (SocketException e) {
				// System.out.printf("probably someone closed the client, so SocketException at
				// the readObject() line");
				// System.out.println("IOException");

				// e.printStackTrace();
			} catch (EOFException e) {
				// System.out.println("probably someone closed the client, so EOFException at
				// the readObject() line");
				// System.out.println("IOException");

				// e.printStackTrace();
			} catch (IOException e) {

				// System.out.println("IOException");

				// e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}

	private boolean checkLogin(String s, DataOutputStream output) throws IOException {
		Platform.runLater(() -> {
			gui.ta.appendText("checking version; " + Version.VERSION + '\n');
		});
		if (!s.startsWith(Version.VERSION)) {
			output.writeUTF("Wrong token");
			output.flush();
			Platform.runLater(() -> {
				gui.ta.appendText("wrong token " + s + '\n');
			});
			return false;
		} else {
			Platform.runLater(() -> {
				gui.ta.appendText(" Correct token: " + s + '\n');
			});
			output.writeUTF("Correct Token");
			output.flush();
			return true;

		}

	}
}
