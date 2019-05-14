package src.gui.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import src.gui.GUI;
import src.gui.measurementGUI.MeasurmentGUI;

public class OptionsMenu {
	
	Menu getOptionsMenu() {
		Menu options = new Menu("Options");
		
	options.getItems().add(testItem());	
		
		
		
				
		return options;
	}
	
	private MenuItem testItem() {
		MenuItem testItem = new MenuItem("Change Password");
		testItem.setOnAction(e -> {
			GUI.print("in change PW");
			//launch GUI to confirm old PW and enter new PW
			//
			
		});		
		
		return testItem;		
	}

}
