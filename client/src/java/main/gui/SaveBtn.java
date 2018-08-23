package src.java.main.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import measurements.BasicMeasurements;
import src.java.main.client.toServer.ToServer;

/**
 * This class makes a button, that if pressed all the data will be sent to the
 * server to be handled
 * 
 * @TODO TODO this class should implement Buttons interface.
 * @author mathieu
 *  @version 08/23/2018
 */

public class SaveBtn {
	static BasicMeasurements obj;
	String text = "save";
	String tooltip = "save";

	/**
	 * @return Button
	 */
	public Button createBtn() {
		Button btn = new Button("save");
		Tooltip emotieTemp = new Tooltip(tooltip);
		btn.setTooltip(emotieTemp);
		btn.setMinWidth(50.0);
		btn.setPrefWidth(50.0);
		btn.setMaxWidth(50.0);
		btn.setDefaultButton(true);

		btn.setFont(Font.font("Verdana", 12));
		EventHandler<ActionEvent> btnHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				toSave();
				event.consume();
			}

		};
		btn.setOnAction(btnHandler);
		return btn;
	}

	/**
	 * @return HBox - with the save button in it
	 */
	public HBox saveBtn() {
		HBox saveBox = new HBox();
		saveBox.getChildren().addAll(createBtn());
		return saveBox;

	}

	/**
	 * This makes a connection to the server and than passes object obj to the
	 * server.
	 */
	private void toSave() {
		obj.setDuraction();
		ToServer ts = new ToServer();
		ts.sendToServer(obj);
	}

	/**
	 * @param o - object that will be later passed on to the server
	 */
	public void setObject(BasicMeasurements o) {
		obj = o;
	}

	

}