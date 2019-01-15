package by.epam.training.web.dao.connector;

import java.util.ResourceBundle;

public class DBResourceManager {
	
	private ResourceBundle bundle = ResourceBundle.getBundle("resources.db");
	
	private DBResourceManager() {
		super();
	}
	
	private static class SingletonHandler{
		private static final DBResourceManager instance = new DBResourceManager();
	}
	
	public static DBResourceManager getInstance() {
		return SingletonHandler.instance;
	}
	
	public String getValue(String key) {
		return bundle.getString(key);
	}
	
}
