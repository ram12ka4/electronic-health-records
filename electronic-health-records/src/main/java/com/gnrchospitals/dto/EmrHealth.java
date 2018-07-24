package com.gnrchospitals.dto;

import java.util.Map;

public class EmrHealth {

	private String emrNo;
	private String emrDetNo;
	private String createdUser;
	private String lastCreatedUser;
	private Map<String, String> keyValue;

	public EmrHealth(String emrNo, String emrDetNo, String createdUser, String lastCreatedUser,
			Map<String, String> keyValue) {
		super();
		this.emrNo = emrNo;
		this.emrDetNo = emrDetNo;
		this.createdUser = createdUser;
		this.lastCreatedUser = lastCreatedUser;
		this.keyValue = keyValue;
	}

	public String getEmrNo() {
		return emrNo;
	}

	public String getEmrDetNo() {
		return emrDetNo;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public String getLastCreatedUser() {
		return lastCreatedUser;
	}

	public String getEmr() {
		return emrNo;
	}

	public String getEmrDet() {
		return emrDetNo;
	}

	public Map<String, String> getKeyValue() {
		return keyValue;
	}

}
