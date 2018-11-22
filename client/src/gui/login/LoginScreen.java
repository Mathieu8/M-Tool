package src.gui.login;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.stage.Stage;

public class LoginScreen {
	String Username;
	char[] pw;
	Stage stage;

	public void LoginScreen() {
		stage.setTitle("JavaFX 2 Login");
		        BorderPane bp = new BorderPane();
		        bp.setPadding(new Insets( 0,50,50,50));
		        //Adding HBox
		        HBox hb = new HBox();
		        hb.setPadding(new Insets(20,20,20,30));
		        //Adding GridPane
		        GridPane gridPane = new GridPane();
		        gridPane.setPadding(new Insets(20,20,20,20));
		        gridPane.setHgap(5);
		        gridPane.setVgap(5);
		       //Implementing Nodes for GridPane
		        Label lblUserName = new Label("Username");
		        final TextField txtUserName = new TextField();
		        Label lblPassword = new Label("Password");
		        final PasswordField pf = new PasswordField();
		        Button btnLogin = new Button("Login");
		        final Label lblMessage = new Label();
		        //Adding Nodes to GridPane layout
		        gridPane.add(lblUserName, 0, 0);
		        gridPane.add(txtUserName,  1, 0);
		        gridPane.add(lblPassword, 0, 1 );
		        gridPane.add(pf,1  ,  1);
		        gridPane.add(btnLogin, 2,  1);
		        gridPane.add(lblMessage,  1, 2);
		        //Reflection for gridPane
		        Reflection r = new Reflection();
		        r.setFraction(0.7f);
		        gridPane.setEffect(r);
		        //DropShadow effect
		        DropShadow dropShadow = new DropShadow();
		        dropShadow.setOffsetX(5);
		        dropShadow.setOffsetY(5);
		        //Adding text and DropShadow effect to it
		        Text text = new Text("JavaFX 2 Login");
		        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
		        text.setEffect(dropShadow);
		        //Adding text to HBox
		        hb.getChildren().add(text);
		        //Add ID's to Nodes
		        bp.setId("bp");
		        gridPane.setId("root");
		        btnLogin.setId("btnLogin");
		        text.setId("text");
		        //Action for btnLogin
		        EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						
						event.consume();
					}

				};
		        btnLogin.setOnAction(buttonHandler );
		        //Add HBox and GridPane layout to BorderPane Layout
		        bp.setTop(hb);
		        bp.setCenter(gridPane); 
		        //Adding BorderPane to the scene and loading CSS
		     Scene scene = new Scene(bp);
		     scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
		     stage.setScene(scene);
		       stage.titleProperty().bind(
		                 scene.widthProperty().asString().
		                 concat(" : ").
		                 concat(scene.heightProperty().asString()));
		     //stage.setResizable(false);
		     stage.show();
	}
}