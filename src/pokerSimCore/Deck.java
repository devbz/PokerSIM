package pokerSimCore;
import java.util.ArrayList;
import java.util.Random;

public class Deck {
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	public final static Card NOCARD = new Card(0,0);
	
	public Deck() {
		for (int i = 1; i <= 4; i++){
			for(int ii = 1; ii <= 13; ii++){
				cards.add(new Card(i, ii));
			}
		}
	}

	public Card getCard(int i) {
		return cards.get(i);
	}
	
	public Card dealCard() {
		Card c = new Card();
		Random r = new Random();
		int cardNumber = r.nextInt(cards.size());
		c = cards.get(cardNumber);
		cards.remove(cardNumber);
		return c;
		// Test if this returns every card after a while!
	}
}
