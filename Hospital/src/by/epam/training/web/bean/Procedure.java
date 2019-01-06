package by.epam.training.web.bean;

import java.io.Serializable;

public class Procedure extends MedicalTreatment implements Serializable {

	private static final long serialVersionUID = -6443910407930909219L;

	private String description;
	
	public Procedure() {
		super();
		super.setType("procedure");
	}

	public Procedure(String name, String description) {
		this.description = description;
		super.setName(name);
		super.setType("procedure");
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		for(int i=0;i<description.length();i++) {
			result += description.charAt(i);
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "[PROCEDURE NAME: " + super.getName() + ",   DESCRIPTION: " + description + "]";
	}
	
}
