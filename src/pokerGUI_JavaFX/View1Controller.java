package pokerGUI_JavaFX;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import pokerSimCore.Game;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class View1Controller implements javafx.fxml.Initializable {

	private static View1Presenter presenter;
	private MessageKeeper messageKeeper = new MessageKeeper();
	
    @FXML
    private TextField toBet;

    @FXML
    private Label p2la;

    @FXML
    private Label p1la;

    @FXML
    private Label p4card2;

    @FXML
    private Label p4la;

    @FXML
    private Label p1cash;

    @FXML
    private Label p3la;

    @FXML
    private Label p4card1;

    @FXML
    private Label turn;

    @FXML
    private Label p3cash;

    @FXML
    private Label p2card2;

    @FXML
    private Label p2card1;

    @FXML
    private Label pot;

    @FXML
    private Label toCall;

    @FXML
    private Label p2cash;

    @FXML
    private Label p1card1;

    @FXML
    private Label p1card2;

    @FXML
    private Label flop1;

    @FXML
    private Label flop2;

    @FXML
    private Label river;

    @FXML
    private Label flop3;

    @FXML
    private Label p4cash;

    @FXML
    private Label p3card2;

    @FXML
    private Label p3card1;
    
    @FXML
    private Button btnBet;
    
    @FXML
    private Button btnFold;
    
    @FXML
    private Button btnCall;
    
    @FXML
    private Button btnPlay;
    
    @FXML
    private Button btnCalcOdds;
    
    @FXML
    private Label p1position;
    
    @FXML
    private Label p2position;
    
    @FXML
    private Label p3position;
    
    @FXML
    private Label p4position;
    
    @FXML
    private TitledPane player1Name;

    @FXML
    private TitledPane player2Name;

    @FXML
    private TitledPane player3Name;
    
    @FXML
    private TitledPane player4Name;

    @FXML
    private Label gameMessage;
        
    @FXML
    private ListView<String> gameMessageBox = new ListView<>();
    
    private ObservableList<String> messages = FXCollections.observableArrayList ();

	public void addHistoryEntry(List<String> list) {
    	Collections.reverse(list);
    	messages.addAll(0, list);
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		gameMessageBox.setItems(messages);

		btnBet.setOnAction(new EventHandler() {
			@Override
			public void handle(Event e) {
				presenter.bet(toBet.getText());
			}
		});
		
		btnFold.setOnAction(new EventHandler() {
			@Override
			public void handle(Event e) {
				presenter.fold();
			}
		});
		
		btnCall.setOnAction(new EventHandler() {
			@Override
			public void handle(Event e) {
				presenter.call();
			}
		});
		
		btnPlay.setOnAction(new EventHandler() {
			@Override
			public void handle(Event e) {
				presenter.play();
			}
		});
		
		btnCalcOdds.setOnAction(new EventHandler() {
			@Override
			public void handle(Event e) {
				showCalcOdds();
			}
		});
	}
	
	public void setPresenter(View1Presenter presenter) {
		View1Controller.presenter = presenter;
	}
	
	public void update(ViewData viewData) {
		this.player1Name.setText(viewData.players.get(0).name);
		this.player2Name.setText(viewData.players.get(1).name);
		this.player3Name.setText(viewData.players.get(2).name);
		this.player4Name.setText(viewData.players.get(3).name);
		this.p1cash.setText(String.valueOf(viewData.players.get(0).cash));
		this.p2cash.setText(String.valueOf(viewData.players.get(1).cash));
		this.p3cash.setText(String.valueOf(viewData.players.get(2).cash));
		this.p4cash.setText(String.valueOf(viewData.players.get(3).cash));
		this.p1la.setText(viewData.players.get(0).lastAction);
		this.p2la.setText(viewData.players.get(1).lastAction);
		this.p3la.setText(viewData.players.get(2).lastAction);
		this.p4la.setText(viewData.players.get(3).lastAction);
		this.p1position.setText(viewData.players.get(0).position);
		this.p2position.setText(viewData.players.get(1).position);
		this.p3position.setText(viewData.players.get(2).position);
		this.p4position.setText(viewData.players.get(3).position);
		this.p1card1.setText(viewData.players.get(0).card1Name);
		this.p1card2.setText(viewData.players.get(0).card2Name);
		this.p2card1.setText(viewData.players.get(1).card1Name);
		this.p2card2.setText(viewData.players.get(1).card2Name);
		this.p3card1.setText(viewData.players.get(2).card1Name);
		this.p3card2.setText(viewData.players.get(2).card2Name);
		this.p4card1.setText(viewData.players.get(3).card1Name);
		this.p4card2.setText(viewData.players.get(3).card2Name);
		
		this.pot.setText(String.valueOf(viewData.pot));
		//this.toBet.setText(String.valueOf(viewData.minRaise));
		this.toCall.setText(String.valueOf(viewData.toCall));
		this.flop1.setText(viewData.flopCard1);
		this.flop2.setText(viewData.flopCard2);
		this.flop3.setText(viewData.flopCard3);
		this.turn.setText(viewData.turnCard);
		this.river.setText(viewData.riverCard);
		
		this.gameMessage.setText(viewData.gameMessage);
		this.messageKeeper.push(viewData.messageList); 
		addHistoryEntry(messageKeeper.pull());
		
		if(viewData.errorMessage.isError()) {
			JavaFXApplication.showPopupMessage(viewData.errorMessage.getMessage());
		}
	}

	public boolean showCalcOdds() {
		try {
		    // Load the fxml file and create a new stage for the popup
			// FXMLLoader loader = new FXMLLoader(getClass().getResource("OddsCalcDialog.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("OddsCalcDialog2.fxml"));
			AnchorPane page = loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Calculated Odds");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(JavaFXApplication.getMainStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			OddsCalcDialog2Controller controller = loader.getController();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					controller.setInfo(presenter.calc2());
				}
			});
			
			// Show the dialog and wait until the user closes it
		    dialogStage.showAndWait();
		
		    return true;
		
	  	} catch (IOException e) {
		    // Exception gets thrown if the fxml file could not be loaded
		    e.printStackTrace();
		    return false;
	  	}
	}
	
	@SuppressWarnings("unchecked")
	public void makeBindings(Game game) {
		this.btnBet.textProperty().bind((ObservableValue<? extends String>) game.viewBindables.minimumRaise);
	}
	
}
