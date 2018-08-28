package com.gnrchospitals.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gnrchospitals.dto.Patient;

public interface PatientDao {

	public abstract boolean getValidatedIp(String ipNumber) throws SQLException;

	public abstract Patient findByIpNumber(String ipNumber) throws SQLException;

	public abstract boolean findEmrByIpNumber(String ipNumber) throws SQLException;

	public abstract boolean validateKey(String key) throws SQLException;
 
	public abstract boolean insertEmrClinicalData(Patient data) throws SQLException;

	public abstract boolean insertEmrHealthData(Patient data) throws SQLException;
	
	public abstract List<List<String>> getDoctorPreviousData(String ipNumber, String action) throws SQLException;
	
	public abstract Map<String, String> getPreviousData(String edNumber, String attrType) throws SQLException;

	public abstract List<String> getParameterList(String parameterType) throws SQLException;
	
	public abstract List<String> getPreviousRecordNo(String parameterType) throws SQLException;

	public abstract boolean deleteDoctorData(String emrDetId) throws SQLException; 
	
	public abstract List<List<String>> getDoctorNote(String emrDetId) throws SQLException;
	
	public abstract boolean updateDoctorNote(String doctorNote, String emrDetId) throws SQLException;
	
	//public abstract boolean updateNote(String doctorNote, String emrDetId) throws SQLException;
	
	
	public abstract List<List<String>> fetchGobalTempData(String ipNumber, String [] arr) throws SQLException;


}
