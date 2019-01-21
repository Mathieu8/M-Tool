package src.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import src.gui.GUI;
import src.toServer.ToServer;

public class Login extends ToServer {
	private class User {
		public User(String userName, char[] pw) {
			// TODO Auto-generated constructor stub
			this.userName = userName;
			this.pw = pw;
		}

		private String userName;
		private char[] pw;

	}

	private static Login l = null;
	User user;
	private boolean isLogin = false;
	private Token token = new Token();

	public char[] getToken() {
		return token.getToken();
	}

	private Login() {
	}

	public static Login loginEntry() {
		if (l == null) {
			l = new Login();
		}
		return l;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public boolean tokenValid() {
		boolean temp = false;
		System.out.println("in tokenValid()");
		try (Socket socket = new Socket(host, 8002);
				DataOutputStream token = new DataOutputStream(socket.getOutputStream());
				DataInputStream input = new DataInputStream(socket.getInputStream())) {

			if (sendToken(token, input)) {
				System.out.println("Token Valid ");
				temp = true;
			} else {
				System.out.println("Token inValid ");
				temp = false;
			}
			
			token.writeUTF("Close");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
}
