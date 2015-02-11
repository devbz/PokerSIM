package pokerCalculator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class HandRankerComparator implements Comparator<HandRanker> {
	private List<Comparator<HandRanker>> listComparators;
	
	@SafeVarargs
	public HandRankerComparator(Comparator<HandRanker>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}
	
	@Override
	public int compare(HandRanker hr1, HandRanker hr2) {
		for(Comparator<HandRanker> c: listComparators) {
			int result = c.compare(hr2, hr1);
			if(result != 0) { return result; }
		}
		
		HandRanker.getSplitters().add(hr1);
		HandRanker.getSplitters().add(hr2);
		return 0;
	}
}
