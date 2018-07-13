package com.gnrchospitals.dao;

public interface DatabaseInterface {

	String getDbName();

	void setDbName(String dbName);

	String getServerAddress();

	void setServerAddress(String serverAddress);

	String getDbUserName();

	void setDbUserName(String dbUserName);

	String getDbPassword();

	void setDbPassword(String dbPassword);

	String getDbPort();

	void setDbPort(String dbPort);

}
