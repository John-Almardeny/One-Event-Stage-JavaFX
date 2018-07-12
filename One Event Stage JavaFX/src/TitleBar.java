
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * This class to create a default/customized Title Bar 
 * to be added to Undecorated Stage in JavaFX Application
 * @author Yahya Almardeny
 * @version 27/05/2017
 */
public class TitleBar {
	private HBox titleBar;
	private ImageView icon;
	private StackPane close, minimize, maximize; // represent customized components for the title bar (by using the second constructor)
	private Image maximizeBefore, maximizeAfter; // for changing maximize icon when it's full screen
	private Label title;
	private double height, stageWidth, stageHeight, x,y, offsetX, offsetY;
	private double screenWidth = Screen.getPrimary().getVisualBounds().getWidth(), 
				   screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
	private Color backgroundColor;
	private StackPane maximizeButton; // for default title bar
	private Label minimizeButton, closeButton; // for default title bar
	private Stage stage;
    private boolean intialized = false, fromMax = false;
	
    public static enum Components {ICON,TITLE,MINIMIZE,MAXIMIZE,CLOSE;}
    
	/**
	 * the default constructor, appearance of Windows 10
	 * @param title
	 */
	public TitleBar(String title){
		titleBar = new HBox();
		
		icon =  new ImageView(new Image(TitleBar.class.getResourceAsStream("/icon/icon.png")));
		icon.setFitWidth(15); this.icon.setFitHeight(13); 
		
		closeButton = new Label("×");
		closeButton.setFont(Font.font("Times New Roman", 25));
		closeButton.setPrefWidth(46);
		closeButton.setAlignment(Pos.CENTER);
		
		minimizeButton = new Label("—");
		minimizeButton.setFont(Font.font(10));
		minimizeButton.setPrefWidth(46);
		minimizeButton.setPrefHeight(29);
		
		minimizeButton.setAlignment(Pos.CENTER);
		maximizeButton = maximiazeButton();
		
		this.title = new Label(title);
		
		final Pane space = new Pane();
		HBox.setHgrow(space,Priority.ALWAYS);
		
		titleBar.getChildren().addAll(this.icon, this.title,space,this.minimizeButton, this.maximizeButton, this.closeButton);
		titleBar.setAlignment(Pos.CENTER_RIGHT);
		
		HBox.setMargin(this.icon, new Insets(0,5,0,10)); // top,right, bottom, left
	
		initalize(); // private method to get the Stage for first time
		setDefaultControlsFunctionality(); // private method to add the default controls functionality
		
	}

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
     * This is constructor to create a custom title bar
     * @param icon
     * @param minimize
     * @param maximize
     * @param close
     * @param title
     */
    public TitleBar(Image icon, Image minimize, Image maximizeBefore, Image maximizeAfter, Image close, String title){
		titleBar = new HBox();
		
		this.icon =  new ImageView(icon);
		this.icon.setFitWidth(15); this.icon.setFitHeight(14);  // values can be changed via setters
		
		this.close = new StackPane();
		this.close.setPrefSize(25, 20);
		this.close.getChildren().add(new ImageView(close));
		((ImageView) this.close.getChildren().get(0)).setFitWidth(20);
		((ImageView) this.close.getChildren().get(0)).setFitHeight(20);
		
		this.minimize = new StackPane();
		this.minimize.setPrefSize(25, 20);
		this.minimize.getChildren().add(new ImageView(minimize));
		((ImageView) this.minimize.getChildren().get(0)).setFitWidth(20);
		((ImageView) this.minimize.getChildren().get(0)).setFitHeight(20);
		
		this.maximizeBefore = maximizeBefore;
		this.maximize = new StackPane();
		this.maximize.setPrefSize(25, 20);
		this.maximize.getChildren().add(new ImageView(maximizeBefore));
		((ImageView) this.maximize.getChildren().get(0)).setFitWidth(20);
		((ImageView) this.maximize.getChildren().get(0)).setFitHeight(20);
		this.maximizeAfter = maximizeAfter;
		
		this.title = new Label(title);
		
		final Pane space = new Pane();
		HBox.setHgrow(space,Priority.ALWAYS);
		
		titleBar.getChildren().addAll(this.icon, this.title,space,this.minimize, this.maximize, this.close);
		titleBar.setAlignment(Pos.CENTER_RIGHT);
		
		HBox.setMargin(this.icon, new Insets(0,5,0,10)); // top,right, bottom, left
		HBox.setMargin(this.close, new Insets(0,5,0,0));
		
		initalize();
		setCustomizedControlsFunctionality();
	}
    

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	

