package pokerCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import pokerSimCore.Card;
import pokerSimCore.CardContainer;
import pokerSimCore.Deck;

public class RecursiveCalculator {
	// private Hand hand;
	
	private @Getter HashMap<CombinationRanks, ArrayList<Set<Card>>> outs;
	
	private Deck checkDeck;
	private @Getter int checkCounter;
	private Set<Card> checkingCards;
	private Set<Card> originalCards;
	private int depth;
	private static final int totalMaxCards = 7;	// currently already too deep when no initial cards are provided
	
	private RecursiveCalculator() {
		outs = new HashMap<>();
		for (CombinationRanks cr: CombinationRanks.values()) {
			outs.put(cr, new ArrayList<Set<Card>>());
		}
		checkCounter = 0;
		checkDeck = new Deck();
	}
	
	public RecursiveCalculator(CardContainer c) {
		this();
		
		originalCards = new HashSet<Card>(c.getAllCards());
		checkingCards = new HashSet<Card>(c.getAllCards());
		depth = checkingCards.size();
		checkDeck.removeAll(c.getAllCards());
	}
	
	public void calc() {
		Deck checkDeck = new Deck(this.checkDeck);
		checkDeck.removeAll(checkingCards);
		depth++;
		for (Card checkCard : checkDeck) {
			
			if (depth >= totalMaxCards) {
				HashSet<Card> checkingCards = new HashSet<Card>(this.checkingCards);
				checkingCards.add(checkCard);
				Hand handOption = new Hand(checkingCards);
				CombinationRanks highestOption = handOption.getHandRanker()
						.getHighestMade().getDrawRank();
				checkingCards.removeAll(this.originalCards);
				outs.get(highestOption).add(checkingCards);
				checkCounter++;
			} else {
				checkingCards.add(checkCard);
				calc();
				checkingCards.remove(checkCard);
			}
		}
		
		depth--;
	}
}
