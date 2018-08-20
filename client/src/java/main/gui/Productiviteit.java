package src.java.main.gui;

import javafx.stage.Stage;
import src.java.main.gui.elements.SliderTest;

/**
 * 
 * een slider voor productiviteit
 * 
 * Ik heb bij de eventhandlers een controle ingebouwd momenteel om zeker van te
 * zijn dat hij de goede pakt en niet een verkeerde
 * 
 * @TODO doesn't work yet, problem with the Slider class in elements
 * @author mathieu
 */

public class Productiviteit extends SliderTest {
	GUI GUI;
	int ProductiviteitNumber;

	/*
	 * public VBox productiviteit(GUI Gui) { VBox productiviteitBox = new VBox();
	 * Label text = new Label("Productiviteit");
	 * productiviteitBox.getChildren().addAll(text, getSlider(false)); return
	 * productiviteitBox; }
	 * 
	 * public void changed(ObservableValue<? extends Number> prop, Number oldValue,
	 * Number newValue) { ProductiviteitNumber = (int) SliderTest.getValue();
	 * //sliders.sliderNumber(); System.out.println("Productiviteit " +
	 * ProductiviteitNumber); //TODO toSave the data }
	 */
	@Override
	public void action(int n) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
