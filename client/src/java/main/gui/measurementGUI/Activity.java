package src.java.main.gui.measurementGUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import measurements.Measurements;
import src.java.main.gui.elements.Dropdown;

/**
 * 
 * 
 * Hier wordt de een dropDownmenutje gemaakt voor de activiteiten
 * 
 * Ik heb bij de eventhandlers een controle ingebouwd momenteel om zeker van te
 * zijn dat hij de goede pakt en niet een verkeerde
 * 
 * @author mathieu
 * @version 08/23/2018
 */

public class Activity implements Dropdown {
	static Measurements m;
	String[] activiteiten = { "werk", "ontspanning", "reizen", "sociaal", "sporten" };

	
	public VBox activiteitVBox() {
		VBox temp = new VBox();
		Label text = new Label("wat ben je nu aan het doen?");
		temp.getChildren().addAll(text, dropDownVBox(activiteiten));
		return temp;
		
	}

	public static void setM(Measurements obj) {
		m = obj;
	}

	@Override
	public void actionComboBox(String oldValue, String newValue) {
		m.setActivity(newValue);
		
	}

}
