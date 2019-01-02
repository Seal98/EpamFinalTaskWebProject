package by.epam.training.web.bean;

public abstract class MedicalTreatment {
	
	private String name;

	public MedicalTreatment() {
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
