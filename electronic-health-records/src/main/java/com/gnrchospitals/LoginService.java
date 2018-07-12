package com.gnrchospitals;

public class LoginService {

	public boolean isLoginValid(String userName, String password) {

		if ("ram".equalsIgnoreCase(userName) && "basak".equalsIgnoreCase(password))
			return true;
		return false;

	}

}
