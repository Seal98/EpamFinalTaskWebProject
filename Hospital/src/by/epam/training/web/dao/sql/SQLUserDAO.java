package by.epam.training.web.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.training.web.bean.User;
import by.epam.training.web.dao.UserDAO;
import by.epam.training.web.dao.connector.ConnectionPoolDAO;
import by.epam.training.web.exception.DAOException;

public class SQLUserDAO implements UserDAO {

	private static final ConnectionPoolDAO connectionPool = new ConnectionPoolDAO("jdbc:mysql://localhost:3306/webapp",
			"root", "root");
	private List<User> users = new ArrayList<>();
	public static final String getAllUsersDBQuery = "SELECT * from users";
	public static final String insertUserDBQuery = "INSERT into users(login, password) values(?, ?)";
	public static final String idConst = "id";
	public static final String loginConst = "login";
	public static final String passwordConst = "password";
	public static final String typeConst = "type";

    private static Logger logger = LogManager.getLogger(SQLUserDAO.class);
	
	public SQLUserDAO() {
		super();
		Connection con = null;
		Statement getUsersStmt = null;
		try {
			con = connectionPool.getConnection();
			getUsersStmt = con.createStatement();
			ResultSet usersSet = getUsersStmt.executeQuery(getAllUsersDBQuery);
			while (usersSet.next()) {
				users.add(new User(usersSet.getInt(idConst), usersSet.getString(loginConst),
						usersSet.getString(passwordConst), usersSet.getString(typeConst)));
			}
		} catch (SQLException e) {
			logger.info(e.getMessage());
		} finally {
			connectionPool.putConnection(con);
			try {
				getUsersStmt.close();
			} catch (SQLException e) {
				logger.info(e.getMessage());
			}
		}
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
	public void registration(String login, String password) throws DAOException {
		try {
			User existingUser = getExistingUser(login);
			if (existingUser != null) {
				throw new DAOException(UserDAO.userAlreadyExistMessage);
			} else {
				createUser(login, password);
				users.add(new User(users.size() + 1, login, password));
			}
		} catch (DAOException e) {
			throw e;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	private void createUser(String login, String password) throws DAOException, SQLException {
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionPool.getConnection();
			insertStmt = connection.prepareStatement(insertUserDBQuery);
			insertStmt.setString(1, login);
			insertStmt.setString(2, password);
			insertStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			connectionPool.putConnection(connection);
			insertStmt.close();
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
