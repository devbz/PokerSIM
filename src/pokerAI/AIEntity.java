package pokerAI;

import java.util.Random;

import pokerSimCore.Game;
import pokerSimCore.Player;
import lombok.Data;

public @Data class AIEntity {
	private Random r = new Random();
	
	private Game game;
	private Player player;
	
	private int agression;
	private int confidence;
	private double stackPosition;
	
	public AIEntity(Player p) {
		player = p;
		
		updateStackPosition();
		
		agression = r.nextInt(100);
		confidence = 50;
	}

	private void updateStackPosition() {
		int activePlayers = 0;
		int playerPos = 0;
		for (Player pl: game.getPlayers()) {
			if (pl.isInPlay())	{	activePlayers++;	}
			if (pl.getCash() > player.getCash())	{	playerPos++;	}
		}
		stackPosition = playerPos / activePlayers;
	}

}
