package com.gnrchospitals.dao;

import com.gnrchospitals.dto.EmrHealth;

public interface EmrHealthDao {
	
	public abstract boolean validateKey(String key);
	public abstract boolean insert(EmrHealth data);
	

}
