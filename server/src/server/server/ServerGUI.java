package src.server.server;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerGUI extends Application {
	Server server = null;
	// Text area for displaying contents
	TextArea ta = new TextArea();

	// Number a client
	private int clientNo = 0;

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		Button btn = new Button("exit");
		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(1);
				event.consume();
			}
		};
		btn.setOnAction(buttonHandler);
		// Create a scene and place it in the stage

		GridPane root = new GridPane();
		VBox vb = new VBox();
		vb.getChildren().addAll(btn, ta);
		root.getChildren().addAll(vb);

		Scene scene = new Scene(root);// , 450, 200);

		primaryStage.setTitle("MultiThreadServer"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		server = new Server(this);
		server.runServer();
		
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}