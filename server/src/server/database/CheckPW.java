package src.server.database;

import java.nio.CharBuffer;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import src.server.database.connection.ConnectionToDB;
import src.server.database.connection.ShouldBeOnlyOneException;
import src.server.server.ServerGUI;

public class CheckPW {
	private Hash hash = new Hash(this);
	protected ConnectionToDB conn = null;

	public CheckPW(ConnectionToDB conn) {
		this.conn = conn;
	}

	public Optional<Long> check(char... pw) {
		Optional<Long> user = null;
		try {
			user = checkToken(pw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated catch block

		return user;
	}

	public Optional<Long> checkToken(char... token) throws SQLException {
		Optional<Long> sessionID = Optional.empty();
		LocalDate date = LocalDate.now();
		date = date.minusMonths(1);
		StringBuilder query = new StringBuilder();
		query.append("SELECT `ID` FROM `Session ID` WHERE `Token` = '");
		query.append(token);
//				Stream.of(token).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
//		query.append("'");
		query.append("' AND `last Login` <= '" + date + "'");

		ServerGUI.print(query.toString());
		List<Long> temp = null;
		try {
			temp = conn.readLongDB(query.toString());

		} catch (IndexOutOfBoundsException IOOB) {
			ServerGUI.print("no valid token");
//			ServerGUI.print("Optional sessionID is " + sessionID.isPresent());
		}

		if (temp.size() == 1) {
			return Optional.of(temp.get(0));
		} else {
			throw new ShouldBeOnlyOneException("Too much data returned");
		}
	}

	public Optional<Long> checkPassword(String user, char... pw) throws SQLException {
		Optional<Long> sessionID = null;
		ServerGUI.print("in chechkPassword()");
		String[] hashPW = hash.hashPW(user, pw);

		// check if username is allready taken

		for (int i = 0; i < hashPW.length; i++) {
			StringBuilder queryBuilder = new StringBuilder(
					"SELECT `user ID` FROM `users` WHERE `Email` ='" + user + "' AND `password`='" + hashPW[i] + "'");
			ServerGUI.print(queryBuilder.toString());
			String query = queryBuilder.toString();

			List<Integer> UID = conn.readIntDB(query);

			if (UID.size() == 1) {
				ServerGUI.print("UID is " + UID.get(0));
				sessionID = Optional.of(hash.tokenHash(UID.get(0)));
				if (sessionID.get() == 1) {
					return sessionID;
				}
			}
		}

		return sessionID;
	}

	public String getToken(long sessionID) throws SQLException {
		String queryBuilder = "SELECT `token` FROM `Session ID` WHERE `ID` ='" + sessionID + "'";
		ServerGUI.print(queryBuilder);
		String query = queryBuilder;

		List<String> token = conn.readStringDB(query);
		if (token.size() == 1) {
			return token.get(0);
		} else {
			throw new ShouldBeOnlyOneException("Too much data returned");
		}
	}

	public String newUser(String user, char[] pw) throws SQLException {
		Optional<Long> SessionID = null;
		ServerGUI.print("in newUser()");
		String queryUsername = "SELECT `user ID` FROM `smtdb`.`users` WHERE `Email` ='" + user + "'";
		ServerGUI.print(queryUsername);
		ServerGUI.print("ResultSet.size() " + conn.readIntDB(queryUsername).size());

		if (conn.readIntDB(queryUsername).size() == 0) {

//		var hashPW = hash.hashPW(user, pw);

			String[] fields = { "email", "password", "passwordSalt", "passwordHashAlgorithm" };
			String pwHashAlgorithm = "test";
			String[] data = { user, hash.newHashPW(user, pw, pwHashAlgorithm), "test", pwHashAlgorithm };
			conn.createDB("users", fields, data);
			return checkPassword(user, pw).get().toString();
		} else {
			return "username allready taken";
		}
	}
}