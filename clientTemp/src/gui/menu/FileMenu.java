package src.gui.menu;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import src.gui.measurementGUI.MeasurmentGUI;

public class FileMenu {
	
	private MeasurmentGUI measurment;
	private Stage measurmentGUI;
	
	Menu getFileMenu() {
		Menu file = new Menu("File");
		
		
		
		
		file.getItems().add(testItem());
		file.getItems().add(exitItem());
				
		return file;
	}
	
	
	private MenuItem exitItem() {
		MenuItem exitItem = new MenuItem("E_xit");
		exitItem.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});
		return exitItem;
	}
	
	private MenuItem testItem() {
		measurment = new MeasurmentGUI();
		measurmentGUI = measurment.initialized();
		MenuItem testItem = new MenuItem("Measurment");
		testItem.setOnAction(e -> {
			measurment.setUID(1);
			measurment.reset(1);
			measurmentGUI.show();
		});		
		
		return testItem;		
	}
	
	
	

}
