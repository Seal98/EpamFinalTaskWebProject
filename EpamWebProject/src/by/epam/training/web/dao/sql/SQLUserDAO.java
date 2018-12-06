package by.epam.training.web.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	public SQLUserDAO() {
		super();
		Connection con = null;
		try {
			con = connectionPool.getConnection();
			Statement logStmt = con.createStatement();
			ResultSet logRes = logStmt.executeQuery(getAllUsersDBQuery);
			while (logRes.next()) {
				users.add(new User(logRes.getInt(idConst), logRes.getString(loginConst),
						logRes.getString(passwordConst)));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			connectionPool.putConnection(con);
		}
	}

	@Override
	public void signIn(String login, String password) throws DAOException {
		if (!isExistingUser(login, password)) {
			throw new DAOException("User not found - login or password is wrong");
		}
	}

	@Override
	public void registration(String login, String password) throws DAOException {
		try {
			if (isExistingUser(login)) {
				throw new DAOException("User with such login already exists");
			} else {
				createUser(login, password);
				users.add(new User(users.size() + 1, login, password));
			}
		} catch (DAOException e) {
			throw e;
		}
	}

	private void createUser(String login, String password) throws DAOException {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
			PreparedStatement logStmt = connection.prepareStatement(insertUserDBQuery);
			logStmt.setString(1, login);
			logStmt.setString(2, password);
			logStmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			connectionPool.putConnection(connection);
		}
	}

	private boolean isExistingUser(String userLogin) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserLogin().compareTo(userLogin) == 0) {
				return true;
			}
		}
		return false;
	}

	private boolean isExistingUser(String userLogin, String userPassword) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserLogin().compareTo(userLogin) == 0
					&& users.get(i).getUserPassword().compareTo(userPassword) == 0) {
				return true;
			}
		}
		return false;
	}
}
