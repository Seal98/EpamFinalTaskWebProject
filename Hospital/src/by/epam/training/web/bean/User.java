package by.epam.training.web.bean;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private static final long serialVersionUID = 8815653912194571673L;
	public static final String sampleHashcodeString = "sampleString";

	private int userId;
	private String userLogin;
	private String userPassword;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String userType;
	
	public User() {
		super();
	}

	public User(int userId, String userLogin, String userPassword, String firstName, String lastName, Date birthDate) {
		this.setUserId(userId);
		this.setUserLogin(userLogin);
		this.setUserPassword(userPassword);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setBirthDate(new Date(birthDate.getTime()));
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (this.getClass().getName().compareTo(o.getClass().getName()) != 0) {
			return false;
		}
		if (this.hashCode() != o.hashCode()) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = userId * 5 + userLogin.compareTo(sampleHashcodeString) * 4
				+ userPassword.compareTo(sampleHashcodeString) * 4 + firstName.compareTo(sampleHashcodeString) * 7
				+ lastName.compareTo(sampleHashcodeString) * 3 + (int) birthDate.getTime() / 500000;
		return result;
	}

	@Override
	public String toString() {
		return "ID: " + userId + ",  " + "LOGIN: " + userLogin + ",  PASSWORD: " + userPassword + ",  NAME: "
				+ firstName + ",  SURNAME: " + lastName + ",  BIRTH_DATE: " + birthDate;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
