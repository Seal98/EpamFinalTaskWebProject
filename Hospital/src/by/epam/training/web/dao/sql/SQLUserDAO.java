package by.epam.training.web.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.LinkedList;
import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.bean.Appointment;
import by.epam.training.web.bean.Doctor;
import by.epam.training.web.bean.MedicalTreatment;
import by.epam.training.web.bean.Medicine;
import by.epam.training.web.bean.Patient;
import by.epam.training.web.bean.PatientCuringInfo;
import by.epam.training.web.bean.PatientDischargeInfo;
import by.epam.training.web.bean.Procedure;
import by.epam.training.web.bean.Surgery;
import by.epam.training.web.bean.User;
import by.epam.training.web.bean.factory.DoctorFactory;
import by.epam.training.web.bean.factory.NurseFactory;
import by.epam.training.web.bean.factory.PatientFactory;
import by.epam.training.web.bean.factory.UserFactory;
import by.epam.training.web.dao.UserDAO;
import by.epam.training.web.dao.connector.ConnectionPoolDAO;
import by.epam.training.web.exception.DAOException;

public class SQLUserDAO implements UserDAO {

	private static final ConnectionPoolDAO connectionPool = new ConnectionPoolDAO("jdbc:mysql://localhost:3306/webapp",
			"root", "root");
	private List<User> users = new LinkedList<>();
	private List<MedicalTreatment> treatment = new LinkedList<>();
	public static final String getAllUsersDBQuery = "SELECT * from users";
	public static final String getAllDoctorsDBQuery = "SELECT * from doctors";
	public static final String getAllPatientsDBQuery = "SELECT * from patients";
	public static final String getAllNursesDBQuery = "SELECT * from nurses";
	public static final String getMedicineQuert = "select * from medicine";
	public static final String getProcedures = "select * from procedures";
	public static final String getSurgeries = "select * from surgeries";
	public static final String indicationsConst = "indications";
	public static final String insertUserDBQuery = "INSERT into users(login, password) values(?, ?)";
	public static final String getAttendedDoctor = "select * from doctors where id = (select attended_doctor_id from patients where login_data_id=?)";
	public static final String getPatientAppointmentsNursesExecutors = "select a.id, a.appointed_doctor_id, a.procedures, a.medicine, a.surgery, a.completion_status, n.nurse_id from appointments a inner join nurses_executors n on n.appointment_id = a.id where patient_id = (select id from patients where login_data_id=?)";
	public static final String getPatientAppointmentsDoctorsExecutors = "select a.id, a.appointed_doctor_id, a.procedures, a.medicine, a.surgery, a.completion_status, n.doctor_id from appointments a inner join doctors_executors n on n.appointment_id = a.id where patient_id = (select id from patients where login_data_id=?);";
	public static final String insertPatientDBQuery = "INSERT into patients(first_name, last_name, birthdate, admission_date, attended_doctor_id, login_data_id) values(?, ?, ?, ?, ?, ?)";
	public static final String selectUserByIdDBQuery = "select * from users  where id = ?";
	public static final String selectDoctorByIdQuery = "select * from doctors where login_data_id = ?";
	public static final String getTherapistsDBQuery = "select u.id from users u inner join doctors d where u.id = d.login_data_id and d.speciality = 'therapist'";
	public static final String getPatientByAttendedDoctorId = "select login_data_id from patients where attended_doctor_id = (select id from doctors where login_data_id = ?)";
	public static final String userTypeDoctorUpperCase = "DOCTOR";
	public static final String userTypeNurseUpperCase = "NURSE";
	public static final String userTypePatientUpperCase = "PATIENT";
	public static final String specializationTherapistUpperCase = "THERAPIST";
	public static final String getDoctorIdInfoWithDoctorIdQuery = "select id from doctors where login_data_id=?";
	public static final String getPatientIdInfoWithPatientIdQuery = "select id from patients where login_data_id=?";
	public static final String loginDataIdConst = "login_data_id";
	public static final String idConst = "id";
	public static final String loginConst = "login";
	public static final String passwordConst = "password";
	public static final String typeConst = "type";
	public static final String nameConst = "name";
	public static final String firstNameConst = "first_name";
	public static final String lastNameConst = "last_name";
	public static final String birthdateConst = "birthdate";
	public static final String specialityConst = "speciality";
	public static final String experienceConst = "experience";
	public static final String admissionDateConst = "admission_date";
	public static final String attendedDoctorIdConst = "attended_doctor_id";
	public static final String dischargeStatusConst = "discharge_status";
	public static final String medicineUpperCaseConst = "MEDICINE";
	public static final String procedureUpperCaseConst = "PROCEDURE";
	public static final String surgeryUpperCaseConst = "SURGERY";
	public static final String medicineConst = "medicine";
	public static final String proceduresConst = "procedures";
	public static final String surgeryConst = "surgery";
	public static final String patientIdConst = "patient_id";
	public static final String nurseIdConst = "nurse_id";
	public static final String doctorIdConst = "doctor_id";
	public static final String dischargeDateConst = "discharge_date";
	public static final String diagnosisConst = "diagnosis";
	public static final String appointedDoctorIdConst = "appointed_doctor_id";
	public static final String therapistAppointmentsQueryInnerJoinPatients = "select ap.id, ap.executor_type, ap.procedures, ap.medicine, ap.surgery, ap.completion_status, p.login_data_id, p.discharge_status from appointments ap left join patients p on ap.patient_id=p.id where appointed_doctor_id=(select id from doctors where login_data_id=?)";
	public static final String therapistAppointmentsQueryInnerJoinDoctorsExecutors = "select d.login_data_id from appointments ap inner join doctors_executors dex on ap.id=dex.appointment_id inner join doctors d on dex.doctor_id=d.id where ap.id=?";
	public static final String therapistAppointmentsQueryInnerJoinNursesExecutors = "select n.login_data_id from appointments ap inner join nurses_executors nex on ap.id=nex.appointment_id inner join nurses n on nex.nurse_id=n.id where ap.id=?";
	public static final String insertAppointmentUpdate = "INSERT into appointments(patient_id, appointed_doctor_id, executor_type, procedures, medicine, surgery) values(?, ?, ?, ?, ?, ?)";
	public static final String getUserTypeQuery = "select type from users where id=?";
	public static final String getNurseIdInfoWithNurseIdQuery = "select id from nurses where login_data_id=?";
	public static final String insertIntoNursesExecutorsUpdate = "insert into nurses_executors(appointment_id, nurse_id) values(?, ?)";
	public static final String insertIntoDoctorsExecutorsUpdate = "insert into doctors_executors(appointment_id, doctor_id) values(?, ?)";
	public static final String executorTypeConst = "executor_type";
	public static final String completionStatusConst = "completion_status";
	public static final String getAppointmentsQueryInnerJoinProcedures = "select p.name from appointments ap inner join appointed_procedures dep on ap.id=dep.appointment_id inner join procedures p on p.id=dep.procedure_id where ap.id=?";
	public static final String getAppointmentsQueryInnerJoinMedicine = "select p.name from appointments ap inner join appointed_medicine dep on ap.id=dep.appointment_id inner join medicine p on p.id=dep.medicine_id where ap.id=?";
	public static final String getAppointmentsQueryInnerJoinSurgeries = "select p.name from appointments ap inner join appointed_surgeries dep on ap.id=dep.appointment_id inner join surgeries p on p.id=dep.surgery_id where ap.id=?";
	public static final String getDoctorAppointmentsInnerJoinExecutors = "select ap.id, ap.patient_id, ap.appointed_doctor_id, ap.procedures, ap.medicine, ap.surgery, ap.completion_status from doctors_executors de inner join appointments ap on de.appointment_id = ap.id where doctor_id = (select id from doctors where login_data_id = ?)";
	public static final String getIdAndDischargeStatusFromPatients = "select login_data_id, discharge_status from patients where id = ?";
	public static final String getNurseAppointmentsInnerJoinExecutors = "select ap.id, ap.patient_id, ap.appointed_doctor_id, ap.procedures, ap.medicine, ap.surgery, ap.completion_status from nurses_executors ne inner join appointments ap on ne.appointment_id = ap.id where nurse_id = (select id from nurses where login_data_id = ?)";
	public static final String getDoctorIdByDoctorIdInfo = "select login_data_id from doctors where id = ?";
	public static final String getDischargeCuringInfoById = "select * from discharge_curing_info where patient_id = ?";
	public static final String getNurseIdByNurseIdInfo = "select login_data_id from nurses where id = ?";
	public static final String changeCompletionStatusUpdate = "update appointments set completion_status = 'completed' where id = ?";
	public static final String changeDischargeStatusUpdate = "update patients set discharge_status = 1 where id = ?";
	public static final String cancelAppointmentUpdate = "update appointments set completion_status = 'canceled' where id = ?";
	public static final String createDischargeCuringInfoUpdate = "insert into discharge_curing_info (discharge_date, diagnosis, final_diagnosis, patient_id) values (?, ?, ?, ?)";
	public static final String getPatientIdInfoByPatientId = "select id from patients where login_data_id = ?";
	public static final String getIdFromProceduresByName = "select id from procedures where name=?";
	public static final String getIdFromSurgeriesByName = "select id from surgeries where name=?";
	public static final String getIdFromMedicineByName = "select id from medicine where name=?";
	public static final String insertIntoAppointedProceduresUpdate = "insert into appointed_procedures(procedure_id, appointment_id) values(?, ?)";
	public static final String insertIntoAppointedSurgeriesUpdate = "insert into appointed_surgeries(surgery_id, appointment_id) values(?, ?)";
	public static final String insertIntoAppointedMedicineUpdate = "insert into appointed_medicine(medicine_id, appointment_id) values(?, ?)";
	public static final String getProcedureByAppointedProcedureId = "select * from procedures where id = (select procedure_id from appointed_procedures where appointment_id=?)";
	public static final String getSurgeryByAppointedSurgeryId = "select * from surgeries where id = (select surgery_id from appointed_surgeries where appointment_id=?)";
	public static final String getMedicineByAppointedMedicineId = "select * from medicine where id = (select medicine_id from appointed_medicine where appointment_id=?)";
	public static final String descriptionConst = "description";
	public static final String durationConst = "duration";
	public static final String finalDiagnosisConst = "final_diagnosis";
	private UserFactory factory = null;

