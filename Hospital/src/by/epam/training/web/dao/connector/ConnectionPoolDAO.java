package by.epam.training.web.dao.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPoolDAO {
	
	private String urlDB;
	private String userDB;
	private String passwordDB;
	private BlockingQueue<Connection> connections = new LinkedBlockingQueue<Connection>(20);

    private static Logger logger = LogManager.getLogger(ConnectionPoolDAO.class);
	public static final String driverName = "com.mysql.jdbc.Driver";
	
    public ConnectionPoolDAO(String urlDB, String userDB, String passwordDB) {
    	this.urlDB = urlDB;
    	this.userDB = userDB;
    	this.passwordDB = passwordDB;
    	createConnections(20);
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
        if(connections.isEmpty()) {
        	Thread.sleep(50);
        }
        return connections.poll();
    }
    
    public void putConnection(Connection connection) {
        connections.add(connection);
    }
}
