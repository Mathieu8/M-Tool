package src.java.main.gui.measurementGUI;

import java.util.Arrays;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import measurements.Measurements;

/**
 * 
 * 
 * 
 * @author mathieu
 * @version 08/23/2018
 *
 */

public class Smileys implements src.java.main.gui.elements.Buttons, src.java.main.gui.elements.SliderInterface {
	static Measurements m;
	String[] text = { "\uD83D\uDE00", "\uD83D\uDE21", "\uD83D\uDE31", "\uD83D\uDE2D", "\uD83D\uDE33" }; // de smileys
																										// zijn in
																										// unicode
	String[] tooltip = { "blij", "boos", "bang", "bedroefd", "beschaamd" };
	boolean disable = true;
	VBox SIBox = new VBox();
	VBox sliderBox = new VBox();

	public void actionButton(String emotie) {
		m.setEmotion(tooltip[Arrays.asList(text).indexOf(emotie)]);
		disable = false;
		Platform.runLater(() -> {
			sliderBox.setDisable(disable);
		});

	}

	public VBox smileys() {
		SIBox.getChildren().addAll(smileysHBox(), sliderVBox());
		return SIBox;
	}

	public HBox smileysHBox() {
		HBox smileyBox = new HBox();
		// neemt het minst aantal elementen van text en tooltip, zodat er later geen
		// problemen ontstaan
		int minLength = java.lang.Math.min(text.length, tooltip.length);
		for (int i = 0; i < minLength; i++) {
			// final int j = i;
			// voegt de knop toe aan HBox
			smileyBox.getChildren().addAll(buttons(text[i], tooltip[i]));
		}

		return smileyBox;

	}

	public static void setMeasurement(Measurements obj) {
		m = obj;
	}

	@Override
	public void action(int newValue) {
		m.setIntesitity(newValue);
		System.out.println(" value of the intesity slider " + newValue);
		// TODO Auto-generated method stub

	}

	private VBox sliderVBox() {
		Label text = new Label("hoe intessief is je gevoel?");
		sliderBox.getChildren().addAll(text, SliderBox());
		sliderBox.setDisable(disable);

		return sliderBox;
	}

}
