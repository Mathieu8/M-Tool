package src.java.main.gui;
import javafx.scene.layout.VBox;

public class SlinterInterface implements src.java.main.gui.elements.SliderInterface{
	VBox box = new VBox();
	//Text text = new Text("wat ben je nu aan het doen?");
	
	public VBox SliderBox() {
		box.getChildren().addAll( Sliders());
		
		return box;
	}

	public void action(int newValue) {
		System.out.println("SliderInterface" + newValue);
		
	}
	
	

}
