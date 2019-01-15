package by.epam.training.web.bean;

import java.io.Serializable;

public class Patient extends User implements Serializable {

	private static final long serialVersionUID = 2267299134657869647L;

	private String admissionDate;
	private int attendedDoctorId;
	private boolean dischargeStatus;
	private PatientDischargeInfo dischargeInfo;

	public Patient() {
		super();
		super.setUserType(User.patientConst);
	}

	public Patient(int userID, String login, String password, String language, String firstName, String lastName, String birthDate,
			String admissionDate, int attendedDoctorId, boolean dischargeStatus) {
		super(userID, login, password, language, firstName, lastName, birthDate);
		super.setUserType(User.patientConst);
		this.setAdmissionDate(admissionDate);
		this.attendedDoctorId = attendedDoctorId;
		this.dischargeStatus = dischargeStatus;
	}

	public boolean isDischargeStatus() {
		return dischargeStatus;
	}

	public void setDischargeStatus(boolean dischargeStatus) {
		this.dischargeStatus = dischargeStatus;
	}

	public PatientDischargeInfo getDischargeInfo() {
		return dischargeInfo;
	}

	public void setDischargeInfo(PatientDischargeInfo dischargeInfo) {
		this.dischargeInfo = dischargeInfo;
	}

	public String getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}

	public int getAttendedDoctorId() {
		return attendedDoctorId;
	}

	public void setAttendedDoctorId(int attendedDoctorId) {
		this.attendedDoctorId = attendedDoctorId;
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
		int result = super.hashCode() + admissionDate.compareTo(sampleHashcodeString);
		return result;
	}

	@Override
	public String toString() {
		return super.toString() + ",  ADMISSION_DATE: " + admissionDate;
	}
}
