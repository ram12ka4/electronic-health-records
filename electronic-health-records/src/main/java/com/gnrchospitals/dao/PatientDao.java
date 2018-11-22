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

	//public abstract List<List<String>> getDoctorNote(String emrDetId) throws SQLException;

	public abstract boolean updateDoctorNote(String doctorNote, String emrDetId) throws SQLException;

	public abstract boolean updateNote(String edNo, Map<String, String> records) throws SQLException;

	public abstract List<List<String>> fetchGobalTempData(String ipNumber, String[] arr) throws SQLException;

	public List<List<String>> getPreviousRecord(String ipNumber, String eamType) throws SQLException;

	public List<List<String>> getExcelHeaderRange(String eamType) throws SQLException;

	public ArrayList<ArrayList<String>> getPatientList(String empCode, String wardId) throws SQLException;

	public List<IndoorPatient> getPatientList1(String empCode, String wardId) throws SQLException;

	public List<String> getWardList(String empCode) throws SQLException;

	public List<String> getServiceList() throws SQLException;

	public List<String> getSpecimenList(String serviceCode) throws SQLException;

	public List<String> getDoctorList() throws SQLException;

	public String insertServiceOrderData(String serviceOrderKey, String doctorOrderNo, String patientNo, String netAmount, String doctorId,
			String mrd, String patientType, String visitNo, String userId, String disIndication, String referDoctor,
			String[] serviceId, String[] qty, String[] disAmount, String[] disPercent, String[] specimen,
			String[] treatedBy,String [] specimenChecked, String voucherNumber) throws SQLException;

	public List<List<String>> getPrevServiceOrderList(String patientNo) throws SQLException;
	
	public List<List<String>> getPatientHistory(String patientNo) throws SQLException;

	public List<String> getServiceOrderDetail(String soNumber) throws SQLException;

	public List<String> getPanelServiceCodeList(String serviceCode) throws SQLException;

	public String getServiceIdRate(String serviceId, String patientNo) throws SQLException;

	public List<ServiceOrder> getServiceRateList(String serviceCat, String serviceDesc) throws SQLException;

	public List<String> getParentLink(String userRole) throws SQLException;

	public List<String> getChildLink(String userRole, String catCode) throws SQLException;

	public String insertDoctorOrderData(String patientNo, String mrd, String visitNo, String doctorId, String wardNo,
			String bedNo, String advice, String medication, String laboratory, String diet, String progress,
			String userId, String doctorOrderoNumber) throws SQLException;
	
	public List<String> getDoctorNote(String doctorOrderNumber) throws SQLException;

}
