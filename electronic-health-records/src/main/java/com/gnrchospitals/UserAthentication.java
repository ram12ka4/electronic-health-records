package com.gnrchospitals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gnrchospitals.daoimpl.DatabaseDaoImpl;

public class UserAthentication {

	private DatabaseDaoImpl dao = new DatabaseDaoImpl();

	public String getUserAuthDetail(ArrayList<String> list, String userName) {

		String serverAddress = list.get(0);
		String dbPort = list.get(1);
		String dbName = list.get(2);
		String dbUser = list.get(3);
		String dbPassword = list.get(4);
		
		System.out.println("Server Address " + serverAddress);
		System.out.println("Server db port " + dbPort);
		System.out.println("Server db name " + dbName);
		System.out.println("Server db user " + dbUser);
		System.out.println("Server password " + dbPassword);

		dao.setServerAddress(serverAddress);
		dao.setDbPort(dbPort);
		dao.setDbName(dbName);
		dao.setDbUserName(dbUser);
		dao.setDbPassword(dbPassword);

		String password = getUserPassword(userName);

		return password;

	}

	public String getUserPassword(String userName) {

		String password = "";

		try (Connection con = DatabaseDaoImpl.getConnection();
				PreparedStatement ps = createPreparedStatement(con, userName);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				password = rs.getString(1);
				
			}

		} catch (SQLException e) {
			System.out.println("Connection failed");
			e.printStackTrace();
		}
		
		System.out.println(password);

		return password;

	}

	private PreparedStatement createPreparedStatement(Connection con, String userName) throws SQLException {
		String sql = "select dpw(HUM_USER_PASSWD) from as_user_master where HUM_USER_ID = ? and HUM_USER_STATUS = 'A'";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, userName.trim());
		return ps;
	}

}
