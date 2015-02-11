package pokerCalculator;

import lombok.Data;
import pokerSimCore.Card;

public @Data class Out {
	private Card card1;
	private Card card2;
	private float odds;
}
