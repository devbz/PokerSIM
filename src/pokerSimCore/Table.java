package pokerSimCore;

import lombok.Getter;

/*	Table class will contain only information about the deck, community cards and the stage of the game */

public class Table {
	
	private @Getter Round 		round;
	private			Deck 		deck;
	private @Getter Card 		flop1;
	private @Getter Card 		flop2;
	private @Getter Card 		flop3;
	private @Getter Card 		turn;
	private @Getter Card 		river;
	private @Getter GameStage	stage;
	
	public Table(Round round) {
		this.round = round;
		flop1 = new Card();
		flop2 = new Card();
		flop3 = new Card();
		turn = new Card();
		river = new Card();
		deck = new Deck();
		stage = GameStage.PREFLOP;
	}
	
	public void flop() {
		flop1 = deck.dealCard();
		flop2 = deck.dealCard();
		flop3 = deck.dealCard();
		stage = GameStage.FLOP;
	}
	
	public void turn() {
		turn = deck.dealCard();
		stage = GameStage.TURN;
	}
	
	public void river() {
		river = deck.dealCard();
		stage = GameStage.RIVER;
	}
	
	public Card dealCard() {
		return deck.dealCard();
	}
}
