package by.epam.training.web.dao;

import java.sql.Date;
import java.util.List;

import by.epam.training.web.bean.Appointment;
import by.epam.training.web.bean.MedicalTreatment;
import by.epam.training.web.bean.Patient;
import by.epam.training.web.bean.PatientCuringInfo;
import by.epam.training.web.bean.User;
import by.epam.training.web.exception.DAOException;

public interface UserDAO {

	public static final String userNotFoundMessage = "User not found - login or password is wrong";
	public static final String userAlreadyExistMessage = "User with such login already exists";

	public User signIn(String login, String password) throws DAOException;

	public void registration(String login, String password, String firstName, String lastName, Date birthdate,
			Date admissionDate) throws DAOException;

	public PatientCuringInfo getUserInfo(int userId) throws DAOException;
	
	public List<Patient> getAttendedPatients(int therapistId) throws DAOException;
	
	public List<User> getExecutors() throws DAOException;
	
	public List<MedicalTreatment> getMedicalTreatment();

	public void createAppointment(int patientId, int executorId, String treatmentType, String treatment, int doctorId) throws DAOException;

	public List<Appointment> getTherapistAppointments(int therapistId) throws DAOException;

	public List<Appointment> getNurseAppointments(int executorId) throws DAOException;

	public List<Appointment> getDoctorAppointments(int executorId) throws DAOException;

	public void completeAppointment(int parseInt) throws DAOException;

	public void dischargePatient(int patientId, String diagnosis, String finalDiagnosis, Date date) throws DAOException;
}
