package uencom.xgame.engine.web;

public class PasswordReminderJsonConverter {
	
	private String user_id;
	private String current_password;
	
	public PasswordReminderJsonConverter(String id, String pass) {
		user_id = id;
		current_password = pass;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return current_password;
	}

	public void setPassword(String password) {
		this.current_password = password;
	}
	
	

}
