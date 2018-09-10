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
import com.gnrchospitals.dto.Patient;
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

				} while (rs.next());
			}

			System.out.println("List is : " + patient);

		}
		return patient;
	}

	private PreparedStatement createPreparedStatement(Connection con, String ipNumber) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append(
				" SELECT  B.RRH_MR_NUM \"MRD NO\", A.WAT_IP_NUM \"IP NO\", B.RRH_FIRST_NAME||' '||B.RRH_MIDDLE_NAME||' '||B.RRH_LAST_NAME NAME ");
		sql.append(
				" , (SELECT C.GPM_PARAMETER_VALUE FROM GA_PARAMETER_MASTER C WHERE C.GPM_PARAMETER_CD = B.RRH_PAT_SEX AND C.GPM_PARAMETER_TYPE = 'SEX') SEX");
		sql.append(" , ROUND((trunc(SYSDATE) - B.RRH_PAT_DOB) / 365) AGE");
		sql.append(
				" , A.WAT_ADMN_DT \"ADMISSION DATE\", 'DR. ' || D.EEM_FIRST_NAME||' '|| D.EEM_MIDDLE_NAME||''|| D.EEM_LAST_NAME \"DOCTOR INCHARGE\"");
		sql.append(
				" , g.GDM_DEPT_DESC speciality , a.WAT_BED_CD \"BED NO\" , e.WWM_WARD_DESC ward , f.GPM_PARAMETER_VALUE \"MARITAL STATUS\" ");
		sql.append(
				" from wa_admission_txn a, RE_REGISTRATION_HEADER b,  hr_employee_master d, WA_WARD_MASTER e, ga_parameter_master f, ga_department_master g ");
		sql.append(
				" WHERE a.WAT_MR_NUM = b.RRH_MR_NUM and d.EEM_EMP_NUM = a.WAT_DOCTOR_INCHARGE and a.WAT_WARD_CD = e.WWM_WARD_CD and ");
		sql.append(
				" b.RRH_MARITAL_STATUS =  f.GPM_PARAMETER_CD and a.WAT_ADMM_DEPT = g.GDM_DEPT_CD and a.WAT_IP_NUM = ? ");

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
				" SELECT EAM_ATTRB_CODE, EAM_ATTRB_DESC FROM EMR_ATTRIBUTE_MASTER WHERE EAM_ATTRB_TYPE=? AND EAM_ACTIVE_FLG = 'A' ORDER BY TO_NUMBER(EAM_ATTRB_SLNO) ");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		ps.setString(1, eamType);

		return ps;
	}

	private PreparedStatement createPreparedStatement17(Connection con, String ipNumber, String eamType,
			List<String> keyValue) throws SQLException {

		int i = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select  to_char(d.EHR_CRT_DT, 'dd-MON-yyyy hh:mi') \"CREATE_DATE\", ");
		
		//System.out.println("KEYVALUE size : " + keyValue.size());

		while (i < keyValue.size()) {
			//System.out.println("Size of i " + i);
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
				" AND d.EHR_ATTRB_CODE in (SELECT EAM_ATTRB_CODE FROM EMR_ATTRIBUTE_MASTER WHERE EAM_ATTRB_TYPE = ?) ");
		sql.append(" AND d.EHR_EMR_NUM = c.ECD_EM_NUM ");
		sql.append(" GROUP BY d.EHR_CRT_DT ");

		System.out.println("Get Previous record Query : \r\n" + sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		ps.setString(2, eamType);

		return ps;
	}

}
