package pokerSimCore;

public class TestGame {

	public static void main(String[] args) {
		Thread appLauncher = new Thread(new Runnable() {
			@Override
			public void run() {
				javafx.application.Application.launch(pokerGUI_JavaFX.JavaFXApplication.class);
			}
		});
		appLauncher.setName("Java FX myLauncher");
		appLauncher.start();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block Sleep
			e1.printStackTrace();
		}
		
		FactoryGame factory = new FactoryGame();
		
		/* Create a viewUpdater Thread that updates the JavaFX view every 100ms
		 * by PULLING data from the presenter */
		Thread viewUpdater = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!factory.gameController.isInitialized()) {
					try {
						Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block Sleep
							e.printStackTrace();
						}
				}
				
				while(pokerGUI_JavaFX.JavaFXApplication.isInitialized()) {
					try {
					Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block Sleep
						e.printStackTrace();
					}
					factory.presenter.updateView();
				}
			}
		});
		viewUpdater.setName("View Updater");
		viewUpdater.start();
	
		/* Start the game! */
		factory.gameController.run();
		return;
	}

}
