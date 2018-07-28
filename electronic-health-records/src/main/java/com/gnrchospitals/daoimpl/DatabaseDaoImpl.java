package com.gnrchospitals.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gnrchospitals.dao.DatabaseDao;
import com.gnrchospitals.dto.Database;
import com.gnrchospitals.util.LocationDBConnection;

public class DatabaseDaoImpl implements DatabaseDao {

	/*
	 * private static String host; private static String port; private static String
	 * dbName; private static String userName; private static String password;
	 * 
	 * @Override public void setDbHost(String host) { // TODO Auto-generated method
	 * stub DatabaseDaoImpl.host = host;
	 * 
	 * }
	 * 
	 * @Override public void setDbPort(String port) { // TODO Auto-generated method
	 * stub DatabaseDaoImpl.port = port;
	 * 
	 * }
	 * 
	 * @Override public void setDbName(String dbName) { // TODO Auto-generated
	 * method stub DatabaseDaoImpl.dbName = dbName; }
	 * 
	 * @Override public void setDbUserName(String userName) { // TODO Auto-generated
	 * method stub DatabaseDaoImpl.userName = userName; }
	 * 
	 * @Override public void setDbPassword(String password) { // TODO Auto-generated
	 * method stub DatabaseDaoImpl.password = password;
	 * 
	 * }
	 * 
	 * @Override public String getDbHost() { // TODO Auto-generated method stub
	 * return DatabaseDaoImpl.host; }
	 * 
	 * @Override public String getDbPort() { // TODO Auto-generated method stub
	 * return DatabaseDaoImpl.port; }
	 * 
	 * @Override public String getDbName() { // TODO Auto-generated method stub
	 * return DatabaseDaoImpl.dbName; }
	 * 
	 * @Override public String getDbUserName() { // TODO Auto-generated method stub
	 * return DatabaseDaoImpl.userName; }
	 * 
	 * @Override public String getDbPassword() { // TODO Auto-generated method stub
	 * return DatabaseDaoImpl.password; }
	 */

	@Override
	public boolean findByLocation(String location) {

		try (Connection con = LocationDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement(con, location);
				ResultSet rs = ps.executeQuery()) {

			Database database = Database.getInstance();
			System.out.println("Database Instance ID " + database);

			if (rs.next()) {
				database.setServerIp(rs.getString(1));
				database.setDbPort(rs.getString(2));
				database.setDbName(rs.getString(3));
				database.setUserName(rs.getString(4));
				database.setDbPassword(rs.getString(5));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private PreparedStatement createPreparedStatement(Connection con, String location) throws SQLException {
		String sql = "select SRV_ADD, DB_PORT, DB_NAME, DB_USER, DB_PSWD from MST_GLOBAL_INFO where LOC_ID = ? and STATUS ='A'";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, location);
		return ps;
	}

}
