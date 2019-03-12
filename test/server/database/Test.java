package server.database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.apache.commons.codec.digest.DigestUtils;

public class Test {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
		String passwordToHash = "password";
		byte[] salt = getSalt("SUN");

		String securePassword = getSecurePassword(passwordToHash, salt);
		System.out.println(securePassword); // Prints 83ee5baeea20b6c21635e4ea67847f66

		String regeneratedPassowrdToVerify = getSecurePassword(passwordToHash, salt);
		System.out.println(regeneratedPassowrdToVerify); // Prints 83ee5baeea20b6c21635e4ea67847f66
	}

	private static String getSecurePassword(String passwordToHash, byte[] salt) {
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(salt);
			// Get the hash's bytes
			byte[] bytes = md.digest(passwordToHash.getBytes());
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	// Add salt
	private static byte[] getSalt(String s) throws NoSuchAlgorithmException, NoSuchProviderException {
		// Always use a SecureRandom generator
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", s);
		// Create array for salt
		byte[] salt = new byte[16];
		// Get a random salt
		sr.nextBytes(salt);
		System.out.print("salt is ");
		for(byte c:salt) {
			System.out.print((char)c);
		}
		System.out.println();
		// return salt
		return salt;
	}
//		
}