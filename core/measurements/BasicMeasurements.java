package measurements;

/**
 * All Measurements should implants this interface. As it does makes it possible
 * for the SaveMethod class to correctly fill in the data to the DB
 * 
 * @author Mathieu
 * @version 08/06/2018
 * 
 */
public interface BasicMeasurements extends java.io.Serializable {

	/**
	 * 
	 * @TODO TODO may go away
	 * @return the name of the DB table the data should go into
	 * @deprecated
	 */
	public abstract String getTableName();

	/**
	 * This method should return all the data in the class in an orderly fashion so
	 * it can be enter straight into DB, in one query
	 * 
	 * @TODO TODO may go away
	 * @return String array with all the datafields 
	 * @deprecated
	 * 
	 */
	public abstract String[] getFields();

	/**
	 * This method should return all the data in the class in an orderly fashion so
	 * it can be enter straight into DB, in one query.
	 * 
	 * 
	 * <br><br><b> WARNING</b> the data that this method returns should be in the same order as the
	 *          getFields() method.
	 * @return String array with all the data in it
	 * 
	 */
	public abstract String[] getData();
	
	//public abstract String[] getDataa();
	public abstract void setDuraction();
}
