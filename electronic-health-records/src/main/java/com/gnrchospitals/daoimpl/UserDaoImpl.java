package com.gnrchospitals.daoimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import com.gnrchospitals.dao.UserDao;
import com.gnrchospitals.util.LoginDBConnection;

public class UserDaoImpl implements UserDao {

	public String authenticateUser(String userID, String password) throws SQLException {
		System.out.println("in authenticateUser");
		int result = 0;
		String userName = "";
		String errorMsg = "";
		try (Connection con = LoginDBConnection.getConnection()) {
					result = 0;
					errorMsg = "ERROR";
						try (CallableStatement cs = createCallableStatement1(con, userID, password)) {
							userName = cs.getString(3);
							result = cs.getInt(4);
							errorMsg = cs.getString(5);
							System.out.println("PKGJV_LOGIN.PRC_VALIDATE_USER OUT PARAMETER : " + result);
							System.out.println("PKGJV_LOGIN.PRC_VALIDATE_USER RETURN MSG : " + errorMsg);
						} catch (SQLException e) {
							e.printStackTrace();
						}
		}

		if (result == 1)
			return result+userName;
		return result+errorMsg;
	}

	private CallableStatement createCallableStatement1(Connection con, String userID, String password) throws SQLException {
		CallableStatement cs = con.prepareCall("{call PKGJV_LOGIN.PRC_VALIDATE_USER(?,?,?,?,?)}");
		cs.setString(1, userID);
		cs.setString(2, password);
		cs.registerOutParameter(3, Types.VARCHAR);
		cs.registerOutParameter(4, Types.BIGINT);
		cs.registerOutParameter(5, Types.VARCHAR);
		cs.execute();
		return cs;
	}	

}
