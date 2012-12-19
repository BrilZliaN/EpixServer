package org.Epixcrafted.EpixServer.mysql;

import java.sql.*;

public class MySQL
{
	
	public String user;
	public String pass;
	public String host;
	public String base;

	public Connection connection;
	private boolean connected;
	private ResultSet rs;
	private Statement stmt;
	
	public MySQL() {
	}
	
	public void connect(String user, String pass, String host, String base) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		this.user = user;
		this.pass = pass;
		this.host = host;
		this.base = base;
		
		String urlConn = "jdbc:mysql://"+host+"/"+base+"?autoReconnect=true"; 
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(urlConn, user, pass);
		connected = true;
	}
	
	public boolean disconnect() {
		try {
			if (connected) {
				connection.close();
				return true;
			} else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			connected = false;
			return false;
		}
	}
	
	public ResultSet query(String q) {
		try {
			rs = null;
			stmt = null;
			stmt = connection.createStatement();
			rs = stmt.executeQuery(q);
		} catch (Exception e) {
			e.printStackTrace();
			connected = false;
		}
		
		return rs;
	}
	
	public void update(String q) {
		try {
			stmt = null;
			stmt = connection.createStatement();
			stmt.executeUpdate(q);
		} catch (Exception e) {
			e.printStackTrace();
			connected = false;
		}
	}
}