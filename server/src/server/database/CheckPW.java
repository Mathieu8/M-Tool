package src.server.database;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import src.server.database.connection.ConnectionToDB;
import src.server.server.ServerGUI;

public class CheckPW {
	private ConnectionToDB conn = null;

	public CheckPW(ConnectionToDB conn) {
		this.conn = conn;
	}

	public Optional<Integer> check( char... pw) {
		Optional<Integer> user = null;

				try {
					user = checkToken(pw);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			// TODO Auto-generated catch block

		return user;

	}

	public Optional<Integer> checkToken(char... token) throws SQLException {
		Optional<Integer> sessionID = null;
		LocalDate date = LocalDate.now();
		date = date.minusMonths(1);
		StringBuilder query = new StringBuilder();
		query.append("SELECT `ID` FROM `Session ID` WHERE `Token` = '");
		query.append(Stream.of(token).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append));
		query.append("'");
//		query.append("' AND `last Login` <= '" + date + "'");

		ServerGUI.print(query.toString());
		try {
		sessionID = Optional.of(conn.searchIntDB(query.toString()).get(0));
		} catch( IndexOutOfBoundsException IOOB) {
			System.out.println("no valid token");
		}
		return sessionID;
	}

	public Optional<Integer> checkPassword(char... pw) throws SQLException {
		Optional<Integer> SessionID = null;

		String query = "SELECT * FROM 'users'";
		int UID = conn.searchIntDB(query).get(0);

		return SessionID;
	}
}
