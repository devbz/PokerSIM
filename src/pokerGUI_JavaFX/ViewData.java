package pokerGUI_JavaFX;

import java.util.ArrayList;
import java.util.List;

import pokerSimCore.ErrorMessage;
import pokerSimCore.Game;

public class ViewData {
	public class PlayerData {
		public String name;
		public String position;
		public String lastAction;
		public int cash;
		public String card1Name;
		public String card2Name;		
		
		public PlayerData() {}
	}
	
	public List<PlayerData> players;
	
	public ViewData() {
		players = new ArrayList<>();
		for(int i = 0; i <= Game.NUMPLAYERS; i++) {
			players.add(new PlayerData());
		}
	}
	
	public int pot;
	public int toCall;
	
	public String flopCard1;
	public String flopCard2;
	public String flopCard3;
	public String turnCard;
	public String riverCard;
	
	public String gameMessage;
	public List<String> messageList = new ArrayList<>();
	public ErrorMessage errorMessage;
	public int minRaise;
}
