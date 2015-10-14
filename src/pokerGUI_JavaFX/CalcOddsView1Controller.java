package pokerGUI_JavaFX;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class CalcOddsView1Controller {
	Stage dialogStage;

    @FXML
    private ListView<String> listBoxCalcOdds = new ListView<>();
    
    private ObservableList<String> listBoxInfo;

	@FXML
    void initialize() {
		listBoxInfo = FXCollections.observableArrayList();
        assert listBoxCalcOdds != null : "fx:id=\"listBoxCalcOdds\" was not injected: check your FXML file 'CalculatedOddsView.fxml'.";
    }
	
	
	/** setInfo only formats the output conforming to the view, and attaches the information to the view.
	 * @param A Map of the different ranks of combinations and the corresponding outs */
	void setInfo(HashMap<String, String> info) {
		for(String s: info.keySet()) {
			listBoxInfo.add(String.format("%1$-30s", s) + "\t" + String.format("%1$6s", info.get(s)));
		}
		listBoxCalcOdds.setItems(listBoxInfo);
	}
}