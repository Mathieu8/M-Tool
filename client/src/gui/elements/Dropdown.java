package src.gui.elements;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * a generic ComboBox is made here. An String array is required to start it, as
 * this will be the text that will appear. <br>
 * <br>
 * only the actionComboBox() needs to be overridden to use this interface.
 * 
 * @author mathieu
 * @version 08/23/2018
 */

public interface Dropdown {

	/**
	 * a basic comboBox is created with this method.
	 * 
	 * @param s - String array with all the elements you want in the comboBox.
	 * @return comboBox<String>
	 */
	public default ComboBox<String> dropDownVBox(String[] s) {
		ComboBox<String> dropdownBox = new ComboBox<>();
		for (int i = 0; i < s.length; i++) {
			dropdownBox.getItems().add(s[i]);
		}

		dropdownBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String oldValue, String newValue) {
				actionComboBox(oldValue, newValue);

			}

		});
		return dropdownBox;
	}

	/**
	 * This method need to be overridden to use the comboBox created above.
	 * 
	 * @param oldValue - the value the comboBox was before it was changed.
	 * @param newValue - the new value of the comboBox.
	 */
	public void actionComboBox(String oldValue, String newValue);
}
