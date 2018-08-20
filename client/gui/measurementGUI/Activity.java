package gui.measurementGUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import measurements.Measurements;
import src.java.main.gui.GUI;

/**
 * 
 * 
 * Hier wordt de een dropDownmenutje gemaakt voor de activiteiten
 * 
 * Ik heb bij de eventhandlers een controle ingebouwd momenteel om zeker van te
 * zijn dat hij de goede pakt en niet een verkeerde
 * 
 * @author mathieu
 */

public class Activity {
	static Measurements m;
	int counter =0;
	// lijst van activiteiten:
	String[] activiteiten = { "werk", "ontspanning", "reizen", "sociaal", "sporten" };

	public VBox activiteitVBox() {
		VBox activiteitVBox = new VBox();
		Label text = new Label("wat ben je nu aan het doen?");
		ComboBox<String> dropdownBox = new ComboBox<>();
		for (int i = 0; i < activiteiten.length; i++) {
			dropdownBox.getItems().add(activiteiten[i]);
		}

		/*
		 * hier onder staat de code die wat doet als er een verandering in het
		 * geslecteerd item van het dropdown menutje is
		 * 
		 * ObservableValue ov wordt niet gebruikt, maar is wel verplicht om er in te
		 * hebben
		 * 
		 */
		dropdownBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				System.out.println("activiteit was " + t);
				System.out.println("activiteit is " + t1);
				counter++;
				// TODO toSave the data
			}
		});
		activiteitVBox.getChildren().addAll(text, dropdownBox);
		return activiteitVBox;
	}

	public static void setM(Measurements obj) {
		m = obj;
	}

}
