package com.gnrchospitals.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gnrchospitals.dto.Patient;
import com.gnrchospitals.dto.ServiceOrder;

public interface PatientDao {

	public abstract boolean getValidatedIp(String ipNumber) throws SQLException;

	public abstract Patient findByIpNumber(String ipNumber) throws SQLException;

	public abstract boolean findEmrByIpNumber(String ipNumber) throws SQLException;

	public abstract boolean validateKey(String key) throws SQLException;

	public abstract Map<String, String> getPreviousData(String edNumber, String attrType) throws SQLException;

	public abstract List<String> getParameterList(String parameterType) throws SQLException;

	public abstract List<String> getPreviousRecordNo(String ipNo, String parameterType) throws SQLException;

	public abstract boolean updateNote(String edNo, Map<String, String> records) throws SQLException;

	public abstract List<List<String>> fetchGobalTempData(String ipNumber, String[] arr) throws SQLException;

	public List<List<String>> getPreviousRecord(String ipNumber, String eamType) throws SQLException;

	public List<List<String>> getExcelHeaderRange(String eamType) throws SQLException;

	public List<String> getPatientList(String empCode, String wardId) throws SQLException;

	public List<String> getWardList(String empCode) throws SQLException;

	public List<String> getServiceList() throws SQLException;

	public List<String> getSpecimenList(String serviceCode) throws SQLException;

	public List<String> getDoctorList() throws SQLException;

	public List<List<String>> getPrevServiceOrderList(String patientNo) throws SQLException;

	

	public List<String> getServiceOrderDetail(String soNumber) throws SQLException;

	public List<String> getPanelServiceCodeList(String serviceCode) throws SQLException;

	public String getServiceIdRate(String serviceId, String patientNo) throws SQLException;

	public List<ServiceOrder> getServiceRateList(String serviceCat, String serviceDesc) throws SQLException;

	public List<String> getDrugReqItemList(String itemName) throws SQLException;

	public List<String> getParentLink(String userRole) throws SQLException;

	public List<String> getChildLink(String userRole, String catCode) throws SQLException;

	public String insertUpdateServiceOrder(String serviceOrderKey, String doctorOrderNo, String patientNo,
			String netAmount, String mrd, String patientType, String visitNo, String userId,
			String disIndication, String referDoctor, String[] serviceId, String[] qty, String[] disAmount,
			String[] disPercent, String[] specimen, String[] treatedBy, String[] specimenChecked, String voucherNumber,
			String checkBoxFlag) throws SQLException;

	public String insertUpdateDoctorOrder(String patientNo, String mrd, String visitNo, String doctorId, String wardNo,
			String bedNo, String advice, String medication, String laboratory, String diet, String progress,
			String userId, String doctorOrderoNumber) throws SQLException;

	public String insertUpdateNurseNote(String patientNo, String mrd, String visitNo, String doctorId, String wardNo,
			String bedNo, String nurseNote, String userId, String doctorOrderoNumber) throws SQLException;

	public String insertUpdateVitalChart(String patientNo, String mrd, String visitNo, String doctorId, String wardNo,
			String bedNo, Map<String, String> map, String userId, String vitalChartNumber) throws SQLException;

	public String insertUpdatePharmaOrder(String locationCode, String pharmaOrderNumber, String wardCode,
			String stockingPoint, String mrd, String patientNo, String referDoctor, String userId, String[] itemCode,
			String[] qty) throws SQLException;

	public String insertUpdatePatientTransfer(String patientNo, String mrd, String fromWardNumber, String fromBedNumber,
			String toWardNumber, String toRoomNumber, String toBedNumber, String toBedType, String recommDcotorId,
			String remark, String userId, String bedTypeFlag) throws SQLException;

	public List<String> getDoctorNote(String doctorOrderNumber) throws SQLException;
	public List<String> getPreviousVitalChart(String ipNumber) throws SQLException;

	public List<List<String>> getPreviousDoctorNotes(String patientNo) throws SQLException;
	public List<List<String>> getPreviousNurseNotes(String patientNo) throws SQLException;
	public List<List<String>> getPrevPharmaOrderList(String ipNumber) throws SQLException;
	public List<List<String>> getPatientTransfer(String ipNumber) throws SQLException;
	
	public List<String> getNurseNote(String nurseNoteNumber) throws SQLException;
	public List<String> getPharmaOrderDetail(String poNumber) throws SQLException;
	public List<String> getWardNameList() throws SQLException;
	public List<String> getWardBedNameList(String wardCode) throws SQLException;

}
