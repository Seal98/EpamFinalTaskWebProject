package by.epam.training.web.bean;

import java.io.Serializable;
import java.util.Date;

public class Patient extends User implements Serializable {

	private static final long serialVersionUID = 2267299134657869647L;

	private Date admissionDate;
	private int attendedDoctorId;
	private boolean dischargeStatus;
	private PatientDischargeInfo dischargeInfo;

	public Patient() {
		super();
		super.setUserType("Patient");
	}

	public Patient(int userID, String login, String password, String firstName, String lastName, Date birthDate,
			Date admissionDate, int attendedDoctorId, boolean dischargeStatus) {
		super(userID, login, password, firstName, lastName, birthDate);
		super.setUserType("Patient");
		this.setAdmissionDate(new Date(admissionDate.getTime()));
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

	public Date getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
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
		int result = super.hashCode() + (int) admissionDate.getTime() / 50000;
		return result;
	}

	@Override
	public String toString() {
		return super.toString() + ",  ADMISSION_DATE: " + admissionDate;
	}
}
