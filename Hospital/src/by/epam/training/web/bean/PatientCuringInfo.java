package by.epam.training.web.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PatientCuringInfo implements Serializable {

	private static final long serialVersionUID = -1800783388447929924L;

	private String diagnosis;
	private Date dischargeDate;
	private String finalDiagnosis;
	private List<Appointment> appointments;
	private User attendedDoctor;

	public PatientCuringInfo() {
		super();
	}

	public PatientCuringInfo(String diagnosis, Date dischargeDate, String finalDiagnosis) {
		this.diagnosis = diagnosis;
		this.dischargeDate = new Date(dischargeDate.getTime());
		this.finalDiagnosis = finalDiagnosis;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Date getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public String getFinalDiagnosis() {
		return finalDiagnosis;
	}

	public void setFinalDiagnosis(String finalDiagnosis) {
		this.finalDiagnosis = finalDiagnosis;
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
		int result = diagnosis.compareTo(User.sampleHashcodeString) * 5
				+ finalDiagnosis.compareTo(User.sampleHashcodeString) * 9 + (int) dischargeDate.getTime() / 50000;
		return result;
	}

	@Override
	public String toString() {
		return ",  DIAGNOSIS: " + diagnosis + ",  " + "DISCHARGE_DATE: " + dischargeDate + ",  "
				+ ",  FINAL_DIAGNOSIS: " + finalDiagnosis + ",  APPOINTMENTS: " + appointments;
	}

	public User getAttendedDoctor() {
		return attendedDoctor;
	}

	public void setAttendedDoctor(User attendedDoctor) {
		this.attendedDoctor = attendedDoctor;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
}
