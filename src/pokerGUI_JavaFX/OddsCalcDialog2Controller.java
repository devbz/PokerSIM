package pokerGUI_JavaFX;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import pokerCalculator.CombinationRanks;
import pokerSimCore.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class OddsCalcDialog2Controller {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableColumn<OddsOutModel, Double> clmChance;
    @FXML
    private TitledPane title;
    @FXML
    private TableColumn<OddsOutModel, CombinationRanks> clmDraw;
    @FXML
    private TableColumn<OddsOutModel, String> clmCards;
    @FXML
    private TableView<OddsOutModel> tblOdds;
    
    private ObservableList<OddsOutModel> data;

	@FXML
    void initialize() {
		assert clmChance != null : "fx:id=\"clmChance\" was not injected: check your FXML file 'OddsCalcDialog2.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'OddsCalcDialog2.fxml'.";
        assert clmDraw != null : "fx:id=\"clmDraw\" was not injected: check your FXML file 'OddsCalcDialog2.fxml'.";
        assert clmCards != null : "fx:id=\"clmCards\" was not injected: check your FXML file 'OddsCalcDialog2.fxml'.";
        assert tblOdds != null : "fx:id=\"tblOdds\" was not injected: check your FXML file 'OddsCalcDialog2.fxml'.";
	        
    	data = FXCollections.observableArrayList();
//        sortedData = new SortedList<>(data);
//        sortedData.comparatorProperty().bind(tblOdds.comparatorProperty());
//        tblOdds.setItems(sortedData);
    	
    	clmDraw.setCellValueFactory(new PropertyValueFactory<OddsOutModel, CombinationRanks>("draw"));
    	clmChance.setCellValueFactory(new PropertyValueFactory<OddsOutModel, Double>("chance"));
    	// Format the cells in this row so that they display a nice percentage:
    	clmChance.setCellFactory(new Callback<TableColumn<OddsOutModel, Double>, TableCell<OddsOutModel, Double>>() {
    		public TableCell<OddsOutModel, Double> call(TableColumn<OddsOutModel, Double> p) {
                return new TableCell<OddsOutModel, Double>() {
                	 protected void updateItem(Double item, boolean empty) {
             	        super.updateItem(item, empty);

             	        // If the row is not empty but the Double-value is null,
             	        // display 0%:
             	        if (!empty && null == item) {
             	            item = new Double(0.0d);
             	        }

             	        // Set the format of the displayed text:
             	        NumberFormat twoDecPercentage = NumberFormat.getPercentInstance();
             	        twoDecPercentage.setMaximumFractionDigits(2);
             	        setText(item == null ? "" : twoDecPercentage.format(item));
                	 };
                };
    		}
    	});
    	
    	//TODO re-sort clmChance
    	
    	clmCards.setCellValueFactory(new PropertyValueFactory<OddsOutModel, String>("cards"));
    	clmCards.setCellFactory(cell -> {
    		return new TableCell<OddsOutModel, String>() {
    			@Override
    			protected void updateItem(String item, boolean empty) {
    				super.updateItem(item, empty);
    				setText(item);
    				setTooltip(new Tooltip("Make sense here"));
    			}
    		};
    	});
//    	clmCards.setCellFactory(new Callback<TableColumn<OddsOutModel, String>, TableCell<OddsOutModel, String>>() {
//    		//TODO make a mouseover popup here:
//			@Override
//			public TableCell<OddsOutModel, String> call(
//					TableColumn<OddsOutModel, String> arg0) {
//				return new TableCell<OddsOutModel, String>() {
//					protected void updateItem(String item, boolean empty) {
//						super.updateItem(item, empty);
//						if (this.getTableRow() != null && item != null) {
//							//TODO make tooltip here
//						} else {
//		                    
//		                }
//					};
//				};
//			}
//    	});
//    	
//    	clmCards.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
//    		event.
//    	});
    	
    	
    	tblOdds.setItems(data);
    }
    
    void setInfo(ArrayList<OddsOutModel> inputData) {
    	data.addAll(inputData);
    }
}
