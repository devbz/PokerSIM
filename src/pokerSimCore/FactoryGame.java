package pokerSimCore;

public class FactoryGame {
	public pokerGUI_JavaFX.View1Presenter presenter;
	public GameController gameController;
	
	public FactoryGame() {
		waitForMainView();		// FX takes its time to initialize in another thread...
		
		presenter = pokerGUI_JavaFX.JavaFXApplication.getPresenter();
		gameController = new GameController();
		
		presenter.setGameController(gameController);
	}
	
	private void waitForMainView() {	// waits to let JavaFX start application and assign variables to static fields
		while(pokerGUI_JavaFX.JavaFXApplication.isInitialized() == false) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
}
