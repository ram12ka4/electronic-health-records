package com.gnrchospitals.dao;

import java.sql.SQLException;

public interface DatabaseDao {

	/*public abstract void setDbHost(String host);

	public abstract void setDbPort(String port);

	public abstract void setDbName(String dbName);

	public abstract void setDbUserName(String userName);

	public abstract void setDbPassword(String password);

	public abstract String getDbHost();

	public abstract String getDbPort();

	public abstract String getDbName();

	public abstract String getDbUserName();

	public abstract String getDbPassword();*/

	public abstract boolean findByLocation(String location) throws SQLException;

}
