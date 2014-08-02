package models;


public class User {
	
	private String facebookAccount;//Should be GraphUser's Id
	private String username;

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFacebookAccount() {
		return facebookAccount;
	}
	public void setFacebookAccount(String facebookAccount) {
		this.facebookAccount = facebookAccount;
	}
	
	
}
