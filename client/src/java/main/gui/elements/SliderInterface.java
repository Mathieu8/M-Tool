package src.java.main.gui.elements;

import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * An interface for all the sliders. the size of this slider is 256. <br>
 * <br>
 * right now it goes from from red to yellow to green, but we might need to
 * change that later
 * 
 * @TODO TODO right now that actionSlider will sent back everytime someone is
 *       sliding the slider, there should be some kind of filter that makes sure that they will be not be handled
 * @TODO TODO when the slider is mouse over or pick upped handle those events 
 * 
 * @author Mathieu
 * @version 08/23/2018
 */
public interface SliderInterface {
	/**
	 * the main method of this interface, as it will created the slider. the range
	 * of this slider is 256.
	 * 
	 * @return Slider
	 */
	public default Slider sliders() {
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
			actionSlider(value);
		});// .addListener(this::changed);

		return sliderTemp;
	}

	/**
	 * This needs to be overridden, as all the data that the user makes with the slider will be passed thru here
	 * 
	 * @param newValue - the value the slider has been changed to
	 */
	public void actionSlider(int newValue);
}
