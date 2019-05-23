package src.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class MenuBar_test {

	MenuBar getMenuBar() {

		Menu m = new Menu("Options");
		Menu testBar = new Menu("Testing");

		// create menuitems
		MenuItem m1 = new MenuItem("menu item 1");
		MenuItem m2 = new MenuItem("menu item 2");
		MenuItem m3 = new MenuItem("menu item 3");

		// add menu items to menu
		
		m.getItems().add(m1);
		m.getItems().add(m2);
		m.getItems().add(m3);
		m.getItems().add(exitItem());


		// create events for menu items
		// action event

		// add event
		m1.setOnAction(event);
		m2.setOnAction(event);
		m3.setOnAction(event);

		// create a menubar
//		MenuBar mb = new MenuBar(m);
		MenuBar menuBar = new MenuBar(m);

		return menuBar;
	}

	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			GUI.print("\t\t\t\t" + ((MenuItem) e.getSource()).getText() + " selected");
		}
	};
	
	
	private MenuItem exitItem() {
		MenuItem exitItem = new MenuItem("E_xit");
		exitItem.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});
		return exitItem;
	}

}
