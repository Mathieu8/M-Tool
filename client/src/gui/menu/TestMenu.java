package src.gui.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import src.gui.GUI;
import src.login.LoginGUI;

public class TestMenu {

	Menu getTestMenu() {
		Menu testMenu = new Menu("Test");
		
	testMenu.getItems().add(testLoginAgain());	
		
		
		
				
		return testMenu;
	}
	
	private MenuItem testLoginAgain() {
		MenuItem loginAgainItem = new MenuItem("Login Again");
		loginAgainItem.setOnAction(e -> {
			GUI.print("in Login Again");
			LoginGUI loginGUI = new LoginGUI();
			loginGUI.initialize();
			loginGUI.show();
			GUI.hideStage();
			
			//launch GUI to confirm old PW and enter new PW
			//
			
		});		
		
		return loginAgainItem;		
	}
}
