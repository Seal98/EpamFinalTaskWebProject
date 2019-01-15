package by.epam.training.web.dao.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPoolDAO {
	
	private String urlDB;
	private String userDB;
	private String passwordDB;
	private String driverName;
	private BlockingQueue<Connection> connections;

    private static Logger logger = LogManager.getLogger(ConnectionPoolDAO.class);
	
    public ConnectionPoolDAO() {
    	DBResourceManager dbResourceManager = DBResourceManager.getInstance();
    	int pollNumber = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));
    	this.connections = new LinkedBlockingQueue<Connection>(pollNumber);
    	this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
    	this.urlDB = dbResourceManager.getValue(DBParameter.DB_URL);
    	this.userDB = dbResourceManager.getValue(DBParameter.DB_USER);
    	this.passwordDB = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
    	createConnections(pollNumber);
    }
    
    private void createConnections(int connectionsNumber) {
		try {
			Class.forName(driverName);
	        for (int i = 0; i < connectionsNumber; i++) {
	            connections.add(DriverManager.getConnection(urlDB, userDB, passwordDB));
	        }
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
    }
    
    public Connection getConnection() throws SQLException, InterruptedException {
        while(connections.isEmpty()) {
        	Thread.sleep(50);
        }
        return connections.poll();
    }
    
    public void putConnection(Connection connection) {
        connections.add(connection);
    }
}
