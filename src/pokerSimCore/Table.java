package pokerSimCore;

import java.util.ArrayList;

import pokerCalculator.Calculator;
import lombok.Getter;

/*	Table class will contain only information about the deck, community cards and the stage of the game */

public class Table implements CardContainer {
	
	private @Getter Round 		round;
	private			Deck 		deck;
	private @Getter Card 		flop1;
	private @Getter Card 		flop2;
	private @Getter Card 		flop3;
	private @Getter Card 		turn;
	private @Getter Card 		river;
	private @Getter GameStage	stage;
	
	private @Getter Calculator tableOdds;
	
	public Table(Round round) {
		this.round = round;
		flop1 = new Card();
		flop2 = new Card();
		flop3 = new Card();
		turn = new Card();
		river = new Card();
		deck = new Deck();
		stage = GameStage.PREFLOP;
		tableOdds = new Calculator(this);
	}
	
//	Commented out because of existential crisis:
//	public Table(Table t) {
//		this.flop1 = t.flop1;
//		this.flop2 = t.flop2;
//		this.flop3 = t.flop3;
//		this.turn = t.turn;
//		this.river = t.river;
//	}
	
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
	
	@Override
	public ArrayList<Card> getAllCards() {
		ArrayList<Card> cardList = new ArrayList<>();
		switch(stage) {
		case FINALLY:
		case RIVER:
			cardList.add(river);
		case TURN:
			cardList.add(turn);
		case FLOP:
			cardList.add(flop1);
			cardList.add(flop2);
			cardList.add(flop3);
		case PREFLOP:
			break;
		default:
			break;
		}

		return cardList;
	}
}
