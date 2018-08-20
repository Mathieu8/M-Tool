package src.main.java.net.management_tool.client;

import java.time.LocalDateTime;

import gui.measurementGUI.MeasurmentGUI;
import measurements.Measurements;

/**
 * Here start the clientside of the app. <br>
 * <br>
 * right now (08/07/2018) it doesn't do too much. It starts with making a
 * Measurement m and a MeasurementGUI begin. and secondly it passes Measurement
 * m to the GUI and start the GUI
 * 
 * 
 * @author Thijs
 * @author Mathieu
 * @version 08/07/2018
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
		//LocalDateTime dt = LocalDateTime.now();
		//Measurements m = new Measurements(UID, dt);
		MeasurmentGUI begin = new MeasurmentGUI();

		begin.setUID(UID);
		begin.startGUI(args);

	}
}
