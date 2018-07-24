package com.gnrchospitals.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.gnrchospitals.dao.EmrHealthDao;
import com.gnrchospitals.dto.EmrHealth;

public class EmrHealthDaoImpl implements EmrHealthDao {

	@Override
	public boolean validateKey(String key) {

		try (Connection con = DatabaseDaoImpl.getConnection();
				PreparedStatement ps = createPreparedStatement(con, key);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private PreparedStatement createPreparedStatement(Connection con, String key) throws SQLException {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM EMR_ATTRIBUTE_MASTER WHERE EAM_ATTRB_CODE = ?");

		System.out.println(sql.toString());

		PreparedStatement ps = con.prepareStatement(sql.toString());
		ps.setString(1, key);
		return ps;
	}

	@Override
	public boolean insert(EmrHealth data) {

		boolean flag = false;

		try (Connection con = DatabaseDaoImpl.getConnection()) {

			con.setAutoCommit(false);

			try (PreparedStatement ps = createPreparedStatement(con, data)) {

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

	private PreparedStatement createPreparedStatement(Connection con, EmrHealth data) throws SQLException {

		StringBuilder sql = new StringBuilder();

		String emrNo = data.getEmr();
		String emrDetNo = data.getEmrDetNo();
		String createdUser = data.getCreatedUser();
		String lastCreatedUser = data.getLastCreatedUser();
		Map<String, String> keyValue = data.getKeyValue();
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

			System.out.println("EMR NO : " + emrNo);
			System.out.println("EMR DET NO : " + emrDetNo);
			System.out.println("KEY : " + me.getKey());
			System.out.println("VALUE  : " + me.getValue());
			System.out.println("CREATED USER : " + createdUser);
			System.out.println("LAST CREATED USER : " + lastCreatedUser);
			ps.addBatch();
		}
		return ps;
	}

}
