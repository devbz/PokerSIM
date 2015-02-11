package pokerSimCore;

import java.util.Comparator;

public class Card implements Comparator<Card> {
	
	private Ranks rank;
	private Suits suit;
	
	public enum Suits {
		EMPTY(0),
		HEARTS(1),
		SPADES(2),
		CLUBS(3),
		DIAMONDS(4);
		
		private final int i;
		
		private Suits(int c) { i = c; }
		
		public int getSuitNum() { return i; }
		
		public String toString() {
			switch (i) {
			case 0:
				return "";
			case 1:
				return "Hearts";
			case 2:
				return "Spades";
			case 3:
				return "Clubs";
			case 4:
				return "Diamonds";
			}
			return "Joker";
		}
	}
	
	public enum Ranks {
		EMPTY(0),
		TWO(2),
		THREE(3),
		FOUR(4),
		FIVE(5),
		SIX(6),
		SEVEN(7),
		EIGHT(8),
		NINE(9),
		TEN(10),
		JACK(11),
		QUEEN(12),
		KING(13),
		ACE(14);
		
		private final int r;
		
		Ranks(int c) { r = c; }
		
		public int getRanksNum() { return r; }
		
		public String toString() {
			switch (r) {
			case 0:
				return "";
			case 2:
				return "Two";
			case 3:
				return "Three";
			case 4:
				return "Four";
			case 5:
				return "Five";
			case 6:
				return "Six";
			case 7:
				return "Seven";
			case 8:
				return "Eight";
			case 9:
				return "Nine";
			case 10:
				return "Ten";
			case 11:
				return "Jack";
			case 12:
				return "Queen";
			case 13:
				return "King";
			case 14:
				return "Ace";
			default:
				break;
			}
			return "Joker";
		}
		
		public static Ranks fromNum(int i) {
			for (Ranks r: Ranks.values()) {
				if (r.getRanksNum() == i) {	return r;	}
			}
			return null;
		}
	}
	
	public Card() {
		this(0,0);
	}
	
	public Card(int suit, int rank) {
		this.rank = Ranks.values()[rank];
		this.suit = Suits.values()[suit];
	}

	public Ranks getRank() {
		return rank;
	}
	
	public Suits getSuit() {
		return suit;
	}
	
	public String getCardName() {
		if(suit == Suits.EMPTY && rank == Ranks.EMPTY) {
			return "";
		}
		return (this.rank + " of " + this.suit);
	}
	
	public String toString() {
		if(suit == Suits.EMPTY && rank == Ranks.EMPTY) {
			return "";
		}
		return (this.rank + " of " + this.suit);
	}
	
	public boolean isCard(Card card) {
		if(card.getCardName() == this.getCardName()) { return true; }
		return false;
	}
	
	public static Card.Ranks findHighestRank(Card[] c) {
		Card.Ranks foundRank = Ranks.EMPTY;
		for (Card iCard : c) { 
			if (iCard.getRank() == Ranks.ACE) {
				foundRank = Ranks.ACE;
			} else if (iCard.rank.r > foundRank.r) {
				foundRank = iCard.rank;
			}
		}
		return foundRank;
	}

	@Override
	public int compare(Card o1, Card o2) {
		return(o1.rank.getRanksNum() > o2.rank.getRanksNum() ? -1 :
			(o1.rank.getRanksNum() < o2.rank.getRanksNum() ? 1 : 0));
	}
}
