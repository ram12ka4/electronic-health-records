package com.gnrchospitals.daoimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

public class PatientDaoImpl implements PatientDao {

	private Patient patient = Patient.getInstance();

	@Override
	public Patient findByIpNumber(String ipNumber) throws SQLException {

		System.out.println("In findByIpNumber");
		try (Connection con = LoginDBConnection.getConnection()) {
			con.setAutoCommit(false);
			try (CallableStatement cs = createCallableStatement13(con, ipNumber)) {

				ResultSet rs = ((OracleCallableStatement)cs).getCursor(2);
				int i = 0;

				if (rs.next()) {
					do {
						patient.setMrdNumber(rs.getString(++i));
						patient.setIpNumber(rs.getString(++i));
						patient.setPatientName(rs.getString(++i));
						patient.setSex(rs.getString(++i));
						patient.setAge(rs.getString(++i));
						patient.setAdmissionDate(rs.getString(++i));
						patient.setDoctorId(rs.getString(++i));
						patient.setDoctorIncharge(rs.getString(++i));
						patient.setSpeciality(rs.getString(++i));
						patient.setBedNo(rs.getString(++i));
						patient.setWardCode(rs.getString(++i));
						patient.setWardDesc(rs.getString(++i));
						patient.setMaritalStatus(rs.getString(++i));
						patient.setPatientCategoryCode(rs.getString(++i));
						patient.setPatientCategory(rs.getString(++i));
						patient.setPatientSubCategoryCode(rs.getString(++i));
						patient.setPatientSubCategory(rs.getString(++i));

					} while (rs.next());
				}
			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				e.printStackTrace();
			}
		}

		return patient;
	}
	
	private CallableStatement createCallableStatement13(Connection con, String ipNumber) throws SQLException {
		CallableStatement cs = con.prepareCall("BEGIN PKGJV_EMR.PKG_GET_PATIENT_INFO(?,?); END;");
		cs.setString(1, ipNumber);
		cs.registerOutParameter(2, OracleTypes.CURSOR);
		cs.execute();
		return cs;
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

	/*
	 * @Override public List<List<String>> getDoctorNote(String emrDetId) throws
	 * SQLException {
	 * 
	 * List<List<String>> list = new ArrayList<>(); List<String> col = new
	 * ArrayList<>(); List<String> row = new ArrayList<>();
	 * 
	 * try (Connection con = LoginDBConnection.getConnection(); PreparedStatement ps
	 * = createPreparedStatement8(con, emrDetId); ResultSet rs = ps.executeQuery())
	 * {
	 * 
	 * ResultSetMetaData rsmd = rs.getMetaData();
	 * 
	 * for (int i = 1; i <= rsmd.getColumnCount(); i++) {
	 * col.add(rsmd.getColumnName(i)); }
	 * 
	 * list.add(col);
	 * 
	 * if (rs.next()) {
	 * 
	 * do { for (int i = 1; i <= rsmd.getColumnCount(); i++) {
	 * row.add(rs.getString(rsmd.getColumnName(i))); } } while (rs.next());
	 * 
	 * }
	 * 
	 * list.add(row);
	 * 
	 * } return list;
	 * 
	 * }
	 */

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

		System.out.println("Get Previous record Query : " + sql.toString());

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
		String sql = "SELECT DISTINCT b.BMH_MAJOR_CD, UPPER(b.BMH_MAJOR_DESC) " + "FROM "
				+ "        BI_SERVICE_MASTER a," + "        BI_MAJORMINOR_HEADER b" + "        WHERE "
				+ "        a.BSM_SERVICE_STATUS = 'A' " + "        AND a.BSM_MAJOR_CD = b.BMH_MAJOR_CD "
				+ "        AND b.BMH_MAJOR_CD <> 'DKMJ' ORDER BY UPPER(b.BMH_MAJOR_DESC)";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());

