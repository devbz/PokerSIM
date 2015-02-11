package pokerSimCore;

public enum Decision {
	EMPTY(0),
	FOLD(1),
	CALL(2),
	BET(3),
	ALLIN(4),
	BANKRUPT(5);
	
	private int i;
	static private final int length = Decision.values().length;
	
	private Decision(int i){
		this.i = i;
	}
	
	public int getValue(){
		return i;
	}
	
	public static int length() {
		return length - 1;
	}
}
