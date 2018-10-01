package src.toServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Mathieu
 * @version 09/27/2018
 */
public class ToServer {
	String host = "localhost";

	/**
	 * @param o - object that will be send to the server
	 */
	public void sendToServer(Object o) {
		try (ObjectOutputStream toServer = new ObjectOutputStream(new Socket(host, 8000).getOutputStream())) {
			toServer.writeObject(o);
		} catch (IOException e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

}