		return ps;
	}

	public List<ServiceOrder> getServiceRateList(String serviceCat, String serviceDesc) throws SQLException {

		List<ServiceOrder> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement25(con, serviceCat, serviceDesc);
					ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					do {
						ServiceOrder serviceOrder = new ServiceOrder();
						serviceOrder.setServiceId(rs.getString(1));
						serviceOrder.setMinorCode(rs.getString(2));
						serviceOrder.setServiceCode(rs.getString(3));
						serviceOrder.setServiceName(rs.getString(4));
						serviceOrder.setRate(rs.getString(5));
						serviceOrder.setDiscount(rs.getString(6));
						serviceOrder.setTreatedBy(rs.getString(7));
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

	private PreparedStatement createPreparedStatement25(Connection con, String serviceCat, String serviceDesc)
			throws SQLException {
		String sql = "select s.BSM_SERVICE_ID,s.BSM_MINOR_CD, s.BSM_SERVICE_CD, s.bsm_service_desc,d.brd_dsct_rate,s.bsm_cash_discount, NVL(BSM_TREATED_BY, 'N')  treat_by "
				+ " from   bi_category_rate_detail    d" + " ,bi_category_rate_header    h"
				+ " ,bi_service_master          s" + "  where  d.brd_ctgry_dsct_cd = h.bcr_ctgry_dsct_cd "
				+ "  and  h.bcr_ctgry_cd      = 'REG' " + "  and  h.bcr_grade_cd      = 'REG' "
				+ "  and  h.bcr_bill_class_cd = 'NORMAL' " + "  and  d.brd_ssrvc_cd      = s.bsm_service_id "
				+ "  and  s.bsm_service_status = 'A' " + "  and  d.brd_dsct_rate     > 0 "
				+ "  and s.BSM_MAJOR_CD = ? and upper(s.bsm_service_desc) like upper('%'|| ? ||'%')";
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
						list.add(rs.getString(2));
					} while (rs.next());
				}
			}
			System.out.println("Indoor Patient List : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement26(Connection con, String serviceCode) throws SQLException {
		String sql = " select b.LAT_TEST_CD, a.LTM_TEST_DESC  from " + "       ls_test_master a, "
				+ "       ls_panel_test b " + "  		where " + "       a.LTM_TEST_CD = b.LAT_TEST_CD "
				+ "       and b.LAT_PANEL_CD = ?";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, serviceCode);
		return ps;
	}

	public List<String> getSpecimenList(String serviceCode) throws SQLException {

		System.out.println("in getSpecimenList");
		System.out.println("Service Code : " + serviceCode);

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement27(con, serviceCode); ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					do {
						list.add(rs.getString(2));
						list.add(rs.getString(3));
					} while (rs.next());
				}
			}

			System.out.println("Specimen List : " + list);
			return list;
		}

	}

	private PreparedStatement createPreparedStatement27(Connection con, String serviceCode) throws SQLException {
		String sql = "SELECT B.LTS_TEST_CD AS TEST_CODE, " + "B.LTS_SPECIMEN_CD AS SPECIMAN_CODE, "
				+ "A.LSM_SPECIMEN_DESC AS SPEC_DESC, LTS_DEFAULT_FLAG " + "FROM LS_SPECIMEN_MASTER A, "
				+ "LS_TEST_SPECIMEN_MASTER B " + "WHERE A.LSM_SPECIMEN_CD=B.LTS_SPECIMEN_CD "
				+ "AND B.LTS_TEST_CD  = UPPER(?) " + "ORDER BY B.LTS_DEFAULT_FLAG DESC,LSM_SPECIMEN_DESC ";

		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, serviceCode);

		return ps;
	}

	public List<String> getDoctorList() throws SQLException {

		System.out.println("in getDoctorList");

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement28(con); ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					do {
						list.add(rs.getString(1));
						list.add(rs.getString(2));
					} while (rs.next());
				}
			}

			System.out.println("Specimen List : " + list);
			return list;
		}

	}

	private PreparedStatement createPreparedStatement28(Connection con) throws SQLException {
		String sql = "SELECT DISTINCT  A.EEM_EMP_NUM,"
				+ "A.EEM_FIRST_NAME || ' ' || A.EEM_MIDDLE_NAME || ' ' || A.EEM_LAST_NAME AS EEM_NAME "
				+ "FROM    HR_EMPLOYEE_MASTER A, " + "GA_CLINICAL_STAFF_MASTER B "
				+ "WHERE A.EEM_EMP_NUM=B.GCS_EMPLOYEE_NUM " + "AND A.EEM_EMP_STATUS='A' "
				+ "AND B.GCS_STAFF_TYPE_CD IN ('CONS','SURG') " + "ORDER BY EEM_NAME ";

		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		return ps;
	}

	public String insertUpdateServiceOrder(String soNumber, String docOrderNumber, String patientNo, String netAmount,
			String doctorId, String mrdNumber, String patientType, String visitNo, String userId, String disIndication,
			String referDoctorCode, String[] serviceId, String[] qty, String[] disAmount, String[] disPercent,
			String[] specimen, String[] treatedBy, String[] specimenChecked, String voucherNumber, String checkBoxFlag)
			throws SQLException {

		System.out.println("In insertServiceOrderData");
		int result = 0;
		String soId = "";
		String returnMsg = "";
		System.out.println("Total specimen length : " + specimenChecked.length);

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			int length1 = removeDuplicateSpace(serviceId, serviceId.length);
			int length2 = removeDuplicateSpace(qty, qty.length);
			int length3 = removeDuplicateSpace(disAmount, disAmount.length);

			if (checkBoxFlag.equalsIgnoreCase("disable")) {

				// Fresh Service order entry or Service order updation
				System.out.println("Fresh Service order entry or Service order updation");

				try (CallableStatement cs = createCallableStatement2(con, soNumber, docOrderNumber, patientNo,
						netAmount, doctorId, mrdNumber, patientType, visitNo, userId)) {
					soId = cs.getString(1);
					result = cs.getInt(10);
					returnMsg = cs.getString(11);

					System.out.println("SO Number : " + soId);
					System.out.println("PKG_I_SERVORDER_HEADER_N OUT PARAMETER : " + result);
					System.out.println("PKG_I_SERVORDER_HEADER_N RETURN MSG : " + returnMsg);

				} catch (SQLException e) {
					con.rollback();
					con.setAutoCommit(true);
					e.printStackTrace();
				}

				if (result == 1) {

					/*
					 * int length1 = removeDuplicateSpace(serviceId, serviceId.length); int length2
					 * = removeDuplicateSpace(qty, qty.length); int length3 =
					 * removeDuplicateSpace(disAmount, disAmount.length);
					 */

					System.out.println("Service Id length : " + length1);
					System.out.println("Specimen Length : " + specimen.length);
					System.out.println("Treated By length : " + treatedBy.length);
					System.out.println("Specimen Checked length : " + specimenChecked);
					System.out.println("Voucher Number : " + voucherNumber);

					for (int i = 0; i < length1; i++) {

						System.out.println("Service Id : " + serviceId[i]);
						System.out.println("Qty : " + qty[i]);
						System.out.println("Discount Amount : " + disAmount[i]);
						System.out.println("Discount Percent : " + disPercent[i]);
						System.out.println("Specimen : " + specimen[i]);
						System.out.println("Treated By : " + treatedBy[i]);

						try (CallableStatement cs = createCallableStatement3(con, soId, serviceId[i], qty[i],
								disAmount[i], disIndication, referDoctorCode, disPercent[i], specimen[i], treatedBy[i],
								userId, specimenChecked[i], voucherNumber)) {

							result = cs.getInt(12);
							returnMsg = cs.getString(13);

							System.out.println("PKG_I_SERVORDER_DETAIL OUT PARAMETER : " + result);
							System.out.println("PKG_I_SERVORDER_HEADER_N RETURN MSG : " + returnMsg);

							// If you
							if (result == 0) {
								/*
								 * con.rollback(); con.setAutoCommit(true);
								 */
								break;
							}

						} catch (SQLException e) {
							con.rollback();
							con.setAutoCommit(true);
							e.printStackTrace();
						}
					} // End of for loop

					System.out.println("PKG_I_SERVORDER_DETAIL OUT PARAMETER : " + result);
					System.out.println("PKG_I_SERVORDER_HEADER_N RETURN MSG : " + returnMsg);

				} else {
					con.rollback();
					con.setAutoCommit(true);
				}
			} else {

				// After billing Service order updation along with specimen test initiation
				System.out.println("After billing Service order updation along with specimen test initiation");

				for (int i = 0; i < length1; i++) {

					if (specimenChecked[i].equalsIgnoreCase("p")) {
						continue;
					}

					try (CallableStatement cs = createCallableStatement7(con, soNumber, serviceId[i], userId,
							specimenChecked[i])) {
						soId = soNumber;
						result = cs.getInt(5);
						returnMsg = cs.getString(6);

						if (result == 0) {
							break;
						}

						System.out.println("SO Number : " + soId);
						System.out.println("PKG_I_SERVORDER_HEADER_N OUT PARAMETER : " + result);
						System.out.println("PKG_I_SERVORDER_HEADER_N RETURN MSG : " + returnMsg);

					} catch (SQLException e) {
						con.rollback();
						con.setAutoCommit(true);
						e.printStackTrace();
					}

				}

			}

			if (result == 1) {
				con.commit();
				con.setAutoCommit(true);
				System.out.println("All Transactions are commited");
			} else {
				System.out.println("All Transactions are rollbacked");
				con.rollback();
				con.setAutoCommit(true);
			}

		}

		if (result == 1)
			return soId;
		return "F" + returnMsg;

	}

	private CallableStatement createCallableStatement2(Connection con, String soNumber, String noteRefNumber,
			String patNumber, String netAmount, String doctorId, String mrdNumber, String patType, String visitNumber,
			String userId) throws SQLException {
		CallableStatement cs = con
				.prepareCall("{call PKGJV_BI_SERVICE_ORDER.PKG_I_SERVORDER_HEADER(?,?,?,?,?,?,?,?,?,?,?)}");
		cs.registerOutParameter(1, Types.VARCHAR);
		cs.setString(1, soNumber);
		cs.setString(2, patNumber);
		cs.setString(3, netAmount);
		cs.setString(4, doctorId);
		cs.setString(5, mrdNumber);
		cs.setString(6, patType);
		cs.setString(7, visitNumber);
		cs.setString(8, noteRefNumber);
		cs.setString(9, userId);
		cs.registerOutParameter(10, Types.DECIMAL);
		cs.registerOutParameter(11, Types.VARCHAR);
		cs.execute();
		return cs;
	}

	private CallableStatement createCallableStatement3(Connection con, String soNumber, String serviceId, String qty,
			String disAmount, String disIndi, String reffDocCode, String discountPercent, String specimenCode,
			String treatedBy, String userId, String specimenChecked, String voucherNumber) throws SQLException {
		CallableStatement cs = con
				.prepareCall("{call PKGJV_BI_SERVICE_ORDER.PKG_I_SERVORDER_DETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		cs.setString(1, soNumber);
		cs.setString(2, serviceId);
		cs.setString(3, qty);
		cs.setString(4, userId);
		cs.setString(5, disAmount);
		cs.setString(6, disIndi);
		cs.setString(7, treatedBy);
		cs.setString(8, discountPercent);
		cs.setString(9, specimenCode);
		cs.setString(10, specimenChecked);
		cs.setString(11, voucherNumber);
		cs.registerOutParameter(12, Types.DECIMAL);
		cs.registerOutParameter(13, Types.VARCHAR);
		cs.execute();
		return cs;
	}

	private CallableStatement createCallableStatement7(Connection con, String soNumber, String serviceId, String userId,
			String specimenChecked) throws SQLException {
		CallableStatement cs = con.prepareCall("{call PKGJV_BI_SERVICE_ORDER.PKG_P_SERVORDER_DETAIL(?,?,?,?,?,?)}");
		cs.setString(1, soNumber);
		cs.setString(2, serviceId);
		cs.setString(3, userId);
		cs.setString(4, specimenChecked);
		cs.registerOutParameter(5, Types.DECIMAL);
		cs.registerOutParameter(6, Types.VARCHAR);
		cs.execute();
		return cs;
	}

	public int removeDuplicateSpace(String arr[], int n) {
		if (n == 0 || n == 1) {
			return n;
		}
		int j = 0;
		for (int i = 0; i < arr.length; i++) {
			if (!arr[i].isEmpty()) {
				arr[j++] = arr[i];
			}
		}
		return j;
	}

	public List<List<String>> getPrevServiceOrderList(String patientNo) throws SQLException {
		System.out.println("in getPrevServiceOrderList");

		List<List<String>> list = new ArrayList<>();
		List<String> row = new ArrayList<>();
		List<String> column = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement29(con, patientNo); ResultSet rs = ps.executeQuery()) {

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
					} while (rs.next());

					list.add(row);
				}
			}

			System.out.println("Previous Service Order List : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement29(Connection con, String patientNo) throws SQLException {
		String sql = " select * from  ( "
				+ "  	select distinct a.BSH_PAT_NUM, a.BSH_TXN_NUM,  a.BSH_TXN_TOT_AMT, TO_CHAR(a.BSH_CRT_DT, 'DD-MON-YYYY hh:mi AM') ORDER_DATE, a.BSH_CRT_UID, 'NOT BILLED' VCHR_NO, NVL(a.BSH_NOTE_REF_NO, 'NOT AVAILABLE') NOTE_REF_NO "
				+ "		, count(a.BSH_TXN_NUM)  TOT_SPC , SUM(DECODE(B.BSD_PROCESS_STS,'Y',1,0))  TOT_PRC "
				+ "   	from bi_service_header a,       bi_service_detail b  where "
				+ "		a.bsh_txn_num = b.BSD_TXN_NUM       and b.BSD_VOUCHER_NO is null  "
				+ "   	GROUP BY a.BSH_PAT_NUM, a.BSH_TXN_NUM,  a.BSH_TXN_TOT_AMT, TO_CHAR(a.BSH_CRT_DT, 'DD-MON-YYYY hh:mi AM') , a.BSH_CRT_UID,  NVL(a.BSH_NOTE_REF_NO, 'NOT AVAILABLE') "
				+ "   	union all "
				+ "		select distinct  a.BSH_PAT_NUM, a.BSH_TXN_NUM,  a.BSH_TXN_TOT_AMT, TO_CHAR(a.BSH_CRT_DT, 'DD-MON-YYYY hh:mi AM') ORDER_DATE"
				+ "                , a.BSH_CRT_UID, c.BVM_VCHR_NUM, NVL(a.BSH_NOTE_REF_NO, 'NOT AVAILABLE') NOTE_REF_NO "
				+ "                ,count(a.BSH_TXN_NUM)  TOT_SPC , SUM(DECODE(B.BSD_PROCESS_STS,'Y',1,0))  TOT_PRC"
				+ "				from bi_service_header a,       bi_service_detail b,       bi_voucher_header c  where "
				+ "				     a.bsh_txn_num = b.BSD_TXN_NUM       and b.BSD_VOUCHER_NO = c.BVM_VCHR_NUM "
				+ "                    GROUP BY a.BSH_PAT_NUM, a.BSH_TXN_NUM,  a.BSH_TXN_TOT_AMT, TO_CHAR(a.BSH_CRT_DT, 'DD-MON-YYYY hh:mi AM') "
				+ "                        , a.BSH_CRT_UID, c.BVM_VCHR_NUM, NVL(a.BSH_NOTE_REF_NO, 'NOT AVAILABLE') ) "
				+ "				where BSH_PAT_NUM = ? ORDER BY 4 DESC ";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, patientNo);
		return ps;
	}

	public List<String> getServiceOrderDetail(String soNumber) throws SQLException {
		System.out.println("in getServiceOrderDetail");

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement30(con, soNumber); ResultSet rs = ps.executeQuery()) {

				ResultSetMetaData rsmd = rs.getMetaData();

				if (rs.next()) {
					do {
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							list.add(rs.getString(rsmd.getColumnName(i)));
						}
					} while (rs.next());

				}
			}

			System.out.println("Previous Service Order Detail : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement30(Connection con, String soNumber) throws SQLException {
		String sql = "select  * from ( "
				+ "select sd.bsd_txn_num  so_num , sd.bsd_service_id  serv_id , sm.bsm_service_cd  serv_cd  , replace(sm.bsm_service_desc, ',', '|')  serv_desc, sm.bsm_major_cd  maj_code, sm.bsm_minor_cd  min_code "
				+ ",sd.bsd_qty   qty  , 0  rate  , sd.bsd_conc_pct   conc_per, sd.bsd_docd , 'NOT_BILLED'   vch_num, sd.BSD_SPCMN_CD, sm.bsm_treated_by,  TO_CHAR(sh.BSH_TXN_DT, 'DD-MON-YYYY') ORDER_DATE, sd.BSD_PROCESS_STS, sh.BSH_NOTE_REF_NO   "
				+ " from        bi_service_detail   sd " + " ,bi_service_master   sm, bi_service_header sh "
				+ " where sd.bsd_service_id    = sm.bsm_service_id "
				+ "  and       sd.bsd_voucher_no   is null and     sh.bsh_txn_num = sd.BSD_TXN_NUM " + " union all "
				+ " select sd.bsd_txn_num  so_num , sd.bsd_service_id  serv_id , sm.bsm_service_cd  serv_cd  , replace(sm.bsm_service_desc, ',', '|')  serv_desc, sm.bsm_major_cd  maj_code, sm.bsm_minor_cd  min_code "
				+ " ,sd.bsd_qty   qty  , vd.bvd_srvc_rate rate  , sd.bsd_conc_pct   conc_per, sd.bsd_docd  , vd.bvd_vchr_num, sd.BSD_SPCMN_CD, sm.bsm_treated_by, TO_CHAR(sh.BSH_TXN_DT, 'DD-MON-YYYY') ORDER_DATE, sd.BSD_PROCESS_STS, sh.BSH_NOTE_REF_NO   "
				+ " from       bi_service_detail   sd " + "           ,bi_service_master   sm, bi_service_header sh "
				+ "           ,bi_voucher_detail   vd " + " where      sd.bsd_service_id    = sm.bsm_service_id "
				+ " and        sd.bsd_voucher_no    = vd.bvd_vchr_num "
				+ " and        sd.bsd_service_id    = vd.bvd_srvc_id and sh.bsh_txn_num = sd.BSD_TXN_NUM ) where     so_num  = ?";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, soNumber);
		return ps;
	}

	public String getServiceIdRate(String serviceId, String patientNo) throws SQLException {

		System.out.println("In getServiceIdRate");

		System.out.println("Service Id : " + serviceId);
		System.out.println("Patient Id : " + patientNo);

		String discountType = "";
		String discountRate = "";
		String rate = "";
		String errorCode = "";
		String errorMsg = "";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
		Date date = new Date();
		String currentDate = sdf.format(date);

		System.out.println("Current Date : " + currentDate);

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			try (CallableStatement cs = createCallableStatement4(con, serviceId, patientNo, currentDate)) {

				discountType = cs.getString(4);
				discountRate = String.valueOf(cs.getDouble(5));
				rate = String.valueOf(cs.getDouble(6));
				errorCode = cs.getString(7);
				errorMsg = cs.getString(8);

				System.out.println("Discount Type : " + discountType);
				System.out.println("Discount Rate : " + discountRate);
				System.out.println("Rate : " + rate);

			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				e.printStackTrace();
			}

			System.out.println("BSP_GET_SERVICE_RATE ERROR CODE : " + errorCode + " ERROR MSG : " + errorMsg);

		}

		return rate;

	}

	private CallableStatement createCallableStatement4(Connection con, String serviceId, String patientNo,
			String currentDate) throws SQLException {
		CallableStatement cs = con
				.prepareCall("{call PKGMM_BI_SERVORDER_SEARCH.BSP_GET_SERVICE_RATE(?,?,?,?,?,?,?,?)}");
		cs.setString(1, serviceId.trim());
		cs.setString(2, patientNo.trim());
		cs.setString(3, currentDate);
		cs.registerOutParameter(4, Types.VARCHAR);
		cs.registerOutParameter(5, Types.DECIMAL);
		cs.registerOutParameter(6, Types.DECIMAL);
		cs.registerOutParameter(7, Types.VARCHAR);
		cs.registerOutParameter(8, Types.VARCHAR);
		cs.execute();
		return cs;
	}

	public String insertUpdateDoctorOrder(String patientNo, String mrd, String visitNo, String doctorId, String wardNo,
			String bedNo, String advice, String medication, String laboratory, String diet, String progress,
			String userId, String doctorOrderoNumber) throws SQLException {

		System.out.println("In insertDoctorOrderData");
		int result = 0;
		String drNumber = "";
		String returnMsg = "";

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			try (CallableStatement cs = createCallableStatement5(con, patientNo, mrd, visitNo, doctorId, wardNo, bedNo,
					advice, medication, laboratory, diet, progress, userId, doctorOrderoNumber)) {
				// result = String.valueOf(cs.getDouble(14));
				result = cs.getInt(14);
				drNumber = cs.getString(13);
				returnMsg = cs.getString(15);
				System.out.println("Out parameter result : " + result);
				System.out.println("Out parameter drNumber : " + drNumber);
				System.out.println("Out parameter returnMsg : " + returnMsg);
			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				e.printStackTrace();
			}

			System.out.println("PKG_I_DOCTOR_NOTE OUT PARAMETER : " + result);

			if (result == 1) {
				con.commit();
				System.out.println("All Transactions are commited");
			} else {
				System.out.println("All Transactions are rollbacked");
				con.rollback();
			}

		}

		if (result == 1)
			return drNumber;
		return "0";

	}

	private CallableStatement createCallableStatement5(Connection con, String patientNo, String mrd, String visitNo,
			String doctorId, String wardNo, String bedNo, String advice, String medication, String laboratory,
			String diet, String progress, String userId, String doctorOrderoNumber) throws SQLException {
		CallableStatement cs = con.prepareCall("{call PKGJV_EMR.PKG_I_DOCTOR_NOTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		cs.setString(1, patientNo);
		cs.setString(2, mrd);
		cs.setString(3, visitNo);
		cs.setString(4, doctorId);
		cs.setString(5, wardNo);
		cs.setString(6, bedNo);
		cs.setString(7, advice);
		cs.setString(8, medication);
		cs.setString(9, laboratory);
		cs.setString(10, diet);
		cs.setString(11, progress);
		cs.setString(12, userId);
		cs.registerOutParameter(13, Types.VARCHAR);
		cs.setString(13, doctorOrderoNumber);
		cs.registerOutParameter(14, Types.DECIMAL);
		cs.registerOutParameter(15, Types.VARCHAR);
		cs.execute();
		return cs;
	}

	public List<List<String>> getPatientHistory(String patientNo) throws SQLException {
		System.out.println("in getPrevServiceOrderList");

		List<List<String>> list = new ArrayList<>();
		List<String> row = new ArrayList<>();
		List<String> column = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement31(con, patientNo); ResultSet rs = ps.executeQuery()) {

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
					} while (rs.next());

					list.add(row);
				}
			}

			System.out.println("Patient History List : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement31(Connection con, String patientNo) throws SQLException {
		String sql = " select distinct ecn.ECN_TXN_NUM DOCTOR_NOTE, TO_CHAR(ecn.ECN_NOTE_DT, 'DD-MON-YYYY hh:mi AM') \"DATE\", "
				+ " hem.EEM_FIRST_NAME || ' ' || hem.EEM_MIDDLE_NAME || ' ' || hem.EEM_LAST_NAME AS REFER_DOCTOR, NVL(ecn.ECN_NOTE_STS, 'NOT PROCESS') STATUS"
				+ "                from " + "                emr_clinical_detail ecd,"
				+ "                emr_clinical_note ecn," + "                HR_EMPLOYEE_MASTER hem,"
				+ "                GA_CLINICAL_STAFF_MASTER csm" + "                where "
				+ "                hem.EEM_EMP_NUM=csm.GCS_EMPLOYEE_NUM  "
				+ "                AND hem.EEM_EMP_STATUS='A' "
				+ "				 AND csm.GCS_STAFF_TYPE_CD IN ('CONS','SURG')  "
				+ "                and hem.EEM_EMP_NUM = ecn.ECN_DOCTOR_ID" + "                and ecd.ECD_PAT_NUM = ? "
				+ "                and ecd.ECD_EM_NUM = ecn.ECN_EMR_NUM order by \"DATE\" desc";
		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, patientNo);
		return ps;
	}

	public List<String> getDoctorNote(String doctorNoteNumber) throws SQLException {

		System.out.println("in getDoctorNote");

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement32(con, doctorNoteNumber);
					ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					do {
						list.add(rs.getString(1));
						list.add(rs.getString(2));
						list.add(rs.getString(3));
						list.add(rs.getString(4));
						list.add(rs.getString(5));
						list.add(rs.getString(6));
					} while (rs.next());
				}
			}

			System.out.println("Doctor Note List : " + list);
			return list;
		}

	}

	private PreparedStatement createPreparedStatement32(Connection con, String doctorNoteNumber) throws SQLException {
		String sql = "select t.ecn_txn_num " + "          ,TO_CHAR(t.ecn_note_dt, 'DD-MON-YYYY') NOTE_DATE  "
				+ "			,upper(e.eem_first_name||' '||e.eem_middle_name||' '||e.eem_last_name )   doc_name "
				+ "           ,t.ecn_note_type " + "           ,replace(t.ecn_note_dtl, ',', '|')"
				+ "           ,t.ecn_doctor_id " + "      from   emr_clinical_note   t  "
				+ "            ,hr_employee_master  e  " + "      where  t.ecn_doctor_id = e.eem_emp_num "
				+ "             and  t.ecn_txn_num   = ? ";

		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, doctorNoteNumber);
		return ps;
	}

	public String insertUpdateNurseNote(String patientNo, String mrd, String visitNo, String doctorId, String wardNo,
			String bedNo, String nurseNote, String userId, String doctorOrderoNumber) throws SQLException {

		System.out.println("In insertDoctorOrderData");
		int result = 0;
		String nurseNoteNumber = "";
		String returnMsg = "";

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			try (CallableStatement cs = createCallableStatement6(con, patientNo, mrd, visitNo, doctorId, wardNo, bedNo,
					nurseNote, userId, doctorOrderoNumber)) {
				// result = String.valueOf(cs.getDouble(14));
				nurseNoteNumber = cs.getString(9);
				result = cs.getInt(10);
				returnMsg = cs.getString(11);
				System.out.println("Out parameter result : " + result);
				System.out.println("Out parameter drNumber : " + nurseNoteNumber);
				System.out.println("Out parameter returnMsg : " + returnMsg);
			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				e.printStackTrace();
			}

			System.out.println("PKG_I_NURSE_NOTE OUT PARAMETER : " + result);

			if (result == 1) {
				con.commit();
				System.out.println("All Transactions are commited");
			} else {
				System.out.println("All Transactions are rollbacked");
				con.rollback();
			}

		}

		if (result == 1)
			return nurseNoteNumber;
		return "0";

	}

	private CallableStatement createCallableStatement6(Connection con, String patientNo, String mrd, String visitNo,
			String doctorId, String wardNo, String bedNo, String nurseNote, String userId, String nurseNoteoNumber)
			throws SQLException {
		CallableStatement cs = con.prepareCall("{call PKGJV_EMR.PKG_I_NURSE_NOTE(?,?,?,?,?,?,?,?,?,?,?)}");
		cs.setString(1, patientNo);
		cs.setString(2, mrd);
		cs.setString(3, visitNo);
		cs.setString(4, doctorId);
		cs.setString(5, wardNo);
		cs.setString(6, bedNo);
		cs.setString(7, nurseNote);
		cs.setString(8, userId);
		cs.registerOutParameter(9, Types.VARCHAR);
		cs.setString(9, nurseNoteoNumber);
		cs.registerOutParameter(10, Types.DECIMAL);
		cs.registerOutParameter(11, Types.VARCHAR);
		cs.execute();
		return cs;
	}

	public List<List<String>> getNurseNoteHistory(String patientNo) throws SQLException {
		System.out.println("in getNurseNoteHistory");

		List<List<String>> list = new ArrayList<>();
		List<String> row = new ArrayList<>();
		List<String> column = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement33(con, patientNo); ResultSet rs = ps.executeQuery()) {

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
					} while (rs.next());

					list.add(row);
				}
			}

			System.out.println("Nurse Note History : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement33(Connection con, String patientNo) throws SQLException {
		String sql = "select distinct ecn.ECN_TXN_NUM NURSE_NOTE, TO_CHAR(ecn.ECN_NOTE_DT, 'DD-MON-YYYY hh:mi AM') \"DATE\", "
				+ "upper(hem.eem_first_name||' '||hem.eem_middle_name||' '||hem.eem_last_name )   created_by " + "from "
				+ "emr_clinical_detail ecd, " + "emr_clinical_note ecn,  " + "HR_EMPLOYEE_MASTER hem " + "where "
				+ "hem.EEM_EMP_NUM=ecn.ECN_CRT_UID " + "and ecd.ECD_PAT_NUM = ? "
				+ "and ecd.ECD_EM_NUM = ecn.ECN_EMR_NUM order by \"DATE\" desc ";

		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, patientNo);
		return ps;
	}

	public List<String> getNurseNote(String nurseNoteNumber) throws SQLException {

		System.out.println("in getNurseNote");

		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement34(con, nurseNoteNumber);
					ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					do {
						list.add(rs.getString(1));
						list.add(rs.getString(2));
						list.add(rs.getString(3));
						list.add(rs.getString(4));
						list.add(rs.getString(5));
					} while (rs.next());
				}
			}

			System.out.println("Doctor Note List : " + list);
			return list;
		}

	}

	private PreparedStatement createPreparedStatement34(Connection con, String nurseNoteNumber) throws SQLException {
		String sql = "   select t.ecn_txn_num \r\n" + "  ,TO_CHAR(t.ecn_note_dt, 'DD-MON-YYYY') NOTE_DATE "
				+ "  ,t.ecn_doctor_id " + "	,doctor_name " + "  ,replace(t.ecn_note_dtl, ',', '|') "
				+ "  from   emr_clinical_note   t " + "  ,( "
				+ "  select e.eem_emp_num, upper(e.eem_first_name||' '||e.eem_middle_name||' '||e.eem_last_name ) doctor_name "
				+ "  from hr_employee_master  e" + "  union " + "  select 'NA', '' from dual " + "  ) e "
				+ "  where  t.ecn_doctor_id = e.eem_emp_num " + "  and  t.ecn_txn_num   = ?";

		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, nurseNoteNumber);
		return ps;
	}

	public String insertUpdateVitalChart(String patientNo, String mrd, String visitNo, String doctorId, String wardNo,
			String bedNo, Map<String, String> map, String userId, String vitalChartNumber) throws SQLException {

		System.out.println("In insertUpdateVitalChart");
		int result = 0;
		String vitalChartNo = "";
		String returnMsg = "";
		Set<Map.Entry<String, String>> set = map.entrySet();

		try (Connection con = LoginDBConnection.getConnection()) {
			con.setAutoCommit(false);
			for (Map.Entry<String, String> keyValue : set) {
				try (CallableStatement cs = createCallableStatement7(con, patientNo, mrd, visitNo, doctorId, wardNo,
						bedNo, keyValue.getKey(), keyValue.getValue(), userId, vitalChartNumber)) {
					vitalChartNo = cs.getString(10);
					result = cs.getInt(11);
					returnMsg = cs.getString(12);
					System.out.println("Out parameter result : " + result);
					System.out.println("Out parameter drNumber : " + vitalChartNo);
					System.out.println("Out parameter returnMsg : " + returnMsg);
					if (result == 0) {
						break;
					}
				} catch (SQLException e) {
					con.rollback();
					con.setAutoCommit(true);
					e.printStackTrace();
				}
			}
			System.out.println("PKG_I_NURSE_NOTE OUT PARAMETER : " + result);

			if (result == 1) {
				con.commit();
				System.out.println("All Transactions are commited");
			} else {
				System.out.println("All Transactions are rollbacked");
				con.rollback();
			}

		}

		if (result == 1)
			return vitalChartNo;
		return "0";

	}

	private CallableStatement createCallableStatement7(Connection con, String patientNo, String mrd, String visitNo,
			String doctorId, String wardNo, String bedNo, String vitalNote, String vitalValue, String userId,
			String vitalChartNumber) throws SQLException {
		CallableStatement cs = con.prepareCall("{call PKGJV_EMR.PKG_I_VITAL_CHART(?,?,?,?,?,?,?,?,?,?,?,?)}");
		cs.setString(1, patientNo);
		cs.setString(2, mrd);
		cs.setString(3, visitNo);
		cs.setString(4, doctorId);
		cs.setString(5, wardNo);
		cs.setString(6, bedNo);
		cs.setString(7, vitalNote);
		cs.setString(8, vitalValue);
		cs.setString(9, userId);
		cs.registerOutParameter(10, Types.VARCHAR);
		cs.setString(10, vitalChartNumber);
		cs.registerOutParameter(11, Types.DECIMAL);
		cs.registerOutParameter(12, Types.VARCHAR);
		cs.execute();
		return cs;
	}

	public List<String> getPreviousVitalChart(String ipNumber) throws SQLException {
		System.out.println("in getPreviousVitalChart");
		List<String> list = new ArrayList<>();
		try (Connection con = LoginDBConnection.getConnection();) {
			try (PreparedStatement ps = createPreparedStatement35(con, ipNumber); ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					do {
						list.add(rs.getString(1));
						list.add(rs.getString(2));
						list.add(rs.getString(3));
						list.add(rs.getString(4));
						list.add(rs.getString(5));
						list.add(rs.getString(6));
						list.add(rs.getString(7));
						list.add(rs.getString(8));
						list.add(rs.getString(9));
						list.add(rs.getString(10));
						list.add(rs.getString(11));
					} while (rs.next());
				}
			}

			System.out.println("Vital Chart List : " + list);
			return list;
		}
	}

	private PreparedStatement createPreparedStatement35(Connection con, String ipNumber) throws SQLException {
		String sql = "select to_char(ecn.ECN_NOTE_DT, 'DD-MON-YYYY') \"date\" "
				+ ",TO_CHAR(ecn.ECN_NOTE_DT, 'hh:mi AM') \"time\" " + ",ecn.ECN_NOTE_DT "
				+ ",upper(hem.eem_first_name||' '||hem.eem_middle_name||' '||hem.eem_last_name )  created_by "
				+ ",max(decode(ecn.ECN_NOTE_TYPE,'EYOP' , ecn.ECN_NOTE_DTL, '')) eyeopen "
				+ ",max(decode(ecn.ECN_NOTE_TYPE,'VERE' , ecn.ECN_NOTE_DTL, '')) verbal_reponse "
				+ ",max(decode(ecn.ECN_NOTE_TYPE,'MORE' , ecn.ECN_NOTE_DTL, '')) motor_response "
				+ ",max(decode(ecn.ECN_NOTE_TYPE,'BLPD' , ecn.ECN_NOTE_DTL, '')) diastolic "
				+ ",max(decode(ecn.ECN_NOTE_TYPE,'BLPS' , ecn.ECN_NOTE_DTL, '')) systolic "
				+ ",max(decode(ecn.ECN_NOTE_TYPE,'PUPL' , ecn.ECN_NOTE_DTL, '')) pupil_left "
				+ ",max(decode(ecn.ECN_NOTE_TYPE,'PUPR' , ecn.ECN_NOTE_DTL, '')) pupil_right " + " from "
				+ "emr_clinical_detail ecd, " + "emr_clinical_note ecn, " + "HR_EMPLOYEE_MASTER hem " + " where "
				+ "hem.EEM_EMP_NUM=ecn.ECN_CRT_UID " + "and ecd.ECD_PAT_NUM = ? "
				+ "and ecn.ECN_NOTE_TYPE IN ('MORE', 'BLPD', 'BLPS', 'PUPR', 'PUPL', 'EYOP', 'VERE') "
				+ "and ecd.ECD_EM_NUM = ecn.ECN_EMR_NUM "
				+ "group by ecn.ECN_NOTE_DT, to_char(ecn.ECN_NOTE_DT, 'DD-MON-YYYY'), "
				+ "TO_CHAR(ecn.ECN_NOTE_DT, 'hh:mi AM'), upper(hem.eem_first_name||' '||hem.eem_middle_name||' '||hem.eem_last_name) "
				+ "order by ecn.ECN_NOTE_DT asc ";

		System.out.println(sql.toString());
		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		return ps;
	}

	/*
	 * public List<String> getDrugReqItemList(String itemName) throws SQLException {
	 * System.out.println("In getDrugReqItemList"); int result = 0; String
	 * vitalChartNo = ""; String returnMsg = ""; String [] itemDescStringArr = null;
	 * 
	 * try (Connection con = LoginDBConnection.getConnection()) {
	 * con.setAutoCommit(false); try (CallableStatement cs =
	 * createCallableStatement8(con,itemName)) {
	 * 
	 * ARRAY itemCodeArr = ((OracleCallableStatement)cs).getARRAY(3); ARRAY
	 * itemDescArr = ((OracleCallableStatement)cs).getARRAY(4); ARRAY moleculeArr =
	 * ((OracleCallableStatement)cs).getARRAY(5); ARRAY stockArr =
	 * ((OracleCallableStatement)cs).getARRAY(6);
	 * 
	 * String [] itemCodeStringArr = (String [])(itemCodeArr.getArray());
	 * itemDescStringArr = (String [])(itemDescArr.getArray()); String []
	 * moleculeStringArr = (String [])(moleculeArr.getArray()); Integer []
	 * stockStringArr = (Integer [])(stockArr.getArray());
	 * 
	 * 
	 * for (String s : itemCodeStringArr) { System.out.println("Item Code : " + s);
	 * }
	 * 
	 * for (String s : itemCodeStringArr) { System.out.println("Item Name : " + s);
	 * }
	 * 
	 * for (String s : moleculeStringArr) { System.out.println("Molecule : " + s); }
	 * 
	 * for (int s : stockStringArr) { System.out.println("Item Code : " + s); }
	 * 
	 * } catch (SQLException e) { con.rollback(); con.setAutoCommit(true);
	 * e.printStackTrace(); } }
	 * 
	 * return new ArrayList<String>(Arrays.asList(itemDescStringArr));
	 * 
	 * 
	 * }
	 * 
	 * private CallableStatement createCallableStatement8(Connection con, String
	 * itemName) throws SQLException { CallableStatement cs = con
	 * .prepareCall("{call PKGJV_PH_MED_REQUEST.PKG_POPU_PROD(?,?,?,?,?,?)}");
	 * cs.setString(1, itemName); cs.setString(2, "PHRMC");
	 * cs.registerOutParameter(3, OracleTypes.ARRAY, "ITEM_CODE_T");
	 * cs.registerOutParameter(4, OracleTypes.ARRAY, "ITEM_DESC_T");
	 * cs.registerOutParameter(5, OracleTypes.ARRAY, "ITEM_UNIT_T");
	 * cs.registerOutParameter(6, OracleTypes.ARRAY, "ITEM_QTY_T"); cs.execute();
	 * return cs; }
	 */

	public List<String> getDrugReqItemList(String itemName) throws SQLException {
		System.out.println("In getDrugReqItemList");
		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection()) {
			con.setAutoCommit(false);
			try (CallableStatement cs = createCallableStatement8(con, itemName)) {

				ResultSet rs = ((OracleCallableStatement)cs).getCursor(3);
				
				while(rs.next()) {
				//	System.out.println(rs.getString("ITEM_CODE") + "----> " + rs.getString("ITEM_NAME") + "-----> " +rs.getString("MOLE_NAME") + "----> " + rs.getString("ITEM_STK"));
					list.add(rs.getString("ITEM_CODE"));
					list.add(rs.getString("ITEM_NAME"));
					list.add(rs.getString("MOLE_NAME"));
					list.add(rs.getString("ITEM_STK"));
					list.add("0");
				}
				
				
				
			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				e.printStackTrace();
			}
		}

		return list;

	}

	private CallableStatement createCallableStatement8(Connection con, String itemName) throws SQLException {
		CallableStatement cs = con.prepareCall("BEGIN PKGJV_PH_MED_REQUEST.PKG_POPU_PROD(?,?,?); END;");
		cs.setString(1, itemName);
		cs.setString(2, "PHRMC");
		cs.registerOutParameter(3, OracleTypes.CURSOR);
		cs.execute();
		return cs;
	}
	
	public String insertUpdatePharmaOrder(String locationCode,String pharmaOrderNumber,String wardCode,
			String  stockingPoint,String mrd,String patientNo,String referDoctor,String userId,String [] itemCode,String [] qty)
			throws SQLException {

		System.out.println("In insertUpdatePharmaOrder");
		int result = 0;
		String poId = "";
		String returnMsg = "";
		

		try (Connection con = LoginDBConnection.getConnection()) {

			con.setAutoCommit(false);

			int length1 = removeDuplicateSpace(itemCode, itemCode.length);
			int length2 = removeDuplicateSpace(qty, qty.length);

		

				// Fresh Service order entry or Service order updation
				System.out.println("Fresh Service order entry or Service order updation");

				try (CallableStatement cs = createCallableStatement9(con, locationCode, pharmaOrderNumber, wardCode,
						stockingPoint, mrd, patientNo, referDoctor, userId)) {
					
					poId = cs.getString(2);
					result = cs.getInt(9);
					returnMsg = cs.getString(10);

					System.out.println("PO Number : " + poId);
					System.out.println("PKGJV_PH_MED_REQUEST.PKG_I_MED_REQ_HEADER OUT PARAMETER : " + result);
					System.out.println("PKGJV_PH_MED_REQUEST.PKG_I_MED_REQ_HEADER RETURN MSG : " + returnMsg);

				} catch (SQLException e) {
					con.rollback();
					con.setAutoCommit(true);
					e.printStackTrace();
				}

				if (result == 1) {
					
					result = 0;
					returnMsg = "ERROR";
					
					for (int i = 0; i < length1; i++) {
						System.out.println("Item Code : " + itemCode[i]);
						System.out.println("Qty : " + qty[i]);
						try (CallableStatement cs = createCallableStatement10(con, poId, itemCode[i], qty[i], userId)) {
							result = cs.getInt(5);
							returnMsg = cs.getString(6);
							System.out.println("PKGJV_PH_MED_REQUEST.PKG_I_MED_REQ_DETAIL OUT PARAMETER : " + result);
							System.out.println("PKGJV_PH_MED_REQUEST.PKG_I_MED_REQ_DETAIL RETURN MSG : " + returnMsg);
							if (result == 0) {
								break;
							}
						} catch (SQLException e) {
							con.rollback();
							con.setAutoCommit(true);
							e.printStackTrace();
						}
					} // End of for loop

				

				}
			

			if (result == 1) {
				con.commit();
				System.out.println("All Transactions are commited");
			} else {
				System.out.println("All Transactions are rollbacked");
				con.rollback();
			}

		}

		if (result == 1)
			return poId;
		return "F" + returnMsg;

	}

	private CallableStatement createCallableStatement9(Connection con,String locationCode,String pharmaOrderNumber,String wardCode,
			String stockingPoint,String mrd,String patientNo,String referDoctor,String userId) throws SQLException {
		CallableStatement cs = con.prepareCall("{call PKGJV_PH_MED_REQUEST.PKG_I_MED_REQ_HEADER(?,?,?,?,?,?,?,?,?,?)}");
		cs.setString(1, locationCode);
		cs.registerOutParameter(2, Types.VARCHAR);
		cs.setString(2, pharmaOrderNumber);
		cs.setString(3, wardCode);
		cs.setString(4, stockingPoint);
		cs.setString(5, mrd);
		cs.setString(6, patientNo);
		cs.setString(7, referDoctor);
		cs.setString(8, userId);
		cs.registerOutParameter(9, Types.DECIMAL);
		cs.registerOutParameter(10, Types.VARCHAR);
		cs.execute();
		return cs;
	}

	private CallableStatement createCallableStatement10(Connection con, String poNumber, String itemCode, String qty, String userId) throws SQLException {
		CallableStatement cs = con
				.prepareCall("{call PKGJV_PH_MED_REQUEST.PKG_I_MED_REQ_DETAIL(?,?,?,?,?,?)}");
		cs.setString(1, poNumber);
		cs.setString(2, itemCode);
		cs.setString(3, qty);
		cs.setString(4, userId);
		cs.registerOutParameter(5, Types.DECIMAL);
		cs.registerOutParameter(6, Types.VARCHAR);
		cs.execute();
		return cs;
	}
	
	public List<List<String>> getPrevPharmaOrderList(String ipNumber) throws SQLException {
		System.out.println("In getPrevPharmaOrderList");
		List<List<String>> list = new ArrayList<>();
		List<String> row = new ArrayList<>();
		List<String> column = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection()) {
			con.setAutoCommit(false);
			try (CallableStatement cs = createCallableStatement11(con, ipNumber)) {

				ResultSet rs = ((OracleCallableStatement)cs).getCursor(2);
				ResultSetMetaData rsmd = rs.getMetaData();
				
				for(int i=1; i<=rsmd.getColumnCount(); i++) {
					column.add(rsmd.getColumnName(i));
				}
				
				list.add(column);
				if (rs.next()) {
					do {
						for(int i =1; i<=rsmd.getColumnCount(); i++) {
							row.add(rs.getString(rsmd.getColumnName(i)));
						}
					}while(rs.next());
				}
				
				
				list.add(row);
			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				e.printStackTrace();
			}
		}

		return list;

	}

	private CallableStatement createCallableStatement11(Connection con, String ipNumber) throws SQLException {
		CallableStatement cs = con.prepareCall("BEGIN PKGJV_PH_MED_REQUEST.PKG_POPU_PHAR_REQ_HEAD(?,?); END;");
		cs.setString(1, ipNumber);
		cs.registerOutParameter(2, OracleTypes.CURSOR);
		cs.execute();
		return cs;
	}
	
	public List<String> getPharmaOrderDetail(String poNumber) throws SQLException {
		System.out.println("In getPrevPharmaOrderList");
		List<String> list = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection()) {
			con.setAutoCommit(false);
			try (CallableStatement cs = createCallableStatement12(con, poNumber)) {

				ResultSet rs = ((OracleCallableStatement)cs).getCursor(2);
				ResultSetMetaData rsmd = rs.getMetaData();
				
				if (rs.next()) {
					do {
						for(int i =1; i<=rsmd.getColumnCount(); i++) {
							list.add(rs.getString(rsmd.getColumnName(i)));
						}
					}while(rs.next());
				}
				
				
			} catch (SQLException e) {
				con.rollback();
				con.setAutoCommit(true);
				e.printStackTrace();
			}
		}

		return list;

	}

	private CallableStatement createCallableStatement12(Connection con, String ipNumber) throws SQLException {
		CallableStatement cs = con.prepareCall("BEGIN PKGJV_PH_MED_REQUEST.PKG_POPU_PHAR_REQ_DET(?,?); END;");
		cs.setString(1, ipNumber);
		cs.registerOutParameter(2, OracleTypes.CURSOR);
		cs.execute();
		return cs;
	}

}
