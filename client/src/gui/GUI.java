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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.gui.measurementGUI.MeasurmentGUI;

/**
 * this class launches the javaFX application. <BR>
 * Right now the timer is also launched from here,<BR>
 * @TODO TODO move the timer to it's one method/class
 * @TODO TODO Options menu, to change certain values
 * 
 * 
 * @author Mathieu
 * @version 09/27/2018
 *
 */
public class GUI extends Application {
	static MeasurmentGUI measurment;
	static Stage measurmentGUI;

	@Override
	public void start(Stage primaryStage) throws Exception {
		measurment = new MeasurmentGUI();
		Label welcome = new Label("Welcome");

		primaryStage.setTitle("Self Management Tool");
		Platform.setImplicitExit(false);

		StackPane root = new StackPane();
		root.getChildren().add(welcome);

		Scene scene = new Scene(root, 300, 250);

		primaryStage.setScene(scene);
		primaryStage.show();
		
		//doesn't work
		welcome.textProperty().addListener((observable, oldValue, newValue) -> {
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			pause.setOnFinished(event -> primaryStage.hide());
			pause.play();
		});
		
		Duration d = Duration.seconds(5); // seconds for testing
		// d.add(Duration.minutes(50)); // minutes for use
		measurmentGUI = measurment.initialized();
		Timeline fiveSecondsWonder = new Timeline(new KeyFrame(d, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("this is called every 5 seconds on UI thread");
				measurment.setUID(1);
				measurment.reset(1);

				measurmentGUI.show();
			}
		}));
		fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
		fiveSecondsWonder.play();

	}

	public void startGUI(String[] args) {
		Application.launch(args);
	}



}