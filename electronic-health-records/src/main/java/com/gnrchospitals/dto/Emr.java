package com.gnrchospitals.dto;

import java.util.Map;

public class Emr {

	private String emrNo;
	private String emrDetNo;
	private String visitNo;
	private String encounterNo;
	private String createUser;
	private String updateUser;
	private Map<String, String> keyValue;
	private static Emr emr;

	private Emr() {

	}

	static {

		try {
			emr = new Emr();
		} catch (Exception e) {
			throw new RuntimeException("Error occured in Singleton object creation");
		}
	}

	public static Emr getInstance() {
		return emr;
	}

	public String getEmrNo() {
		return emrNo;
	}

	public void setEmrNo(String emrNo) {
		this.emrNo = emrNo;
	}

	public String getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(String visitNo) {
		this.visitNo = visitNo;
	}

	public String getEncounterNo() {
		return encounterNo;
	}

	public void setEncounterNo(String encounterNo) {
		this.encounterNo = encounterNo;
	}

	public void setEmrDetNo(String emrDetNo) {
		this.emrDetNo = emrDetNo;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public void setKeyValue(Map<String, String> keyValue) {
		this.keyValue = keyValue;
	}

	public String getEmrNumber() {
		return emrNo;
	}

	public String getEmrDetNo() {
		return emrDetNo;
	}

	public String getVisitNumber() {
		return visitNo;
	}

	public String getEncounterNumber() {
		return encounterNo;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public Map<String, String> getKeyValue() {
		return keyValue;
	}

	@Override
	public String toString() {
		return "Emr [emrNumber=" + emrNo + ", emrDetNo=" + emrDetNo + ", visitNumber=" + visitNo + ", encounterNumber="
				+ encounterNo + ", createUser=" + createUser + ", updateUser=" + updateUser + ", keyValue=" + keyValue
				+ "]";
	}

}
