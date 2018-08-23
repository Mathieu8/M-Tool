package src.java.main.gui.measurementGUI;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import measurements.Measurements;
import src.java.main.gui.elements.SliderInterface;
/**
 * 
 * @author Mathieu
 * @version 08/23/2018
 */
public class Productivity implements SliderInterface {

	private static Measurements m;

	@Override
	public void action(int newValue) {
		System.out.println("value of the productivity slider " + newValue);
		m.setProductivity(newValue);
		// TODO Auto-generated method stub

	}
	
	public VBox prodSlider() {
		Label text = new Label("Hoe nuttig ben je nu bezig?");
		VBox temp =  new VBox();
		temp.getChildren().addAll(text,Sliders());
		return temp;
	}
	
	public static void setMeasurement(Measurements obj) {
		m = obj;
	}

}
