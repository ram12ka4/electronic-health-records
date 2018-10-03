package com.gnrchospitals.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gnrchospitals.dao.UserDao;
import com.gnrchospitals.dto.User;
import com.gnrchospitals.util.LoginDBConnection;

public class UserDaoImpl implements UserDao {

	@Override
	public boolean authenticateUser(User user) {

		String userName = "";
		String password = "";
		String userRole = "";

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement(con, user);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				userName = rs.getString(1);
				password = rs.getString(2);
				userRole = rs.getString(3);
				if (user.getPassword().equals(password)) {
					user.setUsername(userName);
					user.setUserRole(userRole);
					return true;
				}

			}

		} catch (SQLException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
		}

		System.out.println(password);

		return false;
	}

	private PreparedStatement createPreparedStatement(Connection con, User user) throws SQLException {

		String id = user.getId();
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT a.HUM_USER_NAME, dpw(a.HUM_USER_PASSWD), b.HUR_ROLE_CD ");
		sql.append(" FROM ");
		sql.append(" AS_USER_MASTER a, ");
		sql.append(" AS_USER_ROLES b ");
		sql.append(" WHERE a.HUM_USER_ID = ? ");
		sql.append(" AND a.HUM_USER_STATUS = 'A' ");
		sql.append(" AND a.HUM_USER_ID = b.HUR_USER_ID ");
		

		//String sql = "select HUM_USER_NAME, dpw(HUM_USER_PASSWD) from as_user_master where HUM_USER_ID = ? and HUM_USER_STATUS = 'A'";
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, id.trim());
		return ps;
	}

}
