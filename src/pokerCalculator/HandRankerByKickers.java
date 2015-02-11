package pokerCalculator;

import java.util.Comparator;

public class HandRankerByKickers implements Comparator<HandRanker> {

	@Override
	public int compare(HandRanker hr1, HandRanker hr2) {
		for(int i = 0; i < hr1.getHighestMade().getKickerValues().size(); i++) {
			if(hr1.getHighestMade().getKickerValues().get(i) > hr2.getHighestMade().getKickerValues().get(i)) { return 1; }
			if(hr1.getHighestMade().getKickerValues().get(i) < hr2.getHighestMade().getKickerValues().get(i)) { return -1; }
		}
		return 0;
	}
	
}
