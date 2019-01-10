package by.epam.training.web.bean.factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import by.epam.training.web.bean.Nurse;
import by.epam.training.web.bean.User;
import by.epam.training.web.dao.sql.SQLUserDAO;

public class NurseFactory implements UserFactory {

	private NurseFactory() {
		super();
	}
	
	public static UserFactory getInstance() {
		return NurseSingletonHandler.instance;
	}
	
	private static class NurseSingletonHandler{
		private static final UserFactory instance = new NurseFactory();
	}
	
	@Override
	public User createUser(ResultSet usersSet, ResultSet loginSet) throws SQLException {
		int userId = usersSet.getInt(SQLUserDAO.loginDataIdConst);
		String userLogin = loginSet.getString(SQLUserDAO.loginConst);
		String userPassword = loginSet.getString(SQLUserDAO.passwordConst);
		String userFirstName = usersSet.getString(SQLUserDAO.firstNameConst);
		String userLastName = usersSet.getString(SQLUserDAO.lastNameConst);
		Date birthdate = usersSet.getDate(SQLUserDAO.birthdateConst);
		String experience = usersSet.getString(SQLUserDAO.experienceConst);
		return new Nurse(userId, userLogin, userPassword, userFirstName, userLastName, birthdate, experience);
	}

}
