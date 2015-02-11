package pokerCalculator;

public enum CombinationRanks implements Comparable<CombinationRanks> {
	ROYAL_FLUSH(9),
	STRAIGHT_FLUSH(8),
	FOUR_OF_A_KIND(7),
	FULL_HOUSE(6),
	FLUSH(5),
	STRAIGHT(4),
	THREE_OF_A_KIND(3),
	TWO_PAIR(2),
	PAIR(1),
	HIGH_CARD(0);
	
	int rankNum = -1;
	
	CombinationRanks(int i) {
		rankNum = i;
	}
	
	public int getValue() {
		return rankNum;
	}
	
	public String toString() {
		switch(rankNum) {
		case 9:
			return "Royal Flush!";
		case 8:
			return "Straight Flush";
		case 7:
			return "Four of a Kind";
		case 6:
			return "Full House";
		case 5:
			return "Flush";
		case 4:
			return "Straight";
		case 3:
			return "Three of a Kind";
		case 2:
			return "Two Pair";
		case 1:
			return "One Pair";
		case 0:
			return "High Card";
		}
		return "";
	}
}