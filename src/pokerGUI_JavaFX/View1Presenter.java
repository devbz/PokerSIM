package pokerGUI_JavaFX;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import pokerSimCore.GameController;

public class View1Presenter {
	private View1Controller controller;
	private GameController gameController;
	
	public View1Presenter(View1Controller controller) {
		this.controller = controller;
		controller.setPresenter(this);
	}
	
	public void bet(String amount) {
		gameController.bet(amount);
	}
	
	public void fold() {
		gameController.fold();
	}
	
	public void call() {
		gameController.call();
	}
	
	public void play() {
		gameController.play();
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}
	
	public void calcOdds() {
		
	}
	
	public void updateView() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				controller.update(gameController.getViewData());
			}
		});
	}
	
	public void initGameEnd() {
		gameController.end();
	}

	public HashMap<String, String> calculate() {
		return gameController.calculate();
	}
	
	public ArrayList<OddsOutModel> calc2() {
		return gameController.calc2();
	}
}
