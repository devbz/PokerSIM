package pokerCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import pokerSimCore.Card;
import pokerSimCore.Player;
import pokerSimCore.Table;

public @Data class Hand {
	
	private Card pocketCard1;
	private Card pocketCard2;
	private Card flopCard1;
	private Card flopCard2;
	private Card flopCard3;
	private Card turnCard;
	private Card riverCard;
	
	private List<Card> allCards = new ArrayList<Card>();
	
	private HandRanker handRanker;
	private Player player;
	
	public Hand(Player p, Table t) {
		this.player = p;
		
		pocketCard1 = p.getCard1();
		allCards.add(pocketCard1);
		pocketCard2 = p.getCard2();
		allCards.add(pocketCard2);
		flopCard1 = t.getFlop1();
		allCards.add(flopCard1);
		flopCard2 = t.getFlop2();
		allCards.add(flopCard2);
		flopCard3 = t.getFlop3();
		allCards.add(flopCard3);
		turnCard = t.getTurn();
		allCards.add(turnCard);
		riverCard = t.getRiver();
		allCards.add(riverCard);
		
		Collections.sort(allCards, new Card());
		
		handRanker = new HandRanker(this);
	}
	
	public boolean equals(Hand h2) {
		return handRanker.equals(h2.getHandRanker());
	}
	
	public String toString() {
		return (handRanker.toString());
	}
}
