package src.server.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.sql.Statement;

public class ConnectionToDB {
	private Connection conn = null;
	private Long time = System.nanoTime();
//	private Statement stmt;

	/**
	 * This method creates a Connection to SMTDB. After that all methods within the
	 * SaveMethod class can use it
	 * 
	 * @return
	 * 
	 * 
	 * @throws SQLException        - if a database access error occurs or the url is
	 *                             null
	 * @throws SQLTimeoutException - when the driver has determined that the timeout
	 *                             value specified by the setLoginTimeout method has
	 *                             been exceeded and has at least tried to cancel
	 *                             the current database connection attempt
	 */
	public static ConnectionToDB createConn() {
		ConnectionToDB connToDB = new ConnectionToDB();
		String dbName = "jdbc:mysql:";
		String server = "//ict-stadsbrug.nl?";
		String user = "user=smt&";
		String pw = "password=12345";

		try {
			connToDB.conn = DriverManager.getConnection(dbName + server + user + pw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connToDB;
	}

	private boolean checkConn() throws SQLException {
		if (conn != null ? true : false) {
			if (time + 60_000_000 < System.nanoTime()) {
				return true;
			}
			else {
				closeConn();
			}

		}

		return false;
	}

	protected void excuteStamenet(String query) throws SQLException {
		if (checkConn()) {
			PreparedStatement st = conn.prepareStatement(query);
			st.executeUpdate();
			st.close();
		}
	}

	protected List<String> searchStringDB(String query) throws SQLException {
		List<String> list = new ArrayList<String>();
		if (checkConn()) {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			int i = 1;
			if (rs.next()) {
				list.add(rs.getString(i++));
			}
		}
		return list;
	}
	
	protected List<Integer> searchIntDB(String query) throws SQLException {
		List<Integer> list = new ArrayList<Integer>();
		if (checkConn()) {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			int i = 1;
			if (rs.next()) {
				list.add(rs.getInt( i++));
			}
		}
		return list;
	}

	/**
	 * does what it's name suggest, closes the connection to the database
	 * 
	 * @param conn
	 * @return Connection that is closed
	 * @throws SQLException - SQLException if a database access error occurs
	 * 
	 */
	protected void closeConn() throws SQLException {
		if (conn != null) {
			conn.close();
			time = null;
		}
	}

}
