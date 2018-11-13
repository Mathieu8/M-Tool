package src.toServer;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.sql.Array;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
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
			Socket objectSocket = new Socket(host, 8001);
			objectToServer = new ObjectOutputStream(objectSocket.getOutputStream());
			token = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			if (sendToken(token, input)) {
				token.writeUTF("BasicMeasurements");
				Platform.runLater(() -> {
					GUI.welcome.appendText("sending \"BasicMeasurements\" " + '\n');
				});
				token.flush();
				Platform.runLater(() -> {
					GUI.welcome.appendText("send \"BasicMeasurements\" " + '\n');
				});
				Thread.sleep(500);
				
				Platform.runLater(() -> {
					GUI.welcome.appendText("sending object " + '\n');
				});
				objectToServer.writeObject(o);
				objectToServer.flush();
			}
		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean sendToken(DataOutputStream token, DataInputStream input) {

		try {
			Login login = Login.loginEntry();
			char[] s = login.getToken();

			token.writeUTF("Token");
			token.flush();

			token.writeInt(s.length);
//			CharBuffer.wrap(s).chars().forEach(i -> {
//				try {
//					token.writeChar(i);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			});

			for (char c : s) {
				token.writeChar(c);
//				Platform.runLater(() -> {
//					GUI.welcome.appendText("" + c);
//				});
			}
//			Platform.runLater(() -> {
//				GUI.welcome.appendText("\n");
//			});

			// token.writeUTF(s.toString());
			token.flush();
			s = null;
			login = null;

			String temp = input.readUTF();
			if (temp.equals("Correct Token")) {
//				Platform.runLater(() -> {
//					GUI.welcome.appendText("Correct Token " + temp + '\n');
//				});
				return true;
			} else {
//				Platform.runLater(() -> {
//					GUI.welcome.appendText(" incorrect Token " + temp + '\n');
//				});
				return false;

			}
		} catch (IOException e) {
//			System.out.println("error");
			// e.printStackTrace();
		}
		return false;
	}

	public boolean SendPW(String User, char[] pw) {
		try {
			token = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());

			token.writeUTF(User);
			token.writeInt(pw.length);
			CharBuffer.wrap(pw).chars().forEach(i -> {
				try {
					token.writeChar(i);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			for (char c : pw) {
				token.writeChar(c);
				Platform.runLater(() -> {
					GUI.welcome.appendText("" + c);
				});
			}
			Platform.runLater(() -> {
				GUI.welcome.appendText("\n");
			});

			// token.writeUTF(s.toString());
			token.flush();
			pw = null;

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
//			System.out.println("error");
			// e.printStackTrace();
		}
		return false;
	}

	public static void sendToServer(Object o) {
		ToServer ts = new ToServer();
		ts.sendObject(o);

	}

}
