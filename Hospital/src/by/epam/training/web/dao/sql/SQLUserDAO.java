package by.epam.training.web.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.bean.Doctor;
import by.epam.training.web.bean.Nurse;
import by.epam.training.web.bean.Patient;
import by.epam.training.web.bean.User;
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

	private void loadUsers() {
		Connection connection = null;
		Statement activeStmt = null;
		try {
			try {
				connection = connectionPool.getConnection();
			} catch (InterruptedException e) {
				logger.error(e);
			}
			activeStmt = connection.createStatement();
			loadDoctors(activeStmt, connection);
			System.out.println("Size: " + users.size());
			loadNurses(activeStmt, connection);
			System.out.println("Size: " + users.size());
			loadPatients(activeStmt, connection);
			System.out.println("Size: " + users.size());
		} catch (SQLException e) {
			logger.error(e);
		} catch (DAOException e) {
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
		while (usersSet.next()) {
			ResultSet loginSet = getLoginDataFromUsers(activeStmt, con, usersSet.getInt(loginDataIdFKConst));
			users.add(new Doctor(usersSet.getInt(loginDataIdFKConst), loginSet.getString(loginConst),
					loginSet.getString(passwordConst), usersSet.getString(firstNameConst),
					usersSet.getString(lastNameConst), usersSet.getDate(birthdateConst),
					usersSet.getString(specialityConst), usersSet.getString(experienceConst)));
		}
	}

	private void loadNurses(Statement activeStmt, Connection con) throws SQLException, DAOException {
		ResultSet usersSet = activeStmt.executeQuery(getAllNursesDBQuery);
		while (usersSet.next()) {
			ResultSet loginSet = getLoginDataFromUsers(activeStmt, con, usersSet.getInt(loginDataIdFKConst));
			users.add(new Nurse(usersSet.getInt(loginDataIdFKConst), loginSet.getString(loginConst),
					loginSet.getString(passwordConst), usersSet.getString(firstNameConst),
					usersSet.getString(lastNameConst), usersSet.getDate(birthdateConst),
					usersSet.getString(experienceConst)));
		}
	}

	private void loadPatients(Statement activeStmt, Connection con) throws SQLException, DAOException {
		ResultSet usersSet = activeStmt.executeQuery(getAllPatientsDBQuery);
		while (usersSet.next()) {
			ResultSet loginSet = getLoginDataFromUsers(activeStmt, con, usersSet.getInt(loginDataIdFKConst));
			users.add(new Patient(usersSet.getInt(loginDataIdFKConst), loginSet.getString(loginConst),
					loginSet.getString(passwordConst), usersSet.getString(firstNameConst),
					usersSet.getString(lastNameConst), usersSet.getDate(birthdateConst),
					usersSet.getDate(admissionDateConst), usersSet.getInt(attendedDoctorIdConst)));
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
			//////////////////////////////
			System.out.println("LAST ID: " + lastId);
			//////////////////////////////
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
		System.out.println(users.size());
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i));
			if (users.get(i).getUserLogin().compareTo(userLogin) == 0
					&& users.get(i).getUserPassword().compareTo(userPassword) == 0) {
				existingUser = users.get(i);
			}
		}
		return existingUser;
	}
}
