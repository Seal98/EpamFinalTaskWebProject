package by.epam.training.web.dao;

import by.epam.training.web.dao.sql.SQLUserDAO;

public class FactoryDAO {
	
	private UserDAO userSqlDao = new SQLUserDAO();
	
	private FactoryDAO() {
	}
	
	private static class SingletonHandler{
		private static final FactoryDAO instance = new FactoryDAO();
	}
	
	public static FactoryDAO getInstance() {
		return SingletonHandler.instance;
	}
	
	public UserDAO getUserDAO() {
		return userSqlDao;
	}
	
}
