package src.server.server;

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

/**
 * start of the server side of the application. It does 2 things, beside
 * regulating the server. It also makes a basic GUI that shows that all the
 * connection to the server apart from all the errors and other println's.
 * 
 * 
 * @author Mathieu
 * @version 08/07/2018
 *
 */
public class Server extends Application {
	// Text area for displaying contents
	private TextArea ta = new TextArea();

	// Number a client
	private int clientNo = 0;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		Button btn = new Button("exit");
		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(1);
				event.consume();
			}
		};
		btn.setOnAction(buttonHandler);
		// Create a scene and place it in the stage
		
		GridPane root = new GridPane();
		VBox vb = new VBox();
		vb.getChildren().addAll(btn, ta);
		root.getChildren().addAll(vb);
		
		Scene scene = new Scene(root);// , 450, 200);
		
		primaryStage.setTitle("MultiThreadServer"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage


		new Thread(() -> {
			try {
				// Create a server socket
				ServerSocket serverSocket = new ServerSocket(8000);
				ta.appendText("MultiThreadServer started at " + new Date() + '\n');

				while (true) {
					// Listen for a new connection request
					Socket socket = serverSocket.accept();

					// Increment clientNo
					clientNo++;

					Platform.runLater(() -> {
						// Display the client number
						ta.appendText("Starting thread for client " + clientNo + " at " + new Date() + '\n');

						// Find the client's host name, and IP address
						InetAddress inetAddress = socket.getInetAddress();
						ta.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
						ta.appendText("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");
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
			try {
				// Create data input and output streams
				ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());

				// Continuously serve the client
				while (true) {
					BasicMeasurements object = (BasicMeasurements) inputFromClient.readObject();

					Platform.runLater(() -> {
						ta.appendText("TableName received from client: " + object.getTableName() + '\n');
						ta.appendText("first item in the data set: " + object.getData()[0] + '\n');
					});

					SaveMethod sm = new SaveMethod(object);
				}
			} catch (SocketException e) {
				System.out.printf("probably someone closed the client, so SocketException at the readObject() line");
				// e.printStackTrace();
			}catch(EOFException e) {
				System.out.println("probably someone closed the client, so EOFException at the readObject() line");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
