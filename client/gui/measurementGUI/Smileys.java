package gui.measurementGUI;

import java.util.Arrays;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import measurements.Measurements;

/**
 * 
 * 
 * hier wordt een rijdtje met knopjes met daarop de smileys gemaakt
 * 
 * bij aan passing zorg er voor dat text als tooltip even veel elementen hebben.
 * 
 * @author mathieu
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
	// IntensiteitTest slider = new IntensiteitTest();
	// Intensiteit intensiteit = new Intensiteit(disable);
	// SliderInterface test = new SlinterInterface();

	public void action(String emotie) {
		m.setEmotion(tooltip[Arrays.asList(text).indexOf(emotie)]);

	}

	public VBox smileys() {
		SIBox.getChildren().addAll(smileysHBox(), SliderBox()); // slider.intensiteitTestBox(disable));
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
		System.out.println("value of the slider" + newValue);
		// TODO Auto-generated method stub

	}

}
