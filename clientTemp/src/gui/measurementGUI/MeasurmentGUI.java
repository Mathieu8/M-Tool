package src.gui.measurementGUI;

import java.time.LocalDateTime;
//import java.util.TimerTask;
//import java.util.concurrent.Callable;
//import java.util.concurrent.FutureTask;
//
//import javafx.animation.Timeline;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import measurements.Measurements;
import src.gui.SaveBtn;

/**
 * This class makes a GUI for the measurement in the Core. So this is the place
 * where everything is accessed thru. As it initialized all the small parts like
 * the buttons, dropdown box and sliders.
 * 
 * @TODO TODO reseting the stage after a new one is launched.
 * @author Mathieu
 * @version 09/27/2018
 *
 */
public class MeasurmentGUI {

	/*
	 * important, cause startGUI creates a new instance of this class to run
	 * everything from it is required to make it static
	 */
	static Measurements m;
	int UID;

	//initializers for classes that are needed
	public static Stage stage = new Stage();
	SaveBtn saveButton = new SaveBtn();
	Smileys smileys = new Smileys();
	Activity activity = new Activity();
	Productivity productivity = new Productivity();
	
	//all the elements that are used later
	VBox layout = new VBox(); 
	VBox smileyBox = smileys.smileys();
	VBox activityBox = activity.activiteitVBox();
	VBox productivityBox =  productivity.prodSlider();
	HBox saveBox = saveButton.saveBtn();

	/**
	 * 
	 * 
	 * @return Stage to measure the measurement. 
	 */
	public Stage initialized() {
		setUID(1);
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);

		layout.getChildren().addAll(smileyBox, activityBox, productivityBox, saveBox);

		root.getChildren().add(layout); 
		root.setMinSize(300, 250);
		root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: BLACK;");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Self Management Tool");
		return stage;
	}

	/**
	 * this method set the obj reference correctly in this and all the other class
	 * that are used in MeasurmentGUI to use later
	 * 
	 * @param UID - it might be used later
	 */
	public void setUID(int UID) {
		this.UID = UID;
		m = new Measurements(UID, LocalDateTime.now());
		Smileys.setMeasurement(m);
		Activity.setM(m);
		Productivity.setMeasurement(m);
		saveButton.setObject(m);
	}
	
	
	/**
	 * Here it should reset the stage each time it's launched 
	 * 
	 * @param UID - it might be used later
	 */
	public void reset(int UID) {
		setUID(UID);
		stage.hide();
		layout.getChildren().removeAll(smileyBox, activityBox, productivityBox, saveBox);
		
		
		layout.getChildren().addAll(smileyBox, activityBox, productivityBox, saveBox);		
	}
}
