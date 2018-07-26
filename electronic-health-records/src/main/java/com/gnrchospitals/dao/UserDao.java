package com.gnrchospitals.dao;

import com.gnrchospitals.dto.User;

public interface UserDao {

	public abstract boolean authenticateUser(User user);

}
