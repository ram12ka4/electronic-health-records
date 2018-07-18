package com.gnrchospitals.dto;

import java.util.List;

import com.gnrchospitals.dto.PatientIdentification;

public interface PatientIdentificationDao {
	
	List<PatientIdentification> getList(String ipNumber);

}
