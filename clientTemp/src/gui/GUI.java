package src.gui;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.gui.elements.Buttons;
import src.gui.measurementGUI.MeasurmentGUI;
import src.gui.menu.MBar;
import src.login.Login;

/**
 * this class launches the javaFX application. <BR>
 * Right now the timer is also launched from here,<BR>
 * 
 * @TODO TODO move the timer to it's one method/class
 * @TODO TODO Options menu, to change certain values
 * 
 * 
 * @author Mathieu
 * @version 09/27/2018
 *
 */
public class GUI  {
	static MeasurmentGUI measurment;
	static Stage measurmentGUI;
	static Stage main = null;
	public static TextArea welcome;

	public Stage initialize() {
		if (main == null) {
			main = new Stage();
			welcome = new TextArea();
			measurment = new MeasurmentGUI();
			// Label welcome = new Label("Welcome");

			Platform.setImplicitExit(false);
//			String test = "test";
			StackPane root = new StackPane();
			MenuBar menuBar = new MBar().getMenuBar();
			VBox vb = new VBox();
			
			vb.getChildren().addAll(menuBar , welcome);
			root.getChildren().addAll(vb);

			Scene scene = new Scene(root, 300, 250);

			main.setScene(scene);
			main.setTitle("Self Management Tool");

			Duration d = // Duration.seconds(5); // seconds for testing
					// d.add(Duration.minutes(50)); // minutes for use
					Duration.INDEFINITE; // so it never launches during testing
			measurmentGUI = measurment.initialized();

//		Login.loginEntry().tokenValid();

			Timeline fiveSecondsWonder = new Timeline(new KeyFrame(d, new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Platform.runLater(() -> {
						welcome.appendText("this is called every " + d + " on UI thread" + '\n');
					});
					measurment.setUID(1);
					measurment.reset(1);
					measurmentGUI.show();
				}
			}));
			fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
			fiveSecondsWonder.play();
		}
		return main;
	}

	public void showStage() {
		main.show();
	}
	
	public static void hideStage() {
		main.hide();
	}

	public static void print(String s) {
		System.out.println(s);

		if (main != null) {
			Platform.runLater(() -> {
				GUI.welcome.appendText(s + '\n');
			});
		}
	}

}
