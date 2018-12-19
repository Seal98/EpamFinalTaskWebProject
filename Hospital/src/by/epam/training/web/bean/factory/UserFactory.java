package by.epam.training.web.bean.factory;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.training.web.bean.User;

public interface UserFactory {

	public User createUser(ResultSet userData, ResultSet userLoginInfo) throws SQLException;
	
}
