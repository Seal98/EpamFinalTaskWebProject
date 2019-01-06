package by.epam.training.web.bean;

public abstract class MedicalTreatment {
	
	private String name;
	private String type;
	
	public MedicalTreatment() {
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
