package uencom.xgame.jsonconverters;

public class ChangeEmailJsonConverter {
	private String user_id;
	private String new_email;
	
	
	public ChangeEmailJsonConverter(String id , String mail)
	{
		user_id = id;
		new_email = mail;
	}
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getNew_email() {
		return new_email;
	}
	public void setNew_email(String new_email) {
		this.new_email = new_email;
	}
	
	

}
