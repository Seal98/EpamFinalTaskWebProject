package by.epam.training.web.service;

import by.epam.training.web.dao.FactoryDAO;
import by.epam.training.web.dao.UserDAO;
import by.epam.training.web.exception.DAOException;
import by.epam.training.web.exception.ServiceException;
import by.epam.training.web.service.validator.ValidatorHelper;
import by.epam.training.web.service.validator.ValidatorResult;

public class ClientService {

	public void signIn(String login, String password) throws ServiceException {
		ValidatorResult result = ValidatorHelper.getUserValidator().userSignInDataValidator(login, password);
		if(!result.isValid()) {
			throw new ServiceException(result.getValidationMessage());
		}
		try {
			FactoryDAO factoryDao = FactoryDAO.getInstance();
			UserDAO userDao = factoryDao.getUserDAO();
			userDao.signIn(login, password);
		}catch(DAOException daoE) {
			throw new ServiceException(daoE.getMessage());
		}
	}
	
	public void signUp(String login, String password, String confirmedPassword) throws ServiceException {
		ValidatorResult result = ValidatorHelper.getUserValidator().userSignUpDataValidator(login, password, confirmedPassword);
		if(!result.isValid()) {
			throw new ServiceException(result.getValidationMessage());
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
