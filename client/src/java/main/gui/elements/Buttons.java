package src.java.main.gui.elements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;

public interface Buttons {
	public default Button buttons(String btnText, String tooltip) {
		// number = numberTemp;
		Button btn = new Button(btnText);
		Tooltip btnTooltip = new Tooltip(tooltip);
		btn.setTooltip(btnTooltip);
		btn.setMinWidth(MinWidth());
		btn.setPrefWidth(50.0);
		btn.setMaxWidth(50.0);
		btn.setFont(Font.font("Verdana", 20));
		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				action(btnText);
				event.consume();
			}

		};
		btn.setOnAction(buttonHandler);
		return btn;

	}

	public void action(String text);
	
	public default double MinWidth() {
		return 50.0;
	}
}
