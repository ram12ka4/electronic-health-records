package com.gnrchospitals.dto;

public class EmrClinical {

	private String mrdNumber;
	private String emrNumber;
	private String ipNumber;
	private String visitNumber;


	public void setMrdNumber(String mrdNumber) {
		this.mrdNumber = mrdNumber;
	}

	public void setEmrNumber(String emrNumber) {
		this.emrNumber = emrNumber;
	}

	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
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

}
