package by.epam.training.web.dao.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPoolDAO {
	
	private String urlDB;
	private String userDB;
	private String passwordDB;
	private Deque<Connection> connections = new ArrayDeque<Connection>(20);

    private static Logger logger = LogManager.getLogger(ConnectionPoolDAO.class);
	public static final String driverName = "com.mysql.jdbc.Driver";
	
    public ConnectionPoolDAO(String urlDB, String userDB, String passwordDB) {
    	this.urlDB = urlDB;
    	this.userDB = userDB;
    	this.passwordDB = passwordDB;
    	createConnections(20);
    }
    
    private synchronized void createConnections(int connectionsNumber) {
		try {
			Class.forName(driverName);
	        for (int i = 0; i < connectionsNumber; i++) {
	            connections.add(DriverManager.getConnection(urlDB, userDB, passwordDB));
	        }
		} catch (ClassNotFoundException e) {
			logger.info(e.getMessage());
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
    }
    
    public synchronized Connection getConnection() throws SQLException {
        if(connections.isEmpty()) {
        	return DriverManager.getConnection(urlDB, userDB, passwordDB);
        }
        return connections.poll();
    }
    
    public synchronized void putConnection(Connection connection) {
        connections.add(connection);
    }
}
