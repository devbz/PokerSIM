package pokerSimCore;

import lombok.Data;

public @Data class ErrorMessage {
	private boolean error = false;
	private String message = "";
}
