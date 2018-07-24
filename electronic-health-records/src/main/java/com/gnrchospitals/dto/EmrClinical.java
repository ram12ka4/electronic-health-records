package com.gnrchospitals.dto;

public class EmrClinical {

	private String mrdNumber;
	private String emrNumber;
	private String ipNumber;
	private String visitNumber;
	private String encounterNumber;
	private String createUser;
	private String updateUser;

	public EmrClinical(String mrdNumber, String emrNumber, String ipNumber, String visitNumber, String encounterNumber,
			String createUser, String updateUser) {
		super();
		this.mrdNumber = mrdNumber;
		this.emrNumber = emrNumber;
		this.ipNumber = ipNumber;
		this.visitNumber = visitNumber;
		this.encounterNumber = encounterNumber;
		this.createUser = createUser;
		this.updateUser = updateUser;
	}

	public String getMrdNumber() {
		return mrdNumber;
	}

	public String getEmrNumber() {
		return emrNumber;
	}

	public String getIpNumber() {
		return ipNumber;
	}

	public String getVisitNumber() {
		return visitNumber;
	}

	public String getEncounterNumber() {
		return encounterNumber;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

}
