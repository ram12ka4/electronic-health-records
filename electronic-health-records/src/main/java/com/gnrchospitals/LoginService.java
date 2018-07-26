package com.gnrchospitals;

import java.util.ArrayList;

import com.gnrchospitals.dao.DatabaseDao;
import com.gnrchospitals.daoimpl.DatabaseDaoImpl;
import com.gnrchospitals.dto.Database;



public class LoginService {

	private DatabaseCredentials dbService = new DatabaseCredentials();
	private UserAthentication userAuth = new UserAthentication();

	public boolean isLoginValid(String locId, String userName, String password) {

		/*
		 * get specific database credentials for given user name and password as per
		 * given location
		 */
		//ArrayList<String> list = dbService.getDbCredentials(locId);
		
		DatabaseDao databaseDo = new DatabaseDaoImpl();
		
		boolean isLoginDatabaseConfigure = databaseDo.findByLocation(locId);
		
		String dbPassword =  userAuth.getUserAuthDetail(userName);
		

		if (dbPassword.trim().equalsIgnoreCase(password))
			return true;
		return false;

	}

}
