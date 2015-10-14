package pokerCalculator;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import pokerSimCore.Card;
import pokerSimCore.Card.Ranks;

/* Contains a (possible) combination of cards and its rank.
 * Can distill kicker cards if passed a Hand class. */
public @Data class Combination {
	
	private boolean made = false;
	private Card.Ranks rank;
	private CombinationRanks drawRank;
	private List<Card> combinationCards = new ArrayList<Card>();
	private List<Integer> kickerValues = new ArrayList<Integer>();	//TODO make nice string values
	private List<String> kickerNames = new ArrayList<String>();
	
	/* Ctor for a combination that has been 'made'
	 * Injected by a combination rank and an array of cards making the combination 
	 */
	public Combination(CombinationRanks r, List<Card> allCards) {
		this.drawRank = r;
		combinationCards = allCards;
		rank = combinationCards.get(0).getRank();
		made = true;
	}
	
	public Combination(CombinationRanks r, Combination... c1) {
		this.drawRank = r;
		// Safe varargs:
		for(Combination c: c1) {
			combinationCards.addAll(c.getCombinationCards());
		}
		rank = combinationCards.get(0).getRank();
		made = true;
	}
	
	/* Ctor for an abstract combination, to enable a 'named and ranked collection variable' in other classes */
	public Combination() {
		this.drawRank = CombinationRanks.HIGH_CARD;
		this.rank = Ranks.EMPTY;
		made = false;
	}
	
	protected void findKickerValues(Hand h) {
		ArrayList<Card> unUsedCards = new ArrayList<Card>(h.getAllCards());
		unUsedCards.removeAll(combinationCards);
		while(!unUsedCards.isEmpty()) {
			Card c = unUsedCards.remove(0);
			kickerValues.add(c.getRank().getRanksNum());
		}
		for (int i : kickerValues) {
			kickerNames.add("" + Card.Ranks.fromNum(i));
		}
	}
	
	public String toString() {
		switch(drawRank) {
		case FLUSH:
			return (drawRank + ", " + rank.fullName() + " high, with kickers: " + kickerValues);
		case THREE_OF_A_KIND:
		case PAIR:
		case FOUR_OF_A_KIND:
			return (drawRank + " of " + rank.fullName() + "s with kickers: " + kickerValues);
		case FULL_HOUSE:
			return (drawRank + ", " + rank.fullName() + "s over " + combinationCards.get(3).getRank() + "s with kicker: " + kickerValues);
		case HIGH_CARD:
			return (drawRank + " " + rank.fullName() + " with kickers: " + kickerValues);
		case ROYAL_FLUSH:
		case STRAIGHT:
		case STRAIGHT_FLUSH:
			return (drawRank + " " + rank.fullName() + " high");
		case TWO_PAIR:
			if(combinationCards.size() > 2) {
				return (drawRank + " of " + rank.fullName() + " and " + combinationCards.get(2).getRank().fullName() + " with kickers: " + kickerNames);
			} else {
				return "Two Pairs in making";
			}
		default:
			break;
		}
		return (drawRank + " of " + rank + "s with kickers: " + kickerNames);
	}
	
	public int compareToOnlyForReporting(Combination c) {
		return (c.drawRank.rankNum > this.drawRank.rankNum ? -1 : 1);
	}
	
	public boolean equals (Combination c2) {
		return (drawRank == c2.getDrawRank() ? c2.getKickerValues().containsAll(kickerValues) : false);
	}
}