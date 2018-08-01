package com.gnrchospitals.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
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
	public Patient findByIpNumber(String ipNumber) {

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

		} catch (SQLException e) {
			e.printStackTrace();
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
	public boolean validateKey(String key) {
		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement2(con, key);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
	public boolean insertEmrClinicalData(Patient data) {
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

		} catch (SQLException e) {
			e.printStackTrace();
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
	public boolean insertEmrHealthData(Patient data) {
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
				e.printStackTrace();
			}

			con.commit();
			con.setAutoCommit(true);
			if (flag)
				return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
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
	public boolean getValidatedIp(String ipNumber) {
		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement1(con, ipNumber);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
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
	public boolean findEmrByIpNumber(String ipNumber) {
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

		} catch (SQLException e) {
			e.printStackTrace();
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
	public List<List<String>> getDoctorPreviousNote(String ipNumber) {

		List<List<String>> list = new ArrayList<>();
		List<String> col = new ArrayList<>();
		List<String> row = new ArrayList<>();

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement5(con, ipNumber);
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

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private PreparedStatement createPreparedStatement5(Connection con, String ipNumber) throws SQLException {

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT MAX(DECODE(B.EHR_ATTRB_CODE, 'DN001',B.EHR_ATTRB_VALUE,'')) DOCTOR_NAME ");
		sql.append(" , MAX(DECODE(B.EHR_ATTRB_CODE, 'DN004',B.EHR_ATTRB_VALUE,'')) DOCTOR_NOTE ");
		sql.append(" , B.EHR_DTL_CODE, B.EHR_CRT_DT ");
		sql.append(" FROM EMR_CLINICAL_DETAIL A, EMR_HEALTH_RECORD B ");
		sql.append(" WHERE B.EHR_ATTRB_CODE IN ( 'DN004', 'DN001') ");
		sql.append(" AND A.ECD_EM_NUM = B.EHR_EMR_NUM AND A.ECD_PAT_NUM = ? ");
		sql.append(" GROUP BY B.EHR_DTL_CODE, B.EHR_CRT_DT ");
		sql.append(" ORDER BY EHR_DTL_CODE DESC ");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		return ps;
	}

	@Override
	public boolean deleteDoctorNote(String emrDetId) {
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

		} catch (SQLException ex) {
			ex.printStackTrace();
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
	public boolean updateDoctorNote(String doctorNote, String emrDetId) {
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

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	private PreparedStatement createPreparedStatement7(Connection con, String doctorNote, String edNo)
			throws SQLException {

		System.out.println("EMR DET ID: " + edNo);

		String sql = "UPDATE EMR_HEALTH_RECORD SET EHR_ATTRB_VALUE = ?  WHERE EHR_DTL_CODE = ? and EHR_ATTRB_CODE='DN004'";

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());

		ps.setString(1, doctorNote);
		ps.setString(2, edNo);

		return ps;
	}

	@Override
	public List<List<String>> getDoctorNote(String emrDetId) {

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

		} catch (SQLException e) {
			e.printStackTrace();
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

}
