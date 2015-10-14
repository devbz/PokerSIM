package pokerAI;

import lombok.Getter;
import pokerSimCore.Card;

public class Hold {
	private @Getter String name;
	
	public Hold(Card c1, Card c2){
		name = c1.getRank().toHold() + c2.getRank().toHold() + (
				c1.getRank() != c2.getRank() ?
					(c1.getSuit() == c2.getSuit() ? "s" : "o")
					: "");
	}
	
	public String toString(){
		return name;
	}
}