	private static Logger logger = LogManager.getLogger(SQLUserDAO.class);

	public SQLUserDAO() {
		super();
		loadUsers();
	}

	@Override
	public User signIn(String login, String password) throws DAOException {
		User existingUser = getExistingUser(login, password);
		if (existingUser == null) {
			throw new DAOException(UserDAO.userNotFoundMessage);
		}
		return existingUser;
	}

	@Override
	public void registration(String login, String password, String firstName, String lastName, Date birthDate,
			Date admissionDate, int therapistId) throws DAOException {
		try {
			User existingUser = getExistingUser(login);
			if (existingUser != null) {
				throw new DAOException(UserDAO.userAlreadyExistMessage);
			} else {
				createUser(login, password, firstName, lastName, birthDate, admissionDate, therapistId);
			}
		} catch (DAOException e) {
			throw e;
		}
	}

	@Override
	public PatientCuringInfo getUserInfo(int userId) throws DAOException {
		PatientCuringInfo curingInfo = new PatientCuringInfo();
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
			setAttendedDoctor(curingInfo, userId, connection);
			List<Appointment> patientAppointments = new LinkedList<>();
			getPatientAppointmentsNursesExecutors(userId, patientAppointments, connection);
			getPatientAppointmentsDoctorsExecutors(userId, patientAppointments, connection);
			curingInfo.setAppointments(patientAppointments);
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (InterruptedException e) {
			throw new DAOException(e);
		}finally {
			connectionPool.putConnection(connection);
		}
		return curingInfo;
	}

