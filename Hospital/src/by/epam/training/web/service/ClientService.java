package by.epam.training.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import by.epam.training.web.bean.User;
import by.epam.training.web.dao.FactoryDAO;
import by.epam.training.web.dao.UserDAO;
import by.epam.training.web.exception.DAOException;
import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.validator.ValidatorHelper;
import by.epam.training.web.service.validator.ValidatorResult;

public class ClientService {

	public User signIn(String login, String password) throws ServiceException {
		ValidatorResult result = ValidatorHelper.getUserValidator().userSignInDataValidator(login, password);
		User existingUser = null;
		if (!result.isValid()) {
			throw new ServiceException(result.getValidationMessage());
		}
		try {
			FactoryDAO factoryDao = FactoryDAO.getInstance();
			UserDAO userDao = factoryDao.getUserDAO();
			existingUser = userDao.signIn(login, password);
		} catch (DAOException daoE) {
			throw new ServiceException(daoE.getMessage(), daoE);
		}
		return existingUser;
	}

	public void signUp(String login, String password, String confirmedPassword, String firstName, String lastName, String birthdateStr) throws ServiceException {
		ValidatorResult result = ValidatorHelper.getUserValidator().userSignUpDataValidator(login, password,
				confirmedPassword, firstName, lastName);
		if (!result.isValid()) {
			throw new ServiceException(result.getValidationMessage());
		}
		FactoryDAO factoryDao = FactoryDAO.getInstance();
		UserDAO userDao = factoryDao.getUserDAO();
		try {			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date birthdate = format.parse(birthdateStr);
			userDao.registration(login, password, firstName, lastName, new java.sql.Date(birthdate.getTime()),
					new java.sql.Date(new Date().getTime()));
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		} catch (ParseException e) {
			throw new ServiceException("Input birthdate once again", e);
		}
	}

}
