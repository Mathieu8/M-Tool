package src.server.server;



public class ServerGUI  {
//	Server server = null;
	// Text area for displaying contents
//	static TextArea ta = new TextArea();

//	@Override // Override the start method in the Application class
//	public void start(Stage primaryStage) {
//		Button btn = new Button("exit");
//		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				System.exit(1);
//				event.consume();
//			}
//		};
//		btn.setOnAction(buttonHandler);
//		// Create a scene and place it in the stage
//
//		GridPane root = new GridPane();
//		VBox vb = new VBox();
////		vb.getChildren().addAll(btn, ta);
//		root.getChildren().addAll(vb);
//
//		Scene scene = new Scene(root);// , 450, 200);
//
//		primaryStage.setTitle("MultiThreadServer"); // Set the stage title
//		primaryStage.setScene(scene); // Place the scene in the stage
//		primaryStage.show(); // Display the stage
//
//
//	}

	public static void main(String[] args) {
//		launch(args);
		new Server().runServer();

	}

	public static void print(String s) {
		System.out.println(s);
//		
//		Platform.runLater(() -> {
//			ta.appendText(s + '\n');
//		});
	}

}