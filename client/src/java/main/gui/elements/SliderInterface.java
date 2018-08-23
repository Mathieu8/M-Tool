package src.java.main.gui.elements;

import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * An interface for all the sliders. the size of this slider is 256. <br><br>
 * right now it goes from from red to yellow to green, but we might need to
 * change that later
 * 
 * @author Mathieu
 * @version 08/23/2018
 */
public interface SliderInterface {
/**
 * the main method of this interface, as it will created the slider. the range of this slider is 256.
 * @return Slider 
 */
	public default Slider Sliders() {
		double maxNumber = java.lang.Math.pow(2, 8) - 1;
		Slider sliderTemp = new Slider(0, maxNumber, maxNumber / 2 + 0.5);

		sliderTemp.setShowTickLabels(true);
		sliderTemp.setShowTickMarks(true);
		sliderTemp.setMajorTickUnit(maxNumber / 2);
		sliderTemp.setMinorTickCount(0);

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
		sliderTemp.valueProperty().addListener((e) -> {
			int value = (int) sliderTemp.getValue();
			action(value);
		});// .addListener(this::changed);

		return sliderTemp;
	}

	/**
	 * 
	 * @return a VBox with in it a slider
	 */
	public default VBox SliderBox() {
		VBox temp = new VBox();
		temp.getChildren().add(Sliders());

		return temp;
	}

	public void action(int newValue);
}