	@Override
	public List<Patient> getAttendedPatients(int therapistId) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		List<Patient> attendedPatients = null;
		try {
			connection = connectionPool.getConnection();
			statement = connection.prepareStatement(getPatientByAttendedDoctorId);
			statement.setInt(1, therapistId);
			ResultSet patients = statement.executeQuery();
			attendedPatients = new LinkedList<>();
			while (patients.next()) {
				for (User patient : users) {
					if (patient.getUserId() == patients.getInt(loginDataIdConst)) {
						attendedPatients.add((Patient) patient);
					}
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (InterruptedException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.putConnection(connection);
			try {
				statement.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		return attendedPatients;
	}

	@Override
	public List<User> getExecutors() {
		List<User> executors = new LinkedList<>();
		for (User executor : users) {
			String executorType = executor.getClass().getSimpleName().toUpperCase();
			if (executorType.compareTo(userTypeNurseUpperCase) == 0 || (executorType.compareTo(userTypeDoctorUpperCase) == 0
					&& ((Doctor) executor).getSpecialization().toUpperCase().compareTo(specializationTherapistUpperCase) != 0)) {
				executors.add(executor);
			}
		}
		return executors;
	}

	@Override
	public List<MedicalTreatment> getMedicalTreatment() {
		return treatment;
	}

	@Override
	public int createAppointment(int patientId, int executorId, String treatmentType, String treatment, int doctorId)
			throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStmt = null;
		PreparedStatement userTypeStmt = null;
		PreparedStatement executorsTableStmt = null;
		int newAppointmentId = -1;
		try {
			connection = connectionPool.getConnection();
			preparedStmt = connection.prepareStatement(getDoctorIdInfoWithDoctorIdQuery);
			preparedStmt.setInt(1, doctorId);
			ResultSet doctorSet = preparedStmt.executeQuery();
			doctorSet.next();
			int doctorIdInfo = doctorSet.getInt(idConst);
			preparedStmt.close();
			preparedStmt = connection.prepareStatement(getPatientIdInfoWithPatientIdQuery);
			preparedStmt.setInt(1, patientId);
			ResultSet patientInfoSet = preparedStmt.executeQuery();
			patientInfoSet.next();
			int patientInfoId = patientInfoSet.getInt(idConst);
			preparedStmt.close();
			preparedStmt = connection.prepareStatement(insertAppointmentUpdate,	Statement.RETURN_GENERATED_KEYS);
			userTypeStmt = connection.prepareStatement(getUserTypeQuery);
			userTypeStmt.setInt(1, executorId);
			ResultSet usersSet = userTypeStmt.executeQuery();
			usersSet.next();
			String executorType = usersSet.getString(typeConst);
			connection.setAutoCommit(false);
			preparedStmt.setInt(1, patientInfoId);
			preparedStmt.setInt(2, doctorIdInfo);
			preparedStmt.setString(3, executorType);
			preparedStmt.setBoolean(4, false);
			preparedStmt.setBoolean(5, false);
			preparedStmt.setBoolean(6, false);
			if (treatmentType.toUpperCase().compareTo(procedureUpperCaseConst) == 0) {
				preparedStmt.setBoolean(4, true);
			} else if (treatmentType.toUpperCase().compareTo(medicineUpperCaseConst) == 0) {
				preparedStmt.setBoolean(5, true);
			} else if (treatmentType.toUpperCase().compareTo(surgeryUpperCaseConst) == 0) {
				preparedStmt.setBoolean(6, true);
			}
			preparedStmt.executeUpdate();
			ResultSet result = preparedStmt.getGeneratedKeys();
			result.next();
			newAppointmentId = result.getInt(1);
			preparedStmt.close();
			setTreatmentDependencies(connection, newAppointmentId, treatmentType, treatment);
			int executorIdInfo = 0;
			if (executorType.toUpperCase().compareTo(userTypeNurseUpperCase) == 0) {
				preparedStmt = connection.prepareStatement(getNurseIdInfoWithNurseIdQuery);
				preparedStmt.setInt(1, executorId);
				ResultSet executorSet = preparedStmt.executeQuery();
				executorSet.next();
				executorIdInfo = executorSet.getInt(idConst);
				executorsTableStmt = connection.prepareStatement(insertIntoNursesExecutorsUpdate);
			} else if (executorType.toUpperCase().compareTo(userTypeDoctorUpperCase) == 0) {
				preparedStmt = connection.prepareStatement(getDoctorIdInfoWithDoctorIdQuery);
				preparedStmt.setInt(1, executorId);
				ResultSet executorSet = preparedStmt.executeQuery();
				executorSet.next();
				executorIdInfo = executorSet.getInt(idConst);
				executorsTableStmt = connection.prepareStatement(insertIntoDoctorsExecutorsUpdate);
			}
			executorsTableStmt.setInt(1, newAppointmentId);
			executorsTableStmt.setInt(2, executorIdInfo);
			executorsTableStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (InterruptedException e) {
			throw new DAOException(e);
		} finally {
			try {
				preparedStmt.close();
				executorsTableStmt.close();
				userTypeStmt.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
			connectionPool.putConnection(connection);
		}
		return newAppointmentId;
	}

	@Override
	public List<Appointment> getTherapistAppointments(int therapistId) throws DAOException {
		Connection connection = null;
		PreparedStatement therapistAppointmentsStmt = null;
		List<Appointment> appointments = new LinkedList<>();
		try {
			connection = connectionPool.getConnection();
			therapistAppointmentsStmt = connection.prepareStatement(therapistAppointmentsQueryInnerJoinPatients);
			therapistAppointmentsStmt.setInt(1, therapistId);
			ResultSet appointmentsSet = therapistAppointmentsStmt.executeQuery();
			PreparedStatement activeStmt = null;
			while (appointmentsSet.next()) {
				Appointment appointment = new Appointment();
				appointment.setPatient((Patient) getUserById(appointmentsSet.getInt(loginDataIdConst)));
				int appointmentId = appointmentsSet.getInt(idConst);
				String executorType = appointmentsSet.getString(executorTypeConst);
				if (executorType.toUpperCase().compareTo(userTypeDoctorUpperCase) == 0) {
					activeStmt = connection.prepareStatement(therapistAppointmentsQueryInnerJoinDoctorsExecutors);
				} else if (executorType.toUpperCase().compareTo(userTypeNurseUpperCase) == 0) {
					activeStmt = connection.prepareStatement(therapistAppointmentsQueryInnerJoinNursesExecutors);
				}
				activeStmt.setInt(1, appointmentId);
				ResultSet executorSet = activeStmt.executeQuery();
				executorSet.next();
				appointment.setAppointmentExecutor(getUserById(executorSet.getInt(loginDataIdConst)));
				activeStmt.close();
				appointment.setTreatment(
						getMedicalTreatmentFromAppointmentSetElement(appointmentsSet, appointmentId, connection));
				appointment.setCompletionStatus(appointmentsSet.getString(completionStatusConst));
				appointment.setAppointmentId(appointmentId);
				appointments.add(appointment);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (InterruptedException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.putConnection(connection);
			try {
				therapistAppointmentsStmt.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		return appointments;
	}

	private MedicalTreatment getMedicalTreatmentFromAppointmentSetElement(ResultSet appointmentsSet, int appointmentId,
			Connection connection) throws SQLException {
		String treatmentName = null;
		PreparedStatement treatmentStmt = null;
		MedicalTreatment treatment = null;
		if (appointmentsSet.getBoolean(proceduresConst)) {
			treatment = new Procedure();
			treatmentStmt = connection.prepareStatement(getAppointmentsQueryInnerJoinProcedures);
		} else if (appointmentsSet.getBoolean(medicineConst)) {
			treatment = new Medicine();
			treatmentStmt = connection.prepareStatement(getAppointmentsQueryInnerJoinMedicine);
		} else if (appointmentsSet.getBoolean(surgeryConst)) {
			treatment = new Surgery();
			treatmentStmt = connection.prepareStatement(getAppointmentsQueryInnerJoinSurgeries);
		}
		treatmentStmt.setInt(1, appointmentId);
		ResultSet treatmentSet = treatmentStmt.executeQuery();
		treatmentSet.next();
		treatmentName = treatmentSet.getString(nameConst);
		treatment.setName(treatmentName);
		return treatment;
	}

	@Override
	public List<Appointment> getDoctorAppointments(int executorId) throws DAOException {
		List<Appointment> appointments = new LinkedList<>();
		Connection connection = null;
		PreparedStatement appointmentStmt = null;
		try {
			connection = connectionPool.getConnection();
			appointmentStmt = connection.prepareStatement(getDoctorAppointmentsInnerJoinExecutors);
			appointmentStmt.setInt(1, executorId);
			ResultSet appointmentsSet = appointmentStmt.executeQuery();
			while (appointmentsSet.next()) {
				Appointment appointment = new Appointment();
				int appointmentId = appointmentsSet.getInt(idConst);
				PreparedStatement patientStmt = connection.prepareStatement(getIdAndDischargeStatusFromPatients);
				patientStmt.setInt(1, appointmentsSet.getInt(patientIdConst));
				ResultSet patientSet = patientStmt.executeQuery();
				patientSet.next();
				if(patientSet.getBoolean(dischargeStatusConst)) {
					patientStmt.close();
					continue;
				}
				appointment.setPatient((Patient) getUserById(patientSet.getInt(loginDataIdConst)));
				patientSet.close();
				PreparedStatement doctorStmt = connection
						.prepareStatement(getDoctorIdByDoctorIdInfo);
				doctorStmt.setInt(1, appointmentsSet.getInt(appointedDoctorIdConst));
				ResultSet doctorSet = doctorStmt.executeQuery();
				doctorSet.next();
				appointment.setAppointee((Doctor) getUserById(doctorSet.getInt(loginDataIdConst)));
				appointment.setTreatment(
						getMedicalTreatmentFromAppointmentSetElement(appointmentsSet, appointmentId, connection));
				appointment.setCompletionStatus(appointmentsSet.getString(completionStatusConst));
				appointment.setAppointmentId(appointmentId);
				appointments.add(appointment);
				doctorStmt.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (InterruptedException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.putConnection(connection);
			try {
				appointmentStmt.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		return appointments;
	}

	@Override
	public List<Appointment> getNurseAppointments(int executorId) throws DAOException {
		List<Appointment> appointments = new LinkedList<>();
		Connection connection = null;
		PreparedStatement appointmentStmt = null;
		try {
			connection = connectionPool.getConnection();
			appointmentStmt = connection.prepareStatement(getNurseAppointmentsInnerJoinExecutors);
			appointmentStmt.setInt(1, executorId);
			ResultSet appointmentsSet = appointmentStmt.executeQuery();
			while (appointmentsSet.next()) {
				Appointment appointment = new Appointment();
				int appointmentId = appointmentsSet.getInt(idConst);
				PreparedStatement patientStmt = connection
						.prepareStatement(getIdAndDischargeStatusFromPatients);
				patientStmt.setInt(1, appointmentsSet.getInt(patientIdConst));
				ResultSet patientSet = patientStmt.executeQuery();
				patientSet.next();
				if(patientSet.getBoolean(dischargeStatusConst)) {
					patientStmt.close();
					continue;
				}
				appointment.setPatient((Patient) getUserById(patientSet.getInt(loginDataIdConst)));
				patientSet.close();
				PreparedStatement doctorStmt = connection
						.prepareStatement(getDoctorIdByDoctorIdInfo);
				doctorStmt.setInt(1, appointmentsSet.getInt(appointedDoctorIdConst));
				ResultSet doctorSet = doctorStmt.executeQuery();
				doctorSet.next();
				appointment.setAppointee((Doctor) getUserById(doctorSet.getInt(loginDataIdConst)));
				appointment.setTreatment(
						getMedicalTreatmentFromAppointmentSetElement(appointmentsSet, appointmentId, connection));
				appointment.setCompletionStatus(appointmentsSet.getString(completionStatusConst));
				appointment.setAppointmentId(appointmentId);
				appointments.add(appointment);
				doctorStmt.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (InterruptedException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.putConnection(connection);
			try {
				appointmentStmt.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		return appointments;
	}

	@Override
	public void completeAppointment(int appointmentId) throws DAOException {
		Connection connection = null;
		PreparedStatement appointmentStmt = null;
			try {
				connection = connectionPool.getConnection();
				appointmentStmt = connection.prepareStatement(changeCompletionStatusUpdate);
				appointmentStmt.setInt(1, appointmentId);
				appointmentStmt.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException(e);
			} catch (InterruptedException e) {
				throw new DAOException(e);
			} finally {
				connectionPool.putConnection(connection);
				try {
					appointmentStmt.close();
				} catch (SQLException e) {
					throw new DAOException(e);
				}
			}
	}
	
	@Override
	public void dischargePatient(int patientId, String diagnosis, String finalDiagnosis, Date currentDate) throws DAOException {
		Connection connection = null;
		PreparedStatement dischargeStatement = null;
		try {
			connection = connectionPool.getConnection();
			connection.setAutoCommit(false);
			dischargeStatement = connection.prepareStatement(createDischargeCuringInfoUpdate);
			PreparedStatement patientStmt = connection.prepareStatement(getPatientIdInfoByPatientId);
			patientStmt.setInt(1, patientId);
			ResultSet patient = patientStmt.executeQuery();
			patient.next();
			int patientIdInfo = patient.getInt(idConst);
			patientStmt.close();
			dischargeStatement.setDate(1, currentDate);
			dischargeStatement.setString(2, diagnosis);
			dischargeStatement.setString(3, finalDiagnosis);
			dischargeStatement.setInt(4, patientIdInfo);
			patientStmt = connection.prepareStatement(changeDischargeStatusUpdate);
			patientStmt.setInt(1, patientIdInfo);
			patientStmt.executeUpdate();
			dischargeStatement.executeUpdate();
			connection.commit();
			Patient dischargedPatient = ((Patient)getUserById(patientId));
			dischargedPatient.setDischargeStatus(true);
			dischargedPatient.setDischargeInfo(new PatientDischargeInfo(new java.util.Date(currentDate.getTime()), diagnosis, finalDiagnosis));
			patientStmt.close();
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (InterruptedException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.putConnection(connection);
			try {
				dischargeStatement.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}
	
	@Override
	public void cancelAppointment(int appointmentId) throws DAOException {
		Connection connection = null;
		PreparedStatement cancelStmt = null;
		try {
			connection = connectionPool.getConnection();
			cancelStmt = connection.prepareStatement(cancelAppointmentUpdate);
			cancelStmt.setInt(1, appointmentId);
			cancelStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (InterruptedException e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<User> getTherapists() throws DAOException {
		Connection connection = null;
		Statement therapistsStmt = null;
		List<User> therapists = new LinkedList<>();
		try {
			connection = connectionPool.getConnection();
			therapistsStmt = connection.createStatement();
			ResultSet therapistsSet = therapistsStmt.executeQuery(getTherapistsDBQuery);
			while(therapistsSet.next()) {
				therapists.add(getUserById(therapistsSet.getInt(idConst)));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (InterruptedException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.putConnection(connection);
			try {
				therapistsStmt.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		return therapists;
	}
	
	private User getUserById(int userId) {
		User result = null;
		for (User user : users) {
			if (user.getUserId() == userId) {
				result = user;
				break;
			}
		}
		return result;
	}

	private void setAttendedDoctor(PatientCuringInfo curingInfo, int userId, Connection connection)
			throws SQLException {
		PreparedStatement activeStmt = connection.prepareStatement(getAttendedDoctor);
		activeStmt.setInt(1, userId);
		ResultSet usersSet = activeStmt.executeQuery();
		usersSet.next();
		int doctorId = usersSet.getInt(loginDataIdConst);
		Doctor attendedDoctor = null;
		for (User user : users) {
			if (user.getUserId() == doctorId) {
				attendedDoctor = (Doctor) user;
				break;
			}
		}
		curingInfo.setAttendedDoctor(attendedDoctor);
		activeStmt.close();
	}

	private void getPatientAppointmentsNursesExecutors(int userId, List<Appointment> patientAppointments,
			Connection connection) throws SQLException, DAOException {
		PreparedStatement activeStmt = connection.prepareStatement(getPatientAppointmentsNursesExecutors);
		activeStmt.setInt(1, userId);
		ResultSet appointmentsSet = activeStmt.executeQuery();
		while (appointmentsSet.next()) {
			PreparedStatement appointeeStmt = connection.prepareStatement(getDoctorIdByDoctorIdInfo);
			appointeeStmt.setInt(1, appointmentsSet.getInt(appointedDoctorIdConst));
			PreparedStatement executorsStmt = connection.prepareStatement(getNurseIdByNurseIdInfo);
			executorsStmt.setInt(1, appointmentsSet.getInt(nurseIdConst));
			ResultSet appointee = appointeeStmt.executeQuery();
			ResultSet nurseExecutor = executorsStmt.executeQuery();
			nurseExecutor.next();
			appointee.next();
			Appointment appointment = new Appointment();
			appointment.setProcedure(appointmentsSet.getBoolean(proceduresConst));
			appointment.setMedicine(appointmentsSet.getBoolean(medicineConst));
			appointment.setSurgery(appointmentsSet.getBoolean(surgeryConst));
			appointment.setCompletionStatus(appointmentsSet.getString(completionStatusConst));

			if (appointment.getProcedure()) {
				appointment.setTreatment(getProcedureForCuringInfo(appointmentsSet.getInt(idConst), connection));
			} else {
				appointment.setTreatment(getMedicineForCuringInfo(appointmentsSet.getInt(idConst), connection));
			}
			int appointeeId = appointee.getInt(loginDataIdConst);
			int executorId = nurseExecutor.getInt(loginDataIdConst);
			for (User user : users) {
				if (user.getUserId() == appointeeId) {
					appointment.setAppointee((Doctor) user);
					break;
				}
			}
			for (User user : users) {
				if (user.getUserId() == executorId) {
					appointment.setAppointmentExecutor(user);
					break;
				}
			}
			patientAppointments.add(appointment);
			appointeeStmt.close();
		}
		activeStmt.close();
	}

	private void getPatientAppointmentsDoctorsExecutors(int userId, List<Appointment> patientAppointments,
			Connection connection) throws SQLException, DAOException {
		PreparedStatement activeStmt = connection.prepareStatement(getPatientAppointmentsDoctorsExecutors);
		activeStmt.setInt(1, userId);
		ResultSet appointmentsSet = activeStmt.executeQuery();
		while (appointmentsSet.next()) {
			PreparedStatement appointeeStmt = connection.prepareStatement(getDoctorIdByDoctorIdInfo);
			appointeeStmt.setInt(1, appointmentsSet.getInt(appointedDoctorIdConst));
			PreparedStatement executorsStmt = connection.prepareStatement(getDoctorIdByDoctorIdInfo);
			executorsStmt.setInt(1, appointmentsSet.getInt(doctorIdConst));
			ResultSet appointee = appointeeStmt.executeQuery();
			ResultSet doctorExecutor = executorsStmt.executeQuery();
			doctorExecutor.next();
			appointee.next();
			Appointment appointment = new Appointment();
			appointment.setProcedure(appointmentsSet.getBoolean(proceduresConst));
			appointment.setMedicine(appointmentsSet.getBoolean(medicineConst));
			appointment.setSurgery(appointmentsSet.getBoolean(surgeryConst));
			appointment.setCompletionStatus(appointmentsSet.getString(completionStatusConst));
			if (appointment.getProcedure()) {
				appointment.setTreatment(getProcedureForCuringInfo(appointmentsSet.getInt(idConst), connection));
			} else if (appointment.getSurgery()) {
				appointment.setTreatment(getSurgeryForCuringInfo(appointmentsSet.getInt(idConst), connection));
			} else {
				appointment.setTreatment(getMedicineForCuringInfo(appointmentsSet.getInt(idConst), connection));
			}
			int appointeeId = appointee.getInt(loginDataIdConst);
			int executorId = doctorExecutor.getInt(loginDataIdConst);
			for (User user : users) {
				if (user.getUserId() == appointeeId) {
					appointment.setAppointee((Doctor) user);
					break;
				}
			}
			for (User user : users) {
				if (user.getUserId() == executorId) {
					appointment.setAppointmentExecutor(user);
					break;
				}
			}
			patientAppointments.add(appointment);
			appointeeStmt.close();
			doctorExecutor.close();
			executorsStmt.close();
		}
		activeStmt.close();
	}

	private void setTreatmentDependencies(Connection connection, int appointmentId, String treatmentType,
			String treatment) throws SQLException {
		if (treatmentType.toUpperCase().compareTo(procedureUpperCaseConst) == 0) {
			setProcedureDependencies(connection, appointmentId, treatment);
		} else if (treatmentType.toUpperCase().compareTo(medicineUpperCaseConst) == 0) {
			setMedicineDependencies(connection, appointmentId, treatment);
		} else if (treatmentType.toUpperCase().compareTo(surgeryUpperCaseConst) == 0) {
			setSurgeryDependencies(connection, appointmentId, treatment);
		}
	}

	private void setProcedureDependencies(Connection connection, int appointmentId, String treatment)
			throws SQLException {
		PreparedStatement procedureStmt = connection.prepareStatement(getIdFromProceduresByName);
		treatment = treatment.substring(0, 1).toUpperCase() + treatment.substring(1).toLowerCase();
		System.out.println(treatment);
		procedureStmt.setString(1, treatment);
		ResultSet procedureIdSet = procedureStmt.executeQuery();
		procedureIdSet.next();
		int procedureId = procedureIdSet.getInt(idConst);
		PreparedStatement procedureDependencyStmt = connection.prepareStatement(insertIntoAppointedProceduresUpdate);
		procedureDependencyStmt.setInt(1, procedureId);
		procedureDependencyStmt.setInt(2, appointmentId);
		procedureDependencyStmt.executeUpdate();
		procedureStmt.close();
		procedureDependencyStmt.close();
	}

	private void setSurgeryDependencies(Connection connection, int appointmentId, String treatment)
			throws SQLException {
		PreparedStatement surgeryStmt = connection.prepareStatement(getIdFromSurgeriesByName);
		treatment = treatment.substring(0, 1).toUpperCase() + treatment.substring(1).toLowerCase();
		System.out.println(treatment);
		surgeryStmt.setString(1, treatment);
		ResultSet procedureIdSet = surgeryStmt.executeQuery();
		procedureIdSet.next();
		int surgeryId = procedureIdSet.getInt(idConst);
		PreparedStatement surgeryDependencyStmt = connection.prepareStatement(insertIntoAppointedSurgeriesUpdate);
		surgeryDependencyStmt.setInt(1, surgeryId);
		surgeryDependencyStmt.setInt(2, appointmentId);
		surgeryDependencyStmt.executeUpdate();
		surgeryStmt.close();
		surgeryDependencyStmt.close();
	}

	private void setMedicineDependencies(Connection connection, int appointmentId, String treatment)
			throws SQLException {
		PreparedStatement medicineStmt = connection.prepareStatement(getIdFromMedicineByName);
		treatment = treatment.substring(0, 1).toUpperCase() + treatment.substring(1).toLowerCase();
		System.out.println(treatment);
		medicineStmt.setString(1, treatment);
		ResultSet procedureIdSet = medicineStmt.executeQuery();
		procedureIdSet.next();
		int medicineId = procedureIdSet.getInt(idConst);
		PreparedStatement medicineDependencyStmt = connection.prepareStatement(insertIntoAppointedMedicineUpdate);
		medicineDependencyStmt.setInt(1, medicineId);
		medicineDependencyStmt.setInt(2, appointmentId);
		medicineDependencyStmt.executeUpdate();
		medicineStmt.close();
		medicineDependencyStmt.close();
	}

	private Medicine getMedicineForCuringInfo(int appointmentId, Connection connection)
			throws DAOException, SQLException {
		PreparedStatement treatmentStmt = null;
		Medicine medicine = new Medicine();
		try {
			treatmentStmt = connection.prepareStatement(getMedicineByAppointedMedicineId);
			treatmentStmt.setInt(1, appointmentId);
			ResultSet treatmentSet = treatmentStmt.executeQuery();
			treatmentSet.next();
			medicine.setName(treatmentSet.getString(nameConst));
			medicine.setIndications(treatmentSet.getString(indicationsConst));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			treatmentStmt.close();
		}
		return medicine;
	}

	private Procedure getProcedureForCuringInfo(int appointmentId, Connection connection)
			throws SQLException, DAOException {
		Procedure procedure = new Procedure();
		PreparedStatement treatmentStmt = null;
		try {
			treatmentStmt = connection.prepareStatement(getProcedureByAppointedProcedureId);
			treatmentStmt.setInt(1, appointmentId);
			ResultSet treatmentSet = treatmentStmt.executeQuery();
			treatmentSet.next();
			procedure.setName(treatmentSet.getString(nameConst));
			procedure.setDescription(treatmentSet.getString(descriptionConst));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			treatmentStmt.close();
		}
		return procedure;
	}

	private Surgery getSurgeryForCuringInfo(int appointmentId, Connection connection)
			throws DAOException, SQLException {
		PreparedStatement treatmentStmt = null;
		Surgery surgery = new Surgery();
		try {
			treatmentStmt = connection.prepareStatement(getSurgeryByAppointedSurgeryId);
			treatmentStmt.setInt(1, appointmentId);
			ResultSet treatmentSet = treatmentStmt.executeQuery();
			treatmentSet.next();
			surgery.setName(treatmentSet.getString(nameConst));
			surgery.setDuration(treatmentSet.getString(durationConst));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			treatmentStmt.close();
		}
		return surgery;
	}

	private void loadUsers() {
		Connection connection = null;
		Statement activeStmt = null;
		try {
			connection = connectionPool.getConnection();
			activeStmt = connection.createStatement();
			loadDoctors(activeStmt, connection);
			loadNurses(activeStmt, connection);
			loadPatients(activeStmt, connection);
			loadTreatment(activeStmt, connection);
		} catch (SQLException e) {
			logger.error(e);
		} catch (DAOException e) {
			logger.error(e);
		} catch (InterruptedException e) {
			logger.error(e);
		} finally {
			connectionPool.putConnection(connection);
			try {
				activeStmt.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

	private void loadDoctors(Statement activeStmt, Connection con) throws SQLException, DAOException {
		ResultSet usersSet = activeStmt.executeQuery(getAllDoctorsDBQuery);
		factory = DoctorFactory.getInstance();
		while (usersSet.next()) {
			ResultSet loginSet = getLoginDataFromUsers(activeStmt, con, usersSet.getInt(loginDataIdConst));
			users.add(factory.createUser(usersSet, loginSet));
		}
	}

	private void loadNurses(Statement activeStmt, Connection con) throws SQLException, DAOException {
		ResultSet usersSet = activeStmt.executeQuery(getAllNursesDBQuery);
		factory = NurseFactory.getInstance();
		while (usersSet.next()) {
			ResultSet loginSet = getLoginDataFromUsers(activeStmt, con, usersSet.getInt(loginDataIdConst));
			users.add(factory.createUser(usersSet, loginSet));
		}
	}

	private void loadPatients(Statement activeStmt, Connection con) throws SQLException, DAOException {
		ResultSet usersSet = activeStmt.executeQuery(getAllPatientsDBQuery);
		factory = PatientFactory.getInstance();
		while (usersSet.next()) {
			ResultSet loginSet = getLoginDataFromUsers(activeStmt, con, usersSet.getInt(loginDataIdConst));
			Patient patient = (Patient)factory.createUser(usersSet, loginSet);
			if(usersSet.getBoolean(dischargeStatusConst)) {
				int patientIdInfo = usersSet.getInt(idConst);
				PreparedStatement userDischargeInfoStmt = con.prepareStatement(getDischargeCuringInfoById);
				userDischargeInfoStmt.setInt(1, patientIdInfo);
				ResultSet dischargeInfoSet = userDischargeInfoStmt.executeQuery();
				dischargeInfoSet.next();
				Date dischargeDate = dischargeInfoSet.getDate(dischargeDateConst);
				String diagnosis = dischargeInfoSet.getString(diagnosisConst);
				String finalDiagnosis = dischargeInfoSet.getString(finalDiagnosisConst);
				patient.setDischargeInfo(new PatientDischargeInfo(new java.util.Date(dischargeDate.getTime()), diagnosis, finalDiagnosis));
				userDischargeInfoStmt.close();
			}
			users.add(patient);
		}
	}

	private void loadTreatment(Statement activeStmt, Connection con) throws SQLException, DAOException {
		loadMedicine(activeStmt);
		loadProcedures(activeStmt);
		loadSurgeries(activeStmt);
	}

	private void loadMedicine(Statement activeStmt) throws SQLException {
		ResultSet treatmentSet = activeStmt.executeQuery(getMedicineQuert);
		while (treatmentSet.next()) {
			treatment.add(new Medicine(treatmentSet.getString(nameConst), treatmentSet.getString(indicationsConst)));
		}
	}

	private void loadProcedures(Statement activeStmt) throws SQLException {
		ResultSet treatmentSet = activeStmt.executeQuery(getProcedures);
		while (treatmentSet.next()) {
			treatment.add(new Procedure(treatmentSet.getString(nameConst), treatmentSet.getString(descriptionConst)));
		}
	}

	private void loadSurgeries(Statement activeStmt) throws SQLException {
		ResultSet treatmentSet = activeStmt.executeQuery(getSurgeries);
		while (treatmentSet.next()) {
			treatment.add(new Surgery(treatmentSet.getString(nameConst), treatmentSet.getString(durationConst)));
		}
	}

	private ResultSet getLoginDataFromUsers(Statement activeStmt, Connection connection, int loginFKId)
			throws SQLException, DAOException {
		PreparedStatement insertStmt = connection.prepareStatement(selectUserByIdDBQuery);
		insertStmt.setInt(1, loginFKId);
		ResultSet result = insertStmt.executeQuery();
		if (result.next()) {
			return result;
		}
		throw new DAOException("User wasn't found among users");
	}

	private void createUser(String login, String password, String firstName, String lastName, Date birthdate,
			Date admissionDate, int therapistId) throws DAOException{
		Connection connection = null;
		PreparedStatement insertUserStmt = null;
		PreparedStatement insertPatientStmt = null;
		PreparedStatement selectDoctorStmt = null;
		try {
			try {
				connection = connectionPool.getConnection();
			} catch (InterruptedException e) {
				logger.error(e);
			}
			selectDoctorStmt = connection.prepareStatement(selectDoctorByIdQuery);
			selectDoctorStmt.setInt(1, therapistId);
			ResultSet doctorSet = selectDoctorStmt.executeQuery();
			doctorSet.next();
			int therapistIdInfo = doctorSet.getInt(idConst);
			connection.setAutoCommit(false);
			insertUserStmt = connection.prepareStatement(insertUserDBQuery, Statement.RETURN_GENERATED_KEYS);
			insertUserStmt.setString(1, login);
			insertUserStmt.setString(2, password);
			insertUserStmt.executeUpdate();
			insertPatientStmt = connection.prepareStatement(insertPatientDBQuery);
			ResultSet result = insertUserStmt.getGeneratedKeys();
			result.next();
			int lastId = result.getInt(1);
			insertPatientStmt.setString(1, firstName);
			insertPatientStmt.setString(2, lastName);
			insertPatientStmt.setDate(3, birthdate);
			insertPatientStmt.setDate(4, admissionDate);
			insertPatientStmt.setInt(5, therapistIdInfo);
			insertPatientStmt.setInt(6, lastId);
			insertPatientStmt.executeUpdate();
			users.add(new Patient(lastId, login, password, firstName, lastName, birthdate, admissionDate, therapistIdInfo, false));
			connection.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.putConnection(connection);
			try {
				insertPatientStmt.close();
				selectDoctorStmt.close();
				insertUserStmt.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}

	private User getExistingUser(String userLogin) {
		User existingUser = null;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserLogin().compareTo(userLogin) == 0) {
				existingUser = users.get(i);
			}
		}
		return existingUser;
	}

	private User getExistingUser(String userLogin, String userPassword) {
		User existingUser = null;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserLogin().compareTo(userLogin) == 0
					&& users.get(i).getUserPassword().compareTo(userPassword) == 0) {
				existingUser = users.get(i);
			}
		}
		return existingUser;
	}
}
