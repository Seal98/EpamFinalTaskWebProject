package by.epam.training.web.dao;

import by.epam.training.web.exception.DAOException;

public interface UserDAO {
	
	public static final String userNotFoundMessage = "User not found - login or password is wrong";
	public static final String userAlreadyExistMessage = "User with such login already exists";
	
	public void signIn(String login, String password) throws DAOException;
	
	public void registration(String login, String password) throws DAOException;
	
}
