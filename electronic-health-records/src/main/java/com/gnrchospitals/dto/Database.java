package com.gnrchospitals.dto;

public class Database {

	private String serverIp;
	private String dbName;
	private String userName;
	private String dbPassword;
	private String dbPort;

	public Database() {

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

	public String getDbPassword() {
		return dbPassword;
	}

	public String getDbPort() {
		return dbPort;
	}

}
