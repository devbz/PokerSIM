package pokerGUI_JavaFX;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class JavaFXApplication extends Application {
	
	private static View1Presenter presenter;
	private static boolean initialized = false;
	private static Stage mainStage;
	
	public static View1Presenter getPresenter() {
		return presenter;
	}

	public static boolean isInitialized() {
		return initialized;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			mainStage = primaryStage;
			FXMLLoader fxmlLoader = new FXMLLoader();
			AnchorPane root = fxmlLoader.load(getClass().getResource("View2.fxml").openStream());
			presenter = new View1Presenter(fxmlLoader.getController());
			initialized = true;
			
			Scene scene = new Scene(root,0,0);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent arg0) {
					presenter.initGameEnd();	// ends game loop
					initialized = false;		// ends waiting loops
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	public static Popup createPopup(final String message) {
	    final Popup popup = new Popup();
	    popup.setAutoFix(true);
	    popup.setAutoHide(true);
	    popup.setHideOnEscape(true);
	    Label label = new Label(message);
	    label.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent e) {
	            popup.hide();
	        }
	    });
	    // label.getStylesheets().add("/css/styles.css");
	    // label.getStyleClass().add("popup");
	    popup.getContent().add(label);
	    return popup;
	}

	public static void showPopupMessage(final String message) {
	    final Popup popup = createPopup(message);
	    popup.setOnShowing(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent e) {
	            popup.setX(mainStage.getX() + mainStage.getWidth()/2 - popup.getWidth()/2);
	            popup.setY(mainStage.getY() + mainStage.getHeight()/2 - popup.getHeight()/2);
	        }
	    });        
	    popup.show(mainStage);
	}
	
	public void Main(String[] args) {
		launch(args);
	}
}
