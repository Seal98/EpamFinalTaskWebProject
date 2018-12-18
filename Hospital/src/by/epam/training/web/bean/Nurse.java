package by.epam.training.web.bean;

import java.io.Serializable;
import java.util.Date;

public class Nurse extends User implements Serializable {

	private static final long serialVersionUID = -396252388533017277L;

	private String experience;
	
	public Nurse() {
		super();
	}

	public Nurse(int userID, String login, String password, String firstName, String lastName, Date birthDate, String experience) {
		super(userID, login, password, firstName, lastName, birthDate);
		this.experience = experience;
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
		int result = super.hashCode() + experience.compareTo(sampleHashcodeString)*5;
		return result;
	}

	@Override
	public String toString() {
		return super.toString() + ",  EXPERIENCE: " + experience;
	}
}
