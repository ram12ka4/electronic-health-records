package com.gnrchospitals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import com.gnrchospitals.dao.LoginDatabaseDao;

public class DatabaseCredentials {

	public ArrayList<String> getDbCredentials(String locationId) {

		ArrayList<String> list = new ArrayList<>();

		try (Connection con = LoginDatabaseDao.getConnection();
				PreparedStatement ps = createPreparedStatement(con, locationId);
				ResultSet rs = ps.executeQuery()) {

			ResultSetMetaData rsmd = rs.getMetaData();

			if (rs.next()) {
				do {
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						list.add(rs.getString(rsmd.getColumnName(i)));
					}
				} while (rs.next());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	private PreparedStatement createPreparedStatement(Connection con, String locationId) throws SQLException {
		String sql = "select SRV_ADD, DB_PORT, DB_NAME, DB_USER, DB_PSWD from MST_GLOBAL_INFO where LOC_ID = ? and STATUS ='A'";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, locationId);
		return ps;
	}

}
