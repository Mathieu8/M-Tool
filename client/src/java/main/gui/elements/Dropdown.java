package src.java.main.gui.elements;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

/**
 * @author mathieu
 *
 *         generieke ComboBox of een dropdown menutje
 * 
 *         als je het gebruikt vergeet niet om de changed te veranderen en aan
 *         de dropdown toe te voegen.
 * 
 *         Ik heb bij de eventhandlers een controle ingebouwd momenteel om zeker
 *         van te zijn dat hij de goede pakt en niet een verkeerde
 */

public class Dropdown {
	ComboBox<String> dropdownBox;

	public ComboBox<String> returnDropdownBox() {
		dropdownBox = new ComboBox<>();
		return dropdownBox;
	}

	public void addItem(String s) {
		dropdownBox.getItems().add(s);
	}

	{
		dropdownBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				System.out.print("niet goed in dropdown");
				System.out.println(t);
				System.out.print("niet goed in dropdown");
				System.out.println(t1);
				// TODO toSave the data
			}
		});
	}

}
