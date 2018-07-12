import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * This class customize  a given Stage to record the changes 
 * of its size only when user starts and finishes resizing (recording one event)
 * @author Yahya Almardeny
 * @version 28/05/2017
 */
public class OneEventStage{
	private double originalWidth;  // the initial width of Scene when the program starts
	private double originalHeight; // the initial height of Scene when the program starts
	private TitleBar titleBar; // can be customized by the setter method (by default I made it for Windows 10 style)
	private boolean started, alreadyFullScreen;
	private DoubleProperty widthChange, heightChange; // record the changes in size
	public Scene s;
	public BorderPane scene; // this will be considered as a Scene when used in the program
	
	public OneEventStage(Stage stage, double width, double height){
		originalWidth = width; originalHeight = height;
		
		widthChange = new SimpleDoubleProperty(originalWidth);
		heightChange = new SimpleDoubleProperty(originalHeight);
		
		started = false;
		titleBar = new TitleBar("");
		
		scene = new BorderPane();
		scene.setTop(titleBar.getTitleBar());
		
		s = new Scene(scene, originalWidth,originalHeight);
		
		stage.initStyle(StageStyle.UNDECORATED);
		
		stage.setScene(s);
		
		ResizeHelper.addResizeListener(stage);
		
		Task<Void> task = new Task<Void>(){
			@Override
			protected Void call() throws Exception {
				Platform.runLater(new Runnable(){	
					@Override
					public void run() {
						// change listener, to be added to and removed from the scene
						ChangeListener<Number> changeListener= (observable, oldValue, newValue) ->{
							if(isFullScreen()){
								widthChange.setValue(stage.getWidth());
							    heightChange.setValue(stage.getHeight());
							    alreadyFullScreen=true;
							}
							
							else if (alreadyFullScreen){ // coming from full screen mode
								widthChange.setValue(Screen.getPrimary().getVisualBounds().getWidth());
							    heightChange.setValue(Screen.getPrimary().getVisualBounds().getHeight());
								widthChange.setValue(originalWidth);
							    heightChange.setValue(originalHeight);
								alreadyFullScreen = false;
							}
							else if(!alreadyFullScreen && !started){
								started = true;	// to inform the detecting Mouse Release Event is required
							}
						};

						s.setOnMouseReleased(e->{
							if(started){ // if this happens particularly after changing the size/dragging
								originalWidth = stage.getWidth(); // record the new scene size 
							    originalHeight = stage.getHeight();
								widthChange.setValue(originalWidth); // add it
							    heightChange.setValue(originalHeight);
							    started = false;
							}
						});
							
					
						// add the change listener when the program starts up
						s.widthProperty().addListener(changeListener); 
						s.heightProperty().addListener(changeListener);
					}
					
				});
				return null;
			}};
		new Thread(task).start();
		
	}

	/*
	 * to detected if user clicked on maximize button or double click on the title bar
	 */
	private boolean isFullScreen(){
		return this.s.getWindow().getWidth()==Screen.getPrimary().getVisualBounds().getWidth() &&
			   this.s.getWindow().getHeight()==Screen.getPrimary().getVisualBounds().getHeight();
	}
	
	
	public DoubleProperty getWidthChange() {
		return widthChange;
	}

	public DoubleProperty getHeightChange() {
		return heightChange;
	}

	public TitleBar getTitleBar() {
		return titleBar;
	}


	public void setTitleBar(TitleBar titleBar) {
		this.titleBar = titleBar;
	}
	
	public void setTitle(String title){
	   titleBar.getTitle().setText(title);
	}


}
