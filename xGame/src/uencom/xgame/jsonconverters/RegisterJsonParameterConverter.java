package uencom.xgame.jsonconverters;

public class RegisterJsonParameterConverter {
	private String grant_type;
	private String email;
	private String password;
	private String device_id;

	public RegisterJsonParameterConverter(String gr, String mail, String pass,
			String dev_id) {
		setGrant_type(gr);
		setEmail(mail);
		setPassword(pass);
		setDevice_id(dev_id);
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
}
