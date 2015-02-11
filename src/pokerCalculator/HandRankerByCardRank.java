package pokerCalculator;

import java.util.Comparator;
import java.util.Iterator;

import pokerSimCore.Card;

public class HandRankerByCardRank implements Comparator<HandRanker> {

	@Override
	public int compare(HandRanker hr1, HandRanker hr2) {
		Iterator<Card> hr1Iterator = hr1.getHighestMade().getCombinationCards().iterator();
		Iterator<Card> hr2Iterator = hr2.getHighestMade().getCombinationCards().iterator();
		while(hr1Iterator.hasNext()) {
			int rankOf1 = hr1Iterator.next().getRank().getRanksNum();
			int rankOf2 = hr2Iterator.next().getRank().getRanksNum();
			if(rankOf1 > rankOf2) { return 1; }
			if(rankOf1 < rankOf2) { return -1; }
		}

		return 0;
	}

}
