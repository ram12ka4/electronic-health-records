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

		return false;
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
