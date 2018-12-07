package by.epam.training.web.dao;

import by.epam.training.web.exception.DAOException;

public interface UserDAO {
	
	public void signIn(String login, String password) throws DAOException;
	
	public void registration(String login, String password) throws DAOException;
	
}
