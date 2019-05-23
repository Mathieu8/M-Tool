package src.toServer;

import java.time.LocalDateTime;

import src.login.Token;

public class Test {

	public static void main(String[] args) {
		System.out.println("Local time " + LocalDateTime.now());
		new src.login.Test().deleteFile();
		new src.login.Test().createFile("token test token");
		Token t = new Token();
		System.out.println("printing Token + meta data " + new src.login.Test().getToken());

	}

}