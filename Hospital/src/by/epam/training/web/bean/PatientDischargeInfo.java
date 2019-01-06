package by.epam.training.web.bean;

import java.io.Serializable;
import java.util.Date;

public class PatientDischargeInfo implements Serializable {

	private static final long serialVersionUID = -554758722344270457L;

	private Date dischargeDate;
	private String diagnosis;
	private String finalDiagnosis;

	public PatientDischargeInfo() {
		super();
	}

	public PatientDischargeInfo(Date dischargeDate, String diagnosis, String finalDiagnosis) {
		this.dischargeDate = dischargeDate;
		this.diagnosis = diagnosis;
		this.finalDiagnosis = finalDiagnosis;
	}
	
	public Date getDischargeDate() {
		return dischargeDate;
	}

	public void setDischargeDate(Date dischargeDate) {
		this.dischargeDate = dischargeDate;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
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
		int result = 0;
		result += dischargeDate.getTime();
		for (int i = 0; i < diagnosis.length(); i++) {
			result += diagnosis.charAt(i) * 5;
		}
		for (int i = 0; i < finalDiagnosis.length(); i++) {
			result += finalDiagnosis.charAt(i) * 10;
		}
		return result;
	}

	@Override
	public String toString() {
		return "Discharge date: " + dischargeDate + ",   diagnosis: " + diagnosis + ",   final diagnosis: " + finalDiagnosis;
	}

}
