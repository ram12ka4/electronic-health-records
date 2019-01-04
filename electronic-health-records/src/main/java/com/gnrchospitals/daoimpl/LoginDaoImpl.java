package com.gnrchospitals.daoimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.gnrchospitals.dao.LoginDao;
import com.gnrchospitals.util.LoginDBConnection;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

public class LoginDaoImpl implements LoginDao {

	public String validateUser(String userID, String password) throws SQLException {
		System.out.println("in authenticateUser");
		int result = 0;
		String userName = "";
		String errorMsg = "";
		try (Connection con = LoginDBConnection.getConnection()) {
			result = 0;
			errorMsg = "ERROR";
			try (CallableStatement cs = validateUserCallableStatement(con, userID, password)) {
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
			return result + userName;
		return result + errorMsg;
	}

	private CallableStatement validateUserCallableStatement(Connection con, String userID, String password)
			throws SQLException {
		CallableStatement cs = con.prepareCall("{call PKGJV_LOGIN.PRC_VALIDATE_USER(?,?,?,?,?)}");
		cs.setString(1, userID);
		cs.setString(2, password);
		cs.registerOutParameter(3, Types.VARCHAR);
		cs.registerOutParameter(4, Types.BIGINT);
		cs.registerOutParameter(5, Types.VARCHAR);
		cs.execute();
		return cs;
	}

	public List<String> userRole(String userID) throws SQLException {

		System.out.println("in userRole");

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (CallableStatement cs = userRoleCallableStatement(con, userID)) {

				ResultSet rs = ((OracleCallableStatement) cs).getCursor(2);
				ResultSetMetaData rsmd = rs.getMetaData();

				if (rs.next()) {
					do {
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							list.add(rs.getString(rsmd.getColumnName(i)));
						}
					} while (rs.next());
				}
			}

			System.out.println("User Role : " + list);
			return list;
		}
	}

	private CallableStatement userRoleCallableStatement(Connection con, String userID) throws SQLException {
		CallableStatement cs = con.prepareCall("BEGIN PKGJV_LOGIN.PRC_GET_MODULE_LIST(?,?); END;");
		cs.setString(1, userID);
		cs.registerOutParameter(2, OracleTypes.CURSOR);
		cs.execute();
		return cs;
	}

	public List<String> userMenu(String userID, String moduleName) throws SQLException {

		System.out.println("in userMenu");

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (CallableStatement cs = userMenuCallableStatement(con, userID, moduleName)) {

				ResultSet rs = ((OracleCallableStatement) cs).getCursor(3);
				ResultSetMetaData rsmd = rs.getMetaData();

				if (rs.next()) {
					do {
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							list.add(rs.getString(rsmd.getColumnName(i)));
						}
					} while (rs.next());
				}
			}
			System.out.println("User Menu : " + list);
			return list;
		}
	}

	private CallableStatement userMenuCallableStatement(Connection con, String userID,String moduleName) throws SQLException {
		CallableStatement cs = con.prepareCall("BEGIN PKGJV_LOGIN.PRC_GET_FORM_LIST(?,?,?); END;");
		cs.setString(1, userID);
		cs.setString(2, moduleName);
		cs.registerOutParameter(3, OracleTypes.CURSOR);
		cs.execute();
		return cs;
	}

}