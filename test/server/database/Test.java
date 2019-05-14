//package server.database;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//
//public class Test {
//
////	public static void main(String[] args) {
////		char[] pw = { 't', 'e', 's', 't' };
////		String username = "test";
////
////	}
//
//	public static void main(String[] args) throws NoSuchAlgorithmException {
//		char[] passwordToHash = { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd' };
//		System.out.println(passwordToHash.toString());
//		String pw = "password";
//
//		String securePassword = get_SHA_1_SecurePassword(pw, salt);
//		System.out.println(securePassword);
//
//		securePassword = get_SHA_256_SecurePassword(pw, salt);
//		System.out.println(securePassword);
//
//		securePassword = get_SHA_384_SecurePassword(pw, salt);
//		System.out.println(securePassword);
//
//		securePassword = get_SHA_512_SecurePassword(pw, salt);
//		System.out.println(securePassword);
//	}
//
//	private static String get_SHA_1_SecurePassword(String passwordToHash, byte[] salt) {
//		String generatedPassword = null;
//		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-1");
//			md.update(salt);
//			byte[] bytes = md.digest(passwordToHash.getBytes());
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < bytes.length; i++) {
//				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//			}
//			generatedPassword = sb.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return generatedPassword;
//	}
//
//	private static String get_SHA_256_SecurePassword(String passwordToHash, byte[] salt) {
////        Use MessageDigest md = MessageDigest.getInstance("SHA-256");
//		String generatedPassword = null;
//		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-256");
//			md.update(salt);
//			byte[] bytes = md.digest(passwordToHash.getBytes());
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < bytes.length; i++) {
//				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//			}
//			generatedPassword = sb.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return generatedPassword;
//	}
//
//	private static String get_SHA_384_SecurePassword(String passwordToHash, byte[] salt) {
////        Use MessageDigest md = MessageDigest.getInstance("SHA-384");
//		String generatedPassword = null;
//		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-384");
//			md.update(salt);
//			byte[] bytes = md.digest(passwordToHash.getBytes());
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < bytes.length; i++) {
//				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//			}
//			generatedPassword = sb.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return generatedPassword;
//	}
//
//	private static String get_SHA_512_SecurePassword(String passwordToHash, byte[] salt) {
////        Use MessageDigest md = MessageDigest.getInstance("SHA-512");
//		String generatedPassword = null;
//		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-512");
//			md.update(salt);
//			byte[] bytes = md.digest(passwordToHash.getBytes());
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < bytes.length; i++) {
//				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//			}
//			generatedPassword = sb.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return generatedPassword;
//	}
//
//	// Add salt
//	private static byte[] getSalt(char[] preSalt) throws NoSuchAlgorithmException {
//		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//		byte[] salt;
//		if (preSalt.length < 16) {
//			salt = new byte[16];
//			for(char c:preSalt) {
//				
//			}
//		} else {
//			salt = new byte[16];
//			
//		}
//		sr.nextBytes(salt);
//		System.out.println("salt is ");
//		for (byte b : salt) {
//			System.out.print(b);
//		}
//		System.out.println();
//		return salt;
//	}
//}
