package pokerCalculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;

/* Class HandRanker
 * Finds the best possible combination that can be made with a Hand */

public class HandRanker {
	private @Getter Combination highestMade = new Combination();
	private List<HandRankFinder> handFinders = new ArrayList<>();
	
	private @Getter Hand hand;
	private boolean empty;
	
	//TODO Splitting needs place in overlaying class:
	private static @Getter Set<HandRanker> splitters = new HashSet<HandRanker>();
	
	public HandRanker() {	empty = true;	}
	
	public HandRanker(Hand h) {
		empty = false; 
		hand = h;
		buildHandFinders();
		for(HandRankFinder hf: handFinders) {
			hf.find();
		}
	}
	
	private void buildHandFinders() {
		handFinders.add(new StraightDetector(this));
		handFinders.add(new ThreeKindDetector(this));
		handFinders.add(new HighCardDetector(this));
	}
	
	public String toString() {
		return (highestMade.isMade() ? highestMade.toString() : "");
	}
	
	public void reportCombination(Combination c) {
		if(c.compareToOnlyForReporting(highestMade) > 0) {
			highestMade = c;
		}
	}
	
	public boolean equals(HandRanker hr2) {
		return highestMade.equals(hr2.getHighestMade());
	}

	public boolean isEmpty() {
		return empty;
	}
}
