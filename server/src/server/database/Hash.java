package src.server.database;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
//import java.util.Base64;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		algoritms = checkPW.conn.readStringDB("SELECT `passwordHashAlgorithm` FROM `users` WHERE 1");
//		String seed = checkPW.conn.readStringDB("SELECT `password_hash_algorithm` FROM `users` WHERE 1");

		List<String> temp = new ArrayList<>();
		if (algoritms.contains("none")) {
			// @Deprecated
			ServerGUI.print("hash algoritm is \'none\'");
			StringBuilder t = new StringBuilder();
			for (char c : pw) {
				t.append(c);
			}
			String s = t.toString();
			temp.add(s);
		}
		if (algoritms.contains("test")) {
			// @Deprecated

			ServerGUI.print("hash algoritm is \'test\'");
			StringBuilder t = new StringBuilder();
			for (char c : pw) {
				t.append(c);
			}
			temp.add(t.toString());
		}
		if (algoritms.contains("SHA256")) {
			ServerGUI.print("hash algoritm is \'SHA256\'");
			temp.add(hashSHA256("", pw));
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
		String[] data = { Long.toString(r), Integer.toString(ID), token, dt.toString() };

		checkPW.conn.createDB("Session ID", fields, data);

		return r;
	}

	private String hashSHA256(String salt, char... pw) {
		String generatedPassword = "";

		try {
			byte[] s = getSalt(salt);
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(s);
			// Get the hash's bytes
			ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(CharBuffer.wrap(pw));
			byte[] bytesPW = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
			Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
			
			Arrays.fill(pw, (char) 0); // clear sensitive data

			byte[] bytes = md.digest(bytesPW);

			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		return generatedPassword;
//		MessageDigest digest = MessageDigest.getInstance("SHA-256");
//		byte[] encodedhash = digest.digest(saltedPW.toString().getBytes(StandardCharsets.UTF_8));
//		return null;
	}

	protected String newHashPW(String user, char[] pw) {

		return null;
	}

	public String newHashPW(String user, char[] pw, String pwHashAlgorithm) {
		switch (pwHashAlgorithm) {
		case "SHA256":
			return hashSHA256("salt", pw);
		case "NULL":
		case "test":
			StringBuilder t = new StringBuilder();
			for (char c : pw) {
				t.append(c);
			}
			return t.toString();
		default:
			break;
		}
		return null;
	}

	private static byte[] getSalt(String s) throws NoSuchAlgorithmException, NoSuchProviderException {
		// Always use a SecureRandom generator
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", s);
		// Create array for salt
		byte[] salt = new byte[16];
		// Get a random salt
		sr.nextBytes(salt);
		// return salt
		return salt;
	}
}
