package com.gnrchospitals.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gnrchospitals.dao.DatabaseDao;
import com.gnrchospitals.dao.PatientIdenficationDao;

public class PatientIdentificationDaoImpl implements PatientIdenficationDao {

	@Override
	public List<List<String>> findByIPNumber(String ipNumber) {

		ArrayList<String> row = new ArrayList<>();
		ArrayList<String> column = new ArrayList<>();
		List<List<String>> list = new ArrayList<>();
		int rowCount = 0;

		try (Connection con = DatabaseDao.getConnection();
				PreparedStatement ps = createPreparedStatement(con, ipNumber);
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
			
			System.out.println("List is : " + list);

			list.add(row);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Row Count + " + rowCount);
		System.out.println("Indoor Patient List : " + list);
		System.out.println("Indoor Patient List size : " + list.get(1).size());
		return list;
	}

	private PreparedStatement createPreparedStatement(Connection con, String ipNumber) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT  B.RRH_MR_NUM \"MRD NO\", A.WAT_IP_NUM \"IP NO\", B.RRH_FIRST_NAME||' '||B.RRH_MIDDLE_NAME||' '||B.RRH_LAST_NAME NAME ");
		sql.append(" , (SELECT C.GPM_PARAMETER_VALUE FROM GA_PARAMETER_MASTER C WHERE C.GPM_PARAMETER_CD = B.RRH_PAT_SEX AND C.GPM_PARAMETER_TYPE = 'SEX') SEX");
		sql.append(" , ROUND((trunc(SYSDATE) - B.RRH_PAT_DOB) / 365) AGE");
		sql.append(" , TO_CHAR(A.WAT_ADMN_DT, 'DD-MON-YYYY hh:mi AM') \"ADMISSION DATE\", 'DR. ' || D.EEM_FIRST_NAME||' '|| D.EEM_MIDDLE_NAME||''|| D.EEM_LAST_NAME \"DOCTOR NAME\"");
		sql.append(" FROM WA_ADMISSION_TXN A, RE_REGISTRATION_HEADER B,  HR_EMPLOYEE_MASTER D ");
		sql.append(" WHERE  A.WAT_MR_NUM = B.RRH_MR_NUM AND D.EEM_EMP_NUM = A.WAT_DOCTOR_INCHARGE AND  A.WAT_IP_NUM = ?");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		return ps;
	}

	@Override
	public boolean save(String ipNumber) {
		// TODO Auto-generated method stub
		return false;
	}

}
