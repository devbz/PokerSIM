package pokerCalculator;

import java.util.ArrayList;
import java.util.List;

import pokerSimCore.Card;

public class PairDetector extends HandRankFinder {

	public PairDetector(HandRanker h) {
		super(h);
	}

	@Override
	public void find() {
		List<Card> allCards = new ArrayList<>();
		
		List<Card> checkCards = new ArrayList<Card>(hand.getAllCards());
		
		// Cards in Hand are sorted high to low, so this always returns the highest pair
		for(Card card: hand.getAllCards()) {
			
			checkCards.remove(card);
			
			for(Card checkCard: checkCards) {
				
				if(card.getRank() == checkCard.getRank()) { 
					ArrayList<Card> cards = new ArrayList<Card>();
					cards.add(card);
					cards.add(checkCard);
					allCards.addAll(cards);

					if(allCards.size() == 4) {
						foundCombination(new Combination(CombinationRanks.TWO_PAIR, allCards));
						return;
					}
				}
			}
		}
		
		if(allCards.size() == 2) {
			foundCombination(new Combination(CombinationRanks.PAIR, allCards));
			return;
		}
	}
}
