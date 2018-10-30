package com.gnrchospitals.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gnrchospitals.dto.IndoorPatient;
import com.gnrchospitals.dto.Patient;
import com.gnrchospitals.dto.ServiceOrder;

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
	
	public abstract List<String> getPreviousRecordNo(String ipNo, String parameterType) throws SQLException;

	public abstract boolean deleteDoctorData(String emrDetId) throws SQLException; 
	
	public abstract List<List<String>> getDoctorNote(String emrDetId) throws SQLException;
	
	public abstract boolean updateDoctorNote(String doctorNote, String emrDetId) throws SQLException;
	
	public abstract boolean updateNote(String edNo, Map<String,String> records) throws SQLException;
	
	public abstract List<List<String>> fetchGobalTempData(String ipNumber, String [] arr) throws SQLException;
	
	public List<List<String>> getPreviousRecord(String ipNumber, String eamType) throws SQLException;
	
	public List<List<String>> getExcelHeaderRange(String eamType) throws SQLException;
	
	public ArrayList<ArrayList<String>> getPatientList(String empCode, String wardId) throws SQLException;
	
	public List<IndoorPatient> getPatientList1(String empCode, String wardId) throws SQLException;
	
	public List<String> getWardList(String empCode) throws SQLException;

	public List<String> getServiceList() throws SQLException;
	
	public List<String> getPanelServiceCodeList(String serviceCode) throws SQLException;
	
	public List<ServiceOrder> getServiceRateList(String serviceCat, String serviceDesc) throws SQLException;
	
	public List<String> getParentLink(String userRole) throws SQLException;
	
	public List<String> getChildLink(String userRole, String catCode) throws SQLException;
	


}
