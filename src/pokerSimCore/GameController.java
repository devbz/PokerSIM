package pokerSimCore;

import lombok.Getter;
import pokerGUI_JavaFX.ViewData;

public class GameController {
	
	/* @SuppressWarnings("unused")	// Only used outside of class to enable callback methods
	private View1Presenter presenter;
		public void setPresenter(View1Presenter presenter) {
			this.presenter = presenter;
		}
	*/
	
	private Game game;
	private @Getter boolean initialized;
	
	public GameController() {
		game = new Game();
		initialized = false;
	}

	public void play() { game.unPause(); }
	 
	public void call() {
		game.call();
		game.unPause();
	}
	
	public void bet(String amount) {
		game.bet(amount);
		game.unPause();
	}
	
	public void fold() {
		game.fold();
		game.unPause();
	}
	
	public ViewData getViewData() {
		return game.makeViewData();
	}
	
	public void run() {
		initialized = true;
		game.play();
	}
	
	public void end() {
		game.endGame();
	}
}
