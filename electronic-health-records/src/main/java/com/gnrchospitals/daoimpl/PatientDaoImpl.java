package com.gnrchospitals.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.dto.Patient;
import com.gnrchospitals.util.LoginDBConnection;

public class PatientDaoImpl implements PatientDao {

	private static Patient patientInfo = null;

	@Override
	public Patient findByIpNumber(String ipNumber) {

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement(con, ipNumber);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				do {
					patientInfo = new Patient(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
							rs.getString(10), rs.getString(11));
				} while (rs.next());
			}

			System.out.println("List is : " + patientInfo);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patientInfo;
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

}
