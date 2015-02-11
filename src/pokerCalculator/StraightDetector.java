package pokerCalculator;

import java.util.ArrayList;
import java.util.List;

import pokerSimCore.Card;
import pokerSimCore.Card.Ranks;

public class StraightDetector extends HandRankFinder {

	public StraightDetector(HandRanker h) {
		super(h);
	}

	@Override
	protected void find() {
		foundCombination(findFlush(hand.getAllCards()));
		
		int straightCounter = 1;
		int lastValue = 0;
		ArrayList<Card> straightCards = new ArrayList<>();
		ArrayList<Card> wildAces = new ArrayList<>();
		
		for(Card c: hand.getAllCards()) {
			int cardValue = c.getRank().getRanksNum();
			
			if(c.getRank() == Ranks.ACE) { wildAces.add(c); }
			
			if((lastValue == 0) || (cardValue == lastValue)) {
				straightCards.add(c);
				lastValue = cardValue;
			} else if(cardValue == lastValue - 1) {
				straightCounter++;
				straightCards.add(c);
				lastValue = cardValue;
				if((c.getRank() == Ranks.TWO) && (!wildAces.isEmpty())) {
					straightCards.addAll(wildAces);
					straightCounter++;
				}
			} else if(straightCounter < 5) {
				straightCards.clear();
				straightCounter = 1;
				straightCards.add(c);
				lastValue = cardValue;
			}
		}
		
		// Report either a Royal Flush, Straight Flush or Regular Straight:
		if(straightCounter >= 5) {
			Combination straightFlush = findFlush(straightCards);
			if((straightFlush.isMade()) && (straightFlush.getCombinationCards().get(0).getRank() == Ranks.ACE)) {
				foundCombination(new Combination(CombinationRanks.ROYAL_FLUSH, straightFlush.getCombinationCards()));
			} else if(straightFlush.isMade()) {
				foundCombination(straightFlush);
			} else {
				trimStraightCombination(straightCards);
				foundCombination(new Combination(CombinationRanks.STRAIGHT, straightCards));
			}
		}
	}
	
	// Basically unnecessary function as no kickers are taken into account, but what the hell:
	private void trimStraightCombination(List<Card> cards) {
		int cardValue = 0;
		int toFive = 0;
		ArrayList<Card> newCards = new ArrayList<>(cards);
		
		for(Card c: cards) {
			if(cardValue == 0) {
				cardValue = c.getRank().getRanksNum();
				toFive++;
			} else if((c.getRank().getRanksNum() == cardValue) || (toFive > 5)) {
					newCards.remove(c);
					toFive++;
			}
		}
		
		cards = newCards;
	}
	
	protected Combination findFlush(List<Card> cards) {
		ArrayList<Card> heartsCards = new ArrayList<>();
		ArrayList<Card> diamondsCards = new ArrayList<>();
		ArrayList<Card> spadesCards = new ArrayList<>();
		ArrayList<Card> clubsCards = new ArrayList<>();
		
		for(Card c: cards) {
			switch(c.getSuit()) {
			case CLUBS:
				clubsCards.add(c);
				break;
			case DIAMONDS:
				diamondsCards.add(c);
				break;
			case EMPTY:
				break;
			case HEARTS:
				heartsCards.add(c);
				break;
			case SPADES:
				spadesCards.add(c);
				break;
			default:
				break;
			}
		}
		
		if(clubsCards.size() >= 5)		{ return new Combination(CombinationRanks.FLUSH, clubsCards); }
		if(diamondsCards.size() >= 5)	{ return new Combination(CombinationRanks.FLUSH, diamondsCards); }
		if(spadesCards.size() >= 5)		{ return new Combination(CombinationRanks.FLUSH, spadesCards); }
		if(heartsCards.size() >= 5)		{ return new Combination(CombinationRanks.FLUSH, heartsCards); }
		
		return new Combination();
	}

}