	/**
	 * create the default maximize button
	 * @return container
	 */
	private StackPane maximiazeButton(){
		StackPane container = new StackPane();
		Rectangle rect = new Rectangle(8,8);
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(Color.BLACK);
		container.setPrefWidth(46);
		container.getChildren().add(rect);	
		
        return container;
	}
	
	

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * To get the Stage of the application for one time only
	 * as well as adding listener to iconifiedProperty()
	 */
	private void initalize(){
	   titleBar.setOnMouseEntered(e->{ // the entire block will be executed only once
		  if(!intialized){
			 // get the stage and assign it to the Stage field
		     stage = ((Stage)titleBar.getScene().getWindow());
		     
		     // add listener toiconifiedProperty()
		     stage.iconifiedProperty().addListener(ee->{
		    	 if(!stage.isIconified()){ 
		    	    stage.setMaximized(true);
		    	    if(fromMax){ // if already maximized
		    	       stage.setWidth(screenWidth);
		    	       stage.setHeight(screenHeight);
					   stage.setX(0);
					   stage.setY(0);
		    	    }
		    	    else{
		    	    	stage.setWidth(stageWidth);
			    	    stage.setHeight(stageHeight);
		    	    	stage.setX(x);
						stage.setY(y);
		    	    }
				    try { // to remove the flash
						 Thread.sleep(10);
					} catch (InterruptedException ex) {	
					     ex.printStackTrace();
					}
					stage.setOpacity(1.0);
		    	 }
		    });
		     
		    	
		    intialized=true;
		 }
	   });	
	}
	
	

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * To add functionality to title bar controls
	 * via event listeners
	 */
	private void setDefaultControlsFunctionality(){
		
		// Double-Click on Title Bar
		titleBar.setOnMouseClicked(e->{
			if(e.getClickCount()==2){
				maximizefunctonality();
			}		
		});
		
		//Maximize Control
		maximizeButton.setOnMouseEntered(e->{// highlight when hover	
			maximizeButton.setBackground(
				new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));
				((Rectangle)maximizeButton.getChildren().get(0)).setFill(Color.LIGHTGRAY);
			    if(maximizeButton.getChildren().size()==2){
			       ((Rectangle)maximizeButton.getChildren().get(1)).setFill(Color.LIGHTGRAY);
			    }
			   
		});
		maximizeButton.setOnMouseExited(e->{ // remove highlight 
			maximizeButton.setBackground(
				new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
			   ((Rectangle)maximizeButton.getChildren().get(0)).setFill(Color.TRANSPARENT);
			   if(maximizeButton.getChildren().size()==2){
			      ((Rectangle)maximizeButton.getChildren().get(1)).setFill(Color.WHITE);
			    }
		});
		maximizeButton.setOnMouseClicked(e->{
			maximizefunctonality();
			
		});
		
		//Close Control
		closeButton.setOnMouseEntered(e->{
			closeButton.setBackground(
				new Background(new BackgroundFill(Color.CRIMSON,null,null)));
				closeButton.setTextFill(Color.WHITE);
		});
		closeButton.setOnMouseExited(e->{
			closeButton.setBackground(
				new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
				closeButton.setTextFill(Color.BLACK);
		});
		
		closeButton.setOnMouseClicked(e->{
			stage.close();	
		});
		
		
		//Minimize Control
		minimizeButton.setOnMouseEntered(e->{
			minimizeButton.setBackground(
				new Background(new BackgroundFill(Color.LIGHTGRAY,null,null)));	
		});
		minimizeButton.setOnMouseExited(e->{
			minimizeButton.setBackground(
				new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
		});
		minimizeButton.setOnMouseClicked(e->{
			if(!stage.isIconified()){ // if it's not minimized
				if(fromMax){ // check if it's already full screen(maximized)
					stage.setOpacity(0.0);
					stage.setIconified(true); // minimize it
				}
				else{ // if it's not -> record the size and position
					stageWidth = stage.getWidth();
					stageHeight = stage.getHeight();
					x = stage.getX();
					y = stage.getY();
					stage.setOpacity(0.0);
					stage.setIconified(true); // minimize it
				}
			}
		});
		
		// to make title bar movable
		titleBar.setOnMousePressed(e->{
			if(stage.getWidth()<screenWidth || stage.getHeight()<screenHeight){
				offsetX = e.getScreenX() - stage.getX();
				offsetY = e.getScreenY() - stage.getY();
			}
		});
		titleBar.setOnMouseDragged(e->{
			if(stage.getWidth()<screenWidth || stage.getHeight()<screenHeight){
				stage.setX(e.getScreenX() - offsetX);
				stage.setY(e.getScreenY() - offsetY);
			}
		});
  
	}
	
	

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
   
	
	private void maximizefunctonality(){
    	Rectangle rect = (Rectangle) maximizeButton.getChildren().get(0);
		if(stage.getWidth()<screenWidth||stage.getHeight()<screenHeight){
			// get the previous size + position
			stageWidth = stage.getWidth();
			stageHeight = stage.getHeight();
			x = stage.getX();
			y = stage.getY();
			// maximize it
			stage.setWidth(screenWidth);
			stage.setHeight(screenHeight);
			stage.centerOnScreen();
			
			// change the maximize button appearance
			rect.setTranslateX(2);
			rect.setTranslateY(-2);
			Rectangle rect1 = new Rectangle(8,8);
			rect1.setFill(Color.WHITE);
			rect1.setStroke(Color.BLACK);
			maximizeButton.getChildren().add(rect1);
			
			fromMax = true;
		}
		else{ // if already maximized -> return to previous size + position
			stage.setWidth(stageWidth);
			stage.setHeight(stageHeight);
			stage.setX(x);
			stage.setY(y);	
			fromMax = false;
			
			// change the maximize button appearance
			rect.setTranslateX(0);
			rect.setTranslateY(0);
			maximizeButton.getChildren().remove(1);
		}
		
    }
  


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    
	
	private void setCustomizedControlsFunctionality(){
		
		//Maximize Control
		maximize.setOnMouseClicked(e->{
			if(stage.getWidth()<screenWidth||stage.getHeight()<screenHeight){
				// get the previous size + position
				stageWidth = stage.getWidth();
				stageHeight = stage.getHeight();
				x = stage.getX();
				y = stage.getY();
				// maximize it
				stage.setWidth(screenWidth);
				stage.setHeight(screenHeight);
				stage.centerOnScreen();
				
				// change the maximize button appearance
				((ImageView) maximize.getChildren().get(0)).setImage(maximizeAfter);
	
				fromMax = true;
			}
			else{ // if already maximized -> return to previous size + position
				stage.setWidth(stageWidth);
				stage.setHeight(stageHeight);
				stage.setX(x);
				stage.setY(y);	
				fromMax = false;
				
				// change the maximize button appearance
				((ImageView) maximize.getChildren().get(0)).setImage(maximizeBefore);
			}
			
		});
		
		close.setOnMouseClicked(e->{
			stage.close();	
		});
		
		
		//Minimize Control
		minimize.setOnMouseClicked(e->{
			if(!stage.isIconified()){ // if it's not minimized
				if(fromMax){ // check if it's already full screen(maximized)
					stage.setOpacity(0.0);
					stage.setIconified(true); // minimize it
				}
				else{ // if it's not -> record the size and position
					stageWidth = stage.getWidth();
					stageHeight = stage.getHeight();
					x = stage.getX();
					y = stage.getY();
					stage.setOpacity(0.0);
					stage.setIconified(true); // minimize it
				}
			}
		});
		
		// to make title bar movable
		titleBar.setOnMousePressed(e->{
			if(stage.getWidth()<screenWidth || stage.getHeight()<screenHeight){
				offsetX = e.getScreenX() - stage.getX();
				offsetY = e.getScreenY() - stage.getY();
			}
		});
		titleBar.setOnMouseDragged(e->{
			if(stage.getWidth()<screenWidth || stage.getHeight()<screenHeight){
				stage.setX(e.getScreenX() - offsetX);
				stage.setY(e.getScreenY() - offsetY);
			}
		});
  
	}
	
	
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	
    /**
     * To change margins/insets to the Title Bar components
     * @param component
     * @param top
     * @param right
     * @param bottom
     * @param left
     */
	public void setInsets(Components component, double top, double right, double bottom, double left){
		switch(component){
		case TITLE:
			HBox.setMargin(title, new Insets(top, right, bottom ,left));
			break;
		case ICON:
			HBox.setMargin(icon, new Insets(top, right, bottom ,left));
			break;
		case CLOSE:
			HBox.setMargin(close, new Insets(top, right, bottom ,left));
			break;
		case MAXIMIZE:
			HBox.setMargin(maximize, new Insets(top, right, bottom ,left));
			break;
		case MINIMIZE:
			HBox.setMargin(minimize, new Insets(top, right, bottom ,left));
			break;
		}
	}
	
   
	
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	public void setControlsSpace(Components component, double width, double height){
		switch(component){
		
		case CLOSE:
			close.setPrefSize(width, height);
			break;
		case MAXIMIZE:
			maximize.setPrefSize(width, height);
			break;
		case MINIMIZE:
			minimize.setPrefSize(width, height);
			break;
		case TITLE:
			//do nothing
			break;
		case ICON:
			// do nothing
			break;
		}
	}
	
	


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    
	
	
	public void addHoverEffect(Components component, Color defaultColor, Color onHover, Cursor cursor){
		switch(component){
		case TITLE:
			title.setOnMouseEntered(e->{
				title.setBackground(new Background(new BackgroundFill(onHover, null, null)));
				title.setCursor(cursor);
			});
			title.setOnMouseExited(e->{
				title.setBackground(new Background(new BackgroundFill(defaultColor, null, null)));
				title.setCursor(Cursor.DEFAULT);
			});
			break;
		
		case CLOSE:
			close.setOnMouseEntered(e->{
				close.setBackground(new Background(new BackgroundFill(onHover, null, null)));
				close.setCursor(cursor);
			});
			close.setOnMouseExited(e->{
				close.setBackground(new Background(new BackgroundFill(defaultColor, null, null)));
				close.setCursor(Cursor.DEFAULT);
			});
			break;
		case MAXIMIZE:
			maximize.setOnMouseEntered(e->{
				maximize.setBackground(new Background(new BackgroundFill(onHover, null, null)));
				maximize.setCursor(cursor);	
			});
			maximize.setOnMouseExited(e->{
				maximize.setBackground(new Background(new BackgroundFill(defaultColor, null, null)));
				maximize.setCursor(Cursor.DEFAULT);
			});
			break;
		case MINIMIZE:
			minimize.setOnMouseEntered(e->{
				minimize.setBackground(new Background(new BackgroundFill(onHover, null, null)));
				minimize.setCursor(cursor);	
			});
			minimize.setOnMouseExited(e->{
				minimize.setBackground(new Background(new BackgroundFill(defaultColor, null, null)));
				minimize.setCursor(Cursor.DEFAULT);
			});
			break;
		case ICON:
			// do nothing
			break;
		}
	}


	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * to change the background color
	 * of the title bar
	 * @param backgroundColor
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		titleBar.setBackground(new Background(new BackgroundFill(backgroundColor,null,null)));
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public HBox getTitleBar() {
		return titleBar;
	}


	public ImageView getIcon() {
		return icon;
	}

	public void setIcon(ImageView icon) {
		this.icon = icon;
	}

	public StackPane getClose() {
		return close;
	}

	public void setClose(StackPane close) {
		this.close = close;
	}

	public StackPane getMinimize() {
		return minimize;
	}

	public void setMinimize(StackPane minimize) {
		this.minimize = minimize;
	}

	public StackPane getMaximize() {
		return maximize;
	}

	public void setMaximize(StackPane maximize) {
		this.maximize = maximize;
	}

	public Label getTitle() {
		return title;
	}

	public void setTitle(Label title) {
		this.title = title;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	
	public StackPane getMaximizeButton(){
		return this.maximizeButton;
	}
}
