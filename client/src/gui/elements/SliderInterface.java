package src.gui.elements;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * An interface for all the sliders. the size of this slider is 256. <br>
 * <br>
 * right now it goes from from red to yellow to green, but we might need to
 * change that later
 * <br>
 * <br>
 * TODO right now that actionSlider will sent back everytime someone is sliding
 * the slider, there should be some kind of filter that makes sure that they
 * will be not be handled , should be dealt with as 08/27/2018<br>
 * <br>
 * <b> TODO</b> when the slider is mouse over or pick upped handle those events
 * 
 * @author Mathieu
 * @version 08/27/2018
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

		sliderTemp.valueProperty().addListener((obs, oldValue, newValue) -> {
			/*
			 * this event listener is for when the user clicks on the slider to change it or
			 * uses the arrow buttons to change it
			 */
			if (newValue != null && !newValue.equals(oldValue) && !sliderTemp.isValueChanging()) {
				int value = (int) sliderTemp.getValue();
				actionSlider(value);
			}
		});

		sliderTemp.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			// this event listener is for when the user drags the dot around the slider
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging,
					Boolean changing) {
				if (!changing) {
					int value = (int) sliderTemp.getValue();
					mouseSlider(value);
				}
			}
		});

		return sliderTemp;
	}

	/**
	 * This needs to be overridden, as all the data that the user makes with the
	 * slider will be passed thru here <BR>
	 * <BR>
	 * as in the sliderInterface both mouseSlider(int newValue) and clickSlider(int
	 * newValue) pass their newValue back to this method
	 * 
	 * @param newValue - the value the slider has been changed to
	 */
	public void actionSlider(int newValue);

	/**
	 * This simpley passes the value of the slider onto the action that the slider
	 * should take in general<br>
	 * <br>
	 * this is method can be overridden to specify which action is required for when
	 * the user drags the slider around
	 * 
	 * @param newValue - the most recent value of the slider should go into here
	 */
	default void mouseSlider(int newValue) {
		actionSlider(newValue);
	}

	/**
	 * This simpley passes the value of the slider onto the action that the slider
	 * should take in general<br>
	 * <br>
	 * this is method can be overridden to specify which action is required for when
	 * the user clicks the slider around
	 * 
	 * @param newValue - the most recent value of the slider should go into here
	 */
	default void clickSlider(int newValue) {
		actionSlider(newValue);
	}
}
