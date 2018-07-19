package com.gnrchospitals.dto;

public class Database {

	private String serverIp;
	private String dbName;
	private String userName;
	private String dbPassword;
	private String dbPort;

	public Database(String serverIp, String dbName, String userName, String dbPassword, String dbPort) {
		super();
		this.serverIp = serverIp;
		this.dbName = dbName;
		this.userName = userName;
		this.dbPassword = dbPassword;
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
