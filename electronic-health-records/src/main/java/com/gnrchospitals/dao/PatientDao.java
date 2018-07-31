package com.gnrchospitals.dao;

import java.util.List;

import com.gnrchospitals.dto.Patient;

public interface PatientDao {

	public abstract boolean getValidatedIp(String ipNumber);

	public abstract Patient findByIpNumber(String ipNumber);

	public abstract boolean findEmrByIpNumber(String ipNumber);

	public abstract boolean validateKey(String key);

	public abstract boolean insertEmrClinicalData(Patient data);

	public abstract boolean insertEmrHealthData(Patient data);
	
	public abstract List<List<String>> getDoctorPreviousNote(String ipNumber);
	
	public abstract boolean deleteDoctorNote(String emrDetId); 

}
