package com.gnrchospitals.dao;

import com.gnrchospitals.dto.Patient;

public interface PatientDao {
	
	public abstract Patient findByIpNumber(String ipNumber);

}
