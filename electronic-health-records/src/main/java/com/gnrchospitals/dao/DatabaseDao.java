package com.gnrchospitals.dao;

import java.sql.SQLException;

public interface DatabaseDao {

	public abstract boolean findByLocation(String location) throws SQLException;

}
