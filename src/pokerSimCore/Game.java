package pokerSimCore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import pokerGUI_JavaFX.ViewData;
import lombok.Getter;
import lombok.Setter;

public class Game {
	
	private Random r = new Random();
	
	public final int DELAYTIME = 1000;			// simulated computer 'think time' in ms
	public final static int BIGBLIND = 4;		// big blind value
	public final static int BEGINCASH = 250;	// starting Cash value
	public final static int NUMPLAYERS = 4;		// amount of players // TODO make this come from ini file
	private @Getter Player[] players; 			// flat array of players
	private @Getter ArrayList<Player> roundPlayers;		// players in round
	
	private Round round;						// contains all variables for current playing round
	// private @Getter int dealerPosition;		// dealer position stored outside of subclasses
	private @Getter Player dealer;
	private @Setter String gameMessage;			// game action message
	
	private @Getter static boolean running = true;	// game wait loops terminator	
		public void endGame() {
			running = false;
		}
		
	private boolean paused = true;				// used to wait for interaction
		public void pause() { paused = true; }
		public void unPause() { paused = false; }
		
	private List<String> messageList;			// contains game message history
		public void postMessage(String message) { messageList.add(message); }
		
	private ErrorMessage errorMessage;
	
	public ViewBindables viewBindables;
	
	public Game() {
		viewBindables = new ViewBindables();
		errorMessage = new ErrorMessage();
		messageList = new ArrayList<String>();
		postMessage("Game started!");
		players = new Player[NUMPLAYERS];
		roundPlayers = new ArrayList<Player>();
		for(int i = 0; i < players.length; i++){
			players[i] = new Player(this);
			roundPlayers.add(players[i]);
			if(i == 0) {
				players[i].setName("Player");
			} else {
				players[i].setName("Computer " + i);
			}
		}
		
		dealer = players[r.nextInt(NUMPLAYERS)];
		dealer.setPosition("Dealer");
		
		while(!roundPlayers.get(0).equals(dealer)) {	Collections.rotate(roundPlayers, 1);	}
		postMessage("Hello player!");
		round = new Round(this);
		makeViewBindings();
	}
	
	/* Internal Game Controls */
	public void play() {
		//TODO create end game conditions
		while (	running	&&
				roundPlayers.size() > 1	) {
			round.play();
			round = new Round(this);
		}
		return;
	}
	
	public void nextDealer() {
		do {
			Collections.rotate(roundPlayers, -1);
			dealer = roundPlayers.get(0);
		} while (!dealer.isInPlay());
	}
	
	public void waitForUser() {
		gameMessage = "Waiting for your move";
		pause();
		while (paused && running) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block Sleep
				e.printStackTrace();
			}
		}
		gameMessage = "";
	}
	
	public void delay() {
		try {
			Thread.sleep(DELAYTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public boolean isPlayer(Player p) {
		return players[0].equals(p);
	}
	/* End Internal Game Controls */
	
	/* Player controls: */
	public void playerChoice() {
		if( (!players[0].isFolded())	&&
			(!players[0].isAllIn())		&&
			(!players[0].isBankRupt())		)
		{	
			waitForUser();	
		}
	}
	
	public void call() {
		players[0].call(round);
		unPause();
		}
	
	public void fold() {
		players[0].fold(round.getBetRound());
		unPause();
		}
	
	public void bet(String amount) {
		int i = Integer.parseInt(amount);
		players[0].bet(i, round);
		//players[0].setRaise(i);
		unPause();
	}
	/* End Player controls*/
	
	/* Creates UI artifact: */
	public ViewData makeViewData() {
		ViewData viewData = new ViewData();
		
		for(int i = 0; i < NUMPLAYERS; i++) {
			viewData.players.get(i).name = players[i].getName();
			viewData.players.get(i).cash = players[i].getCash();
			viewData.players.get(i).lastAction = players[i].getLastAction();
			viewData.players.get(i).position = players[i].getPosition();
			viewData.players.get(i).card1Name = players[i].getCard1().toString();
			viewData.players.get(i).card2Name = players[i].getCard2().toString();
		}
		
		viewData.pot 		= this.round.getPot();
		viewData.minRaise	= this.round.getBetRound().getMinimumRaise().getValue();
		viewData.toCall 	= this.round.getStake() - players[0].getBet();
		viewData.flopCard1	= this.round.getTable().getFlop1().toString();
		viewData.flopCard2	= this.round.getTable().getFlop2().toString();
		viewData.flopCard3	= this.round.getTable().getFlop3().toString();
		viewData.turnCard	= this.round.getTable().getTurn().toString();
		viewData.riverCard	= this.round.getTable().getRiver().toString();
		viewData.gameMessage = this.gameMessage;
		viewData.messageList = this.messageList;
		viewData.errorMessage = this.errorMessage;

		return viewData;
	}
	
	public void makeViewBindings() {
		viewBindables.minimumRaise = this.round.getBetRound().getMinimumRaise();
	}
	
	// TODO make this a direct UI call:
	public void showError(String string) {
		errorMessage.setError(true);
		errorMessage.setMessage(string);
	}
	
	public ErrorMessage getError() {
		if (errorMessage.isError()) {
			ErrorMessage wrong = errorMessage;
			errorMessage = new ErrorMessage();
			return wrong;
		} else { return null; }
	}
}
