package src.java.main.gui.elements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;

/**
 * Basic Button will be made with a text for on the button and a tooltip to go
 * with the button.
 * 
 * @version 08/23/2018
 * @author Mathieu
 *
 */
public interface Buttons {

	/**
	 * 
	 * @param btnText - the text that will be shown when looking at the button
	 * @param tooltip - the text that will be shown when the user is mouseover the
	 *                button
	 * @return Button - with the text btnText and a tooltip of tooltip, the rest of
	 *         the button is very basic
	 */
	public default Button buttons(String btnText, String tooltip) {
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
				actionButton(btnText);
				event.consume();
			}

		};
		btn.setOnAction(buttonHandler);
		return btn;

	}

	/**
	 * all the action that will happen when the button is pressed will be defined
	 * here
	 * 
	 * @param text -  that is on the button. 
	 */
	public void actionButton(String text);

	public default double MinWidth() {
		return 50.0;
	}
}
