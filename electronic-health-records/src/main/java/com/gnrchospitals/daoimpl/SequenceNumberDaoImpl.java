package com.gnrchospitals.daoimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gnrchospitals.dao.SequenceNumberDao;
import com.gnrchospitals.dto.SequenceNumber;

public class SequenceNumberDaoImpl implements SequenceNumberDao {

	@Override
	public String getSequenceNumber() {

		SequenceNumber sequenceNumber = new SequenceNumber();

		String sequenceCode = sequenceNumber.getSequenceCode();
		String userId = sequenceNumber.getUid();

		try (Connection con = DatabaseDaoImpl.getConnection();
				CallableStatement cs = createCallableStatement(con, sequenceCode, userId);
				ResultSet rs = cs.executeQuery()) {

			if (rs.next()) {
				return cs.getString(3);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private CallableStatement createCallableStatement(Connection con, String sequenceCode, String userId)
			throws SQLException {

		String sql = "{call PKGMM_LOGIN_DETAILS.PKG_GET_NEXT_SEQID(?, ?, ?, ?)}";

		System.out.println(sql.toString());

		CallableStatement cs = con.prepareCall(sql);
		cs.setString(1, sequenceCode);
		cs.setString(2, userId);
		cs.registerOutParameter(3, java.sql.Types.VARCHAR);
		cs.registerOutParameter(4, java.sql.Types.VARCHAR);

		return cs;
	}

}
