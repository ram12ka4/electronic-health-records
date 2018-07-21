package com.gnrchospitals.dao;

import com.gnrchospitals.dto.EmrClinical;

public interface EmrClinicalDao {

	public abstract boolean findByIpNum(String ipNumber);

	public abstract boolean insert(EmrClinical data);

}
