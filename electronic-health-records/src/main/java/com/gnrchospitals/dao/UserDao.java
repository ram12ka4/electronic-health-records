package com.gnrchospitals.dao;

import java.sql.SQLException;

public interface UserDao {

	public abstract String authenticateUser(String userID, String password) throws SQLException;

}
