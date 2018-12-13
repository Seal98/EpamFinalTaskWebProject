package by.epam.training.web.bean;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = 8815653912194571673L;
	
	private int userId;
	private String userLogin;
	private String userPassword;
	
	public User() {
		super();
	}
	
	public User(int userId, String userLogin, String userPassword) {
		this.setUserId(userId);
		this.setUserLogin(userLogin);
		this.setUserPassword(userPassword);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(this.getClass().getName().compareTo(o.getClass().getName()) != 0) {
			return false;
		}
		if(this.hashCode() != o.hashCode()) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = userId*5 + userLogin.compareTo("sampleString")*4 + userPassword.compareTo("sampleString")*4;
		return result;
	}
	
}
