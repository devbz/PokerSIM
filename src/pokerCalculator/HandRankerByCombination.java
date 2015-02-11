package pokerCalculator;

import java.util.Comparator;

public class HandRankerByCombination implements Comparator<HandRanker> {

	@Override
	public int compare(HandRanker hr1, HandRanker hr2) {
		if(hr1.getHighestMade().getDrawRank().getValue() > hr2.getHighestMade().getDrawRank().getValue()) { return 1; }
		if(hr1.getHighestMade().getDrawRank().getValue() < hr2.getHighestMade().getDrawRank().getValue()) { return -1; }
		return 0;
	}
	
}
