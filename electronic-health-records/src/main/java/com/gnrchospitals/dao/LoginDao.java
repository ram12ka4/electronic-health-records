package com.gnrchospitals.dao;

import java.sql.SQLException;
import java.util.List;

public interface LoginDao {
	public abstract String validateUser(String userID, String password) throws SQLException;
	public abstract List<String> userRole(String userID) throws SQLException;
	public abstract List<String> userMenu(String userID, String moduleName) throws SQLException;
}
