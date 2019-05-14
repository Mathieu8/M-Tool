package src.gui.menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import src.gui.GUI;

public class MBar {
	public MenuBar getMenuBar() {

	

		// create a menubar
//		MenuBar mb = new MenuBar(m);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(new FileMenu().getFileMenu());
		menuBar.getMenus().add(new OptionsMenu().getOptionsMenu());
		menuBar.getMenus().add(new TestMenu().getTestMenu());

		return menuBar;
	}

	
}
