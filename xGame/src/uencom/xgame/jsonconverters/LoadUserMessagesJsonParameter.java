package uencom.xgame.jsonconverters;

public class LoadUserMessagesJsonParameter {
private String user_id;

public LoadUserMessagesJsonParameter(String ID)
{
	setUser_id(ID);
}

public String getUser_id() {
	return user_id;
}

public void setUser_id(String user_id) {
	this.user_id = user_id;
}
}
