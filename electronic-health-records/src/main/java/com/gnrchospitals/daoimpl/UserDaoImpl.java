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

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement(con, user);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				userName = rs.getString(1);
				password = rs.getString(2);

				if (user.getPassword().equals(password)) {
					user.setUsername(userName);
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

		String sql = "select HUM_USER_NAME, dpw(HUM_USER_PASSWD) from as_user_master where HUM_USER_ID = ? and HUM_USER_STATUS = 'A'";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, id.trim());
		return ps;
	}

}
