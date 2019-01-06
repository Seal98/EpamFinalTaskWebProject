package by.epam.training.web.bean;

import java.io.Serializable;

public class Surgery extends MedicalTreatment implements Serializable {

	private static final long serialVersionUID = -6443910407930909219L;

	private String duration;
	
	public Surgery() {
		super();
		super.setType("surgery");
	}

	public Surgery(String name, String duration) {
		this.duration = duration;
		super.setName(name);
		super.setType("surgery");
	}
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(o == this) {
			return true;
		}
		if(o.getClass().getName().compareTo(this.getClass().getName()) != 0) {
			return false;
		}
		if(o.hashCode() != this.hashCode()) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		String name = super.getName();
		for(int i=0;i<name.length();i++) {
			result += name.charAt(i);
		}
		for(int i=0;i<duration.length();i++) {
			result += duration.charAt(i);
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "[SURGERY NAME: " + super.getName() + ",   DURATION: " + duration + "]";
	}
	
}
