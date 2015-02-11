package pokerSimCore;

import java.util.ArrayList;

import lombok.Getter;

public class Pot {
	private @Getter	int potValue;
	private @Getter ArrayList<Player> potPlayers;
	
	public Pot(ArrayList<Player> players) {
		potValue = 0;
		this.potPlayers = new ArrayList<>(players);
	}
	
	public void put(int value) {
		potValue += value;
	}
	
	public void removePlayer(Player p) {
		potPlayers.remove(p);
	}
}
