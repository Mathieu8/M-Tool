package src.java.main.client.toServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Mathieu
 * @version 08/07/2018
 */
public class ToServer {
	String host = "localhost";

	/**
	 * @param o - object that will be send to the server
	 */
	public void sendToServer(Object o) {
		Socket socket;
		try {
			socket = new Socket(host, 8000);
			ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
			toServer.writeObject(o);
		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

}
