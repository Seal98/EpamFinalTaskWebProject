package by.epam.training.web.bean.factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import by.epam.training.web.bean.Patient;
import by.epam.training.web.bean.User;
import by.epam.training.web.dao.sql.SQLUserDAO;

public class PatientFactory implements UserFactory {

	private PatientFactory() {
		super();
	}
	
	public static UserFactory getInstance() {
		return NurseSingletonHandler.instance;
	}
	
	private static class NurseSingletonHandler{
		private static final UserFactory instance = new PatientFactory();
	}
	
	@Override
	public User createUser(ResultSet usersSet, ResultSet loginSet) throws SQLException {
		int userId = usersSet.getInt(SQLUserDAO.loginDataIdFKConst);
		String userLogin = loginSet.getString(SQLUserDAO.loginConst);
		String userPassword = loginSet.getString(SQLUserDAO.passwordConst);
		String userFirstName = usersSet.getString(SQLUserDAO.firstNameConst);
		String userLastName = usersSet.getString(SQLUserDAO.lastNameConst);
		Date birthdate = usersSet.getDate(SQLUserDAO.birthdateConst);
		Date admissionDate = usersSet.getDate(SQLUserDAO.admissionDateConst);
		int attendedDoctorId = usersSet.getInt(SQLUserDAO.attendedDoctorIdConst);
		boolean dischargeStatus = usersSet.getBoolean(SQLUserDAO.dischargeStatusConst);
		return new Patient(userId, userLogin, userPassword, userFirstName, userLastName, birthdate, admissionDate, attendedDoctorId, dischargeStatus);
	}

}
