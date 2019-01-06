package by.epam.training.web.bean;

import java.io.Serializable;
import java.util.Date;

public class Doctor extends User implements Serializable {

	private static final long serialVersionUID = 2681990724280059747L;

	private String specialization;
	private String experience;
	
	public Doctor() {
		super();
		super.setUserType("Doctor");
	}

	public Doctor(int userID, String login, String password, String firstName, String lastName, Date birthDate,
			String specialization, String experience) {
		super(userID, login, password, firstName, lastName, birthDate);
		super.setUserType("Doctor");
		this.specialization = specialization;
		this.experience = experience;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	@Override
	public boolean equals(Object o) {
		super.equals(o);
		if (this.hashCode() != o.hashCode()) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode() + specialization.compareTo(sampleHashcodeString)*8 + experience.compareTo(sampleHashcodeString)*5;
		return result;
	}

	@Override
	public String toString() {
		return super.toString() + ",  SPECIALIZATION: " + specialization + ",  EXPERIENCE: " + experience;
	}
	
}
