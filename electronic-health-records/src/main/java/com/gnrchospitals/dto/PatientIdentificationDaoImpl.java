package com.gnrchospitals.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.gnrchospitals.dao.DatabaseDao;
import com.gnrchospitals.dto.PatientIdentification;

public class PatientIdentificationDaoImpl implements PatientIdentificationDao {

	private static List<PatientIdentification> patientList = new ArrayList<>();

	@Override
	public List<PatientIdentification> getList(String ipNumber) {

		int rowCount = 0;
		//List<PatientIdentification> patientList = new ArrayList<>();
		try (Connection con = DatabaseDao.getConnection();
				PreparedStatement ps = createPreparedStatement(con, ipNumber);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				do {

					PatientIdentification patient = new PatientIdentification(rs.getString(1), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
					patientList.add(patient);
					rowCount++;
				} while (rs.next());
			}

			System.out.println("List is : " + patientList);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Row Count + " + rowCount);
		System.out.println("Indoor Patient List : " + patientList);
		System.out.println("Indoor Patient List size : " + patientList.size());

		return patientList;

	}

	private PreparedStatement createPreparedStatement(Connection con, String ipNumber) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append(
				" SELECT  B.RRH_MR_NUM \"MRD NO\", A.WAT_IP_NUM \"IP NO\", B.RRH_FIRST_NAME||' '||B.RRH_MIDDLE_NAME||' '||B.RRH_LAST_NAME NAME ");
		sql.append(
				" , (SELECT C.GPM_PARAMETER_VALUE FROM GA_PARAMETER_MASTER C WHERE C.GPM_PARAMETER_CD = B.RRH_PAT_SEX AND C.GPM_PARAMETER_TYPE = 'SEX') SEX");
		sql.append(" , ROUND((trunc(SYSDATE) - B.RRH_PAT_DOB) / 365) AGE");
		sql.append(
				" , A.WAT_ADMN_DT \"ADMISSION DATE\", 'DR. ' || D.EEM_FIRST_NAME||' '|| D.EEM_MIDDLE_NAME||''|| D.EEM_LAST_NAME \"DOCTOR NAME\"");
		sql.append(" FROM WA_ADMISSION_TXN A, RE_REGISTRATION_HEADER B,  HR_EMPLOYEE_MASTER D ");
		sql.append(
				" WHERE  A.WAT_MR_NUM = B.RRH_MR_NUM AND D.EEM_EMP_NUM = A.WAT_DOCTOR_INCHARGE AND  A.WAT_IP_NUM = ?");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		return ps;
	}

}
