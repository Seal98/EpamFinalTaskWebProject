package by.epam.training.web.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.bean.Appointment;
import by.epam.training.web.bean.Doctor;
import by.epam.training.web.bean.Medicine;
import by.epam.training.web.bean.Patient;
import by.epam.training.web.bean.PatientCuringInfo;
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
	private List<User> users = new ArrayList<>();
	public static final String getAllUsersDBQuery = "SELECT * from users";
	public static final String getAllDoctorsDBQuery = "SELECT * from doctors";
	public static final String getAllPatientsDBQuery = "SELECT * from patients";
	public static final String getAllNursesDBQuery = "SELECT * from nurses";

	public static final String insertUserDBQuery = "INSERT into users(login, password) values(?, ?)";
	public static final String getAttendedDoctor = "select * from doctors where id = (select attended_doctor_id from patients where login_data_id=?)";
	public static final String getPatientAppointmentsNursesExecutors = "select a.id, a.appointed_doctor_id, a.procedure, a.medicine, a.surgery, n.nurse_id from appointments a inner join nurses_executors n on n.appointment_id = a.id where patient_id = (select id from patients where login_data_id=?)";
	public static final String getPatientAppointmentsDoctorsExecutors = "select a.id, a.appointed_doctor_id, a.procedure, a.medicine, a.surgery, n.doctor_id from appointments a inner join doctors_executors n on n.appointment_id = a.id where patient_id = (select id from patients where login_data_id=?);";
	public static final String insertPatientDBQuery = "INSERT into patients(first_name, last_name, birthdate, admission_date, attended_doctor_id, login_data_id) values(?, ?, ?, ?, ?, ?)";
	public static final String selectUserByIdDBQuery = "select * from users  where id = ?";
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
			Date admissionDate) throws DAOException {
		try {
			User existingUser = getExistingUser(login);
			if (existingUser != null) {
				throw new DAOException(UserDAO.userAlreadyExistMessage);
			} else {
				createUser(login, password, firstName, lastName, birthDate, admissionDate);
			}
		} catch (DAOException e) {
			throw e;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public PatientCuringInfo getUserInfo(int userId) throws DAOException {
		PatientCuringInfo curingInfo = null;
		Connection connection = null;
		PreparedStatement activeStmt = null;
		try {
			connection = connectionPool.getConnection();
			activeStmt = connection.prepareStatement(getAttendedDoctor);
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
			curingInfo = new PatientCuringInfo();
			curingInfo.setAttendedDoctor(attendedDoctor);
			/*
			 * 
			 * FOR APPOINTMENTS
			 * 
			 */
			activeStmt.close();
			List<Appointment> patientAppointments = new LinkedList<>();
			activeStmt = connection.prepareStatement(getPatientAppointmentsNursesExecutors);
			activeStmt.setInt(1, userId);
			ResultSet appointmentsSet = activeStmt.executeQuery();
			while (appointmentsSet.next()) {
				PreparedStatement appointeeStmt = connection
						.prepareStatement("select login_data_id from doctors where id = "
								+ appointmentsSet.getInt("appointed_doctor_id"));
				PreparedStatement executorsStmt = connection.prepareStatement(
						"select login_data_id from nurses where id = " + appointmentsSet.getInt("nurse_id"));
				ResultSet appointee = appointeeStmt.executeQuery();
				ResultSet nurseExecutor = executorsStmt.executeQuery();
				nurseExecutor.next();
				appointee.next();
				Appointment appointment = new Appointment();
				appointment.setProcedure(appointmentsSet.getBoolean("procedure"));
				appointment.setMedicine(appointmentsSet.getBoolean("medicine"));

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

			activeStmt = connection.prepareStatement(getPatientAppointmentsDoctorsExecutors);
			activeStmt.setInt(1, userId);
			appointmentsSet = activeStmt.executeQuery();
			while (appointmentsSet.next()) {
				PreparedStatement appointeeStmt = connection
						.prepareStatement("select login_data_id from doctors where id = "
								+ appointmentsSet.getInt("appointed_doctor_id"));
				PreparedStatement executorsStmt = connection.prepareStatement(
						"select login_data_id from doctors where id = " + appointmentsSet.getInt("doctor_id"));
				ResultSet appointee = appointeeStmt.executeQuery();
				ResultSet doctorExecutor = executorsStmt.executeQuery();
				doctorExecutor.next();
				appointee.next();
				Appointment appointment = new Appointment();
				appointment.setProcedure(appointmentsSet.getBoolean("procedure"));
				appointment.setMedicine(appointmentsSet.getBoolean("medicine"));
				appointment.setSurgery(appointmentsSet.getBoolean("surgery"));
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
			activeStmt.close();
			curingInfo.setAppointments(patientAppointments);
		} catch (SQLException e) {
			throw new DAOException(e);
		} catch (InterruptedException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.putConnection(connection);
			try {
				activeStmt.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return curingInfo;
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
			users.add(factory.createUser(usersSet, loginSet));
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
			Date admissionDate) throws DAOException, SQLException {
		Connection connection = null;
		PreparedStatement insertUserStmt = null;
		PreparedStatement insertPatientStmt = null;
		try {
			try {
				connection = connectionPool.getConnection();
			} catch (InterruptedException e) {
				logger.error(e);
			}
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
			insertPatientStmt.setInt(5, 1);
			insertPatientStmt.setInt(6, lastId);
			insertPatientStmt.executeUpdate();
			users.add(new Patient(lastId, login, password, firstName, lastName, birthdate, admissionDate, 1));
			connection.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			connectionPool.putConnection(connection);
			insertUserStmt.close();
			insertPatientStmt.close();
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
