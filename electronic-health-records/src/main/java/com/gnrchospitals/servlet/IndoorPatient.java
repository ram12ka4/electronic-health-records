package com.gnrchospitals.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gnrchospitals.util.LoginDBConnection;

public class IndoorPatient {

	public ArrayList<ArrayList<String>> getPatientList() {

		ArrayList<String> row = new ArrayList<>();
		ArrayList<String> column = new ArrayList<>();
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		int rowCount = 0;

		try (Connection con = LoginDBConnection.getConnection();
				PreparedStatement ps = createPreparedStatement(con);
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

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Row Count + " + rowCount);
		System.out.println("Indoor Patient List : " + list);
		System.out.println("Indoor Patient List size : " + list.get(1).size());
		return list;

	}

	private PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		StringBuilder sql = new StringBuilder();

		sql.append("select a.WAT_IP_NUM \"IP NUMBER\", b.RRH_FIRST_NAME||' '||b.RRH_MIDDLE_NAME||' '||b.RRH_LAST_NAME NAME, ");
		sql.append(
				"d.WWM_WARD_DESC WARD, a.WAT_BED_CD \"BED NUMBER\", c.EEM_FIRST_NAME||' '||c.EEM_MIDDLE_NAME||' '||c.EEM_LAST_NAME as \"ADMITTING DOCTOR\" ,e.GDM_DEPT_DESC \"SPECIALITY\" ");
		sql.append(" from wa_admission_txn a " + " ,RE_REGISTRATION_HEADER b " + " ,hr_employee_master c"
				+ " ,wa_ward_master d" + " ,ga_department_master e");
		sql.append(" where a.WAT_MR_NUM = b.RRH_MR_NUM " + "  and a.WAT_CURR_WARD_CD = d.WWM_WARD_CD "
				+ "  and a.WAT_DOCTOR_INCHARGE = c.EEM_EMP_NUM " + "  and a.WAT_ADMM_DEPT = e.GDM_DEPT_CD "
				+ "  and WAT_pat_status = 'ADIP' order by name");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		return ps;
	}

}
