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
import src.login.LoginGUI;

public class WelcomeGUI extends Application {
	private static Stage STAGE;
	private Login login = null;
	private boolean loginValid = false;
	private boolean firstTime = true;
	private Timeline secondsWonder;
	

	public boolean isLoginValid() {
		return loginValid;
	}

	public void setLoginValid(boolean loginValid) {
		this.loginValid = loginValid;
	}
	
	
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		TextArea welcome = new TextArea("Welcome to the app");
		STAGE = primaryStage;

		StackPane root = new StackPane();
		root.getChildren().addAll(welcome);

		Scene scene = new Scene(root, 300, 250);
		primaryStage.setTitle("welcome");

		primaryStage.setScene(scene);
		primaryStage.show();


		Duration d = Duration.seconds(1); // seconds for testing

		secondsWonder = new Timeline(new KeyFrame(d, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("in TimeLine( in KeyFrame( handle )) ");
				System.out.println("LoginValid = " + loginValid);

				if (loginValid) {
					GUI gui = new GUI();
					gui.initialize();
					gui.showStage();
					secondsWonder.stop();
					primaryStage.hide();
					
				} else if(firstTime) {
//					GUI gui = new GUI();
//					gui.initialize();
//					gui.showStage();
//					fiveSecondsWonder.stop();
//					primaryStage.hide();
					loginValid = Login.loginEntry().tokenValid();
					firstTime = false;
					
				} else {
					secondsWonder.pause();
					LoginGUI login = new LoginGUI();
					login.initialize();
					login.show();
					
				}
				//test that PW is correct, if so show gui
				
			}
		}));
		secondsWonder.setCycleCount(Timeline.INDEFINITE);
		secondsWonder.play();

		// TODO Auto-generated method stub

	}

	public void startGUI(String... args) {
		Application.launch(args);
	}

	public WelcomeGUI() {
		login = Login.loginEntry();
		loginValid = login.tokenValid();
	}

	public static void hideStage() {
		STAGE.hide();
		// TODO Auto-generated method stub
		
	}

}
