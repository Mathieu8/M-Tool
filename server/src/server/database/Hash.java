package src.server.database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
//import java.util.Base64;
import java.util.Random;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import src.server.server.ServerGUI;

public class Hash {
	CheckPW checkPW;

	protected Hash(CheckPW checkPW) {
		this.checkPW = checkPW;
	}

	protected String[] hashPW(String user, char... pw) throws SQLException {
		ServerGUI.print("in HashPW");
		// get different hash algoritms

		List<String> algoritms = new ArrayList<>();

		algoritms = checkPW.conn.readStringDB("SELECT `password_hash_algorithm` FROM `users` WHERE 1");

		List<String> temp = new ArrayList<>();
		if (algoritms.contains("none")) {
			ServerGUI.print("hash algoritm is \'NULL\'");
			StringBuilder t = new StringBuilder();
			for (char c : pw) {
				t.append(c);
			}
			String s = t.toString();
			temp.add(s);
		}
		if (algoritms.contains("test")) {
			ServerGUI.print("hash algoritm is \'test\'");
			StringBuilder t = new StringBuilder();
			for (char c : pw) {
				t.append(c);
			}
			temp.add(t.toString());

		}

		// for each hash algoritm run it
		String[] array = temp.stream().toArray(String[]::new);

		return array;
	}

	protected long tokenHash(int ID) throws SQLException {
		Long r = new Random().nextLong();
		LocalDateTime dt = LocalDateTime.now();
		String token = Base64.getEncoder().withoutPadding()
				.encodeToString(Long.toString(dt.hashCode() * ID * r).getBytes());
		ServerGUI.print("The Token is " + token);
		

		// save r and zdt on the DB
		String[] fields = { "ID", "User ID", "token", "date created" };
		String[] data = {Long.toString(r), Integer.toString(ID), token, dt.toString() };

		checkPW.conn.createDB("Session ID", fields, data);

		return r;
	}

	private String hashSHA256() {
//		MessageDigest digest = MessageDigest.getInstance("SHA-256");
//		byte[] encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
		return null;
	}

}
