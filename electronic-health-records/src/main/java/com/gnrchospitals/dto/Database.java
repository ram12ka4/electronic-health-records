package com.gnrchospitals.dto;

public class Database {

	private String serverIp;
	private String dbName;
	private String userName;
	private String dbPassword;
	private String dbPort;
	private static Database database;

	private Database() {

	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getServerIp() {
		return serverIp;
	}

	public String getDbName() {
		return dbName;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return "Database [serverIp=" + serverIp + ", dbName=" + dbName + ", userName=" + userName + ", dbPassword="
				+ dbPassword + ", dbPort=" + dbPort + "]";
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public String getDbPort() {
		return dbPort;
	}

	static {
		try {
			database = new Database();
		} catch (Exception e) {
			throw new RuntimeException("Exception occured in creating singleton instance");
		}
	}

	public static Database getInstance() {
		return database;
	}

}
