package pokerGUI_JavaFX;

import java.util.ArrayList;
import java.util.Set;

import pokerCalculator.CombinationRanks;
import pokerSimCore.Card;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class OddsOutModel {
    private final CombinationRanks draw;
    private final SimpleDoubleProperty chance;
    private final ArrayList<Set<Card>> cardSet;
    private final SimpleStringProperty cards;
 
    public OddsOutModel(CombinationRanks draw, Double chance, ArrayList<Set<Card>> cardSets) {
        this.draw = draw;
        this.chance = new SimpleDoubleProperty(chance);
        if(cardSets.size() != 0 && cardSets.get(0) != null) {
        	this.cards = new SimpleStringProperty(cardSets.get(0).toString());
        	this.cardSet = cardSets;
        } else {
        	this.cards = new SimpleStringProperty("");
        	this.cardSet = new ArrayList<Set<Card>>();
        }
    }
    
    public CombinationRanks getDraw() {
    	return draw;
    }
    
    public Double getChance() {
    	return chance.get();
    }
    
    public String getCards() {
    	return cards.get();
    }
    
    public ArrayList<Set<Card>> getCardSet() {
    	return cardSet;
    }
    
    public String toString() {
    	return draw.toString() + " " + chance.get();
    }
}
