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
import src.login.Token;
import version.Version;

/**
 * @author Mathieu
 * @version 09/27/2018
 */
public class ToServer {
	protected static String host = "localhost";
//	private Socket socket;
//	private ObjectOutputStream objectToServer = null;
//	private DataOutputStream token = null;
//	private DataInputStream input = null;

	public ToServer() {
		
	}

	/**
	 * 
	 * @param o - object that will be send to the server
	 */
	private void sendObject( Object o) {
		GUI.print("");
		GUI.print("in sendObject()");
		GUI.print("");

		try(Socket socket = new Socket(host, 8002);
				Socket objectSocket = new Socket(host, 8001);
				ObjectOutputStream objectToServer = new ObjectOutputStream(objectSocket.getOutputStream());
				DataOutputStream token = new DataOutputStream(socket.getOutputStream());
				DataInputStream input = new DataInputStream(socket.getInputStream())){
		
			boolean test = sendToken(token, input);
//			GUI.print("sendToken(token, input) = " + test);
			if (test) {

				GUI.print("sending option = \"BasicMeasurements\" ");
				token.writeUTF("BasicMeasurements");
				token.flush();
				GUI.print("sended \"BasicMeasurements\" ");
				Thread.sleep(100);

				GUI.print("sending object ");
				GUI.print(o.toString());
				objectToServer.writeObject(o);
				objectToServer.flush();
				
				Thread.sleep(100);
				GUI.print("sending option = \"Close\" ");
				token.writeUTF("Close");
				token.flush();
				GUI.print("sended \"Close\" ");
			}
		} catch (IOException | InterruptedException e) {
			System.out.println("error");
			e.printStackTrace();
//		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}

	protected boolean sendToken() {
		try(Socket socket = new Socket(host, 8002);
				Socket objectSocket = new Socket(host, 8001);
				ObjectOutputStream objectToServer = new ObjectOutputStream(objectSocket.getOutputStream());
				DataOutputStream token = new DataOutputStream(socket.getOutputStream());
				DataInputStream input = new DataInputStream(socket.getInputStream())){
			
			return sendToken(token,input);
		} catch (IOException | InterruptedException e) {
			System.out.println("error");
			e.printStackTrace();
//		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return false;
	}
	
	private boolean sendToken(DataOutputStream token, DataInputStream input) throws IOException, InterruptedException {

		Login login = Login.loginEntry();
		Token t = new Token();
		char[] tkn = login.getToken();
		String[] meta = t.getMetadata();

		System.out.println("sending option = \"Token\"");
		token.writeUTF("Token");
		token.flush();
		System.out.println("sended option = \"Token\"");

		Thread.sleep(10);
		System.out.println("sending token");
		token.writeInt(tkn.length);

		for (char c : tkn) {
			token.writeChar(c);
		}

		token.flush();
		
		System.out.println("sended token");
		Thread.sleep(10);
		token.writeUTF(meta[1]);
		token.flush();
		
		tkn = null;
		login = null;

		System.out.println("waiting for conformation of the token");

		String temp = input.readUTF();
		if (temp.equals("Correct Token")) {
			System.out.println("temp = " + temp);
			return true;
		} else {
			System.out.println("temp = " + temp);
			return false;
		}
	}
	
	

	public boolean SendPW(String User, char[] pw) {
		try (Socket socket = new Socket(host, 8002);
				DataOutputStream token = new DataOutputStream(socket.getOutputStream());
				DataInputStream input = new DataInputStream(socket.getInputStream())){
			

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
			GUI.print("\n");

			// token.writeUTF(s.toString());
			token.flush();
			pw = null;

			String temp = input.readUTF();
			if (temp.equals("Correct Token")) {
				GUI.print("Correct Token " + temp + '\n');
				return true;
			} else {
				GUI.print(" incorrect Token " + temp + '\n');
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
