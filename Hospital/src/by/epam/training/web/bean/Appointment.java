package by.epam.training.web.bean;

import java.io.Serializable;

public class Appointment implements Serializable {

	private static final long serialVersionUID = -1939077644266138661L;

	private Doctor appointee;
	private User appointmentExecutor;
	private boolean surgery;
	private boolean medicine;
	private boolean procedure;
	private MedicalTreatment treatment;
	
	public Appointment() {
		super();
	}
	
	
	public User getAppointmentExecutor() {
		return appointmentExecutor;
	}

	public void setAppointmentExecutor(User appointmentExecutor) {
		this.appointmentExecutor = appointmentExecutor;
	}

	public Doctor getAppointee() {
		return appointee;
	}

	public void setAppointee(Doctor appointee) {
		this.appointee = appointee;
	}

	public boolean getSurgery() {
		return surgery;
	}

	public void setSurgery(boolean surgery) {
		this.surgery = surgery;
	}

	public boolean getMedicine() {
		return medicine;
	}

	public void setMedicine(boolean medicine) {
		this.medicine = medicine;
	}

	public boolean getProcedure() {
		return procedure;
	}

	public void setProcedure(boolean procedure) {
		this.procedure = procedure;
	}

	public MedicalTreatment getTreatment() {
		return treatment;
	}


	public void setTreatment(MedicalTreatment treatment) {
		this.treatment = treatment;
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
		int result = appointee.hashCode() * 3 + appointmentExecutor.hashCode() * 5 + (medicine ? -100 : 100)
				+ (procedure ? -200 : 200) + (surgery ? -500 : 500) + treatment.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "[APPOINTEE:   " + appointee + ",   EXECUTOR:   " + appointmentExecutor + ",   RESOURCES:   medicine : "
				+ medicine + " | procedure : " + procedure + " | surgery : " + surgery + ",   TREATMENT: " + treatment + "]";
	}

}
