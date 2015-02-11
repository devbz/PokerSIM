package pokerCalculator;

import pokerSimCore.Card;
import pokerSimCore.GameStage;
import pokerSimCore.Player;
import pokerSimCore.Table;

public class Calculator {
	
	private Player player;
	private Table table;
	
	private final Card noCard = new Card(0,0);
	
	private float odds;
		public float getOdds() { return odds; }
			
	public Calculator(Player p, Table t) {
		this.player = p;
		this.table = t;
		odds = -1;
		calculate();
	}
	
	private void calculate() {
		
		int cardsInCalc;
		
		switch(findStage()) {
		case PREFLOP:
			cardsInCalc = 2;
			break;
		case FLOP:
			cardsInCalc = 5;
			break;
		case TURN:
			cardsInCalc = 6;
			break;
		case RIVER:
			cardsInCalc = 7;
			break;
		
		case FINALLY:
			break;
		default:
			break;
		}
	}
	
	private GameStage findStage() {
		if(table.getFlop1().isCard(noCard)) { return GameStage.PREFLOP; }
		else if(table.getTurn().isCard(noCard)) { return GameStage.FLOP; }
		else if(table.getRiver().isCard(noCard)) { return GameStage.TURN; }
		else return GameStage.RIVER;
	}

}
