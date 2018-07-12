package com.gnrchospitals.dao;

import static java.lang.System.out;

import java.sql.Connection;
import java.sql.DriverManager;


public class LoginDatabaseDao  {


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
			con = DriverManager.getConnection("jdbc:oracle:thin:@172.16.12.150:1521:hmsdev","gnrc_global", "gnrc_global");
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
