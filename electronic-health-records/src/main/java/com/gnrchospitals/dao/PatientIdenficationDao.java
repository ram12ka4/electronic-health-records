package com.gnrchospitals.dao;

import java.util.List;

public interface PatientIdenficationDao {
	
	public abstract List<List<String>> findByIPNumber(String ipNumber);
	public abstract boolean save(String ipNumber);

}
