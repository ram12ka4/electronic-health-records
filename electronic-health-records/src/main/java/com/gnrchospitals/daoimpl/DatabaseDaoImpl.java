package com.gnrchospitals.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gnrchospitals.dao.DatabaseDao;
import com.gnrchospitals.dto.Database;
import com.gnrchospitals.util.LocationDBConnection;

public class DatabaseDaoImpl implements DatabaseDao {



	@Override
	public boolean findByLocation(String location) throws SQLException{
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
