package src.login;

import java.time.LocalDateTime;

public class Test {

	public static void main(String[] args) {
		// Token t = new Token();
		Test t = new Test();
		System.out.println("Local time " + LocalDateTime.now());
		t.deleteFile();
		t.createFile("token test");

		System.out.println("printing Token + meta data " + t.getToken());

	}

	Token t = new Token();

	public void deleteFile() {
		t.deleteFile();
	}

	public void createFile(String s) {
		t.createFile(s);
	}

	public String getToken() {
		return t.getToken();
	}

}
