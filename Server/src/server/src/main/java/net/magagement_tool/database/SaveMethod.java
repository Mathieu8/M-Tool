package src.server.src.main.java.net.magagement_tool.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import measurements.BasicMeasurements;

/**
 * 
 * This interface has all the default methods that are needed to connected to
 * the DB
 * 
 * @TODO TODO Should the save methods also require a preparedStatement as
 *       parameter?
 * 
 * @author mathieu
 * @version 08/07/2018
 */

public class SaveMethod {
	private Connection conn;

	/**
	 * This constructor takes an object that implements the interface
	 * BasicMeasurements. First it takes the data from object with the methods from
	 * the interface to be able to use them. <br />
	 * <br />
	 * 
	 * After that it does a couple of things within the try block. In order: making
	 * connection to the database, insert the data and than close the connection to
	 * the database
	 * 
	 * @param BasicMeasurements
	 */
	public SaveMethod(BasicMeasurements bm) {
		String nameDB = bm.getTableName();
		String[] dataFields = bm.getFields();
		String[] data = bm.getData();
		try {
			createConn();
			int ID = createUID(nameDB);
			save(nameDB, dataFields, data, ID);
			closeConn();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a Connection to SMTDB. After that all methods within the
	 * SaveMethod class can use it
	 * 
	 * 
	 * @throws SQLException        - if a database access error occurs or the url is
	 *                             null
	 * @throws SQLTimeoutException - when the driver has determined that the timeout
	 *                             value specified by the setLoginTimeout method has
	 *                             been exceeded and has at least tried to cancel
	 *                             the current database connection attempt
	 */
	public void createConn() throws SQLException {
		String dbName = "jdbc:mysql:";
		String server = "//ict-stadsbrug.nl?";
		String user = "user=smt&";
		String pw = "password=12345";
		conn = DriverManager.getConnection(dbName + server + user + pw);

	}

	/**
	 * Creates a new row in the nameDB, with all the values set to NULL, later we
	 * can with the UID change the datafields to fill in the data.
	 * 
	 * @param conn      - connection to the database
	 * @param nameTable - String that points this method to the right table where to
	 *                  create a new row
	 * @return UID - int is the number primary key to the new row in the database,
	 *         later it can be used to update that row and add new data to it
	 * @throws SQLException
	 */
	public int createUID(String nameTable) throws SQLException {

		String query = "INSERT INTO smtdb." + nameTable + "  values ()";

		Statement st = conn.createStatement();
		st.execute(query, Statement.RETURN_GENERATED_KEYS);
		int UID = -1;

		ResultSet rs = st.getGeneratedKeys();

		System.out.println(rs.getRow());
		if (rs.next()) {
			UID = rs.getInt(1);
		} else {
			// throw an exception from here
			System.out.println("somthing went wrong");
		}

		return UID;
	}

	/**
	 * changes in the nameDB the timeStamp from NULL to the given value
	 * 
	 * @param conn
	 * @param nameDB
	 * @param UID
	 * @param dateTime
	 * @throws SQLException
	 * @TODO TODO should we also add the name of the datafield as a parameter?
	 * @deprecated Mathieu: I believe we should enter all via the same method, the
	 *             one that takes String arrays
	 */
	public void save(Connection conn, String nameDB, int UID, Timestamp dateTime) throws SQLException {

		String query = "UPDATE `smtdb`.`" + nameDB + "` SET `dateTime` = ' " + dateTime
				+ "' WHERE `meetresultaat`.`ID` = " + UID + ";";
		System.out.println(query);

		PreparedStatement st = conn.prepareStatement(query);

		@SuppressWarnings("unused")
		int insertedRecordsCount = st.executeUpdate();
		int t = st.executeBatch().length;
		System.out.println(t);
		st.close();

	}

	/**
	 * This method takes everything that is needed to create a prepareStatement. So
	 * it starts by making one, and executing it. afterwards it closes the
	 * prepareStatement.
	 * 
	 * @param conn      - Connection to the database
	 * @param nameTable - name of the table the data should go into
	 * @param UID       - int, this should be equal to the int that is returned from
	 *                  createUID()
	 * @param data      - a String Array with all the data in it
	 * @param nameField - a String Array with all the names of the fields the data
	 *                  should go into
	 * @throws SQLException        - if a database access error occurs; this method
	 *                             is called on a closed PreparedStatement or the
	 *                             SQL statement returns a ResultSet object
	 * @throws SQLTimeoutException - when the driver has determined that the timeout
	 *                             value that was specified by the setQueryTimeout
	 *                             method has been exceeded and has at least
	 *                             attempted to cancel the currently running
	 *                             Statement
	 * 
	 */
	public void save(String nameTable, String[] nameField, String[] data, int UID) throws SQLException {
		StringBuilder queryTemp = new StringBuilder();
		for (int i = 0; i < nameField.length; i++) {
			queryTemp.append(nameField[i] + " = '" + data[i] + "'");
			if (i < nameField.length - 1) {
				queryTemp.append(',');
			}
		}

		String query = "UPDATE `smtdb`.`" + nameTable + "` SET " + queryTemp + " WHERE  ID = " + UID + ";";
		System.out.println(query);

		PreparedStatement st = conn.prepareStatement(query);

		@SuppressWarnings("unused")
		int insertedRecordsCount = st.executeUpdate();
		st.close();

	}

	/**
	 * does what it's name suggest, closes the connection to the database
	 * 
	 * @param conn
	 * @return Connection that is closed
	 * @throws SQLException - SQLException if a database access error occurs
	 * 
	 */
	public Connection closeConn() throws SQLException {
		conn.close();
		return conn;

	}

}
