package pokerAI;

import pokerSimCore.Decision;
import pokerSimCore.Game;
import pokerSimCore.Player;
import pokerSimCore.Table;

public class AI {
	
	private Game game;
	
	public AI(Game g) {
		game = g;
	}
	
	public Decision decide(Player p) {
		Table table = game.getRound().getTable();
		return null;
	}

}
