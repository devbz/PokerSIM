package pokerSimCore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class Deck implements Iterable<Card> {
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	public final static Card NOCARD = new Card(0,0);
	
	public Deck() {
		for (int i = 1; i <= 4; i++){
			for(int ii = 1; ii <= 13; ii++){
				cards.add(new Card(i, ii));
			}
		}
	}
	
	public Deck(Deck d) {
		this.cards = new ArrayList<Card>(d.cards);
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
	
	public void removeCard(Card c) {
		for (Iterator<Card> checkCard = cards.iterator(); checkCard.hasNext();) {
			if (checkCard.next().isCard(c)) {
				checkCard.remove();
			}
		}
	}
	
	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	public int size() {
		return cards.size();
	}

	public void removeAll(Collection<Card> handCards) {
		for(Card c: handCards) {
			for(Iterator<Card> ci = cards.iterator(); ci.hasNext(); ) {
				if (((Card) ci.next()).isCard(c)) {	ci.remove();	}
			}
		}
	}
}
