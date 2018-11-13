package src.server.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import measurements.BasicMeasurements;
import src.server.database.connection.ConnectionToDB;

/**
 * 
 * This interface has all the default methods that are needed to connected to
 * the DB<br>
 * <br>
 * 
 * <b> TODO</b> Should the save methods also require a preparedStatement as
 * parameter?
 * 
 * @author mathieu
 * @version 08/07/2018
 */

public class SaveMethod {
	private ConnectionToDB conn = null;
	BasicMeasurements bm = null;
	int sessionID;

	public SaveMethod(ConnectionToDB conn) {
		this.conn = conn;
	}

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
	public void SaveData(BasicMeasurements bm, int sessionID) {
		this.bm = bm;
		this.sessionID = sessionID;
		String nameDB = bm.getTableName();
		try {
//			int ID = createUID(nameDB);
			save(nameDB);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * @deprecated
	 */
	public int createUID(String nameTable) throws SQLException {

		String query = "INSERT INTO smtdb." + nameTable + "  values ()";
		int UID = conn.searchIntDB(query).get(0);

		return UID;
	}

	/**
	 * This method takes everything that is needed to create a prepareStatement. So
	 * it starts by making one, and executing it. afterwards it closes the
	 * prepareStatement.
	 * 
	 * @param conn      - Connection to the database
	 * @param nameTable - name of the table the data should go into
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
	public void save(String nameTable) throws SQLException {
		String queryTemp = bm.getData().replaceAll("=", " = '").replaceAll(",", "',").replaceAll("<", "")
				.replaceAll(">", "") + "', `session ID` = '" + sessionID+ '\'';
		String query = "INSERT `smtdb`.`" + nameTable + "` SET " + queryTemp + ";";

		System.out.println(query);

		conn.excuteStamenet(query);

	}

}
