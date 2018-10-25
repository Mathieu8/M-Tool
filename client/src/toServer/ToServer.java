package src.toServer;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.ZipOutputStream;

import javafx.application.Platform;
import src.gui.GUI;
import src.login.Login;
import version.Version;

/**
 * @author Mathieu
 * @version 09/27/2018
 */
public class ToServer {
	static String host = "localhost";
	private Socket socket;
	private ObjectOutputStream objectToServer = null;
	private DataOutputStream token = null;
	private DataInputStream input = null;

	public ToServer() {
		try {
			socket = new Socket(host, 8000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param o - object that will be send to the server
	 */
	private void sendObject(Object o) {

		try {
			objectToServer = new ObjectOutputStream(socket.getOutputStream());
			if (sendToken()) {
				Platform.runLater(() -> {
					GUI.welcome.appendText("Sleeping ");
				});
				Platform.runLater(() -> {
					GUI.welcome.appendText("waking " + '\n');
				});

				Platform.runLater(() -> {
					GUI.welcome.appendText("sending object " + '\n');
				});
				objectToServer.writeObject(o);
				objectToServer.flush();
			}
		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		}

	}

	private boolean sendToken() {

		try {
				token = new DataOutputStream(socket.getOutputStream());
				input = new DataInputStream(socket.getInputStream());
			Login login = new Login();
			final String s = login.getToken();
			System.out.print(s);

			Platform.runLater(() -> {
				GUI.welcome.appendText("testing Token " + s + '\n');
			});

			token.writeUTF(s);
			token.flush();

			// s = null;
			login = null;

			String temp = input.readUTF();
			if (temp.equals("Correct Token")) {
				Platform.runLater(() -> {
					GUI.welcome.appendText("Correct Token " + temp + '\n');
				});
				return true;
			} else {
				Platform.runLater(() -> {
					GUI.welcome.appendText(" incorrect Token " + temp + '\n');
				});
				return false;

			}
		} catch (IOException e) {
			System.out.println("error");
			// e.printStackTrace();
		}
		return false;
	}
	
	public static void sendToServer(Object o) {
		ToServer ts = new ToServer();
		ts.sendObject(o);
		
	}

}
