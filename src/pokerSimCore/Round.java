package pokerSimCore;

import java.util.ArrayList;
import java.util.Collections;

import lombok.Getter;
import pokerCalculator.Hand;
import pokerCalculator.HandRanker;
import pokerCalculator.HandRankerByCardRank;
import pokerCalculator.HandRankerByCombination;
import pokerCalculator.HandRankerByKickers;
import pokerCalculator.HandRankerComparator;

public class Round {
	
	private @Getter Game game;
	private @Getter ArrayList<Player> roundPlayers;
	private @Getter BetRound betRound;
	private @Getter GameStage stage;

	private @Getter Table table;
	private @Getter int pot;

	public Round(Game game) {
		this.game = game;
		table = new Table(this);
		
		roundPlayers = new ArrayList<Player>(game.getRoundPlayers());
		betRound = new BetRound(this);
		pot = 0;
		
		stage = GameStage.PREFLOP;
	}
	
	/* Main functionality */
	public void play() {
		while(Game.isRunning()) {
			switch(stage) {
			case PREFLOP:
				
				// deal cards to players in hand:
				for (Player p: roundPlayers) {
					p.setCard1(table.dealCard());
					p.setCard2(table.dealCard());
				}
				betRound.play();
				if(betRound.checkLastManStanding()) {
					stage = GameStage.FINALLY;
				} else {
					table.flop();
					stage = GameStage.FLOP;
				}
				break;
			case FLOP:
				betRound = new BetRound(this);
				betRound.play();
				if(betRound.checkLastManStanding()) {
					stage = GameStage.FINALLY;
				} else {
					table.turn();
					stage = GameStage.TURN;
				}
				break;
			case TURN:
				betRound = new BetRound(this);
				betRound.play();
				if(betRound.checkLastManStanding()) {
					stage = GameStage.FINALLY;
				} else {
					table.river();
					stage = GameStage.RIVER;
				}
				break;
			case RIVER:
				betRound = new BetRound(this);
				betRound.play();
				stage = GameStage.FINALLY;
				break;
			case FINALLY:
				winnersFinder();
				
				game.delay();
				
				// reset all decisions and bets, remove bankrupt players from game:
				for(Player p: game.getPlayers()) {
					if(p.getCash() == 0) {
						game.getRoundPlayers().remove(p);
						roundPlayers = new ArrayList<Player>(game.getRoundPlayers());
					}
					p.reset();
				}
				
				HandRanker.getSplitters().clear();
				game.nextDealer();
				betRound = new BetRound(this);

				return;
			default:
				break;
			}
		}
	}
	
	private void winnersFinder() {
		game.delay();
	
		ArrayList<HandRanker> rankers = new ArrayList<>();
		for(Player p: roundPlayers) {
			if(!p.isFolded()) {
				Hand h = new Hand(p, table);
				rankers.add(h.getHandRanker());
			}
		}
		
		// Sort from highest to lowest hand rank:
		HandRankerComparator holdemComparator = new HandRankerComparator(
				new HandRankerByCombination(),
				new HandRankerByCardRank(),
				new HandRankerByKickers());
		Collections.sort(rankers, holdemComparator);
		
		while (!(pot == 0)) {
			HandRanker winner = rankers.get(0);
			rankers.remove(winner);
			ArrayList<HandRanker> winners = new ArrayList<>();
			winners.add(winner);
			for (HandRanker checkHr : rankers) {
				if (winner.equals(checkHr)) {
					winners.add(checkHr);
					rankers.remove(checkHr);
				}
				break;
			}
			if (winners.size() > 1) {	splitPot(winners);	}
			else {	winPot(winner.getHand());	}
		}
		
		ArrayList<Hand> winningHands = new ArrayList<>();	//TODO remove this and the Splitters property of HandRanker!
		for (HandRanker hr: HandRanker.getSplitters()) {	winningHands.add(hr.getHand());	}
	}
	
	private void splitPot(ArrayList<HandRanker> winners) {
		int splittedPot = pot / winners.size();
		int winnersCapped = 0;
		
		for (HandRanker h: winners) {
			if (h.getHand().getPlayer().getMaxPot() <= splittedPot) {
				Player p = h.getHand().getPlayer();
				p.win(splittedPot);
				pot -= splittedPot;
				game.postMessage(p.getName() + " wins a pot of "
						+ splittedPot + " with "
						+ h.toString());
			} else {
				winPot(h.getHand());
				// Adjust (up) the splitted pot size to account for the capped win: 
				if (winners.size() - winnersCapped != 0) {	// guard /0 -> will be the last case in this split anyway
					splittedPot = pot / (winners.size() - winnersCapped++);
				}
			}
		}

		// Give the resting chips to the winner closest to the dealer:
		if (	pot != 0	&&
				pot < winners.size()) {
			int posClosestToDealer = game.getRoundPlayers().size() + 1;
			Player closestToDealer = null;
			for (HandRanker h: winners) {
				if (game.getRoundPlayers().indexOf(h.getHand().getPlayer()) < posClosestToDealer) {
					posClosestToDealer = game.getRoundPlayers().indexOf(h.getHand().getPlayer());
					closestToDealer = h.getHand().getPlayer();
				}
			}
			closestToDealer.win(pot);
			pot = 0;
		}
	}
	
	private void winPot(Hand h) {
		Player p = h.getPlayer();
		if (p.isCapped()) {
			p.win(p.getMaxPot());
			game.postMessage(p.getName() + " wins a pot of " + p.getMaxPot() + " with "
					+ h.toString());
			pot -= p.getMaxPot();
		} else {
			p.win(pot);
			game.postMessage(p.getName() + " wins a pot of " + pot + " with "
					+ h.toString());
			pot = 0;
		}
	}
	
	public void endRound()	{	stage = GameStage.FINALLY;	}
		
	public int getStake() {	
		if (betRound != null) {
			return betRound.getStake();
		} else {
			return 0;
		}
	}
		
	public void receiveBet(int bet) {
		this.pot += bet;
	}
	
	public void newBet(Player player, int stake) {
		betRound.getMinimumRaise().set(stake);
		betRound.setBetSetter(player);
		betRound.setStake(stake);
	}
}
