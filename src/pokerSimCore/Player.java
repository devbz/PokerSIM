package pokerSimCore;

import java.util.Random;

import lombok.Getter;
import lombok.Setter;

public class Player {	
	private Game game;
	private @Getter 		int 		cash;				// holds current cash value
	private @Getter @Setter Card 		card1;				// holds first card
	private @Getter @Setter Card 		card2;				// holds second card
	private @Getter @Setter String 		lastAction;			// action message
	private 		 		int 		currentBet;			// holds current agreed bet
	private @Getter @Setter String 		position;			// position message	
	private @Getter @Setter String 		name;				// player name
	private @Getter @Setter int			maxPot;				// max pot won after all in
	
	private @Getter @Setter boolean 	folded;
	private @Getter @Setter boolean 	allIn;
	private @Getter @Setter boolean 	bankRupt;
	private @Getter			boolean 	capped;				// true if maxPot has been set after betround evaluation
	
	public Player() {
		card1 = new Card();
		card2 = new Card();
	}
	
	public Player(Game game) {
		this.game = game;
		cash = Game.BEGINCASH;
		card1 = new Card();
		card2 = new Card();
		currentBet = 0;
		folded = false;
		allIn = false;
		capped = false;
	}
	
	/* Label Player controls */
	/* Low level control methods just sets internal player fields and relays
	 * bet to Round to further handle. Doesn't take into account rules for
	 * betting etc! */
	public void call(Round round) {
		if(round.getStake() == this.currentBet) {
			lastAction = "Checked";
			return;
		}
		
		int bet = round.getStake() - this.currentBet;
		
		if(bet >= cash) {
			allIn(round); 
		} else {
			payUp(bet, round);
			lastAction = "Called " + round.getStake();
		}
	}
	
	public void bet(int stake, Round round) {
		// Betting 0 == checking
		if(stake == 0) {
			lastAction = "Checked";
			return;
		}
		
		int toBet = stake - this.currentBet;
		
		// Betting no more than the actual stakes == calling
		if(toBet == 0) {
			call(round);
			return;
		}
		
		// Trying to bet more than I have results in Allin
		if(toBet >= cash) {
			allIn(round);
			return;
		} else {
		// Finally this is a real bet:
			payUp(toBet, round);
			round.newBet(this, stake);
			lastAction = "Bet " + stake;
			game.postMessage(name + " bet/raised to " + stake);
		}
	}
	
	public void allIn(Round round) {
		payUp(cash, round);
		//TODO make split pot options
		//TODO make it so that all ins don't overwrite if equal
		maxPot = round.getPot();
		capped = false;
		round.newBet(this, currentBet);
		allIn = true;
		lastAction = "All in!";
		game.postMessage(name + " went all in!");
	}
	
	public void fold(BetRound r) {
		folded = true;
		lastAction = "Folded";
		game.postMessage(name + " folded.");
	}
	/* End Player controls */
	
	/* low level payUp() requires that any checks on that stake variable 
	 * have already been done before the player controls */
	public void payUp(int toBet, Round round) {
		cash -= (toBet);
		this.currentBet += toBet;
		round.receiveBet(toBet);
	}
	
	/* automated very simple decision making */
	public void makeDecision(Round round, int stake) {
		if(folded || allIn || bankRupt){
			return;
		}	else if(cash == 0) {
			bankRupt = true;
			return;
		}
		
		game.delay();									// simulate 'thinking time'
		
		Random random = new Random();
		double potOdds = random.nextInt(100);			// simulates calculated pot odds in %
		final int FOLDCHANCE = 10;
		final int ALLINCHANCE = 1;
		final int RAISECHANCE = 5;
		
		if(potOdds < FOLDCHANCE) {
			fold(round.getBetRound());
		} else if(potOdds > 100 - ALLINCHANCE) {
			allIn(round);
		} else if(potOdds < 100 - RAISECHANCE) {
			call(round);
		} else {
			int raise = (int) (round.getPot() * (potOdds / (100.0 - RAISECHANCE)));
			if(round.getStake() == 0) { payUp(stake, round); }	// required for placing of blind before raise
			bet(round.getStake() + raise, round);
		}
	}
	
	/* Label Resetters */
	public void resetBet() {
		currentBet = 0;
		capped = false;
	}
	
	public void resetLA() {
		lastAction = "";
	}
	
	public void prepareBetRound() {
		if (this.isInPlay()	&&
			!this.allIn) {
			resetLA();
			resetBet();
		}
		if (this.isInPlay()	&&
			this.allIn)	{
			resetBet();
		}
	}
	
	public void reset() {
		setPosition("");
		resetBet();
		resetLA();
		folded = false;
		allIn = false;
		card1 = new Card();
		card2 = new Card();
		capped = false;
	}
	/* End Label Resetters */
	
	public void win(int wonAmt) {
		cash += wonAmt;
		lastAction = "Won a pot of " + wonAmt;
	}
	
	public boolean isInPlay() {
		return (!folded && !bankRupt);
	}
	
	public int getBet() {
		return currentBet;
	}
	
	public String toString() {
		return name;
	}

	public void capWin(int playersCalled) {
		if (!capped) {
			maxPot = currentBet * playersCalled;
			capped = true;
		}
	}
}
