package src.main.java.net.management_tool.client;

import java.time.LocalDateTime;

import measurements.Measurements;
import src.java.main.gui.measurementGUI.MeasurmentGUI;

/**
 * Here start the clientside of the app. <br>
 * <br>
 * right now (08/23/2018) it doesn't do too much. It starts with making a
 * MeasurementGUI begin. and secondly it starts the GUI
 * 
 * @author Thijs
 * @author Mathieu
 * @version 08/23/2018
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
		int UID = 1;
		// LocalDateTime dt = LocalDateTime.now();
		// Measurements m = new Measurements(UID, dt);
		MeasurmentGUI begin = new MeasurmentGUI();

		begin.setUID(UID);
		begin.startGUI(args);

	}
}
