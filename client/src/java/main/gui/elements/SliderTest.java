package src.java.main.gui.elements;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

public abstract class SliderTest extends Application {
	
	int number;
	public Slider slider;
	double maxNumber = java.lang.Math.pow(2, 8) - 1;
	public boolean disable = true;

	/*
	 * de methode getSlider heeft boolean parameter, zodat intensiteit kan in voor
	 * en na dat een emotie gekozen is gekozen, de zelfde slider class gebruiken
	 */
	protected Slider Sliders() {
		System.out.println("test");
		//Slider sliderTemp 
		slider = new Slider(0, maxNumber, maxNumber / 2 + 0.5);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(maxNumber / 2);
		slider.setMinorTickCount(0);
		slider.setDisable(disable);
		slider.setLabelFormatter(new StringConverter<Double>() {
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

		slider.setBlockIncrement(20);
		slider.setSnapToTicks(false);
		slider.valueProperty().addListener(this::changed);
		//slider = sliderTemp;
		return slider;
		
	}

	public void changed(ObservableValue<? extends Number> prop, Number oldValue, Number newValue) {
		number = (int) slider.getValue();
		action(number);
	}
	
	public abstract void action(int n);

}
