package src.java.main.gui;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.java.main.gui.elements.SliderTest;

/**
 * 
 * 
 * een slider voor Intensiteit van de emotie
 * 
 * Ik heb bij de eventhandlers een controle ingebouwd momenteel om zeker van te
 * zijn dat hij de goede pakt en niet een verkeerde
 * 
 * @TODO doesn't work yet, problem with the Slider class in elements
 * @author mathieu
 */
public class Intensiteit extends SliderTest {

	boolean enable = true;
	int intensiteitNumber;
	VBox intensiteitBox = new VBox();

	Slider slider;

	public VBox intensiteit(GUI Gui) {
		Label text = new Label("Intensiteit van emotie");
		intensiteitBox.getChildren().addAll(text);
		setEnable();
		return intensiteitBox;
	}

	//
	public void changed(ObservableValue<? extends Number> prop, Number oldValue, Number newValue) {
		intensiteitNumber = (int) slider.getValue();
		System.out.println("intensiteit " + intensiteitNumber);
		// TODO toSave the data
	}

	/*
	 * Hier wordt als er een emotie is geselecteerd een, wordt de slider vernieuwed
	 * daarnaast bij de eerste keer dat een emotie wordt geselecteerd dan wordt de
	 * slider ook interactable gemaakt
	 */
	public void setEnable() {
		System.out.println("test 1");
		enable = false;
		System.out.println("test 2");

		intensiteitBox.getChildren().remove(slider);
		System.out.println("test 3");
		slider = (Slider) new Sliders();
		intensiteitBox.getChildren().addAll(slider);
		System.out.println("test 4");

	}

	@Override
	public void action(int n) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}
