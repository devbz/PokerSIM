package pokerCalculator;

/* HandRankFinders are built to find a specific combination of cards that 
 * make up a specific ranked Poker combination via the find() function.
 * Result should be passed via callback to the HandRanker class via foundCombination(). */
public abstract class HandRankFinder {
	HandRanker handRanker;
	Hand hand;
		
	public HandRankFinder(HandRanker h) {
		this.handRanker = h;
		this.hand = h.getHand();
	}
	
	protected abstract void find();
	
	// Returns the best found combination via callback
	protected void foundCombination(Combination c) {
		c.findKickerValues(hand);
		handRanker.reportCombination(c);
	}
}
