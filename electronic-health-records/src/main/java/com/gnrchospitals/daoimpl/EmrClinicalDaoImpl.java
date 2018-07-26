package com.gnrchospitals.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gnrchospitals.dao.EmrClinicalDao;
import com.gnrchospitals.dto.EmrClinical;

public class EmrClinicalDaoImpl implements EmrClinicalDao {

	@Override
	public boolean insert(EmrClinical data) {

		boolean flag = false;

		try (Connection con = DatabaseDaoImpl.getConnection()) {

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

	private PreparedStatement createPreparedStatement(Connection con, EmrClinical data) throws SQLException {

		System.out.println(data.getEmrNumber());
		System.out.println(data.getMrdNumber());
		System.out.println(data.getIpNumber());
		System.out.println(data.getVisitNumber());
		System.out.println(data.getEncounterNumber());
		System.out.println(data.getCreateUser());
		System.out.println(data.getUpdateUser());

		String sql = "INSERT INTO EMR_CLINICAL_DETAIL(ECD_EM_NUM, ECD_MR_NUM, ECD_PAT_NUM, ECD_VISIT_NUM, ECD_ENCOUNTER_NUMBER, ECD_ENCOUNTER_DATE, "
				+ "   ECD_CRT_USER_ID, ECD_CRT_DATE, ECD_LST_UPD_USER_ID, ECD_LST_UPD_DATE) VALUES(?,?,?,?,?,sysdate,?,sysdate,?,sysdate)";

		System.out.println(sql);

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, data.getEmrNumber());
		ps.setString(2, data.getMrdNumber());
		ps.setString(3, data.getIpNumber());
		ps.setString(4, data.getVisitNumber());
		ps.setString(5, data.getEncounterNumber());
		ps.setString(6, data.getCreateUser());
		ps.setString(7, data.getUpdateUser());

		return ps;
	}

	@Override
	public boolean findByIpNum(String ipNumber) {

		try (Connection con = DatabaseDaoImpl.getConnection();
				PreparedStatement ps = createPreparedStatement(con, ipNumber);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	private PreparedStatement createPreparedStatement(Connection con, String ipNumber) throws SQLException {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM EMR_CLINICAL_DETAIL WHERE ECD_PAT_NUM = ?");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, ipNumber);
		return ps;
	}

}
