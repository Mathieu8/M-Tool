package src.java.main.gui.measurementGUI;

import java.time.LocalDateTime;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import measurements.Measurements;
import src.java.main.gui.SaveBtn;

/**
 * This class makes a GUI for the measurement in the Core. So this is the place
 * where everything is accessed thru. As it initialized all the small parts like
 * the buttons, dropdown box and sliders.
 * 
 * @author Mathieu
 * @version 08/23/2018
 *
 */
public class MeasurmentGUI extends Application {
	/*
	 * important, cause startGUI creates a new instance of this class to run
	 * everything from it is required to make o static
	 */
	static Measurements m;
	SaveBtn saveButton = new SaveBtn();
	Smileys smileys = new Smileys();
	Activity activity = new Activity();
	Productivity productivity = new Productivity();

	/**
	 * @param args
	 */
	public void startGUI(String[] args) {
		Application.launch(args);
	}

	public void start(Stage stage) {
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);

		VBox layout = new VBox();
		// HBox saveBtn = saveButton.saveBtn();
		layout.getChildren().addAll(smileys.smileys(), activity.activiteitVBox(), productivity.prodSlider(),
				saveButton.saveBtn());

		root.getChildren().add(layout);
		// root.getChildren().add(saveBtn);
		root.setMinSize(300, 250);
		root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: BLACK;");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Self Management Tool");
		stage.show();

	}

	/**
	 * this method set the obj reference correctly in this and all the other class that are used in MeasurmentGUI
	 * to use later
	 * @param UID - it might be used later 
	 */
	public void setUID(int UID) {
		m = new Measurements(UID, LocalDateTime.now());
		Smileys.setMeasurement(m);
		Activity.setM(m);
		Productivity.setMeasurement(m);
		saveButton.setObject(m);
	}

}
