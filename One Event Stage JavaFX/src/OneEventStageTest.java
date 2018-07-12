import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Implementing an Example of OneEventStage to test it
 * @author Yahya Almardeny
 * @version 28/05/2017
 */
public class OneEventStageTest extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// create stage
		OneEventStage stage = new OneEventStage(primaryStage, 400,400); 
		stage.setTitle("One Event Stage");
		
		// simple containers and its components for testing purpose
		VBox container = new VBox();
		container.setAlignment(Pos.CENTER);
		
		HBox widthInfoContainer = new HBox();
		widthInfoContainer.setAlignment(Pos.CENTER);
		Label widthChangeL = new Label("Width Changes");
		TextField widthChangeV = new TextField();
		widthChangeV.setEditable(false);
		widthInfoContainer.getChildren().addAll(widthChangeL, widthChangeV);
		HBox.setMargin(widthChangeL, new Insets(10));
		HBox.setMargin(widthChangeV, new Insets(10));
		
		HBox heightInfoContainer = new HBox();
		heightInfoContainer.setAlignment(Pos.CENTER);
		Label heightChangeL = new Label("Height Changes");
		TextField heightChangeV = new TextField();
		heightChangeV.setEditable(false);
		heightInfoContainer.getChildren().addAll(heightChangeL, heightChangeV);
		HBox.setMargin(heightChangeL, new Insets(10));
		HBox.setMargin(heightChangeV, new Insets(10));
		
		container.getChildren().addAll(widthInfoContainer, heightInfoContainer);
		
		//////////////////////////////////////////////////////////////////////////
		
		DoubleProperty widthChange = stage.getWidthChange();
		DoubleProperty heightChange = stage.getHeightChange();
		
		
		// listen to the changes (Testing)
		widthChange.addListener((obs, old, newV)->{
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					
					widthChangeV.setText("From(" + old.doubleValue() + ")  To(" + newV.doubleValue() + ")");
					
				}
				
			});
			
		});
		
		heightChange.addListener((obs, old, newV)->{
			Platform.runLater(new Runnable(){
				@Override
				public void run() {
					
					heightChangeV.setText("From(" + old.doubleValue() + ") To(" + newV.doubleValue() + ")");
			
				}
				
			});
			
		});
		
		//////////////////////////////////////////////////////////////////////////////////////
		
		// represent a root but in fact it's inside the real root (BorderPane in the OneEventStage Class!).
		StackPane root = new StackPane(); 
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(container);
		stage.scene.setCenter(root);
				
		primaryStage.show();	
	}
	
	
	public static void main(String[] args) {
		launch();
	}


}
