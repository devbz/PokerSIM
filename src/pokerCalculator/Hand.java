package pokerCalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import pokerSimCore.Card;
import pokerSimCore.CardContainer;
import pokerSimCore.Player;
import pokerSimCore.Table;

public class Hand implements CardContainer {

	private List<Card> allCards = new ArrayList<Card>();
	
	private @Getter HandRanker handRanker;
	private @Getter Player player;
	
	public Hand(Player p, Table t) {
		this.player = p;
		
		allCards.add(p.getCard1());
		allCards.add(p.getCard2());
		allCards.addAll(t.getAllCards());
		
		Collections.sort(allCards, new Card());
		handRanker = new HandRanker(this);
	}
	
	public Hand(Collection<Card> cs) {
		allCards.addAll(cs);
		Collections.sort(allCards, new Card());
		handRanker = new HandRanker(this);
	}
	
	public boolean equals(Hand h2) {
		return handRanker.equals(h2.getHandRanker());
	}
	
	public String toString() {
		return (handRanker.toString());
	}

	@Override
	public ArrayList<Card> getAllCards() {
		return (ArrayList<Card>) allCards;
	}
}
