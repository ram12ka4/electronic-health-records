package com.gnrchospitals.dto;

public class SequenceNumber {

	private String sequenceCode;
	private String uid;

	public SequenceNumber(String sequenceCode, String uid) {
		super();
		this.sequenceCode = sequenceCode;
		this.uid = uid;
	}

	public SequenceNumber() {

	}

	public String getUid() {
		return uid;
	}

	public String getSequenceCode() {
		return sequenceCode;
	}

	@Override
	public String toString() {
		return "[Sequence Code : " + sequenceCode + " User Id : " + uid +  " ]";
	}

}
