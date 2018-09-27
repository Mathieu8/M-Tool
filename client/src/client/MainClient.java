package src.client;

import src.gui.GUI;

/**
 * Here start the clientside of the app. <br>
 * <br>
 * right now (09/27/2018) it doesn't do too much. It starts with making a
 * MeasurementGUI begin. and secondly it starts the GUI
 * 
 * @TODO TODO load all the user data
 * 
 * @author Thijs
 * @author Mathieu
 * @version 09/27/2018
 * 
 *
 */
public class MainClient {

	/**
	 * see the class description
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GUI begin = new GUI();
		begin.startGUI(args);
	}
	
	
}
