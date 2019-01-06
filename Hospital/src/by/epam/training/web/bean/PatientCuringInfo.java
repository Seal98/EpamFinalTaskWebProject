package by.epam.training.web.bean;

import java.io.Serializable;
import java.util.List;

public class PatientCuringInfo implements Serializable {

	private static final long serialVersionUID = -1800783388447929924L;

	private List<Appointment> appointments;
	private User attendedDoctor;

	public PatientCuringInfo() {
		super();
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
		for(Appointment app : appointments) {
			result += app.hashCode();
		}
		return result;
	}

	@Override
	public String toString() {
		return "Attended doctor: " + attendedDoctor + ",   appointments: " + appointments;
	}

}
