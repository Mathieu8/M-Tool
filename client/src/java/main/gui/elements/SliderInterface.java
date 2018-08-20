package src.java.main.gui.elements;

import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public interface SliderInterface {
	// public int number;
	// public Slider slider;

	/*
	 * de methode getSlider heeft boolean parameter, zodat intensiteit kan in voor
	 * en na dat een emotie gekozen is gekozen, de zelfde slider class gebruiken
	 */
	public default Slider Sliders() {
		double maxNumber = java.lang.Math.pow(2, 8) - 1;
		Slider sliderTemp = new Slider(0, maxNumber, maxNumber / 2 + 0.5);

		sliderTemp.setShowTickLabels(true);
		sliderTemp.setShowTickMarks(true);
		sliderTemp.setMajorTickUnit(maxNumber / 2);
		sliderTemp.setMinorTickCount(0);
		sliderTemp.setDisable(enable());

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
		sliderTemp.valueProperty().addListener((e) -> action((int) sliderTemp.getValue()));// .addListener(this::changed);

		return sliderTemp;
	}

	default boolean enable() {
		return false;
	}

	public default VBox SliderBox() {
		VBox temp = new VBox();
		temp.getChildren().addAll(Sliders());

		return temp;
	}
	/*
	 * @Override public void changed(ObservableValue<? extends Number> prop, Number
	 * oldValue, Number newValue) { int number = (int) sliderTemp.getValue();
	 * System.out.print("niet goed in sliders "); System.out.println(number); }
	 */

	public void action(int newValue);
}
