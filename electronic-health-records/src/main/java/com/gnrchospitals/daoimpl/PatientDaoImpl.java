package com.gnrchospitals.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.dto.Emr;
import com.gnrchospitals.dto.IndoorPatient;
import com.gnrchospitals.dto.Patient;
import com.gnrchospitals.dto.ServiceOrder;
import com.gnrchospitals.util.LoginDBConnection;

public class PatientDaoImpl implements PatientDao {

	private Patient patient = Patient.getInstance();

	@Override
	public Patient findByIpNumber(String ipNumber) throws SQLException {

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement(con, ipNumber);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				do {
					patient.setMrdNumber(rs.getString(1));
					patient.setIpNumber(rs.getString(2));
					patient.setPatientName(rs.getString(3));
					patient.setSex(rs.getString(4));
					patient.setAge(rs.getString(5));
					patient.setAdmissionDate(rs.getString(6));
					patient.setDoctorIncharge(rs.getString(7));
					patient.setSpeciality(rs.getString(8));
					patient.setBedNo(rs.getString(9));
					patient.setWardNo(rs.getString(10));
					patient.setMaritalStatus(rs.getString(11));
					patient.setPatientCategoryCode(rs.getString(12));
					patient.setPatientCategory(rs.getString(13));
					patient.setPatientSubCategoryCode(rs.getString(14));
					patient.setPatientSubCategory(rs.getString(15));

				} while (rs.next());
			}

