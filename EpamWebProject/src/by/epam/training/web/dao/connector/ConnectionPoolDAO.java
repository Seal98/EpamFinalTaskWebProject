package by.epam.training.web.dao.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPoolDAO {
	
	private String urlDB;
	private String userDB;
	private String passwordDB;
	private Deque<Connection> connections = new ArrayDeque<Connection>(20);
	
    public ConnectionPoolDAO(String urlDB, String userDB, String passwordDB) {
    	this.urlDB = urlDB;
    	this.userDB = userDB;
    	this.passwordDB = passwordDB;
    	createConnections(20);
    }
    
    private synchronized void createConnections(int connectionsNumber) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        for (int i = 0; i < connectionsNumber; i++) {
	            connections.add(DriverManager.getConnection(urlDB, userDB, passwordDB));
	        }
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(e);
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
