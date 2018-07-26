package com.gnrchospitals.util;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;

import com.gnrchospitals.daoimpl.DatabaseDaoImpl;
import com.gnrchospitals.dto.Database;

public class LoginDBConnection {

	private static DatabaseDaoImpl databaseDaoImpl = new DatabaseDaoImpl();

	public static Connection getConnection() {

		out.println("------------------ Oracle JDBC Connection Testing ------------");

		System.out.println("SERVER IP : " + databaseDaoImpl.getDbHost());
		System.out.println("PORT : " + databaseDaoImpl.getDbPort());
		System.out.println("DATABASE NAME : " + databaseDaoImpl.getDbName());
		System.out.println("USER NAME : " + databaseDaoImpl.getDbUserName());
		System.out.println("PASSWORD : " + databaseDaoImpl.getDbPassword());

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			out.println("Where is your Oracle JDBC Driver");
			e.printStackTrace();
			return null;
		}

		out.println("Oracle JDBC Driver Registered");

		Connection con = null;

		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + database.getServerIp() + ":" + database.getDbPort()
					+ ":" + database.getDbPort(), database.getUserName(), database.getDbPassword());

			if (con != null) {
				out.println("You made it! take control your database now! ");
			} else {
				out.println("Failed yo make connection");
				System.out.println(con);
			}

		} catch (Exception e) {
			out.println("Connection Failed! check output console");
			e.printStackTrace();
			return null;
		}

		return con;

	}
}
