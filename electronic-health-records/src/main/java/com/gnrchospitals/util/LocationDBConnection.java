package com.gnrchospitals.util;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;

public class LocationDBConnection {

	private static String HOST = "172.16.12.150";
	private static String PORT = "1521";
	private static String DB_NAME = "hmsdev";
	private static String USER_NAME = "gnrc_global";
	private static String PASSWORD = "gnrc_global";
	private static String URL = "jdbc:oracle:thin:@" + HOST + ":" + PORT + ":" + DB_NAME;

	public static Connection getConnection() {

		out.println("------------------ Oracle JDBC Connection Testing ------------");

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
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (Exception e) {
			out.println("Connection Failed! check output console");
			e.printStackTrace();
			return null;
		}

		if (con != null) {
			out.println("You made it! take control your database now! ");
		} else {
			out.println("Failed yo make connection");
			return null;
		}

		return con;

	}
}
