package by.epam.training.web.service;

import by.epam.training.web.dao.FactoryDAO;
import by.epam.training.web.dao.UserDAO;
import by.epam.training.web.exception.DAOException;
import by.epam.training.web.exception.ServiceException;

public class ClientService {

	public void signIn(String login, String password) throws ServiceException {
		if(login == null || login.isEmpty()) {
			throw new ServiceException("Login is empty");
		}
		try {
			FactoryDAO factoryDao = FactoryDAO.getInstance();
			UserDAO userDao = factoryDao.getUserDAO();
			userDao.signIn(login, password);
		}catch(DAOException daoE) {
			System.out.println(daoE.getMessage());
			throw new ServiceException(daoE.getMessage());
		}
	}
	
	public void signUp(String login, String password, String confirmedPassword) throws ServiceException {
		if(login == null || login.isEmpty()) {
			throw new ServiceException("Login is empty");
		}
		if(password == null || password.isEmpty()) {
			throw new ServiceException("Password is empty");
		}
		if(confirmedPassword == null || confirmedPassword.isEmpty()) {
			throw new ServiceException("Confirm the password");
		}
		if(password.compareTo(confirmedPassword) != 0) {
			throw new ServiceException("Password and confirmation must be same");
		}
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		try {
			userDao.registration(login, password);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
}
