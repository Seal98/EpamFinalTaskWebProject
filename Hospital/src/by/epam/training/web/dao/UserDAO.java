package by.epam.training.web.dao;

import by.epam.training.web.bean.User;
import by.epam.training.web.exception.DAOException;

public interface UserDAO {
	
	public static final String userNotFoundMessage = "User not found - login or password is wrong";
	public static final String userAlreadyExistMessage = "User with such login already exists";
	
	public User signIn(String login, String password) throws DAOException;
	
	public void registration(String login, String password) throws DAOException;
	
}
