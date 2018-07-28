package com.gnrchospitals.util;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import com.gnrchospitals.dto.Database;

public class LoginDBConnection {

	private LoginDBConnection() {
		
	}
	
	
	public static Connection getConnection() {
		
		Database database = Database.getInstance();

		out.println("------------------ Oracle JDBC Connection Testing ------------");
		
		System.out.println("Database credentials : " + database);

		System.out.println("Database Instance ID : " + database);
		System.out.println("SERVER IP : " + database.getServerIp());
		System.out.println("PORT : " + database.getDbPort());
		System.out.println("DATABASE NAME : " + database.getDbName());
		System.out.println("USER NAME : " + database.getUserName());
		System.out.println("PASSWORD : " + database.getDbPassword());

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
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@" + database.getServerIp() + ":" + database.getDbPort() + ":"
							+ database.getDbName(),
					database.getUserName(), database.getDbPassword());

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
