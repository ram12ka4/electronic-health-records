package com.gnrchospitals.dao;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDao implements DatabaseInterface {

	private static String dbName;
	private static String serverAddress;
	private static String dbUserName;
	private static String dbPassword;
	private static String dbPort;

	@Override
	public String getDbName() {
		// TODO Auto-generated method stub
		return dbName;
	}

	@Override
	public void setDbName(String dbName) {
		// TODO Auto-generated method stub
		DatabaseDao.dbName = dbName;

	}

	@Override
	public String getServerAddress() {
		// TODO Auto-generated method stub
		return serverAddress;
	}

	@Override
	public void setServerAddress(String serverAddress) {
		// TODO Auto-generated method stub
		DatabaseDao.serverAddress = serverAddress;

	}

	@Override
	public String getDbUserName() {
		// TODO Auto-generated method stub
		return dbUserName;
	}

	@Override
	public void setDbUserName(String dbUserName) {
		// TODO Auto-generated method stub
		DatabaseDao.dbUserName = dbUserName;

	}

	@Override
	public String getDbPassword() {
		// TODO Auto-generated method stub
		return dbPassword;
	}

	@Override
	public void setDbPassword(String dbPassword) {
		// TODO Auto-generated method stub
		DatabaseDao.dbPassword = dbPassword;

	}

	@Override
	public String getDbPort() {
		// TODO Auto-generated method stub
		return dbPort;
	}

	@Override
	public void setDbPort(String dbPort) {
		// TODO Auto-generated method stub
		DatabaseDao.dbPort = dbPort;

	}

	public static Connection getConnection() throws SQLException {

		out.println("------------------ Oracle JDBC Connection Testing ------------");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			out.println("Where is your Oracle JDBC Driver");
			e.printStackTrace();
			return null;
		}

		out.println("Oracle JDBC Driver Registered");

		Connection con = null;

		// try {
		con = DriverManager.getConnection("jdbc:oracle:thin:@" + serverAddress + ":" + dbPort + ":" + dbName,
				dbUserName, dbPassword);

		if (con != null) {
			out.println("You made it! take control your database now! ");
		} else {
			out.println("Failed yo make connection");
			System.out.println(con);
		}

		/*
		 * } catch (Exception e) {
		 * out.println("Connection Failed! check output console"); e.printStackTrace();
		 * // return null; }
		 */

		return con;

	}

}
