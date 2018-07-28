package com.gnrchospitals.dao;

import com.gnrchospitals.dto.Patient;

public interface PatientDao {
	
	public abstract boolean getValidatedIp(String ipNumber);
	public abstract Patient findByIpNumber(String ipNumber);
	public abstract boolean validateKey(String key);
	public abstract boolean insertEmrClinicalData(Patient data);
	public abstract boolean insertEmrHealthData(Patient data);

}
