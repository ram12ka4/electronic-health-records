package com.gnrchospitals.dto;

import java.util.Map;

public class EmrHealth {

	private String emrNo;
	private String emrDetNo;
	private String createdUser;
	private String createdDate;
	private String lastCreatedUser;
	private String lastCreatedDate;
	private Map<String, String> keyValue;

	public EmrHealth(String emrNo, String emrDetNo, String createdUser, String createdDate, String lastCreatedUser,
			String lastCreatedDate, Map<String, String> keyValue) {
		super();
		this.emrNo = emrNo;
		this.emrDetNo = emrDetNo;
		this.createdUser = createdUser;
		this.createdDate = createdDate;
		this.lastCreatedUser = lastCreatedUser;
		this.lastCreatedDate = lastCreatedDate;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public String getLastCreatedUser() {
		return lastCreatedUser;
	}

	public String getLastCreatedDate() {
		return lastCreatedDate;
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
