package pokerGUI_JavaFX;

import java.util.ArrayList;
import java.util.List;

public class MessageKeeper {
	List<String> messages;
	int indexPulled;
	
	public MessageKeeper() {
		messages = new ArrayList<String>();
		indexPulled = 0;
	}
	
	public void push(List<String> allMessages) {
		messages = allMessages;
	}

	public List<String> pull() {
		int processFrom = indexPulled;
		indexPulled = messages.size();
		return messages.subList(processFrom, messages.size());
	}
}
