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
	 * @return the name of the DB table the data should go into
	 */
	public abstract String getTableName();

	/**
	 * This method should return all the data in the class in an orderly fashion so
	 * it can be enter straight into DB, in one query
	 * 
	 * @return String array with all the datafields 
	 * 
	 */
	public abstract String[] getFields();

	/**
	 * This method should return all the data in the class in an orderly fashion so
	 * it can be enter straight into DB, in one query.
	 * 
	 * @return String array with all the data in it
	 * @warning the data that this method returns should be in the same order as the
	 *          getFields() method.
	 * 
	 */
	public abstract String[] getData();
	
	public abstract void setDuraction();
}
