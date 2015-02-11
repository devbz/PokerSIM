package pokerSimCore;

import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Getter;
import lombok.Setter;

public class BetRound {
	private final 	Round round;
	private final 	Game game;
	private final 	int blindValue = Game.BIGBLIND;	// contains value of small blind
	
	private 				boolean blindBet;		// true if the player on the big blind position has not had a betting opportunity yet
	
	private @Getter	@Setter int stake;		// contains current stake (bet)

	private 				Player dealer;
	private @Setter 		Player betSetter;
	private 				Player bigBlind;
	private 				Player better;
	private @Getter 		IntegerProperty minimumRaise;
	
	private 				ArrayList<Player> betPlayers;
	
	public BetRound(Round round) {
		this.round = round;
		this.game = round.getGame();
		this.dealer = game.getDealer();
		if (game.getRoundPlayers().size() > 2 ) {
			this.bigBlind = game.getRoundPlayers().get(2);
		} else {
			this.bigBlind = game.getRoundPlayers().get(0);
		}
		betPlayers = new ArrayList<Player>(round.getRoundPlayers());
		minimumRaise = new SimpleIntegerProperty(0);
		newRound();
	}
	
	public void newRound() {
		game.delay();
		for (Player p: betPlayers) {
			p.prepareBetRound();
		}
		minimumRaise.set(0);
		betSetter = dealer;
		better = dealer;
		while(betPlayers.get(0) != dealer) {
			Collections.rotate(betPlayers, -1);
		}
		stake = 0;
	}

	/* function to play out one betting round, until everyone has had a chance to bet and an agreed bet has been reached */
	public void play() {
		boolean firstRound = true;
		int countTurns = betterSize();
				
		if(round.getStage() == GameStage.PREFLOP)	{	blinds();	}

		while( (!betAgreed() 	||				// there is no agreement on the bet. PLEASE NOTE this is also where the next better is set!!
				blindBet 		||				// the blinds still need to bet
				firstRound)						// this is still the first round of betting and stakes are still 0 (i.e. betAgreed() == true)
				&&
				Game.isRunning()	)	{		// guard against infinite loop if gui is dead
			
			if (better.isInPlay()) {	betChoice(better);	}
			
			// check loop control conditions:
			// The only case in which the big blind position is allowed
			// to bet again is when everybody checked his original blind bet
			blindBet = blindBet ? !better.equals(bigBlind) : false;
			firstRound = --countTurns > 0;
		}
		
		// End Round if there is only one better left
		if(checkLastManStanding()) { round.endRound(); }
	}
	
	private int betterSize() {
		int size = 0;
		for (Player p: betPlayers) {
			if (p.isInPlay()) {	size++;	}
		}
		return size;
	}

	private void nextBetter() {
		do {
			Collections.rotate(betPlayers, -1);
			better = betPlayers.get(0);
		} while (!better.isInPlay());
	}

	private void betChoice(Player betPlayer) {
		if (checkLastManStanding()	||
				lastManBetting()	)	{
			betPlayer.call(round);
		}
	// User choice:
		else if (betPlayer.getName() == game.getPlayers()[0].getName()) //TODO make better
		{	game.playerChoice();	}
		
	// Computer choice:
		else
		{	betPlayer.makeDecision(round, stake);	}
		
	}

	private boolean lastManBetting() {
		boolean notRestAllIn = false;
		for (Player p: betPlayers) {
			if (!(p == better)	&&
					!(p.isCapped())) {
				notRestAllIn = true;
			}
		}
		return !notRestAllIn;
	}

	public void blinds() {
		dealer.setPosition("Dealer");
		
		nextBetter();
		better.payUp(blindValue, round);
		better.setLastAction("Posted small blind of " + blindValue);
		better.setPosition("Small Blind");
		
		nextBetter();
		better.payUp(blindValue * 2, round);
		better.setLastAction("Posted big blind of " + (blindValue * 2));
		better.setPosition("Big Blind");
		
		blindBet = true;
		stake = blindValue * 2;
		minimumRaise.set(blindValue * 2);
	}
	
	public boolean betAgreed() {
		nextBetter();

		// 'Capping' the potential all in bet setter:
		// This seems the best place to do this, as it is the only place visited once more right before the betRound ends
		if (better.isAllIn() 	&&
			!better.isCapped()	)	{
			
			int numCallers = 0;
			for (Player p: betPlayers) {
				if (p.isInPlay()) { numCallers++;	}
			}
			better.capWin(numCallers);
		}
		
		// Finally, the real check to see if there is an agreed bet:
		int bet = -1;
		for (Player p: betPlayers) {
			if ( p.isInPlay()	&&
				 !p.isCapped() )	{
				if (bet == -1) {	bet = p.getBet();	}
				else if (p.getBet() != bet) {	return false;	}
			}
		}
		return true;
	}
	
	public boolean checkLastManStanding() {
		int sizeOfGame = betPlayers.size();
		for(Player p: betPlayers) {
			if(!p.isInPlay()) {	sizeOfGame--; }
		}
		return (sizeOfGame == 1 ? true : false);
	}
}
