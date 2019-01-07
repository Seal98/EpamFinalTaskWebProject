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

	public static final String insertUserDBQuery = "INSERT into users(login, password) values(?, ?)";
	public static final String getAttendedDoctor = "select * from doctors where id = (select attended_doctor_id from patients where login_data_id=?)";
	public static final String getPatientAppointmentsNursesExecutors = "select a.id, a.appointed_doctor_id, a.procedures, a.medicine, a.surgery, a.completion_status, n.nurse_id from appointments a inner join nurses_executors n on n.appointment_id = a.id where patient_id = (select id from patients where login_data_id=?)";
	public static final String getPatientAppointmentsDoctorsExecutors = "select a.id, a.appointed_doctor_id, a.procedures, a.medicine, a.surgery, a.completion_status, n.doctor_id from appointments a inner join doctors_executors n on n.appointment_id = a.id where patient_id = (select id from patients where login_data_id=?);";
	public static final String insertPatientDBQuery = "INSERT into patients(first_name, last_name, birthdate, admission_date, attended_doctor_id, login_data_id) values(?, ?, ?, ?, ?, ?)";
	public static final String selectUserByIdDBQuery = "select * from users  where id = ?";
	public static final String selectDoctorByIdQuery = "select * from doctors where login_data_id = ?";
	public static final String getTherapistsDBQuery = "select u.id from users u inner join doctors d where u.id = d.login_data_id and d.speciality = 'therapist'";
	public static final String idConst = "id";
	public static final String loginConst = "login";
	public static final String passwordConst = "password";
	public static final String typeConst = "type";
	public static final String firstNameConst = "first_name";
	public static final String lastNameConst = "last_name";
	public static final String birthdateConst = "birthdate";
	public static final String specialityConst = "speciality";
	public static final String experienceConst = "experience";
	public static final String loginDataIdFKConst = "login_data_id";
	public static final String admissionDateConst = "admission_date";
	public static final String attendedDoctorIdConst = "attended_doctor_id";
	public static final String dischargeStatusConst = "discharge_status";

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
		} catch (SQLException e) {
			throw new DAOException(e);
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
		} finally {
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
			statement = connection.prepareStatement(
					"select login_data_id from patients where attended_doctor_id = (select id from doctors where login_data_id = ?)");
			statement.setInt(1, therapistId);
			ResultSet patients = statement.executeQuery();
			attendedPatients = new LinkedList<>();
			while (patients.next()) {
				for (User patient : users) {
					if (patient.getUserId() == patients.getInt("login_data_id")) {
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
			if (executorType.compareTo("NURSE") == 0 || (executorType.compareTo("DOCTOR") == 0
					&& ((Doctor) executor).getSpecialization().toUpperCase().compareTo("THERAPIST") != 0)) {
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
			preparedStmt = connection.prepareStatement("select id from doctors where login_data_id=?");
			preparedStmt.setInt(1, doctorId);
			ResultSet doctorSet = preparedStmt.executeQuery();
			doctorSet.next();
			int doctorIdInfo = doctorSet.getInt("id");
			preparedStmt.close();
			preparedStmt = connection.prepareStatement("select id from patients where login_data_id=?");
			preparedStmt.setInt(1, patientId);
			ResultSet patientInfoSet = preparedStmt.executeQuery();
			patientInfoSet.next();
			int patientInfoId = patientInfoSet.getInt("id");
			preparedStmt.close();
			preparedStmt = connection.prepareStatement(
					"INSERT into appointments(patient_id, appointed_doctor_id, executor_type, procedures, medicine, surgery) values(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			userTypeStmt = connection.prepareStatement("select type from users where id=?");
			userTypeStmt.setInt(1, executorId);
			ResultSet usersSet = userTypeStmt.executeQuery();
			usersSet.next();
			String executorType = usersSet.getString("type");
			connection.setAutoCommit(false);
			preparedStmt.setInt(1, patientInfoId);
			preparedStmt.setInt(2, doctorIdInfo);
			preparedStmt.setString(3, executorType);
			preparedStmt.setBoolean(4, false);
			preparedStmt.setBoolean(5, false);
			preparedStmt.setBoolean(6, false);
			if (treatmentType.toUpperCase().compareTo("PROCEDURE") == 0) {
				preparedStmt.setBoolean(4, true);
			} else if (treatmentType.toUpperCase().compareTo("MEDICINE") == 0) {
				preparedStmt.setBoolean(5, true);
			} else if (treatmentType.toUpperCase().compareTo("SURGERY") == 0) {
				preparedStmt.setBoolean(6, true);
			}
			preparedStmt.executeUpdate();
			ResultSet result = preparedStmt.getGeneratedKeys();
			result.next();
			newAppointmentId = result.getInt(1);
			preparedStmt.close();
			setTreatmentDependencies(connection, newAppointmentId, treatmentType, treatment);
			int executorIdInfo = 0;
			if (executorType.toUpperCase().compareTo("NURSE") == 0) {
				preparedStmt = connection.prepareStatement("select id from nurses where login_data_id=?");
				preparedStmt.setInt(1, executorId);
				ResultSet executorSet = preparedStmt.executeQuery();
				executorSet.next();
				executorIdInfo = executorSet.getInt("id");
				executorsTableStmt = connection
						.prepareStatement("insert into nurses_executors(appointment_id, nurse_id) values(?, ?)");
			} else if (executorType.toUpperCase().compareTo("DOCTOR") == 0) {
				preparedStmt = connection.prepareStatement("select id from doctors where login_data_id=?");
				preparedStmt.setInt(1, executorId);
				ResultSet executorSet = preparedStmt.executeQuery();
				executorSet.next();
				executorIdInfo = executorSet.getInt("id");
				executorsTableStmt = connection
						.prepareStatement("insert into doctors_executors(appointment_id, doctor_id) values(?, ?)");
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
			connectionPool.putConnection(connection);
			try {
				preparedStmt.close();
				userTypeStmt.close();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
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
			therapistAppointmentsStmt = connection.prepareStatement(
					"select ap.id, ap.executor_type, ap.procedures, ap.medicine, ap.surgery, ap.completion_status, p.login_data_id, p.discharge_status from appointments ap left join patients p on ap.patient_id=p.id where appointed_doctor_id=(select id from doctors where login_data_id=?)");
			therapistAppointmentsStmt.setInt(1, therapistId);
			ResultSet appointmentsSet = therapistAppointmentsStmt.executeQuery();
			PreparedStatement activeStmt = null;
			while (appointmentsSet.next()) {
				Appointment appointment = new Appointment();
				appointment.setPatient((Patient) getUserById(appointmentsSet.getInt("login_data_id")));
				int appointmentId = appointmentsSet.getInt("id");
				String executorType = appointmentsSet.getString("executor_type");
				if (executorType.toUpperCase().compareTo("DOCTOR") == 0) {
					activeStmt = connection.prepareStatement(
							"select d.login_data_id from appointments ap inner join doctors_executors dex on ap.id=dex.appointment_id inner join doctors d on dex.doctor_id=d.id where ap.id=?");
				} else if (executorType.toUpperCase().compareTo("NURSE") == 0) {
					activeStmt = connection.prepareStatement(
							"select n.login_data_id from appointments ap inner join nurses_executors nex on ap.id=nex.appointment_id inner join nurses n on nex.nurse_id=n.id where ap.id=?");
				}
				activeStmt.setInt(1, appointmentId);
				ResultSet executorSet = activeStmt.executeQuery();
				executorSet.next();
				appointment.setAppointmentExecutor(getUserById(executorSet.getInt("login_data_id")));
				activeStmt.close();
				appointment.setTreatment(
						getMedicalTreatmentFromAppointmentSetElement(appointmentsSet, appointmentId, connection));
				appointment.setCompletionStatus(appointmentsSet.getString("completion_status"));
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
		if (appointmentsSet.getBoolean("procedures")) {
			treatment = new Procedure();
			treatmentStmt = connection.prepareStatement(
					"select p.name from appointments ap inner join appointed_procedures dep on ap.id=dep.appointment_id inner join procedures p on p.id=dep.procedure_id where ap.id=?");
		} else if (appointmentsSet.getBoolean("medicine")) {
			treatment = new Medicine();
			treatmentStmt = connection.prepareStatement(
					"select p.name from appointments ap inner join appointed_medicine dep on ap.id=dep.appointment_id inner join medicine p on p.id=dep.medicine_id where ap.id=?");
		} else if (appointmentsSet.getBoolean("surgery")) {
			treatment = new Surgery();
			treatmentStmt = connection.prepareStatement(
					"select p.name from appointments ap inner join appointed_surgeries dep on ap.id=dep.appointment_id inner join surgeries p on p.id=dep.surgery_id where ap.id=?");
		}
		treatmentStmt.setInt(1, appointmentId);
		ResultSet treatmentSet = treatmentStmt.executeQuery();
		treatmentSet.next();
		treatmentName = treatmentSet.getString("name");
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
			appointmentStmt = connection.prepareStatement(
					"select ap.id, ap.patient_id, ap.appointed_doctor_id, ap.procedures, ap.medicine, ap.surgery, ap.completion_status from doctors_executors de inner join appointments ap on de.appointment_id = ap.id where doctor_id = (select id from doctors where login_data_id = ?)");
			appointmentStmt.setInt(1, executorId);
			ResultSet appointmentsSet = appointmentStmt.executeQuery();
			while (appointmentsSet.next()) {
				Appointment appointment = new Appointment();
				int appointmentId = appointmentsSet.getInt("id");
				PreparedStatement patientStmt = connection
						.prepareStatement("select login_data_id, discharge_status from patients where id = ?");
				patientStmt.setInt(1, appointmentsSet.getInt("patient_id"));
				ResultSet patientSet = patientStmt.executeQuery();
				patientSet.next();
				if(patientSet.getBoolean("discharge_status")) {
					patientStmt.close();
					continue;
				}
				appointment.setPatient((Patient) getUserById(patientSet.getInt("login_Data_id")));
				patientSet.close();
				PreparedStatement doctorStmt = connection
						.prepareStatement("select login_data_id from doctors where id = ?");
				doctorStmt.setInt(1, appointmentsSet.getInt("appointed_doctor_id"));
				ResultSet doctorSet = doctorStmt.executeQuery();
				doctorSet.next();
				appointment.setAppointee((Doctor) getUserById(doctorSet.getInt("login_data_id")));
				appointment.setTreatment(
						getMedicalTreatmentFromAppointmentSetElement(appointmentsSet, appointmentId, connection));
				appointment.setCompletionStatus(appointmentsSet.getString("completion_status"));
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
			appointmentStmt = connection.prepareStatement(
					"select ap.id, ap.patient_id, ap.appointed_doctor_id, ap.procedures, ap.medicine, ap.surgery, ap.completion_status from nurses_executors ne inner join appointments ap on ne.appointment_id = ap.id where nurse_id = (select id from nurses where login_data_id = ?)");
			appointmentStmt.setInt(1, executorId);
			ResultSet appointmentsSet = appointmentStmt.executeQuery();
			while (appointmentsSet.next()) {
				Appointment appointment = new Appointment();
				int appointmentId = appointmentsSet.getInt("id");
				PreparedStatement patientStmt = connection
						.prepareStatement("select login_data_id, discharge_status from patients where id = ?");
				patientStmt.setInt(1, appointmentsSet.getInt("patient_id"));
				ResultSet patientSet = patientStmt.executeQuery();
				patientSet.next();
				if(patientSet.getBoolean("discharge_status")) {
					patientStmt.close();
					continue;
				}
				appointment.setPatient((Patient) getUserById(patientSet.getInt("login_Data_id")));
				patientSet.close();
				PreparedStatement doctorStmt = connection
						.prepareStatement("select login_data_id from doctors where id = ?");
				doctorStmt.setInt(1, appointmentsSet.getInt("appointed_doctor_id"));
				ResultSet doctorSet = doctorStmt.executeQuery();
				doctorSet.next();
				appointment.setAppointee((Doctor) getUserById(doctorSet.getInt("login_data_id")));
				appointment.setTreatment(
						getMedicalTreatmentFromAppointmentSetElement(appointmentsSet, appointmentId, connection));
				appointment.setCompletionStatus(appointmentsSet.getString("completion_status"));
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
				appointmentStmt = connection.prepareStatement("update appointments set completion_status = 'completed' where id = ?");
				appointmentStmt.setInt(1, appointmentId);
				appointmentStmt.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException(e);
			} catch (InterruptedException e) {
				throw new DAOException(e);
			}
	}
	
	@Override
	public void dischargePatient(int patientId, String diagnosis, String finalDiagnosis, Date currentDate) throws DAOException {
		Connection connection = null;
		PreparedStatement dischargeStatement = null;
		try {
			connection = connectionPool.getConnection();
			connection.setAutoCommit(false);
			dischargeStatement = connection.prepareStatement("insert into discharge_curing_info (discharge_date, diagnosis, final_diagnosis, patient_id) values (?, ?, ?, ?)");
			PreparedStatement patientStmt = connection.prepareStatement("select id from patients where login_data_id = ?");
			patientStmt.setInt(1, patientId);
			ResultSet patient = patientStmt.executeQuery();
			patient.next();
			int patientIdInfo = patient.getInt("id");
			patientStmt.close();
			dischargeStatement.setDate(1, currentDate);
			dischargeStatement.setString(2, diagnosis);
			dischargeStatement.setString(3, finalDiagnosis);
			dischargeStatement.setInt(4, patientIdInfo);
			patientStmt = connection.prepareStatement("update patients set discharge_status = 1 where id = ?");
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
			cancelStmt = connection.prepareStatement("update appointments set completion_status = 'canceled' where id = ?");
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
				therapists.add(getUserById(therapistsSet.getInt("id")));
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
		int doctorId = usersSet.getInt("login_data_id");
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
			PreparedStatement appointeeStmt = connection.prepareStatement(
					"select login_data_id from doctors where id = " + appointmentsSet.getInt("appointed_doctor_id"));
			PreparedStatement executorsStmt = connection.prepareStatement(
					"select login_data_id from nurses where id = " + appointmentsSet.getInt("nurse_id"));
			ResultSet appointee = appointeeStmt.executeQuery();
			ResultSet nurseExecutor = executorsStmt.executeQuery();
			nurseExecutor.next();
			appointee.next();
			Appointment appointment = new Appointment();
			appointment.setProcedure(appointmentsSet.getBoolean("procedures"));
			appointment.setMedicine(appointmentsSet.getBoolean("medicine"));
			appointment.setSurgery(appointmentsSet.getBoolean("surgery"));
			appointment.setCompletionStatus(appointmentsSet.getString("completion_status"));

			if (appointment.getProcedure()) {
				appointment.setTreatment(getProcedureForCuringInfo(appointmentsSet.getInt("id"), connection));
			} else {
				appointment.setTreatment(getMedicineForCuringInfo(appointmentsSet.getInt("id"), connection));
			}
			int appointeeId = appointee.getInt("login_data_id");
			int executorId = nurseExecutor.getInt("login_data_id");
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
			nurseExecutor.close();
		}
		activeStmt.close();
	}

	private void getPatientAppointmentsDoctorsExecutors(int userId, List<Appointment> patientAppointments,
			Connection connection) throws SQLException, DAOException {
		PreparedStatement activeStmt = connection.prepareStatement(getPatientAppointmentsDoctorsExecutors);
		activeStmt.setInt(1, userId);
		ResultSet appointmentsSet = activeStmt.executeQuery();
		while (appointmentsSet.next()) {
			PreparedStatement appointeeStmt = connection.prepareStatement(
					"select login_data_id from doctors where id = " + appointmentsSet.getInt("appointed_doctor_id"));
			PreparedStatement executorsStmt = connection.prepareStatement(
					"select login_data_id from doctors where id = " + appointmentsSet.getInt("doctor_id"));
			ResultSet appointee = appointeeStmt.executeQuery();
			ResultSet doctorExecutor = executorsStmt.executeQuery();
			doctorExecutor.next();
			appointee.next();
			Appointment appointment = new Appointment();
			appointment.setProcedure(appointmentsSet.getBoolean("procedures"));
			appointment.setMedicine(appointmentsSet.getBoolean("medicine"));
			appointment.setSurgery(appointmentsSet.getBoolean("surgery"));
			appointment.setCompletionStatus(appointmentsSet.getString("completion_status"));
			if (appointment.getProcedure()) {
				appointment.setTreatment(getProcedureForCuringInfo(appointmentsSet.getInt("id"), connection));
			} else if (appointment.getSurgery()) {
				appointment.setTreatment(getSurgeryForCuringInfo(appointmentsSet.getInt("id"), connection));
			} else {
				appointment.setTreatment(getMedicineForCuringInfo(appointmentsSet.getInt("id"), connection));
			}
			int appointeeId = appointee.getInt("login_data_id");
			int executorId = doctorExecutor.getInt("login_data_id");
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
		}
	}

	private void setTreatmentDependencies(Connection connection, int appointmentId, String treatmentType,
			String treatment) throws SQLException {
		if (treatmentType.toUpperCase().compareTo("PROCEDURE") == 0) {
			setProcedureDependencies(connection, appointmentId, treatment);
		} else if (treatmentType.toUpperCase().compareTo("MEDICINE") == 0) {
			setMedicineDependencies(connection, appointmentId, treatment);
		} else if (treatmentType.toUpperCase().compareTo("SURGERY") == 0) {
			setSurgeryDependencies(connection, appointmentId, treatment);
		}
	}

	private void setProcedureDependencies(Connection connection, int appointmentId, String treatment)
			throws SQLException {
		PreparedStatement procedureStmt = connection.prepareStatement("select id from procedures where name=?");
		treatment = treatment.substring(0, 1).toUpperCase() + treatment.substring(1).toLowerCase();
		System.out.println(treatment);
		procedureStmt.setString(1, treatment);
		ResultSet procedureIdSet = procedureStmt.executeQuery();
		procedureIdSet.next();
		int procedureId = procedureIdSet.getInt("id");
		PreparedStatement procedureDependencyStmt = connection
				.prepareStatement("insert into appointed_procedures(procedure_id, appointment_id) values(?, ?)");
		procedureDependencyStmt.setInt(1, procedureId);
		procedureDependencyStmt.setInt(2, appointmentId);
		procedureDependencyStmt.executeUpdate();
		procedureStmt.close();
		procedureDependencyStmt.close();
	}

	private void setSurgeryDependencies(Connection connection, int appointmentId, String treatment)
			throws SQLException {
		PreparedStatement surgeryStmt = connection.prepareStatement("select id from surgeries where name=?");
		treatment = treatment.substring(0, 1).toUpperCase() + treatment.substring(1).toLowerCase();
		System.out.println(treatment);
		surgeryStmt.setString(1, treatment);
		ResultSet procedureIdSet = surgeryStmt.executeQuery();
		procedureIdSet.next();
		int surgeryId = procedureIdSet.getInt("id");
		PreparedStatement surgeryDependencyStmt = connection
				.prepareStatement("insert into appointed_surgeries(surgery_id, appointment_id) values(?, ?)");
		surgeryDependencyStmt.setInt(1, surgeryId);
		surgeryDependencyStmt.setInt(2, appointmentId);
		surgeryDependencyStmt.executeUpdate();
		surgeryStmt.close();
		surgeryDependencyStmt.close();
	}

	private void setMedicineDependencies(Connection connection, int appointmentId, String treatment)
			throws SQLException {
		PreparedStatement medicineStmt = connection.prepareStatement("select id from medicine where name=?");
		treatment = treatment.substring(0, 1).toUpperCase() + treatment.substring(1).toLowerCase();
		System.out.println(treatment);
		medicineStmt.setString(1, treatment);
		ResultSet procedureIdSet = medicineStmt.executeQuery();
		procedureIdSet.next();
		int medicineId = procedureIdSet.getInt("id");
		PreparedStatement medicineDependencyStmt = connection
				.prepareStatement("insert into appointed_medicine(medicine_id, appointment_id) values(?, ?)");
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
			treatmentStmt = connection.prepareStatement(
					"select * from medicine where id = (select medicine_id from appointed_medicine where appointment_id="
							+ appointmentId + ")");
			ResultSet treatmentSet = treatmentStmt.executeQuery();
			treatmentSet.next();
			medicine.setName(treatmentSet.getString("name"));
			medicine.setIndications(treatmentSet.getString("indications"));
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
			treatmentStmt = connection.prepareStatement(
					"select * from procedures where id = (select procedure_id from appointed_procedures where appointment_id="
							+ appointmentId + ")");
			ResultSet treatmentSet = treatmentStmt.executeQuery();
			treatmentSet.next();
			procedure.setName(treatmentSet.getString("name"));
			procedure.setDescription(treatmentSet.getString("description"));
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
			treatmentStmt = connection.prepareStatement(
					"select * from surgeries where id = (select surgery_id from appointed_surgeries where appointment_id="
							+ appointmentId + ")");
			ResultSet treatmentSet = treatmentStmt.executeQuery();
			treatmentSet.next();
			surgery.setName(treatmentSet.getString("name"));
			surgery.setDuration(treatmentSet.getString("duration"));
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
			ResultSet loginSet = getLoginDataFromUsers(activeStmt, con, usersSet.getInt(loginDataIdFKConst));
			users.add(factory.createUser(usersSet, loginSet));
		}
	}

	private void loadNurses(Statement activeStmt, Connection con) throws SQLException, DAOException {
		ResultSet usersSet = activeStmt.executeQuery(getAllNursesDBQuery);
		factory = NurseFactory.getInstance();
		while (usersSet.next()) {
			ResultSet loginSet = getLoginDataFromUsers(activeStmt, con, usersSet.getInt(loginDataIdFKConst));
			users.add(factory.createUser(usersSet, loginSet));
		}
	}

	private void loadPatients(Statement activeStmt, Connection con) throws SQLException, DAOException {
		ResultSet usersSet = activeStmt.executeQuery(getAllPatientsDBQuery);
		factory = PatientFactory.getInstance();
		while (usersSet.next()) {
			ResultSet loginSet = getLoginDataFromUsers(activeStmt, con, usersSet.getInt(loginDataIdFKConst));
			Patient patient = (Patient)factory.createUser(usersSet, loginSet);
			if(usersSet.getBoolean("discharge_status")) {
				int patientIdInfo = usersSet.getInt("id");
				PreparedStatement userDischargeInfoStmt = con.prepareStatement("select * from discharge_curing_info where patient_id = ?");
				userDischargeInfoStmt.setInt(1, patientIdInfo);
				ResultSet dischargeInfoSet = userDischargeInfoStmt.executeQuery();
				dischargeInfoSet.next();
				Date dischargeDate = dischargeInfoSet.getDate("discharge_date");
				String diagnosis = dischargeInfoSet.getString("diagnosis");
				String finalDiagnosis = dischargeInfoSet.getString("final_diagnosis");
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
		ResultSet treatmentSet = activeStmt.executeQuery("select * from medicine");
		while (treatmentSet.next()) {
			treatment.add(new Medicine(treatmentSet.getString("name"), treatmentSet.getString("indications")));
		}
	}

	private void loadProcedures(Statement activeStmt) throws SQLException {
		ResultSet treatmentSet = activeStmt.executeQuery("select * from procedures");
		while (treatmentSet.next()) {
			treatment.add(new Procedure(treatmentSet.getString("name"), treatmentSet.getString("description")));
		}
	}

	private void loadSurgeries(Statement activeStmt) throws SQLException {
		ResultSet treatmentSet = activeStmt.executeQuery("select * from surgeries");
		while (treatmentSet.next()) {
			treatment.add(new Surgery(treatmentSet.getString("name"), treatmentSet.getString("duration")));
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
			Date admissionDate, int therapistId) throws DAOException, SQLException {
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
			int therapistIdInfo = doctorSet.getInt("id");
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
			insertUserStmt.close();
			insertPatientStmt.close();
			selectDoctorStmt.close();
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
