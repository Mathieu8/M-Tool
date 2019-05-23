package src.login;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import src.gui.GUI;
import src.toServer.ToServer;

public class Login extends ToServer {
	private static Login l = null;
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

		if (sendToken()) {
			System.out.println("Token valid ");
			return true;
		} else {
			System.out.println("Token invalid ");
			return false;
		}
	}
}
