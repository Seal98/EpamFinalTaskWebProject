package by.epam.training.web.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class User implements Serializable {

	private static final long serialVersionUID = 8815653912194571673L;
	
	public static final String sampleHashcodeString = "sampleString";
	public static final String patientConst = "Patient";
	public static final String doctorConst = "Doctor";
	public static final String nurseConst = "Nurse";
	public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  hh:mm");
	
	private int userId;
	private String userLogin;
	private String userPassword;
	private String language;
	private String firstName;
	private String lastName;
	private String birthDate;
	private String userType;
	
	public User() {
		super();
	}

	public User(int userId, String userLogin, String userPassword, String language, String firstName, String lastName, String birthDate) {
		this.setUserId(userId);
		this.setUserLogin(userLogin);
		this.setUserPassword(userPassword);
		this.setLanguage(language);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setBirthDate(birthDate);
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
	    this.birthDate = birthDate;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
				+ lastName.compareTo(sampleHashcodeString) * 3 + birthDate.compareTo(sampleHashcodeString);
		return result;
	}

	@Override
	public String toString() {
		return "ID: " + userId + ",  " + "LOGIN: " + userLogin + ",  PASSWORD: " + userPassword + ",  NAME: "
				+ firstName + ",  SURNAME: " + lastName + ",  BIRTH_DATE: " + birthDate;
	}

}
