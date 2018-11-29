package src.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.login.Login;

public class WelcomeGUI extends Application {
	private static Login login = null;
	boolean loginValid = false;
	Timeline fiveSecondsWonder;

	@Override
	public void start(Stage primaryStage) throws Exception {
		TextArea welcome = new TextArea("Welcome to the app");

		StackPane root = new StackPane();
		root.getChildren().addAll(welcome);

		Scene scene = new Scene(root, 300, 250);
		primaryStage.setTitle("welcome");

		primaryStage.setScene(scene);
		primaryStage.show();


		Duration d = Duration.seconds(5); // seconds for testing

		fiveSecondsWonder = new Timeline(new KeyFrame(d, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("in TimeLine( in KeyFrame( handle )) ");
				System.out.println(loginValid);

				if (loginValid) {
					GUI gui = new GUI();
					gui.initialize();
					gui.showStage();
					fiveSecondsWonder.stop();
					primaryStage.hide();
					
				} else {
//					login =
							Login.loginEntry().tokenValid();
//					loginValid = login.tokenValid();
//					loginValid = true;
					// show login Screen
				}
			}
		}));
		fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
		fiveSecondsWonder.play();

		// TODO Auto-generated method stub

	}

	public void startGUI(String... args) {
		Application.launch(args);
	}

	public WelcomeGUI() {
		login = Login.loginEntry();
//		loginValid = login.tokenValid();
	}

}
