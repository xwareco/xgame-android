package xware.xgame.jsonconverters;

public class SendUserJsonParameterConverter {
private String message;

public SendUserJsonParameterConverter(String msg)
{
	setMessage(msg);
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
}
