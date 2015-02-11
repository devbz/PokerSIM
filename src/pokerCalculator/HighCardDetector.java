package pokerCalculator;

import java.util.ArrayList;

import pokerSimCore.Card;

public class HighCardDetector extends HandRankFinder {

	public HighCardDetector(HandRanker h) {
		super(h);
	}

	@Override
	protected void find() {
		ArrayList<Card> highCard = new ArrayList<>();
		highCard.add(hand.getAllCards().get(0));
		foundCombination(new Combination(CombinationRanks.HIGH_CARD, highCard));
	}

}
