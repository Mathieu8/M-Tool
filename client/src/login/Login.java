package src.login;

import javafx.application.Platform;
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
		System.out.println("in tokenValid()");
//		if (sendToken()) {
//			System.out.println("Token Valid " + '\n');
//			return true;
//		} else {
//			System.out.println("Token inValid " + '\n');
			return false;
//		}
	}

}
