package src.java.main.gui.elements;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

/**
 * 
 * @author mathieu
 * 
 *         Standaard Slider voor vragen met een rood tot groen achtergrond bij
 *         gebruik pas de changed-method aan
 * 
 *         Ik heb bij de eventhandlers een controle ingebouwd momenteel om zeker
 *         van te zijn dat hij de goede pakt en niet een verkeerde
 */

public class Sliders extends Slider{
	public int number;
	public Slider slider;
	double maxNumber = java.lang.Math.pow(2, 8) - 1;

	/*
	 * de methode getSlider heeft boolean parameter, zodat intensiteit kan in voor
	 * en na dat een emotie gekozen is gekozen, de zelfde slider class gebruiken
	 */
	public Sliders() {
		Slider sliderTemp = new Slider(0, maxNumber, maxNumber / 2 + 0.5);
		sliderTemp.setShowTickLabels(true);
		sliderTemp.setShowTickMarks(true);
		sliderTemp.setMajorTickUnit(maxNumber / 2);
		sliderTemp.setMinorTickCount(0);
		sliderTemp.setDisable(false);

		sliderTemp.setLabelFormatter(new StringConverter<Double>() {
			@Override
			public String toString(Double n) {
				if (n < 0.5)
					return "rood";
				if (n < maxNumber / 2 + 0.5)
					return "greel";

				return "groen";
			}

			@Override
			public Double fromString(String s) {
				switch (s) {
				case "rood":
					return 0d;
				case "geel":
					return 1d;
				case "groen":
					return 2d;

				default:
					return 3d;
				}
			}
		});

		sliderTemp.setBlockIncrement(20);
		sliderTemp.setSnapToTicks(false);
		sliderTemp.valueProperty().addListener(this::changed);
		slider = sliderTemp;
	}

	public void changed(ObservableValue<? extends Number> prop, Number oldValue, Number newValue) {
		number = (int) slider.getValue();
		System.out.print("niet goed in sliders ");
		System.out.println(number);
	}

	

}