			System.out.println("List is : " + patient);

		}
		return patient;
	}

	private PreparedStatement createPreparedStatement(Connection con, String ipNumber) throws SQLException {

		StringBuilder sql = new StringBuilder();

		System.out.println("IP Number : " + ipNumber);

		sql.append(
				" SELECT  B.RRH_MR_NUM \"MRD NO\", A.WAT_IP_NUM \"IP NO\", B.RRH_FIRST_NAME||' '||B.RRH_MIDDLE_NAME||' '||B.RRH_LAST_NAME NAME ");
		sql.append(
				" , (SELECT C.GPM_PARAMETER_VALUE FROM GA_PARAMETER_MASTER C WHERE C.GPM_PARAMETER_CD = B.RRH_PAT_SEX AND C.GPM_PARAMETER_TYPE = 'SEX') SEX");
		sql.append(" , ROUND((trunc(SYSDATE) - B.RRH_PAT_DOB) / 365) AGE");
		sql.append(
				" , A.WAT_ADMN_DT \"ADMISSION DATE\", 'DR. ' || D.EEM_FIRST_NAME||' '|| D.EEM_MIDDLE_NAME||''|| D.EEM_LAST_NAME \"DOCTOR INCHARGE\"");
		sql.append(
				" , g.GDM_DEPT_DESC speciality , a.WAT_BED_CD \"BED NO\" , e.WWM_WARD_DESC ward , f.GPM_PARAMETER_VALUE \"MARITAL STATUS\", h.GPC_PATIENT_CTGRY_CD, h.GPC_PATIENT_CTGRY_DESC, i.GPS_PATIENT_SUBCTGRY_CD, i.GPS_PATIENT_SUBCTGRY_DESC ");
		sql.append(
				" from wa_admission_txn a, RE_REGISTRATION_HEADER b,  hr_employee_master d, WA_WARD_MASTER e, ga_parameter_master f, ga_department_master g,  GA_PATIENT_CATEGORY_MASTER h, GA_PATIENT_SUBCATEGORY_MASTER i");
		sql.append(
				" WHERE a.WAT_MR_NUM = b.RRH_MR_NUM and d.EEM_EMP_NUM = a.WAT_DOCTOR_INCHARGE and a.WAT_WARD_CD = e.WWM_WARD_CD and ");
		sql.append(
				" b.RRH_MARITAL_STATUS =  f.GPM_PARAMETER_CD and a.WAT_ADMM_DEPT = g.GDM_DEPT_CD  and a.WAT_PATIENT_CATEGORY_CD  = h.GPC_PATIENT_CTGRY_CD and a.WAT_PATIENT_SUBCATEGORY_CD = i.GPS_PATIENT_SUBCTGRY_CD and a.WAT_IP_NUM = ? ");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		return ps;
	}

	@Override
	public boolean validateKey(String key) throws SQLException {
		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement2(con, key);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return true;
			}

		}

		return false;
	}

	private PreparedStatement createPreparedStatement2(Connection con, String key) throws SQLException {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM EMR_ATTRIBUTE_MASTER WHERE EAM_ATTRB_CODE = ?");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, key);
		return ps;
	}

	@Override
	public boolean insertEmrClinicalData(Patient data) throws SQLException {
		boolean flag = false;

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			try (PreparedStatement ps = createPreparedStatement(con, data)) {

				int result = ps.executeUpdate();

				if (result != 0)
					flag = true;

			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				e.printStackTrace();
			}

			con.commit();
			con.setAutoCommit(true);

			if (flag)
				return true;

		}
		return false;
	}

	private PreparedStatement createPreparedStatement(Connection con, Patient patientEmr) throws SQLException {

		System.out.println("In insertEmrClinicalData Method");
		System.out.println("Patient instance ID " + patientEmr);
		System.out.println(patientEmr.getEmr().getEmrNo());
		System.out.println(patientEmr.getMrdNumber());
		System.out.println(patientEmr.getIpNumber());
		System.out.println(patientEmr.getEmr().getVisitNo());
		System.out.println(patientEmr.getEmr().getEncounterNo());
		System.out.println(patientEmr.getEmr().getCreateUser());
		System.out.println(patientEmr.getEmr().getUpdateUser());

		String sql = "INSERT INTO EMR_CLINICAL_DETAIL(ECD_EM_NUM, ECD_MR_NUM, ECD_PAT_NUM, ECD_VISIT_NUM, ECD_ENCOUNTER_NUMBER, ECD_ENCOUNTER_DATE, "
				+ "   ECD_CRT_USER_ID, ECD_CRT_DATE, ECD_LST_UPD_USER_ID, ECD_LST_UPD_DATE) VALUES(?,?,?,?,?,sysdate,?,sysdate,?,sysdate)";

		System.out.println(sql);

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, patientEmr.getEmr().getEmrNo());
		ps.setString(2, patientEmr.getMrdNumber());
		ps.setString(3, patientEmr.getIpNumber());
		ps.setString(4, patientEmr.getEmr().getVisitNo());
		ps.setString(5, patientEmr.getEmr().getEncounterNo());
		ps.setString(6, patientEmr.getEmr().getCreateUser());
		ps.setString(7, patientEmr.getEmr().getUpdateUser());

		return ps;
	}

	@Override
	public boolean insertEmrHealthData(Patient data) throws SQLException {
		boolean flag = false;

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			try (PreparedStatement ps = createPreparedStatement4(con, data)) {

				int affectedRecords[] = ps.executeBatch();

				if (affectedRecords.length != 0)
					flag = true;

			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				throw e;
			}

			con.commit();
			con.setAutoCommit(true);
			if (flag)
				return true;

		}

		return false;
	}

	private PreparedStatement createPreparedStatement4(Connection con, Patient patientEmr) throws SQLException {

		StringBuilder sql = new StringBuilder();

		System.out.println("Patient Instance ID : " + patientEmr);
		String emrNo = patientEmr.getEmr().getEmrNo();
		String emrDetNo = patientEmr.getEmr().getEmrDetNo();
		String createdUser = patientEmr.getEmr().getCreateUser();
		String lastCreatedUser = patientEmr.getEmr().getUpdateUser();
		Map<String, String> keyValue = patientEmr.getEmr().getKeyValue();
		Set<Map.Entry<String, String>> st = keyValue.entrySet();

		sql.append(
				"INSERT INTO EMR_HEALTH_RECORD(EHR_EMR_NUM, EHR_DTL_CODE, EHR_ATTRB_CODE, EHR_ATTRB_VALUE, EHR_CRT_UID, EHR_CRT_DT, EHR_LAST_UPD_UID, EHR_LAST_UPD_DT) "
						+ " VALUES(?,?,?,?,?,sysdate,?,sysdate)");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		for (Map.Entry<String, String> me : st) {
			ps.setString(1, emrNo);
			ps.setString(2, emrDetNo);
			ps.setString(3, me.getKey());
			ps.setString(4, me.getValue());
			ps.setString(5, createdUser);
			ps.setString(6, lastCreatedUser);

			ps.addBatch();
		}
		return ps;
	}

	@Override
	public boolean getValidatedIp(String ipNumber) throws SQLException {
		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement1(con, ipNumber);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return true;
			}

		}

		return false;
	}

	private PreparedStatement createPreparedStatement1(Connection con, String ipNumber) throws SQLException {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM EMR_CLINICAL_DETAIL WHERE ECD_PAT_NUM = ?");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		return ps;
	}

	@Override
	public boolean findEmrByIpNumber(String ipNumber) throws SQLException {
		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement4(con, ipNumber);
				ResultSet rs = ps.executeQuery()) {

			Patient patient = Patient.getInstance();
			Emr emr = Emr.getInstance();

			if (rs.next()) {
				emr.setEmrNo(rs.getString(1));
				patient.setEmr(emr);
				return true;
			}

		}

		return false;
	}

	private PreparedStatement createPreparedStatement4(Connection con, String ipNumber) throws SQLException {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ECD_EM_NUM FROM EMR_CLINICAL_DETAIL WHERE ECD_PAT_NUM = ?");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		return ps;
	}

	@Override
	public List<List<String>> getDoctorPreviousData(String ipNumber, String action) throws SQLException {

		List<List<String>> list = new ArrayList<>();
		List<String> col = new ArrayList<>();
		List<String> row = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement5(con, ipNumber, action);
				ResultSet rs = ps.executeQuery()) {

			ResultSetMetaData rsmd = rs.getMetaData();

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				col.add(rsmd.getColumnName(i));
			}

			list.add(col);

			if (rs.next()) {

				do {
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						row.add(rs.getString(rsmd.getColumnName(i)));
					}
				} while (rs.next());

			}

			list.add(row);

		}
		return list;
	}

	private PreparedStatement createPreparedStatement5(Connection con, String ipNumber, String action)
			throws SQLException {

		StringBuilder sql = new StringBuilder();

		if ("DOCTOR_PREVIOUS_NOTES".equals(action)) {
			sql.append(" SELECT MAX(DECODE(B.EHR_ATTRB_CODE, 'DN001',B.EHR_ATTRB_VALUE,'')) DOCTOR_NAME ");
			sql.append(" , MAX(DECODE(B.EHR_ATTRB_CODE, 'DN004',B.EHR_ATTRB_VALUE,'')) DOCTOR_NOTE ");
			sql.append(" , B.EHR_DTL_CODE, B.EHR_CRT_DT ");
			sql.append(" FROM EMR_CLINICAL_DETAIL A, EMR_HEALTH_RECORD B ");
			sql.append(" WHERE B.EHR_ATTRB_CODE IN ( 'DN004', 'DN001') ");
			sql.append(" AND A.ECD_EM_NUM = B.EHR_EMR_NUM AND A.ECD_PAT_NUM = ? ");
			sql.append(" GROUP BY B.EHR_DTL_CODE, B.EHR_CRT_DT ");
			sql.append(" ORDER BY EHR_DTL_CODE DESC ");
		} else if ("DOCTOR_PREVIOUS_ORDERS".equals(action)) {
			sql.append(
					" SELECT  B.EHR_CRT_DT, B.EHR_DTL_CODE, MAX(DECODE(B.EHR_ATTRB_CODE,'DO007' , B.EHR_ATTRB_VALUE, '')) DOCTOR_NAME, ");
			sql.append(" MAX(DECODE(B.EHR_ATTRB_CODE,'DO003' , B.EHR_ATTRB_VALUE, '')) MEDICINE, ");
			sql.append(" MAX(DECODE(B.EHR_ATTRB_CODE,'DO002' , B.EHR_ATTRB_VALUE, '')) TREATMENT, ");
			sql.append(" MAX(DECODE(B.EHR_ATTRB_CODE,'DO005' , B.EHR_ATTRB_VALUE, '')) DIET, ");
			sql.append(" MAX(DECODE(B.EHR_ATTRB_CODE,'DO004' , B.EHR_ATTRB_VALUE, '')) LABORATORY ");
			sql.append(" FROM  EMR_CLINICAL_DETAIL A, EMR_HEALTH_RECORD B ");
			sql.append(" WHERE B.EHR_ATTRB_CODE IN ( 'DO007', 'DO003', 'DO002', 'DO005', 'DO004', 'DO008') ");
			sql.append(" AND A.ECD_EM_NUM = B.EHR_EMR_NUM AND A.ECD_PAT_NUM = ? ");
			sql.append(" GROUP BY   B.EHR_CRT_DT , B.EHR_DTL_CODE ");
			sql.append(" ORDER BY B.EHR_DTL_CODE DESC ");
		} else if ("PREVIOUS_CONSULT_RECORDS".equals(action)) {
			sql.append(
					" SELECT  TO_CHAR(B.EHR_CRT_DT, 'DD-MON-YYYY hh:mi AM') CREATE_DATE, B.EHR_DTL_CODE, MAX(DECODE(B.EHR_ATTRB_CODE,'CR001' , B.EHR_ATTRB_VALUE, '')) REFER_BY_DOCTOR_NAME, ");
			sql.append(" MAX(DECODE(B.EHR_ATTRB_CODE,'CR003' , B.EHR_ATTRB_VALUE, '')) REF_BY_CONSULTANT, ");
			sql.append(" MAX(DECODE(B.EHR_ATTRB_CODE,'CR002' , B.EHR_ATTRB_VALUE, '')) REFER_TO_DOCTOR_NAME, ");
			sql.append(" MAX(DECODE(B.EHR_ATTRB_CODE,'CR004' , B.EHR_ATTRB_VALUE, '')) REF_TO_CONSULTANT, ");
			sql.append(" MAX(DECODE(B.EHR_ATTRB_CODE,'CR006' , B.EHR_ATTRB_VALUE, '')) CLINICAL_NOTES, ");
			sql.append(" MAX(DECODE(B.EHR_ATTRB_CODE,'CR007' , B.EHR_ATTRB_VALUE, '')) CONSULT_OPINION ");
			sql.append(" FROM  EMR_CLINICAL_DETAIL A, EMR_HEALTH_RECORD B ");
			sql.append(" WHERE B.EHR_ATTRB_CODE IN ( 'CR001', 'CR003', 'CR002', 'CR004', 'CR006', 'CR007') ");
			sql.append(" AND A.ECD_EM_NUM = B.EHR_EMR_NUM AND A.ECD_PAT_NUM = ? ");
			sql.append(" GROUP BY   B.EHR_CRT_DT , B.EHR_DTL_CODE ");
			sql.append(" ORDER BY B.EHR_DTL_CODE DESC ");
		} else if ("PREVIOUS_INVESTIGATION_RECORDS".equals(action)) {

		}

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		return ps;
	}

	@Override
	public boolean deleteDoctorData(String emrDetId) throws SQLException {
		boolean flag = false;

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			try (PreparedStatement ps = createPreparedStatement6(con, emrDetId)) {

				int result = ps.executeUpdate();

				if (result != 0)
					flag = true;

			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				e.printStackTrace();
			}

			con.commit();
			con.setAutoCommit(true);
			if (flag)
				return true;

		}

		return false;

	}

	private PreparedStatement createPreparedStatement6(Connection con, String edNo) throws SQLException {

		System.out.println("EMR DET ID: " + edNo);

		String sql = "DELETE FROM EMR_HEALTH_RECORD WHERE EHR_DTL_CODE = ?";

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		ps.setString(1, edNo);

		return ps;
	}

	@Override
	public boolean updateDoctorNote(String doctorNote, String emrDetId) throws SQLException {
		boolean flag = false;

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			try (PreparedStatement ps = createPreparedStatement7(con, doctorNote, emrDetId)) {

				int result = ps.executeUpdate();

				if (result != 0)
					flag = true;

			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				e.printStackTrace();
			}

			con.commit();
			con.setAutoCommit(true);
			if (flag)
				return true;

		}

		return false;
	}

	private PreparedStatement createPreparedStatement7(Connection con, String doctorNote, String edNo)
			throws SQLException {

		System.out.println("EMR DET ID : " + edNo);
		System.out.println("DOCTOR NOTE : " + doctorNote);

		String sql = "UPDATE EMR_HEALTH_RECORD SET EHR_ATTRB_VALUE = ?  WHERE EHR_DTL_CODE = ? and EHR_ATTRB_CODE='DN004'";

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		ps.setString(1, doctorNote);
		ps.setString(2, edNo);

		return ps;
	}

	@Override
	public List<List<String>> getDoctorNote(String emrDetId) throws SQLException {

		List<List<String>> list = new ArrayList<>();
		List<String> col = new ArrayList<>();
		List<String> row = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement8(con, emrDetId);
				ResultSet rs = ps.executeQuery()) {

			ResultSetMetaData rsmd = rs.getMetaData();

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				col.add(rsmd.getColumnName(i));
			}

			list.add(col);

			if (rs.next()) {

				do {
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						row.add(rs.getString(rsmd.getColumnName(i)));
					}
				} while (rs.next());

			}

			list.add(row);

		}
		return list;

	}

	private PreparedStatement createPreparedStatement8(Connection con, String edNo) throws SQLException {

		System.out.println("EMR DET ID: " + edNo);

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT MAX(DECODE(B.EHR_ATTRB_CODE, 'DN001',B.EHR_ATTRB_VALUE,'')) DOCTOR_NAME ");
		sql.append(" , MAX(DECODE(B.EHR_ATTRB_CODE, 'DN004',B.EHR_ATTRB_VALUE,'')) DOCTOR_NOTE ");
		sql.append(" , B.EHR_DTL_CODE, B.EHR_CRT_DT ");
		sql.append(" FROM EMR_CLINICAL_DETAIL A, EMR_HEALTH_RECORD B ");
		sql.append(" WHERE B.EHR_ATTRB_CODE IN ( 'DN004', 'DN001') ");
		sql.append(" AND A.ECD_EM_NUM = B.EHR_EMR_NUM AND b.EHR_DTL_CODE = ? ");
		sql.append(" GROUP BY B.EHR_DTL_CODE, B.EHR_CRT_DT ");
		sql.append(" ORDER BY EHR_DTL_CODE DESC ");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		ps.setString(1, edNo);

		return ps;
	}

	@Override
	public List<String> getParameterList(String paramaterType) throws SQLException {

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement9(con, paramaterType);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				do {
					list.add(rs.getString(1));
				} while (rs.next());
			}
		}
		return list;
	}

	private PreparedStatement createPreparedStatement9(Connection con, String parameterType) throws SQLException {

		String sql = "";

		if ("BG".equals(parameterType)) {
			sql = "SELECT GPM_PARAMETER_VALUE FROM GA_PARAMETER_MASTER WHERE GPM_PARAMETER_TYPE = ?";
		}

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		ps.setString(1, parameterType);

		return ps;
	}

	@Override
	public List<List<String>> fetchGobalTempData(String ipnumber, String[] arr) throws SQLException {

		List<List<String>> list = new ArrayList<>();
		List<String> col = new ArrayList<>();
		List<String> row = new ArrayList<>();
		List<String> paramNameList = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			try (PreparedStatement ps = createPreparedStatement10(con, arr)) {

				int affectedRecords[] = ps.executeBatch();

				if (affectedRecords.length != 0)
					System.out.println("data inserted successfully");

			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				throw e;
			}

			con.commit();
			con.setAutoCommit(true);

			try (PreparedStatement ps = createPreparedStatement11(con);) {

				for (String paramCode : arr) {

					ps.setString(1, paramCode);

					try (ResultSet rs = ps.executeQuery()) {

						if (rs.next()) {
							paramNameList.add(rs.getString(1));
						}
					}
				}

			}

			try (PreparedStatement ps = createPreparedStatement12(con, arr, ipnumber, paramNameList);
					ResultSet rs = ps.executeQuery()) {

				ResultSetMetaData rsmd = rs.getMetaData();

				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					col.add(rsmd.getColumnName(i));
				}

				list.add(col);

				if (rs.next()) {
					do {
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							row.add(rs.getString(rsmd.getColumnName(i)));
						}
					} while (rs.next());
				}
				list.add(row);
			}

		}

		return list;

	}

	private PreparedStatement createPreparedStatement10(Connection con, String[] arr) throws SQLException {

		System.out.println("PARAM VALUE : " + arr[0]);

		String sql = "INSERT INTO GTT_EHR_ATTRB values(?)";

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		for (String paramName : arr) {
			ps.setString(1, paramName);
			ps.addBatch();
		}

		return ps;
	}

	private PreparedStatement createPreparedStatement11(Connection con) throws SQLException {

		String sql = "SELECT EAM_ATTRB_DESC FROM EMR_ATTRIBUTE_MASTER WHERE EAM_ATTRB_CODE = ?";

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		return ps;
	}

	private PreparedStatement createPreparedStatement12(Connection con, String[] arr, String ipNumber,
			List<String> paramNameList) throws SQLException {

		System.out.println("paramNameList Size : " + paramNameList);
		System.out.println("paramNameList Size : " + paramNameList.size());
		System.out.println("Arr Size : " + arr.length);

		int index = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select  to_char(b.EHR_CRT_DT, 'dd-MON-yyyy hh:mi'), b.EHR_DTL_CODE, ");

		for (String paramCode : arr) {
			if (index + 1 == arr.length) {
				sql.append("max(decode(b.ehr_attrb_code,'" + paramCode + "' , b.EHR_ATTRB_VALUE, '')) " + "\""
						+ paramNameList.get(index) + "\"");
			} else {
				sql.append("max(decode(b.ehr_attrb_code,'" + paramCode + "' , b.EHR_ATTRB_VALUE, '')) " + "\""
						+ paramNameList.get(index) + "\"" + ",");
			}
			index++;
		}
		sql.append(" FROM  EMR_CLINICAL_DETAIL A, EMR_HEALTH_RECORD B ");
		sql.append(" WHERE B.EHR_ATTRB_CODE IN (SELECT * FROM GTT_EHR_ATTRB) ");
		sql.append(" AND A.ECD_EM_NUM = B.EHR_EMR_NUM AND A.ECD_PAT_NUM = ? ");
		sql.append(" GROUP BY   B.EHR_CRT_DT , B.EHR_DTL_CODE ");
		sql.append(" ORDER BY B.EHR_DTL_CODE DESC ");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);

		return ps;
	}

	@Override
	public List<String> getPreviousRecordNo(String ipNumber, String paramaterType) throws SQLException {

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement13(con, ipNumber, paramaterType);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				do {
					list.add(rs.getString(1));
					list.add(rs.getString(2));
					list.add(rs.getString(3));
				} while (rs.next());
			}
		}
		return list;
	}

	private PreparedStatement createPreparedStatement13(Connection con, String ipNumber, String parameterType)
			throws SQLException {

		// String sql = "SELECT DISTINCT EHR_DTL_CODE, to_char(EHR_CRT_DT, 'dd-MON-yyyy
		// hh:mi AM') as previous_date, EHR_CRT_DT FROM EMR_HEALTH_RECORD WHERE
		// EHR_ATTRB_CODE IN (SELECT EAM_ATTRB_CODE FROM EMR_ATTRIBUTE_MASTER WHERE
		// EAM_ATTRB_TYPE = ?) ORDER BY EHR_CRT_DT DESC";
		StringBuilder sql = new StringBuilder();

		sql.append(
				" SELECT distinct b.EHR_DTL_CODE, to_char(b.EHR_CRT_DT, 'dd-MON-yyyy hh:mi AM') as previous_date, b.EHR_CRT_DT ");
		sql.append(" FROM EMR_CLINICAL_DETAIL a, ");
		sql.append(" EMR_HEALTH_RECORD b ");
		sql.append(" WHERE ");
		sql.append(" a.ECD_PAT_NUM = ? ");
		sql.append(" and a.ECD_EM_NUM = b.EHR_EMR_NUM ");
		sql.append(
				" and b.EHR_ATTRB_CODE IN (SELECT EAM_ATTRB_CODE FROM EMR_ATTRIBUTE_MASTER WHERE EAM_ATTRB_TYPE = ?) ORDER BY b.EHR_CRT_DT DESC ");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		ps.setString(1, ipNumber);
		ps.setString(2, parameterType);

		return ps;
	}

	@Override
	public Map<String, String> getPreviousData(String edNumber, String attrType) throws SQLException {
		System.out.println("In Get Previous Data");

		Map<String, String> map = new HashMap<>();

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement14(con, edNumber, attrType);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				do {
					map.put(rs.getString(1), rs.getString(2));
				} while (rs.next());
			}
		}

		return map;
	}

	private PreparedStatement createPreparedStatement14(Connection con, String edNumber, String attrType)
			throws SQLException {
		String sql = "SELECT EHR_ATTRB_CODE, EHR_ATTRB_VALUE FROM EMR_HEALTH_RECORD WHERE  EHR_ATTRB_CODE IN (SELECT EAM_ATTRB_CODE FROM EMR_ATTRIBUTE_MASTER WHERE EAM_ATTRB_TYPE = ?) AND EHR_DTL_CODE = ?";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, attrType);
		ps.setString(2, edNumber);
		return ps;
	}

	@Override
	public boolean updateNote(String edNo, Map<String, String> records) throws SQLException {

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			try (PreparedStatement ps = createPreparedStatement15(con, edNo, records)) {

				int affectedRecords[] = ps.executeBatch();

				System.out.println("Updated Records : " + affectedRecords);

			} catch (SQLException e) {
				// TODO: handle exception
				con.rollback();
				con.setAutoCommit(true);
				throw e;
			}

			con.commit();
			con.setAutoCommit(true);

			return true;

		}

	}

	private PreparedStatement createPreparedStatement15(Connection con, String edNumber, Map<String, String> records)
			throws SQLException {

		Set<Map.Entry<String, String>> set = records.entrySet();

		System.out.println("Updated List : " + set);

		String sql = "UPDATE EMR_HEALTH_RECORD SET EHR_ATTRB_VALUE = ? WHERE EHR_DTL_CODE = ? AND EHR_ATTRB_CODE= ?";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());

		for (Map.Entry<String, String> keyValue : set) {

			System.out.println("Updated Key : " + keyValue.getKey() + " Value :" + keyValue.getValue());

			ps.setString(1, keyValue.getValue());
			ps.setString(2, edNumber);
			ps.setString(3, keyValue.getKey());
			ps.addBatch();
		}

		return ps;
	}

	public List<List<String>> getPreviousRecord(String ipNumber, String eamType) throws SQLException {

		System.out.println("In getPreviousRecord method");
		List<List<String>> list = new ArrayList<>();
		List<String> col = new ArrayList<>();
		List<String> row = new ArrayList<>();
		List<String> keyValue = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {

			try (PreparedStatement ps = createPreparedStatement16(con, eamType); ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					do {
						keyValue.add(rs.getString(1));
						keyValue.add(rs.getString(2));
					} while (rs.next());
				}
			}

			try (PreparedStatement ps = createPreparedStatement17(con, ipNumber, eamType, keyValue);
					ResultSet rs = ps.executeQuery()) {

				ResultSetMetaData rsmd = rs.getMetaData();

				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					col.add(rsmd.getColumnName(i));
				}

				list.add(col);

				if (rs.next()) {
					do {
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							row.add(rs.getString(rsmd.getColumnName(i)));
						}
					} while (rs.next());
				}
				list.add(row);
			}
		}

		return list;

	}

	private PreparedStatement createPreparedStatement16(Connection con, String eamType) throws SQLException {

		StringBuilder sql = new StringBuilder();

		sql.append(
				" SELECT EAM_ATTRB_CODE, EAM_ATTRB_DESC FROM EMR_ATTRIBUTE_MASTER WHERE EAM_ATTRB_TYPE=? AND EAM_ACTIVE_FLG = 'A' AND EAM_REMARKS <> ? ORDER BY TO_NUMBER(EAM_ATTRB_SLNO) ");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		ps.setString(1, eamType);
		ps.setString(2, eamType);

		return ps;
	}

	private PreparedStatement createPreparedStatement17(Connection con, String ipNumber, String eamType,
			List<String> keyValue) throws SQLException {

		int i = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select  to_char(d.EHR_CRT_DT, 'dd-MON-yyyy hh:mi AM') \"CREATE_DATE\", ");

		// System.out.println("KEYVALUE size : " + keyValue.size());

		while (i < keyValue.size()) {
			// System.out.println("Size of i " + i);
			if (i + 2 == keyValue.size()) {
				sql.append("max(decode(d.ehr_attrb_code,'" + keyValue.get(i) + "' , d.EHR_ATTRB_VALUE, '')) " + "\""
						+ keyValue.get(i + 1) + "\"");
			} else {
				sql.append("max(decode(d.ehr_attrb_code,'" + keyValue.get(i) + "' , d.EHR_ATTRB_VALUE, '')) " + "\""
						+ keyValue.get(i + 1) + "\"" + ",");
			}
			i += 2;
		}
		sql.append(" FROM ");
		sql.append(" RE_REGISTRATION_HEADER a, ");
		sql.append(" WA_ADMISSION_TXN b , ");
		sql.append(" EMR_CLINICAL_DETAIL c , ");
		sql.append(" EMR_HEALTH_RECORD d ");
		sql.append(" WHERE ");
		sql.append(" b.WAT_MR_NUM = a.RRH_MR_NUM ");
		sql.append(" AND WAT_PAT_STATUS = 'ADIP' ");
		sql.append(" AND c.ECD_PAT_NUM = b.WAT_IP_NUM ");
		sql.append(" AND c.ECD_PAT_NUM = ? ");
		sql.append(
				" AND d.EHR_ATTRB_CODE in (SELECT EAM_ATTRB_CODE FROM EMR_ATTRIBUTE_MASTER WHERE EAM_ATTRB_TYPE = ? and EAM_REMARKS <> ? ) ");
		sql.append(" AND d.EHR_EMR_NUM = c.ECD_EM_NUM ");
		sql.append(" GROUP BY d.EHR_CRT_DT ");

		System.out.println("Get Previous record Query : \r\n" + sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		ps.setString(2, eamType);
		ps.setString(3, eamType);

		return ps;
	}

	public List<List<String>> getExcelHeaderRange(String eamType) throws SQLException {

		System.out.println("In getPreviousRecord method");
		List<List<String>> list = new ArrayList<>();
		List<String> col = new ArrayList<>();
		List<String> row = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {

			try (PreparedStatement ps = createPreparedStatement18(con, eamType); ResultSet rs = ps.executeQuery()) {

				ResultSetMetaData rsmd = rs.getMetaData();

				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					col.add(rsmd.getColumnName(i));
				}

				list.add(col);

				if (rs.next()) {
					do {
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							row.add(rs.getString(rsmd.getColumnName(i)));
						}
					} while (rs.next());
				}
				list.add(row);
			}
		}

		return list;

	}

	private PreparedStatement createPreparedStatement18(Connection con, String eamType) throws SQLException {

		StringBuilder sql = new StringBuilder();

		sql.append(
				" SELECT EAM_ATTRB_DESC, (SELECT COUNT(*) FROM EMR_ATTRIBUTE_MASTER WHERE EAM_REMARKS = A.EAM_ATTRB_CODE) RANGE FROM EMR_ATTRIBUTE_MASTER A WHERE EAM_REMARKS = ? ");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, eamType);

		return ps;
	}

	public List<String> getWardList(String empCode) throws SQLException {

		int doctorCount = 0;
		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {

			try (PreparedStatement ps = createPreparedStatement19(con, empCode); ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					do {
						doctorCount = Integer.valueOf(rs.getString(1));
					} while (rs.next());
				}
			}

			try (PreparedStatement ps = createPreparedStatement20(con, doctorCount, empCode);
					ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					do {
						list.add(rs.getString(1));
						list.add(rs.getString(2));
					} while (rs.next());
				}
			}

			System.out.println("Indoor Patient List : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement19(Connection con, String empCode) throws SQLException {
		String sql = "SELECT COUNT(*) FROM AS_USER_ROLES WHERE HUR_USER_ID = ? AND HUR_ROLE_CD = 'DOC1'";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, empCode);
		return ps;
	}

	public List<String> getServiceList() throws SQLException {

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement24(con); ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					do {
						list.add(rs.getString(1));
						list.add(rs.getString(2));
					} while (rs.next());
				}
			}

			System.out.println("Indoor Patient List : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement24(Connection con) throws SQLException {
		String sql = "SELECT DISTINCT b.BMH_MAJOR_CD, UPPER(b.BMH_MAJOR_DESC) FROM " + "        BI_SERVICE_MASTER a,"
				+ "        BI_MAJORMINOR_HEADER b" + "        WHERE " + "        a.BSM_SERVICE_STATUS = 'A' "
				+ "        AND a.BSM_MAJOR_CD = b.BMH_MAJOR_CD "
				+ "        AND b.BMH_MAJOR_CD <> 'DKMJ' ORDER BY UPPER(b.BMH_MAJOR_DESC)";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());

		return ps;
	}
	
	

	public List<ServiceOrder> getServiceRateList(String serviceCat , String serviceDesc) throws SQLException {

		List<ServiceOrder> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement25(con, serviceCat, serviceDesc); ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					do {
						ServiceOrder serviceOrder = new ServiceOrder();
						serviceOrder.setServiceId(rs.getString(1));
						serviceOrder.setMinorCode(rs.getString(2));
						serviceOrder.setServiceCode(rs.getString(3));
						serviceOrder.setServiceName(rs.getString(4));
						serviceOrder.setRate(rs.getString(5));
						serviceOrder.setDiscount(rs.getString(6));
						serviceOrder.setQty("1");
						serviceOrder.setDiscountAmount("0");
						serviceOrder.setNetAmount("0");
						list.add(serviceOrder);
					} while (rs.next());
				}
			}
			System.out.println("Indoor Patient List : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement25(Connection con, String serviceCat, String serviceDesc) throws SQLException {
		String sql = "select s.BSM_SERVICE_ID,s.BSM_MINOR_CD, s.BSM_SERVICE_CD, s.bsm_service_desc,d.brd_dsct_rate,s.bsm_cash_discount "
				+ " from   bi_category_rate_detail    d" + " ,bi_category_rate_header    h"
				+ " ,bi_service_master          s" + "  where  d.brd_ctgry_dsct_cd = h.bcr_ctgry_dsct_cd "
				+ "  and  h.bcr_ctgry_cd      = 'REG' " + "  and  h.bcr_grade_cd      = 'REG' "
				+ "  and  h.bcr_bill_class_cd = 'NORMAL' " + "  and  d.brd_ssrvc_cd      = s.bsm_service_id "
				+ "  and  s.bsm_service_status = 'A' " + "  and  d.brd_dsct_rate     > 0 " + "  and s.BSM_MAJOR_CD = ? and upper(s.bsm_service_desc) like upper('%'|| ? ||'%')";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, serviceCat.trim());
		ps.setString(2, serviceDesc.trim());
		return ps;
	}
	
	public List<String> getPanelServiceCodeList(String serviceCode) throws SQLException {

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement26(con, serviceCode); ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					do {
						list.add(rs.getString(1));
					} while (rs.next());
				}
			}
			System.out.println("Indoor Patient List : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement26(Connection con, String serviceCode) throws SQLException {
		String sql = " select LAT_TEST_CD from ls_panel_test where LAT_PANEL_CD = ?";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, serviceCode);
		return ps;
	}

	private PreparedStatement createPreparedStatement20(Connection con, int doctorCount, String empCode)
			throws SQLException {

		StringBuilder sql = new StringBuilder();

		System.out.println("Doctor Count : " + doctorCount);

		if (doctorCount == 0) {
			sql.append("select distinct a.WAT_CURR_WARD_CD, b.WWM_WARD_DESC from   wa_admission_txn a,"
					+ "  wa_ward_master b  where a.WAT_CURR_WARD_CD = b.WWM_WARD_CD");
		} else {
			sql.append("select distinct a.WAT_CURR_WARD_CD, b.WWM_WARD_DESC from  wa_admission_txn a, "
					+ "    wa_ward_master b  where  a.WAT_CURR_WARD_CD = b.WWM_WARD_CD and a.WAT_DOCTOR_INCHARGE = ?");
		}

		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());

		if (doctorCount != 0) {
			ps.setString(1, empCode);
		}

		return ps;
	}

	public ArrayList<ArrayList<String>> getPatientList(String empCode, String wardId) throws SQLException {

		ArrayList<String> row = new ArrayList<>();
		ArrayList<String> column = new ArrayList<>();
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int rowCount = 0;

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement21(con, empCode, wardId);
				ResultSet rs = ps.executeQuery()) {

			ResultSetMetaData rsmd = rs.getMetaData();

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				column.add(rsmd.getColumnName(i));
			}

			list.add(column);

			if (rs.next()) {
				do {
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						row.add(rs.getString(rsmd.getColumnName(i)));
					}
					rowCount++;
				} while (rs.next());
			}

			list.add(row);

		}

		System.out.println("Row Count + " + rowCount);
		System.out.println("Indoor Patient List : " + list);
		System.out.println("Indoor Patient List size : " + list.get(1).size());
		return list;

	}

	private PreparedStatement createPreparedStatement21(Connection con, String empCode, String wardId)
			throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append(" select a.WAT_IP_NUM \"IP\", b.RRH_FIRST_NAME||' '||b.RRH_MIDDLE_NAME||' '||b.RRH_LAST_NAME NAME, "
				+ "						                           d.WWM_WARD_DESC WARD, a.WAT_BED_CD \"BED\", a.WAT_ADMN_DT \"ADMN DATE\",  c.EEM_FIRST_NAME||' '||c.EEM_MIDDLE_NAME||' '||c.EEM_LAST_NAME as \"ADMN DOCTOR\" ,e.GDM_DEPT_DESC \"SPECIALITY\", f.gps_patient_subctgry_desc \"CATEGORY\""
				+ "                                                    	  from "
				+ "                                                         wa_admission_txn a, "
				+ "                                                         RE_REGISTRATION_HEADER b, "
				+ "                                                         hr_employee_master c, "
				+ "                                                         wa_ward_master d, "
				+ "                                                         ga_department_master e,"
				+ "                                                         ga_patient_subcategory_master f"
				+ "                                                         where "
				+ "                                                         a.WAT_MR_NUM = b.RRH_MR_NUM "
				+ "                                                         and a.WAT_CURR_WARD_CD = d.WWM_WARD_CD "
				+ "                                                         and a.WAT_DOCTOR_INCHARGE = c.EEM_EMP_NUM"
				+ "                                                         and a.WAT_ADMM_DEPT = e.GDM_DEPT_CD "
				+ "                                                         and WAT_pat_status = 'ADIP' "
				+ "                                                         and a.WAT_CURR_WARD_CD = d.WWM_WARD_CD ");

		if (!"0".equals(wardId)) {
			sql.append(" and a.WAT_CURR_WARD_CD = ? ");
		}

		sql.append("                                                         and a.WAT_DOCTOR_INCHARGE = ? "
				+ "                                                         and f.GPS_PATIENT_CTGRY_CD = a.WAT_PATIENT_CATEGORY_CD"
				+ "                                                         and f.GPS_PATIENT_SUBCTGRY_CD = a.WAT_PATIENT_SUBCATEGORY_CD"
				+ "                                                         order by name");

		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());

		if (!"0".equals(wardId)) {
			ps.setString(1, wardId);
			ps.setString(2, empCode);
		} else {
			ps.setString(1, empCode);
		}

		return ps;
	}

	public List<IndoorPatient> getPatientList1(String empCode, String wardId) throws SQLException {

		List<IndoorPatient> list = new ArrayList<>();
		int rowCount = 0;

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement21(con, empCode, wardId);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				IndoorPatient patient = new IndoorPatient();
				patient.setIpNumber(rs.getString(1));
				patient.setIpName(rs.getString(2));
				patient.setWard(rs.getString(3));
				patient.setBedNumber(rs.getString(4));
				patient.setAdmissionDate(rs.getString(5));
				patient.setAdmittingDoctor(rs.getString(6));
				patient.setSpeciality(rs.getString(7));
				patient.setSubCategory(rs.getString(8));
				list.add(patient);
			}

		}

		System.out.println("Row Count + " + rowCount);
		System.out.println("Indoor Patient List : " + list);

		return list;

	}

	public List<String> getParentLink(String userRole) throws SQLException {

		ArrayList<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement22(con, userRole);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				do {
					list.add(rs.getString(1));
					list.add(rs.getString(2));
					list.add(rs.getString(3));
				} while (rs.next());
			}

			System.out.println("User Link List : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement22(Connection con, String userRole) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append(" select  a.HCM_CATG_CD, a.HCM_CATG_DESC, count(*) ");
		sql.append(" from ");
		sql.append(" as_catg_master a,");
		sql.append(" as_role_form_access b ");
		sql.append(" where ");
		sql.append(" a.HCM_CATG_CD = b.HFA_CATG_CD ");
		sql.append(" and b.hfa_role_cd = ? ");
		sql.append(" group by a.HCM_CATG_CD, a.HCM_CATG_DESC ");

		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());

		ps.setString(1, userRole.trim());

		return ps;
	}

	public List<String> getChildLink(String userRole, String catCode) throws SQLException {

		ArrayList<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement23(con, userRole, catCode);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				do {
					list.add(rs.getString(1));
					list.add(rs.getString(2));
				} while (rs.next());
			}

			System.out.println("User Link List : " + list);

			return list;
		}
	}

	private PreparedStatement createPreparedStatement23(Connection con, String userRole, String catCode)
			throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append(" select b.HFD_FORM_DESC, b.HFD_FORM_PATH from  ");
		sql.append(" as_catg_master a, ");
		sql.append(" as_form_details b, ");
		sql.append(" as_catg_form c, ");
		sql.append(" as_role_form_access d ");
		sql.append(" where ");
		sql.append(" a.HCM_CATG_CD = c.HCF_CATG_CD ");
		sql.append(" and b.HFD_FORM_CD = c.HCF_FORM_CD ");
		sql.append(" and d.HFA_FORM_CD = b.HFD_FORM_CD ");
		sql.append(" and a.HCM_CATG_CD = ? ");
		sql.append(" and d.HFA_ROLE_CD = ? order by c.HCF_SEQ_NO ");

		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, catCode.trim());
		ps.setString(2, userRole.trim());

		return ps;
	}

}
