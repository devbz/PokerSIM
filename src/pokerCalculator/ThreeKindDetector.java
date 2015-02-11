package pokerCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pokerSimCore.Card;

public class ThreeKindDetector extends HandRankFinder {

	public ThreeKindDetector(HandRanker h) {
		super(h);
	}

	@Override
	protected void find() {
		Combination threeKind = new Combination();
		Combination twoPair = new Combination();
		Combination secondPair = new Combination();
		Combination onePair = new Combination();
		List<Card> checkCards = new ArrayList<Card>(hand.getAllCards());
		
		for(int cardPosition = 0; cardPosition < checkCards.size(); cardPosition++) {
			
			int sameRanks = 0;
			
			// Because the cards are sorted, we can easily check on how many cards after the current card are of the same rank:
			while((cardPosition + 1 + sameRanks < checkCards.size()) && 
					(checkCards.get(cardPosition).getRank() == checkCards.get(cardPosition + 1 + sameRanks).getRank())) {
				sameRanks++;
			}
			
			// Now we create paired combinations, based on how many of the same cards we've found
			// Do note that in this process we also ++ the cardPosition so we don't check it again
			switch(sameRanks) {
			case 0:
				break;
			case 1:
				if(!onePair.isMade()) {
					onePair = new Combination(CombinationRanks.PAIR, new ArrayList<Card>(Arrays.asList(
							checkCards.get(cardPosition),
							checkCards.get(cardPosition++))));
				} else if (!secondPair.isMade()) {
					secondPair = new Combination(CombinationRanks.PAIR, new ArrayList<Card>(Arrays.asList(
							checkCards.get(cardPosition),
							checkCards.get(cardPosition++))));
					twoPair = new Combination(CombinationRanks.TWO_PAIR, onePair, secondPair);
				}
				break;
			case 2:
				threeKind = new Combination(CombinationRanks.THREE_OF_A_KIND, new ArrayList<Card>(Arrays.asList(
						checkCards.get(cardPosition),
						checkCards.get(cardPosition++),
						checkCards.get(cardPosition++))));
				break;
			case 3:
				// If we find a four of a kind, immediately create it as the best outcome and return:
				foundCombination(new Combination(CombinationRanks.THREE_OF_A_KIND, new ArrayList<Card>(Arrays.asList(
						checkCards.get(cardPosition),
						checkCards.get(cardPosition++),
						checkCards.get(cardPosition++),
						checkCards.get(cardPosition++)))));
				return;
			}
		}
		
		// After all checks for pairs are made, full house is the best outcome, so return immediately after:
		if(threeKind.isMade() && onePair.isMade()) {
			foundCombination(new Combination(CombinationRanks.FULL_HOUSE, threeKind, onePair));
			return;
		}
		
		// Finally, find the highest made other possible combinations:
		if(threeKind.isMade()) {
			foundCombination(threeKind);
		} else if(twoPair.isMade()) {
			foundCombination(twoPair);
		} else if(onePair.isMade()) {
			foundCombination(onePair);
		}
		return;
	}
}