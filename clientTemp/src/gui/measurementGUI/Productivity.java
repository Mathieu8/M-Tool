package src.gui.measurementGUI;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import measurements.Measurements;
import src.gui.elements.SliderInterface;
/**
 * Here a slider is implemented that will be used to measure how productive you are right now.
 * 
 * @author Mathieu
 * @version 08/23/2018
 */
public class Productivity implements SliderInterface {

	private static Measurements m;

	@Override
	public void actionSlider(int newValue) {
		System.out.println("value of the productivity slider " + newValue);
		m.setProductivity(newValue);

	}
	
	
	/**
	 * @return VBox - a VBox with a question and a slider in it.
	 */
	public VBox prodSlider() {
		Label text = new Label("Hoe nuttig ben je nu bezig?");
		VBox temp =  new VBox();
		temp.getChildren().addAll(text,sliders());
		return temp;
	}
	
	/**
	 * 
	 * @param obj - sets the right reference to save the data that is generated by the used input
	 */
	public static void setMeasurement(Measurements obj) {
		m = obj;
	}

}
